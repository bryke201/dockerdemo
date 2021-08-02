package com.dockersample.demo.controller;

import com.dockersample.demo.domain.TestItem;
import com.dockersample.demo.repository.TestItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private TestItemRepository testTableItemRepository;

    @GetMapping
    public ResponseEntity<String> home() {
        return new ResponseEntity<>("This is the home endpoint", HttpStatus.OK);
    }

    @GetMapping("test-item/{id}")
    public ResponseEntity<String> countStudents(@PathVariable("id") String id) {
        TestItem item = testTableItemRepository.findById(id).get();
        return new ResponseEntity<>("Value for item with [ID: " + id + "]: " + item.getValue(), HttpStatus.OK);
    }
}
