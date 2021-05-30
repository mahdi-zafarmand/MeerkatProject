/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import algorithm.graph.filter.GraphFilter;
import config.FilteringOperators;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.reader.MeerkatReader;
import java.util.List;
import static test.MeerkatGraphReaderTest.CLASS_NAME;

/**
 *
 * @author aabnar
 */
public class FilteringGraphTest {
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        String graphPath = "/cshome/aabnar/workspace/MeerkatLogicMVP/resources/files/test.meerkat";
        
        MeerkatReader reader = new MeerkatReader(graphPath);
        System.out.println(CLASS_NAME+": reads dynamic file...");
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = reader.loadFile();
        
        String[][] arrstrVertexFilters = new String[1][];
        arrstrVertexFilters[0] = new String[3];
        arrstrVertexFilters[0][0] = "d0";
        arrstrVertexFilters[0][1] = FilteringOperators.LEQ;
        arrstrVertexFilters[0][2] = "30";
        List<IVertex> selectedVs = GraphFilter.filterByVertexAttributes(dynaGraph, dynaGraph.getAllTimeFrames().get(0), arrstrVertexFilters);
        
        for (IVertex v : selectedVs) {
            System.out.println(v.getUserAttributer().getAttributeValue("FILE_ID", dynaGraph.getAllTimeFrames().get(0)) +
                    " : d0 = " + v.getUserAttributer().getAttributeValue("d0", dynaGraph.getAllTimeFrames().get(0)));
        }
    }
}
