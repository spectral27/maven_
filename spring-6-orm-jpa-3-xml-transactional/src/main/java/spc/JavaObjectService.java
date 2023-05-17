package spc;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class JavaObjectService {

    private final JavaObjectRepository repository;

    public JavaObjectService(JavaObjectRepository repository) {
        this.repository = repository;
    }

    public void insertObject(JavaObject javaObject) {
        repository.insertJavaObject(javaObject);
    }

    public List<JavaObject> selectObjects() {
        return repository.selectJavaObjects();
    }

}
