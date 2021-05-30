/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithm.graph.communitymining.dynamic.local;

import algorithm.graph.communitymining.dynamic.auxilaryDS.Region;
import algorithm.graph.communitymining.dynamic.auxilaryDS.Snapshot;
import algorithm.graph.communitymining.dynamic.measure.graphcriteria.Lmetric;
import algorithm.graph.communitymining.dynamic.measure.graphcriteria.Mmetric;
import algorithm.graph.communitymining.dynamic.measure.graphcriteria.Rmetric;
import algorithm.graph.communitymining.dynamic.mininginfo.LocalInfo;
import algorithm.graph.communitymining.dynamic.util.VertexComparator;
import config.dynamiccommunitymining.Metric;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class RegionIdentification<V extends IVertex, E extends IEdge<V>> {
    private Snapshot<V, E> snapshot;

    public RegionIdentification(Snapshot<V, E> snapshot) {
            this.snapshot = snapshot;
    }

    public Region<V,E> identifyRegion(LocalInfo miningInfo, Set<V> startVertices) {

        TreeSet<V> orderedVertices = new TreeSet<>(new VertexComparator<>(
                        snapshot.getLabels()));
        orderedVertices.addAll(startVertices);
        Region<V,E> newRegion = new Region<>(snapshot.getLabels(), snapshot.getGraph(), snapshot.getTimeFrame(),
                        orderedVertices, snapshot.getAssignedVertices());

        
        // expand the community
        newRegion = discoveryPhase(newRegion, miningInfo.getHub(),
                        miningInfo.getMetric());

//        System.out.println("RegionIdentification.identifyRegion() : region vertices --> ");
//        for (V v : newRegion.getVertices()) {
//            System.out.print(v.getId()+ " , ");
//        }
//        System.out.println();
        
        if (miningInfo.getMetric().equals(Metric.L)
                        && miningInfo.getHub() == true) {
            // remove the hub Vertices
            newRegion = examinationPhase(newRegion);
        }

        return newRegion;

    }

    private Region<V,E> discoveryPhase(Region<V,E> region, boolean hub,
                    Metric metric) {
        if (metric.equals(Metric.L)) {
            return discoveryPhaseWithLmetric(region, hub);
        } else if (metric.equals(Metric.M)) {
            return discoveryPhaseWithMmetric(region);
        } else if (metric.equals(Metric.R)) {
            return null;
        }
        return null;
    }

    private Region<V,E> discoveryPhaseWithLmetric(Region<V,E> region, boolean hub) {
        Collection<V> selectedShells = new Vector<>();

        while (true) {

            Region<V,E> maxRegionPrime = findExpandedRegionWithMaxMetric(region,
                            selectedShells, Metric.L);
            if (maxRegionPrime == null) {
                break;
            }

            if (evaluateRegion(maxRegionPrime, Metric.L) < evaluateRegion(
                            region, Metric.L)) {
                break;
            }

            // check the second case
            if (hub
                && (maxRegionPrime.getRegion2RegionWeight() < region
                                .getRegion2RegionWeight() && maxRegionPrime
                                .getBoundary2ShellWeight() < region
                                .getBoundary2ShellWeight())) {

                // find out the added V
                Set<V> addedVertex = maxRegionPrime.getVertices();
                addedVertex.removeAll(region.getVertices());

                // save this shell so that it won't chosen again
                selectedShells.add(addedVertex.iterator().next());

            } else {

                // keep the Vertex for the first and third cases
                region = maxRegionPrime;

                // reset selected shells
                selectedShells = new Vector<>();
            }
        }

        return region;
    }

    private Region<V,E> discoveryPhaseWithMmetric(Region<V,E> region) {

        while (true) {

            Region<V,E> maxRegionPrime = findExpandedRegionWithMaxMetric(region,
                            null, Metric.M);
            if (maxRegionPrime == null)
                break;

            if (evaluateRegion(maxRegionPrime, Metric.M) < evaluateRegion(
                            region, Metric.M))
                break;

            else {
                // keep the Vertex
                region = maxRegionPrime;
            }
        }

        return region;
    }

    private Region<V,E> examinationPhase(Region<V,E> region) {

        @SuppressWarnings("unchecked")
        Region<V,E> resultRegion = (Region<V,E>) region.clone();

        // after the merge check the Vertex in community
        for (V vertex : region.getVertices()) {

            @SuppressWarnings("unchecked")
            Region<V,E> regionBeforeMerge = (Region<V,E>) region.clone();
            regionBeforeMerge.removeVertex(vertex);

            // only keep the fist case
            if (!(regionBeforeMerge.getRegion2RegionWeight() <= region
                            .getRegion2RegionWeight() && regionBeforeMerge
                            .getBoundary2ShellWeight() >= region
                            .getBoundary2ShellWeight())) {
                resultRegion.removeVertex(vertex);
            }
        }

        return resultRegion;

    }

    protected Region<V,E> findExpandedRegionWithMaxMetric(Region<V,E> region,
                    Collection<V> selectedShells, Metric metric) {
        Region<V,E> maxRegionPrime = null;
        V maxVertex = null;

        for (V shellVertex : region.getShellVertices()) {
            if (selectedShells == null || !selectedShells.contains(shellVertex)) {

                @SuppressWarnings("unchecked")
                Region<V,E> regionPrime = (Region<V,E>) region.clone();
                regionPrime.addVertex(shellVertex);

                if (maxRegionPrime == null
                                || evaluateRegion(regionPrime, metric) > evaluateRegion(
                                                maxRegionPrime, metric)) {
                    maxRegionPrime = regionPrime;
                    maxVertex = shellVertex;
                }
                // make it consistent
                else if (evaluateRegion(regionPrime, metric) == evaluateRegion(
                                maxRegionPrime, metric)) {

                    VertexComparator<V> comparator = new VertexComparator<>(
                                    region.getLables());

                    if (comparator.compare(shellVertex, maxVertex) < 0) {
                            maxRegionPrime = regionPrime;
                            maxVertex = shellVertex;
                    }
                }
            }
        }
        return maxRegionPrime;
    }

    protected void insertionSort(Vector<Set<V>> connectedComponents, int index,
                    Set<V> component) {

        for (; index < connectedComponents.size(); index++) {
            if (connectedComponents.get(index).size() < component.size()) {
                break;
            }
        }

        connectedComponents.add(index, component);

    }

    private double evaluateRegion(Region<V,E> region, Metric metric) {
        double value = 0;

        if (metric.equals(Metric.L)) {
            Lmetric<V, E> lmetric = new Lmetric<>(snapshot.getGraph(),
                    snapshot.getTimeFrame(),
                            /*snapshot.getWeights(),*/ false);
            value = lmetric.evaluateRegion(region);
        } else if (metric.equals(Metric.M)) {

            Mmetric<V, E> mmetric = new Mmetric<>(snapshot.getGraph()/*,
                            snapshot.getWeights()*/,
                            snapshot.getTimeFrame());
            value = mmetric.evaluateRegion(region);
        } else if (metric.equals(Metric.R)) {
            Rmetric<V, E> rmetric = new Rmetric<>();
            value = rmetric.evaluateRegion(region);
        }

        return value;
    }
}
