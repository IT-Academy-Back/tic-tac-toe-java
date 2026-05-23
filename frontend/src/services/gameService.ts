import type { GameState, MoveRequest } from '../types/game'

export async function startGame(): Promise<GameState> {
  const response = await fetch('/game/start', { method: 'POST' })

  if (!response.ok) {
    throw new Error('Could not start the game')
  }

  return await response.json() as GameState
}

export async function playMove(move: MoveRequest): Promise<GameState> {
  const response = await fetch('/game/play', {
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
