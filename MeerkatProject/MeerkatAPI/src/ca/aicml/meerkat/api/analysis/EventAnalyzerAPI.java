/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.aicml.meerkat.api.analysis;

import algorithm.graph.evolutionanalysis.EvolutionAnalyzer;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import main.meerkat.MeerkatBIZ;
import main.project.Project;

/**
 *
 * @author aabnar
 */
public class EventAnalyzerAPI {

    /**
     *  Method Name     : runEvolutionAnalyzer()
     *  Created Date    : 20xx-xx-xx
     *  Description     : 
     *  Version         : 1.0
     *  @author         : Afra
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-08-04      Elahe           Making the method static
     * 
    */
    
    public static Object[] runEvolutionAnalyzer(int pintProjectId, 
            int pintGraphId) {
        MeerkatBIZ meerkat = MeerkatBIZ.getMeerkatApplication();
        
        Project prj = meerkat.getProject(pintProjectId);
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph= prj.getGraph(pintGraphId);
        
        EvolutionAnalyzer<IVertex,IEdge<IVertex>> evAnalyzer =
                new EvolutionAnalyzer<>(dynaGraph);
        
        evAnalyzer.run();
        
        List<String> lstAllEvents = evAnalyzer.getAllEvents();
        
        ArrayList<Map<String, Map<String, ArrayList<String>>>> individualEvents = evAnalyzer.getAllIndividualEvents();
        
        Object[] eventArray = new Object[2];
        eventArray[0] = lstAllEvents;
        eventArray[1] = individualEvents;
        
        return eventArray;
    }
    
//    /**
//     *  Method Name     : runEvolutionAnalyzerIndividualEvents()
//     *  Created Date    : 2017-04-20
//     *  Description     : returns all the individual events.
//     *  Version         : 1.0
//     *  @author         : sankalp
//     * 
//     *  EDIT HISTORY (most recent at the top)
//     *  Date            Author          Description
//     * 
//    */
//    public static ArrayList<Map<String, Map<String, ArrayList<String>>>>
//         runEvolutionAnalyzerIndividualEvents(int pintProjectId, int pintGraphId){
//        MeerkatBIZ meerkat = MeerkatBIZ.getMeerkatApplication();
//        
//        Project prj = meerkat.getProject(pintProjectId);
//        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph= prj.getGraph(pintGraphId);
//        
//        EvolutionAnalyzer<IVertex,IEdge<IVertex>> evAnalyzer =
//                new EvolutionAnalyzer<>(dynaGraph);
//        
//        evAnalyzer.run();
//        
//        ArrayList<Map<String, Map<String, ArrayList<String>>>> individualEvents = evAnalyzer.getAllIndividualEvents();
//        
//        return individualEvents;
//    }
}
