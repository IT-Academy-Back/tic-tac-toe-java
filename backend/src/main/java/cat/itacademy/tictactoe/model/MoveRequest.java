package cat.itacademy.tictactoe.model;

public class MoveRequest {

	private int row;
	private int col;

	public MoveRequest() {
	}

	public MoveRequest(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
}
