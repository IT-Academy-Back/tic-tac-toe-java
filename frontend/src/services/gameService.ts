import type { Board, CellValue, GameState, MoveRequest } from '../types/game'

const PLAYER: CellValue = 1
const MACHINE: CellValue = 2

export const initialGameState: GameState = {
  board: createEmptyBoard(),
  status: 'IN_PROGRESS',
  message: 'Choose a square to start the game.',
}

let fakeGameState = cloneGameState(initialGameState)

export async function startGame(): Promise<GameState> {
  const response = await fetch('/game/start', { method: 'POST' })

  if (!response.ok) {
    throw new Error('Could not start the game')
  }

  fakeGameState = await response.json() as GameState
  return cloneGameState(fakeGameState)
}

export async function playMove(move: MoveRequest): Promise<GameState> {
  if (fakeGameState.status !== 'IN_PROGRESS') {
    return cloneGameState(fakeGameState)
  }

  if (!isInsideBoard(move.row, move.col) || fakeGameState.board[move.row][move.col] !== 0) {
    fakeGameState = {
      ...fakeGameState,
      message: 'That square is not available.',
    }
    return cloneGameState(fakeGameState)
  }

  const boardAfterPlayer = placeMark(fakeGameState.board, move.row, move.col, PLAYER)

  if (hasWinner(boardAfterPlayer, PLAYER)) {
    fakeGameState = {
      board: boardAfterPlayer,
      status: 'PLAYER_WON',
      message: 'You won. Nice move.',
    }
    return cloneGameState(fakeGameState)
  }

  if (isDraw(boardAfterPlayer)) {
    fakeGameState = {
      board: boardAfterPlayer,
      status: 'DRAW',
      message: 'The board is full. Draw.',
    }
    return cloneGameState(fakeGameState)
  }

  const boardAfterMachine = playRandomMachineMove(boardAfterPlayer)

  if (hasWinner(boardAfterMachine, MACHINE)) {
    fakeGameState = {
      board: boardAfterMachine,
      status: 'MACHINE_WON',
      message: 'The machine won this sketch.',
    }
    return cloneGameState(fakeGameState)
  }

  fakeGameState = {
    board: boardAfterMachine,
    status: isDraw(boardAfterMachine) ? 'DRAW' : 'IN_PROGRESS',
    message: isDraw(boardAfterMachine) ? 'The board is full. Draw.' : 'Turn processed.',
  }

  return cloneGameState(fakeGameState)
}

function createEmptyBoard(): Board {
  return [
    [0, 0, 0],
    [0, 0, 0],
    [0, 0, 0],
  ]
}

function cloneGameState(gameState: GameState): GameState {
  return {
    ...gameState,
    board: gameState.board.map((line) => [...line]),
  }
}

function isInsideBoard(row: number, col: number) {
  return row >= 0 && row <= 2 && col >= 0 && col <= 2
}

function placeMark(board: Board, row: number, col: number, mark: CellValue): Board {
  return board.map((line, rowIndex) =>
    line.map((cell, colIndex) => (rowIndex === row && colIndex === col ? mark : cell)),
  )
}

function playRandomMachineMove(board: Board): Board {
  const emptyCells = board.flatMap((line, row) =>
    line.flatMap((cell, col) => (cell === 0 ? [{ row, col }] : [])),
  )

  const move = emptyCells[Math.floor(Math.random() * emptyCells.length)]
  return move ? placeMark(board, move.row, move.col, MACHINE) : board
}

function hasWinner(board: Board, mark: CellValue) {
  return [
    board[0],
    board[1],
    board[2],
    [board[0][0], board[1][0], board[2][0]],
    [board[0][1], board[1][1], board[2][1]],
    [board[0][2], board[1][2], board[2][2]],
    [board[0][0], board[1][1], board[2][2]],
    [board[0][2], board[1][1], board[2][0]],
  ].some((line) => line.every((cell) => cell === mark))
}

function isDraw(board: Board) {
  return board.every((line) => line.every((cell) => cell !== 0))
}
