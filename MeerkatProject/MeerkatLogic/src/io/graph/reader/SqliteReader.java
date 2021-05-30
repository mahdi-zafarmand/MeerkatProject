package io.graph.reader;

import config.MeerkatSystem;
import java.util.ArrayList;
import java.util.List;

import datastructure.core.DynamicAttributable.DynamicAttributer.GenericDynamicAttributer;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import datastructure.core.graph.impl.*;
import datastructure.general.DynamicArray;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * SqliteReader created on Date 2017-02-xx. A static graph reader that 
 * specifically reads SQLite database graph of the type (.db) format.
 * @version: 1.0
 * @author sankalp
 */
public class SqliteReader extends GraphReader{
    
    private int vertexCount = 0;
    private int edgeCount = 0;
    
    int minVID = Integer.MAX_VALUE;
    int maxVID = 0;
    private IVertex[] hmpAllVertices;
    
    @SuppressWarnings("FieldMayBeFinal")
    private TimeFrame tf = TimeFrame.DEFAULT_TIMEFRAME;
    
    private DynamicGraph<IVertex, IEdge<IVertex>> dynaGraph;
    private DynamicArray<Map<IVertex,IEdge>> edges;
    
    /**
     * @param pstrPath2FileName
     */
    public SqliteReader(String pstrPath2FileName){
        super(pstrPath2FileName);
    }
    
    /**
     * MethodName: loadFile()
     * Description: prepares the dynamic graph , uses method calls to read
     * vertices and edges from .db file.
     * @version: 1.0
     * @author: sankalp
     */
    
    @Override
    public DynamicGraph<IVertex,IEdge<IVertex>> loadFile() {
        System.out.println("path to file: " + strFilePath);
        firstRun();
        
        try{
        dynaGraph 
               = new DynamicGraph<>(vertexCount , edgeCount);

        IStaticGraph<IVertex,IEdge<IVertex>> igraph 
            = new StaticGraph<>(vertexCount , edgeCount);
        
        edges = new DynamicArray<>(edgeCount);

        dynaGraph.addGraph(tf, igraph);
        
        if(maxVID - minVID>0)
            hmpAllVertices = new IVertex[maxVID - minVID + 1];
        
        
        
        System.out.println("min, max vertex id :" + minVID + " "+maxVID);
        }catch(Exception e){
            System.out.println("io.graph.reader.SqliteReader.loadFile() Exception:"+ e.getMessage());
        }

        readVertex();
        readEdge(false);
 
        return dynaGraph;
    }
    
    /**
     * MethodName: readVertex()
     * Description: reads the vertices from .db file
     * @version: 1.0
     * @author: sankalp
     */
    private void readVertex() {       
        
        try {
            Connection conn =  DriverManager.getConnection("jdbc:sqlite:"+strFilePath);
            Statement statement1 = conn.createStatement();
            ResultSet resultset1 = statement1.executeQuery("SELECT * from Node");
            
            if(hmpAllVertices.length>0){
            while(resultset1.next()){
                
                String vID = resultset1.getString(1);
                int intVID = Integer.parseInt(vID);
                IVertex ivtNewV = null;
                if (hmpAllVertices[intVID - minVID] == null) {
                    ivtNewV = new Vertex();
                    hmpAllVertices[intVID - minVID] = ivtNewV;
                }
                
                ArrayList<String> column_names = new ArrayList<>();
                ArrayList<String> column_values = new ArrayList<>();
                
                ResultSetMetaData rsmd1 = resultset1.getMetaData();
                column_names.add(rsmd1.getColumnName(1));
                column_names.add(rsmd1.getColumnName(2));
                String node_id =  resultset1.getString(1);
                String tablename =  resultset1.getString(2);                
                
                column_values.add(node_id);
                column_values.add(tablename);
                
                Statement statement2 = conn.createStatement();
                ResultSet resultset2 = statement2.executeQuery("SELECT * from "
                        +tablename+" where id=="+node_id);
                ResultSetMetaData rsmd2 = resultset2.getMetaData();
                int counter = rsmd2.getColumnCount();
                
                for(int i = 1; i <= counter; i++){
                     if(!"id".equals(rsmd2.getColumnName(i))){
                         column_values.add(resultset2.getString(i));
                         column_names.add(rsmd2.getColumnName(i));
                     }                    
                }
                 
                Set<Integer> sysAttrNames = new HashSet<>();
                List<String> attrNames = new ArrayList<>();

                int count = column_names.size();
                String columnName[] = new String[count];

                for(int i = 0; i < count; i++){
                    columnName[i] = column_names.get(i);
                    if("id".equals(columnName[i]) || 
                            MeerkatSystem.isSystemAttribute(columnName[i].toUpperCase())){
                       sysAttrNames.add(i);
                    }
                    attrNames.add(columnName[i]);
                }

                String[] cells = column_values.toArray(new String[column_values.size()]);

                GenericDynamicAttributer attributer = new GenericDynamicAttributer();
                for (int i = 0; i < cells.length; i++) {
                    if (cells[i] != null) {
                        if (attrNames.get(i).equals(GraphMLReader.id) ||
                                attrNames.get(i).equals(GMLReader.strId)) {
                            ivtNewV.getUserAttributer().
                                addAttribute(MeerkatSystem.FILE_ID, 
                                ivtNewV.getId() + "",
                                tf);
                        } else if (sysAttrNames.contains(i)) {
                            ivtNewV.getSystemAttributer()
                                .addAttributeValue(attrNames.get(i), 
                                    cells[i],
                                    new Date(),
                                    TimeFrame.DEFAULT_TIMEFRAME);
                        } else {
                            ivtNewV.getUserAttributer()
                                .addAttribute(attrNames.get(i), 
                                    cells[i],
                                    TimeFrame.DEFAULT_TIMEFRAME);
                        }
                    }
                }
                
                dynaGraph.addVertex(ivtNewV, tf);
                edges.add(ivtNewV.getId(), new HashMap<>());
                
                if (!ivtNewV.getAttributeNames().contains(MeerkatSystem.FILE_ID)){
                      ivtNewV.getUserAttributer().
                          addAttribute(MeerkatSystem.FILE_ID, 
                                  ivtNewV.getId() + "",
                                  tf);
                    }

            }
            }             
        }catch (SQLException e){
            System.out.println(e.toString());
            }       
    }
    
