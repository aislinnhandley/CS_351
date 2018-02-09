//Aislinn Handley
//CS 351L 002

//Board.java
//encapsulates boneyard, checks play validity, and manages arraylist of
//played dominoes

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class Board {

    private ArrayList<Domino> board;
    private String[][] dominoes;
    private Boneyard bones;
    private int lmove;
    private int rmove;
    private boolean canDraw;
    private boolean firstTurn;
    //private int rightCount;
    //replaced by:
    private int addLeft;
    private int addRight;
    private GridPane displayBoard;
    private ImageView spacer;
    private HBox hTop;
    private HBox hBottom;

    //looks a little sloppy, but it defines the output for our domino values
    public Board(GridPane guiBoard) {
        spacer = new ImageView(new Image("00.png", 50, 50 , false, false));
        spacer.setOpacity(0);
        hTop = new HBox();
        hTop.setAlignment(Pos.CENTER_LEFT);
        hTop.setMinHeight(50);
        hTop.setPadding(new Insets(5));
        hTop.getChildren().add(spacer);
        hTop.autosize();
        hBottom = new HBox();
        hBottom.setAlignment(Pos.CENTER_LEFT);
        hBottom.setMinHeight(50);
        hBottom.setPadding(new Insets(5));
        hBottom.autosize();
        displayBoard = guiBoard;
        displayBoard.setGridLinesVisible(false);
        displayBoard.autosize();

        displayBoard.add(hTop,50,10,100,1);
        displayBoard.add(hBottom,50,11,100,1);

        bones = new Boneyard();
        board = new ArrayList();
        firstTurn = true;
        canDraw = true;
        //now represented by only addLeft and addRight
        //rightJustified = true;
        //rightCount = 0;
        addLeft = 0;
        addRight = 0;

/*        dominoes = new String[][]{
                {"     ", "     ", "     "},
                {"     ", "  .  ", "     "},
                {".    ", "     ", "    ."},
                {".    ", "  .  ", "    ."},
                {".   .", "     ", ".   ."},
                {".   .", "  .  ", ".   ."},
                {". . .", "     ", ". . ."}
        };*/

        //go anywhere for first move
        //so give lmove and rmove wildcard values
        lmove = 10;
        rmove = 10;
    }

    //draw() encapsulates our boneyard draw method
    //so the only one accessing the boneyard is the gameboard
    public Domino draw(){
        if (canDraw){
            Domino d = bones.drawOne();
            if(bones.isEmpty()){canDraw = false;}
            return d;
        }
        //if there are no more dominoes left in the boneyard, can't
        //draw one
        //there are other checks in place, but if that fails
        //this method will return null, rather than trying to draw a
        //Domino object that doesn't exist
        System.out.println("Shouldn't be returning null Domino in Board class");
        return null;
    }

    //flips dominos before adding them to the board, as necessary
    //discards the unflipped domino, which should be garbage collected
    private Domino flip(Domino d){
        if(!d.getIsDouble()){
            ImageView tempPic = d.getImage();
            int s1 = d.getSideTwo();
            int s2 = d.getSideOne();
            Domino temp = new Domino(s1,s2);
            return temp;
        }
        return d;
    }

    //add play adds a domino to our ArrayList at the beginning (left side) or end (right side)
    //depending on the player's move
    //if playLeftSide == true, the move is on the left side of the board
    //this method also checks logic for whether or not the move is valid
    //and returns the boolean representation of a successful move
    public boolean addPlay(Domino d, boolean playLeftSide){
        int left_right = (playLeftSide) ? 0 : 1;
        //first, check move is valid
        if(left_right == 0){
            //if it's a double, it can go anywhere
            if(d.getSideOne() == 0 && d.getIsDouble()){
                board.add(0,d);
                //if wildcard, set move = 10
                lmove = 10;
                updateDisplayBoard(d, 0, addLeft);
                if(firstTurn){rmove = 10; firstTurn = false;}
                addLeft++;
                return true;
            }
            //if move == 10, anything can be played off of it
            if(lmove == 10){
                board.add(0,d);
                updateDisplayBoard(d, 0, addLeft);
                if(firstTurn && !(d.getSideOne() == 0 && d.getIsDouble())){rmove = d.getSideTwo();firstTurn = false;}// addRight++;}
                addLeft++;
                lmove = d.getSideOne();
                return true;
            }
            //if there are not matching moves and no wildcard, return false
            if(d.getSideTwo() != lmove && d.getSideOne() != lmove){return false;}
            //if side one matches the left move, flip the domino and play it
            if(d.getSideTwo() != lmove && d.getSideOne() == lmove ){
                d = flip(d);
                board.add(0, d);
                updateDisplayBoard(d, 0, addLeft);
                addLeft++;
                //flipped domino where new side one == old side two
                lmove = d.getSideOne();
                return true;
            }
            //if side two matches the left move, add play and return true
            else if(d.getSideTwo() == lmove){
                board.add(0, d);
                updateDisplayBoard(d, 0, addLeft);
                addLeft++;
                lmove = d.getSideOne();
                return true;
            }

        }
        //ArrayList add() appends element to end of list
        // & thus doesn't need an index
        if(left_right == 1){
            //it it's a double, it can go anywhere
            if(d.getSideOne() == 0 && d.getIsDouble()){
                board.add(d);
                updateDisplayBoard(d, 1, addRight);
                //if wildcard, set move = 10
                rmove = 10;
                if(firstTurn){lmove = 10; firstTurn = false;}
                addRight++;
                return true;
            }
            //if move == 10, anything can be played off of it
            if(rmove == 10){
                board.add(d);
                if(firstTurn && !(d.getSideOne() == 0 && d.getIsDouble())){lmove = d.getSideOne();firstTurn = false;}//addLeft++;}
                updateDisplayBoard(d, 1, addRight);
                rmove = d.getSideTwo();
                addRight++;
                return true;
            }
            //if there are not matching moves and no wildcard, return false
            if(d.getSideOne() != rmove && d.getSideTwo() != rmove){return false;}
            //if side two matches the right move, flip the domino and play it
            if(d.getSideOne() != rmove && d.getSideTwo() == rmove ){
                d = flip(d);
                board.add(d);
                updateDisplayBoard(d, 1, addRight);
                //flipped domino where new side one == old side two
                rmove = d.getSideTwo();
                addRight++;
                return true;
            }
            //if side one matches the right move, add play and return true
            else if(d.getSideOne() == rmove){
                board.add(d);
                updateDisplayBoard(d, 1, addRight);
                rmove = d.getSideTwo();
                addRight++;
                return true;
            }
        }
        //and if by some miracle we get here...
        System.out.println("Error in Board.addPlay() - reaching end of method");
        return false;
    }

    //lmove and rmove hold the available play
    //values for our game
    //so that we don't pass dominoes back and forth
    public int getLmove(){
        return lmove;
    }

    public int getRmove(){
        return rmove;
    }

    //returns whether or not boneyard is empty
    public boolean isCanDraw(){
        //that's... not a good name
        return canDraw;
    }

    private void updateDisplayBoard(Domino d, int side, int addSide){
        //checks what side we add the domino (0  == left, 1 == right)
        //then checks for addLeft or addRight values
        // (addSide%2 == 0 means add to bottom row)
        if(side == 0){
            if(addSide%2 == 0){
                hTop.getChildren().remove(spacer);
                hTop.getChildren().add(0, d.getImage());
                hBottom.getChildren().add(0, spacer);
                return;
            }
            else{
                hBottom.getChildren().remove(spacer);
                hBottom.getChildren().add(0, d.getImage());
                hTop.getChildren().add(0, spacer);
                return;
            }
        }
        if(side == 1){
            if(addSide%2 == 0){
                int last = hBottom.getChildren().size();
                hBottom.getChildren().add(last, d.getImage());
                return;
            }
            else{
                int last = hTop.getChildren().size();
                hTop.getChildren().add(last, d.getImage());
                return;
            }
        }
    }

    //handles console printing of gameplay
    //very large method, does one very small task
    public void printBoard(){
        //our top is right justified if we have 0%2 moves to the right side of the board
//        boolean rightJust = (addLeft%2 == 0);
//        String t1 = "";
//        String t2 = "";
//        if(rightJust) { t2 = "    ";}
//        else{ t1 = "    ";}

        String t1 = "";
        String t2 = "    ";

        //Map dictionary = bones.getDictionary();
        int size = board.size();
        String[] dominoKeys = new String[size];
        for(int i = 0; i < board.size(); i++){
            Domino temp = board.get(i);
            String s1 = Integer.toString(temp.getSideOne());
            String s2 = Integer.toString(temp.getSideTwo());
            String key = "| "+ s1+ "   " + s2 + " |";
            dominoKeys[i] = key;
        }

        System.out.print(t2);
        for(int i = 1; i < dominoKeys.length; i=i+2){
            System.out.print(dominoKeys[i]);
        }
        System.out.println();

        System.out.print(t1);
        for(int i = 0; i < dominoKeys.length; i=i+2){
            System.out.print(dominoKeys[i]);
        }


        //<editor-fold desc="Old PrintBoard Method">
        /*
        //size = size*2;
        int[] boardList = new int[size];
        Object[] tempBoard = board.toArray();

        int count = 0;
        for(Object temp: tempBoard){
            Domino d = (Domino) temp;
            int s1 = d.getSideOne();
            int s2 = d.getSideTwo();
            boardList[count] = s1;
            boardList[count + 1] = s2;
            count += 2;
        }

        //because our dominoes display in alternating rows,
        //our output will be offset
        //evens on top row
        //odds on bottom row

        //TOP ROW
        System.out.print(t1);
        for(int i = 0; i < boardList.length; i=i+4){
            System.out.printf("-------------");
        }
        System.out.println();
        System.out.print(t1);
        for(int i = 0; i < boardList.length; i=i+4) {

            int s1 = boardList[i];
            int s2 = boardList[i + 1];

            System.out.printf("|%s|%s|", dominoes[s1][0], dominoes[s2][0]);
        }
        System.out.println();
        System.out.print(t1);
        for(int i = 0; i < boardList.length; i=i+4) {

            int s1 = boardList[i];
            int s2 = boardList[i + 1];

            System.out.printf("|%s|%s|", dominoes[s1][1], dominoes[s2][1]);
        }
        System.out.println();
        System.out.print(t1);
        for(int i = 0; i < boardList.length; i=i+4) {

            int s1 = boardList[i];
            int s2 = boardList[i + 1];

            System.out.printf("|%s|%s|", dominoes[s1][2], dominoes[s2][2]);
        }
        System.out.println();

        System.out.print(t1);
        for(int i = 0; i < boardList.length; i=i+4){
            System.out.printf("-------------");
        }
        System.out.println();

        //BOTTOM ROW
        System.out.print(t2);
        for(int i = 2; i < boardList.length; i=i+4){
            System.out.printf("-------------");
        }
        System.out.println();
        System.out.print(t2);
        for(int i = 2; i < boardList.length; i=i+4) {

            int s1 = boardList[i];
            int s2 = boardList[i + 1];

            System.out.printf("|%s|%s|", dominoes[s1][0], dominoes[s2][0]);
        }
        System.out.println();
        System.out.print(t2);
        for(int i = 2; i < boardList.length; i=i+4) {

            int s1 = boardList[i];
            int s2 = boardList[i + 1];

            System.out.printf("|%s|%s|", dominoes[s1][1], dominoes[s2][1]);
        }
        System.out.println();
        System.out.print(t2);
        for(int i = 2; i < boardList.length; i=i+4) {

            int s1 = boardList[i];
            int s2 = boardList[i + 1];

            System.out.printf("|%s|%s|", dominoes[s1][2], dominoes[s2][2]);
        }
        System.out.println();
        System.out.print(t2);
        for(int i = 2; i < boardList.length; i=i+4){
            System.out.printf("-------------");
        }
        */
        //</editor-fold>

        System.out.println();
    }

}

