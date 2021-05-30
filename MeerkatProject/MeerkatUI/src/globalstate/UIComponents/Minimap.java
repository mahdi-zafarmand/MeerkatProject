/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalstate.UIComponents;

import config.SceneConfig;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author AICML Administrator
 */
public class Minimap{
        private StackPane stkMiniMap ; // Holder for the minimap
        private Shape shpMiniMapMasking ;
        
        
        //private GraphCanvas graphCanvas;
        private PanAndZoomPane[] ArrPanAndZoomPane;
        private SimpleIntegerProperty intCurrentTimeFrameIndexProperty;
        //private ScrollPane scrlCanvasM;
        
        public Minimap(PanAndZoomPane[] pArrpanAndZoomPane, SimpleIntegerProperty intCurrentTimeFrameIndexProperty){
        //public Minimap(PanAndZoomPane anchZoomPane, ScrollPane scrlCanvas){
            //this.graphCanvas = graphCanvas;
            this.ArrPanAndZoomPane = pArrpanAndZoomPane;
            this.intCurrentTimeFrameIndexProperty = intCurrentTimeFrameIndexProperty;
            //this.scrlCanvasM = scrlCanvas;
            // Child 2 - Initializing the MiniMap
              //stkMiniMap = new StackPane();

              // #COMMENTED: Trying to add the minimap after the graph is displayed
              // DIDNT WORK
              // Add the Minimap so that the zoom pane can now be released to get it attached on the actual canvas
              //stkMiniMap = addMiniMap(group);
              stkMiniMap = addMiniMap(getPanAndZoomPane());
              stkMiniMap.setStyle(SceneConfig.MINIMAP_BACKGROUND_COLOR_DEFAULT );
              addLayerToMiniMap();              


              

              // #Debug
              // System.out.println("GraphTab.initiateUIComponents(): ID = "+this.intGraphID);


              //this.uitabGraph.setContent(this.stkCanvasMinimapHolder);
        }
        
