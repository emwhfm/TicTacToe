/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tictactoegui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 *
 * @author emwhfm
 */
public class TicTacToeGUI extends Application {

    // Indicate which player has a turn, initially it is the X player
    private char whoseTurn = 'X';

    // Create and initialize cell
    private final Cell[][] cell = new Cell[3][3];

    // Create and initialize a status label
    private final Label lblStatus = new Label("X's turn to play");
        
    @Override
    public void start(Stage primaryStage) {

        // Pane to hold cell
        GridPane pane = new GridPane();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                pane.add(cell[i][j] = new Cell(), j, i);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(lblStatus);
        BorderPane.setAlignment(lblStatus, Pos.CENTER);

        // Create a scene and place it in the stage
        Scene scene = new Scene(borderPane, 450, 170);
        primaryStage.setTitle("TicTacToe"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage        
    }

    /** 
     * Determine if the cell are all occupied
     * @return game board is full
     */
    public boolean isFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (cell[i][j].getToken() == ' ')
                    return false;
        return true;
    }
    
    /** 
     * Determine if the player with the specified token wins 
     * @param  token current player
     * @return       has current player won
     */
    public boolean isWon(char token) {
        for (int i = 0; i < 3; i++)
            if (cell[i][0].getToken() == token
                    && cell[i][1].getToken() == token
                    && cell[i][2].getToken() == token) {
                return true;
            }

        for (int j = 0; j < 3; j++)
            if (cell[0][j].getToken() == token
                    && cell[1][j].getToken() == token
                    && cell[2][j].getToken() == token) {
                return true;
            }
        
        if (cell[0][0].getToken() == token
                && cell[1][1].getToken() == token
                && cell[2][2].getToken() == token) {
            return true;
        }
        
        if (cell[0][2].getToken() == token
                && cell[1][1].getToken() == token
                && cell[2][0].getToken() == token) {
            return true;
        }
        
        return false;
    }
    
    // An inner class for a cell
    public class Cell extends StackPane {
        // Token used for this cell
        private char token = ' ';

        public Cell() {
            setStyle("-fx-border-color: black");
            this.setPrefSize(2000, 2000);
            this.setOnMouseClicked(e -> handleMouseClick());            
        }
        
        /** 
         * Return token
         * @return actual token in cell
         */
        public char getToken() {
            return token;
        }
        
        /** 
         * Set a new token
         * @param c token of claimer of cell
         */
        public void setToken(char c) {
            token = c;
            if (token == 'X') {    
                // Add the 'x' to the pane
                ImageView ivx = new ImageView("image/x.gif");
                ivx.fitWidthProperty().bind(this.widthProperty().divide(2));
                ivx.fitHeightProperty().bind(this.heightProperty().divide(2));
                this.getChildren().add(ivx);              
            }
            else if (token == 'O') {
                // Add the 'o' to the pane
                ImageView ivy = new ImageView("image/o.gif");
                ivy.fitWidthProperty().bind(this.widthProperty().divide(2));
                ivy.fitHeightProperty().bind(this.heightProperty().divide(2));
                this.getChildren().add(ivy);
            }
        }
        
        /* Handle a mouse click event */
        private void handleMouseClick() {
            // If cell is empty and game is not over
            if (token == ' ' && whoseTurn != ' ') {
                setToken(whoseTurn); // Set token in the cell
                // Check game status
                if (isWon(whoseTurn)) {
                    lblStatus.setText(whoseTurn + " won! The game is over");
                    whoseTurn = ' '; // Game is over
                }
                else if (isFull()) {
                    lblStatus.setText("Draw! The game is over");
                    whoseTurn = ' '; // Game is over
                }
                else {
                    // Change the turn
                    whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
                    // Display whose turn
                    lblStatus.setText(whoseTurn + "'s turn");
                }
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}