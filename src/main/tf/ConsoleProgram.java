package main.tf;

import java.util.Scanner;

/**
 * Created by Ganon on 4/14/2017.
 */
public class ConsoleProgram {
    public static void main(String[] args){
        new ConsoleProgram().runConsoleProgram();
    }

    public void runConsoleProgram(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter directory containing document set (include final \\):");
        String directory = scanner.next();
        System.out.println("Please enter word list:");
        scanner.nextLine();
        String wordsString = scanner.nextLine();
        CompareDocs temp = new CompareDocs();
        temp.docDirectory = directory;
        temp.tfWordSet = wordsString.split(" ");
        temp.runProgram();
    }
}
