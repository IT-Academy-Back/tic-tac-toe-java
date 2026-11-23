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

    // TODO: descriu que representen aquests atributs i què vol dir que siguin "private"
    private int[][] board = createEmptyBoard();
    private String actualStatus = IN_PROGRESS;
    private final Random random = new Random();

    // TODO: descriu que fa aquest mètode línea a línea i què vol dir que sigui "public"
    public GameResponse startGame() {
        System.out.println("Service: starting a new game");
        board = createEmptyBoard();
        actualStatus = IN_PROGRESS;

        return new GameResponse(board, actualStatus, "Game started");
    }

    // TODO: descriu que fa aquest mètode, perquè és públic i des d'on s'utilitza
    public GameResponse playMove(MoveRequest moveRequest) {
        System.out.println("Service: processing player move row=" + moveRequest.getRow() + ", col=" + moveRequest.getCol());

        // TODO: descriu que fa aquest bloc de codi línea a línea
        if (!actualStatus.equals(IN_PROGRESS) || !isValidMove(moveRequest)) {
            System.out.println("Service: invalid move");
            return new GameResponse(board, INVALID_MOVE, "Invalid move");
        }

        // TODO: que fa aquesta línea?
        board[moveRequest.getRow()][moveRequest.getCol()] = PLAYER;

        // TODO: Què comprova aquest bloc de codi, com i que passa?
        if (hasWinner(PLAYER)) {
            actualStatus = PLAYER_WON;

            return new GameResponse(board, actualStatus, "Player won");
        }

        // TODO: Què comprova aquest bloc de codi, com i que passa?
        if (isDraw()) {
            actualStatus = DRAW;

            return new GameResponse(board, actualStatus, "Draw");
        }

        // TODO: Què és això i que fa?
        this.playMachineMove();

        // TODO: Què comprova aquest bloc de codi, com i que passa?
        if (hasWinner(MACHINE)) {
            actualStatus = MACHINE_WON;

            return new GameResponse(board, actualStatus, "Machine won");
        }

        // TODO: Què comprova aquest bloc de codi, com i que passa?
        if (isDraw()) {
            actualStatus = DRAW;

            return new GameResponse(board, actualStatus, "Draw");
        }


        return new GameResponse(board, actualStatus, "Turn processed");
    }

    // TODO: Descriu que fa aquest mètode, quin tipus de dada retorna,
    private boolean isValidMove(MoveRequest moveRequest) {

		return false;
    }

    // TODO: Descriu que fa aquest mètode, quin tipus de dada retorna,
    private boolean isInsideBoard(int row, int col) {
        return row >= 0 && row <= 2 && col >= 0 && col <= 2;
    }

    // TODO: Intenta entendre que fa aquest mètode en general i, si pots, línea a línea
    private void playMachineMove() {
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

    // TODO: Què fa aquest mètode y que representa el seu paràmetre?
    // TODO: Es reutilitza? si es així, desde on?
    private boolean hasWinner(int mark) {
        if (hasWinningRow(mark)) return true;
        if (hasWinningColumn(mark)) return true;
        if (hasWinningDiagonal(mark)) return true;

		return false;
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

    // TODO: que fa aquest mètode?
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

    // TODO: que fa aquest mètode i desde on s'utilitza?
    private int[][] createEmptyBoard() {
        return new int[][]{
                {EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY}
        };
    }

}
