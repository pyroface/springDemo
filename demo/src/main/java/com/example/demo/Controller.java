package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.List;


@RestController
public class Controller {

  private PersonRepository personRepository;

  @Autowired
  public Controller(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  @GetMapping("/persons")
  public List<Person> all(){
    return personRepository.findAll();
  }

  @GetMapping("/persons/{id}")
  public Person one(@PathVariable Long id){
    return personRepository.findById(id).orElseThrow(
      () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id " + id + " not found"));
  }

  @PostMapping("/persons")
  @ResponseStatus(HttpStatus.CREATED)
  public Person create(@RequestBody Person person){
    return personRepository.save(person);
  }

  @DeleteMapping("/persons/{id}")
  public void delete(@PathVariable Long id) {
    personRepository.deleteById(id);
  }

  //PUT
  @PutMapping("/persons/{id}")
  public ResponseEntity<Person> update(@RequestBody Person person, @PathVariable long id) {
    Optional<Person> personOptional = personRepository.findById(id);
    if (personOptional.isEmpty())
      return ResponseEntity.notFound().build();

    person.setId(id);
    personRepository.save(person);
    return ResponseEntity.noContent().build();
  }

  //PATCH
  @PatchMapping("/persons/{id}")
  public ResponseEntity<Person> partUpdate(@RequestBody Person person, @PathVariable long id) {
    Optional<Person> personOptional = personRepository.findById(id);
    if (personOptional.isEmpty())
      return ResponseEntity.notFound().build();

    var oldPerson = personOptional.get();

    if(person.getName() != null && !person.getName().isEmpty() ){
      oldPerson.setName(person.getName());
    }
    if(person.getEmail() != null && !person.getEmail().isEmpty() ){
      oldPerson.setEmail(person.getEmail());
    }

    personRepository.save(oldPerson);
    return ResponseEntity.noContent().build();
  }

  //GET SEARCH
  @GetMapping("/persons/find")
  @ResponseBody
  public List<Person> findAll(@RequestParam(required = false) String name) {
    return personRepository.findAllByName(name);
  }

}
