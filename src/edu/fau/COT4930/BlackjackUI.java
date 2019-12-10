package edu.fau.COT4930;

import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JTextField;
import java.beans.*; //property change stuff
import java.awt.*;
import java.awt.event.*;


public class BlackjackUI extends JPanel {
    private int sizeX = 1200;
    private int sizeY = 800;
    static int winScore = 0;
    static int loseScore = 0;
    static int tieScore = 0;
    private Image offscrImg;
    private Graphics offscrGC;
    private Object[] possibilities = {"Beach", "Fish Blue", "Fish Teal", "Checker 1", "Checker 2",
            "Vine 1", "Vine 2", "Robot","Roses", "Shell", "Castle", "Holding Cards"};
    private Deck d;
    private User u;
    private Dealer de;
    private String playerName = "";
    Player p;


    private void addComponentsToPane(JFrame f) {

        //Create panels
        JMenuBar menuBar = new JMenuBar(); //create the menu bar
        JPanel buttonPanel = new JPanel(); //create the button panel
        buttonPanel.setLayout(new GridLayout(1, 3));
        buttonPanel.setPreferredSize(new Dimension(10, 100));

        //create a menu
        JMenu menu = new JMenu("Menu");
        menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
        menuBar.add(menu);

        //Add objects to buttonPanel layout
        JButton finalButton = new JButton("Hit");
        buttonPanel.add(finalButton);
        JButton finalGradeTextArea = new JButton("Stand");
        buttonPanel.add(finalGradeTextArea);
        JButton reset = new JButton("New Hand");
        buttonPanel.add(reset);

        String userChoice = (String)JOptionPane.showInputDialog(
                null,
                "Select your preferred card back",
                "Card Back Selector",
                1,
                null,
                possibilities,
                "Beach");
        if (userChoice == null)
            System.exit(1);
        d = new Deck(userChoice, possibilities);



        while (playerName.equals("")) {
            playerName = JOptionPane.showInputDialog(null, "Player Name: ");
            if (playerName == null)
                System.exit(1);
        }
        Player.setName(playerName);

        ManageDB.scoreKeeper(playerName);

        //Implement button functions
        finalButton.addActionListener(event -> { UserHit(); });
        finalGradeTextArea.addActionListener(event -> { UserStand();} );
        reset.addActionListener(event -> { runGame(); });

        //Set empty borders to serve as a buffer between fields
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        //Align the panes to the cardinal directions

        // create a menu item
        JMenuItem menuItem = new JMenuItem("Rules");  // images are not scaled but displayed in original size
        menuItem.addActionListener(e -> ShowRules());
        menuItem.addActionListener(e -> System.out.println("\nUser checked: " + e.getActionCommand() + "\n"));
        menu.add(menuItem);

        // add a check box item to the menu
        f.add(buttonPanel, BorderLayout.SOUTH);
        f.setJMenuBar(menuBar);
    }

