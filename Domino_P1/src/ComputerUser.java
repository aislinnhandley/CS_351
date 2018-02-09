//Aislinn Handley
//CS 351L 002

//ComputerUser.java
//extends User class
//version of user class without any input/output controls

public class ComputerUser extends User{
    //base class fields:
    //private String name;
    //private Board gameboard;
    //private ArrayList<Domino> hand;
    //private boolean playLeftSide;
    //private String[][] dominoes;
    //private Domino myMove;

    private boolean draw;

    public ComputerUser(String n, Board board){
        //in base constructor:
        //name = n;
        //gameboard = board;
        //initialize hand
        //'draw' dominoes
        super(n, board);
        this.draw = false;
    }

    @Override
    //draws domino from gameboard and adds it to the top
    //of our hand
    public void draw(){
        Domino d = gameboard.draw();
        hand.add(0,d);
    }

    //computer user will play the first available move that it finds, & does not check
    //for optimal move; there is no included logic for checking i/o, and the computer user
    //cannot forfeit a turn by refusing to draw from the boneyard
    //it does contain the necessary logic to determine if it can move, can draw from the boneyard,
    //or if it cannot place a piece
    public void playTurn(){
        //check for first available move by comparing the hand values to our
        //int l, int r values
        int l = gameboard.getLmove();
        int r = gameboard.getRmove();
        boolean canGo = false;
        while(!canGo){
            //if double zeroes
            if(l == 10 || r == 10){
                canGo = true;
                myMove = hand.get(0);
                if(l == 10){playLeftSide = true;}
                else{playLeftSide = false;}
                break;
            }
            for(int i = 0; i < hand.size(); i++){
                Domino temp = hand.get(i);
                if(temp.getSideOne() == 0 && temp.getIsDouble()){
                    playLeftSide = true;
                    myMove = temp;
                    canGo = true;
                }
                //Computer doesn't get to be smart, so it plays the absolute first space available
                if(temp.getSideOne() == l | temp.getSideTwo() == l){
                    playLeftSide = true;
                    myMove = temp;
                    canGo = true;}
                else if(temp.getSideOne() == r | temp.getSideTwo() == r){
                    playLeftSide = false;
                    myMove = temp;
                    canGo = true;}
                if(canGo){
                    //as soon as we find a move, break
                    break;
                }
            }

            //if no move available, start drawing dominoes
            if(!canGo && gameboard.isCanDraw()){
                draw();
            }
            //here, there are no moves and no more pieces to draw
            if(!canGo && !gameboard.isCanDraw()){
                draw = true;
                //null myMove value means I'm out of moves & can't continue playing
                myMove = null;
                return;
            }
        }
        //remove played piece from hand
        playPiece(myMove);
    }

    //if the hand is empty & can't draw any more dominoes,
    //player wins
    public boolean isWinner(){
        return (hand.isEmpty() && !draw); //winner!
    }

    //overidden here in case we need to change anything at
    //the point we add graphics
    public void printHand(){
        super.printHand();
    }

    //we do not override gets, playPiece, printHand
}