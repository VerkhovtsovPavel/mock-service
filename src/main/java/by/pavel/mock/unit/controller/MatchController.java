package by.pavel.mock.unit.controller;

import by.pavel.mock.unit.entity.Mapping;
import by.pavel.mock.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MatchController {

    @Autowired
    private Storage storage;

    private static final String MATCH_PATH = "match";
    private static final int MATCH_PATH_LENGTH = MATCH_PATH.length();
    private static final Mapping NOT_FOUND_RESPONSE = new Mapping(404, "Not found");

    @GetMapping(MATCH_PATH + "/**")
    public ResponseEntity<String> matchGet(HttpServletRequest request) {
        String path = fetchPath(request);
        var mapping = storage.readByPath("get", path).orElse(NOT_FOUND_RESPONSE);
        return new ResponseEntity<>(mapping.getBody(), HttpStatus.valueOf(mapping.getResponseCode()));
    }

    @PostMapping(MATCH_PATH + "/**")
    public ResponseEntity<String> matchPost(HttpServletRequest request) {
        String path = fetchPath(request);
        var mapping = storage.readByPath("post", path).orElse(NOT_FOUND_RESPONSE);
        return new ResponseEntity<>(mapping.getBody(), HttpStatus.valueOf(mapping.getResponseCode()));
    }

    private String fetchPath(HttpServletRequest request) {
        String path = request.getServletPath();
        if(path.isEmpty()) {
            path = request.getPathInfo();
        }
        var matchingPath = path.substring(MATCH_PATH_LENGTH);
        return matchingPath.substring(matchingPath.indexOf('/'));
    }
}
