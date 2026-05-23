export type CellValue = 0 | 1 | 2
export type Board = CellValue[][]
export type GameStatus =
  | 'IN_PROGRESS'
  | 'PLAYER_WON'
  | 'MACHINE_WON'
  | 'DRAW'
  | 'INVALID_MOVE'

export type MoveRequest = {
  row: number
  col: number
}

export type GameState = {
  board: Board
  status: GameStatus
  message: string
}

export const cellLabel: Record<CellValue, string> = {
  0: '',
  1: 'X',
  2: 'O',
}
