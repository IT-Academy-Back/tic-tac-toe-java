package cat.itacademy.tictactoe.controller;

import cat.itacademy.tictactoe.model.GameResponse;
import cat.itacademy.tictactoe.service.GameService;
import org.springframework.web.bind.annotation.PostMapping;
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
		System.out.println("Uoooo! Received request to start a new game!!");

		return gameService.startGame();
	}
}
