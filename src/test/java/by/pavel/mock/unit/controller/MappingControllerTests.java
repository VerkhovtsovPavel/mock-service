package by.pavel.mock.unit.controller;

import by.pavel.mock.unit.entity.Mapping;
import by.pavel.mock.storage.Storage;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MappingControllerTests {

    @MockBean
    private Storage storage;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    private final Mapping testMapping = new Mapping(200, "{item:1}", "/item/1", "GET");

    @Test
    void getMappingsFromEmptyStorage() throws Exception {
        when(storage.readAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/mappings"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getMappingsFromStorageWithSingleItem() throws Exception {
        when(storage.readAll()).thenReturn(List.of(testMapping));
        mockMvc.perform(get("/mappings"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("["+gson.toJson(testMapping)+"]"));
    }

    @Test
    void getMappingsFromStorageWithSeveralItem() throws Exception {
        List<Mapping> mappingsList = List.of(testMapping, testMapping, testMapping);
        when(storage.readAll()).thenReturn(mappingsList);
        mockMvc.perform(get("/mappings"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(gson.toJson(mappingsList)));
    }

    @Test
    void postNewMappings() throws Exception {
        when(storage.create(testMapping)).thenReturn(Optional.of("42"));
        mockMvc.perform(post("/mappings")
                .content(gson.toJson(testMapping))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("42"));
    }

    @Test
    void putMappings() throws Exception {
        when(storage.update("42", testMapping)).thenReturn(testMapping);
        mockMvc.perform(put("/mappings/42")
                .content(gson.toJson(testMapping))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(gson.toJson(testMapping)));
    }

    @Test
    void deleteMappings() throws Exception {
        doNothing().when(storage).delete("42");
        mockMvc.perform(delete("/mappings/42"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        verify(storage, times(1)).delete("42");
    }

    @Test
    void getMappingByInvalidId() throws Exception {
        mockMvc.perform(get("/mappings/invalid"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"url\":null,\"method\":null,\"responseCode\":404,\"body\":\"Mapping not found\"}"));
    }
}
