import { useEffect, useRef, useState } from 'react'
import { GameBoard } from './components/GameBoard'
import { IntroPanel } from './components/IntroPanel'
import { playMove, startGame } from './services/gameService'
import type { GameState } from './types/game'
import './App.css'

function App() {
  const [gameState, setGameState] = useState<GameState | null>(null)
  const [isLoading, setIsLoading] = useState(false)
  const hasStartedGame = useRef(false)

  useEffect(() => {
    if (hasStartedGame.current) return

    hasStartedGame.current = true
    resetSketch()
  }, [])

  async function playCell(row: number, col: number) {
    if (isLoading || gameState === null || gameState.board[row][col] !== 0) return

    setIsLoading(true)
    try {
      const nextGameState = await playMove({ row, col })
      setGameState(nextGameState)
    } finally {
      setIsLoading(false)
    }
  }

  async function resetSketch() {
    setIsLoading(true)
    try {
      const nextGameState = await startGame()
      setGameState(nextGameState)
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <main className="page-shell">
      <IntroPanel
        status={gameState?.status ?? 'LOADING'}
        message={gameState?.message ?? 'Starting game...'}
        onReset={resetSketch}
      />
      {gameState !== null && (
        <GameBoard
          board={gameState.board}
          disabled={isLoading || !['IN_PROGRESS', 'INVALID_MOVE'].includes(gameState.status)}
          onCellClick={playCell}
        />
      )}
    </main>
  )
}

export default App
