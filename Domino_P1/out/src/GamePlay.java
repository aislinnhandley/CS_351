//Aislinn Handley
//CS 351L 002

//GamePlay.java
//contains the mechanics for alternating turns
//and for creating our users and gameboard

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class GamePlay{
    private boolean firstMove;
    private int counter;

    private ComputerUser terminator;
    private HumanUser player1;
    private Board gameboard;
    private User[] users;
    private User lastplayer;

    private BorderPane displayHand;
    private GridPane displayBoard;
    private HBox handBox;

    public GamePlay(BorderPane hand, GridPane board){
        firstMove = true;
        counter = 0;

        displayHand = hand;
        displayBoard = board;
        gameboard = new Board(displayBoard);
        users = new User[2];
        //right now, users are hardcoded to limit need for console input
        handBox = new HBox(10);
        handBox.setMinHeight(150);
        handBox.setPadding(new Insets(15));
        //handBox.autosize();
        handBox.setPrefWidth(displayBoard.getWidth());
        //Paint stroke, BorderStrokeStyle style, CornerRadii radii, BorderWidths widths
        //handBox.setBorder(new Border(new BorderStroke(Paint.valueOf("0a0909"), BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        ScrollPane scrollable = new ScrollPane();
        scrollable.setFitToWidth(true);
        scrollable.setVmax(handBox.getHeight());
        scrollable.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollable.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollable.setContent(handBox);
        player1 = new HumanUser("Player 1", gameboard, handBox);
        displayHand.setCenter(scrollable);
        terminator = new ComputerUser("Computer", gameboard);
        users[0] = player1;
        users[1] = terminator;

    }

    //play() will loop until one of its return statements is executed
    //by an outright win (empty hand) or by the other player's inability to play
    // where winner is the last person to play (there is not case for a draw)
    //it loops through each player in an array of Users, and calls more robust check
    //logic for human users, & no check logic for computer players

    //renamed from v1 to reflect changes to overall structure]
    //previously called play()
    public boolean playTurn(){

        //if it's the first move, our last played piece will be null
        //but after that, the only time last played piece is null
        //is if a User cannot make a move
        //boolean firstMove = true;
        //loop until we reach a return statement
        //remove: for(int i = 0; i < users.length; i++){
        //instead, use counter to determine which user's turn it is
        //where counter%2 will return 0 or 1 for our index

        int i = counter%2;
            if(users[i] instanceof ComputerUser){
                ComputerUser computer = (ComputerUser)users[i];
                //check if last player went
                if(!firstMove && lastplayer.getLastMove() == null){
                    System.out.printf("\n%s unable to make a move. %s wins!", lastplayer.getName(), computer.getName());
                }
                System.out.printf("\n%s's turn! %n", computer.getName());
                computer.playTurn();
                firstMove = false;
                lastplayer = computer;
                //won't use the return here because it doesn't give us anything important
                //use only for testing purposes, where it will show us logic errors
                //boolean discardResult =
                gameboard.addPlay(computer.getLastMove(), computer.getPlayLeftSide());
                if(computer.isWinner()){
                    System.out.printf("\n%s wins!!!",computer.getName());
                    return true;
                }
            }
            else if(users[i] instanceof HumanUser){
                gameboard.printBoard();
                HumanUser human = (HumanUser)users[i];
                //check if other player was unable to move
                if(!firstMove && lastplayer.getLastMove() == null){
                    System.out.printf("\n%s unable to make a move. %s wins!", lastplayer.getName(), human.getName());
                }
                //try to get a valid move from human player
                //and loop until he/she provides one or skips
                //boolean valid;
                human.playTurn();
//                do{
//                    human.playTurn();
//                    //if we forfeit a turn, we don't want to add a move to the gameboard
//                    //so check that condition before placing a piece on the board
//                    if(!human.getForfeitTurn()) {
//                        valid = gameboard.addPlay(human.getLastMove(), human.getPlayLeftSide());
//                        human.setLastMoveOkay(valid);
//                    }
//                    //forfeiting is a valid move, so exit the loop by setting valid = true
//                    else{
//                        valid = true;
//                    }
//                }while (valid == false);
                //if our move isn't valid, or
                //if we only used our turn to draw, we get to go again
                // to do this, we will decrement our counter to keep ourselves the current
                // player
                //also, lastplayer should remain as the other player
                boolean didDraw = human.drawTurn();
                System.out.println(didDraw);
                boolean validMove = true;
                if(!didDraw){ validMove = gameboard.addPlay(human.getLastMove(), human.getPlayLeftSide());}
                //if(!didDraw && !validMove){};
                if(didDraw || !validMove){
                    if(!validMove && !didDraw){
                        System.out.println("Oops, that wasn't a valid move! Please try again.\n");
                        human.setLastMoveOkay(validMove);
                    }
                    counter--;
                }
                else{
                    lastplayer = human;
                }
                firstMove = false;
                if(human.isWinner()){
                    System.out.printf("\n%s wins!!!",human.getName());
                    return false;
                }
            }
        //}
        counter++;
        return true;
    }

    public GridPane getDisplayBoard() {
        return displayBoard;
    }

    public BorderPane getDisplayHand() {
        return displayHand;
    }

    public String getWinner(){
        return lastplayer.name;
    }
}



