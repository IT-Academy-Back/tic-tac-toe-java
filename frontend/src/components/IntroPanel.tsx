type IntroPanelProps = {
  status: string
  message: string
  onReset: () => void
}

export function IntroPanel({ status, message, onReset }: IntroPanelProps) {
  return (
    <section className="intro-panel" aria-labelledby="game-title">
      <p className="eyebrow">Sprint 0 · Backend logic</p>
      <h1 id="game-title">Tic-Tac-Toe</h1>
      <p className="intro-copy">
        Play against the machine and follow each backend decision from the
        console.
      </p>
      <div className="status-card">
        <span>{status.replace('_', ' ')}</span>
        <strong>X</strong>
      </div>
      <p className="game-message">{message}</p>
      <button className="reset-button" type="button" onClick={onReset}>
        New sketch
      </button>
    </section>
  )
}
