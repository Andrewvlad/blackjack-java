package edu.fau.COT4930;

import java.awt.*;
/**
 * Main class for the cards themselves. You can:
 *      Get their Suit
 *      Get their Rank
 *      Get their Value
 *      Get their Stats
 *      Get their Card Face
 *      Get their Card Back
 */
class Card {
    private int suit; //the suit of a card
    private int rank; //the value of a card
    private Image imageFace; //the image face of a card
    private Image imageBack; //the image back of a card
    private String[] ranks = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"}; //all ranks
    private String[] suits = {"Spades", "Hearts", "Clubs", "Diamonds"}; //all suits

    Card(int s, int r, Image imf, Image imb) { //a 4 input constructor
        this.suit = s; //s represents the suit of the card
        this.rank = r; //v represents the value
        this.imageFace = imf; //im is the Image face of the card
        this.imageBack = imb; //im is the Image back of the card
    }
    private String getSuit() { //returns the suit of the card
        return suits[suit];
    } //returns the suit of the card
    String getRank() { //returns the value of the card
        return ranks[rank];
    } //returns the rank of the card
    int getValue() { //returns the value of the card
        int value;
        if (rank == 14) value = 11; //set aces to 11
        else value = Math.min(rank, 10); //set faces to 10
        return value; //everything else is its rank
    }
    String getCardStats() { return (getRank() + " of " + getSuit()); } //returns the stats of the card
    Image getImageFace() { return imageFace; } //returns the face of the card
    Image getImageBack() { return imageBack; } //returns the back of the card

}