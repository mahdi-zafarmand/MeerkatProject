/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.DataWriter;
import main.meerkat.MeerkatBIZ;
import main.project.Project;

/**
 *
 * @author aabnar
 */
public class WritersTest {
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        MeerkatBIZ meerkat = MeerkatBIZ.getMeerkatApplication();
        int prjId = meerkat.createNewProject();
        Project prj = meerkat.getProject(prjId);
        
        String[] readerType = {".meerkat", ".net", ".pairs",".csv",".graphml" , ".gml"};
        String[] writerType = {".meerkat", ".net",".pairs",".csv",".json", ".graphml", ".gml", ".wpairs"};
        
        for (String reader : readerType) {
            String strFilePath = "/cshome/aabnar/workspace/readers/" + "test" + reader;
            int graphid = prj.loadFile(reader, strFilePath, 0);

//                GMLReader pjkreader = new GMLReader(strFilePath);
            IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph = prj.getGraph(graphid);
            for(String writer : writerType) {
                System.out.println(writer + "--------->");
                DataWriter.writeGraph(dynaGraph, null, writer, "/cshome/aabnar/workspace/writers/source_"+ reader.substring(1) +"/"+ "writerResult");
            }
        }
    }
}