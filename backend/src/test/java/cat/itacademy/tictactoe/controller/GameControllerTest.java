package cat.itacademy.tictactoe.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
}
