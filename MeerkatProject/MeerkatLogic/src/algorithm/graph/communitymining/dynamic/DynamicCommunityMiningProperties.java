/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.communitymining.dynamic;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 *
 * @author aabnar
 */
public class DynamicCommunityMiningProperties {
    
    public static final String algorithm = "algorithm";
    public static final String algName = "algName";
    public static final String facetnetLocation = "facetnetLocation";
    public static final String facetnetMFile = "facetnetMFile";
    public static final String alpha = "alpha";
    public static final String maxNbCluster = "maxNbCluster";
    public static final String nbCluster = "nbCluster";
    public static final String similarityThreshold = "similarityThreshold";
    public static final String metric = "metric";
    public static final String method = "method";
    public static final String overlap = "overlap";
    public static final String hub = "hub";
    public static final String history = "history";
    public static final String instability = "instability";
    public static final String dataset_name = "datasetName";
    public static final String dataset_location = "datasetLocation";
    public static final String startVertices = "startVertices";
    public static final String dataset = "dataset";
    public static final String properties = "properties";

    /*
     * read properties required to run dynamic community mining experiment.
     * 
     * @return properties object containing all required properties and their
     * value
     * 
     * @throws IOException
     */
    public static Properties readPropertiesFile(String propertiesFile)
                    throws IOException {
        System.out.println("Read properties from file ...");
        Properties prop = new Properties();
        // load a properties file
        prop.load(new FileInputStream(propertiesFile));

        return prop;

    }

    public static Properties creatPropertiesFile(String args[])
                    throws IllegalArgumentException, IllegalAccessException {
        System.out.println("Create properties from args ...");
        Properties prop = new Properties();

        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) == '-') {
                String arg = args[i].substring(1, args[i].length());

                if (!isArgumentExist(arg)) {
                    Field[] fields = DynamicCommunityMiningProperties.class
                                    .getFields();

                    throw new IllegalArgumentException("Not a valid option: "
                                    + args[i] + "\n Acceptable options are: " + fields);
                }

                else {
                    if (args.length - 1 == i || args[i + 1].charAt(0) == '-')
                            throw new IllegalArgumentException(
                                            "Expected argument after: " + args[i]);
                    prop.put(arg, args[i + 1]);
                    i++;
                }

            }

            else {
                throw new IllegalArgumentException("Expected - before option: "
                                    + args[i]);
            }
        }

        return prop;
    }

    private static boolean isArgumentExist(String arg)
                    throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = DynamicCommunityMiningProperties.class.getFields();
        for (Field field : fields) {
            if (arg.equals(field.get(null).toString()))
                return true;
        }
        return false;
    }
}
