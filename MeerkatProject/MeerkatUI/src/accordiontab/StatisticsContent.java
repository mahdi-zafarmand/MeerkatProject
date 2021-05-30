/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accordiontab;


import config.LangConfig;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *  Class Name      : StatisticsContent
 *  Created Date    : 2015-07-22
 *  Description     : Contents to be displayed on the StatisticsContent Pane
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2015-09-09      Talat           Renamed the class name from Statistics to StatisticsContent
 *  2015-09-01      Talat           Implements ITitledPaneContents
 * 
*/
public class StatisticsContent implements ITitledPaneContents {
    
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    // Meta-Data Content    
    private String strTitle ;
    MetricElements mtrElements ;
    
    // UI Elements     
    private VBox vboxContainer ;
    private List<CheckBox> lstchkMetrics;
    private TableView tblMetrics ;
    private GridPane gridMetrics ;
    
    private double [][] dblMetrics ;
    
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    private String getTitle(){
        return this.strTitle;
    }
    private void setTitle(String pstrTitle){
        this.strTitle = pstrTitle;
    }
    private MetricElements getMetrics() {
        return this.mtrElements;
    }
    
    public GridPane getGridMetrics() {
        return this.gridMetrics ;
    }
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    public StatisticsContent(String pstrTitle) {
        setTitle(pstrTitle);
        vboxContainer = new VBox();
        this.gridMetrics = new GridPane();
        this.mtrElements = MetricElements.getInstance();
    }
       
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : intialize()
     *  Created Date    : 2015-07-22
     *  Description     : Initializes the StatisticsContent for the StatisticsContent Pane in Accordion
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param paccParentAccordionPane: Accordion
     *  @param paccTitledPane: TitledPane
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-04-05      Talat           Fit the width and Height of the Scroll Pane
     *  2015-09-01      Talat           Changed initiateStatistics() to the overridden method initialize()
     * 
    */
    @Override
    public void initialize(Accordion paccParentAccordionPane, TitledPane paccTitledPane) {
        
        tblMetrics = new TableView();
        TableColumn tclmVertexID = new TableColumn(LangConfig.GENERAL_VERTEXID);
        tblMetrics.getColumns().addAll(tclmVertexID);
 
        getGridMetrics().setVgap(4);
        getGridMetrics().setPadding(new Insets(5, 5, 5, 5));
        
        int intIndex = 1;
        for (String strKey : getMetrics().getMetricsIDTextMapping().keySet()) {            
            String strReaderID = getMetrics().getMetricID(strKey);            
            CheckBox chbCurrentItem = new CheckBox(strKey);
            // chbCurrentItem.setDisable(true);
            getGridMetrics().add(chbCurrentItem, (intIndex+1)%2, (intIndex-1)/2);
            intIndex++;
        }
        
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setContent(tblMetrics);
        
        vboxContainer.getChildren().addAll(scroll, gridMetrics) ;
        vboxContainer.setSpacing(20);
        vboxContainer.setPadding(new Insets(10, 10, 10, 10)) ;
        vboxContainer.setDisable(true);
        
        paccTitledPane.setText(this.getTitle());
        paccTitledPane.setContent(vboxContainer);
        
        // #DEBUG
        // this.mtrElements.Debug_Print();
    }
    
    /* METHODS */
    /**
     *  Method Name     : updateTab
     *  Created Date    : 2015-09-01
     *  Description     : Initializes the UI components that are to be displayed on the StatisticsContent Tab in the Analysis Screen
     *  Version         : 1.0
     *  @author         : Talat
     *  
     *  @param paccParentAccordionPane: Accordion
     *  @param paccTitledPane: TitledPane
     *  @param pintProjectID : int - Project ID that contains the graph
     *  @param pintGraphID: int - Graph whose parameters are to be reflected in the StatisticsContent Pane
     *  @param pintTimeFrameIndex : int - The Time Frame Index of the graph
     *  @param pobjContent : Object - Any object that contains the dependent information
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    @Override
    public void updatePane(Accordion paccParentAccordionPane, TitledPane paccTitledPane, int pintProjectID, int pintGraphID, int pintTimeFrameIndex, Object pobjContent) {   
        
        StatisticsValues statValues = (StatisticsValues)pobjContent;
        
        paccTitledPane.setContent(statValues.getStatValuesContainer());
        
    }
}
