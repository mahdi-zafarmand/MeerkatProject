/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accordiontab;

import ca.aicml.meerkat.api.GraphAPI;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 *  Class Name      : FiltersValues
 *  Created Date    : 2015-09-28
 *  Description     : Graph specific values to be displayed on the Filters Pane
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-08-23      Abhi            Added methods for getting VertexFilterValues(), EdgeFilterValues() 
 *  2016-08-23      Abhi            Added methods for a remove filter button
 *  
 * 
*/
public class FilterValues {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    // Content related UI Variables
    private Map<String, Boolean> hmapVertexAttributes ;
    private Map<String, Boolean> hmapEdgeAttributes ;
    
    // UI Related Variables
    private Map<Integer, AttributeFilterBox>  mapVertexFilterBox ;
    private VBox vboxVertexFilterContainer ;
    private Button btnAddVertexFilter;
        
    private Map<Integer, AttributeFilterBox>  mapEdgeFilterBox ;     
    private VBox vboxEdgeFilterContainer ;
    private Button btnAddEdgeFilter;
    
    // Content Filter format     
    // 0th index will store the AttributeName
    // 1st Index will store the Operator
    // 2nd Index will store the value to which it is to be compared
    // 3rd Index will store the ID of the AttributeFilterBox which can be used to detect the UI component (helps in removing)
    private String [][] arrarrstrFilters = new String [20][4];
    private int intNumberOfFilters = 0;
    private int intFilterID = 0;
    
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public VBox getVertexBox() {
        return this.vboxVertexFilterContainer;
    }
    public VBox getEdgeBox() {        
        return this.vboxEdgeFilterContainer;
    }
    
    // Edge Attributes
    public Map<String, Boolean> getAllEdgeAttributes () {
        return this.hmapEdgeAttributes;
    }
    private void setEdgeAttributes(int pintProjectID, int pintGraphID, int pintTimeFrameIndex) {
        this.hmapEdgeAttributes = GraphAPI.getEdgeAttributeNamesWithType(pintProjectID, pintGraphID, pintTimeFrameIndex);
    }
    
    // Vertex Attributes
    public Map<String, Boolean> getAllVertexAttributes () {
        return this.hmapVertexAttributes;
    }
    private void setVertexAttributes(int pintProjectID, 
            int pintGraphID, 
            int pintTimeFrameIndex) {
        this.hmapVertexAttributes = GraphAPI.getVertexAttributeNamesWithType(
                pintProjectID, pintGraphID, pintTimeFrameIndex);
        
        //removing SYS:X and SYS:Y attributes from the filter.
        if(hmapVertexAttributes.containsKey(GraphAPI.getMeerkatSystemXAttribute()))
            hmapVertexAttributes.remove(GraphAPI.getMeerkatSystemXAttribute());
        if(hmapVertexAttributes.containsKey(GraphAPI.getMeerkatSystemYAttribute()))
            hmapVertexAttributes.remove(GraphAPI.getMeerkatSystemYAttribute());
    }
    
    private Map<Integer, AttributeFilterBox> getVertexFilters () {
        return this.mapVertexFilterBox ;
    }
    
    private Map<Integer, AttributeFilterBox> getEdgeFilters () {
        return this.mapEdgeFilterBox ;
    }
    public int getNoOfVertexFilters(){
        return mapVertexFilterBox.size();
    }
    
