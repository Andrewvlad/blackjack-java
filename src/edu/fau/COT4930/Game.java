/**package edu.fau.COT4930;

import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;



public class Game extends JPanel {
    int sizeX = 1200;
    int sizeY = 800;
    int userChoice = 11;
    static int winScore = 0;
    static int loseScore = 0;
    static int tieScore = 0;
    Image offscrImg;
    Graphics offscrGC;
    Card c;
    Deck d = new Deck(userChoice);
    User u;
    Dealer de;
    String playerName;
    Player p;
    String currentScore;



    public void run(JFrame f)
    {
        addComponentsToPane(f);
    }

    public void addComponentsToPane(JFrame f) {

        //Create panels
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1, 3));

        //Add objects to bottom layout
        JButton finalButton = new JButton("Hit");
        bottom.add(finalButton);
        JButton finalGradeTextArea = new JButton("Stand");
        bottom.add(finalGradeTextArea);
        JButton reset = new JButton("Reset");
        bottom.add(reset);
        playerName = JOptionPane.showInputDialog(f, "Player Name: ");

        //Implement button functions
        finalButton.addActionListener(event -> { UserHit(); });
        finalGradeTextArea.addActionListener(event -> { UserStand(); });
        reset.addActionListener(event -> {
            super.paintComponent(offscrGC);
            runGame(); });

        //Set empty borders to serve as a buffer between fields
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        //Align the panes to the cardinal directions
        f.add(bottom, BorderLayout.SOUTH);


        //Create the menu bar.
        JMenuBar menuBar = new JMenuBar();

        // create a menu
        JMenu menu = new JMenu("Menu");
        menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
        menuBar.add(menu);

        // create a menu item
        JMenuItem menuItem = new JMenuItem("Rules");  // images are not scaled but displayed in original size
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("2nd Menu Item Event = " + e.getActionCommand());
            }
        });
        menuItem.addActionListener(e -> ShowRules("Dealer deals 2 cards to the players and two to himself (1 card face up, the other face down).\n" +
                "\n" +
                "Blackjack card values: All cards count their face value in blackjack. Picture cards count as 10 and the ace can count as either 1 or 11. Card suits have no meaning in blackjack. The total of any hand is the sum of the card values in the hand\n" +
                "\n" +
                "Players must decide whether to stand, hit, surrender, double down, or split.\n" +
                "\n" +
                "The dealer acts last and must hit on 16 or less and stand on 17 through 21.\n" +
                "\n" +
                "Players win when their hand totals higher than dealer’s hand, or they have 21 or less when the dealer busts (i.e., exceeds 21).", "Rules"));
        menu.add(menuItem);

        // add a check box item to the menu
        f.setJMenuBar(menuBar);
    }

    public static void ShowRules(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public void UserHit() {
        System.out.println("You hit");
        u.hand.add(d.draw());
        System.out.println("Deck Size: " + d.getNumberLeftInDeck());
        for (int i = 0; i < u.hand.size(); i++) {
            repaint();
            offscrGC.drawImage(u.hand.get(i).getImageFace(), (i % 13) * 30 + 100, ((i / 13) * 30) + 400, this);
        }
    }
    public void UserStand()
    {
        try {
            System.out.println("You stand");
            System.out.println("Dealer's Turn!");
            repaint();
            de.turn();
            for (int i = 0; i < de.hand.size(); i++) {
                offscrGC.drawImage(de.hand.get(i).getImageFace(), (i % 13) * 30 + 100,  0, this);
                Thread.sleep(25);
            }
            repaint();
            System.out.println("Player's final hand: " + u.getHandValue());
            System.out.println("Dealer's final hand: " + de.getHandValue());

            checkWinner();
        } catch (InterruptedException e) {}
    }
    public static void main(String[] args) {
        Game dt = new Game();
        JFrame f = new JFrame("Blackjack");
        dt.setSize(dt.sizeX, dt.sizeY);
        f.add(dt);
        dt.run(f);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        Insets insets = f.getInsets();
        f.setSize(dt.sizeX + insets.left + insets.right, dt.sizeY + insets.top + insets.bottom);
        f.setVisible(true);
        dt.runGame();
    }

    public void buildDecks() {
        try {
            offscrImg = this.createImage(sizeX, sizeY);
            offscrGC = offscrImg.getGraphics();
            for (int i = 0; i < 52; i++) {
                c = d.draw();
                offscrGC.drawImage(c.getImageFace(), (i % 13) * 30, (i / 13) * 30, this);
                Thread.sleep(25);
            }
            for (int i = 52; i-- > 0;) {
                c = d.draw();
                offscrGC.drawImage(c.getImageBack(), (i % 13) * 30, (i / 13) * 30, this);
                Thread.sleep(25);
            }
            d.shuffle();
            for (int i = 0; i < 52; i++) {
                repaint();
                c = d.draw();
                offscrGC.drawImage(c.getImageFace(), (i % 13) * 30, (i / 13) * 30, this);
                Thread.sleep(25);
            }
            for (int i = 51; i >= 0; i--) {
                repaint();
                c = d.draw();
                offscrGC.drawImage(c.getImageBack(), (i % 13) * 30, (i / 13) * 30, this);
                Thread.sleep(25);
            }
            super.paintComponent(offscrGC);
            for (int i = 0; i < 52; i++) { //card backs
                repaint();
                c = d.draw();
                offscrGC.drawImage(c.getImageBack(), i * 2 + 500, 0, this);
                Thread.sleep(25);
            }
        } catch (InterruptedException e) {}
    }
    public void runGame() {
        //try {
            buildDecks();
            u = new User();
            de = new Dealer();
            u.hand.add(d.draw());
            de.hand.add(d.draw());
            u.hand.add(d.draw());
            de.hand.add(d.draw());
            repaint();

            for (int i = 0; i < u.hand.size(); i++) {
                repaint();
                offscrGC.drawImage(u.hand.get(i).getImageFace(), (i % 13) * 30 +100, ((i / 13) * 30) + 400, this);
            }
            offscrGC.drawImage(de.hand.get(0).getImageFace(), 100, 0, this);
            offscrGC.drawImage(de.hand.get(1).getImageBack(), 130, 0, this);

            System.out.println("Deck Size: " + d.getNumberLeftInDeck());

        //} //catch (InterruptedException e) {}
    }

    void checkWinner() {
        if (!de.busted() || de.getHandValue() < u.getHandValue() && u.busted()) {
            winScore++;
        }
        else if (!u.busted() || de.getHandValue() > u.getHandValue()) {
            loseScore++;
        }
        else if (de.getHandValue() == u.getHandValue()) {
            tieScore++;
        }
        currentScore= playerName + " score: \nW: " + winScore + " \nL: " + loseScore + " \nT: " + tieScore;
        System.out.println(currentScore);
    }

    public void paint(Graphics g) { //paints the screen
        g.setColor(Color.green);
        g.fillRect(0, 0, sizeX, sizeY);
        g.drawImage(offscrImg, 0, 0, this);
    }
    public void update(Graphics g) { paint(g); } //updates the screen

}













 package edu.fau.COT4930;

 import java.awt.*;
 import javax.swing.*;
 import javax.swing.JFrame;
 import javax.swing.JMenuBar;
 import javax.swing.JMenu;
 import javax.swing.JMenuItem;
 import java.awt.event.ActionListener;
 import java.awt.event.ActionEvent;
 import javax.swing.JButton;
 import javax.swing.JOptionPane;



 public class Blackjack extends JPanel {
 int sizeX = 1200;
 int sizeY = 800;
 int userChoice = 11;
 static int winScore = 0;
 static int loseScore = 0;
 static int tieScore = 0;
 Image offscrImg;
 Graphics offscrGC;
 Card c;
 Deck d = new Deck(userChoice);
 User u;
 Dealer de;
 String playerName;
 Player p;
 String currentScore;



 public void run(JFrame f)
 {
 addComponentsToPane(f);
 }

 public void addComponentsToPane(JFrame f) {

 //Create panels
 JMenuBar menuBar = new JMenuBar(); //create the menu bar
 JPanel buttonPanel = new JPanel(); //create the button panel
 buttonPanel.setLayout(new GridLayout(1, 3));

 //Add objects to buttonPanel layout
 JButton finalButton = new JButton("Hit");
 buttonPanel.add(finalButton);
 JButton finalGradeTextArea = new JButton("Stand");
 buttonPanel.add(finalGradeTextArea);
 JButton reset = new JButton("Reset");
 buttonPanel.add(reset);
 playerName = JOptionPane.showInputDialog(f, "Player Name: ");

 //Implement button functions
 finalButton.addActionListener(event -> { UserHit(); });
 finalGradeTextArea.addActionListener(event -> { UserStand(); });
 reset.addActionListener(event -> { runGame(); });

 //Set empty borders to serve as a buffer between fields
 buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

 //Align the panes to the cardinal directions
 f.add(buttonPanel, BorderLayout.SOUTH);


 // create a menu
 JMenu menu = new JMenu("Menu");
 menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
 menuBar.add(menu);

 // create a menu item
 JMenuItem menuItem = new JMenuItem("Rules");  // images are not scaled but displayed in original size
 menuItem.addActionListener(new ActionListener()
 {
 public void actionPerformed(ActionEvent e)
 {
 System.out.println("2nd Menu Item Event = " + e.getActionCommand());
 }
 });
 menuItem.addActionListener(e -> ShowRules("Dealer deals 2 cards to the players and two to himself (1 card face up, the other face down).\n" +
 "\n" +
 "Blackjack card values: All cards count their face value in blackjack. Picture cards count as 10 and the ace can count as either 1 or 11. Card suits have no meaning in blackjack. The total of any hand is the sum of the card values in the hand\n" +
 "\n" +
 "Players must decide whether to stand, hit, surrender, double down, or split.\n" +
 "\n" +
 "The dealer acts last and must hit on 16 or less and stand on 17 through 21.\n" +
 "\n" +
 "Players win when their hand totals higher than dealer’s hand, or they have 21 or less when the dealer busts (i.e., exceeds 21).", "Rules"));
 menu.add(menuItem);

 // add a check box item to the menu
 f.setJMenuBar(menuBar);
 }

 public static void ShowRules(String infoMessage, String titleBar)
 {
 JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
 }

 public void UserHit() {
 System.out.println("You hit");
 u.hand.add(d.draw());
 System.out.println("Deck Size: " + d.getNumberLeftInDeck());
 for (int i = 0; i < u.hand.size(); i++) {
 repaint();
 offscrGC.drawImage(u.hand.get(i).getImageFace(), (i % 13) * 30 + 100, ((i / 13) * 30) + 400, this);
 }
 }
 public void UserStand()
 {
 try {
 System.out.println("You stand");
 System.out.println("Dealer's Turn!");
 repaint();
 while (de.shouldDraw()) {
 de.hand.add(d.draw());
 }            for (int i = 0; i < de.hand.size(); i++) {
 offscrGC.drawImage(de.hand.get(i).getImageFace(), (i % 13) * 30 + 100,  0, this);
 Thread.sleep(25);
 }
 repaint();
 System.out.println("Player's final hand: " + new Hand(u.hand).getHandValue());
 System.out.println("Dealer's final hand: " + new Hand(de.hand).getHandValue());

 checkWinner();
 } catch (InterruptedException e) {}
 }
 public static void main(String[] args) {
 Blackjack dt = new Blackjack();
 JFrame f = new JFrame("Blackjack");
 dt.setSize(dt.sizeX, dt.sizeY);
 f.add(dt);
 dt.run(f);
 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 f.pack();
 Insets insets = f.getInsets();
 f.setSize(dt.sizeX + insets.left + insets.right, dt.sizeY + insets.top + insets.bottom);
 f.setVisible(true);
 dt.runGame();
 }

 public void buildDecks() {
 try {
 offscrImg = this.createImage(sizeX, sizeY);
 offscrGC = offscrImg.getGraphics();
 for (int i = 0; i < 52; i++) {
 c = d.draw();
 offscrGC.drawImage(c.getImageFace(), (i % 13) * 30, (i / 13) * 30, this);
 Thread.sleep(25);
 }
 for (int i = 52; i-- > 0;) {
 c = d.draw();
 offscrGC.drawImage(c.getImageBack(), (i % 13) * 30, (i / 13) * 30, this);
 Thread.sleep(25);
 }
 d.shuffle();
 super.paintComponent(offscrGC);
 for (int i = 0; i < 52; i++) {
 repaint();
 c = d.draw();
 offscrGC.drawImage(c.getImageFace(), (i % 13) * 30, (i / 13) * 30, this);
 Thread.sleep(25);
 }
 for (int i = 51; i >= 0; i--) {
 repaint();
 c = d.draw();
 offscrGC.drawImage(c.getImageBack(), (i % 13) * 30, (i / 13) * 30, this);
 Thread.sleep(25);
 }
 super.paintComponent(offscrGC);
 for (int i = 0; i < 52; i++) { //card backs
 repaint();
 c = d.draw();
 offscrGC.drawImage(c.getImageBack(), i * 2 + 500, 0, this);
 Thread.sleep(25);
 }
 } catch (InterruptedException e) {}
 }
 public void runGame() {
 //try {
 buildDecks();
 u = new User();
 de = new Dealer();
 u.hand.add(d.draw());
 de.hand.add(d.draw());
 u.hand.add(d.draw());
 de.hand.add(d.draw());
 repaint();

 for (int i = 0; i < u.hand.size(); i++) {
 repaint();
 offscrGC.drawImage(u.hand.get(i).getImageFace(), (i % 13) * 30 +100, ((i / 13) * 30) + 400, this);

 }
 offscrGC.drawImage(de.hand.get(0).getImageFace(), 100, 0, this);
 offscrGC.drawImage(de.hand.get(1).getImageBack(), 130, 0, this);

 System.out.println("Deck Size: " + d.getNumberLeftInDeck());

 //} //catch (InterruptedException e) {}
 }

 void checkWinner() {
 if ((!new Hand(de.hand).busted() || new Hand(de.hand).getHandValue() < new Hand(u.hand).getHandValue()) && new Hand(u.hand).busted()) {
 winScore++;
 }
 else if (new Hand(de.hand).getHandValue() > new Hand(u.hand).getHandValue() || !new Hand(de.hand).busted()) {
 loseScore++;
 }
 else if (new Hand(de.hand).getHandValue() == new Hand(u.hand).getHandValue()) {
 tieScore++;
 }
 currentScore= playerName + " score: \nW: " + winScore + " \nL: " + loseScore + " \nT: " + tieScore;
 System.out.println(currentScore);
 }

 public void paint(Graphics g) { //paints the screen
 g.setColor(Color.green);
 g.fillRect(0, 0, sizeX, sizeY);
 g.drawImage(offscrImg, 0, 0, this);
 }
 public void update(Graphics g) { paint(g); } //updates the screen

 } */
