/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.Clustering;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author aabnar
 * @param <T>
 */
public class KMedoidClustering<T /*Type of data points*/> {
    
    Map<T,Map<T,Double>> mapDistances;
    ArrayList<T> arrDataPoints = new ArrayList<>();

    /**
     *
     * @param pmpDataPoints
     */
    public KMedoidClustering (Map<T,Map<T,Double>> pmpDataPoints) {
        this.mapDistances = pmpDataPoints;
        arrDataPoints.addAll(mapDistances.keySet());
    }
    
    /**
     *
     * @param pintNumberOfClusters
     * @return
     */
    public List<Set<T>> cluster (int pintNumberOfClusters, BooleanProperty isThreadRunningProperty) {
        
        List<T> arrSeedPoints = computeInitialSeeds(pintNumberOfClusters);
        
        List<Set<T>> clusters = new LinkedList<>();
        if(isThreadRunningProperty.getValue()==false){
            return clusters;
        }
        for (int i = 0 ; i < pintNumberOfClusters ; i++) {
            clusters.add(new HashSet<>());
        }
        if(isThreadRunningProperty.getValue()==false){
            return clusters;
        }
        double prevDistance = Double.MAX_VALUE;
        double newDistance = 0.0;
        
        for ( int i = 0 ; i < clusters.size() ; i++ ) {
            for (T t : clusters.get(i)) {
                if(isThreadRunningProperty.getValue()==false){
                    return clusters;
                }
                newDistance += mapDistances.get(arrSeedPoints.get(i)).get(t);
            }
        }
        
        while (newDistance < prevDistance) {
            if(isThreadRunningProperty.getValue()==false){
                return clusters;
            }
            for(T t : arrDataPoints){
            //arrDataPoints.stream().forEach((t) -> {
                if(isThreadRunningProperty.getValue()==false){
                    return clusters;
                }
                double[] adbDistanceToSeed = new double[pintNumberOfClusters];
                for (int i = 0 ; i < pintNumberOfClusters; i++) {
                    adbDistanceToSeed[i] = mapDistances.get(t).get(arrSeedPoints.get(i));
                }

                double dblMinDistance = Double.MAX_VALUE;
                int minIndex = 0 ;
                for (int i = 0 ; i < adbDistanceToSeed.length; i++) {
                    if (adbDistanceToSeed[i] < dblMinDistance) {
                        dblMinDistance = adbDistanceToSeed[i];
                        minIndex = i;
                    }
                }
                clusters.get(minIndex).add(t);
            }//);

            for (int i = 0 ; i < clusters.size(); i++) {
                if(isThreadRunningProperty.getValue()==false){
                    return clusters;
                }
                double dblAllDistance = 0.0;
                for (T t : clusters.get(i)) {
                    if(isThreadRunningProperty.getValue()==false){
                        return clusters;
                    }
                    dblAllDistance += mapDistances.get(arrSeedPoints.get(i)).get(t);
                }

                for (T t : clusters.get(i)) {
                    if(isThreadRunningProperty.getValue()==false){
                        return clusters;
                    }
                    if (!arrSeedPoints.contains(t)) {
                        T tempSeed = t;
                        double dblNewDistance = 0.0;
                        for (T t2 : clusters.get(i)) {
                            
                            dblNewDistance += mapDistances.get(tempSeed).get(t2);
                        }
                        if (dblNewDistance < dblAllDistance) {
                            arrSeedPoints.set(i, tempSeed);
                        }
                    }
                }
            }
            
            prevDistance = newDistance;
            
            newDistance = 0.0;
        
            for ( int i = 0 ; i < clusters.size() ; i++ ) {
                if(isThreadRunningProperty.getValue()==false){
                    return clusters;
                }
                for (T t : clusters.get(i)) {
                    if(isThreadRunningProperty.getValue()==false){
                        return clusters;
                    }
                    newDistance += mapDistances.get(arrSeedPoints.get(i)).get(t);
                }
            }
        }
        return clusters;
    }
    
    private List<T> computeInitialSeeds(int pintNumberOfSeeds) {
        ArrayList<T> arrSeedPoints = new ArrayList<>();
        
        for (int i = 0; i < pintNumberOfSeeds ; i++) {
            int random = 
                  (int) (Math.floor(Math.random() * mapDistances.size()) + 1);
            
            arrSeedPoints.add(arrDataPoints.get(random));
        }
        
        return arrSeedPoints;
    }
    
}