    public String [][] getFilters() {
        
        List<String[]> lstFilters = new ArrayList<>();
        
        // Add all the Vertex Filters
        for (AttributeFilterBox currentFilter : getVertexFilters().values()) {
            lstFilters.addAll(currentFilter.getValue());
        }
        
        // Add all the Edge Filters
        for (AttributeFilterBox currentFilter : getEdgeFilters().values()) {
            lstFilters.addAll(currentFilter.getValue());
        }
        
        this.arrarrstrFilters = new String[lstFilters.size()][3];
        lstFilters.toArray(arrarrstrFilters);
        
        return this.arrarrstrFilters;
    }
    public String [][] getVertexFiltersValues() {
        
        List<String[]> lstFilters = new ArrayList<>();
        
        // Add all the Vertex Filters
        for (AttributeFilterBox currentFilter : getVertexFilters().values()) {
            lstFilters.addAll(currentFilter.getValue());
        }
        
        this.arrarrstrFilters = new String[lstFilters.size()][3];
        lstFilters.toArray(arrarrstrFilters);
        
        return this.arrarrstrFilters;
    }
    public String [][] getEdgeFiltersValues() {
        
        List<String[]> lstFilters = new ArrayList<>();
        
        // Add all the Edge Filters
        for (AttributeFilterBox currentFilter : getEdgeFilters().values()) {
            lstFilters.addAll(currentFilter.getValue());
        }
        
        this.arrarrstrFilters = new String[lstFilters.size()][3];
        lstFilters.toArray(arrarrstrFilters);
        
        return this.arrarrstrFilters;
    }
   
    
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    
    /**
     *  Constructor Name: FilterValues()
     *  Created Date    : 2015-09-28
     *  Description     : Constructor to initialize the components
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     *  @param pintTimeFrameIndex : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-02-25      Talat           Added the parameter for sending Time Frame Index
     * 
    */
    public FilterValues (int pintProjectID, int pintGraphID, int pintTimeFrameIndex) {
        
        // Initialize the retrieve all the attributes required
        this.hmapVertexAttributes = new HashMap<>();  
        setVertexAttributes(pintProjectID, pintGraphID, pintTimeFrameIndex);
        
        this.hmapEdgeAttributes = new HashMap<>();
        setEdgeAttributes(pintProjectID, pintGraphID, pintTimeFrameIndex);
        
        this.initializeUIComponents();
        
        int intIndex = 0;
        for (AttributeFilterBox currentFilter : getVertexFilters().values()) {
            // getVertexGrid().add(currentFilter.getAttributeFilterBox(), 0, intIndex++);
            getVertexBox().getChildren().add(currentFilter.getAttributeFilterBox());
        }
        
        intIndex = 0;
        for (AttributeFilterBox currentFilter : getEdgeFilters().values()) {
            // getEdgeGrid().add(currentFilter.getAttributeFilterBox(), 0, intIndex++);
            getEdgeBox().getChildren().add(currentFilter.getAttributeFilterBox());
        }
    }
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : initializeUIComponents
     *  Created Date    : 2015-09-28
     *  Description     : Initializes the UI components
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void initializeUIComponents() {        
        mapVertexFilterBox = new HashMap<>();    
        vboxVertexFilterContainer = new VBox();
        mapEdgeFilterBox = new HashMap<>();    
        vboxEdgeFilterContainer = new VBox();
        initializeAddFilterButtons();
    }
    
