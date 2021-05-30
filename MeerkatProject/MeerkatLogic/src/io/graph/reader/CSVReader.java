package io.graph.reader;

import config.MeerkatSystem;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import datastructure.core.DynamicAttributable.DynamicAttributer.GenericDynamicAttributer;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import datastructure.core.graph.impl.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * The CSV file has to have this format: The first row contains the name of the
 * attributes, each row is a vertex, each cell is separated by a tab.
 * 
 * @author xl16
 * 
 * -------------------------------------
 * Edit history
 * Date				Author			Description
 * july 21, 2015	aabnar			updated to be compatible with the new 
 * 									Data structure in the migration period.
 * 
 */
public class CSVReader extends GraphReader{

    private int vertexCount = 0;
    
    private TimeFrame tf = TimeFrame.DEFAULT_TIMEFRAME;
    
    /**
     *
     * @param pstrPath2FileName
     */
    public CSVReader(String pstrPath2FileName) {
        super(pstrPath2FileName);
    }
    
    /**
     * MethodName: loadFile
     * Description: reads a csv file and creates a graph without any edges
     * Version: 1.0
     * Author: Afra
     * @return 
     * 
     * 
     * EDIT HISTORY
     * DATE         AUTHOR      DESCRIPTION
     * 2016 Apr 21  Afra        saves Id of the vertex as FILE_ID to the attributes. Because there is no default must have id in CSV files.
     *                          //TODO: later on we may ask the user to choose the FILE_ID attribute among the ones in the file.
     */
    @Override
    public DynamicGraph<IVertex,IEdge<IVertex>> loadFile() {
//        firstRun();
        
        DynamicGraph<IVertex,IEdge<IVertex>> dynaGraph 
                = new DynamicGraph<>(vertexCount , 0);
        
        IStaticGraph<IVertex,IEdge<IVertex>> igraph 
                = new StaticGraph<>(vertexCount , 0);
        
        dynaGraph.addGraph(tf, igraph);
        

        InputStreamReader reader;
        try {
            reader = new InputStreamReader(
                            new FileInputStream(strFilePath));

            BufferedReader br = new BufferedReader(reader,BUFFER_SIZE);
            String line;
            boolean firstLine = true;
            // Defined by the first row of the CSV file

            Set<Integer> sysAttrNames = new HashSet<>();
            List<String> attrNames = new ArrayList<>();
            
            line = br.readLine();
            while (line != null) {
                line = line.trim();
                
                // The cells must be separated by a comma
                String[] cells = line.split(",");
                cells = treatSpcialCasesWithComma(cells);
                if (firstLine) {
                    // Default: the first line are the attribute names
                    for (int i = 0 ; i < cells.length ; i++) {
                        if (MeerkatSystem.isSystemAttribute(cells[i])) {
                            sysAttrNames.add(i);
                        } 
                        attrNames.add(cells[i]);
                    }
                    firstLine = false;
                } else {
                    IVertex ivtNewV = new Vertex();
                    vertexCount++;
                    // Add all the attributes
                    GenericDynamicAttributer attributer = new GenericDynamicAttributer();
                    for (int i = 0; i < cells.length; i++) {
                        if (cells[i] != null) {
                            if (attrNames.get(i).equals(GraphMLReader.id)) {
                                ivtNewV.getUserAttributer().addAttribute(MeerkatSystem.FILE_ID, ivtNewV.getId() + "", tf);
                            } else if (sysAttrNames.contains(i)) {
                                ivtNewV.getSystemAttributer().addAttributeValue(attrNames.get(i), cells[i], new Date(), TimeFrame.DEFAULT_TIMEFRAME);
                            } else {
                                ivtNewV.getUserAttributer().addAttribute(attrNames.get(i), cells[i], TimeFrame.DEFAULT_TIMEFRAME);
                            }
                        }
                    }
                    
                    dynaGraph.addVertex(ivtNewV, tf);
                    
                    /* FORNOW: We write Meerkat ID as the FILE_ID in CSV until
                        later on that we decide to get which attribute is the FILE_ID 
                        from the user */
                    if (!ivtNewV.getAttributeNames().contains(MeerkatSystem.FILE_ID)) {
                        ivtNewV.getUserAttributer().
                            addAttribute(MeerkatSystem.FILE_ID, 
                                    ivtNewV.getId() + "",
                                    tf);
                    }
                }
                line = br.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return dynaGraph;
    }
    
    private String[] treatSpcialCasesWithComma(String[] input){
        List<String> output = new ArrayList<>();
        int start = 0;
        int end = 0;
        int i = 0;
        
        while(i < input.length){
            start = i;
            end = i;
            if(input[start].startsWith("\"")){
                while(end < input.length && input[i].endsWith("\"") == false){
                    i++;
                }
                end = i;
            }
            String temp = "";
            for(int j = start; j <= end; j++){
                temp += input[j];
            }
            output.add(temp);
            i++;
        }
        return output.toArray(new String[0]);
    }
    
    /**
     * MethodName: firstRun
     * Description: reads the file once to counts the number of vertices
     * Version: 1.0
     * Author: Afra
     * 
     */
    //    MAHDI: the next function only counts the number of vertices and edges, this can easily be done when nodes and edges are created.

//    private void firstRun() {
//        InputStreamReader reader;
//        try {
//            reader = new InputStreamReader(
//                            new FileInputStream(strFilePath));
//
//            BufferedReader br = new BufferedReader(reader,BUFFER_SIZE);
//            String line;
//            boolean firstLine = true;
//            // Defined by the first row of the CSV file
//
//            List<String> attrNames = new ArrayList<>();
//            line = br.readLine();
//            line = br.readLine();
//            while ((line = br.readLine()) != null) {
//                line = line.trim();
//                vertexCount++;
//                line = br.readLine();
//            }
//            reader.close();
//        } catch (FileNotFoundException e1) {
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
