/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core.graph.classinterface;

import datastructure.core.TimeFrame;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author AICML Administrator
 */
public interface ICommunityColors {

    void calculateCommunityColor(TimeFrame tf, Set<String> setCommunities);

    String getCommunityColor(String strCommunity, TimeFrame tf);

    Map<String, String> getMapCommunityColors(TimeFrame tf);

    void setCommunityColor(String strCommunity, String strColor, TimeFrame tf);

    public void addTimeFrame(TimeFrame pTimeFrame);

    public void resetGlobalCommunityColorMap();

    public void setCommunityColorMap(TimeFrame tf, Map<String, String> mapCommunityColorMap);

    public void setGlobalCommunityColorMap(Map<String, String> mapGloablCommunityColor);
    
    public Map<String,String> getMapGloablCommunityColor();
    
}
