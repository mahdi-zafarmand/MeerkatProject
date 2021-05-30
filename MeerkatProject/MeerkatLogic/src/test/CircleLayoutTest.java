/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import algorithm.graph.layout.algorithms.CircleLayout;
import algorithm.graph.layout.algorithms.RandomLayout;
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
public class CircleLayoutTest {
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                loadFile("C:\\Users\\Talat-AICML\\Documents\\NetBeansProjects\\MeerkatAPI\\resources\\files\\test.meerkat");
        
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
            CircleLayout crlLayout = new CircleLayout(dynaGraph, t, null);
            
            RandomLayout rndLayout = new RandomLayout(dynaGraph, t, null);
            
            Thread th1 = new Thread(rndLayout);
            th1.start();
            
            while(th1.isAlive());
            
            for (int vid : dynaGraph.getGraph(t).getAllVertexIds()) {
                IVertex v = dynaGraph.getVertex(vid);
                System.out.print(v.getId() + " : " + v.getSystemAttributer().getAttributeValue(MeerkatSystem.X, t) + " , ");
                System.out.println(v.getSystemAttributer().getAttributeValue(MeerkatSystem.Y, t));
                
            }
            System.out.println("-----------------------");
            Thread th = new Thread(crlLayout);
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
