/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accordiontab;

import layout.LayoutGroup;
import config.LangConfig;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import layout.LayoutElements;
import layout.LayoutSet;

/**
 * Class Name : LayoutContent
 *
 * @author : Talat Created Date : 2015-07-17 Description : Contents to be
 * displayed on the LayoutContent Pane Version : 1.0
 *
 * EDIT HISTORY (most recent at the top) Date Author Description 2015-09-09
 * Talat Renamed the class name from Layout to LayoutContent 2015-09-01 Talat
 * Implements ITitledPaneContents
 * 
*/
public class LayoutContent implements ITitledPaneContents {

    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private String strTitle;
    // private List<LayoutGroup> lstlytGroup;

    private Button btnRun;
    private Button btnStop;
    private GridPane grid;
    private String strSelectedLayoutID;
    private LayoutGroup lgSelectedLayoutGroup ;
    private String[] arrstrParameters;
    private boolean blnIsBusyWaiting = true;
    
    private Task task;

    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public void setTitle(String pstrText) {
        this.strTitle = pstrText;
    }

    public String getTitle() {
        return this.strTitle;
    }

    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    public LayoutContent(String pstrTitle) {
        this.strTitle = pstrTitle;        
    }

    /* *************************************************************** */
    /* ****************     OVER-RIDDEN  METHODS     ***************** */
    /* *************************************************************** */
    /* UI Related Methods */
    /**
     * Method Name : initialize() Created Date : 2015-07-17 Description :
     * Initializes the LayoutContent Tab in the Accordion Pane with the layouts
     * retrieved the input configuration file Version : 1.0
     *
     * @author : Talat
     *
     * @param paccParentAccordionPane: Accordion
     * @param paccTitledPane: TitledPane
     *
     * EDIT HISTORY (most recent at the top) Date Author Description 2015-09-01
     * Talat Changed initiateLayoutsTab() to the overridden method initialize()
     *
     */
    @Override
    public void initialize(Accordion paccParentAccordionPane, TitledPane paccTitledPane) {

        grid = new GridPane();
        grid.setVgap(4);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.setDisable(true);
        int intRowPosition = 0; /* The row number in which a particular element is to be added */

        final ToggleGroup layoutToggle = new ToggleGroup();

        arrstrParameters = new String[10];

        /* 
         For each of the layout group (such as Standard, Metric Layouts), 
         add the group along with its elements  to the Accordion Tab
         */
        LayoutSet lytSet = LayoutSet.getInstance();        
        for (LayoutGroup currentGroup : lytSet.getAllLayouts()) {
            grid.add(new Label(currentGroup.getTitle()), 0, intRowPosition);
            intRowPosition++;
            /* For each of the element in the layout group, display it on the screen and set the onSelect property */
            for (LayoutElements currentElement : currentGroup.getAllLayoutElements()) {
                RadioButton rbLayout = new RadioButton(currentElement.getText());
                rbLayout.setDisable(true);
                rbLayout.setToggleGroup(layoutToggle);
                grid.add(rbLayout, 0, intRowPosition);
                intRowPosition++;
            }
        }

        btnStop = new Button(LangConfig.GENERAL_STOP);
        btnStop.setDisable(true);

        btnRun = new Button(LangConfig.GENERAL_RUN);
        btnRun.setDisable(true);

        /* Adding the Accordion a Scroll Pane to allow scrolling when the number of layouts go beyond the space */
        ScrollPane scroll = new ScrollPane();
        scroll.setPrefHeight(paccParentAccordionPane.getHeight());
        scroll.prefWidth(paccParentAccordionPane.getWidth());
        scroll.setContent(grid);
        
        HBox hboxRunStop = new HBox(btnRun, btnStop);
        hboxRunStop.setSpacing(30);
        hboxRunStop.setAlignment(Pos.CENTER);
        
        VBox vboxContainer = new VBox(scroll, hboxRunStop);
        vboxContainer.setSpacing(10);

        paccTitledPane.setText(getTitle());
        
        if(paccTitledPane.getContent()==null){
            paccTitledPane.setContent(vboxContainer);
        }else{
            paccTitledPane.getContent().setDisable(true);
        }
    }

    /* UI Related Methods */
    /**
     * Method Name : updateTab() Created Date : 2015-07-17 Description :
     * Initializes the LayoutContent Tab in the Accordion Pane with the layouts
     * retrieved the input configuration file Version : 1.0
     *
     * @author : Talat
     *
     * @param paccParentAccordionPane: Accordion
     * @param paccTitledPane: TitledPane
     * @param pintProjectID : int
     * @param pintGraphID : int
     * @param pintTimeFrameIndex : int
     * @param pobjContent : Object - Any object that contains the dependent
     * information
     *
     * EDIT HISTORY (most recent at the top) Date Author Description 2015-09-01
     * 
     *
     */
    @Override
    public void updatePane(Accordion paccParentAccordionPane, TitledPane paccTitledPane, int pintProjectID, int pintGraphID, int pintTimeFrameIndex, Object pobjContent) {

        LayoutValues lytValues = (LayoutValues)pobjContent;
        paccTitledPane.setContent(lytValues.getLayoutContainer());
    }
}
