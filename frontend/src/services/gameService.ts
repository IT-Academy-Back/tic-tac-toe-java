import type { GameState, MoveRequest } from '../types/game'

const API_URL = 'http://localhost:8080'

export async function startGame(): Promise<GameState> {
  const response = await fetch(`${API_URL}/game/start`, { method: 'POST' })

  if (!response.ok) {
    throw new Error('Could not start the game')
  }

  return await response.json() as GameState
}

export async function playMove(move: MoveRequest): Promise<GameState> {
  const response = await fetch(`${API_URL}/game/play`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(move),
  })

  if (!response.ok) {
    throw new Error('Could not play the move')
  }

  return await response.json() as GameState
}
