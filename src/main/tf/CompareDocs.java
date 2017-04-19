package main.tf;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.*;

/**
 * Created by Ganon on 4/13/2017.
 */
public class CompareDocs {
    public String docDirectory;
    public String tfWordSet[];
    public HashMap<String, DocTermFrequency> maxTermFrequencyByWordMap = new HashMap<>();
    private File[] docSet;
    private List<DocTermFrequency> docWithTermFrequenciesList;

    public static void main(String[] args){
        CompareDocs temp = new CompareDocs();
        temp.docDirectory = "C:\\Users\\Teresa\\IdeaProjects\\TF\\SampleDocs";
        temp.tfWordSet = new String[]{"queequeg", "whale", "sea", "man"};
        temp.runProgram();
    }

    //runs the full program, the docDirectory and tfWordSet variables must be set for it to run
    public void runProgram(){
        getDocNames();
        DocTermFrequency.initializeWordIndex(tfWordSet);
        doCounts();
        getDocsWithMaxTFs();
        printMaxTermFrequencies();
    }

    //sets the variable docs to a File[] consisting of every file in the docDirectory
    private void getDocNames(){
        File folder = new File(docDirectory);
        docSet = folder.listFiles();
    }

    //performs counting of words in tfWordSet for each document in the variable docs and adds the counts to a list
    private void doCounts(){
        docWithTermFrequenciesList = Arrays.stream(docSet).parallel().map(name -> {
            DocTermFrequency docTermFrequency = new DocTermFrequency(docDirectory , name.getName());
            docTermFrequency.countWords();
            return docTermFrequency;
        } ).collect(Collectors.toList());

    }

    //gets the set of documents containing the max term frequencies and adds it to the 
    //maxTermFrequencyByWordMap
    private void getDocsWithMaxTFs(){
        for(String word: tfWordSet){
            DocTermFrequency maxDocTermFrequency = docWithTermFrequenciesList.stream().max((doc1, doc2) -> {
                        if(doc1.getRatio(word) > doc2.getRatio(word)){
                            return 1;
                        }else if(doc1.getRatio(word) < doc2.getRatio(word)){
                            return -1;
                        }else{
                            return 0;
                        }
                    }
            ).get();
            maxTermFrequencyByWordMap.put(word, maxDocTermFrequency);
        }
    }

    //prints the output as word, document name, frequency
    private void printMaxTermFrequencies(){
        for(String word: tfWordSet){
            System.out.println(word + "    " + maxTermFrequencyByWordMap.get(word).docName + "  "
                    + maxTermFrequencyByWordMap.get(word).getRatio(word));
        }
    }

}
