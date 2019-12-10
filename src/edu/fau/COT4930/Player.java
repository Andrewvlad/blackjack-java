package edu.fau.COT4930; //The Player class for the COT4930 project.
/**
 */
class Player {
    private static String name;
    private int score;

    //The constructor creates a default Player object.
    Player() { name = ""; }

    //The constructor creates a Player object with the specified name. @param n represents the name of the Player.
    public Player(String n) { name = n; }

    //Method to retrieve the name of the player. @return a String representing the name of the Player.
    static String getName() { return name; }// return the name

    //Method to set the Players name. @param n represents the name of the Player.
    static void setName(String n) { name = n; } // set the PLayers name

    //Method to retrieve the name of the player. @return a String representing the name of the Player.
    public int getScore() { return score; }// return the name

    //Method to set the Players name. @param n represents the name of the Player.
    public void setScore(int n) { score = n; } // set the PLayers name
}