    private static void ShowRules() {
        JOptionPane.showMessageDialog(null, "Dealer deals 2 cards to the players and two to himself (1 card face up, the other face down).\n" +
                "\n" +
                "Blackjack card values: All cards count their face value in blackjack. Picture cards count as 10 and the ace can count as either 1 or 11. Card suits have no meaning in blackjack. The total of any hand is the sum of the card values in the hand\n" +
                "\n" +
                "Players must decide whether to stand, hit, surrender, double down, or split.\n" +
                "\n" +
                "The dealer acts last and must hit on 16 or less and stand on 17 through 21.\n" +
                "\n" +
                "Players win when their hand totals higher than dealer’s hand, or they have 21 or less when the dealer busts (i.e., exceeds 21).", "InfoBox: " + "Rules", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        BlackjackUI dt = new BlackjackUI();
        JFrame f = new JFrame("Blackjack");
        dt.setSize(dt.sizeX, dt.sizeY);
        f.add(dt);
        dt.addComponentsToPane(f);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        Insets insets = f.getInsets();
        f.setSize(dt.sizeX + insets.left + insets.right, dt.sizeY + insets.top + insets.bottom);
        f.setVisible(true);
        f.setBackground(Color.black);
        f.setForeground(Color.blue);
        //dt.buildDecks();
        dt.runGame();
    }

    private void buildDecks() {
        try {
            repaint();
            offscrImg = this.createImage(sizeX, sizeY);
            offscrGC = offscrImg.getGraphics();
            super.paintComponent(offscrGC);
            repaint();

            Card c;
            for (int i = 0; i < 52; i++) {
                c = d.draw();
                offscrGC.drawImage(c.getImageFace(), (i % 13) * 30, (i / 13) * 30, this);
                paintImmediately(0,0,sizeX, sizeY);
                Thread.sleep(25);
            }
            for (int i = 52; i-- > 0;) {
                c = d.draw();
                offscrGC.drawImage(c.getImageBack(), (i % 13) * 30, (i / 13) * 30, this);
                paintImmediately(0,0,sizeX, sizeY);
                Thread.sleep(20);
            }
            d.shuffle();
            super.paintComponent(offscrGC);
            for (int i = 0; i < 52; i++) {
                c = d.draw();
                offscrGC.drawImage(c.getImageFace(), (i % 13) * 30, (i / 13) * 30, this);
                paintImmediately(0,0,sizeX, sizeY);
                Thread.sleep(25);
            }
            for (int i = 51; i >= 0; i--) {
                c = d.draw();
                offscrGC.drawImage(c.getImageBack(), (i % 13) * 30, (i / 13) * 30, this);
                paintImmediately(0,0,sizeX, sizeY);
                Thread.sleep(20);
            }

        } catch (InterruptedException e) {System.out.println(e);}
    }
    private void runGame() {
        try {
            buildDecks();
            Card c;
        super.paintComponent(offscrGC);

        for (int i = 0; i < 52; i++) { //card backs
            paintImmediately(0,0,sizeX, sizeY);
            c = d.draw();
            offscrGC.drawImage(c.getImageBack(), i * 2 + 500, 0, this);
            Thread.sleep(25);
        }
            u = new User();
            de = new Dealer();
            u.hand.add(d.draw());
            de.hand.add(d.draw());
            u.hand.add(d.draw());
            de.hand.add(d.draw());

            offscrGC.drawImage(u.hand.get(0).getImageFace(), 100, 400, this);
            paintImmediately(0,0,sizeX, sizeY);
            Thread.sleep(100);
            offscrGC.drawImage(u.hand.get(1).getImageFace(), 130, 400, this);
            paintImmediately(0,0,sizeX, sizeY);
            Thread.sleep(100);
            offscrGC.drawImage(de.hand.get(0).getImageFace(), 100, 0, this);
            paintImmediately(0,0,sizeX, sizeY);
            Thread.sleep(100);
            offscrGC.drawImage(de.hand.get(1).getImageBack(), 130, 0, this);
            paintImmediately(0,0,sizeX, sizeY);
            Thread.sleep(100);



            offscrGC.drawString("Your starting hand: " + new Hand(u.hand).getHandValue() + " (" + u.hand.get(0).getCardStats() + ", and " + u.hand.get(1).getCardStats() + ")",100 ,510);
            offscrGC.drawString("Dealer's starting hand: " + de.hand.get(0).getValue() + "+ (" + de.hand.get(0).getCardStats() + ", and a Hidden Card)",100 ,110);
            offscrGC.drawString(d.getNumberLeftInDeck() + " cards left in deck", 500, 110);


            paintImmediately(0,0,sizeX, sizeY);

            System.out.println(d.getNumberLeftInDeck() + " cards left in deck");
            System.out.println("Your current hand: " + new Hand(u.hand).getHandValue() + " (" + u.hand.get(0).getCardStats() + ", and " + u.hand.get(1).getCardStats() + ")");
            System.out.println("Dealer's current hand: " + de.hand.get(0).getValue() + "+ (" + de.hand.get(0).getCardStats() + ", and a hidden card)");
            System.out.println("\nWould you like to hit or stand?");
        } catch (InterruptedException e) {System.out.println("oops" + e); }
    }
    private void UserHit() {
        System.out.println("You hit\n");
        u.hand.add(d.draw());
        repaint();

        System.out.println("You draw: " + u.hand.get(u.hand.size()-1).getCardStats());
        System.out.println(d.getNumberLeftInDeck() + " cards left in deck");
        paintImmediately(0,0,sizeX, sizeY);
        repaint();

        offscrGC.drawString("Your current hand: " + new Hand(u.hand).getHandValue() + " (" + u.hand.get(0).getCardStats() + ", and " + u.hand.get(1).getCardStats() + ")",100 ,510);
        System.out.println("Your new hand value: " + new Hand(u.hand).getHandValue());
        for (int i = 0; i < u.hand.size(); i++) {
            repaint();
            offscrGC.drawImage(u.hand.get(i).getImageFace(), (i % 13) * 30 + 100, ((i / 13) * 30) + 400, this);
        }
        if(!new Hand(u.hand).busted())
            checkWinner();
    }

    private void UserStand() {
        //try {
            System.out.println("You stand\n");
            System.out.println("Dealer's Turn!");
            repaint();
            while (de.shouldDraw()) {
                de.hand.add(d.draw());
            }            for (int i = 0; i < de.hand.size(); i++) {
                offscrGC.drawImage(de.hand.get(i).getImageFace(), (i % 13) * 30 + 100,  0, this);
                repaint();
            }

            System.out.println("Your final hand: " + new Hand(u.hand).getHandValue());
            System.out.println("Dealer's final hand: " + new Hand(de.hand).getHandValue());

            checkWinner();
        //} catch (InterruptedException e) {System.out.println("oops"+ e);}
    }

    private void checkWinner() {
        String currentScore;
        String result = null;
        if ((!new Hand(u.hand).busted())) { //player bust condition
            loseScore++;
            result ="You busted!";
        }
        else if (!new Hand(de.hand).busted()) { //dealer bust condition
            winScore++;
            result ="The dealer busted!";
        }
        else if (new Hand(de.hand).getHandValue() < new Hand(u.hand).getHandValue()) { //win condition
            winScore++;
            result ="You won!";
        }
        else if (new Hand(de.hand).getHandValue() > new Hand(u.hand).getHandValue()) { //loss condition
            loseScore++;
            result ="You lost!";
        }
        else if (new Hand(de.hand).getHandValue() == new Hand(u.hand).getHandValue()) { //tie condition
            tieScore++;
            result ="You tied!";
        }
        currentScore = Player.getName() + "'s score: \nWins: " + winScore + " \nLosses: " + loseScore + " \nTies: " + tieScore;
        offscrGC.drawString("New Game Loading",400 ,400);
        JOptionPane.showMessageDialog(null, result +"Your final hand: " + new Hand(u.hand).getHandValue() + "\nDealer's final hand: " + new Hand(de.hand).getHandValue() + "\n\n" + currentScore, "InfoBox: " + "Game Score", JOptionPane.INFORMATION_MESSAGE);
        super.paintComponent(offscrGC);
        //buildDecks();
        runGame();

    }

    public void paint(Graphics g) { //paints the screen black if the game UI fails
        g.setColor(Color.black); //sets color to black
        g.fillRect(0, 0, sizeX, sizeY); //selects the entire area
        g.drawImage(offscrImg, 0, 0, this); //paints the area
        setOpaque(true);
    }
    public void update(Graphics g) { paint(g); } //updates the screen

}