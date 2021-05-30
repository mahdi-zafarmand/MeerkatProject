package algorithm.graph.communitymining;

import config.CommunityMiningParameters;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import config.MeerkatSystem;
import config.Parameter;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import java.util.Map;

/**
 * Generates explicit communities based on the same attribute value of a chosen
 * attribute
 *
 * @author xl16
 * @param <V>
 * @param <E>
 *
 */
public class AttributeValueMiner<V extends IVertex, E extends IEdge<V>>
        extends Miner<V, E> {

    /**
     *
     */
    public static final String STR_NAME = "Same Attribute Value";

    private static final String DEFAULT_SEPARATOR = ",";
    private static final boolean DEFAULT_MULTI_VALUES = false;
    
    // Algorithm Parameters
    private String chosenAttribute;
    private boolean ifMultipleValues = DEFAULT_MULTI_VALUES;
    private String separatorText = DEFAULT_SEPARATOR;

//    private HashMap<Integer, List<V>> hmpCommunities = new HashMap<>();

    /**
     * Constructor
     * Version: 2.0
     * @param pIGraph
     *          dynamic graph (not null)
     * @param pstrParameters
     *          Array of String of all required parameters.
     *          - pstrParameters[i] = The ith parameter and its value --> parametername:value
     * 
     * EDIT HISTORY
     * Date         Author          Description
     * 2016 Apr 20  Afra            put all parameters in a one dimensional array (both param name and value in one string)
     * 2016 Apr 19  Afra            put all parameters in a 2D array of Strings
     * @param parameters
     */
    public AttributeValueMiner(IDynamicGraph<V, E> pIGraph,
            TimeFrame tf,
            String[] parameters) {
        super(pIGraph, tf);
        
        List<Parameter> lstParams = 
                CommunityMiningParameters.getParameters(this.getClass().getSimpleName());
        
        if(parameters != null) {
            for (String strP : parameters) {
                for (Parameter p : lstParams) {
                    if (strP.startsWith(p.key)) {
                        String value = strP.substring(p.key.length()+1).trim();
                        if (p.key.equals(CommunityMiningParameters.ATTRIBUTEVALUEMINER_CHOSENATTRIBUTE)) {
                            this.chosenAttribute = value;
                        } else if (p.key.equals(CommunityMiningParameters.ATTRIBUTEVALUEMINER_MULTIPLEVALUE)) {
                            this.ifMultipleValues = Boolean.parseBoolean(value);
                        } else if (p.key.equals(CommunityMiningParameters.ATTRIBUTEVALUEMINER_SEPARATOR)) {
                            this.separatorText = value; 
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     *
     */
    @Override
    public void run() {
        mineGraph();
        if(!running){
                return;
        }
        updateDataStructure();
        blnDone = true;
    }

    /**
     * MethodName: mineGraph
     * Description: checks chosen attribute value for each node and clusters vertices of same value together. In case of multiple value for a node, that node belongs to multiple communities.
     * Version: 2.0
     * Author: previous Meerkat
     * 
     * EDIT HISTORY
     * DATE         AUTHOR      DESCRIPTION
     * 2016 Apr 29  Afra        Chaged the whole implementation. Previous one had bugs, was too complicated and long and consumed lots of time.
     */
    public void mineGraph() {
        
        int comId = 0;
        Map<String, Integer> mapValue2ComId = new HashMap<>();
        
        for (V v : dynaGraph.getVertices(tf)) {
            if(!running){
                return;
            }
            String val = null;
            if (v.getUserAttributer().getAttributeNames()
                    .contains(chosenAttribute)) {
                val = v.getUserAttributer()
                        .getAttributeValue(chosenAttribute, tf)
                        .toLowerCase().trim();
            } else if (v.getSystemAttributer().getAttributeNames()
                    .contains(chosenAttribute)) {
                val = v.getSystemAttributer()
                        .getAttributeValue(chosenAttribute, tf);
                
            }
            if (val != null) {
                String[] values = val.split(separatorText);
                for (String value : values) {
                    if (value.trim().isEmpty()) {
                        continue;
                    }
                    if (mapValue2ComId.keySet().contains(value)) {
                        hmpCommunities.get(mapValue2ComId.get(value)+"")
                                .add(v.getId());
                    } else {
                        mapValue2ComId.put(value, comId);
                        hmpCommunities.put(comId + "", new LinkedList<>());
                        hmpCommunities.get(comId + "").add(v.getId());
                        comId++;
                    }
                }
            }
        } 
    }

    /**
     *
     * @return
     */
    public boolean requiresParameters() {
        return true;
    }

    /**
     *
     * @return
     */
    public String[] getParameters() {
        String[] paramValues = new String[3];
        paramValues[0] = chosenAttribute;
        paramValues[1] = ifMultipleValues + "";
        paramValues[2] = separatorText;
        
        return paramValues;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return STR_NAME;
    }
}
