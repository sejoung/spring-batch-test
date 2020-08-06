package com.github.sejoung.component.repositories;


import com.github.sejoung.component.entity.Writer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriterRepository extends CrudRepository<Writer, Long> {

}
