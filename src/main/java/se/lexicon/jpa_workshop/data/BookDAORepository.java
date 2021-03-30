package se.lexicon.jpa_workshop.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpa_workshop.model.Book;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Optional;

@Repository
public class BookDAORepository implements BookDAO{

    private final EntityManager entityManager;

    @Autowired
    public BookDAORepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public Book findById(int id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Book> findAll() {
        return entityManager
                .createQuery("SELECT b FROM Book b", Book.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Book> findByAuthorId(int authorId) {
        return entityManager
                .createQuery("SELECT book FROM Book book JOIN FETCH book.authors AS author WHERE author.authorId = :id", Book.class)
                .setParameter("id", authorId)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Book> findByOnLoanStatus(boolean onLoanStatus) {
        return entityManager.createQuery("SELECT book FROM Book book WHERE book.onLoan = :status", Book.class)
                .setParameter("status", onLoanStatus)
                .getResultList();

    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Book> findByAuthorName(String name) {
        return entityManager.createQuery("SELECT book FROM Book book JOIN FETCH book.authors AS author WHERE UPPER(author.firstName) LIKE UPPER(CONCAT('%', :name, '%')) OR UPPER(author.lastName) LIKE UPPER(CONCAT('%', :name, '%')) ", Book.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Book create(Book book) {
        if(book.getBookId() != 0) throw new IllegalArgumentException("Book book is already in already in the database");
        entityManager.persist(book);
        return book;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Book update(Book book) {
        if(book.getBookId() == 0) throw new IllegalArgumentException("Book book is not yet persisted");
        return entityManager.merge(book);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(int id) {
        Optional.ofNullable(findById(id))
                .ifPresent(entityManager::remove);
    }
}
