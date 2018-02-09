//Aislinn Handley
//CS 351L 002

//DominoGame.java
//starts the GamePlay

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static javafx.application.Platform.exit;

public class DominoGame extends javafx.application.Application{

    private static GamePlay game;
    private BorderPane root;
    private BorderPane hand;
    private GridPane board;
    private ScrollPane scrollBoard;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        AnimationTimer play;

        //Image img = new Image("00.png",150,300,true,true);
        ///ImageView test = new ImageView(img);
        //Label testLabel = new Label("Show up");
        root = new BorderPane();

        scrollBoard = new ScrollPane();
        scrollBoard.setFitToWidth(true);
        scrollBoard.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollBoard.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        board = new GridPane();
        board.setAlignment(Pos.CENTER);
        board.setGridLinesVisible(true);
        board.setCenterShape(true);
        //scrollBoard.setVmax(board.getHeight()+50);
        scrollBoard.setMinHeight(board.getHeight()+150);
        scrollBoard.setContent(board);

        hand = new BorderPane();
        hand.setMinHeight(150);
        game = new GamePlay(hand, board);
        root.autosize();
        root.setBottom(hand);
        root.setTop(scrollBoard);

        Scene mainScene = new Scene(root, 850, 300, Color.PEACHPUFF);

        primaryStage.setTitle("Dominoes V2");
        primaryStage.setScene(mainScene);
        primaryStage.show();

        play = new AnimationTimer(){
            Alert winner = new Alert(Alert.AlertType.CONFIRMATION, "Game Over!", ButtonType.OK);
            boolean isStart = false;

            @Override
            public void handle(long now){
                //board = game.getDisplayBoard();
                //hand = game.getDisplayHand();
                if(!isStart){
                    isStart = true;
                }
                else{
                    boolean keepGoing = game.playTurn();
                    if (!keepGoing){

                        //Alert(Alert.AlertType alertType, String contentText, ButtonType... buttons)
                        winner.setTitle(String.format("%s wins!!!", game.getWinner()));
                        winner.show();
                        stop();
                        exit();
                    }
                }
            }
        };

        play.start();

    }
}