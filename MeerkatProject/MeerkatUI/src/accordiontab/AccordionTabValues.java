/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accordiontab;

import globalstate.GraphCanvas;
import java.util.Map;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;

/**
 *  Class Name      : AccordionTabValues
 *  Created Date    : 2015-09-14
 *  Description     : Contains the values that are to be displayed for a graph in the Accordion Tab
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-01-04      Talat           Added the code required by the CommunitiesValues
 *  2015-09-18      Talat           Added the code required by the FilterValues
 *  2015-09-25      Talat           Added the code required by the StatisticsValues
 * 
*/
public class AccordionTabValues {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    private DetailsValues dtValues;
    private FilterValues fltValues ;
    private StatisticsValues stValues ;
    private CommunitiesValues cmnValues ;
    private LayoutValues lytValues ;
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
    public DetailsValues getDetailsValues() {
        return this.dtValues;
    }
    public FilterValues getFiltersValues() {
        return this.fltValues;
    }
    public LayoutValues getLayoutValues() {
        return this.lytValues ;
    }
    public StatisticsValues getStatisticsValues() {
        return this.stValues;
    }
    public CommunitiesValues getCommunitiesValues() {
        return this.cmnValues ;
    }
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    
    /**
     *  Constructor Name: 
     *  Created Date    : 2015-09-14
     *  Description     : Constructor for the AccordionTabValues
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     *  @param pintTimeFrameIndex : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-02-25      Talat           Added a parameter to send the TimeFrameIndex
     *  2015-09-18      Talat           Added the code required by the FilterValues
     *  2015-09-25      Talat           Added the code required by the StatisticsValues
    */
    public AccordionTabValues(int pintProjectID, int pintGraphID, int pintTimeFrameIndex) {
        
        Long time1 = System.currentTimeMillis();
        // System.out.println("=========================MeerkatUI - AccordianTabValues Started Making AccordianTabValuesObject");
        
        dtValues = new DetailsValues();
        fltValues = new FilterValues(pintProjectID, pintGraphID, pintTimeFrameIndex);
        lytValues = new LayoutValues(pintProjectID, pintGraphID, pintTimeFrameIndex);
        
        
        
        
        stValues = new StatisticsValues(pintProjectID, pintGraphID, pintTimeFrameIndex);
        
        
        cmnValues = new CommunitiesValues(pintProjectID, pintGraphID, pintTimeFrameIndex);
        
        Long time2 = System.currentTimeMillis();
        // System.out.println("=========================MeerkatUI - AccordianTabValues Finished making AccordianTabValuesObject --  time taken in millis = " + (time2-time1));
        
        
    }
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : updateAccordionTabValues()
     *  Created Date    : 2015-09-14
     *  Description     : Updates the Accordion Tabs when there is a change in graph
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     *  @param pintTimeFrameIndex : int
     *  @param pgraphCanvas : GraphCanvas
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-02-25      Talat           Added the parameter to send the Time Frame Index of the graph
     *  2015-09-18      Talat           Added the code required by the FilterValues
     *  2015-09-25      Talat           Added the code required by the StatisticsValues
     * 
    */
    //public void updateAccordionTabValuesOnLoading(int pintProjectID, int pintGraphID, int pintTimeFrameIndex, GraphCanvas pgraphCanvas, Map<String, Color> pMapGlobalCommunityColor, SimpleIntegerProperty pIntNextColorIndexProperty) {
    public void updateAccordionTabValuesOnLoading(int pintProjectID, int pintGraphID, int pintTimeFrameIndex, GraphCanvas pgraphCanvas) {
    
        Long time1 = System.currentTimeMillis();
        // System.out.println("=========================MeerkatUI - AccordianTabValues updateAccordionTabValuesOnLoading -- starting now");
        
            
        
        dtValues.updateDetailsValues(pintProjectID, pintGraphID, pintTimeFrameIndex);
        fltValues.updateFilterValues(pintProjectID, pintGraphID, pintTimeFrameIndex);
        lytValues.updateLayoutValues(pintProjectID, pintGraphID, pintTimeFrameIndex);
        stValues.updateStatisticsValues(pintProjectID, pintGraphID, pintTimeFrameIndex);
        
        //cmnValues.updateCommunitiesValuesOnLoading(pintProjectID, pintGraphID, pintTimeFrameIndex, pgraphCanvas, pMapGlobalCommunityColor, pIntNextColorIndexProperty);
        cmnValues.updateCommunitiesValuesOnLoading(pintProjectID, pintGraphID, pintTimeFrameIndex, pgraphCanvas);
        
        Long time2 = System.currentTimeMillis();
        // System.out.println("=========================MeerkatUI - AccordianTabValues  updateAccordionTabValuesOnLoading --FINISHED NOW --  time taken in millis = " + (time2-time1));

    }
    
    public void updateAccordionTabValues(int pintProjectID, int pintGraphID, int pintTimeFrameIndex) {
        dtValues.updateDetailsValues(pintProjectID, pintGraphID, pintTimeFrameIndex);
        //TODO -> Currently filter and Layout values are not being updated.
//        fltValues.updateFilterValues(pintProjectID, pintGraphID, pintTimeFrameIndex);
//        lytValues.updateLayoutValues(pintProjectID, pintGraphID, pintTimeFrameIndex);
//        stValues.updateStatisticsValues(pintProjectID, pintGraphID, pintTimeFrameIndex);
        stValues = new StatisticsValues(pintProjectID, pintGraphID, pintTimeFrameIndex);
        cmnValues.updateCommunitiesValues(pintProjectID, pintGraphID, pintTimeFrameIndex);
    }
    
}
