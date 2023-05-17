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
        repository.insertJavaObject(javaObject);
    }

    public List<JavaObject> select() {
        return repository.selectJavaObjects();
    }

}
