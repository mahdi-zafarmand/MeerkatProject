/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import algorithm.graph.layout.algorithms.Bullseye;
import algorithm.graph.metric.Betweenness;
import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.reader.MeerkatReader;
import static test.MeerkatGraphReaderTest.CLASS_NAME;

/**
 *
 * @author aabnar
 */
public class BullseyeLayoutTest {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                loadFile("/cshome/aabnar/workspace/MeerkatLogic/"
                                + "resources/files/demo.meerkat");
        
        layoutGraph(dynaGraph);
    }
    
    /**
     *
     * @param pstrFilePath
     * @return
     */
    public static IDynamicGraph<IVertex,IEdge<IVertex>> loadFile(String pstrFilePath) {
        MeerkatReader reader = new MeerkatReader(pstrFilePath);
        System.out.println(CLASS_NAME+": reads dynamic file...");
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = reader.loadFile();
        
        return dynaGraph;
    }
    
    /**
     *
     * @param dynaGraph
     */
    public static void layoutGraph(IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph) {

        for (TimeFrame t : dynaGraph.getAllTimeFrames()) {
            String[] parameters = new String[1];
            parameters[0] = Betweenness.class.toString();
            Bullseye mfrlLayout = new Bullseye(dynaGraph, t, parameters);
            
            Thread th = new Thread(mfrlLayout);
            th.start();
            
            while(th.isAlive());
            System.out.println("");
            System.out.println("TimeFrame "+t);
            for (int vid : dynaGraph.getGraph(t).getAllVertexIds()) {
                IVertex v = dynaGraph.getVertex(vid);
                System.out.print(v.getSystemAttributer().getAttributeValue(MeerkatSystem.X, t) + " , ");
                System.out.println(v.getSystemAttributer().getAttributeValue(MeerkatSystem.Y, t));
                
            }
        }
    }
}
