package algorithm.graph.communitymining;

import datastructure.core.graph.classinterface.*;
import algorithm.GraphAlgorithm;
import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import io.graph.writer.CommunityWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author aabnar
 * @param <V>
 * @param <E>
 */
public abstract class Miner<V extends IVertex, E extends IEdge<V>> 
                                                    extends GraphAlgorithm<V,E>{

    /**
     *
     */
    public HashMap<String,List<Integer>> hmpCommunities;  // ComId --> List[VertexId]

    /**
     *
     * @param pIGraph
     * @param tf
     */
    public Miner(IDynamicGraph<V,E> pIGraph, TimeFrame tf) {
        super(pIGraph, tf);
        hmpCommunities = new HashMap<>();
    }

    //public boolean requiresParameters();

    /**
     *
     * @return
     */
    
    @Override
    public synchronized boolean updateDataStructure() {
        //calculate color of communities here and update them in data strucutre
        dynaGraph.calculateCommunityColors(hmpCommunities.keySet(), tf);
        
        //update sys:com and sys:color of vertices that belong to communties
        for (String comId : hmpCommunities.keySet()) {
            
            String strcolorCommuity = dynaGraph.getCommunityColor(comId, tf);
            
            for (Integer vId : hmpCommunities.get(comId)) {
                
                
                dynaGraph.getVertex(vId).getSystemAttributer().addAttributeValue(
                                MeerkatSystem.COMMUNITY, 
                        comId, 
                        dtCallTime,
                        tf);
                //update sys:color here since we are done mining
                dynaGraph.getVertex(vId).getSystemAttributer().addAttributeValue(
                                MeerkatSystem.COLOR, 
                        strcolorCommuity, 
                        dtCallTime,
                        tf);
            }
        }
        
        System.out.println("Miner.updateDataStructure()");
        return true;
    }

    /**
     *
     */
    @Override
    public void writeToFile() {
        String filename = MeerkatSystem.OUTPUT_DIRECTORY+
                    "graph"+
                    dynaGraph.getGraph(tf).getId() +
                    ".community";
        
        CommunityWriter.write(filename, hmpCommunities);
    }
    
    protected File getTempExecFIle(String pstringalgoName, String pstringExecLocation){
        
        InputStream inputStream = null;
	OutputStream outputStream = null;
        File tempFile = null;

	try {
		// read this file into InputStream
		inputStream = this.getClass().getClassLoader().getResourceAsStream(pstringExecLocation);

		// write the inputStream to a FileOutputStream
                tempFile = new File("temp/"+ pstringalgoName);
		outputStream =
                    new FileOutputStream(tempFile);

		int read = 0;
		byte[] bytes = new byte[1024];

		while ((read = inputStream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}

	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (outputStream != null) {
			try {
				// outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
        tempFile.setExecutable(true);
        tempFile.deleteOnExit();
    
    return tempFile;
    }
}
