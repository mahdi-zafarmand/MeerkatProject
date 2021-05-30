/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import config.FilteringOperators;

/**
 *
 * @author aabnar
 */
public class FilteringComaprisonTest {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        boolean blnresult = false;
        blnresult = FilteringOperators.compare(FilteringOperators.LESSTHAN, null, null);
        System.out.println("FilteringComparisonTest.main()  LESSTHAN, null, null : " + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.LESSTHAN, null, "23");
        System.out.println("FilteringComparisonTest.main() LESSTHAN, null, \"23\" : " + blnresult);        
        blnresult = FilteringOperators.compare(FilteringOperators.LESSTHAN, "23", null);
        System.out.println("FilteringComparisonTest.main() LESSTHAN, \"23\", null : " + blnresult);        
        blnresult = FilteringOperators.compare(FilteringOperators.LESSTHAN, "12", "18");
        System.out.println("FilteringComparisonTest.main() LESSTHAN, \"12\", \"18\") : " + blnresult);        
        blnresult = FilteringOperators.compare(FilteringOperators.LESSTHAN, "18", "12");
        System.out.println("FilteringComparisonTest.main() LESSTHAN, \"18\", \"12\" : " + blnresult);        
        blnresult = FilteringOperators.compare(FilteringOperators.LESSTHAN, "a", "afra");
        System.out.println("FilteringComparisonTest.main() LESSTHAN, \"a\", \"afra\" : " + blnresult);        
        blnresult = FilteringOperators.compare(FilteringOperators.LESSTHAN, "afra", "a");
        System.out.println("FilteringComparisonTest.main() LESSTHAN, \"afra\", \"a\" : " + blnresult);        
        
