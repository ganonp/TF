package main.tf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.*;

/**
 * Created by Ganon on 4/13/2017.
 */
public class DocTermFrequency {
    public String directory;
    public String docName;
    private static String[] wordList;
    private static Map<String, Integer> wordIndexMap;
    private int[] wordCounts;
    private int total = 0;

    public DocTermFrequency(String[] wordList, String directory, String docName){
        this.directory = directory;
        this.docName = docName;
        this.wordList = wordList;
        wordCounts = new int[this.wordList.length];
    }

    public DocTermFrequency( String directory, String docName){
        this.directory = directory;
        this.docName = docName;
        wordCounts = new int[this.wordList.length];
    }

    public static void main(String[] args){
        DocTermFrequency test = new DocTermFrequency(new String[]{"whale","man"}, "C:\\Users\\Teresa\\IdeaProjects\\TF\\SampleDocs\\", "mobydick-chapter1.txt");
        test.countWords();
        System.out.println("whale " + test.getCount("whale"));
    }

    //Initializes wordIndexMap so that every document counts words using the same index
    //Must be run before any other methods
    public static void initializeWordIndex(String[] wordList1){
        HashMap<String,Integer> tempWordIndexMap = new HashMap<>();
        for(int i = 0; i < wordList1.length; i++){
            tempWordIndexMap.put(wordList1[i],i);
        }
        wordIndexMap = Collections.unmodifiableMap(tempWordIndexMap);
        wordList = wordList1;
    }

    //Counts the words in the word list for this document and places the counts in total and wordsCounts
    public void countWords(){
        try (Stream<String> docByLineStream = Files.lines(Paths.get(directory + docName)).parallel()) {
            Stream<String> docByWordStream = docByLineStream.flatMap(DocTermFrequency::formatText);
            docByWordStream.forEach(word -> countWord(word));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    //Checks if we want to get this word's term frequency, if so, counts it, also increases the total word count.
    private void countWord(String word){
        if(wordIndexMap.containsKey(word)){
            incrementWordCount(word);
        }
        incrementTotal();
    }

    //Removes punctuation and splits words using spaces
    private static Stream<String> formatText(String line){
        String formattedText = line.toLowerCase().replaceAll("[^a-z ]", "");
        return Arrays.stream(formattedText.split(" "));
    }

    private synchronized void incrementTotal() {
        total++;
    }
    private synchronized void incrementWordCount(String word) {
        wordCounts[wordIndexMap.get(word)]++;
    }

    public double getRatio(String word){
        return (double) wordCounts[wordIndexMap.get(word)] / (double) total;
    }

    public int getCount(String word){
        return wordCounts[wordIndexMap.get(word)];
    }

}