    /**
     *  Method Name     : initializeAddFilterButtons
     *  Created Date    : 2016-09-12
     *  Description     : initializeAddFilterButtons, AddVertexFilter and AddEdge Filter, On clicking these buttons, it adds a new filter box and repaints
     *                    the filter pane by sending control to FIlterContent class
     *  Version         : 1.0
     *  @author         : Abhi
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void initializeAddFilterButtons(){
    
              
        this.btnAddVertexFilter = new Button("+");
        this.vboxVertexFilterContainer.setSpacing(10);
        this.vboxVertexFilterContainer.setPadding(new Insets(10,5,5,5));
        vboxVertexFilterContainer.getChildren().add(this.btnAddVertexFilter);
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        btnAddVertexFilter.setOnAction((ActionEvent e) -> {
            
            UIInstance.getController().updateStatusBar(true, StatusMsgsConfig.FILTERS_ADDING);
            
            if (UIInstance.getActiveProjectTab() != null && UIInstance.getActiveProjectTab().getActiveGraphTab() != null) {
                addVertexAttributeFilterBox("-", btnAddVertexFilter);
                // AccordianTabInstance is a singelton. getFilters gives the instance of FilterContent
                AccordionTabContents.getAccordionTabInstance().getFilters().updateVertexFilterContainer2();
                AccordionTabContents.getAccordionTabInstance().getFilters().updateVertexTabPaneNoOfFilters(getNoOfVertexFilters());
            }
            
            UIInstance.getController().updateStatusBar(true, StatusMsgsConfig.FILTERS_ADDED);
        });
        
        this.btnAddEdgeFilter = new Button("+");
        this.vboxEdgeFilterContainer.setSpacing(10);
        this.vboxEdgeFilterContainer.setPadding(new Insets(10,5,5,5));
        vboxEdgeFilterContainer.getChildren().add(this.btnAddEdgeFilter);
        
        btnAddEdgeFilter.setOnAction((ActionEvent e) -> {
            UIInstance.getController().updateStatusBar(true, StatusMsgsConfig.FILTERS_ADDING);
            if (UIInstance.getActiveProjectTab() != null && UIInstance.getActiveProjectTab().getActiveGraphTab() != null) {
                addEdgeAttributeFilterBox("-", btnAddEdgeFilter);
                // AccordianTabInstance is a singelton. getFilters gives the instance of FilterContent
                AccordionTabContents.getAccordionTabInstance().getFilters().updateEdgeFilterContainer2();
            }
            
            UIInstance.getController().updateStatusBar(true, StatusMsgsConfig.FILTERS_ADDED);
        });
        
    }
    
   
    
    /**
     *  Method Name     : addNewFilterBox
     *  Created Date    : 2015-09-28
     *  Description     : Creates a new created AttributeFilterBox
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param phmapAttributeNames : Map<String, Boolean> 
     *  @return AttributeFilterBox
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private AttributeFilterBox addNewFilterBox(Map<String, Boolean> phmapAttributeNames) {
        return (new AttributeFilterBox(phmapAttributeNames));
    }
    /**
     *  Method Name     : addNewFilterBox
     *  Created Date    : 2016-08-23
     *  Description     : Creates a new AttributeFilterBox with remove button
     *  Version         : 1.0
     *  @author         : Abhi
     * 
     *  @param phmapAttributeNames : Map<String, Boolean>
     *  @param intFilterID : Integer
     *  @param mapFilterBox : Map<Integer, AttributeFilterBox>
     *  @param strRemoveFilterButtonText : String
     *  @return AttributeFilterBox
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private AttributeFilterBox addNewFilterBox(Map<String, Boolean> phmapAttributeNames, int intFilterID, Map<Integer, AttributeFilterBox>  mapFilterBox, String strRemoveFilterButtonText, Button pbtnAddVertexFilter) {
        return (new AttributeFilterBox(phmapAttributeNames, intFilterID, mapFilterBox, strRemoveFilterButtonText, this, pbtnAddVertexFilter));
    }
    
    /**
     *  Method Name     : addVertexAttributeFilterBox
     *  Created Date    : 2015-09-28
     *  Description     : Adds an attributeFIlterBox for a vertex
     *  Version         : 1.0
     *  @author         : Talat
     *  @param pstrRemoveVertexButtonText : String 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-08-23      Abhi            Added a remove filter button
     *  2016-09-12      Abhi            Added facility for clearing up all boxes in the container, add all boxes again, new one at the end and add the + button at last
     * 
    */
    public void addVertexAttributeFilterBox(String pstrRemoveVertexButtonText, Button pbtnAddFilter) {
        //mapVertexFilterBox.put(intFilterID, addNewFilterBox(getAllVertexAttributes()));
        //System.out.println("accordiantab.FilterValues.addVertexAttributeFilterBox(): intFilterID : "+ intFilterID);
        //mapVertexFilterBox.put(intFilterID, addNewFilterBox(getAllVertexAttributes(),intFilterID, mapVertexFilterBox, pstrRemoveVertexButtonText, pbtnAddFilter));
        
        //disable the + button after adding a filter box. wait for values to be filled and then make it active.
        //FilterContent.disableAddVertexFilterButton();
        
        //vboxVertexFilterContainer.getChildren().add(mapVertexFilterBox.get(mapVertexFilterBox.size()-1).getAttributeFilterBox()); // Getting the last element        
        
        
        //vboxVertexFilterContainer.getChildren().add(mapVertexFilterBox.get(intFilterID).getAttributeFilterBox()); // Getting the last element        
        
        mapVertexFilterBox.put(intFilterID, addNewFilterBox(getAllVertexAttributes(),intFilterID, mapVertexFilterBox, pstrRemoveVertexButtonText, pbtnAddFilter));

        vboxVertexFilterContainer.getChildren().clear();
        for(AttributeFilterBox filterBox: mapVertexFilterBox.values()){
            vboxVertexFilterContainer.getChildren().add(filterBox.getAttributeFilterBox());
        }
        
        vboxVertexFilterContainer.getChildren().add(this.btnAddVertexFilter);
        intFilterID++ ;
    }
    