    /**
     * MethodName: readEdge()
     * Description: reads the edges from .db file
     * @version: 1.0
     * @author: sankalp
     */
    private void readEdge(boolean blnDirected){
        
        try {
            if(hmpAllVertices.length>0){
            
            Connection conn =  DriverManager.getConnection("jdbc:sqlite:"+strFilePath);
            Statement statement1 = conn.createStatement();
            ResultSet resultset1 = statement1.executeQuery("SELECT * from Edge");
           
            while(resultset1.next()){
                ArrayList<String> column_names = new ArrayList<>();
                ArrayList<String> column_values = new ArrayList<>();
                
                ResultSetMetaData rsmd1 = resultset1.getMetaData();
                int countcolumn = rsmd1.getColumnCount();
                
                for(int i = 1; i <= countcolumn; i++){
                    column_names.add(rsmd1.getColumnName(i));
                    column_values.add(resultset1.getString(i));
                }
                
                String strFileSrc =  resultset1.getString(2);
                int intFileSrc = Integer.parseInt(strFileSrc);
                String strFileDest =  resultset1.getString(3);
                int intFileDest = Integer.parseInt(strFileDest);
                
                IVertex vtxSource = hmpAllVertices[intFileSrc - minVID];
                IVertex vtxDestination = hmpAllVertices[intFileDest - minVID];
                if (vtxSource == null || vtxDestination == null) {
                    return;
                }
                
                double weight = 1.0;
                
                IEdge<IVertex> iedgeNew = null;
                
                if (edges.get(vtxSource.getId()).containsKey(vtxDestination)) {
                iedgeNew = edges.get(vtxSource.getId()).get(vtxDestination);
                } else {
                    iedgeNew = new Edge<>(vtxSource,
                            vtxDestination,
                            blnDirected,
                            weight);
                    edges.get(vtxSource.getId()).put(vtxDestination, iedgeNew);
                }
               
                // Make the defaults of an Edge to be undirected and not-predicted
                iedgeNew.getSystemAttributer().addAttributeValue(MeerkatSystem.PREDICTED, "false", new Date(), tf);
                iedgeNew.getSystemAttributer().addAttributeValue(MeerkatSystem.DIRECTED, "false", new Date(), tf);
                
                for (int i = 0; i < column_names.size(); i++) {
                    
                    String strKey = column_names.get(i);
                    String strValue = column_values.get(i);

                    if (strKey != null) {
                        boolean blnSysAtt = false;
                        for (String s : MeerkatSystem.SystemAttributes) {
                            if (strKey.toUpperCase().equals(s)) {
                                iedgeNew.getSystemAttributer().addAttributeValue(
                                    s,
                                    strValue,
                                    new Date(),
                                    tf);

                                blnSysAtt = true;
                                break;
                            }
                        }
                        if (strKey.equalsIgnoreCase("weight")) {
                            weight = Double.parseDouble(strValue);
                            iedgeNew.setWeight(weight);
                        } 
                        if (!blnSysAtt) {
                            iedgeNew.getUserAttributer().addAttribute(strKey,
                                    strValue,
                                    tf);
                        }
                    }
                }
            dynaGraph.addEdge(iedgeNew, tf);

            }
        }}catch(NumberFormatException | SQLException e){
            System.out.println("SQLException :"+ e.getMessage());
        }   
    }
    
    /**
     * MethodName: firstRun()
     * Description: counts the number of vertices and edges from .db file.
     * @version: 1.0
     * @author: sankalp
     */
    private void firstRun(){
        System.out.println("FIRST RUN");
        
        Connection connection = null; 
        ResultSet resultSet = null;
        ResultSet secondResultSet = null;
        Statement statement = null;
        Statement SecondStatement = null;

        try 
        {  
            Class.forName("org.sqlite.JDBC");  
            connection = DriverManager.getConnection("jdbc:sqlite:"+strFilePath);
            statement = connection.createStatement();
            resultSet = statement  
                    .executeQuery("SELECT * FROM Node");
            
            while (resultSet.next()) 
            {  
                vertexCount++;               
                String strId = resultSet.getString(1);
                int intId = Integer.parseInt(strId);
                if (minVID > intId) {
                    minVID = intId;
                } else if (maxVID < intId) {
                    maxVID = intId;
                }
            } 
            
            System.out.println("vertex count :"+ vertexCount);
            SecondStatement = connection.createStatement();  
            secondResultSet = SecondStatement.executeQuery("SELECT count(*) FROM edge");
            
            while(secondResultSet.next()){
                edgeCount=Integer.parseInt(secondResultSet.getString(1));
                System.out.println("edge count :"+ edgeCount);
            }
        } 
        catch (ClassNotFoundException | NumberFormatException | SQLException e) 
        {  
            System.out.println("SQLException "+e.getMessage());
        }
        finally 
        {  
            try 
            {  
                if(resultSet != null && statement != null && secondResultSet 
                        != null && SecondStatement != null && connection != null){
                resultSet.close();  
                statement.close();
                secondResultSet.close();
                SecondStatement.close();
                connection.close();  
            } }
            catch (SQLException e) 
            {  
                System.out.println("SQLException "+e.getMessage());
            }  
        }
    }

    
}
