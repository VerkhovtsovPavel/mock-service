package by.pavel.mock.unit.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class MappingTests {

    @Test
    void testDefaultConstructor() {
        Mapping mapping = new Mapping();
        assertEquals(0, mapping.getResponseCode());
        assertNull(mapping.getBody());
        assertNull(mapping.getUrl());
        assertNull(mapping.getMethod());
    }

    @Test
    void testConstructorWithTwoParams() {
        Mapping mapping = new Mapping(200, "OK");
        assertEquals(200, mapping.getResponseCode());
        assertEquals("OK", mapping.getBody());
        assertNull(mapping.getUrl());
        assertNull(mapping.getMethod());
    }

    @Test
    void testConstructorWithFourParams() {
        Mapping mapping = new Mapping(200, "OK", "/some/resource", "GET");
        assertEquals(200, mapping.getResponseCode());
        assertEquals("OK", mapping.getBody());
        assertEquals("/some/resource", mapping.getUrl());
        assertEquals("GET", mapping.getMethod());
    }

    @Test
    void testNormalize() {
        Mapping mapping = new Mapping(200, "OK", "/Some/Resource", "GET").normalize();
        assertEquals(200, mapping.getResponseCode());
        assertEquals("OK", mapping.getBody());
        assertEquals("/some/resource", mapping.getUrl());
        assertEquals("get", mapping.getMethod());
    }

}
