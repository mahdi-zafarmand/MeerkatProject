/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import algorithm.graph.layout.algorithms.ForceAtlas2;
import algorithm.graph.layout.algorithms.Layout;
import algorithm.graph.layout.algorithms.RandomLayout;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.reader.GMLReader;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import static test.MeerkatGraphReaderTest.CLASS_NAME;

/**
 *
 * @author aabnar
 */
public class ForceAtlavLayoutTestUI extends JFrame {
    
    
    Map<Integer,Double[]> lstVertices = new HashMap<>();
    Map<Integer,Double[]> mapPrevVertice = new HashMap<>();
    public static Graphics2D draw;
    
    
    List<int[]> lstEdges;
    
    public ForceAtlavLayoutTestUI(Map<Integer,Double[]> points, List<int[]> lstEdgs) {
        this.lstVertices = points;
        this.lstEdges = lstEdgs;
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.draw = (Graphics2D) this.getGraphics();
//        this.setBackground(Color.white);
    }
    
    public void paint(Graphics g) {
        
        this.draw = (Graphics2D) g;
        
        int r = 10;
        for (Double[] point : lstVertices.values()) {
            this.draw.setColor(Color.red);
            int x = (int) (point[0] * 500) + 100;
            int y = (int) (point[1] * 500) + 100;
            this.draw.fillOval(x-r,  y-r,  2*r , 2* r);
        }
        
        for (int[] e : lstEdges) {
            System.out.println("DRAW EDGE!");
            this.draw.setColor(getBackground());
            int x_s = (int) (mapPrevVertice.get(e[0])[0] * 500) + 100;
            int y_s = (int) (mapPrevVertice.get(e[0])[1] * 500) + 100;
            
            int x_e = (int) (mapPrevVertice.get(e[1])[0] * 500) + 100;
            int y_e = (int) (mapPrevVertice.get(e[1])[1] * 500) + 100;
            this.draw.drawLine(x_s, y_s, x_e, y_e);
        }
        
        if (!lstVertices.isEmpty()) {
            for (int[] e : lstEdges) {
                this.draw.setColor(Color.BLACK);
                int x_s = (int) (lstVertices.get(e[0])[0] * 500) + 100;
                int y_s = (int) (lstVertices.get(e[0])[1] * 500) + 100;

                int x_e = (int) (lstVertices.get(e[1])[0] * 500) + 100;
                int y_e = (int) (lstVertices.get(e[1])[1] * 500) + 100;
                this.draw.drawLine(x_s, y_s, x_e, y_e);
            }
        }
        
        
        
    }
    
    public void updatePints(Map<Integer,Double[]> mapPoints) {
        
        this.mapPrevVertice = new HashMap<>(lstVertices);
        this.lstVertices = mapPoints;
//        draw.clearRect(0, 0, 1000, 1000);
        
        
        for (int[] e : lstEdges) {
            if (!mapPrevVertice.isEmpty()) {
                this.draw.setColor(getBackground());
                int x_s = (int) (mapPrevVertice.get(e[0])[0] * 500) + 100;
                int y_s = (int) (mapPrevVertice.get(e[0])[1] * 500) + 100;

                int x_e = (int) (mapPrevVertice.get(e[1])[0] * 500) + 100;
                int y_e = (int) (mapPrevVertice.get(e[1])[1] * 500) + 100;
                this.draw.drawLine(x_s, y_s, x_e, y_e);
            }
            this.draw.setColor(Color.BLACK);
            int x_s = (int) (lstVertices.get(e[0])[0] * 500) + 100;
            int y_s = (int) (lstVertices.get(e[0])[1] * 500) + 100;
            
            int x_e = (int) (lstVertices.get(e[1])[0] * 500) + 100;
            int y_e = (int) (lstVertices.get(e[1])[1] * 500) + 100;
            this.draw.drawLine(x_s, y_s, x_e, y_e);
        }
        
        int r = 10;
        for (int id : lstVertices.keySet()) {
            if (!mapPrevVertice.isEmpty()) {
                this.draw.setColor(getBackground());
                int x = (int) (mapPrevVertice.get(id)[0] * 500) + 100;
                int y = (int) (mapPrevVertice.get(id)[1] * 500) + 100;
                this.draw.fillOval(x-r,  y-r,  2*r , 2* r);
            }
            this.draw.setColor(Color.red);
            int x = (int) (lstVertices.get(id)[0] * 500) + 100;
            int y = (int) (lstVertices.get(id)[1] * 500) + 100;
            this.draw.fillOval(x-r,  y-r,  2*r , 2* r);
        }

        repaint();
        
    }

    public static void main(String arg[]) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                
                IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                loadFile("/cshome/aabnar/workspace/set4/network.gml");
        
                List<int[]> lstEdgs = new LinkedList<>();
                for (IEdge e  : dynaGraph.getEdges(dynaGraph.getAllTimeFrames().get(0))) {
                    int[] vIds = new int[2];
                    vIds[0] = e.getSource().getId();
                    vIds[1] = e.getDestination().getId();
                    lstEdgs.add(vIds);
                }
                System.out.println(lstEdgs.size());
                
                ForceAtlavLayoutTestUI frame = new ForceAtlavLayoutTestUI(new HashMap<>(), lstEdgs);
                
                ForceAtlas2<IVertex,IEdge<IVertex>> faLayout = 
                        new ForceAtlas2<>(dynaGraph, dynaGraph.getAllTimeFrames().get(0), null);
                
                Layout layout = layoutGraph(dynaGraph);
                
                Thread th = new Thread(faLayout);
                th.start();

                while(th.isAlive()) {
                    frame.updatePints(faLayout.getResults());
                }
            }
        });
    }

    /**
     *
     * @param pstrFilePath
     * @return
     */
    public static IDynamicGraph<IVertex,IEdge<IVertex>> loadFile(String pstrFilePath) {
        GMLReader reader = new GMLReader(pstrFilePath);
        System.out.println(CLASS_NAME+": reads dynamic file...");
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = reader.loadFile();
        
        return dynaGraph;
    }
    
    /**
     *
     * @param dynaGraph
     */
    public static Layout layoutGraph(IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph) {

            
            RandomLayout rndLayout = new RandomLayout(dynaGraph, dynaGraph.getAllTimeFrames().get(0), null);
            
            Thread th1 = new Thread(rndLayout);
            th1.start();
            
            while(th1.isAlive());
            
            return rndLayout;
    }

    private static void atlasLayout(IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph) {
        System.err.println("----------------------------------------------------");
        
        
    }
}
