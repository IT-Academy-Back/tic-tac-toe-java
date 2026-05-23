package cat.itacademy.tictactoe.model;

public record GameResponse(int[][] board, GameStatus status, String message) {
}
