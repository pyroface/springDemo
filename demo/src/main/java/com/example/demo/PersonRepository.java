package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
public interface PersonRepository extends CrudRepository<Person,Long> {
}
*/

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
  List<Person> findAllByName(String name);

  List<Person> findByName(String name);
}
