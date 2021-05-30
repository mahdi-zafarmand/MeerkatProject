/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import algorithm.graph.communitymining.DynamicMiner;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.reader.MeerkatReader;
import java.io.File;
import static test.MeerkatGraphReaderTest.CLASS_NAME;

/**
 *
 * @author aabnar
 */
public class DynaCMTest {

    public static void main (String[] args) {
        String basePath = new File("").getAbsolutePath();
        String fileName = basePath.concat("/resources/files/demo.meerkat");
        MeerkatReader reader = new MeerkatReader(fileName);
        System.out.println(CLASS_NAME+": reads dynamic file...");
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = reader.loadFile();
        
        DynamicMiner<IVertex,IEdge<IVertex>> dynaMiner = new DynamicMiner<>(dynaGraph, null);
        
        Thread th = new Thread(dynaMiner);
        
        th.start();
        
//        while(th.isAlive());
    }
}
