/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import algorithm.graph.communitymining.AttributeValueMiner;
import config.CommunityMiningParameters;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;

/**
 *
 * @author aabnar
 */
public class CommunityMiningParameterCorrectness {
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        String[] Params = new String[3];
        Params[0] = CommunityMiningParameters.ATTRIBUTEVALUEMINER_CHOSENATTRIBUTE +":"+"firstone";
        Params[1] = CommunityMiningParameters.ATTRIBUTEVALUEMINER_MULTIPLEVALUE+":"+true;
        Params[2] = CommunityMiningParameters.ATTRIBUTEVALUEMINER_SEPARATOR+"::";
        AttributeValueMiner<IVertex, IEdge<IVertex>> miner = new AttributeValueMiner<>(null, null, Params);
        
        String[] paramVals = miner.getParameters();
        
        for (String s : paramVals) {
            System.out.println(s);
        }
    }

}
