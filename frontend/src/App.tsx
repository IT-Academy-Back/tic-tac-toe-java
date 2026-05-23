import { useState } from 'react'
import { GameBoard } from './components/GameBoard'
import { IntroPanel } from './components/IntroPanel'
import { initialGameState, playMove, startGame } from './services/gameService'
import type { GameState } from './types/game'
import './App.css'

function App() {
  const [gameState, setGameState] = useState<GameState>(initialGameState)
  const [isLoading, setIsLoading] = useState(false)

  async function playCell(row: number, col: number) {
    if (isLoading || gameState.board[row][col] !== 0) return

    setIsLoading(true)
    const nextGameState = await playMove({ row, col })
    setGameState(nextGameState)
    setIsLoading(false)
  }

  async function resetSketch() {
    setIsLoading(true)
    const nextGameState = await startGame()
    setGameState(nextGameState)
    setIsLoading(false)
  }

  return (
    <main className="page-shell">
      <IntroPanel
        status={gameState.status}
        message={gameState.message}
        onReset={resetSketch}
      />
      <GameBoard
        board={gameState.board}
        disabled={isLoading || gameState.status !== 'IN_PROGRESS'}
        onCellClick={playCell}
      />
    </main>
  )
}

export default App
