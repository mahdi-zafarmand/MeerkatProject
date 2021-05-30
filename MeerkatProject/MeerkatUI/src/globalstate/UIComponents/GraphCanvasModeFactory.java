/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalstate.UIComponents;

import config.ModeConfig.ModeTypes;

/**
 *
 * @author AICML Administrator
 */
public class GraphCanvasModeFactory {
    public GraphCanvasMode getGraphCanvasMode(ModeTypes pstrCanvasMode){
        //TODO put enum here instead of string
        switch (pstrCanvasMode) {
            case SELECT:
                return new NormalSelectionMode();
            case VERTEXADD:
                return new AddVertexMode();
            case EDGEADD:
                return new AddEdgeMode();
            case VERTEXMULTIFRAMEADD:
                return new AddVertexMultipleTimeFramesMode();
            case VERTEXDELETE:
                return new DeleteVertexMode();
            case EDGEDELETE:
                return new DeleteEdgeMode();
            case SHORTESTPATH:
                return new ShortestPathMode();
            default:
                return new NormalSelectionMode(); 
        }
    }
}