    /**
     *  Method Name     : addEdgeAttributeFilterBox
     *  Created Date    : 2015-09-28
     *  Description     : Adds an attributeFIlterBox for an edge
     *  Version         : 1.0
     *  @author         : Talat
     *  @param pstrRemoveEdgeButtonText : String
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-08-23      Abhi            Added a remove filter button
     * 
    */
    public void addEdgeAttributeFilterBox(String pstrRemoveEdgeButtonText, Button pbtnAddFilter) {
        //mapEdgeFilterBox.put(intFilterID, addNewFilterBox(getAllEdgeAttributes()));
        //mapEdgeFilterBox.put(intFilterID, addNewFilterBox(getAllEdgeAttributes(), intFilterID, mapEdgeFilterBox, pstrRemoveEdgeButtonText, pbtnAddFilter));  
        
        //disable the + button after adding a filter box. wait for values to be filled and then make it active.
        //FilterContent.disableAddEdgeFilterButton();
        
        //vboxEdgeFilterContainer.getChildren().add(mapEdgeFilterBox.get(mapEdgeFilterBox.size()-1).getAttributeFilterBox()); // Getting the last element
        //vboxEdgeFilterContainer.getChildren().add(mapEdgeFilterBox.get(intFilterID).getAttributeFilterBox()); 
        //intFilterID++ ;
        mapEdgeFilterBox.put(intFilterID, addNewFilterBox(getAllEdgeAttributes(), intFilterID, mapEdgeFilterBox, pstrRemoveEdgeButtonText, pbtnAddFilter));
        
        vboxEdgeFilterContainer.getChildren().clear();
        
        for(AttributeFilterBox filterBox: mapEdgeFilterBox.values()){
            vboxEdgeFilterContainer.getChildren().add(filterBox.getAttributeFilterBox());
        }
        
        vboxEdgeFilterContainer.getChildren().add(this.btnAddEdgeFilter);
        
        intFilterID++ ;
    }  
    
