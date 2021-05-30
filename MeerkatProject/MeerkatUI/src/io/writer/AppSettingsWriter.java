/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.writer;

import config.AppConfig;
import config.AppSettingsConfig;
import config.CommunityMiningConfig;
import config.SceneConfig;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *  Class Name      : AppSettingsWriter
 *  Created Date    : 2016-07-15
 *  Description     : The Writer the Application Settings
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-08-23      Abhi            Added a helper method for writing values to pstrSettingsXMLFilePath file
 *  
 * 
*/

public class AppSettingsWriter {
    
    /**
     *  Method Name     : WriteXML()
     *  Created Date    : 2016-07-15
     *  Description     : Writes the XML with the settings in it
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrSettingsXMLFilePath : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void WriteXML(String pstrSettingsXMLFilePath) {
        
        BufferedWriter bw = null;
        
        try {
            
            // Create a file for the xmml settings            
            File file = new File(pstrSettingsXMLFilePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            
            FileWriter fw = new FileWriter(file);             
            bw = new BufferedWriter(fw);             
             
            // Write the root tag - Settings
            bw.write("<"+AppSettingsConfig.SETTINGS_ROOT_TAG+">\n");
            
            // Writes the UI Tab
            bw.write("\t<"+AppSettingsConfig.UITAB_TAG+">\n");
            
            // Write the Vertex Properties
            bw.write("\t\t<"+AppSettingsConfig.UITAB_VERTEXCOLORDEFAULT_TAG+">");
            bw.write(SceneConfig.VERTEX_COLOR_DEFAULT);
            bw.write("</"+AppSettingsConfig.UITAB_VERTEXCOLORDEFAULT_TAG+">\n");
            
            bw.write("\t\t<"+AppSettingsConfig.UITAB_VERTEXTCOLORSELECTED_TAG+">");
            bw.write(SceneConfig.VERTEX_COLOR_SELECTED);
            bw.write("</"+AppSettingsConfig.UITAB_VERTEXTCOLORSELECTED_TAG+">\n");
            
            bw.write("\t\t<"+AppSettingsConfig.UITAB_VERTEXSHAPEDEFAULT_TAG+">");
            bw.write(Integer.toString(SceneConfig.VERTEX_SHAPE_DEFAULT_INT));
            bw.write("</"+AppSettingsConfig.UITAB_VERTEXSHAPEDEFAULT_TAG+">\n");
            
            bw.write("\t\t<"+AppSettingsConfig.UITAB_VERTEXSIZEDEFAULT_TAG+">");
            bw.write(Double.toString(SceneConfig.VERTEX_SIZE_DEFAULT));
            bw.write("</"+AppSettingsConfig.UITAB_VERTEXSIZEDEFAULT_TAG+">\n");
            
            bw.write("\t\t<"+AppSettingsConfig.UITAB_VERTEXOPACITYDEFAULT_TAG+">");
            bw.write(Double.toString(SceneConfig.VERTEX_OPACITY));
            bw.write("</"+AppSettingsConfig.UITAB_VERTEXOPACITYDEFAULT_TAG+">\n");
            
            bw.write("\t\t<"+AppSettingsConfig.UITAB_VERTEXLABLEFONTSIZE_TAG+">");
            bw.write(Integer.toString(SceneConfig.VERTEX_LABEL_FONTSIZE_DEFAULT));
            bw.write("</"+AppSettingsConfig.UITAB_VERTEXLABLEFONTSIZE_TAG+">\n");
            
            
            // Write the Edge Properties
            bw.write("\t\t<"+AppSettingsConfig.UITAB_EDGECOLORPRIMARY_TAG+">");
            bw.write(SceneConfig.EDGE_COLOR_PRIMARYCOLOR);
            bw.write("</"+AppSettingsConfig.UITAB_EDGECOLORPRIMARY_TAG+">\n");
            
            bw.write("\t\t<"+AppSettingsConfig.UITAB_EDGECOLORSECONDARY_TAG+">");
            bw.write(SceneConfig.EDGE_COLOR_SECONDARYCOLOR);
            bw.write("</"+AppSettingsConfig.UITAB_EDGECOLORSECONDARY_TAG+">\n");
            
            bw.write("\t\t<"+AppSettingsConfig.UITAB_EDGECOLORTERTIARY_TAG+">");
            bw.write(SceneConfig.EDGE_COLOR_TERTIARYCOLOR);
            bw.write("</"+AppSettingsConfig.UITAB_EDGECOLORTERTIARY_TAG+">\n");
            
            bw.write("\t\t<"+AppSettingsConfig.UITAB_EDGEOPACITY_TAG+">");
            bw.write(Double.toString(SceneConfig.EDGE_OPACITY));
            bw.write("</"+AppSettingsConfig.UITAB_EDGEOPACITY_TAG+">\n");
            
            bw.write("\t\t<"+AppSettingsConfig.UITAB_EDGESTROKEWIDTH_TAG+">");
            bw.write(Double.toString(SceneConfig.EDGE_STROKEWIDTH));
            bw.write("</"+AppSettingsConfig.UITAB_EDGESTROKEWIDTH_TAG+">\n");
            
            // Write down the canvas properties
            bw.write("\t\t<"+AppSettingsConfig.UITAB_CANVASBGCOLLORDEFAULT_TAG+">");
            bw.write(SceneConfig.CANVAS_BACKGROUND_COLOR);
            bw.write("</"+AppSettingsConfig.UITAB_CANVASBGCOLLORDEFAULT_TAG+">\n");
            
            bw.write("\t\t<"+AppSettingsConfig.UITAB_CANVASDRAGRECTCOLOR_TAG+">");
            bw.write(SceneConfig.CANVAS_DRAGRECT_COLOR);
            bw.write("</"+AppSettingsConfig.UITAB_CANVASDRAGRECTCOLOR_TAG+">\n");
            
            bw.write("\t\t<"+AppSettingsConfig.UITAB_CANVASDRAGRECTOPACITY_TAG+">");
            bw.write(Double.toString(SceneConfig.CANVAS_DRAGRECT_OPACITY));
            bw.write("</"+AppSettingsConfig.UITAB_CANVASDRAGRECTOPACITY_TAG+">\n");
            
            bw.write("\t\t<"+AppSettingsConfig.UITAB_CANVASZOOMFACTOR_TAG+">");
            bw.write(Double.toString(SceneConfig.CANVAS_SCROLL_SCALEFACTOR));
            bw.write("</"+AppSettingsConfig.UITAB_CANVASZOOMFACTOR_TAG+">\n");
            
            // Close the UI Tab
            bw.write("\t</"+AppSettingsConfig.UITAB_TAG+">\n");
            
            // Start the Algorithm Parameter Tab
            bw.write("\t<"+AppSettingsConfig.ALGOPARAMTAB_TAG+">\n");
             // k means
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_KMEANSCLUSTERCOUNT_TAG, Integer.toString(CommunityMiningConfig.KMEANS_CLUSTERCOUNT_DEFAULT));
            //bw.write("\t\t<"+AppSettingsConfig.ALGOPARAMTAB_KMEANSCLUSTERCOUNT_TAG+">");
            //bw.write(Integer.toString(CommunityMiningConfig.KMEANS_CLUSTERCOUNT_DEFAULT));
            //bw.write("</"+AppSettingsConfig.ALGOPARAMTAB_KMEANSCLUSTERCOUNT_TAG+">\n");
            
            // Similar Attribute Value
            //TODO it has to be done with correct values
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_SAMEATTRIBUTEVALUE_MULTIPLEVALUES_TAG, Boolean.toString(CommunityMiningConfig.SAMEVALUEATTR_MULTIPLEVALUES));
            // Fast Modularity
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_FASTMODULARITY_ALGORITHM_TAG, CommunityMiningConfig.FM_METRIC_DEFAULT);
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_FASTMODULARITY_WEIGHTED_TAG, Boolean.toString(CommunityMiningConfig.FM_WEIGHTED));
            // Local Top Leader
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_LOCALTOPLEADERS_CLUSTERCOUNT_TAG, Integer.toString(CommunityMiningConfig.LOCALTOPLEADERS_CLUSTERCOUNT_DEFAULT));
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_LOCALTOPLEADERS_OUTLIERPERCENTAGE_TAG, Double.toString(CommunityMiningConfig.LOCALTOPLEADERS_OUTLIERPERCENTAGE_DEFAULT));
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_LOCALTOPLEADERS_HUBPERCENTAGE_TAG, Double.toString(CommunityMiningConfig.LOCALTOPLEADERS_HUBPERCENTAGE_DEFAULT));
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_LOCALTOPLEADERS_CENTERDISTANCE_TAG, Double.toString(CommunityMiningConfig.LOCALTOPLEADERS_CENTERDISTANCE_DEFAULT));
    
            // Local Community
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_LOCALCOMMUNITY_ALGORITHM_TAG, CommunityMiningConfig.LOCALCOMMUNITY_TYPE_DEFAULT);
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_LOCALCOMMUNITY_OVERLAP_TAG, Boolean.toString(CommunityMiningConfig.LOCALCOMMUNITY_OVERLAP));
            // RosvallInfoMap
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_ROSVALLINFOMAP_ATTEMPTS_TAG, Integer.toString(CommunityMiningConfig.ROSVALLINFOMAP_ATTEMPTS_DEFAULT));
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_ROSVALLINFOMAP_ISDIRECTED_TAG, Boolean.toString(CommunityMiningConfig.ROSVALLINFOMAP_ISDIRECTED));
            // Rosvall InfoMod
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_ROSVALLINFOMOD_ATTEMPTS_TAG, Integer.toString(CommunityMiningConfig.ROSVALLINFOMOD_ATTEMPTS_DEFAULT));
            // Dynamic Community Mining
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_DCMINING_SIMILARITYTHRESHOLD_TAG, Double.toString(CommunityMiningConfig.DCMINING_SIMILARITYTHRESHOLD_DEFAULT));
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_DCMINING_METRIC_TAG, (CommunityMiningConfig.DCMINING_METRIC_DEFAULT));
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_DCMINING_METHOD_TAG, (CommunityMiningConfig.DCMINING_METHOD_DEFAULT));
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_DCMINING_OVERLAP_TAG, Boolean.toString(CommunityMiningConfig.DCMINING_OVERLAP_DEFAULT));
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_DCMINING_HUBS_TAG, Boolean.toString(CommunityMiningConfig.DCMINING_HUBS_DEFAULT));
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_DCMINING_INSTABILITY_TAG, Double.toString(CommunityMiningConfig.DCMINING_INSTABILITY_DEFAULT));
            writerHelper(bw, AppSettingsConfig.ALGOPARAMTAB_DCMINING_HISTORY_TAG, Double.toString(CommunityMiningConfig.DCMINING_HISTORY_DEFAULT));
            
            
            // Close the Algorithm Parameter Tab
            bw.write("\t</"+AppSettingsConfig.ALGOPARAMTAB_TAG+">\n");
            
            
            // Close the root tag - Settings
            bw.write("</"+AppSettingsConfig.SETTINGS_ROOT_TAG+">");
            
            bw.close();
            fw.close();

        } catch (Exception ex) {
            System.out.println("AppSettingsWriter.WriteXML(): Exception");
            ex.printStackTrace();
        }
    }
    
    private static void writerHelper(BufferedWriter bw, String strTagValue, String configFileTagValue){
        try{
            bw.write("\t\t<"+strTagValue+">");
            bw.write(configFileTagValue);
            bw.write("</"+strTagValue+">\n");
        }catch (Exception ex) {
            System.out.println("AppSettingsWriter.WriteXML(): Exception");
            ex.printStackTrace();
        }
    }
    
    
}
