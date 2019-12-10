package edu.fau.COT4930;

import com.sun.org.apache.xpath.internal.objects.XNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Main class for the hand commands. You can:
 *      Build a new deck
 *      Shuffle the deck
 *      Draw a card
 *      Get the remaining deck size
 */
class Deck<javaBackImage> extends JPanel {
    private ArrayList<Card> deck; //represents the deck of cards
    private int currentCardIndex = 0;

    Deck(String userBackPick, Object[] backOptions) {
        deck = new ArrayList<>();
        int backChoice = 1;

        for (int i = 0; i < backOptions.length; i++)
            if (userBackPick.equals(backOptions[i]))
                backChoice = i;
        try {
            BufferedImage im = ImageIO.read(new File("cards.gif"));
            for(int i=0; i<4; i++)
                for(int j=0; j<13; j++) {
                    ImageFilter crop = new CropImageFilter(71*j, 96*i, 71, 96);
                    Image javaFrontImage = createImage(new FilteredImageSource(im.getSource(), crop));
                    ImageFilter cropBack = new CropImageFilter(71*backChoice, 96*4, 71, 96);
                    Image javaBackImage = createImage(new FilteredImageSource(im.getSource(), cropBack));
                    deck.add(new Card(i, j+2, javaFrontImage, javaBackImage));
                }
        } catch (IOException e) {e.printStackTrace();}
    }
    void shuffle() { //randomly shuffles the cards
        Collections.shuffle(deck);
        currentCardIndex = 0;
    }
    Card draw() { //returns the next card in the deck
        if (currentCardIndex >= deck.size())
            currentCardIndex = 0;
        return deck.get(currentCardIndex++);
    }
    int getNumberLeftInDeck() { return 52 - currentCardIndex; } //returns the number of cards left in the deck
    Image backSelections(int i) {
        Image javaBackImage = null;
            try {
                BufferedImage im = ImageIO.read(new File("cards.gif"));
                ImageFilter cropBack = new CropImageFilter(71*i, 96*4, 71, 96);
                javaBackImage = createImage(new FilteredImageSource(im.getSource(), cropBack));

            } catch (IOException e) {e.printStackTrace();}
        return javaBackImage;
    }
}