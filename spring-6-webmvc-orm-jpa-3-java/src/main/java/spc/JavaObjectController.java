package spc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/objects")
public class JavaObjectController {

    @Autowired
    private JavaObjectRepository repository;

    @GetMapping
    public ResponseEntity<?> selectObjects() {
        return new ResponseEntity<>(repository.selectJavaObjects(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> insertObject(@RequestBody JavaObject javaObject) {
        repository.insertJavaObject(javaObject);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
