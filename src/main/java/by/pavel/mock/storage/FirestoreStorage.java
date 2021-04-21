package by.pavel.mock.storage;

import by.pavel.mock.unit.entity.Mapping;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
@Primary
public class FirestoreStorage implements Storage {

    private final Logger logger = LoggerFactory.getLogger(FirestoreStorage.class);

    private static final String COLLECTION = "collection";

    @Autowired
    private Firestore firestore;

    public Optional<String> create(Mapping data) {
        ApiFuture<DocumentReference> documentReference = firestore.collection(COLLECTION).add(data.normalize());
        return Optional.ofNullable(resolveFuture(documentReference)).map(DocumentReference::getId);
    }

    public Optional<Mapping> read(String id) {
        ApiFuture<DocumentSnapshot> documentFuture = firestore.collection(COLLECTION).document(id).get();
        return Optional.ofNullable(resolveFuture(documentFuture)).map(x -> x.toObject(Mapping.class));
    }

    public Optional<Mapping> readByPath(String method, String path) {
       Optional<QuerySnapshot> result = Optional.ofNullable(resolveFuture(firestore.collection(COLLECTION).whereEqualTo("url", path.toLowerCase()).whereEqualTo("method", method.toLowerCase()).get()));
       return result.flatMap(x -> x.getDocuments().stream().findFirst()).map(x -> x.toObject(Mapping.class));
    }

    public Mapping update(String id, Mapping data) {
        resolveFuture(firestore.collection(COLLECTION).document(id).set(data.normalize()));
        return data;
    }

    public void delete(String id)  {
        resolveFuture(firestore.collection(COLLECTION).document(id).delete());
    }

    public List<Mapping> readAll() {
        ArrayList<Mapping> list = new ArrayList<>();
        CollectionReference mappings = firestore.collection(COLLECTION);
        for (DocumentReference ref : mappings.listDocuments()) {
            DocumentSnapshot ds = resolveFuture(ref.get());
            if (ds != null) {
                list.add(ds.toObject(Mapping.class));
            }
        }
        return list;
    }

    private <T> T resolveFuture(ApiFuture<T> future) {
        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            logger.error("Execution exception: ", e.getCause());
        }
        return null;
    }
}