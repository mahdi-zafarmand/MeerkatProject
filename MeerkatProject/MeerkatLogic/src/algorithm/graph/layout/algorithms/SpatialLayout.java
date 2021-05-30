/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithm.graph.layout.algorithms;

import algorithm.graph.GeneralStatistics;
import algorithm.graph.GraphUtil;
import algorithm.util.Numbers;
import config.LayoutParameters;
import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aabnar
 */
public class SpatialLayout<V extends IVertex, E extends IEdge<V>> extends Layout<V, E>{

    public static final double earthR = 6371.0;  // (km)
    private String strLatitudeAttName = "lat";
    private String strLongtitudeAttName = "lon";

    private double dblMinLat = Double.MAX_VALUE;
    private double dblMaxLat = Double.MIN_VALUE;
    
    private double dblMinLong = Double.MAX_VALUE;
    private double dblMaxLong = Double.MIN_VALUE;

   
    private Map<V,double[]> mapCoordinates = new HashMap<>();

    private Map<V,Double> mapCoordinatesX = new HashMap<>();
    private Map<V,Double> mapCoordinatesY = new HashMap<>();
    
    
    private enum Dim {
        ZERO,
        ONE,
        TWO
    };
    
    Dim dimension = Dim.ZERO;
    
    public SpatialLayout (IDynamicGraph<V,E> pDynaGraph, 
            TimeFrame tf,
            String[] parameters) {
        super(pDynaGraph, tf, parameters);
    
        if (parameters.length == 0) {
            this.dimension = Dim.ZERO;
        } else if (parameters.length == 1) {
            this.dimension = Dim.ONE;
            if(!readLatitude(parameters[0])) {
                readLongtitude(parameters[0]);
            }
        } else if (parameters.length == 2) {
            this.dimension = Dim.TWO;
            if(readLatitude(parameters[0])) {
                readLongtitude(parameters[1]);
            } else {
                readLatitude(parameters[1]);
                readLongtitude(parameters[0]);
            }
        }
    }
    
    public void run() {

        if (strLatitudeAttName != null) {
            findMinMax(strLatitudeAttName);
        } else {
            dblMinLat = 0;
            dblMaxLat = 1;
        }
        if (strLongtitudeAttName != null) {
            findMinMax(strLongtitudeAttName);
        } else {
            dblMinLong = 0;
            dblMaxLong = 1;
        }
        
        setPositions();
    }
    
    private double[] calcXYZ(double dblLatDegree, double dblLongDegree) {
        double z = Math.sin(dblMinLat) *earthR;
        double xy = Math.cos(dblMinLat) * earthR;
        double x = Math.cos(dblLongDegree) * xy;
        double y = Math.sin(dblLongDegree) * xy;
        
        double[] xyz = {x,y,z};
        
        return xyz;
    }
    
    private void setPositions() {
        double Max = Math.max(dblMaxLat, dblMaxLong);
        
//        min = min + new Random().nextDouble();
        
        for (V v : dynaGraph.getVertices(tf)) {
            if(!running){
                break;
            }
            double newX = (mapCoordinatesX.get(v) - dblMinLong) / (Max);
            double newY = 1- (mapCoordinatesY.get(v) - dblMinLat) / (Max);
           
            setLocation(v, newX, newY);
        }
    }
    
    private boolean readLatitude(String parameter) {
        if (parameter.startsWith(LayoutParameters.SPATIALLAYOUT_LATITUDE)) {
            this.strLatitudeAttName = parameter.substring(
                    LayoutParameters.SPATIALLAYOUT_LATITUDE.length() + 1);
            
            return true;
        }
        return false;
    }

    private boolean readLongtitude(String parameter) {
        if (parameter.startsWith(LayoutParameters.SPATIALLAYOUT_LONGTITUDE)) {
            this.strLongtitudeAttName = parameter.substring(
                    LayoutParameters.SPATIALLAYOUT_LONGTITUDE.length() + 1);
            
            return true;
        }
        return false;
    }
    
    private void findMinMax(String strAttName) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        
        for (V v : dynaGraph.getVertices(tf)) {
            double dblVal = getDecimal(v.getUserAttributer()
                    .getAttributeValue(strAttName, tf));
            
            System.out.println("SpatialLayout.findMinMax() : " + v.getUserAttributer()
                    .getAttributeValue(strAttName, tf) + " parsed into:  " + dblVal);
           
            if (strAttName.equals(strLatitudeAttName)) {
                mapCoordinatesY.put(v, dblVal);
            } else if (strAttName.equals(strLongtitudeAttName)) {
                mapCoordinatesX.put(v, dblVal);
            }
            
            if (dblVal < min) {
                min = dblVal;
            } else if ( dblVal > max) {
                max = dblVal;
            }
        }
        
        if (strAttName.equals(strLatitudeAttName)) {
            this.dblMaxLat = max;
            this.dblMinLat = min;
        } else if (strAttName.equals(strLongtitudeAttName)) {
            this.dblMaxLong = max;
            this.dblMinLong = min;
        }
    }
    
    /**
     * MethodName: getDecimal
     * Description : converts degree,minute,second to decimal : decimal = degree + minute/60 + second/3600
     * 
     * @param attributeValue
     * @return 
     *          Decimal Value
     */
    private static double getDecimal(String attributeValue) {
        double decimalVal;
                
        StringBuilder degree = new StringBuilder("0");
        StringBuilder minute = new StringBuilder("0");
        StringBuilder second = new StringBuilder("0");
        
        int count = 0;
        boolean prevDigit = false;
        if (Numbers.isNumber(attributeValue)) {
            decimalVal = Double.parseDouble(attributeValue);
        } else {
            for (char c : attributeValue.toCharArray()) {
                if (Character.isDigit(c) ||  c == '.') {
                    prevDigit = true;
                    if (count == 0) {
                        degree.append(c);
                    } else if (count ==1 ) {
                        minute.append(c);
                    } else if(count ==2) {
                        second.append(c);
                    }
                } else {
                    if (prevDigit) {
                        count++;
                    }
                    prevDigit = false;
                }
            }

            System.out.println(degree + " " + minute + " " + second);
            
            decimalVal = Double.parseDouble(degree.toString()) +
                    Double.parseDouble(minute.toString())/60 +
                    Double.parseDouble(second.toString())/3600;

            if (attributeValue.contains("W") || attributeValue.contains("S") || attributeValue.startsWith("-")) {
                decimalVal *= -1;
            }
        }
        return decimalVal;
        
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Type getType() {
        return Type.STANDARD;
    }

}
