package by.pavel.mock.storage;

import by.pavel.mock.unit.entity.Mapping;

import java.util.List;
import java.util.Optional;

public interface Storage {

    Optional<String> create(Mapping data);

    Optional<Mapping> read(String id);

    Optional<Mapping> readByPath(String method, String path);

    Mapping update(String id, Mapping data);

    void delete(String id);

    List<Mapping> readAll();

}
