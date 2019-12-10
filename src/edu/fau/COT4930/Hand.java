package edu.fau.COT4930;

import java.util.ArrayList;
/**
 * Main class for the hand commands. You can:
 *      Get the hand value
 *      Check for an ace
 *      Checks for bust
 */
class Hand {
    private ArrayList<Card> hand; //represents the passed hand

    Hand(ArrayList h){
        this.hand = h;
    }
    int getHandValue() {
        int sum = 0;
        for (Card card : hand) {
            sum += card.getValue();
        }
        if(sum > 21 && containsAce()) {
            sum -= 10;
        }
        return sum;
    }
    private boolean containsAce() {
        for (Card card : hand) {
            if (card.getRank().equals("Ace")) {
                return true;
            }
        }
        return false;
    }
    boolean busted() { return (getHandValue() <= 21); }
}

