import { cellLabel, type Board } from '../types/game'

type GameBoardProps = {
  board: Board
  disabled: boolean
  onCellClick: (row: number, col: number) => void
}

export function GameBoard({ board, disabled, onCellClick }: GameBoardProps) {
  return (
    <section className="board-stage" aria-label="Tic-tac-toe board">
      <div className="paper-sheet">
        <div className="smudge smudge-one" />
        <div className="smudge smudge-two" />
        <div className="board-grid">
          {board.map((line, rowIndex) =>
            line.map((cell, colIndex) => (
              <button
                className={`cell ${cell === 1 ? 'cell-x' : ''} ${
                  cell === 2 ? 'cell-o' : ''
                }`}
                key={`${rowIndex}-${colIndex}`}
                type="button"
                disabled={disabled || cell !== 0}
                aria-label={`Row ${rowIndex + 1}, column ${colIndex + 1}`}
                onClick={() => onCellClick(rowIndex, colIndex)}
              >
                {cellLabel[cell]}
              </button>
            )),
          )}
        </div>
      </div>
    </section>
  )
}
