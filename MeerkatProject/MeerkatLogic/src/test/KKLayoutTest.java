/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import algorithm.graph.layout.algorithms.KKLayout;
import algorithm.graph.layout.algorithms.RandomLayout;
import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.reader.GMLReader;
import static test.MeerkatGraphReaderTest.CLASS_NAME;

/**
 *
 * @author aabnar
 */
public class KKLayoutTest {
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                loadFile("/cshome/aabnar/workspace/set4/network.gml");
        
        layoutGraph(dynaGraph);
    }
    
    /**
     *
     * @param pstrFilePath
     * @return
     */
    public static IDynamicGraph<IVertex,IEdge<IVertex>> loadFile(String pstrFilePath) {
        GMLReader reader = new GMLReader(pstrFilePath);
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
            RandomLayout rndLayout = new RandomLayout(dynaGraph, t, null);
            
            
            
            Thread th1 = new Thread(rndLayout);
            th1.start();
            
            while(th1.isAlive());
            
//            for (int vid : dynaGraph.getGraph(t).getAllVertexIds()) {
//                IVertex v = dynaGraph.getVertex(vid);
//                System.out.print(v.getId() + " : " + v.getSystemAttributer().getAttributeValue(MeerkatSystem.X, t) + " , ");
//                System.out.println(v.getSystemAttributer().getAttributeValue(MeerkatSystem.Y, t));
//                
//            }
//            System.out.println("-----------------------");
            
//            KKLayout_Afra kkLayout = new KKLayout_Afra(dynaGraph, t, null);
//            
//            Thread th = new Thread(kkLayout);
//            th.start();
//            
//            while(th.isAlive());
            
//            for (int vid : dynaGraph.getGraph(t).getAllVertexIds()) {
//                IVertex v = dynaGraph.getVertex(vid);
//                System.out.print(v.getId() + " : " + v.getSystemAttributer().getAttributeValue(MeerkatSystem.X, t) + " , ");
//                System.out.println(v.getSystemAttributer().getAttributeValue(MeerkatSystem.Y, t));
//                
//            }
            
            KKLayout kkLayout = new KKLayout(dynaGraph, t, null);
            Thread th = new Thread(kkLayout);
            th.start();
            
            while(th.isAlive());
                    
            for (int vid : dynaGraph.getGraph(t).getAllVertexIds()) {
                IVertex v = dynaGraph.getVertex(vid);
                System.out.print(v.getId() + " : " + v.getSystemAttributer().getAttributeValue(MeerkatSystem.X, t) + " , ");
                System.out.println(v.getSystemAttributer().getAttributeValue(MeerkatSystem.Y, t));
                
            }
        }
    }
}
