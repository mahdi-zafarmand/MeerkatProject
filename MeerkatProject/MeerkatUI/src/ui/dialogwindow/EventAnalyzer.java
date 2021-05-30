/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import analysisscreen.AnalysisController;
import events.EventAnalyzerDialog;

/**
 *  Class Name      : EventAnalyzer
 *  Created Date    : 2016-07-26
 *  Description     : Export Community dialog boxes
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class EventAnalyzer {
    
    /**
     *  Method Name     : DisplayEventDialog()
     *  Created Date    : 2016-07-26
     *  Description     : Displays the Window to select the metrics required to expose
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController : AnalysisController
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     * 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-08-04      Elahe           Passing pintProjectID and pintGraphID
     *                                  Parameters to Display method of events
     * 
    */
    private static EventAnalyzerDialog events = new EventAnalyzerDialog();
    private enum EventName {

        NONE, SURVIVE, DISSOLVE, SPLIT, MERGE, FORM

    }
    private static EventName eventname = EventName.NONE;
    
    private enum IndividualEventName {

        NONE, JOIN, LEFT, APPEAR, DISAPPEAR

    }
    private static IndividualEventName individualEvent = IndividualEventName.NONE;
    
    public static void DisplayEventDialog (AnalysisController pController, int pintProjectID, int pintGraphID, int intTimeFrame) {
        try {
            
            
            events.DisplayEventDialog(pintProjectID, pintGraphID, intTimeFrame);
            
        } catch (Exception ex) {
            System.out.println("EventAnalyzerDialog.DisplayEventDialog(): EXCEPTION");
            System.err.println(ex.toString());
            ex.printStackTrace();
        } 
    }
    /**
     *  Method Name     : DisplayFormedDialog()
     *  Created Date    : 2016-09-26
     *  Description     : Displays the Window to select the metrics required to expose
     *  Version         : 1.0
     *  @author         : Elahe
     * 
     *  @param pController : AnalysisController
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     * 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public static void DisplayFormedDialog (AnalysisController pController, int pintProjectID, int pintGraphID, int intTimeFrame) {
        try {
            eventname=EventName.FORM;
            events.ConstructEvents(pintProjectID, pintGraphID, intTimeFrame, eventname.toString());
            
        } catch (Exception ex) {
            System.out.println("EventAnalyzerDialog.DisplayFormedDialog(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
    
    /**
     *  Method Name     : DisplayDissolvedDialog()
     *  Created Date    : 2016-09-26
     *  Description     : Displays the Window to represent Dissolved Communities
     *  Version         : 1.0
     *  @author         : Elahe
     * 
     *  @param pController : AnalysisController
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     * 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public static void DisplayDissolvedDialog (AnalysisController pController, int pintProjectID, int pintGraphID, int intTimeFrame) {
        try {
            eventname=EventName.DISSOLVE;
            events.ConstructEvents(pintProjectID, pintGraphID, intTimeFrame, eventname.toString());
            
        } catch (Exception ex) {
            System.out.println("EventAnalyzerDialog.DisplayDissolvedDialog(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
    
    public static void DisplaySurvivedDialog (AnalysisController pController, int pintProjectID, int pintGraphID, int intTimeFrame) {
        try {
            eventname=EventName.SURVIVE;
            events.ConstructEvents(pintProjectID, pintGraphID, intTimeFrame, eventname.toString());
            
        } catch (Exception ex) {
            System.out.println("EventAnalyzerDialog.DisplaySurvivedDialog(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
    
    public static void DisplaySplitDialog (AnalysisController pController, int pintProjectID, int pintGraphID, int intTimeFrame) {
        try {    
            eventname=EventName.SPLIT;
            events.ConstructEvents(pintProjectID, pintGraphID, intTimeFrame, eventname.toString());
            
        } catch (Exception ex) {
            System.out.println("EventAnalyzerDialog.DisplaySurvivedDialog(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
    
    public static void DisplayMergeDialog (AnalysisController pController, int pintProjectID, int pintGraphID, int intTimeFrame) {
        try {
            eventname=EventName.MERGE;
            events.ConstructEvents(pintProjectID, pintGraphID, intTimeFrame, eventname.toString());
            
        } catch (Exception ex) {
            System.out.println("EventAnalyzerDialog.DisplaySurvivedDialog(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
    
    public static void DisplayAppearDialog (AnalysisController pController, int pintProjectID, int pintGraphID, int intTimeFrame) {
        try {
            individualEvent=IndividualEventName.APPEAR;
            events.ConstructIndividualEvents(pintProjectID, pintGraphID, intTimeFrame, individualEvent.toString());
            
        } catch (Exception ex) {
            System.out.println("EventAnalyzerDialog.DisplaySurvivedDialog(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
     
    public static void DisplayDisappearDialog (AnalysisController pController, int pintProjectID, int pintGraphID, int intTimeFrame) {
        try {
            individualEvent=IndividualEventName.DISAPPEAR;
            events.ConstructIndividualEvents(pintProjectID, pintGraphID, intTimeFrame, individualEvent.toString());
            
        } catch (Exception ex) {
            System.out.println("EventAnalyzerDialog.DisplaySurvivedDialog(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
      
    public static void DisplayJoinDialog (AnalysisController pController, int pintProjectID, int pintGraphID, int intTimeFrame) {
        try {
            individualEvent=IndividualEventName.JOIN;
            events.ConstructIndividualEvents(pintProjectID, pintGraphID, intTimeFrame, individualEvent.toString());
            
        } catch (Exception ex) {
            System.out.println("EventAnalyzerDialog.DisplaySurvivedDialog(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
       
    public static void DisplayLeftDialog (AnalysisController pController, int pintProjectID, int pintGraphID, int intTimeFrame) {
        try {
            individualEvent=IndividualEventName.LEFT;
            events.ConstructIndividualEvents(pintProjectID, pintGraphID, intTimeFrame, individualEvent.toString());
            
        } catch (Exception ex) {
            System.out.println("EventAnalyzerDialog.DisplaySurvivedDialog(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
    
    public static void init(int pintProjectID, int pintGraphID, int intTimeFrame) 
    {
        try 
        {
            events.Init(pintProjectID, pintGraphID, intTimeFrame);
        } catch (Exception ex) 
        {
            System.out.println("EventAnalyzerDialog.init(): EXCEPTION");
            ex.printStackTrace();
        }
                
    }
    
}
