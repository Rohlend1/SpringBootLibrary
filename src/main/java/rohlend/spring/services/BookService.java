package rohlend.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rohlend.spring.entities.Book;
import rohlend.spring.repositories.BookRepository;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;


    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book findById(int id){
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book){
        book.setOwner(null);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id){
        bookRepository.delete(findById(id));
    }

    @Transactional
    public void edit(int id,Book book){
        book.setId(id);
        if(book.getRate() != 0) {
            Book existingBook = findById(id);
//            System.out.println(book.getRate());
            existingBook.setNumberOfRates(existingBook.getNumberOfRates()+1);
            System.out.println((book.getRate()+existingBook.getRate())/existingBook.getNumberOfRates());
            existingBook.setRate((book.getRate()+existingBook.getRate())/existingBook.getNumberOfRates());
            existingBook.setName(book.getName());
            existingBook.setDateOfTaking(book.getDateOfTaking());
//            existingBook.setOwnerName(book.getOwnerName());
//            existingBook.setExpired(book.isExpired());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setYear(book.getYear());
            bookRepository.save(existingBook);
        }
        else bookRepository.save(book);
    }

    @Transactional
    public void assign(Book book,int id){
        Book changingBook = findById(id);
        changingBook.setDateOfTaking(new Date());
        changingBook.setOwner(book.getOwner());
        bookRepository.save(changingBook);
    }
    @Transactional
    public void free(Book book){
        book.setOwner(null);
        book.setDateOfTaking(null);
        bookRepository.save(book);
    }

    public List<Book> findAllSortByYear(){
        List<Book> books=bookRepository.findAll();
        books.sort(Comparator.comparingInt(Book::getYear));
        return books;
    }

    public List<Book> findAllSortByRate(){
        List<Book> books = bookRepository.findAll();
        books.sort(Comparator.comparingDouble(Book::getRate));
        return books;
    }

    public List<Book> findByNameStartingWith(String name){
        return bookRepository.findByNameStartingWith(name);
    }
}
