package cat.itacademy.tictactoe.service;

import cat.itacademy.tictactoe.model.GameResponse;
import cat.itacademy.tictactoe.model.MoveRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GameService {

	// Constants del tauler.
	// El frontend espera aquests valors numèrics:
	// 0 = casella buida, 1 = jugador, 2 = màquina.
	private static final int EMPTY = 0;
	private static final int PLAYER = 1;
	private static final int MACHINE = 2;

	// Constants d'estat de la partida.
	// Aquests textos es retornen al frontend dins de la response.
	private static final String IN_PROGRESS = "IN_PROGRESS";
	private static final String PLAYER_WON = "PLAYER_WON";
	private static final String MACHINE_WON = "MACHINE_WON";
	private static final String DRAW = "DRAW";
	private static final String INVALID_MOVE = "INVALID_MOVE";

	// Tauler i estat actual de la partida.
	// Aquest estat es guarda en memòria mentre el backend està en execució.
	private int[][] board = createEmptyBoard();
	private String actualStatus = IN_PROGRESS;
	private final Random random = new Random();

	// Aquest mètode inicia una nova partida.
	// Ja està implementat: no cal modificar aquest mètode.
	public GameResponse startGame() {
		System.out.println("Service: starting a new game");
		board = createEmptyBoard();
		actualStatus = IN_PROGRESS;

		return new GameResponse(board, actualStatus, "Game started");
	}

	// Aquest mètode representa la jugada del jugador i processa la lògica del joc.
	// El controller crida aquest mètode quan el frontend envia POST /game/play.
	public GameResponse playMove(MoveRequest moveRequest) {
		System.out.println("Service: processing player move row=" + moveRequest.getRow() + ", col=" + moveRequest.getCol());

		// TODO: valida que la partida continua i que la jugada del jugador és correcta.
		// Una jugada és invàlida si la partida ja ha acabat, si les coordenades estan fora del tauler
		// o si la casella seleccionada ja està ocupada.
		if (!actualStatus.equals(IN_PROGRESS) || !isValidMove(moveRequest)) {
			System.out.println("Service: invalid move");
			return new GameResponse(board, INVALID_MOVE, "Invalid move");
		}

		// TODO: aplica la jugada del jugador al tauler.
		// El jugador es representa amb el valor 1.
		board[moveRequest.getRow()][moveRequest.getCol()] = PLAYER;

		// TODO: comprova si el jugador ha guanyat després d'aquesta jugada.
		// Si el jugador guanya, la màquina no ha de fer cap jugada més.
		if (hasWinner(PLAYER)) {
			System.out.println("Service: player won");
			actualStatus = PLAYER_WON;
			return new GameResponse(board, actualStatus, "Player won");
		}

		// TODO: comprova si el tauler està ple després de la jugada del jugador.
		if (isDraw()) {
			System.out.println("Service: draw after player move");
			actualStatus = DRAW;
			return new GameResponse(board, actualStatus, "Draw");
		}

		// Ja implementat: la màquina fa una jugada aleatòria en una casella buida.
		// No cal implementar la jugada aleatòria de la màquina.
		playMachineMove();

		// TODO: comprova si la màquina ha guanyat després de la seva jugada.
		if (hasWinner(MACHINE)) {
			System.out.println("Service: machine won");
			actualStatus = MACHINE_WON;
			return new GameResponse(board, actualStatus, "Machine won");
		}

		// TODO: comprova si el tauler està ple després de la jugada de la màquina.
		if (isDraw()) {
			System.out.println("Service: draw after machine move");
			actualStatus = DRAW;
			return new GameResponse(board, actualStatus, "Draw");
		}


		return new GameResponse(board, actualStatus, "Turn processed");
	}

	private boolean isValidMove(MoveRequest moveRequest) {
		// TODO: una jugada vàlida ha d'estar dins del tauler i apuntar a una casella buida.
		// Fes servir isInsideBoard(...) abans de llegir la posició del tauler per evitar índexs invàlids.
		return isInsideBoard(moveRequest.getRow(), moveRequest.getCol())
			&& board[moveRequest.getRow()][moveRequest.getCol()] == EMPTY;
	}

	private boolean isInsideBoard(int row, int col) {
		// TODO: comprova que row i col són coordenades vàlides del tauler.
		// Els valors vàlids són 0, 1 i 2.
		return row >= 0 && row <= 2 && col >= 0 && col <= 2;
	}

	private void playMachineMove() {
		// Ja implementat.
		// Aquest mètode busca totes les caselles buides, en tria una aleatòriament i la marca com a MACHINE.
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
		// TODO: hi ha guanyador si existeix una fila, columna o diagonal guanyadora.
		return hasWinningRow(mark) || hasWinningColumn(mark) || hasWinningDiagonal(mark);
	}

	private boolean hasWinningRow(int mark) {
		// TODO: comprova cada fila i retorna true si una fila conté la mateixa marca tres vegades.
		for (int[] ints : board) {
			if (ints[0] == mark && ints[1] == mark && ints[2] == mark) {
				return true;
			}
		}

		return false;
	}

	private boolean hasWinningColumn(int mark) {
		// TODO: comprova cada columna i retorna true si una columna conté la mateixa marca tres vegades.
		for (int col = 0; col < board.length; col++) {
			if (board[0][col] == mark && board[1][col] == mark && board[2][col] == mark) {
				return true;
			}
		}

		return false;
	}

	private boolean hasWinningDiagonal(int mark) {
		// TODO: comprova les dues diagonals del tauler.
		return board[0][0] == mark && board[1][1] == mark && board[2][2] == mark
			|| board[0][2] == mark && board[1][1] == mark && board[2][0] == mark;
	}

	private boolean isDraw() {
		// TODO: la partida acaba en empat quan no queden caselles buides.
		// Aquest mètode només comprova si el tauler està ple; les comprovacions de victòria es fan abans.
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
		// Ja implementat: crea un nou tauler buit de 3x3.
		return new int[][] {
			{EMPTY, EMPTY, EMPTY},
			{EMPTY, EMPTY, EMPTY},
			{EMPTY, EMPTY, EMPTY}
		};
	}

}
