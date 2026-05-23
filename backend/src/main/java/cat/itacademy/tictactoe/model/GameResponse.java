package cat.itacademy.tictactoe.model;

public class GameResponse {

	private int[][] board;
	private String status;
	private String message;

	public GameResponse(int[][] board, String status, String message) {
		this.board = board;
		this.status = status;
		this.message = message;
	}

	public int[][] getBoard() {
		return board;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
}
