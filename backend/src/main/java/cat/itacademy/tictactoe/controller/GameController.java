package cat.itacademy.tictactoe.controller;

import cat.itacademy.tictactoe.model.GameResponse;
import cat.itacademy.tictactoe.model.MoveRequest;
import cat.itacademy.tictactoe.service.GameService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

	private final GameService gameService;

	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	@PostMapping("/start")
	public GameResponse startGame() {
		System.out.println("Controller: received POST /game/start");
		return gameService.startGame();
	}

	@PostMapping("/play")
	public GameResponse playMove(@RequestBody MoveRequest moveRequest) {
		System.out.println("Controller: received POST /game/play with row=" + moveRequest.getRow() + ", col=" + moveRequest.getCol());
		return gameService.playMove(moveRequest);
	}
}
