package by.pavel.mock.controller;

import by.pavel.mock.entity.Mapping;
import by.pavel.mock.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class MappingController {

    @Autowired
    private Storage storage;

    @GetMapping("/mappings")
    public List<Mapping> getMappings(HttpServletRequest request) {
        return storage.readAll();
    }

    @GetMapping("/mappings/{id}")
    public Mapping getMapping(@PathVariable String id) {
        return storage.read(id).orElse(new Mapping(404, "Mapping not found"));
    }

    @PostMapping("/mappings")
    public String addMapping(@RequestBody Mapping newMapping) {
        return storage.create(newMapping).orElse("Mapping creation error");
    }

    @PutMapping("/mappings/{id}")
    public Mapping updateMapping(@PathVariable String id, @RequestBody Mapping mapping) {
        return storage.update(id, mapping);
    }

    @DeleteMapping("/mappings/{id}")
    public void deleteMapping(@PathVariable String id) {
        storage.delete(id);
    }
}
