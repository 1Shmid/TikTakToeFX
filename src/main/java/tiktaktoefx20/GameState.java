package tiktaktoefx20;



// класс для представления состояния игры

public class GameState {


    private char[][] gameField;
    private char currentPlayer;

    public GameState(char[][] gameField, char currentPlayer) {
        this.gameField = gameField;
        this.currentPlayer = currentPlayer;
    }

    public char[][] getGameField() {
        return gameField;
    }

    public void setGameField(char[][] gameField) {
        this.gameField = gameField;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(char currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}