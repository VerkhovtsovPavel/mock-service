package by.pavel.mock.unit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class UnmatchedController {

    @RequestMapping(value = "/**", method = {GET, HEAD, OPTIONS})
    public ResponseEntity<String> defaultPathForSafeMethods() {
        return new ResponseEntity<>("Unmapped request", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/**", method = {POST, PUT, DELETE})
    public ResponseEntity<String> defaultPathForUnsafeMethods() {
        return new ResponseEntity<>("Unmapped request", HttpStatus.NOT_FOUND);
    }
}