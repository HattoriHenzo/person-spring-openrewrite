package com.practice.application.spring.service;

import com.practice.application.spring.generic.GenericService;
import com.practice.application.spring.model.Person;
import com.practice.application.spring.repository.PersonRepository;
import com.practice.application.spring.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PersonService implements GenericService<Person> {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Person findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ServiceException(String.format("Person with id %s not found", id)));
    }

    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }

    @Override
    public Person createOrUpdate(Person person) {
        if (!isValidPerson(person)) {
            throw new ServiceException("Some values are missing");
        }
        return repository.save(person);
    }

    @Override
    public void delete(Long id) {
        this.findById(id);
        repository.deleteById(id);
    }

    private boolean isValidPerson(Person person) {
        return person != null && !person.getFirstName().isEmpty()
                && !person.getLastName().isEmpty()
                && !person.getBirthDay().isAfter(LocalDate.now())
                && !person.getEmail().isEmpty()
                && !person.getPhoneNumber().isEmpty();
    }
}
