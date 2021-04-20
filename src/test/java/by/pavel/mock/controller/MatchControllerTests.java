package by.pavel.mock.controller;

import by.pavel.mock.entity.Mapping;
import by.pavel.mock.storage.Storage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MatchControllerTests {

    @MockBean
    private Storage storage;

    @Autowired
    private MockMvc mockMvc;

    private final Mapping testMapping = new Mapping(200, "{item:1}", "/item/1", "GET");

    @Test
    void getMatchingFromEmptyStorage() throws Exception {
        when(storage.readByPath("get", "item/1")).thenReturn(Optional.empty());
        mockMvc.perform(get("/match/item/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Not found"));
    }

    @Test
    void getMatchingFromStorage() throws Exception {
        when(storage.readByPath("get", "/item/1")).thenReturn(Optional.of(testMapping));
        mockMvc.perform(get("/match/item/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(testMapping.getBody()));
    }

    @Test
    void postMatchingFromEmptyStorage() throws Exception {
        when(storage.readByPath("post", "item/1")).thenReturn(Optional.empty());
        mockMvc.perform(post("/match/item/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Not found"));
    }

    @Test
    void postMatchingFromStorage() throws Exception {
        when(storage.readByPath("post", "/item/1")).thenReturn(Optional.of(testMapping));
        mockMvc.perform(post("/match/item/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(testMapping.getBody()));
    }
}