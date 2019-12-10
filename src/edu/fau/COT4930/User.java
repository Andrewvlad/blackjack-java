package edu.fau.COT4930;

import java.util.ArrayList;
/**
 */
public class User extends BlackjackUI{
    public ArrayList<Card> hand; //represents the user's hand

    User() {
        hand = new ArrayList<>();
    }
}