/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accordiontab;

import config.AppConfig;
import globalstate.GraphTab;
import globalstate.ProjectTab;
import io.parser.AccordionTabs;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

/**
 *  Class Name      : AccordionTabContents
 *  Created Date    : 2015-08-xx
 *  Description     : The contents of the Accordion Tabs which are the right most panel on the main window
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class AccordionTabContents {
    
    // VARIABLES
    private LayoutContent lytLayoutTab ;
    private FilterContent fltFilterTab ;
    private CommunitiesContent cmnCommunitiesTab ;
    private StatisticsContent stcStatiscticsTab ;
    private DetailsContent dtDetailsTab;
    
    static AccordionTabContents accordionTab = null;

    
    /* ****************** GETTERS AND SETTERS ******************** */
    public void setLayout(LayoutContent pLayout) {
        this.lytLayoutTab = pLayout;
    }
    public LayoutContent getLayout() {
        return this.lytLayoutTab;
    }
    
    public void setFilters(FilterContent pFilter) {
        this.fltFilterTab = pFilter;
    }
    public FilterContent getFilters () {
        return this.fltFilterTab;
    }
    
    public void setCommunities(CommunitiesContent pCommunities) {
        this.cmnCommunitiesTab = pCommunities;
    }
    public CommunitiesContent getCommunities(){
        return this.cmnCommunitiesTab;
    }
    
    public void setDetails(DetailsContent pDetails){
        this.dtDetailsTab = pDetails;
    }
    public DetailsContent getDetails(){
        return this.dtDetailsTab;
    }
    
    public void setStatistics(StatisticsContent pStatistics){
        this.stcStatiscticsTab = pStatistics;
    }
    public StatisticsContent getStatistics() {
        return this.stcStatiscticsTab;
    }
    public static void setAccordionNull() {
        accordionTab=null;
    }
    
    /**
     *  Constructor Name: AccordionTabContents()
     *  Created Date    : 
     *  Description     : Instantiates the AccordionTab Data Structure. To be used only for populating the content of AccordionTab
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return accordionTab: AccordionTab
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private AccordionTabContents() {
        
    }
    
    
    /**
     *  Method Name     : 
  Created Date    : 
  Description     : Instantiates the AccordionTabContents Data Structure. To be used only for populating the content of AccordionTabContents
  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return accordionTab: AccordionTabContents
 
  EDIT HISTORY (most recent at the top)
  Date            Author          Description
     *  
     * 
    */
    public static AccordionTabContents getAccordionTabInstance () {
        if (accordionTab == null) {
            accordionTab = new AccordionTabContents();
            accordionTab = AccordionTabs.Parse(AppConfig.XML_LANGUAGE_FILE);
        }
        return accordionTab;
    }
    
    public void initiateAccordionTabs(Accordion paccParentTab, TitledPane pDetails, TitledPane pLayout, 
            TitledPane pFilters, TitledPane pStatistics, TitledPane pCmmunities) {
        dtDetailsTab.initialize(paccParentTab, pDetails);
        
        lytLayoutTab.initialize(paccParentTab, pLayout);
        
        fltFilterTab.initialize(paccParentTab, pFilters);
       
        cmnCommunitiesTab.initialize(paccParentTab, pCmmunities);
       
        stcStatiscticsTab.initialize(paccParentTab, pStatistics);
       
        
    }
    
    
    public void addProject(ProjectTab  pProjectTab) {
        
    }
    
    public void changeProject(ProjectTab pDestinationProjectTab) {
        
    }
    
    public void addGraph(GraphTab pGraphTab) {
        
    }
    
    public void changeGraph(GraphTab pDestinationGraphTab) {
        
    }
    
    public void populateAccordionTabs() {
        /*
        AccordionTabContents accTabs = AccordionTabs.Parse(AppConfig.XML_LANGUAGE_FILE);
        initiateDetailsTab(accTabs.getDetails());
        initiateLayoutsTab(accTabs.getAllLayouts());
        initiateFilters(accTabs.getFilters());
        initiateStatistics(accTabs.getStatistics());
        initiateCommunities(accTabs.getCommunities());
                */
    }
    
    public void updateAccordionTabs(
              Accordion paccParentAccordionPane
            , TitledPane paccDetails            
            , TitledPane paccLayouts
            , TitledPane paccFilters
            , TitledPane paccStatistics
            , TitledPane paccCommunities
            , int pintProjectID
            , int pintGraphID
            , int pintTimeFrameIndex
            , AccordionTabValues pAccordionValues) {
        
        // System.out.println("AccordionTabContents.updateAccordionTabs(): Edge Count: "+pAccordionValues.getDetailsValues().getEdgeCount());
    
        // System.out.println("AccordionTab.updateAccordionTabs(): Started");
        this.dtDetailsTab.updatePane(paccParentAccordionPane, paccDetails, 
                pintProjectID, pintGraphID, pintTimeFrameIndex, 
                pAccordionValues.getDetailsValues());
        this.fltFilterTab.updatePane(paccParentAccordionPane, paccFilters, 
                pintProjectID, pintGraphID, pintTimeFrameIndex, 
                pAccordionValues.getFiltersValues());
        this.lytLayoutTab.updatePane(paccParentAccordionPane, paccLayouts, 
                pintProjectID, pintGraphID, pintTimeFrameIndex, 
                pAccordionValues.getLayoutValues());        
        this.stcStatiscticsTab.updatePane(paccParentAccordionPane, 
                paccStatistics, pintProjectID, pintGraphID, pintTimeFrameIndex, 
                pAccordionValues.getStatisticsValues());
        this.cmnCommunitiesTab.updatePane(paccParentAccordionPane, 
                paccCommunities, pintProjectID, pintGraphID, pintTimeFrameIndex, 
                pAccordionValues.getCommunitiesValues());
    }
    
    public void updateAccordionCommunitiesPane(  
              Accordion paccParentAccordionPane
            , TitledPane paccDetails            
            , TitledPane paccLayouts
            , TitledPane paccFilters
            , TitledPane paccStatistics
            , TitledPane paccCommunities
            , int pintProjectID
            , int pintGraphID
            , int pintTimeFrameIndex
            , AccordionTabValues pAccordionValues) {
        
         if (pAccordionValues.getCommunitiesValues() != null) {
            this.cmnCommunitiesTab.updatePane(paccParentAccordionPane, 
                    paccCommunities, 
                    pintProjectID, 
                    pintGraphID, 
                    pintTimeFrameIndex, 
                    pAccordionValues.getCommunitiesValues());
        }
    }
        
}
