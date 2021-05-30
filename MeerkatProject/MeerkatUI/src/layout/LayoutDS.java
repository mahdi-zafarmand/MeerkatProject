/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Talat-AICML
 */
public class LayoutDS {
    
    private Map<String, LayoutGroup> mapLayoutGroups ;
    
    public Map<String, LayoutGroup> getLayoutGroups() {
        return this.mapLayoutGroups ;
    }
    
    public LayoutDS() {
        mapLayoutGroups = new HashMap<>();
    }
}
