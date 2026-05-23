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

## Executar el backend

```bash
cd backend
./mvnw spring-boot:run
```

Per executar els tests del backend:

```bash
cd backend
./mvnw test
```

## Executar el frontend

```bash
cd frontend
pnpm install
pnpm dev
```

Per generar una build del frontend:

```bash
cd frontend
pnpm build
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

## Notes per a l'alumnat

El projecte base ja defineix el contracte de la request. La teva feina principal sera implementar la logica del joc al backend:

- validar la jugada del jugador,
- actualitzar el tauler,
- fer la tirada random de la maquina,
- detectar victoria,
- detectar empat,
- retornar un estat coherent.
