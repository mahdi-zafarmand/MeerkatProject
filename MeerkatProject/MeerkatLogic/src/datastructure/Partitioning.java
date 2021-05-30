package datastructure;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author aabnar
 * @param <V>
 */
public class Partitioning<V> {

	Vector<Set<V>> communities;
	Set<V> hubs;
	Set<V> outliers;
	double modularity;
	double criteriaVal;

    /**
     *
     * @return
     */
    public double getCriteriaVal() {
		return criteriaVal;
	}

    /**
     *
     * @param criteriaVal
     */
    public void setCriteriaVal(double criteriaVal) {
		this.criteriaVal = criteriaVal;
	}

    /**
     *
     */
    public Partitioning() {
		communities = new Vector<Set<V>>();
		hubs = new HashSet<V>();
		outliers = new HashSet<V>();
	}

    /**
     *
     * @param clusters
     */
    public Partitioning(Vector<Set<V>> clusters) {
		this.communities = clusters;
		hubs = new HashSet<V>();
		outliers = new HashSet<V>();
	}

    /**
     *
     * @param e
     * @return
     */
    public boolean isHub(V e) {
		return hubs.contains(e);
	}

    /**
     *
     * @param e
     * @return
     */
    public boolean removeHub(V e) {
		return hubs.remove(e);
	}

    /**
     *
     * @param ind
     * @return
     */
    public Set<V> getCluster(int ind) {
		return communities.get(ind);
	}

    /**
     *
     * @param e
     * @return
     */
    public boolean isOutlier(V e) {
		return outliers.contains(e);
	}

	/* TODO: not efficient for large clusters!!! change it if it is being called
	* in a loop
	*/

    /**
     *
     * @param e
     * @return
     */
    
	public Set<V> getCommunity(V e) {
		for (Set<V> cluster : communities) {
			if (cluster.contains(e))
				return cluster;
		}
		if (hubs.contains(e))
			return hubs;
		return outliers;
	}

    /**
     *
     * @param comm
     */
    public void removeCommunity(HashSet<V> comm) {
		communities.remove(comm);
	}

    /**
     *
     * @return
     */
    public Vector<Set<V>> getCommunities() {
		return communities;
	}

    /**
     *
     * @return
     */
    public int getNumberOfClusters() {
		return communities.size();
	}

    /**
     *
     * @return
     */
    public Set<V> getHubs() {
		return hubs;
	}

    /**
     *
     * @param hubs
     */
    public void setHubs(Set<V> hubs) {
		this.hubs = hubs;
	}

    /**
     *
     * @return
     */
    public Set<V> getOutliers() {
		return outliers;
	}

    /**
     *
     * @param e
     * @return
     */
    public boolean addCluster(Set<V> e) {
		return communities.add(e);
	}

    /**
     *
     * @param e
     * @return
     */
    public boolean addOutlier(V e) {
		return outliers.add(e);
	}

    /**
     *
     * @param e
     * @return
     */
    public boolean addHub(V e) {
		return hubs.add(e);
	}

    /**
     *
     * @param outliers
     */
    public void setOutliers(Set<V> outliers) {
		this.outliers = outliers;
	}

    /**
     *
     * @param o
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		if (!(o instanceof Partitioning))
			return false;
		return (new HashSet<Set<V>>(communities)).equals(new HashSet<Set<V>>(
				((Partitioning<V>) o).getCommunities()));
	}

    /**
     *
     * @return
     */
    @Override
	public String toString() {

		Vector<V> hubVect = new Vector<V>();
		for (V vert : hubs) {
			hubVect.add(vert);
		}


		Vector<V> outlierVect = new Vector<V>();
		for (V vert : outliers) {
			outlierVect.add(vert);
		}

		String res = "[";
		res += " Communities(" + communities.size() + "): "
				+ communities.toString();
		res += "\n Hubs: " + hubVect.toString();
		res += "\n Outliers: " + outlierVect.toString() + "]";
		return res;
	}

    /**
     *
     * @return
     */
    public String getStatistics() {
		String res = " Number of Clusters: " + communities.size();

		double avgClusterSize = 0, sdClusterSize = 0, maxClusterSize = 0, minClusterSize = -1;

		for (Set<V> cluster : communities) {
			avgClusterSize += cluster.size();
			sdClusterSize += cluster.size() * cluster.size();
			if (cluster.size() > maxClusterSize)
				maxClusterSize = cluster.size();
			if (minClusterSize == -1 || minClusterSize > cluster.size())
				minClusterSize = cluster.size();
		}

		avgClusterSize /= communities.size();
		sdClusterSize /= communities.size();

		res += " , size: " + avgClusterSize + " +/- "
				+ Math.sqrt(sdClusterSize - avgClusterSize * avgClusterSize)
				+ " in [" + minClusterSize + "," + maxClusterSize + "]";
		// res +=" Clusters: " + clusters.toString();

		res += " , number of Hubs: " + hubs.size();
		res += " , Outliers: " + outliers.size();
		return res;
	}

    /**
     *
     * @return
     */
    public double getModularity() {
		return modularity;
	}

    /**
     *
     * @param modularity
     */
    public void setModularity(double modularity) {
		this.modularity = modularity;
	}

    /**
     *
     * @return
     */
    @Override
	public Partitioning<V> clone() {
		Partitioning<V> partitioning = new Partitioning<V>();

		partitioning.communities = new Vector<Set<V>>();
		for (Set<V> commSet : communities) {
			partitioning.communities.add(new HashSet<V>(commSet));
		}
		partitioning.outliers = new HashSet<V>(outliers);
		partitioning.hubs = new HashSet<V>(hubs);

		return partitioning;
	}

}
