/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accordiontab;

import config.LangConfig;
import config.SceneConfig;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/*
 *  Class Name      : CommunitiesContent
 *  Created Date    : 2015-07-22
 *  Description     : Contents to be displayed on the CommunitiesContent Pane
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
    2015-09-09      Talat           Renamed the class name from Communities to CommunitiesContent
 *  2015-09-01      Talat           Implements ITitledPaneContents
 * 
*/
public class CommunitiesContent implements ITitledPaneContents {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private String strTitle ;
    private Separator separator;
        
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public String getTitle(){
        return this.strTitle;
    }
    public void setTitle(String pstrTitle){
        this.strTitle = pstrTitle;
    }
        
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    public CommunitiesContent(String pstrTitle) {
        this.strTitle = pstrTitle;
        // this.tvwCommunities = new TreeView();
        
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    /**
     *  Method Name     : initiateCommunities()
     *  Created Date    : 2015-07-22
     *  Description     : Initializes the communities pane that is to be displayed on the Accordion
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param paccParentAccordionPane: Accordion
     *  @param paccCommunities: TitledPane
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2015-09-01      Talat           Changed initiateCommunities() to the overridden method initialize()
     * 
    */
    @Override
    public void initialize(Accordion paccParentAccordionPane, TitledPane paccCommunities) {

        TableView tblVertexCommunity = new TableView();
        VBox vboxContainer = new VBox();
        
        Group grpRectangle = new Group();
        Rectangle r = new Rectangle(SceneConfig.COMMUNITYRECT_WIDTH, SceneConfig.COMMUNITYRECT_HEIGHT);
        r.setFill(Color.valueOf(SceneConfig.COLORS_COMMUNITYTITLE));
        grpRectangle.getChildren().add(r);
        
        Text txtParent = new Text(LangConfig.GENERAL_COMMUNITYPLURAL);
        HBox hboxParent = new HBox(grpRectangle, txtParent) ;
        hboxParent.setSpacing(5);
        hboxParent.setPadding(new Insets(2,2,2,2));
        hboxParent.setAlignment(Pos.TOP_LEFT);
        
        TreeItem<HBox> titemCommunities = new TreeItem<>(hboxParent);
        
        TreeView tvwCommunities = new TreeView(titemCommunities);
                
        TableColumn tblclmVertexID = new TableColumn(LangConfig.GENERAL_VERTEXID);
        tblVertexCommunity.getColumns().addAll(tblclmVertexID);
                
        TableColumn tblclmCommunityID = new TableColumn(LangConfig.GENERAL_COMMUNITYID);
        tblVertexCommunity.getColumns().addAll(tblclmCommunityID);
        
        tblclmVertexID.minWidthProperty().bind(tblVertexCommunity.widthProperty().divide(tblVertexCommunity.getColumns().size()));
        tblclmCommunityID.minWidthProperty().bind(tblVertexCommunity.widthProperty().divide(tblVertexCommunity.getColumns().size()));
        
        Label filterLabel = new Label(LangConfig.GENERAL_FILTER);
        TextField filterText = new TextField();
        HBox filter = new HBox();
        filter.getChildren().addAll(filterLabel,filterText);
        filter.setSpacing(10);
        filter.setPadding(new Insets(2,2,2,2));
        filter.setAlignment(Pos.CENTER_LEFT);
        filterText.setPromptText("filter table rows");
                
        ScrollPane scrollCommunitiesTree = new ScrollPane();        
        scrollCommunitiesTree.setContent(tvwCommunities);   
        scrollCommunitiesTree.setFitToHeight(true);
        scrollCommunitiesTree.setFitToWidth(true);
        
        separator = new Separator();
        
        ScrollPane scrollTable = new ScrollPane();
        scrollTable.setContent(tblVertexCommunity);
        scrollTable.setFitToHeight(true);
        scrollTable.setFitToWidth(true);
        
        vboxContainer.getChildren().add(scrollCommunitiesTree) ;
        vboxContainer.getChildren().add(separator);
        vboxContainer.getChildren().add(filter) ;
        vboxContainer.getChildren().add(scrollTable);
        
        vboxContainer.setSpacing(20);
        vboxContainer.setPadding(new Insets(10, 10, 10, 10));
        vboxContainer.setDisable(true);
        
        paccCommunities.setText(this.getTitle());
        paccCommunities.setContent(vboxContainer);
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    /**
     *  Method Name     : updatePane
     *  Created Date    : 2015-09-01
     *  Description     : Initializes the UI components that are to be displayed on the CommunitiesContent Tab in the Analysis Screen
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param paccParentAccordionPane: Accordion
     *  @param paccTitledPane: TitledPane
     *  @param pintProjectID : int
     *  @param pintGraphID: int - Graph whose parameters are to be reflected in the CommunitiesContent Panel
     *  @param pintTimeFrameIndex : int
     *  @param pobjContent : Object
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-04-01      Talat           Writing the contents of the UpdatePane
    */
    @Override
    public void updatePane(Accordion paccParentAccordionPane, 
            TitledPane paccTitledPane, 
            int pintProjectID, 
            int pintGraphID, 
            int pintTimeFrameIndex, 
            Object pobjContent) {   
        
        if (pobjContent != null) {
            
            VBox vboxNewContainer = (VBox)(paccTitledPane.getContent());            
            vboxNewContainer.setDisable(false);
            CommunitiesValues commValues = (CommunitiesValues)pobjContent;
            separator = new Separator();
            
            vboxNewContainer.getChildren().clear();
            vboxNewContainer.getChildren().add(commValues.getTreeView());
            vboxNewContainer.getChildren().add(separator);
            vboxNewContainer.getChildren().add(commValues.getFilter());
            vboxNewContainer.getChildren().add(commValues.getTableView());
        }
    }
}
