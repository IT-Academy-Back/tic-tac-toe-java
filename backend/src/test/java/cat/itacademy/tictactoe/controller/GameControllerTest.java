package cat.itacademy.tictactoe.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasItem;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void startGameReturnsEmptyBoard() throws Exception {
		mockMvc.perform(post("/game/start"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value("IN_PROGRESS"))
			.andExpect(jsonPath("$.message").value("Game started"))
			.andExpect(jsonPath("$.board.length()").value(3))
			.andExpect(jsonPath("$.board[0].length()").value(3))
			.andExpect(jsonPath("$.board[0][0]").value(0))
			.andExpect(jsonPath("$.board[0][1]").value(0))
			.andExpect(jsonPath("$.board[0][2]").value(0))
			.andExpect(jsonPath("$.board[1][0]").value(0))
			.andExpect(jsonPath("$.board[1][1]").value(0))
			.andExpect(jsonPath("$.board[1][2]").value(0))
			.andExpect(jsonPath("$.board[2][0]").value(0))
			.andExpect(jsonPath("$.board[2][1]").value(0))
			.andExpect(jsonPath("$.board[2][2]").value(0));
	}

	@Test
	void playMoveMarksSelectedCell() throws Exception {
		mockMvc.perform(post("/game/start"))
			.andExpect(status().isOk());

		mockMvc.perform(post("/game/play")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"row\":1,\"col\":1}"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value("IN_PROGRESS"))
			.andExpect(jsonPath("$.message").value("Turn processed"))
			.andExpect(jsonPath("$.board[1][1]").value(1));
	}

	@Test
	void playMoveLetsMachineMoveAfterPlayer() throws Exception {
		mockMvc.perform(post("/game/start"))
			.andExpect(status().isOk());

		mockMvc.perform(post("/game/play")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"row\":1,\"col\":1}"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.board[1][1]").value(1))
			.andExpect(jsonPath("$.board[*][*]").value(hasItem(2)));
	}

	@Test
	void playMoveReturnsInvalidMoveWhenCellIsOccupied() throws Exception {
		mockMvc.perform(post("/game/start"))
			.andExpect(status().isOk());

		mockMvc.perform(post("/game/play")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"row\":1,\"col\":1}"))
			.andExpect(status().isOk());

		mockMvc.perform(post("/game/play")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"row\":1,\"col\":1}"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value("INVALID_MOVE"))
			.andExpect(jsonPath("$.message").value("Invalid move"))
			.andExpect(jsonPath("$.board[1][1]").value(1));
	}

	@Test
	void playMoveReturnsInvalidMoveWhenCoordinatesAreOutsideBoard() throws Exception {
		mockMvc.perform(post("/game/start"))
			.andExpect(status().isOk());

		mockMvc.perform(post("/game/play")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"row\":3,\"col\":0}"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value("INVALID_MOVE"))
			.andExpect(jsonPath("$.message").value("Invalid move"))
			.andExpect(jsonPath("$.board[0][0]").value(0))
			.andExpect(jsonPath("$.board[1][1]").value(0))
			.andExpect(jsonPath("$.board[2][2]").value(0));
	}

}