        blnresult = FilteringOperators.compare(FilteringOperators.LEQ, null, null);
        System.out.println("FilteringComparisonTest.main()  : LEQ, null, null" + blnresult);        
        blnresult = FilteringOperators.compare(FilteringOperators.LEQ, null, "23");        
        System.out.println("FilteringComparisonTest.main()  : LEQ, null, \"23\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.LEQ, "23", null);        
        System.out.println("FilteringComparisonTest.main()  : LEQ, \"23\", null" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.LEQ, "22", "23");        
        System.out.println("FilteringComparisonTest.main()  : LEQ, \"22\", \"23\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.LEQ, "24", "23");   
        System.out.println("FilteringComparisonTest.main()  : LEQ, \"24\", \"23\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.LEQ, "23", "23");
        System.out.println("FilteringComparisonTest.main()  : LEQ, \"23\", \"23\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.LEQ, "a", "23");        
        System.out.println("FilteringComparisonTest.main()  : LEQ, \"a\", \"23\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.LEQ, "a", "afra");        
        System.out.println("FilteringComparisonTest.main()  : LEQ, \"a\", \"afra\"" + blnresult);
        
        blnresult = FilteringOperators.compare(FilteringOperators.GREATERTHAN, null, null);
        System.out.println("FilteringComparisonTest.main()  : GREATERTHAN, null, null" + blnresult);    
        blnresult = FilteringOperators.compare(FilteringOperators.GREATERTHAN, "23", null);        
        System.out.println("FilteringComparisonTest.main()  : GREATERTHAN, \"23\", null" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.GREATERTHAN, null, "23");        
        System.out.println("FilteringComparisonTest.main()  : GREATERTHAN,  null, \"23\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.GREATERTHAN, "23", "23");        
        System.out.println("FilteringComparisonTest.main()  : GREATERTHAN, \"23\", \"23\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.GREATERTHAN, "23", "22");        
        System.out.println("FilteringComparisonTest.main()  : GREATERTHAN, \"23\", \"22\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.GREATERTHAN, "23", "24");        
        System.out.println("FilteringComparisonTest.main()  : GREATERTHAN, \"23\", \"24\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.GREATERTHAN, "23", "-1");        
        System.out.println("FilteringComparisonTest.main()  : GREATERTHAN, \"23\", \"-1\"" + blnresult);
        
        blnresult = FilteringOperators.compare(FilteringOperators.GEQ, null, null);
        System.out.println("FilteringComparisonTest.main()  : GEQ, null, null" + blnresult);        
        blnresult = FilteringOperators.compare(FilteringOperators.GEQ, "23", null);
        System.out.println("FilteringComparisonTest.main()  : GEQ, \"23\", null" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.GEQ, null, "23");
        System.out.println("FilteringComparisonTest.main()  : GEQ, null, \"23" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.GEQ, "2", "23");
        System.out.println("FilteringComparisonTest.main()  : GEQ, \"2\", \"23\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.GEQ, "23", "2");
        System.out.println("FilteringComparisonTest.main()  : GEQ, \"23\", \"2\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.GEQ, "23", "23");
        System.out.println("FilteringComparisonTest.main()  : GEQ, \"23\", \"23\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.GEQ, "a", "23");
        System.out.println("FilteringComparisonTest.main()  : GEQ, \"a\", \"23\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.GEQ, "a", "afra");
        System.out.println("FilteringComparisonTest.main()  : GEQ, \"a\", \"afra\"" + blnresult);
        
        blnresult = FilteringOperators.compare(FilteringOperators.EQUAL, null, null);
        System.out.println("FilteringComparisonTest.main()  : EQUAL, null, null" + blnresult);        
        blnresult = FilteringOperators.compare(FilteringOperators.EQUAL, null, "23");
        System.out.println("FilteringComparisonTest.main()  : EQUAL, null, \"23\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.EQUAL, "23", null);
        System.out.println("FilteringComparisonTest.main()  : EQUAL, \"23\", null" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.EQUAL, "23", "23");
        System.out.println("FilteringComparisonTest.main()  : EQUAL, \"23\", \"23\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.EQUAL, "2", "23");
        System.out.println("FilteringComparisonTest.main()  : EQUAL, \"2\", \"23\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.EQUAL, "a", "afra");
        System.out.println("FilteringComparisonTest.main()  : EQUAL, \"a\", \"afra\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.EQUAL, "A", "A");
        System.out.println("FilteringComparisonTest.main()  : EQUAL, \"A\", \"A\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.EQUAL, "a", "A");
        System.out.println("FilteringComparisonTest.main()  : EQUAL, \"a\", \"A\"" + blnresult);
        
        blnresult = FilteringOperators.compare(FilteringOperators.NOTEQ, "2", "2");
        System.out.println("FilteringComparisonTest.main()  : NOTEQ, \"2\", \"2\"" + blnresult);        
        blnresult = FilteringOperators.compare(FilteringOperators.NOTEQ, "2", "3");
        System.out.println("FilteringComparisonTest.main()  : NOTEQ, \"2\", \"3\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.NOTEQ, "afra", "afra");
        System.out.println("FilteringComparisonTest.main()  : NOTEQ, \"afra\", \"afra\"" + blnresult);        
        blnresult = FilteringOperators.compare(FilteringOperators.NOTEQ, "sami", "afi");
        System.out.println("FilteringComparisonTest.main()  : NOTEQ, \"sami\", \"afi\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.NOTEQ, "afra", "afr");
        System.out.println("FilteringComparisonTest.main()  : NOTEQ, \"afra\", \"afr\"" + blnresult);        
        
        blnresult = FilteringOperators.compare(FilteringOperators.CONTAINS, "a", "afra");
        System.out.println("FilteringComparisonTest.main()  : CONTAINS, \"a\", \"afra\"" + blnresult);        
        blnresult = FilteringOperators.compare(FilteringOperators.CONTAINS, "afra", "b");
        System.out.println("FilteringComparisonTest.main()  : CONTAINS, \"afra\", \"b\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.CONTAINS, "123", "1");
        System.out.println("FilteringComparisonTest.main()  : CONTAINS, \"123\", \"1\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.CONTAINS, "-12345", "1");
        System.out.println("FilteringComparisonTest.main()  : CONTAINS, \"-12345\", \"1\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.CONTAINS, "afra", "a");
        System.out.println("FilteringComparisonTest.main()  : CONTAINS, \"afra\", \"a\"" + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.CONTAINS, "afra", "");
        System.out.println("FilteringComparisonTest.main()  : CONTAINS, \"afra\", \"\"" + blnresult);
        
        blnresult = FilteringOperators.compare(FilteringOperators.STARTSWITH, "afra", "afr");
        System.out.println("FilteringComparisonTest.main()  : STARTSWITH, \"afra\", \"afr\" " + blnresult);        
        blnresult = FilteringOperators.compare(FilteringOperators.STARTSWITH, "afra", "abnar");
        System.out.println("FilteringComparisonTest.main()  : STARTSWITH, \"afra\", \"abnar\" " + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.STARTSWITH, "afra123", "afra1");
        System.out.println("FilteringComparisonTest.main()  : STARTSWITH, \"afra123\", \"afra1\" " + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.STARTSWITH, "123afra", "123");
        System.out.println("FilteringComparisonTest.main()  : STARTSWITH, \"123afra\", \"123\" " + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.STARTSWITH, "123afra", "");
        System.out.println("FilteringComparisonTest.main()  : STARTSWITH, \"123afra\", \"\" " + blnresult);

        blnresult = FilteringOperators.compare(FilteringOperators.ENDSWITH, null, null);
        System.out.println("FilteringComparisonTest.main()  : " + blnresult);
        blnresult = FilteringOperators.compare(FilteringOperators.ENDSWITH, null, null);
        System.out.println("FilteringComparisonTest.main()  : " + blnresult);
        
        blnresult = FilteringOperators.compare(FilteringOperators.EMPTY, "meerkat", "");
        System.out.println("FilteringComparisonTest.main()  : EMPTY, \"meerkat\", null " + blnresult);        
        blnresult = FilteringOperators.compare(FilteringOperators.EMPTY, " ", "");
        System.out.println("FilteringComparisonTest.main()  : EMPTY, \"\", null " + blnresult);
        
        blnresult = FilteringOperators.compare(FilteringOperators.NOTEMPTY, "-908912#$%", "");
        System.out.println("FilteringComparisonTest.main()  : NOTEMPTY, \"-908912#$%\", null " + blnresult);        
        blnresult = FilteringOperators.compare(FilteringOperators.NOTEMPTY, " ", "");
        System.out.println("FilteringComparisonTest.main()  : NOTEMPTY, \"\", null " + blnresult);
    }
}
