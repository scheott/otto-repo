// Written by schen345

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;
import java.util.Random;
import java.io.*; 
import java.util.ArrayList;
import java.util.List;
import java.lang.*;
import java.io.FileWriter;
import java.io.IOException;


public class Wordle {

    public String [] getWordArray() throws Exception {

        File f = new File("WordleList.csv");
        String [] words = new String [40290];
        String line; //Reading in my list of words

        Scanner s = new Scanner(f);
            
        int totalWords = 0;
        while (s.hasNextLine()){ // is there another line in file f
            line = s.nextLine(); // if so, read line
            line = line.trim().replaceAll("^[\n\r]$","");
            String wordsOnLine [] = line.split(" "); //split out the words
            for (String word : wordsOnLine) { 
                if (word != null) {
                    words[totalWords] = word; 
                    totalWords = totalWords + 1;
                }
            }   
        }
        s.close(); //closing file
        return words;
    }

    
    public String getAlphabet() {
        String alpharr = "ABCDEFGHI" + "\n" + "JKLMNOPQR" + "\n" + "STUVWXYZ";
        return alpharr;
    }

    public static void main(String[] args) throws Exception {
        
        Wordle w = new Wordle();
        String [] words = w.getWordArray(); 
        String lststr = "";
        String alpharr = w.getAlphabet();
        String green = ""; //store characters where there green etc.
        String yellow = "";
        String grey = "";
        String guesslst = ""; //to later write to the wordle attemps file
        
        Random rand = new Random();
        int rand_int = rand.nextInt(5757); //the bound is 5757 because thats how many words there are
        //the other was 40290 because there were a ton of null values but I'm not sure why        
         String answer = words[rand_int].toUpperCase();
        // the answer is now random and upper case


        System.out.println("\033[0;34m"  + "WORD GUESSING GAME!!!" + "\n" + "YOU GET 6 GUESSES" + "\n" + "GUESS THE 5 LETTER WORD!!!" + "\u001B[0m");

        for (int j = 0; j<6; ++j) {
            //6 tries
            String keyboard = "";
            System.out.println("Enter a guess");
            Scanner myScanner = new Scanner(System.in);
            String guess = myScanner.nextLine().toUpperCase(); //prompts for guess
            boolean nonchar = guess.matches("[a-zA-Z]+"); //true if the guess is all characters and vice versa
            //boolean realword = wordcheck.contains(guess);

            guesslst += guess + "\n";
            //System.out.print(answer);

            if (guess.length() != 5 || nonchar == false /*|| realword == false */) {

                System.out.println("ERROR ENTER A VALID 5 LETTER WORD");
                j = j - 1;
                //If the guess has a character or is not 5 letters or contains a non letter, reprompt

            } 
            else {
                
                String colorstr = "";
                
                if (answer.equals(guess)) {

                    colorstr += "\033[0;34m" + "\u001B[42m" + answer + "\u001B[0m";
                    //if the guess is correct, congraulate in all green, and break the loop
                    System.out.println(colorstr);
                    System.out.println("CONGRATS YOU DID IT!!!");

                    break;
                    
                 
                }else {
                    
                    for (int i =0; i<guess.length(); ++i) {

                        char c = guess.charAt(i);
                        //split the guesss into charcters and iterate through to see if the answer holds one of the characters then print each color out as fit
                        if (answer.charAt(i) == c) {
                            colorstr += "\033[0;34m" + "\u001B[42m" + c + "\u001B[0m";
                            green += c;
                            
                        } else if (answer.contains(Character.toString(c))) {
                            colorstr += "\033[0;34m" + "\u001B[43m" + c + "\u001B[0m";
                            yellow += c;

                        } else {
                            colorstr +=  "\033[0;34m" + "\u001B[47m" + c + "\u001B[0m";
                            grey += c;
                            //add each letter to a list of each color to remember for the keyboard
                        }
                        
                    }
                    
                     
                    for (int k =0; k<26;k++) {
                        char c2 = alpharr.charAt(k);//alphabet into characters, when the characters are in a colors list itll print in that color

                        if (green.contains(Character.toString(c2))) {  

                            keyboard += "\033[0;34m" + "\u001B[42m" + c2 + "\u001B[0m";                           
                        }
                        else if (yellow.contains(Character.toString(c2))) {       

                            keyboard += "\033[0;34m" + "\u001B[43m" + c2 + "\u001B[0m";
                        }
                        else if (grey.contains(Character.toString(c2))) {        

                            keyboard += "\033[0;34m" + "\u001B[47m" + c2 + "\u001B[0m";
                        }
                        else { //the letters that havn't been guessed will be printed normal

                            if (!keyboard.contains(Character.toString(c2)) || !grey.contains(Character.toString(c2)) || !yellow.contains(Character.toString(c2)) || !green.contains(Character.toString(c2))){

                                keyboard += "\033[0;34m" + c2 + "\u001B[0m";
                            }
                        }
                        
                    }
                //Colors, and colorless strings
                    
                    System.out.println(lststr); 
                    System.out.println(colorstr);
                    //to give past answers a list of answers after each guess then print, make sure that the most recent answer is the lowest 
                    lststr = lststr + "\n" + colorstr;
                    System.out.println("\n" + keyboard);

                }

                }            
                // if the last guess is incorrect, sorry message with answer
            if (j == 5) {                    
                if (!guess.equals(answer)) {
                    System.out.println("Sorry!!! The answer was " + answer);
                }               
            }

                   
              
        }
        
        try { //file write the string of guesses that was created above
            FileWriter writer = new FileWriter("WordleAttempts.txt", true);
            writer.write("\n" + "New Attempt" + "\n");
            writer.write(guesslst);
            // then write the answer below, new line so it doesn't overwrite past attemps
            writer.write("Correct Answer: " + answer + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }


        

    }


} 


