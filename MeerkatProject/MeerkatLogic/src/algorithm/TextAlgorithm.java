/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import datastructure.core.text.HNode;
import java.util.HashMap;

/**
 *
 * @author aabnar
 */
public abstract class TextAlgorithm extends Algorithm {
    
    /**
     *
     */
    protected static HashMap<Long, TextAlgorithm> hmpalgorithm = 
                                                            new HashMap<>();
    
    HNode hndRootNode;
    
    /**
     *
     * @param phnode
     */
    protected TextAlgorithm(HNode phnode) {
        super();
        hndRootNode = phnode;
    }
    
    /**
     *
     * @param lngAlgorithmId
     * @return
     */
    public static boolean containsAlglortihm(Long lngAlgorithmId) {
        return hmpalgorithm.containsKey(lngAlgorithmId);
    }
    
    /**
     *
     * @param lngAlgorithmId
     * @return
     */
    public static TextAlgorithm getAlgorithm(Long lngAlgorithmId) {
        return hmpalgorithm.get(lngAlgorithmId);
    }
    
    private static void addAlgorithm(TextAlgorithm pTextAlg) {
        hmpalgorithm.put(pTextAlg.getId(),pTextAlg);
    }
    
    /**
     *
     * @param lngAlgorithmId
     */
    public static void removeAlgorithm(Long lngAlgorithmId) {
        hmpalgorithm.remove(lngAlgorithmId);
    }

    /**
     *
     * @param pTextAlg
     * @return
     */
    public static Long runAlgorithm(TextAlgorithm pTextAlg) {
        addAlgorithm(pTextAlg);
        pTextAlg.run();
        return pTextAlg.getId();
    }
    
    /**
     *
     * @param lngAlgId
     * @return
     */
    public static boolean terminateAlgorithm(Long lngAlgId) {
        //TODO: should we remove the algorithm from the map as well when terminated?
        TextAlgorithm tagRequest = hmpalgorithm.get(lngAlgId);
        tagRequest.interrupt();
        hmpalgorithm.remove(lngAlgId);
       
        return tagRequest.isTerminated();
    }
    
    /**
     *
     */
    public abstract void writeToFile();
}
