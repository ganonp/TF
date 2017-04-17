package test;

import org.junit.*;
import main.tf.DocTermFrequency;
import java.io.*;

/**
 * Created by Ganon on 4/14/2017.
 */

public class DocTermFrequencyTest {
    String directory;

    @Test
    public void countWords() {

        DocTermFrequency test1 = new DocTermFrequency( TestSuite.directory,"5bobs5dogs10hamburgers");
        DocTermFrequency.initializeWordIndex(new String[]{"dogs", "bobs", "hamburgers"});
        test1.countWords();
        Assert.assertTrue(test1.getRatio("dogs") == 5d/20d);
        Assert.assertTrue(test1.getRatio("bobs") == 5d/20d);
        Assert.assertTrue(test1.getRatio("hamburgers") == 10d/20d);
    }

    @Test
    public void countWords2() {

        DocTermFrequency test1 = new DocTermFrequency(new String[]{"dogs", "bobs", "hamburgers"}, TestSuite.directory,"1000dogs1000000words");
        DocTermFrequency.initializeWordIndex(new String[]{"dogs", "bobs", "hamburgers"});

        test1.countWords();
        Assert.assertTrue(test1.getRatio("dogs") == 1000d/1000000d);

    }

    @Before
    public void createHugeFile(){
        ClassLoader classLoader = test.TestSuite.class.getClassLoader();
        File classpathRoot = new File(classLoader.getResource("").getPath());
        directory = classpathRoot.getPath() + "\\TestText\\";
        try{
            File f0 = new File(directory);
            File f1 = new File(directory + "1000dogs1000000words");
            f0.mkdir();
            f1.createNewFile();
        }catch(IOException e){
            System.out.println();
        }

        try (Writer doc1Writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(directory + "1000dogs1000000words"), "utf-8"))) {
            for(int i=0; i < 1000; i++){
                doc1Writer.write("dogs words\n");
            }
            for(int i=0; i < 998000; i++){
                doc1Writer.write("words\n");
            }
            doc1Writer.close();
        }catch(IOException e){

        }
    }

    @After
    public void deleteHugeFile(){
        new File(directory + "1000dogs1000000words").delete();
    }

}
