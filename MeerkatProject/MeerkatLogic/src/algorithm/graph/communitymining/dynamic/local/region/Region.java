package algorithm.graph.communitymining.dynamic.local.region;

import datastructure.core.graph.classinterface.IVertex;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.commons.collections15.keyvalue.MultiKey;
import org.apache.commons.collections15.map.MultiKeyMap;

/**
 * 
 * This class represents the data structure for region which is used in local
 * community mining
 */

public class Region<V extends IVertex> implements Cloneable {

	private Map<V, String> vertices_label;

	private Set<V> assigendVertices;

	private Vector<V> regionVertices;
	private Vector<V> boundaryVertices;
	private Vector<V> shellVertices;

	private Map<V, Vector<V>> verticesNeighbours;
	private MultiKeyMap<V, Double> edgeWeights;

	private double region2RegionWeight;
	private double boundary2RegionWeight;
	private double boundary2ShellWeight;

	public Region(Map<V, Vector<V>> verticesNeighbours,
			MultiKeyMap<V, Double> edgeWeights, Map<V, String> vertices_label,
			TreeSet<V> vertices, Set<V> assignedVertices) {

		this.vertices_label = vertices_label;
		this.assigendVertices = assignedVertices;
		this.verticesNeighbours = verticesNeighbours;
		this.edgeWeights = edgeWeights;

		regionVertices = new Vector<V>();
		boundaryVertices = new Vector<V>();
		shellVertices = new Vector<V>();

		configureAreasAndUpdateWeights(vertices);
	}

	@SuppressWarnings("unchecked")
	public void configureAreasAndUpdateWeights(TreeSet<V> vertices) {
		this.regionVertices.addAll(vertices);
		Set<MultiKey<V>> visitedEdge = new HashSet<MultiKey<V>>();

		for (V vertex : vertices) {
			double current_in = 0;
			double current_out = 0;

			// discover the new shell vertices
			boolean isBoundary = false;
			if (verticesNeighbours.get(vertex) != null)
				for (V adjV : verticesNeighbours.get(vertex)) {
					if (assigendVertices == null
							|| !assigendVertices.contains(adjV)) {
						if (!regionVertices.contains(adjV)) {

							current_out += edgeWeights.get(vertex, adjV);
							if (!isBoundary)
								isBoundary = true;

							if (!shellVertices.contains(adjV))
								shellVertices.add(adjV);

						} else {
							if (!visitedEdge.contains(new MultiKey<V>(vertex,
									adjV))) {
								current_in += edgeWeights.get(vertex, adjV);
								visitedEdge.add(new MultiKey<V>(vertex, adjV));
								visitedEdge.add(new MultiKey<V>(adjV, vertex));
							}

							// make sure the adjV remains in boundary node
							boolean isAdjBoundary = false;
							if (boundaryVertices.contains(adjV)) {
								for (V adjAdjV : verticesNeighbours.get(adjV)) {
									if (!regionVertices.contains(adjAdjV)) {
										isAdjBoundary = true;
										break;
									}
								}
								// if the adjeV doesn't have connection to
								// outside
								// remove it from boundary
								if (!isAdjBoundary) {
									boundaryVertices.remove(adjV);
								}
							}
						}
					}
				}

			region2RegionWeight += current_in;
			boundary2ShellWeight += current_out;

			// add it to boundary if it has connection to outside
			if (isBoundary) {
				boundaryVertices.add(vertex);
				boundary2RegionWeight += current_in;
			}

		}

	}

	@SuppressWarnings("unchecked")
	public void addVertex(V vertex) {
		this.regionVertices.add(vertex);

		double current_in = 0;
		double current_out = 0;
		// discover the new shell vertices
		boolean isBoundary = false;
		if (verticesNeighbours.get(vertex) != null)
			for (V adjV : verticesNeighbours.get(vertex)) {
				if (assigendVertices == null
						|| !assigendVertices.contains(adjV)) {
					if (!regionVertices.contains(adjV)) {
						current_out += edgeWeights.get(vertex, adjV);
						if (!isBoundary)
							isBoundary = true;

						if (!shellVertices.contains(adjV))
							shellVertices.add(adjV);

					} else {
						current_in += edgeWeights.get(vertex, adjV);
						// make sure the adjV remains in boundary node
						boolean isAdjBoundary = false;
						if (boundaryVertices.contains(adjV)) {
							for (V adjAdjV : verticesNeighbours.get(adjV)) {
								if (!regionVertices.contains(adjAdjV)) {
									isAdjBoundary = true;
									break;
								}
							}
							// if the adjeV doesn't have connection to outside
							// remove it from boundary
							if (!isAdjBoundary) {
								boundaryVertices.remove(adjV);
							}
						}
					}
				}
			}

		region2RegionWeight += current_in;
		boundary2ShellWeight += (current_out - current_in);

		// add it to boundary if it has Es to outside
		if (isBoundary) {
			boundaryVertices.add(vertex);
			boundary2RegionWeight += current_in;
		}
		// remove it from shell if it was a shell
		if (shellVertices.contains(vertex))
			shellVertices.remove(vertex);

	}

