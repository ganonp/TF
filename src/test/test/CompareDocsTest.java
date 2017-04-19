package test;

import org.junit.*;
import main.tf.CompareDocs;
import java.io.*;

/**
 * Created by Ganon on 4/14/2017.
 */
public class CompareDocsTest {
    String directory;


    @Test
    public void runProgram(){
        CompareDocs test1 = new CompareDocs();
        test1.docDirectory = TestSuite.directory;
        test1.tfWordSet = new String[]{"bobs", "blobs", "hamburgers"};
        test1.runProgram();
        Assert.assertTrue(test1.maxTermFrequencyByWordMap.get("bobs").getRatio("bobs") == 8d/15d);
        Assert.assertTrue(test1.maxTermFrequencyByWordMap.get("bobs").docName.equals("8bobs4logs3blobs"));
        Assert.assertTrue(test1.maxTermFrequencyByWordMap.get("blobs").getRatio("blobs") == 3d/15d);
        Assert.assertTrue(test1.maxTermFrequencyByWordMap.get("blobs").docName.equals("8bobs4logs3blobs"));
        Assert.assertTrue(test1.maxTermFrequencyByWordMap.get("hamburgers").getRatio("hamburgers") == 10d/20d);
        Assert.assertTrue(test1.maxTermFrequencyByWordMap.get("hamburgers").docName.equals("5bobs5dogs10hamburgers"));
    }

    @Test
    public void runProgram2(){
        CompareDocs test1 = new CompareDocs();
        test1.docDirectory = TestSuite.directory;
        test1.tfWordSet = new String[]{"archers", "lanas", "mallorys"};
        test1.runProgram();
        Assert.assertTrue(test1.maxTermFrequencyByWordMap.get("archers").getRatio("archers") == 2000d/1000000d);
        Assert.assertTrue(test1.maxTermFrequencyByWordMap.get("archers").docName.equals("2000archers1000000words"));
        Assert.assertTrue(test1.maxTermFrequencyByWordMap.get("lanas").getRatio("lanas") == 1000d/1000000d);
        Assert.assertTrue(test1.maxTermFrequencyByWordMap.get("lanas").docName.equals("1000lanas1000000words"));
        Assert.assertTrue(test1.maxTermFrequencyByWordMap.get("mallorys").getRatio("mallorys") == 10000d/1000000d);
        Assert.assertTrue(test1.maxTermFrequencyByWordMap.get("mallorys").docName.equals("10000mallorys1000000words"));
    }

    @Before
    public void createHugeFile(){
        ClassLoader classLoader = test.TestSuite.class.getClassLoader();
        File classpathRoot = new File(classLoader.getResource("").getPath());
        directory = classpathRoot.getPath() + "\\TestText\\";
        try{
            File f0 = new File(directory);
            File f1 = new File(directory + "1000lanas1000000words");
            File f2 = new File(directory + "2000archers1000000words");
            File f3 = new File(directory + "10000mallorys1000000words");
            f0.mkdir();
            f1.createNewFile();
        }catch(IOException e){
            System.out.println();
        }

        try (Writer doc1Writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(directory + "1000lanas1000000words"), "utf-8"))) {
            for(int i=0; i < 1000; i++){
                doc1Writer.write("lanas words\n");
            }
            for(int i=0; i < 998000; i++){
                doc1Writer.write("words\n");
            }
            doc1Writer.close();
        }catch(IOException e){

        }
        try (Writer doc1Writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(directory + "2000archers1000000words"), "utf-8"))) {
            for(int i=0; i < 2000; i++){
                doc1Writer.write("archers words\n");
            }
            for(int i=0; i < 996000; i++){
                doc1Writer.write("words\n");
            }
            doc1Writer.close();
        }catch(IOException e){

        }
        try (Writer doc1Writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(directory + "10000mallorys1000000words"), "utf-8"))) {
            for(int i=0; i < 10000; i++){
                doc1Writer.write("mallorys words\n");
            }
            for(int i=0; i < 980000; i++){
                doc1Writer.write("words\n");
            }
            doc1Writer.close();
        }catch(IOException e){

        }
    }

    @After
    public void deleteHugeFile(){
        new File(directory + "1000lanas1000000words").delete();
        new File(directory + "2000archers1000000words").delete();
        new File(directory + "10000mallorys1000000words").delete();
    }
}
