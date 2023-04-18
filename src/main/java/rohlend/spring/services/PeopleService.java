package rohlend.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rohlend.spring.entities.Book;
import rohlend.spring.entities.Person;
import rohlend.spring.repositories.PeopleRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }
    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }

    public Person findById(int id){
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void edit(int id,Person person){
        person.setPersonId(id);
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.delete(findById(id));
    }


    public List<Person> findByFullNameAndPersonIdNot(String fullName,int id){
        return peopleRepository.findByFullNameAndPersonIdNot(fullName,id);
    }

    public List<Book> findBooks(int id){
        Person person = findById(id);
        List<Book> books = person.getBooks();
        books.forEach(Book::checkTime);
        return books;
    }

}
