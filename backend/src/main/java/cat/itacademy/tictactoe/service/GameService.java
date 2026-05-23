package cat.itacademy.tictactoe.service;

import cat.itacademy.tictactoe.model.GameResponse;
import cat.itacademy.tictactoe.model.GameStatus;
import org.springframework.stereotype.Service;

@Service
public class GameService {

	private int[][] board = createEmptyBoard();
	private GameStatus status = GameStatus.IN_PROGRESS;

	public GameResponse startGame() {
		board = createEmptyBoard();
		status = GameStatus.IN_PROGRESS;

		return new GameResponse(copyBoard(board), status, "Game started");
	}

	private int[][] createEmptyBoard() {
		return new int[][] {
			{0, 0, 0},
			{0, 0, 0},
			{0, 0, 0}
		};
	}

	private int[][] copyBoard(int[][] source) {
		int[][] copy = new int[source.length][];

		for (int i = 0; i < source.length; i++) {
			copy[i] = source[i].clone();
		}

		return copy;
	}
}
