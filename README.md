# S0 Tic-Tac-Toe Backend

Projecte base per practicar logica backend amb una API de 3 en ratlla.

El repositori esta organitzat com un workspace amb dues parts separades:

- `backend/`: API REST amb Spring Boot.
- `frontend/`: client web amb Vite, React i TypeScript.

## Requisits

- Java 21 o superior.
- Maven Wrapper inclos al projecte backend.
- Node.js.
- pnpm.

## Executar el frontend

Pots executar el frontend amb pnpm des de la línia de comandes:

```bash
cd frontend
pnpm install
pnpm dev
```
El frontend ha d'estar disponible a:
```bash
http://localhost:5173
```

## Com executar el backend

1. Obre la carpeta `backend` amb IntelliJ IDEA.
2. Comprova que el projecte utilitza **Java 21**.
3. Executa la classe principal del projecte.
4. El backend ha d'estar disponible a:

```bash
http://localhost:8080
```

També pots executar el backend amb Maven Wrapper des de la línia de comandes:

```bash
cd backend
./mvnw spring-boot:run
```

Per executar els tests del backend:

```bash
cd backend
./mvnw test
```

## Endpoints

### Start game

```http
POST /game/start
```

Response esperada:

```json
{
  "board": [
    [0, 0, 0],
    [0, 0, 0],
    [0, 0, 0]
  ],
  "status": "IN_PROGRESS",
  "message": "Game started"
}
```

### Play move

```http
POST /game/play
```

Request:

```json
{
  "row": 1,
  "col": 1
}
```

Response esperada:

```json
{
  "board": [
    [0, 0, 0],
    [0, 1, 0],
    [0, 2, 0]
  ],
  "status": "IN_PROGRESS",
  "message": "Turn processed"
}
```

La jugada del jugador es marca amb `1`. Si la partida continua, el backend tambe fa una tirada random de la maquina amb `2`.

## Què has de fer?

L'aplicació ja està implementada. La teva tasca és entendre com funciona el codi i corregir un bug que impedeix que el jugador pugui fer una tirada.

1. Revisa el codi i descriu què fa cada part de l'aplicació i com es relaciona amb les altres.
2. Explica el flux d'una partida: l'inici del joc, la validació de la jugada del jugador, l'actualització del tauler, la tirada aleatòria de la màquina i la detecció de victòria o empat.
3. Reprodueix el bug que impedeix al jugador fer una tirada, localitza'n la causa i explica-la.
4. Corregeix el bug i comprova que el jugador pot fer una tirada i que la partida continua correctament.