      private PanAndZoomPane getPanAndZoomPane(){
        return ArrPanAndZoomPane[intCurrentTimeFrameIndexProperty.getValue()];
      }  
      public StackPane getStkPaneMinimap(){
          
          return stkMiniMap;
      }
      public StackPane addMiniMap(Parent pMiniMapNode) {

          boolean GRAPHCANVAS_DIMENSIONS_ADJUSTED = true;
          int GRAPHCANVAS_WIDTH = 700;
          int GRAPHCANVAS_HEIGHT = 700;
          int GRAPHCANVAS_TABTITLE_HEIGHT = 10;



          // HOW ELEMENTS ARE ARRANGED IN MULTILAYERS FRAME
          // StackPane(Anchor) -> StackPane(MiniMap) -> HBox -> ImageView -> Image

          StackPane stkpaneMiniMap_Return = new StackPane();

          // Create a throw away scene that will be rendered but not be displayed
          /*
          if (GRAPHCANVAS_DIMENSIONS_ADJUSTED) {
              // For the first time, the GraphCanvas Height will be less than the stored in SceneConfig.GRAPHCANVAS_HEIGHT, 
              // since the dimensions retrieved are from the inner bounds of the tabpane that has all the projects
              new Scene(pMiniMapNode, GRAPHCANVAS_WIDTH, GRAPHCANVAS_HEIGHT);
          } else {
              new Scene(pMiniMapNode, GRAPHCANVAS_WIDTH, GRAPHCANVAS_HEIGHT - GRAPHCANVAS_TABTITLE_HEIGHT);
              // Adjust the GraphCanvas Height to set the New Graph Height (by suntracting GRAPHCANVAS_TABTITLE_HEIGHT pixels from the total height)
              GRAPHCANVAS_HEIGHT -= GRAPHCANVAS_TABTITLE_HEIGHT ; 
              GRAPHCANVAS_DIMENSIONS_ADJUSTED = true ;
          }
          */
          // Create a writable image of the zoompane that will be used in the minimap
          WritableImage wimgSnapshot = getDrawingPane().snapshot(new SnapshotParameters(), null);           

          /*
          // Convert the writable image to image using BufferedImage - FOR REFERENCE
          BufferedImage biSanpshot = SwingFXUtils.fromFXImage((Image)wimgSnapshot, null); 
          Image imgSnapshot = SwingFXUtils.toFXImage(biSanpshot, null);
          */

          ImageView imgvwMiniMap = new ImageView();

          if (wimgSnapshot.getWidth() > wimgSnapshot.getHeight()) {
              imgvwMiniMap.setFitWidth(SceneConfig.MINIMAP_WIDTH);
              // System.out.println("GraphTab.GraphTab(): Width of the Minimap ImgVW set to: "+AppConfig.MINIMAP_WIDTH);
          } else {
              imgvwMiniMap.setFitHeight(SceneConfig.MINIMAP_HEIGHT);
              // System.out.println("GraphTab.GraphTab(): Height of the Minimap ImgVW set to: "+AppConfig.MINIMAP_HEIGHT);
          }
          imgvwMiniMap.setPreserveRatio(true);
          imgvwMiniMap.setSmooth(true);
          imgvwMiniMap.setCache(true); // To improve performance
          imgvwMiniMap.setImage(wimgSnapshot);
          // sktpaneMiniMap.setStyle(" -fx-border-color: black; -fx-border-width:5; -fx-border-style:solid; -fx-border-insets:3; -fx-border-radius:7;");

          // Add one more image View for blending
          ImageView imgvwForeground = new ImageView() ;

          Rectangle rect = new Rectangle(20,20,60,60);

          // Add the Image View to the HBOX so that a border could be drawn
          HBox hboxImage = new HBox(imgvwMiniMap);
          hboxImage.setStyle(" -fx-border-color: black; -fx-border-width:1; -fx-border-style:solid;");

          // #DEBUG
          // Just to find if the stack pane (minimap) is added or not on to the parent stack pane
          // System.out.println("GraphTab.GraphTab(): ImageView W: "+imgvwMiniMap.getViewport().getWidth()+"\tH: "+imgvwMiniMap.getViewport().getHeight());
          // sktpaneMiniMap.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));        
          // #ENDDEBUG

          // Set the parameters of the MiniMap
          stkpaneMiniMap_Return.setMinWidth(SceneConfig.MINIMAP_WIDTH) ;
          stkpaneMiniMap_Return.setMinHeight(SceneConfig.MINIMAP_HEIGHT) ;

          stkpaneMiniMap_Return.setMaxWidth(SceneConfig.MINIMAP_WIDTH) ;
          stkpaneMiniMap_Return.setMaxHeight(SceneConfig.MINIMAP_HEIGHT) ;

          // Add the minimap to the Parent Stack Pane
          stkpaneMiniMap_Return.getChildren().add(hboxImage);

          // stkpaneMiniMap_Return.setStyle(" -fx-border-color: black; -fx-padding: 0; -fx-background-color: firebrick;");

          return stkpaneMiniMap_Return ;

      }
      // Get the Drawable Pane
      private Node getDrawingPane() {
          return (Node)getPanAndZoomPane();
      }

