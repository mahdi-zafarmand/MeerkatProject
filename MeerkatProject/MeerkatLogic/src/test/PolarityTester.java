/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

//import algorithm.textanalysis.sentimentanalysis.PolarityAnalyzer;
//import algorithm.textanalysis.sentimentanalysis.feedback.LexiconUpdaterForSentences;
import datastructure.core.text.HNode;
import datastructure.core.text.MsgNode;
import main.meerkat.MeerkatBIZ;

/**
 *
 * @author ameneh
 */
public class PolarityTester {

    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        MeerkatBIZ meerkatApplication = MeerkatBIZ.getMeerkatApplication();

        
        String content = "A test of success or failure of system in application"
                + " for people of world to mine their data and knowledge to a "
                + "better understanding and higher performance";
        
        HNode root = new HNode(0, "parent", null);
        MsgNode msg = new MsgNode(0, "title", root, content);
//        MsgNode child1 = new MsgNode("ch1", root);
//        child1.setContent("A door was knocked twice. Insider asked who's there?\n"
//                + "Answer : Thats me Oppurtunity.\n"
//                + "Insider : You are wrong because oppurtunity never knocks twice.\n"
//                + "That's Confidence.");
//        MsgNode child2 = new MsgNode("ch2", root);
//        child2.setContent("Here is a mouthwatering smorgasbord of stories with"
//                + "food in the starring role, by a range of masters of fiction");
//        MsgNode child11 = new MsgNode("ch11", root);
//        child11.setContent("Nice people are like wind,\n"
//                + "You never know what is in their heart,\n"
//                + "You can only feel their presence in sincerity.");
/*
        PolarityAnalyzer analyzer = new PolarityAnalyzer(msg);
        analyzer.run();
//        PolarityAnalyzer analyzer1 = new PolarityAnalyzer(child1);
//        analyzer1.run();
//        PolarityAnalyzer analyzer2 = new PolarityAnalyzer(child2);
//        analyzer2.run();
//        PolarityAnalyzer analyzer11 = new PolarityAnalyzer(child11);
//        analyzer11.run();
        testFeedback(root);
*/
    }

    static void test() {
        String content = "I think the cast of the film did a great job and"
                + " made an outstanding memory in the film industry. I will"
                + " definitely suggest it to all my friends. The visual "
                + "effects were incredible too.";
        MsgNode<String, String> node = new MsgNode<>(0, null, null,
                null, null, null, content);
        Long time = (Long) System.currentTimeMillis();
        /*PolarityAnalyzer pAnalyzer = new PolarityAnalyzer(node);
        pAnalyzer.run();
        while (!pAnalyzer.isDone());
        Long duration = (Long) System.currentTimeMillis() - time;
        System.out.println("time: " + duration);
        Double pol = PolarityAnalyzer.getPolarityDoc(node).getPolarity();
        System.out.println(pol);
        System.out.println(pAnalyzer.getHTML());
*/
    }

    static void testFeedback(HNode msgNode) {
        /*LexiconUpdaterForSentences updater = LexiconUpdaterForSentences.getInstance();
        updater.trigger();
*/
    }

}
