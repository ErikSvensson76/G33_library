package se.lexicon.jpa_workshop.data;

import se.lexicon.jpa_workshop.model.Book;

import java.util.Collection;

public interface BookDAO {
    Book findById(int id);
    Collection<Book> findAll();
    Collection<Book> findByAuthorId(int authorId);
    Collection<Book> findByOnLoanStatus(boolean onLoanStatus);
    Collection<Book> findByAuthorName(String name);
    Book create(Book book);
    Book update(Book book);
    void delete(int id);
    
}