      private void addLayerToMiniMap() {
          Rectangle rectOuter = new Rectangle(0, 0, SceneConfig.MINIMAP_WIDTH, SceneConfig.MINIMAP_HEIGHT);
          Rectangle rectInner = new Rectangle(0, 0, SceneConfig.MINIMAP_WIDTH, SceneConfig.MINIMAP_HEIGHT);
          shpMiniMapMasking = Shape.subtract(rectOuter, rectInner) ;
          shpMiniMapMasking.setStyle(SceneConfig.MINIMAP_MASKING_COLOR);   
          stkMiniMap.getChildren().add(shpMiniMapMasking);
      }
      public void updateMiniMap() {
          //System.out.println("Minimap.updateMinimap()");
          // Create a writable image of the zoompane that will be used in the minimap
          WritableImage wimgSnapshot = getDrawingPane().snapshot(new SnapshotParameters(), null);

          ImageView imgvwMiniMap = new ImageView();
          
          if (wimgSnapshot.getWidth() > wimgSnapshot.getHeight()) {
              imgvwMiniMap.setFitWidth(SceneConfig.MINIMAP_WIDTH);
              // System.out.println("GraphTab.GraphTab(): Width of the Minimap ImgVW set to: "+AppConfig.MINIMAP_WIDTH);
          } else {
              imgvwMiniMap.setFitHeight(SceneConfig.MINIMAP_HEIGHT);
              // System.out.println("GraphTab.GraphTab(): Height of the Minimap ImgVW set to: "+AppConfig.MINIMAP_HEIGHT);
          }
          
          imgvwMiniMap.setPreserveRatio(true);
          imgvwMiniMap.setSmooth(true);
          imgvwMiniMap.setCache(true); // To improve performance
          imgvwMiniMap.setImage(wimgSnapshot);

          // Add one more image View for blending
          ImageView imgvwForeground = new ImageView() ;

          // Add the Image View to the HBOX so that a border could be drawn
          HBox hboxImage = new HBox(imgvwMiniMap);
          hboxImage.setStyle(" -fx-border-color: black; -fx-border-width:1; -fx-border-style:solid;");

          // Set the parameters of the MiniMap
          stkMiniMap.setMinWidth(SceneConfig.MINIMAP_WIDTH) ;
          stkMiniMap.setMinHeight(SceneConfig.MINIMAP_HEIGHT) ;

          stkMiniMap.setMaxWidth(SceneConfig.MINIMAP_WIDTH) ;
          stkMiniMap.setMaxHeight(SceneConfig.MINIMAP_HEIGHT) ;

          // Add the minimap to the Parent Stack Pane
          stkMiniMap.getChildren().clear();
          stkMiniMap.getChildren().add(hboxImage);
          stkMiniMap.getChildren().add(shpMiniMapMasking);

          this.MiniMapMoveProperties(stkMiniMap);

          // pstkpaneMiniMap.setStyle(" -fx-border-color: black; -fx-padding: 0; -fx-background-color: firebrick;");
      }
      public void MiniMapMoveProperties (StackPane pMiniMap) {

          // ON PRESSING ONE OF THE MOUSE BUTTONS
          pMiniMap.setOnMousePressed((MouseEvent mouseEvent) -> {            

              // RIGHT CLICK OF THE MOUSE
              if (mouseEvent.getButton() == MouseButton.SECONDARY) {


                  // Display the Context Menu on right click
                  //MinimapContextMenu mcmInstance = MinimapContextMenu.getInstance(pMiniMap);
                  //mcmInstance.Show(pMiniMap, mouseEvent.getScreenX(), mouseEvent.getScreenY());

              } else if (mouseEvent.getButton() == MouseButton.PRIMARY) { // LEFT CLICK OF THE MOUSE

              }

          });

          // ON MOUSE BUTTON RELEASE
          pMiniMap.setOnMouseReleased((MouseEvent mouseEvent) -> {

          });

          // ON MOUSE BUTTON DRAGGING
          pMiniMap.setOnMouseDragged((MouseEvent mouseEvent) -> {            

              // To move only if the primary button has been pressed and dragged
              if (mouseEvent.getButton() == MouseButton.PRIMARY) {

              }
          });


          // ON HOVERING THE MOUSE ON THE MOUSE HOLDER
          pMiniMap.setOnMouseEntered((MouseEvent mouseEvent) -> {

          });

          // ON MOVING OUT OF THE VERTEX HOLDER AREA
          pMiniMap.setOnMouseExited((MouseEvent mouseEvent) -> {

          });      
      }
      public void changeMiniMapMaskingOriginal(
              double pdblTotalX
              , double pdblTotalY
              , double pdblViewPortX
              , double pdblViewPortY
              , double pdblViewPortXLength
              , double pdblViewPortYLength ) {

          Rectangle rectOuter = new Rectangle(0, 0, SceneConfig.MINIMAP_WIDTH, SceneConfig.MINIMAP_HEIGHT);

          // Normalize all the X values for AppConfig.MINIMAP_WIDTH and Y values for AppConfig.MINIMAP_HEIGHT                
          double dblMiniMapX = pdblViewPortX / pdblTotalX * SceneConfig.MINIMAP_WIDTH ;
          double dblMiniMapY = pdblViewPortY / pdblTotalY * SceneConfig.MINIMAP_HEIGHT ;

          double dblMiniMapXMax = ((pdblViewPortX + pdblViewPortXLength) / pdblTotalX * SceneConfig.MINIMAP_WIDTH)  ;
          double dblMiniMapYMax = ((pdblViewPortY + pdblViewPortYLength) / pdblTotalY * SceneConfig.MINIMAP_HEIGHT)  ;
          // limiting dblMiniMapXMax, dblMiniMapYMax to <=MINIMAP_WIDTH and MINIMAP_HEIGHT, othertwise it does not make sense to substract in shape.substract rectOuter - rectInner
          // dblMiniMapXMax < = MINIMAP_WIDTH-1. Subtracting -1 otherwise at equal size, it does not seem to work
          
          dblMiniMapXMax = dblMiniMapXMax > SceneConfig.MINIMAP_WIDTH? SceneConfig.MINIMAP_WIDTH-1:dblMiniMapXMax;
          dblMiniMapYMax = dblMiniMapYMax > SceneConfig.MINIMAP_HEIGHT? SceneConfig.MINIMAP_HEIGHT-1:dblMiniMapYMax;
          
          double dblMiniMapXLen = dblMiniMapXMax - dblMiniMapX;
          double dblMiniMapYLen = dblMiniMapYMax - dblMiniMapY;
          System.out.println("GraphTab.changeMiniMapMasking(): InnerRectangle: ("+dblMiniMapX+","+dblMiniMapY+")\t("+(dblMiniMapX+dblMiniMapXLen)+","+(dblMiniMapY+dblMiniMapYLen)+")\n");

          Rectangle rectInner = new Rectangle(dblMiniMapX, dblMiniMapY, dblMiniMapXLen, dblMiniMapYLen);

          stkMiniMap.getChildren().remove(shpMiniMapMasking);

          shpMiniMapMasking = Shape.subtract(rectOuter, rectInner);
          shpMiniMapMasking.setStyle(SceneConfig.MINIMAP_MASKING_COLOR);      

          stkMiniMap.getChildren().add(shpMiniMapMasking);

      }
      /**
       * Minimap Masking Logic
       * 
       * Rect1 = Bounding rectangle representing ScrollPane's viewPort. It has its origin fixed at 
       * (0,0) which we never translate. It is located at top-left of the graph displaying area. PanAndZoomPane has coordinates relative to this origin
       * Rect 2 = Bounding rectangle of PanAndZoomPane. This pane has the graph displayed inside it.
       * Its co-ordinates are (dblMinXTotalContent, dblMinYTotalContent) to (dblMinXTotalContent+dblTotalContentWidth, dblMinYTotalContent+dblTotalContentHeight)
       *
       * Take intersection of these 2 rectangles and find the resulting rectangle, this rectangle has to be unmasked
       *  on the minimap. This rectangle has to be scaled down and co-ordinates translated. Then subtract this rectangle from the total masked rectangle.
       * 
      */
      public void changeMiniMapMasking(
              double dblTotalContentWidth
              , double dblTotalContentHeight
              , double dblMinXTotalContent
              , double dblMinYTotalContent
              , double dblScrollPane_VIEWPORT_WIDTH
              , double dblScrollPane_VIEWPORT_HEIGHT ) {
          // java.awt.rectangle - it has co-ordinate system inverted on y-axis compared to cartesian co-ordinate system
          // similarly javafx has it inverted it too. so javafx = awt  = opposite of cartesian on y aixs
          java.awt.Rectangle rect1 = new java.awt.Rectangle(0, 0, (int)dblScrollPane_VIEWPORT_WIDTH, (int)dblScrollPane_VIEWPORT_HEIGHT);
          java.awt.Rectangle rect2 = new java.awt.Rectangle((int)dblMinXTotalContent, (int)dblMinYTotalContent, (int)(dblMinXTotalContent + dblTotalContentWidth), (int)(dblMinYTotalContent + dblTotalContentHeight));
          java.awt.Rectangle intersection = rect2.intersection(rect1);
          // look for a method which takes double as argument for rectangle
          // converting to int causes problem later in Inner rectangle co-ordinates - sometimes they come as -0.003 type because of int and double precision difference
          
          double x = intersection.getMinX();
          double y = intersection.getMinY();
          double width = intersection.getWidth();
          double height = intersection.getHeight();
          // System.out.println("Minimap.changeMinimapmasking: intersection rectangle = " + x +", " + y + " :w,h = " + width + ", " + height);

          Rectangle rectOuter = new Rectangle(0, 0, SceneConfig.MINIMAP_WIDTH, SceneConfig.MINIMAP_HEIGHT);
          // translate co-ordinates and scale down
          // Normalize all the X values for AppConfig.MINIMAP_WIDTH and Y values for AppConfig.MINIMAP_HEIGHT                
          double dblMiniMapX = ((x - dblMinXTotalContent) / dblTotalContentWidth) * SceneConfig.MINIMAP_WIDTH ;
          double dblMiniMapY = ((y - dblMinYTotalContent) / dblTotalContentHeight) * SceneConfig.MINIMAP_HEIGHT ;
          
          //double dblMiniMapXMax = ((pdblViewPortX + width) / pdblTotalX * MINIMAP_WIDTH)  ;
          //double dblMiniMapYMax = ((pdblViewPortY + height) / pdblTotalY * MINIMAP_HEIGHT)  ;
          
          double newWidth = (width/dblTotalContentWidth)*SceneConfig.MINIMAP_WIDTH;
          double newHeight = (height/dblTotalContentHeight)*SceneConfig.MINIMAP_HEIGHT;
          //double dblMiniMapXMax = ((pdblViewPortX+x + pdblViewPortXLength) / pdblTotalX * MINIMAP_WIDTH)  ;
          //double dblMiniMapYMax = ((pdblViewPortY+y + pdblViewPortYLength) / pdblTotalY * MINIMAP_HEIGHT)  ;
          // limiting dblMiniMapXMax, dblMiniMapYMax to <=MINIMAP_WIDTH and MINIMAP_HEIGHT, othertwise it does not make sense to substract in shape.substract rectOuter - rectInner
          // dblMiniMapXMax < = MINIMAP_WIDTH-1. Subtracting -1 otherwise at equal size, it does not seem to work
          
          //dblMiniMapXMax = dblMiniMapXMax > MINIMAP_WIDTH? MINIMAP_WIDTH-1:dblMiniMapXMax;
          //dblMiniMapYMax = dblMiniMapYMax > MINIMAP_HEIGHT? MINIMAP_HEIGHT-1:dblMiniMapYMax;
          
          //double dblMiniMapXLen = dblMiniMapXMax - dblMiniMapX;
          //double dblMiniMapYLen = dblMiniMapYMax - dblMiniMapY;
          // System.out.println("Minimap.changeMiniMapMasking(): InnerRectangle: ("+Math.abs(dblMiniMapX)+","+Math.abs(dblMiniMapX)+")\t("+newWidth+","+newHeight+")\n");

          Rectangle rectInner = new Rectangle(Math.abs(dblMiniMapX), Math.abs(dblMiniMapY), Math.abs(dblMiniMapX) + newWidth-0.1, Math.abs(dblMiniMapY) + newHeight-0.1);

          stkMiniMap.getChildren().remove(shpMiniMapMasking);

          shpMiniMapMasking = Shape.subtract(rectOuter, rectInner);
          shpMiniMapMasking.setStyle(SceneConfig.MINIMAP_MASKING_COLOR);      

          stkMiniMap.getChildren().add(shpMiniMapMasking);

      }
    
}
