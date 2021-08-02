package com.dockersample.demo.repository;

import com.dockersample.demo.domain.TestItem;
import org.springframework.data.repository.CrudRepository;

public interface TestItemRepository extends CrudRepository<TestItem, String>
{
}
