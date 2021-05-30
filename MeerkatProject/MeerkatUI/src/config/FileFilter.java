
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import config.GraphConfig.GraphType;
import meerkat.Utilities;

/**
 *  Class Name      : FileFilter
 *  Created Date    : 2015-07-16
 *  Description     : Data Structure to store the different file filters available
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class FileFilter {
    
    /* VARIABLES */
    private String strFileExtension ; // Extension of the File (eg: .meerkat, .json)
    private String strFileDisplay ; // The Display Name of the kind of files (eg: Meerkat Files, JSON Files)
    private String strFilterID ; // The ID that would map to the Reader class for the particular file type
    private GraphType enmGraphType; // The type of the graph that would correspond to the fileformat
    
    /* GETTERS AND SETTERS */
    public String getFileExtension() {
        return this.strFileExtension; 
    }
    protected void setFileExtension(String pstrFileExtension) {
        this.strFileExtension = pstrFileExtension;
    }
    
    public String getFileDisplay() {
        return this.strFileDisplay;
    }
    protected void setFileDisplay(String pstrFileDisplay) {
        this.strFileDisplay= pstrFileDisplay;
    }
    
    public String getReaderID() {
        return this.strFilterID;
    }
    protected void setReaderID(String pstrID) {
        this.strFilterID = pstrID;
    }
    
    public GraphType getGraphType() {
        return enmGraphType;
    }
    
    /* CONSTRUCTORS */
    public FileFilter(String pstrFilterDisplay, String pstrFileExtension, String pstrID, int pintGraphType) {
        this.strFileDisplay = pstrFilterDisplay ;
        this.strFileExtension = pstrFileExtension ;
        this.strFilterID = pstrID;
        enmGraphType = Utilities.getGraphType(pintGraphType);
    }
}
