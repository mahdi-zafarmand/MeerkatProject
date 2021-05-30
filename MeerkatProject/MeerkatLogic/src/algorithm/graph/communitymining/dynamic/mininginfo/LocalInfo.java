/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.communitymining.dynamic.mininginfo;

import config.dynamiccommunitymining.DynamicCommunityMiningAlgorithmName;
import config.dynamiccommunitymining.Method;
import config.dynamiccommunitymining.Metric;

/**
 *
 * @author aabnar
 */
public class LocalInfo extends MiningInfo{
    private Metric metric;
    private Method method;

    private boolean overlap;
    private boolean hub;

    // number of previous snapshots counts as history. only used in case of
    // spectrum
    private int history = 1;

    private double instability = 0.0;

    private String[] startVertices;

    public LocalInfo(double similarityThreshold, Metric metric, Method method,
                    boolean overlap, boolean hub, int history, 
                    double instability, String[] startVertices) {
        this.similarityThreshold = similarityThreshold;
        this.metric = metric;
        this.method = method;
        this.overlap = overlap;
        this.hub = hub;
        this.history = history;
        this.instability = instability;
        this.startVertices = startVertices;
    }   

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public boolean getOverlap() {
        return overlap;
    }

    public void setOverlap(boolean overlap) {
        this.overlap = overlap;
    }

    public boolean getHub() {
        return hub;
    }

    public void setHub(boolean hub) {
        this.hub = hub;
    }

    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
    }

    public double getInstability() {
        return instability;
    }

    public void setInstability(double instability) {
        this.instability = instability;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String[] getStartVertices() {
        return startVertices;
    }

    public void setStartVertices(String[] startVertices) {
        this.startVertices = startVertices;
    }

    public String getName() {
        String name = DynamicCommunityMiningAlgorithmName.LOCAL + "_" + method;

        if (method.equals(Method.Spectrum)) {
            name += history;
        }
        name += "_" + metric;
        if (overlap) {
            name += "_Overlap";
        }
        if (hub) {
            name += "_Hub";
        }

        return name;
    }
}
