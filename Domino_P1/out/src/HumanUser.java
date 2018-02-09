//Aislinn Handley
//CS 351L 002

//HumanUser.java
//extends User class

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Scanner;

public class HumanUser extends User{
    //base class fields:
    //private String name;
    //private Board gameboard;
    //private ArrayList<Domino> hand;
    //private boolean playLeftSide;
    //private String[][] dominoes;
    //private Domino myMove;

    private boolean lastMoveOK;
    private Scanner input;
    private boolean draw;
    private boolean drawTurn;
    private HBox displayHand;

    public HumanUser(String n, Board board, HBox dhand){
        //in super:
        //name = n;
        //gameboard = board;
        //hand = new ArrayList();
        //get our initial hand of 5 dominos:
        //for(int i = 0; i < 5; i++)
        //{
        //  Domino d = gameboard.draw();
        //  hand.add(d);
        //}
        super(n, board);
        displayHand = dhand;
        displayHand.setAlignment(Pos.CENTER);
        for(Domino d : hand){
            ImageView temp = new ImageView(d.getImage().getImage());
            temp.setRotate(90);
            displayHand.getChildren().add(temp);
        }
        draw = false;
        //myMove = null;
        lastMoveOK = true;
        drawTurn = false;
        input = new Scanner(System.in);
    }

    //to keep track of our last move's validity
    //for human player only
    public void setLastMoveOkay(boolean okay){
        lastMoveOK = okay;
    }

    //to keep track of whether or not we forfeited, rather than drawing
    //from boneyard
    //for human player only

    public boolean drawTurn(){return drawTurn;}

    @Override
    //human user version of play turn needs to have sufficiently robust i/o checks
    //to prevent the user from entering an invalid piece #(int) and location (string);
    //it only knows that it is the player's turn, and doesn't care whether or not
    //the player has previously made invalid moves, is going to forfeit, etc.
    //it checks if there is a valid move, prompts user to draw if not, and
    //then saves the relevant information for access from GamePlay
    public void playTurn(){
        //first, check if our last move wasn't valid, & if not add the last piece we played back into
        //our hand
        if(!lastMoveOK){
            hand.add(0,myMove);
            System.out.println("Oops, that wasn't a valid move.");
            javafx.scene.image.ImageView handImage = new ImageView(myMove.getImage().getImage());
            handImage.setRotate(90);
            displayHand.getChildren().add(0,handImage);
            drawTurn = true;
            lastMoveOK = true;
            return;
        }
        //Print player's hand:
        System.out.println("Your hand:  ");
        printHand();
        //Then, check if we have a move:
        boolean canMove = false;
        while(!canMove) {
            int l = gameboard.getLmove();
            int r = gameboard.getRmove();
            //if double zeroes
            if (l == 10 || r == 10) {
                canMove = true;
                drawTurn = false;
                break;
            }
            for (int i = 0; i < hand.size(); i++) {
                Domino temp = hand.get(i);
                if(temp.getSideOne() == 0 && temp.getIsDouble()){
                    canMove = true;
                    drawTurn = false;
                    break;
                }
                if (temp.getSideOne() == l || temp.getSideTwo() == l || temp.getSideOne() == r || temp.getSideTwo() == r) {
                    canMove = true;
                    drawTurn = false;
                    break;
                }
            }
            //break out of this loop if we have a move available
            if (!canMove && gameboard.isCanDraw()) {
                //if we get to this point, we don't have a move, so we have to draw
                System.out.print("\nLooks like you don't have any available moves. Would you like to draw from the boneyard?(y/n):  ");
                String discard = input.next();
                if (discard.equals("N") || discard.equals("n")) {
                    System.out.println("Too bad, you have to draw.");
                }
                draw();
                printHand();
                drawTurn = true;
                //return if we use a turn to draw so that our panel updates
                //play turn will get called again until drawTurn == false;
                return;
            }

            //here, there are no moves and no more pieces to draw
            if (!canMove && !gameboard.isCanDraw()) {
                this.draw = true;
                drawTurn = false;
                //null myMove value means I'm out of moves & can't continue playing
                myMove = null;
                return;
            }
        }

        //we now do this earlier in the method, so this section has been commented out
            //System.out.printf("\n%s's hand:  \n", name);
            //printHand();
        //player must select the numbered piece he/she wants to play
        System.out.print("\nWhich piece would you like to play? (enter # of piece):  ");
        boolean check = false;
        int piece = -1;
        Domino move = null;
        while(!check){
            try{
                piece = input.nextInt();
                //if it's a valid int, make sure it's within the range of our hand:
                if(piece <= hand.size() && piece > -1){
                    move = hand.get(piece-1);
                    check = true;
                }
                else{
                    System.out.print("\nSorry, that is not a valid choice. Enter the piece # you would like to play:");
                }
            }

            //catch will catch invalid input, like string vals or other non-int stuff:
            catch(Exception e){
                //'clear buffer' by reading all input up to return char
                //if(input.hasNext()){input.next();}
                System.out.print("\nSorry, that is not a valid choice. Enter the piece # you would like to play:");
            }
            //loop on playTurn() will make sure it's a valid move according to the board
        }

        System.out.print("\nOn which side do you want to place the domino? (L/R): ");
        boolean valid = false;
        while(!valid){
            try{
                String answer = input.next();
                if(answer.equals("L") | answer.equals("l")){valid = true; playLeftSide = true;}
                if(answer.equals("R") | answer.equals("r")){valid = true; playLeftSide = false;}
            }
            //catch will catch non-char input
            catch(Exception e){
                //'clear buffer' by reading all input up to return char
                //if(input.hasNext()){input.next();}
                System.out.print("\nSorry, that is not a valid choice. Enter the side you want to place the domino(L/R):  ");
            }
            //if we get this far, and they haven't entered l/L or r/R, prompt to try again:
            if(!valid){System.out.print("\nSorry, that is not a valid choice. Enter the side you want to place the domino(L/R):  ");}
        }

        //save last move
        myMove = move;
        //remove all images from hand and refresh
        //ObservableList<Node> nodes = displayHand.getChildren();
        //for(int i = 0; i < displayHand.getChildren().size(); i++){
        //    displayHand.getChildren().remove(nodes.get(i));
        //}
        for(int i = displayHand.getChildren().size()-1; i > -1; i--){
            displayHand.getChildren().remove(i);
        }
        //remove move from hand
        playPiece(move);
        //re-add hand images
        for(Domino h : hand){
            ImageView temp = new ImageView(h.getImage().getImage());
            temp.setRotate(90);
            displayHand.getChildren().add(temp);
        }
    }

    //if the hand is empty & can't draw any more dominoes,
    //player wins
    public boolean isWinner(){
        return (hand.isEmpty() && !draw); //winner!
    }

    //overridden here in case we need to add anything at
    //the point we add graphics
    public void printHand(){
        for(int i = 0; i < hand.size(); i++)
        {
            System.out.print( "| " + hand.get(i).getSideOne() + " " + hand.get(i).getSideTwo() + " |");
        }
        System.out.println();
        //System.out.println();
        //super.printHand();
    }

    public void draw(){
        //receives a domino from the gameboard object
        //Domino d = gameboard.draw();
        //hand.add(0,d);
        super.draw();
        //now get the 0th item in our hand and add to displayHand
        ImageView drawn = new ImageView(hand.get(0).getImage().getImage());
        drawn.setRotate(90);
        displayHand.getChildren().add(0,drawn);
    }

    //we don't override gets, playPiece, or draw
}