	@SuppressWarnings("unchecked")
	public void removeVertex(V vertex) {
		this.regionVertices.remove(vertex);

		double current_in = 0;
		double current_out = 0;

		// find boundary nodes
		boolean isShell = false;
		if (verticesNeighbours.get(vertex) != null)
			for (V adjV : verticesNeighbours.get(vertex)) {
				if (regionVertices.contains(adjV)) {
					// since this adjV has connection to outside world,
					// it is a boundary
					if (!boundaryVertices.contains(adjV))
						boundaryVertices.add(adjV);
					if (!isShell)
						isShell = true;

					current_in += edgeWeights.get(vertex, adjV);
				} else {
					current_out += edgeWeights.get(vertex, adjV);
				}
				// make sure the adjV remains in shell node
				boolean isAdjShell = false;
				if (shellVertices.contains(adjV)) {
					for (V adjAdjV : verticesNeighbours.get(adjV)) {
						if (regionVertices.contains(adjAdjV)) {
							isAdjShell = true;
							break;
						}
					}
					// if the adjeV doesn't have connection to inside
					// remove it
					if (!isAdjShell) {
						shellVertices.remove(adjV);
					}
				}
			}
		region2RegionWeight -= current_in;
		boundary2ShellWeight -= (current_out - current_in);

		// if it has connection to the community, keep it as shell
		if (isShell)
			shellVertices.add(vertex);

		// if it was boundary node, remove it from boundary nodes
		if (boundaryVertices.contains(vertex)) {
			boundaryVertices.remove(vertex);
			boundary2RegionWeight -= current_in;
		}

	}

	public boolean containsVertex(V V) {
		if (regionVertices.contains(V))
			return true;
		return false;
	}

	public int size() {
		return regionVertices.size();
	}

	public boolean isEmpty() {
		if (this.regionVertices.size() == 0)
			return true;

		return false;
	}

	public Map<V, String> getlabels() {
		return vertices_label;
	}

	public Vector<V> getVertices() {
		return regionVertices;
	}

	public Vector<V> getShellVertices() {
		return shellVertices;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("[");
		for (V v : regionVertices)
			string.append(vertices_label.get(v) + ",");
		// remove extra ,
		string.deleteCharAt(string.length() - 1);
		string.append("]");
		return string.toString();
	}

	public double getRegion2RegionWeight() {
		return region2RegionWeight;
	}

	public double getBoundary2RegionWeight() {
		return boundary2RegionWeight;
	}

	public double getBoundary2ShellWeight() {
		return boundary2ShellWeight;
	}

	public int getRegionSize() {
		return regionVertices.size();
	}

	public int getBoundarySize() {
		return boundaryVertices.size();
	}

	public int getShellSize() {
		return shellVertices.size();
	}

	public Map<V, Vector<V>> getVerticesNeighbours() {
		return verticesNeighbours;
	}

	public MultiKeyMap<V, Double> getEdgeWeights() {
		return edgeWeights;
	}

	@Override
	public Object clone() {
		try {
			@SuppressWarnings("unchecked")
			Region<V> cloned = (Region<V>) super.clone();
			cloned.verticesNeighbours = verticesNeighbours;
			cloned.vertices_label = vertices_label;
			cloned.edgeWeights = edgeWeights;
			cloned.assigendVertices = assigendVertices;

			cloned.regionVertices = new Vector<V>(regionVertices);
			cloned.boundaryVertices = new Vector<V>(boundaryVertices);
			cloned.shellVertices = new Vector<V>(shellVertices);

			cloned.region2RegionWeight = region2RegionWeight;
			cloned.boundary2ShellWeight = boundary2ShellWeight;
			cloned.boundary2RegionWeight = boundary2RegionWeight;

			return cloned;
		} catch (CloneNotSupportedException e) {
			System.out.println(e);
			return null;
		}
	}
}
