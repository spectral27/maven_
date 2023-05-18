package spec;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class JavaObjectService {

    private final JavaObjectRepository repository;

    public JavaObjectService(JavaObjectRepository repository) {
        this.repository = repository;
    }

    public void insert(JavaObject javaObject) {
        repository.insert(javaObject);
    }

    public List<JavaObject> selectAll() {
        return repository.selectAll();
    }

    public JavaObject select(int id) {
        return repository.select(id);
    }

    public void update(JavaObject javaObject) {
        repository.update(javaObject);
    }

    public void delete(int id) {
        repository.delete(id);
    }

}
