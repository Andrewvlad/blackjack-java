package edu.fau.COT4930;

import java.io.*;

import static java.lang.Integer.valueOf;

class ManageDB extends BlackjackUI{

    static void scoreKeeper(String playerName){
        String line; //Setting empty value
        boolean found = false;
        try {
            BufferedReader in = new BufferedReader(new FileReader("GameScore.txt")); // throws FileNotFoundException checked exception
            line = in.readLine(); //First line read
            while (line != null) { //Loop till end of file
                String[] ch = line.split(":"); //Splitter on the ":"
                for (String s : ch) {
                    if (s.isEmpty()) { //Skips first empty item
                        continue;
                    }

                    if (s.substring(0, s.indexOf("=")).equals(playerName)) {
                        found = true;
                    }
                    if (found) {
                        BlackjackUI.winScore = Integer.parseInt(s.substring(s.indexOf("=") + 1, s.indexOf("=") + 2));
                        BlackjackUI.loseScore = Integer.parseInt(s.substring(s.indexOf("=") + 2, s.indexOf("=") + 3));
                        BlackjackUI.tieScore = Integer.parseInt(s.substring(s.indexOf("=") + 3));

                    }
                }
                line = in.readLine(); //Next line read
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException when reading the file " + e); //Exception call
        } catch (IOException e) {
            System.out.println("IOException when reading the file " + e); //Exception call
        }
        if (!found) {System.out.println("aaaaa"); addNewPlayer(); }
    }
    private static void addNewPlayer()
    {
        System.out.println("aaaaa");
        BufferedWriter out;
        String textToWrite;
        char[] array;
        try
        {
            out = new BufferedWriter(new FileWriter("GameScore.txt")); // FileWriter throws IOException
            textToWrite = "This is the first line of text";
            array = textToWrite.toCharArray();
            out.write(array, 0, array.length);  // throws IOException checked exception
            out.newLine();
            textToWrite = "This is the second line of text";
            array = textToWrite.toCharArray();
            out.write(array, 0, array.length);  // throws IOException checked exception
        }
        catch (IOException e)
        {
            System.out.println("IOException when writing the file " + e);
        }
    }
}