/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accordiontab;

import ca.aicml.meerkat.api.GraphAPI;
import ca.aicml.meerkat.api.analysis.CommunityMiningAPI;
import config.LangConfig;
import config.SceneConfig;
import globalstate.GraphCanvas;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import graphelements.CommunitiesTreeCellImpl;
import graphelements.UIEdge;
import graphelements.UIVertex;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 *  Class Name      : CommunitiesValues
 *  Created Date    : 2016-04-01
 *  Description     : The Values for the Communities for each graph
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class CommunitiesValues {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    //public static Map<String, Color> mapGlobalCommunityColor = new HashMap<>();

    
    
    /* Map between the community and the Community Members (Vertices) for this time frame
    This map is filled according to the values in mapGlobalCommunityColor which is common for entire
    graph. Check that in GraphTab.java
    */
    Map<String, List<Integer>> mapCommunities = null;
    
    // Map between the community and the Community Colors 
    Map<String, Color> mapCommunitiesColor = null ;
    
    // Index to store the index of the color that has been selected
    //private static int intNextColorIndex = 0;
    
    // UI Elements
    private CommunitiesTreeItem<HBox> treeitemCommunities ;
    private TableColumn tblclmVertexID ;
    private TableColumn tblclmCommunityID ;
    // private VBox vboxContainer ;
    
    private TreeView tvwCommunities ;
    private TableView tblVertexCommunity ;
    private TextField filterText;
    private Label filterLabel;
    private HBox filter;
    
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
    public TreeView getTreeView() {
        return tvwCommunities ;
    }
    public TableView getTableView() {
        return tblVertexCommunity ;
    }
    public HBox getFilter() {
        return filter ;
    }
   
    public Map<String, List<Integer>> getCommunityVertexMapping() {
        return mapCommunities ;
    }
    
    public Map<String, Color> getCommunityColors() {
        return mapCommunitiesColor ;
    }
    
    public Color getCommunityColor(String pstrCommunityID) {
        return mapCommunitiesColor.get(pstrCommunityID);
    }
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    
    /**
     *  Constructor Name: CommunitiesValues()
     *  Created Date    : 2016-04-01
     *  Description     : 
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
    public CommunitiesValues (int pintProjectID, int pintGraphID, 
            int pintTimeFrameIndex) {
    
        // Inittialize the Community Color Map
            mapCommunities = new HashMap<>();
            mapCommunitiesColor = new HashMap<>() ;
        /*
        try {
            
            // Inittialize the Community Color Map
            mapCommunities = new HashMap<>();
            mapCommunitiesColor = new HashMap<>() ;
            
            // TREE VIEW        
            Group grpRectangle = new Group();
            Rectangle rTitle = new Rectangle(SceneConfig.COMMUNITYRECT_WIDTH, 
                    SceneConfig.COMMUNITYRECT_HEIGHT);
            // rTitle.setFill(Color.valueOf(SceneConfig.COLORS_COMMUNITYTITLE));
            rTitle.setFill(Color.valueOf(SceneConfig.VERTEX_TITLECOLOR_FILL));
            grpRectangle.getChildren().add(rTitle);

            Text txtParent = new Text(LangConfig.GENERAL_COMMUNITYPLURAL);
            HBox hboxParent = new HBox(grpRectangle, txtParent) ;
            hboxParent.setSpacing(5);
            hboxParent.setPadding(new Insets(2,2,2,2));
            hboxParent.setAlignment(Pos.TOP_LEFT);
            treeitemCommunities = new CommunitiesTreeItem<>(hboxParent); // Null Values since it is the root node
            
            // #IMPORTANT : Uncomment the following line
            mapCommunities = CommunityMiningAPI.getCommunities(pintProjectID, 
                    pintGraphID, 
                    pintTimeFrameIndex);
            
            // #DEBUG : Remove it when the above line has been uncommented            
            
//            List<Integer> lst1 = Arrays.asList(1,2,3,4);
//            List<Integer> lst2 = Arrays.asList(6,7,8,9,10);
//            mapCommunities.put("C1", lst1);
//            mapCommunities.put("C2", lst2);
            
            // #ENDDEBUG
            
            List<CommunityTableViewRow> lstData = new ArrayList<>();
                        
            
            // System.out.println("CommunitiesValue.CommunitiesValue(): Number of Entries in the map is "+mapCommunities.size());
            for (String strCurrentCommunity : mapCommunities.keySet()) {
                
                List<Integer> lstintCommunityVertices = 
                        mapCommunities.get(strCurrentCommunity);                
                Set<Integer> setintCommunityVertices = 
                        new HashSet<>(lstintCommunityVertices);
                
                int intCommunityVertexCount = 0 ;
                int intSubCommunities = 0;
                if (lstintCommunityVertices != null) {
                    intCommunityVertexCount = lstintCommunityVertices.size();
                }
                
                Color clrCommunityColor;
                
                if (mapGlobalCommunityColor.containsKey(strCurrentCommunity)) {
                    clrCommunityColor = 
                            mapGlobalCommunityColor.get(strCurrentCommunity);
                } else {
                    clrCommunityColor = Color.valueOf(
                        SceneConfig.COLORS_COMMUNITIES.get(intNextColorIndex));
                    mapGlobalCommunityColor.put(strCurrentCommunity, 
                            clrCommunityColor);
                }                
                Rectangle r = new Rectangle();
                r.setWidth(SceneConfig.COMMUNITYRECT_WIDTH);
                r.setHeight(SceneConfig.COMMUNITYRECT_HEIGHT);
                r.setFill(clrCommunityColor);
                System.out.println("CommunitiesValues.Constructor() : "
                        + "rectangle color: " + clrCommunityColor);
                
                Group grpCurrentRectangle = new Group();
                grpCurrentRectangle.getChildren().add(r);

                Text txtChild = new Text(strCurrentCommunity 
                        + "(" + intCommunityVertexCount + " " + 
                        LangConfig.GENERAL_VERTEXPLURAL + ")"
                        + "[" + intSubCommunities + " " + 
                        LangConfig.GENERAL_COMMUNITYSUB + "]");
                HBox hboxCurrentCommunity = 
                        new HBox(grpCurrentRectangle, txtChild);
                hboxCurrentCommunity.setSpacing(5);
                hboxCurrentCommunity.setPadding(new Insets(2,2,2,2));
                
                CommunitiesTreeItem<HBox> treeitemCurrent = 
                        new CommunitiesTreeItem(hboxCurrentCommunity, 
                                strCurrentCommunity, setintCommunityVertices);
                treeitemCommunities.getChildren().add(treeitemCurrent);
                
                for (int intVertexID : lstintCommunityVertices) {
                    lstData.add(new CommunityTableViewRow(
                            String.valueOf(intVertexID), 
                            mapGlobalCommunityColor.get(strCurrentCommunity), 
                            strCurrentCommunity));
                }
                
                intNextColorIndex++ ;
                if (intNextColorIndex >= SceneConfig.COLORS_COMMUNITIES.size()) {
                    intNextColorIndex = 0;
                }
            }
            
            tvwCommunities = new TreeView(treeitemCommunities);
            
            ScrollPane scrollCommunitiesTree = new ScrollPane();        
            scrollCommunitiesTree.setContent(tvwCommunities);   
            scrollCommunitiesTree.setFitToHeight(true);
            scrollCommunitiesTree.setFitToWidth(true);

            // TABLE VIEW        
            tblVertexCommunity = new TableView();
            tblclmVertexID = new TableColumn(LangConfig.GENERAL_VERTEXID) ;
            tblVertexCommunity.getColumns().addAll(tblclmVertexID);
            
            tblclmCommunityID = new TableColumn(LangConfig.GENERAL_COMMUNITYID) ;            
            tblVertexCommunity.getColumns().addAll(tblclmCommunityID);            

            tblclmVertexID.minWidthProperty().bind(tblVertexCommunity.widthProperty().divide(tblVertexCommunity.getColumns().size()));
            tblclmVertexID.minWidthProperty().bind(tblVertexCommunity.widthProperty().divide(tblVertexCommunity.getColumns().size()));
            
            ObservableList<CommunityTableViewRow> obvData = FXCollections.observableArrayList(lstData);
            tblclmVertexID.setCellValueFactory(new PropertyValueFactory<>(LangConfig.GENERAL_VERTEXSINGULAR));
            tblclmCommunityID.setCellValueFactory(new PropertyValueFactory<>(LangConfig.GENERAL_COMMUNITYSINGULAR));
            tblVertexCommunity.setItems(obvData);
            
            ScrollPane scrollTable = new ScrollPane();
            scrollTable.setContent(tblVertexCommunity);
            scrollTable.setFitToHeight(true);
            scrollTable.setFitToWidth(true);

            
            // Debugging
            System.err.println("CommunityValues.Constructor() : "
                    + "community numbers = " + mapCommunities.keySet().size());
            for (String com : mapCommunities.keySet()) {
                System.err.println("CommunitiesValues.Constructor() : Com " +
                        com  + " has size : " + mapCommunities.get(com).size());
            }
            for (String com : mapCommunitiesColor.keySet()) {
                System.err.println("CommunitiesValues.Constructor() : Com " +
                        com + " has color : " + mapCommunitiesColor.get(com));
            }
        } catch (Exception ex) {
            System.out.println("CommunitiesValue.CommunitiesValue(): EXCEPTION");
            ex.printStackTrace();
        }
    */    
    }
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : updateCommunitiesValuesOnLoading()
     *  Created Date    : 2016-04-01
     *  Description     : Updates the Communities Values whenever there is a change in the Communities
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
    public void updateCommunitiesValuesOnLoading(int pintProjectID, 
            int pintGraphID, 
            int pintTimeFrameIndex, GraphCanvas pGraphCanvas){//, Map<String, Color> pMapGlobalCommunityColor, SimpleIntegerProperty pIntNextColorIndexProperty ) {
        
        try {
            
            // Clear the previous results
            mapCommunities.clear();
            mapCommunitiesColor.clear();
            
                        
            // Tre View
            Group grpRectangle = new Group();
            Rectangle rTitle = new Rectangle(SceneConfig.COMMUNITYRECT_WIDTH, 
                    SceneConfig.COMMUNITYRECT_HEIGHT);
            // rTitle.setFill(Color.valueOf(SceneConfig.COLORS_COMMUNITYTITLE));
            rTitle.setFill(Color.valueOf(SceneConfig.VERTEX_TITLECOLOR_FILL));
            grpRectangle.getChildren().add(rTitle);

            Text txtParent = new Text(LangConfig.GENERAL_COMMUNITYPLURAL);
            HBox hboxParent = new HBox(grpRectangle, txtParent) ;
            hboxParent.setSpacing(5);
            hboxParent.setPadding(new Insets(2,2,2,2));
            hboxParent.setAlignment(Pos.TOP_LEFT);
            treeitemCommunities = new CommunitiesTreeItem<>(hboxParent);
            
            
            
            // #IMPORTANT : Uncomment the following line
            fillMapCommunitiesVertices(pintProjectID,
                     pintGraphID, pintTimeFrameIndex);
            fillMapCommunitiesColors(pintProjectID, pintGraphID, pintTimeFrameIndex);
            // System.out.println("MeerkatUI: CommunitiesValues : updateOnLoading() mapCommunities =" + mapCommunities + ", " + mapCommunities.size());
            // #DEBUG : Remove it when the above line has been uncommented
            
            // #ENDDEBUG
            
            List<CommunityTableViewRow> lstData = new ArrayList<>();
                        
            
            // System.out.println("CommunitiesValue.CommunitiesValue(): Number of Entries in CommunityMap is "+mapCommunities.size());
            for (String strCurrentCommunity : mapCommunitiesColor.keySet()) {
                
                List<Integer> lstintCommunityVertices = mapCommunities.get(strCurrentCommunity);               
                Set<Integer> setintCommunityVertices = new HashSet<>(lstintCommunityVertices);
                                
                int intCommunityVertexCount = 0 ;
                int intSubCommunities = 0;
                if (lstintCommunityVertices != null) {
                    intCommunityVertexCount = lstintCommunityVertices.size();
                }
                         
                Color clrCommunityColor = mapCommunitiesColor.get(strCurrentCommunity);
                
//                if (pMapGlobalCommunityColor.containsKey(strCurrentCommunity)) {
//
//                    clrCommunityColor = 
//                            pMapGlobalCommunityColor.get(strCurrentCommunity);
//                } else {
//
//                    clrCommunityColor = Color.valueOf(
//                        SceneConfig.COLORS_COMMUNITIES.get(pIntNextColorIndexProperty.get()));
//                    pMapGlobalCommunityColor.put(strCurrentCommunity, 
//                            clrCommunityColor);
//                    
//                    pIntNextColorIndexProperty.set(pIntNextColorIndexProperty.get()+1);
//                    
//                    if (pIntNextColorIndexProperty.get() >= SceneConfig.COLORS_COMMUNITIES.size()) {
//                        pIntNextColorIndexProperty.set(0);
//                    }
//                }   
//                mapCommunitiesColor.put(strCurrentCommunity, clrCommunityColor);
                                
                Rectangle r = new Rectangle();
                r.setWidth(20);
                r.setHeight(10);
                r.setFill(clrCommunityColor);
                Group grpCurrentRectangle = new Group(r);
                /*grpCurrentRectangle.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println("--- XXXXXXXXXXXXXX ---- mouse click to change color");
                        
                    }
                });
                */
                
                Text txtChild = new Text(strCurrentCommunity 
                        + "(" + intCommunityVertexCount + " " + LangConfig.GENERAL_VERTEXPLURAL + ")"
                        + "[" + intSubCommunities + " " + LangConfig.GENERAL_COMMUNITYSUB + "]");
                HBox hboxCurrentCommunity = new HBox(grpCurrentRectangle, txtChild);
                
                
                
                hboxCurrentCommunity.setSpacing(5);
                hboxCurrentCommunity.setPadding(new Insets(2,2,2,2));
                
                CommunitiesTreeItem<HBox> treeitemCurrent = new CommunitiesTreeItem(hboxCurrentCommunity, strCurrentCommunity, setintCommunityVertices) ;
                treeitemCommunities.getChildren().add(treeitemCurrent);
                treeitemCommunities.setExpanded(true);
                
                for (int intVertexID : lstintCommunityVertices) {
                    lstData.add(new CommunityTableViewRow(
                            String.valueOf(intVertexID), 
                            clrCommunityColor,//TODO remove it after testing pMapGlobalCommunityColor.get(strCurrentCommunity), 
                            strCurrentCommunity));
                }
                
                
            }
            
            tvwCommunities = new TreeView(treeitemCommunities);
            // -- set selection listeners for community TreeView Cells --//
            tvwCommunities.getSelectionModel().selectedItemProperty()
                    .addListener((ObservableValue observable, Object oldValue, 
                            Object newValue) -> {
                                                
                // Check if the elements it is the root or the child
                if (newValue != null) {
                    CommunitiesTreeItem treeItemSelected = (CommunitiesTreeItem)newValue ;
                    //update the CoomunityContextmenu singelton to point to this selected TreeItem
                    treeItemSelected.setSelectedTreeItemInContextmenu(mapCommunitiesColor.get(treeItemSelected.getCommunityID()));
                    
                    Set<Integer> setSelectedVertices = treeItemSelected.getVertexIDs();
                    // System.out.println("CommunitiesValues.updateOnLoading(): Selected Community: "+treeItemSelected.getCommunityID()+"\tIDs: "+setSelectedVertices);
                    
                    if (setSelectedVertices != null) {
                    
                        // Clear the communities
                        pGraphCanvas.clearSelectedVertexList();
                        
                        // Select all the list in the community
                        pGraphCanvas.selectVertex(setSelectedVertices);
                        
                        // Check if the edges are available if both the vertices are selected
                        for (UIEdge currentEdge : pGraphCanvas.getEdges().values()){

                            UIVertex vtxSource = pGraphCanvas.getVertices().get(currentEdge.getSourceVertexID()) ;
                            UIVertex vtxDestination = pGraphCanvas.getVertices().get(currentEdge.getDestinationVertexID()) ;

                            if (pGraphCanvas.getSelectedVertices()
                                    .contains(vtxSource) && 
                                    pGraphCanvas.getSelectedVertices()
                                            .contains(vtxDestination)) {
                                currentEdge.selectEdge();
                            } else {
                                currentEdge.deselectEdge();
                            }
                        }
                        
                        //update the selected community in minimap
                        MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab().getMinimapDelegator().updateMinimap();
                        MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab().getMinimapDelegator().changeMinimapMasking();
                        
                    } else {
                        // The list would be null if the selected item is a root. So select all the vertices
                         pGraphCanvas.selectAllVertices();
                         pGraphCanvas.selectAllEdges();
                   }
                }
            });
            
            tvwCommunities.setEditable(false);
            tvwCommunities.setCellFactory(new Callback<TreeView<HBox>, TreeCell<HBox>>(){
                @Override
                public TreeCell<HBox> call(TreeView<HBox> p) {
                    return new CommunitiesTreeCellImpl();
                }
            });
            // ----------------------------------------------------------//
            
            
            
            
            
            ScrollPane scrollCommunitiesTree = new ScrollPane();        
            scrollCommunitiesTree.setContent(tvwCommunities);   
            scrollCommunitiesTree.setFitToHeight(true);
            scrollCommunitiesTree.setFitToWidth(true);

            // TABLE VIEW        
            tblVertexCommunity = new TableView();
            tblclmVertexID = new TableColumn(LangConfig.GENERAL_VERTEXID) ;
            tblVertexCommunity.getColumns().addAll(tblclmVertexID);
            
            tblclmCommunityID = new TableColumn(LangConfig.GENERAL_COMMUNITYID) ;            
            tblVertexCommunity.getColumns().addAll(tblclmCommunityID);            

            tblclmVertexID.minWidthProperty().bind(tblVertexCommunity.widthProperty().divide(tblVertexCommunity.getColumns().size()));
            tblclmVertexID.minWidthProperty().bind(tblVertexCommunity.widthProperty().divide(tblVertexCommunity.getColumns().size()));
            
            ObservableList<CommunityTableViewRow> obvData = FXCollections.observableArrayList(lstData);
            tblclmVertexID.setCellValueFactory(new PropertyValueFactory<>(LangConfig.GENERAL_VERTEXSINGULAR));
            tblclmVertexID.setComparator(Comparator.comparing(vertexID -> {
                HBox hb = (HBox) vertexID;
                String cellValue=null;
                for(Node node : hb.getChildren()){
                    if(node instanceof Text)
                        cellValue = ((Text) node).getText();
                }
                return Integer.parseInt(cellValue);
            }));
            
            tblclmCommunityID.setCellValueFactory(new PropertyValueFactory<>(LangConfig.GENERAL_COMMUNITYSINGULAR));
            tblVertexCommunity.setItems(obvData);
            
            //adding fucntionality to filter rows in the table based on the filter text.
            filterLabel = new Label(LangConfig.GENERAL_FILTER);
            filterText = new TextField();
            filter = new HBox();
            filter.getChildren().addAll(filterLabel,filterText);
            filter.setSpacing(10);
            filter.setPadding(new Insets(2,2,2,2));
            filter.setAlignment(Pos.CENTER_LEFT);
            filterText.setPromptText("filter table rows");
            
            filterText.textProperty().addListener(new InvalidationListener(){
                @Override
                public void invalidated(Observable observable) {
                    if(filterText.textProperty().get().isEmpty()){
                        tblVertexCommunity.setItems(obvData);
                    }
                    
                    ObservableList<CommunityTableViewRow> tableItems = FXCollections.observableArrayList();
                    ObservableList<TableColumn<CommunityTableViewRow, ?>> cols = tblVertexCommunity.getColumns();
                    HBox hb = new HBox();
                    
                    for(int i=0; i<obvData.size(); i++) {        
                        for(int j=0; j<cols.size(); j++) {                           
                            TableColumn col = cols.get(j);
                            String cellValue = col.getCellData(obvData.get(i)).toString();
                            if(j==0){
                                hb = (HBox) col.getCellData(obvData.get(i));
                                for(Node node : hb.getChildren()){
                                    if(node instanceof Text)
                                        cellValue = ((Text) node).getText();
                                }
                            }
                            cellValue = cellValue.toLowerCase();
                            if(cellValue.contains(filterText.textProperty().get().toLowerCase())) {
                                tableItems.add(obvData.get(i));
                                break;
                            }
                        }
                    }
                    tblVertexCommunity.setItems(tableItems);
                }
                
            });
            
            tblVertexCommunity.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                    System.out.println("CommunitiesValues(): "+newValue);

                    //Check whether item is selected and set value of selected item to Label

                    if(tblVertexCommunity.getSelectionModel().getSelectedItem() != null) 
                    {    
                       CommunityTableViewRow selectedRow = (CommunityTableViewRow)tblVertexCommunity.getSelectionModel().getSelectedItem();
                       int intSelectedID = selectedRow.getVertexID();
                       
                        // Select the Item in Graph
                       MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
                       UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().selectVertex(intSelectedID);
                    }
                }
            });
            
            ScrollPane scrollTable = new ScrollPane();
            scrollTable.setContent(tblVertexCommunity);
            scrollTable.setFitToHeight(true);
            scrollTable.setFitToWidth(true);
            
            
        } catch (Exception ex) {
            System.out.println("CommunitiesValue.CommunitiesValue(): EXCEPTION");
            ex.printStackTrace();
        }

    }
    
    
    /**
     *  Method Name     : updateCommunitiesValues()
     *  Created Date    : 2016-04-01
     *  Description     : Updates the Communities Values whenever there is a change in the Communities
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
    public void updateCommunitiesValues(int pintProjectID, 
            int pintGraphID, 
            int pintTimeFrameIndex) {
        
        try {
            
            // Clear the previous results
            mapCommunities.clear();
            mapCommunitiesColor.clear();
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            GraphCanvas canvas = UIInstance.getProject(pintProjectID)
                    .getGraphTab(pintGraphID).getGraphCanvas();
            GraphTab activeGraphTab = UIInstance.getActiveProjectTab().getActiveGraphTab();
            //Map<String, Color> mapGlobalCommunityColor = activeGraphTab.getMapGlobalCommunityColor();

            
            // Tree View
            Group grpRectangle = new Group();
            Rectangle rTitle = new Rectangle(SceneConfig.COMMUNITYRECT_WIDTH, 
                    SceneConfig.COMMUNITYRECT_HEIGHT);
            // rTitle.setFill(Color.valueOf(SceneConfig.COLORS_COMMUNITYTITLE));
            rTitle.setFill(Color.valueOf(SceneConfig.VERTEX_TITLECOLOR_FILL));
            grpRectangle.getChildren().add(rTitle);

            Text txtParent = new Text(LangConfig.GENERAL_COMMUNITYPLURAL);
            HBox hboxParent = new HBox(grpRectangle, txtParent) ;
            hboxParent.setSpacing(5);
            hboxParent.setPadding(new Insets(2,2,2,2));
            hboxParent.setAlignment(Pos.TOP_LEFT);
            treeitemCommunities = new CommunitiesTreeItem<>(hboxParent);
            
            
            fillMapCommunitiesVertices(pintProjectID,
                     pintGraphID, pintTimeFrameIndex);
            fillMapCommunitiesColors(pintProjectID, pintGraphID, pintTimeFrameIndex);
            
            List<CommunityTableViewRow> lstData = new ArrayList<>();
                        
            
            // System.out.println("CommunitiesValue.CommunitiesValue(): Number of Entries in the map is "+mapCommunities.size());
            for (String strCurrentCommunity : mapCommunities.keySet()) {
                
                List<Integer> lstintCommunityVertices = 
                        mapCommunities.get(strCurrentCommunity);                               
                Set<Integer> setintCommunityVertices = 
                        new HashSet<>(lstintCommunityVertices);
                
                // #DEBUG
                // System.out.println("CommunitiesValue.CommunitiesValue(): 
                //Number of Entries in the list for "+strCurrentCommunity+" is "
                //+lstintVertices.size());
                // #ENDDEBUG
                
                int intCommunityVertexCount = 0 ;
                int intSubCommunities = 0;
                if (lstintCommunityVertices != null) {
                    intCommunityVertexCount = lstintCommunityVertices.size();
                }
                           
                Color clrCommunityColor = mapCommunitiesColor.get(strCurrentCommunity);
                
//                if (mapGlobalCommunityColor.containsKey(strCurrentCommunity)) {
//
//                    clrCommunityColor = 
//                            mapGlobalCommunityColor.get(strCurrentCommunity);
//                } else {
//
//                    clrCommunityColor = Color.valueOf(
//                        SceneConfig.COLORS_COMMUNITIES.get(activeGraphTab.getIntNextColorIndex()));
//                    mapGlobalCommunityColor.put(strCurrentCommunity, 
//                            clrCommunityColor);
//                    activeGraphTab.IncrementIntNextColorIndex();
//                    
//                    if (activeGraphTab.getIntNextColorIndex() >= SceneConfig.COLORS_COMMUNITIES.size()) {
//                        activeGraphTab.setIntNextColorIndex(0);
//                    }
//                }   
//                
//                mapCommunitiesColor.put(strCurrentCommunity, clrCommunityColor);
                
                Rectangle r = new Rectangle();
                r.setWidth(SceneConfig.COMMUNITYRECT_WIDTH);
                r.setHeight(SceneConfig.COMMUNITYRECT_HEIGHT);
                r.setFill(clrCommunityColor);
                Group grpCurrentRectangle = new Group();
                grpCurrentRectangle.getChildren().add(r);
                
                Text txtChild = new Text(strCurrentCommunity 
                        + "(" + intCommunityVertexCount + " " + 
                        LangConfig.GENERAL_VERTEXPLURAL + ")"
                        + "[" + intSubCommunities + " " + 
                        LangConfig.GENERAL_COMMUNITYSUB + "]");
                HBox hboxCurrentCommunity = 
                        new HBox(5,grpCurrentRectangle, txtChild);
//                hboxCurrentCommunity.setSpacing(5);
                hboxCurrentCommunity.setPadding(new Insets(2,2,2,2));
                
                CommunitiesTreeItem<HBox> treeitemCurrent = 
                        new CommunitiesTreeItem(hboxCurrentCommunity, 
                                strCurrentCommunity, 
                                setintCommunityVertices);
                
                treeitemCommunities.getChildren().add(treeitemCurrent);
                treeitemCommunities.setExpanded(true);
                
                for (int intVertexID : lstintCommunityVertices) {
                    lstData.add(new CommunityTableViewRow(
                            String.valueOf(intVertexID), 
                            clrCommunityColor,//TODO remove it after testing -> mapGlobalCommunityColor.get(strCurrentCommunity), 
                            strCurrentCommunity));
                }
                
                
            }
            
            tvwCommunities = new TreeView(treeitemCommunities);  
            
            tvwCommunities.getSelectionModel().selectedItemProperty()
                    .addListener((ObservableValue observable, Object oldValue, 
                            Object newValue) -> {
                
                System.out.println("CommunitiesValues.update(): "
                        + "Selected TreeItem " + newValue);
                
                // Check if the elements it is the root or the child
                if (newValue != null) {
                    CommunitiesTreeItem treeItemSelected = (CommunitiesTreeItem)newValue ;
                    
                    //update the CoomunityContextmenu singelton to point to this selected TreeItem
                    treeItemSelected.setSelectedTreeItemInContextmenu(mapCommunitiesColor.get(treeItemSelected.getCommunityID()));
                    
                    Set<Integer> setSelectedVertices = treeItemSelected.getVertexIDs();
                    System.out.println("CommunitiesValues.update(): Selected Community: "+treeItemSelected.getCommunityID()+"\tIDs: "+setSelectedVertices);
                    
                    if (setSelectedVertices != null) {
                    
                        // Clear the communities
                        canvas.clearSelectedVertexList();
                        
                        // Select all the list in the community
                        canvas.selectVertex(setSelectedVertices);
                        
                        // Check if the edges are available if both the vertices are selected
                        for (UIEdge currentEdge : canvas.getEdges().values()){

                            UIVertex vtxSource = canvas.getVertices().get(currentEdge.getSourceVertexID()) ;
                            UIVertex vtxDestination = canvas.getVertices().get(currentEdge.getDestinationVertexID()) ;

                            if (canvas.getSelectedVertices()
                                    .contains(vtxSource) && 
                                    canvas.getSelectedVertices()
                                            .contains(vtxDestination)) {
                                currentEdge.selectEdge();
                            } else {
                                currentEdge.deselectEdge();
                            }
                        }   
                    } else {
                        // The list would be null if the selected item is a root. So select all the vertices
                         canvas.selectAllVertices();
                         canvas.selectAllEdges();
                   }
                }
            });
            
            tvwCommunities.setEditable(false);
            tvwCommunities.setCellFactory(new Callback<TreeView<HBox>, 
                    TreeCell<HBox>>(){
                @Override
                public TreeCell<HBox> call(TreeView<HBox> p) {
                    return new CommunitiesTreeCellImpl();
                }
            });
            
           
            ScrollPane scrollCommunitiesTree = new ScrollPane();        
            scrollCommunitiesTree.setContent(tvwCommunities);   
            scrollCommunitiesTree.setFitToHeight(true);
            scrollCommunitiesTree.setFitToWidth(true);

            // TABLE VIEW        
            tblVertexCommunity = new TableView();
            tblclmVertexID = new TableColumn(LangConfig.GENERAL_VERTEXID) ;
            tblVertexCommunity.getColumns().addAll(tblclmVertexID);
            
            tblclmCommunityID = new TableColumn(LangConfig.GENERAL_COMMUNITYID) ;            
            tblVertexCommunity.getColumns().addAll(tblclmCommunityID);            

            tblclmVertexID.minWidthProperty().bind(tblVertexCommunity.widthProperty().divide(tblVertexCommunity.getColumns().size()));
            //TODO - check should it be tblclmCommunityID in next line
            tblclmVertexID.minWidthProperty().bind(tblVertexCommunity.widthProperty().divide(tblVertexCommunity.getColumns().size()));
            
            ObservableList<CommunityTableViewRow> obvData = FXCollections.observableArrayList(lstData);
            tblclmVertexID.setCellValueFactory(new PropertyValueFactory<>(LangConfig.GENERAL_VERTEXSINGULAR));
                        tblclmVertexID.setComparator(Comparator.comparing(vertexID -> {
                HBox hb = (HBox) vertexID;
                String cellValue=null;
                for(Node node : hb.getChildren()){
                    if(node instanceof Text)
                        cellValue = ((Text) node).getText();
                }
                return Integer.parseInt(cellValue);
            }));
                        
            tblclmCommunityID.setCellValueFactory(new PropertyValueFactory<>(LangConfig.GENERAL_COMMUNITYSINGULAR));
            tblVertexCommunity.setItems(obvData);
            
            filterText.textProperty().addListener(new InvalidationListener(){
                @Override
                public void invalidated(Observable observable) {
                    if(filterText.textProperty().get().isEmpty()){
                        tblVertexCommunity.setItems(obvData);
                    }
                    
                    ObservableList<CommunityTableViewRow> tableItems = FXCollections.observableArrayList();
                    ObservableList<TableColumn<CommunityTableViewRow, ?>> cols = tblVertexCommunity.getColumns();
                    HBox hb = new HBox();
                    
                    for(int i=0; i<obvData.size(); i++) {        
                        for(int j=0; j<cols.size(); j++) {                           
                            TableColumn col = cols.get(j);
                            String cellValue = col.getCellData(obvData.get(i)).toString();
                            if(j==0){
                                hb = (HBox) col.getCellData(obvData.get(i));
                                for(Node node : hb.getChildren()){
                                    if(node instanceof Text)
                                        cellValue = ((Text) node).getText();
                                }
                            }
                            cellValue = cellValue.toLowerCase();
                            if(cellValue.contains(filterText.textProperty().get().toLowerCase())) {
                                tableItems.add(obvData.get(i));
                                break;
                            }
                        }
                    }
                    tblVertexCommunity.setItems(tableItems);
                }
                
            });
            
            tblVertexCommunity.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                    System.out.println("CommunitiesValues(): "+newValue);

                    //Check whether item is selected and set value of selected item to Label

                    if(tblVertexCommunity.getSelectionModel().getSelectedItem() != null) 
                    {    
                       CommunityTableViewRow selectedRow = (CommunityTableViewRow)tblVertexCommunity.getSelectionModel().getSelectedItem();
                       int intSelectedID = selectedRow.getVertexID();
                       
                        // Select the Item in Graph
                       MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
                       UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().selectVertex(intSelectedID);
                    }
                }
            });
            
            ScrollPane scrollTable = new ScrollPane();
            scrollTable.setContent(tblVertexCommunity);
            scrollTable.setFitToHeight(true);
            scrollTable.setFitToWidth(true);

            
        } catch (Exception ex) {
            System.out.println("CommunitiesValue.CommunitiesValue(): EXCEPTION");
            ex.printStackTrace();
        }

    }
    
    public static void resetGlobalColors(int pintProjectID, 
            int pintGraphID) {
        //Reset global colors - clear this color map in logic
        GraphAPI.resetGlobalCommunityColorMap(pintProjectID, pintGraphID);
        
        
//        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
//        GraphTab activeGraphTab = UIInstance.getActiveProjectTab().getActiveGraphTab();
//        Map<String, Color> mapGlobalCommunityColor = activeGraphTab.getMapGlobalCommunityColor();
//        mapGlobalCommunityColor.clear();
//        activeGraphTab.setIntNextColorIndex(0);
    }

    public void updateCommunityColorMap(String communityID, Color newColor) {
        //update its local mapCommunityColor for this time frame
        mapCommunitiesColor.put(communityID, newColor);
        
        
//        DO NOT NEED TO DO IT SINCE IT HAS BEEN DONE IN LOGIC
//        update graphs MapGlobalCommunityColor
//        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
//        GraphTab activeGraphTab = UIInstance.getActiveProjectTab().getActiveGraphTab();
//        activeGraphTab.getMapGlobalCommunityColor().put(communityID, newColor);
        
        
    }
    
    public void updateTableVertexCommunityColors(Set<Integer> pSetVertexIds, Color newColor){
        ObservableList<CommunityTableViewRow> tableItems = tblVertexCommunity.getItems();
        for(CommunityTableViewRow row : tableItems){
            if(pSetVertexIds.contains(row.getVertexID())){
                row.setCommunityColor(newColor);
                System.out.println(row.getVertexID() + "  " + row.getCommunity());
            }
            //System.out.println(row.getVertexID() + "  " + row.getCommunity());
        }
        
        
    }

    private void fillMapCommunitiesVertices(int pintProjectID, int pintGraphID, int pintTimeFrameIndex) {
         mapCommunities = CommunityMiningAPI.getCommunities(pintProjectID, //CommunityMiningAPI.getResults(pintProjectID, 
                     pintGraphID, pintTimeFrameIndex);

            
    }

    private void fillMapCommunitiesColors(int pintProjectID, int pintGraphID, int pintTimeFrameIndex) {
            Map<String, String>mapComunityColorsString = GraphAPI.getMapCommunityColors(pintProjectID, pintGraphID, pintTimeFrameIndex);    
            for(String community : mapComunityColorsString.keySet()){
                this.mapCommunitiesColor.put(community, Color.valueOf(mapComunityColorsString.get(community)));
            }
            
    }    
}
