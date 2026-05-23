export type CellValue = 0 | 1 | 2
export type Board = CellValue[][]
export type MoveRequest = {
  row: number
  col: number
}

export type GameState = {
  board: Board
  status: string
  message: string
}

export const cellLabel: Record<CellValue, string> = {
  0: '',
  1: 'X',
  2: 'O',
}
