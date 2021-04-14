package by.pavel.mock.storage;

import by.pavel.mock.entity.Mapping;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class LocalStorage implements Storage{

    private final AtomicLong id = new AtomicLong();
    private final Map<String, Mapping> storage = new HashMap<>();

    @Override
    public Optional<String> create(Mapping data) {
        String nextId = String.valueOf(id.incrementAndGet());
        storage.put(nextId, data);
        return Optional.of(nextId);
    }

    @Override
    public Optional<Mapping> read(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<Mapping> readByPath(String method, String path) {
        return storage.values().stream()
                .filter(x -> x.getMethod().equalsIgnoreCase(method))
                .filter(x -> x.getUrl().equalsIgnoreCase(path))
                .findFirst();
    }

    @Override
    public Mapping update(String id, Mapping data) {
        storage.put(id, data);
        return data;
    }

    @Override
    public void delete(String id) {
        storage.remove(id);
    }

    @Override
    public List<Mapping> readAll() {
        return new ArrayList<>(storage.values());
    }
}
