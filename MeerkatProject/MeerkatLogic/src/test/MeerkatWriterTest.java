/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.writer.MeerkatWriter;
import main.meerkat.MeerkatBIZ;

/**
 *
 * @author aabnar
 */
public class MeerkatWriterTest {

    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
		//MeerkatBIZ meerkatApplication = MeerkatBIZ.getMeerkatApplication();
        //test();
        MeerkatBIZ meerkatApplication = MeerkatBIZ.getMeerkatApplication();

        int projectId = meerkatApplication.createNewProject();
        int graphid = meerkatApplication.getProject(projectId).
                loadFile(".meerkat", "demo.meerkat", 0);
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = 
                meerkatApplication.getProject(projectId).getGraph(graphid);
        
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(0);
        MeerkatWriter<IVertex,IEdge<IVertex>> mWriter = new MeerkatWriter<>();
        mWriter.write(dynaGraph, "testMWriter");

    }
}
