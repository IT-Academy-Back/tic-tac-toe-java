package cat.itacademy.tictactoe.service;

import cat.itacademy.tictactoe.model.GameResponse;
import cat.itacademy.tictactoe.model.MoveRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GameService {

	// Board cell constants
	private static final int EMPTY = 0;
	private static final int PLAYER = 1;
	private static final int MACHINE = 2;

	// Game status constants
	private static final String IN_PROGRESS = "IN_PROGRESS";
	private static final String PLAYER_WON = "PLAYER_WON";
	private static final String MACHINE_WON = "MACHINE_WON";
	private static final String DRAW = "DRAW";
	private static final String INVALID_MOVE = "INVALID_MOVE";

	// Actual game board and status
	private int[][] board = createEmptyBoard();
	private String actualStatus = IN_PROGRESS;
	private final Random random = new Random();

	// This method starts a new game
	public GameResponse startGame() {
		System.out.println("Service: starting a new game");
		board = createEmptyBoard();
		actualStatus = IN_PROGRESS;

		return new GameResponse(board, actualStatus, "Game started");
	}

	// This method represents the player move and processes the game logic
	public GameResponse playMove(MoveRequest moveRequest) {
		System.out.println("Service: processing player move row=" + moveRequest.getRow() + ", col=" + moveRequest.getCol());

		if (!actualStatus.equals(IN_PROGRESS) || !isValidMove(moveRequest)) {
			System.out.println("Service: invalid move");
			return new GameResponse(board, INVALID_MOVE, "Invalid move");
		}

		board[moveRequest.getRow()][moveRequest.getCol()] = PLAYER;

		if (hasWinner(PLAYER)) {
			System.out.println("Service: player won");
			actualStatus = PLAYER_WON;
			return new GameResponse(board, actualStatus, "Player won");
		}

		if (isDraw()) {
			System.out.println("Service: draw after player move");
			actualStatus = DRAW;
			return new GameResponse(board, actualStatus, "Draw");
		}

		playMachineMove();

		if (hasWinner(MACHINE)) {
			System.out.println("Service: machine won");
			actualStatus = MACHINE_WON;
			return new GameResponse(board, actualStatus, "Machine won");
		}

		if (isDraw()) {
			System.out.println("Service: draw after machine move");
			actualStatus = DRAW;
			return new GameResponse(board, actualStatus, "Draw");
		}


		return new GameResponse(board, actualStatus, "Turn processed");
	}

	private boolean isValidMove(MoveRequest moveRequest) {
		return isInsideBoard(moveRequest.getRow(), moveRequest.getCol())
			&& board[moveRequest.getRow()][moveRequest.getCol()] == EMPTY;
	}

	private boolean isInsideBoard(int row, int col) {
		return row >= 0 && row <= 2 && col >= 0 && col <= 2;
	}

	private void playMachineMove() {
		System.out.println("Service: calculating machine random move");
		List<MoveRequest> availableMoves = new ArrayList<>();

		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				if (board[row][col] == EMPTY) {
					availableMoves.add(new MoveRequest(row, col));
				}
			}
		}

		MoveRequest machineMove = availableMoves.get(random.nextInt(availableMoves.size()));
		System.out.println("Service: machine plays row=" + machineMove.getRow() + ", col=" + machineMove.getCol());
		board[machineMove.getRow()][machineMove.getCol()] = MACHINE;
	}

	private boolean hasWinner(int mark) {
		return hasWinningRow(mark) || hasWinningColumn(mark) || hasWinningDiagonal(mark);
	}

	private boolean hasWinningRow(int mark) {
        for (int[] ints : board) {
            if (ints[0] == mark && ints[1] == mark && ints[2] == mark) {
                return true;
            }
        }

		return false;
	}

	private boolean hasWinningColumn(int mark) {
		for (int col = 0; col < board.length; col++) {
			if (board[0][col] == mark && board[1][col] == mark && board[2][col] == mark) {
				return true;
			}
		}

		return false;
	}

	private boolean hasWinningDiagonal(int mark) {
		return board[0][0] == mark && board[1][1] == mark && board[2][2] == mark
			|| board[0][2] == mark && board[1][1] == mark && board[2][0] == mark;
	}

	private boolean isDraw() {
		for (int[] row : board) {
			for (int cell : row) {
				if (cell == EMPTY) {
					return false;
				}
			}
		}

		return true;
	}

	private int[][] createEmptyBoard() {
		return new int[][] {
			{EMPTY, EMPTY, EMPTY},
			{EMPTY, EMPTY, EMPTY},
			{EMPTY, EMPTY, EMPTY}
		};
	}

}
