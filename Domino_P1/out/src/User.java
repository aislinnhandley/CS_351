//Aislinn Handley
//CS 351L 002

//User.java interface

import java.util.ArrayList;

public abstract class User{
    ArrayList<Domino> hand;
    String name;
    Board gameboard;
    String[][] dominoes;
    Domino myMove;
    boolean playLeftSide;

    public User(String n, Board board){
        name = n;
        gameboard = board;
        hand = new ArrayList();

        //'draw' our dominoes
        //for console version
        dominoes = new String[][]{
                {"     ", "     ", "     "},
                {"     ", "  .  ", "     "},
                {".    ", "     ", "    ."},
                {".    ", "  .  ", "    ."},
                {".   .", "     ", ".   ."},
                {".   .", "  .  ", ".   ."},
                {". . .", "     ", ". . ."}
        };
        //get our initial hand of 5 dominos:
        //TODO: fix after debugging!!!
        for(int i = 0; i < 3; i++)
        {
            Domino d = gameboard.draw();
            hand.add(d);
        }

    }

    //public void User(String name, Board gameboard);
    void draw(){
        //receives a domino from the gameboard object
        Domino d = gameboard.draw();
        hand.add(0,d); }

    void printHand() {
        //for console version only
        /*eventual Domino output format:
        "-------------" +
        "|{0}|{1}|" +
        "|{2}|{3}|" +
        "|{4}|{5}|" +
        "-------------" */
        int size = hand.size();
        size = size * 2;
        ArrayList<Integer> handList = new ArrayList();
        Object[] tempHand = hand.toArray();
        ArrayList subhands = new ArrayList();

        int count = 0;
        subhands.add(handList);

        for (Object temp : tempHand) {
            Domino d = (Domino) temp;
            int s1 = d.getSideOne();
            int s2 = d.getSideTwo();
            if (count > 13) {
                handList = new ArrayList();
                subhands.add(handList);
            }
            handList.add(s1);
            handList.add(s2);
            count += 2;
        }
        for (int j = 0; j < subhands.size(); j++) {
            handList = (ArrayList<Integer>) subhands.get(j);
            for (int i = 0; i < handList.size(); i = i + 2) {
                int counter = (i / 2 + 1);
                //so add formatting for min spacing/tab stuff
                System.out.printf("%3d. ------------- ", (counter * (j + 1))); //counter + (j * (counter + 7))
            }
            System.out.println();
            for (int i = 0; i < handList.size(); i = i + 2) {

                int s1 = handList.get(i);
                int s2 = handList.get(i + 1);

                System.out.printf("     |%s|%s| ", dominoes[s1][0], dominoes[s2][0]);
            }
            System.out.println();
            for (int i = 0; i < handList.size(); i = i + 2) {
                int s1 = handList.get(i);
                int s2 = handList.get(i + 1);

                System.out.printf("     |%s|%s| ", dominoes[s1][1], dominoes[s2][1]);
            }
            System.out.println();
            for (int i = 0; i < handList.size(); i = i + 2) {

                int s1 = handList.get(i);
                int s2 = handList.get(i + 1);

                System.out.printf("     |%s|%s| ", dominoes[s1][2], dominoes[s2][2]);
            }
            System.out.println();
            for (int i = 0; i < handList.size(); i = i + 2) {
                System.out.print("     ------------- ");
            }
            System.out.println();
        }
    }

    void playPiece(Domino d){
        if(hand.contains(d)){
            hand.remove(d);
        }
    }

    String getName(){return name;}

    boolean getPlayLeftSide(){return playLeftSide;}

    Domino getLastMove(){return myMove;}

    //we'll figure out the winner determination in the individual classes
    abstract boolean isWinner();
    //same for the method to determine how to play a turn
    abstract void playTurn();
}