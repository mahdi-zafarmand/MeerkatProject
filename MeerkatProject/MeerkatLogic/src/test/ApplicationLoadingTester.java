package test;

//import algorithm.textanalysis.emotionanalysis.EmotionAnalyzer;
import datastructure.core.text.MsgNode;
import main.meerkat.MeerkatBIZ;

/**
 *
 * @author aabnar
 */
public class ApplicationLoadingTester {

    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
		//MeerkatBIZ meerkatApplication = MeerkatBIZ.getMeerkatApplication();
                //test();
		MeerkatBIZ meerkatApplication = MeerkatBIZ.getMeerkatApplication();
		
		int projectId = meerkatApplication.createNewProject();
		int graphid = meerkatApplication.getProject(projectId).
					loadFile(".meerkat", 
                                                "resources/files/demo.meerkat", 
                                                0);
		System.out.println(graphid);
                
                test();
		
	}
        
        static void test(){
            String content2 = "Isn't it crazy how ugly Halle berry can look "
                    + "without makeup #watchinglosingisaiah #greatmovie";
            String content = "i got this present from my mother that i did not"
                    + " expect. i am surprised of what she did.";
            MsgNode<String, String> node = new MsgNode<>(0, null, null,
                                                null, null, null, content);
            Long time = (Long)System.currentTimeMillis();
            /*EmotionAnalyzer eAnalyzer = new EmotionAnalyzer(node, false);
            eAnalyzer.run();
            while ( !eAnalyzer.isDone());
            Long duration = (Long)System.currentTimeMillis() - time;
            System.out.println("time: " + duration);
            System.out.println(eAnalyzer.getResults());
*/
        }
        
}