    /**
     *  Method Name     : removeAttributeFilterBoxMapEntry
     *  Created Date    : 2016-08-23
     *  Description     : Removes the entry of the filter in corresponding AttributeFilterBoxMap, AddFilterButton is active only if there is no filterbox
     *                    or the last filter box present has values filled up.
     *  Version         : 1.0
     *  @author         : Abhi
     *  @param mapFilterBox : Map<Integer, AttributeFilterBox>
     *  @param intFilterID : Integer
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */    
    public void removeAttributeFilterBoxMapEntry(Map<Integer, AttributeFilterBox>  mapFilterBox, int intFilterID, Button pbtnAddNewAttributeFilterBoxButton){
        
        mapFilterBox.remove(intFilterID);
        if(mapFilterBox.size()==0){
            pbtnAddNewAttributeFilterBoxButton.setDisable(false);
            
        }else{
            //get largest key of the map, which must be the latest filterbox added
            //get that filter box and find if it has filled values so that + button can be enabled
            int maxID = 0;
            for(int id : mapFilterBox.keySet()){
                if(id > maxID){
                    maxID = id;
                }
            }
            AttributeFilterBox lastFilterBoxPresent = mapFilterBox.get(maxID);
            //System.out.println("AccordianTabs.FilterValues.removeAttributeFilterBox : box maxID : active "+ maxID + " : " + lastFilterBoxPresent.isActive() );
            if(lastFilterBoxPresent.isFilledWithValues()){
                //lastFilterBoxPresent.getBtnAddNewAttributeFilterBoxButton().setDisable(false);
                pbtnAddNewAttributeFilterBoxButton.setDisable(false);
                //FilterContent.enableAddVertexFilterButton();
            }
            
        }
        // update no of filters in tab pane text
        AccordionTabContents.getAccordionTabInstance().getFilters().updateVertexTabPaneNoOfFilters(getNoOfVertexFilters());
    }
    
    
    /**
     *  Method Name     : updateFilterValues()
     *  Created Date    : 2016-02-25
     *  Description     : Updates the Filter Values
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     *  @param pintTimeFrameIndex : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updateFilterValues(int pintProjectID, int pintGraphID, int pintTimeFrameIndex) {
        // DO something here
    }
    
    
    /**
     *  Method Name     : addFilter()
     *  Created Date    : 2016-05-16
     *  Description     : Add the filter to the array that would be sent for processing
     *  Version         : 1.0
     *  @author         : Talat
     *
     *  @param pstrAttributeName : String
     *  @param pstrOperator : String
     *  @param pstrValue : String
     *  @param pintAttributeFilterBoxID : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void addFilter (String pstrAttributeName, String pstrOperator, String pstrValue, int pintAttributeFilterBoxID) {
        try {
            this.arrarrstrFilters[this.intNumberOfFilters][0] = pstrAttributeName ;
            this.arrarrstrFilters[this.intNumberOfFilters][1] = pstrOperator ;
            this.arrarrstrFilters[this.intNumberOfFilters][2] = pstrValue ;
            this.arrarrstrFilters[this.intNumberOfFilters][3] = String.valueOf(pintAttributeFilterBoxID) ;
            
            this.intNumberOfFilters ++ ;
        } catch (Exception ex) {
            System.out.println(".(): EXCEPTION") ;
            ex.printStackTrace();
        }
    }
    
    
    /**
     *  Method Name     : removeFilter()
     *  Created Date    : 2016-05-16
     *  Description     : Removes the filter based on the ID that was provided
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintAttributeFilterBoxID : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */

    public void removeFilter (int pintAttributeFilterBoxID) {
        try {
            
            int intProcessingRow = 0 ;
            for (int intRow = 0; intRow < this.intNumberOfFilters; intRow ++) {
                if (arrarrstrFilters[intProcessingRow][3].equals(String.valueOf(pintAttributeFilterBoxID))) {
                    
                    
                } else {
                    // Remove this Row by copying the next set of rows to the current row
                    arrarrstrFilters[intProcessingRow][0] = arrarrstrFilters[intRow+1][0];
                    arrarrstrFilters[intProcessingRow][1] = arrarrstrFilters[intRow+1][1];
                    arrarrstrFilters[intProcessingRow][2] = arrarrstrFilters[intRow+1][2];
                    arrarrstrFilters[intProcessingRow][3] = arrarrstrFilters[intRow+1][3];
                    
                    intProcessingRow ++ ;
                }
            }
            
            intNumberOfFilters = intProcessingRow;
            
        } catch (Exception ex) {
            System.out.println(".(): EXCEPTION") ;
            ex.printStackTrace();
        }
    }
    
}
