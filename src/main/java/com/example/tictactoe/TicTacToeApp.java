package com.example.tictactoe;

import com.example.tictactoe.model.GameResult;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class TicTacToeApp extends Application {

    private char currentPlayer = 'X';
    private Button[][] buttons = new Button[3][3];

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button();
                buttons[i][j].setMinSize(100, 100);
                final int x = i;
                final int y = j;
                buttons[i][j].setOnAction(e -> makeMove(x, y));
                grid.add(buttons[i][j], j, i);
            }
        }

        Scene scene = new Scene(grid, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.show();
    }

    private void makeMove(int x, int y) {
        if (buttons[x][y].getText().isEmpty()) {
            buttons[x][y].setText(String.valueOf(currentPlayer));
            if (checkWin()) {
                saveGameResult(currentPlayer + " wins!");
                resetBoard();
            } else if (isBoardFull()) {
                saveGameResult("Draw");
                resetBoard();
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[i][2].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
        }
        for (int j = 0; j < 3; j++) {
            if (buttons[0][j].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[1][j].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[2][j].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
        }
        if (buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][2].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }
        if (buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][0].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        currentPlayer = 'X';
    }

    private void saveGameResult(String result) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/results";

        GameResult gameResult = new GameResult();
        gameResult.setPlayer(currentPlayer == 'X' ? "Player 1" : "Player 2");
        gameResult.setResult(result);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GameResult> request = new HttpEntity<>(gameResult, headers);
        restTemplate.postForObject(url, request, GameResult.class);
    }

   /* public static void main(String[] args) {
        launch(args);
    }
    */
    public static void main(String[] args) {
        Application.launch(TicTacToeApp.class, args);
    }
}
