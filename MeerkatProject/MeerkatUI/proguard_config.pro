-injars dist/MeerkatUI.jar
-outjars dist/o_MeerkatUI.jar
-injars dist/lib/MeerkatAPI.jar
-outjars dist/lib/o_MeerkatAPI.jar
-injars dist/lib/MeerkatLogic.jar
-outjars dist/lib/o_MeerkatLogic.jar


-libraryjars <java.home>/lib/rt.jar
-libraryjars <java.home>/lib/ext/jfxrt.jar
-libraryjars dist/lib

-useuniqueclassmembernames
-dontnote
-dontshrink
-dontoptimize
-flattenpackagehierarchy ''
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,LocalVariable*Table,*Annotation*,Synthetic,EnclosingMethod
-adaptresourcefilecontents **.fxml,**.properties,META-INF/MANIFEST.MF

# keep all class names -keepnames class ** {*;}

-keep class analysisscreen.AnalysisScreen, analysismenubar.*,accordiontab.*,io.graph.reader.*,algorithm.graph.communitymining.*,
algorithm.graph.layout.algorithms.*, algorithm.graph.metric.*, io.graph.writer.*{ public *; } 

-keepclassmembernames class * {
    @javafx.fxml.FXML *;
}

-keepclassmembers class * extends java.lang.Enum {
    <fields>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep - Applications. Keep all application classes, along with their 'main'
# methods.
-keepclasseswithmembers public class com.javafx.main.Main, meerkat.MeerkatApplication {
    public static void main(java.lang.String[]);
}

