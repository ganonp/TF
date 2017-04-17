package test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import java.io.*;

@RunWith(Suite.class)
@SuiteClasses({CompareDocsTest.class, DocTermFrequencyTest.class})
public class TestSuite {
    public static String directory;

    @BeforeClass
    public static void setUp(){
        ClassLoader classLoader = TestSuite.class.getClassLoader();
        File classpathRoot = new File(classLoader.getResource("").getPath());
        directory = classpathRoot.getPath() + "\\TestText\\";
        try{
            File f0 = new File(directory);
            File f1 = new File(directory + "5bobs5dogs10hamburgers");
            File f2 = new File(directory + "8bobs4logs3blobs");
            f0.mkdir();
            f1.createNewFile();
            f2.createNewFile();
        }catch(IOException e){
            System.out.println();
        }

        try (Writer doc1Writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(directory + "5bobs5dogs10hamburgers"), "utf-8"))) {
            doc1Writer.write("bobs bobs, dogs hamburgers hamburgers hamburgers. hamburgers, hamburgers dogs dogs bobs bobs \n");
            doc1Writer.write("hamburgers bobs hamburgers. \n");
            doc1Writer.write("hamburgers hamburgers dogs. dogs, \n");
            doc1Writer.write("hamburgers \n");
            doc1Writer.close();
        }catch(IOException e){

        }
        try (Writer doc2Writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(directory + "8bobs4logs3blobs"), "utf-8"))) {
            doc2Writer.write("bobs blobs, logs bobs \n");
            doc2Writer.write("logs bobs? bobs bobs \n");
            doc2Writer.write("bobs logs bobs \n");
            doc2Writer.write("bobs blobs \n");
            doc2Writer.write("logs blobs' \n");
            doc2Writer.close();
        }catch(IOException e){

        }
    }

    @AfterClass
    public static void tearDown(){
        new File(directory + "5bobs5dogs10hamburgers").delete();
        new File(directory + "8bobs4logs3blobs").delete();
        new File(directory).delete();

    }

}