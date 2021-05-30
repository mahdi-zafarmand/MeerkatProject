/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import datastructure.core.graph.classinterface.IDynamicGraph;
import io.DataWriter;
import main.meerkat.MeerkatBIZ;
import main.project.Project;

/**
 *
 * @author aabnar
 */
public class GraphWriterTest {
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        MeerkatBIZ meerkat = MeerkatBIZ.getMeerkatApplication();
        int pjId = meerkat.createNewProject();
        Project pj = meerkat.getProject(pjId);
        int draphid = pj.loadFile(".meerkat", "/cshome/aabnar/workspace/MeerkatLogic/resources/files/demo.meerkat", 0);
        IDynamicGraph dynaG = pj.getGraph(draphid);
        
        DataWriter.writeGraph(dynaG, null, ".meerkat", "/cshome/aabnar/workspace/MeerkatLogicMVP/testWriter");
    }
}
