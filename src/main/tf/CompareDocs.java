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
    public String words[];
    public HashMap<String, DocTermFrequency> maxTermFrequencyByDocMap = new HashMap<>();
    private File[] docs;
    private List<DocTermFrequency> termFrequencyByDocList;

    public static void main(String[] args){
        CompareDocs temp = new CompareDocs();
        temp.docDirectory = "C:\\Users\\Teresa\\IdeaProjects\\TF\\SampleDocs";
        temp.words = new String[]{"queequeg", "whale", "sea", "man"};
        temp.runProgram();
    }

    //runs the full program, the docDirectory and words variables must be set for it to run
    public void runProgram(){
        getDocNames();
        DocTermFrequency.initializeWordIndex(words);
        doCounts();
        getDocsWithMaxTFs();
        printMaxTermFrequencies();
    }

    //sets the variable docs to a File[] consisting of every file in the doc
    private void getDocNames(){
        File folder = new File(docDirectory);
        docs = folder.listFiles();
    }

    //performs counting of words for each document in the variable docs and adds the counts to a list
    private void doCounts(){
        termFrequencyByDocList = Arrays.stream(docs).parallel().map(name -> {
            DocTermFrequency docTermFrequency = new DocTermFrequency(docDirectory , name.getName());
            docTermFrequency.countWords();
            return docTermFrequency;
        } ).collect(Collectors.toList());

    }

    //gets the set of documents containing the max term frequencies and adds it to the maxTermFrequency
    //maxTermFrequencybyDocMap
    private void getDocsWithMaxTFs(){
        for(String word: words){
            DocTermFrequency maxDocTermFrequency = termFrequencyByDocList.stream().max((doc1, doc2) -> {
                        if(doc1.getRatio(word) > doc2.getRatio(word)){
                            return 1;
                        }else if(doc1.getRatio(word) < doc2.getRatio(word)){
                            return -1;
                        }else{
                            return 0;
                        }
                    }
            ).get();
            maxTermFrequencyByDocMap.put(word, maxDocTermFrequency);
        }
    }

    //prints the output as word, document name, frequency
    private void printMaxTermFrequencies(){
        for(String word: words){
            System.out.println(word + "    " + maxTermFrequencyByDocMap.get(word).docName + "  "
                    + maxTermFrequencyByDocMap.get(word).getRatio(word));
        }
    }

}
