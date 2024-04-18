package tiktaktoefx20.database;

public class GameStats {
    private int totalGames;
    private int totalDuration;
    private int longestGame;
    private int shortestGame;
    private int totalWins;
    private int playerWins;
    private int computerWins;

    public GameStats(int totalGames, int totalDuration, int longestGame, int shortestGame, int totalWins, int playerWins, int computerWins) {
        this.totalGames = totalGames;
        this.totalDuration = totalDuration;
        this.longestGame = longestGame;
        this.shortestGame = shortestGame;
        this.totalWins = totalWins;
        this.playerWins = playerWins;
        this.computerWins = computerWins;
    }

    // Геттеры и сеттеры
}
