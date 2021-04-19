package by.pavel.mock.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UnmatchedControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void unmatchedRequestShouldReturnSpecifiedMessage() throws Exception {
		mockMvc.perform(get("/not_valid"))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().string("Unmapped request"));
	}

}
