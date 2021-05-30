/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import algorithm.graph.layout.algorithms.SpatialLayout;
import config.LayoutParameters;
import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.reader.GraphMLReader;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author aabnar
 */
public class SpatialLayoutTest extends JFrame{
    IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph;
    TimeFrame tf;
    public SpatialLayoutTest (IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph , TimeFrame tf) {
        this.dynaGraph = dynaGraph;
        this.tf = tf;
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (IVertex v : dynaGraph.getVertices(tf)) {
            double x = Double.parseDouble(v.getSystemAttributer().getAttributeValue(MeerkatSystem.X, tf)) * 500 +20;
            double y = Double.parseDouble(v.getSystemAttributer().getAttributeValue(MeerkatSystem.Y, tf)) * 500 +20;
            System.out.println("SpatialLayoutTest.paint() : drawing circles ..." + x + "," + y);
            ((Graphics2D) g).setColor(Color.DARK_GRAY);
            ((Graphics2D) g).fillOval((int) (x ) - 5, (int) (y ) - 5, 10, 10);
            System.out.println("SpatialLayoutTest.paint() : drawing circles ..." );
        }
    }
    
    public static void main(String[] args) {
        
         SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                
                GraphMLReader reader = new GraphMLReader(
                        "/cshome/aabnar/workspace/Spatial Data/cma_canada.graphml");
                IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = reader.loadFile();

                String[] parameters = { LayoutParameters.SPATIALLAYOUT_LATITUDE+":lat", 
                    LayoutParameters.SPATIALLAYOUT_LONGTITUDE+":lon"};
                SpatialLayout<IVertex,IEdge<IVertex>> spLayout = 
                        new SpatialLayout<>(dynaGraph,dynaGraph.getAllTimeFrames().get(0),parameters);

                Thread th = new Thread(spLayout);
                th.start();

                while(th.isAlive());

                System.out.println("SpatialLayoutTest.main() : layout calculation is done!");
                SpatialLayoutTest frame = 
                        new SpatialLayoutTest(dynaGraph, dynaGraph.getAllTimeFrames().get(0));

                frame.repaint();
            }});
    }
}
