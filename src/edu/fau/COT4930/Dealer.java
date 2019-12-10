package edu.fau.COT4930;

import java.util.ArrayList;
/**
 */
class Dealer {
    ArrayList<Card> hand; //represents the dealers  hand

    Dealer() {
        hand = new ArrayList<>();
    }

    boolean shouldDraw() { return (new Hand(hand).getHandValue() < 17); }
}