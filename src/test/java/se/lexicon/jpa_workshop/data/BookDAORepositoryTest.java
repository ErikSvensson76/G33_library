package se.lexicon.jpa_workshop.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpa_workshop.model.Book;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
class BookDAORepositoryTest {

    @Autowired private BookDAORepository testObject;
    @Autowired private TestEntityManager em;

    private Book persistedBook;

    @BeforeEach
    void setUp() {
        persistedBook = em.persistAndFlush(
                new Book(
                        0, "123456789", "JPA for beginners", 30
                )
        );
    }

    @AfterEach
    void tearDown() {
        em.flush();
    }

    @Test
    @DisplayName("Given persistedBook.bookId findById should return entity")
    void findById() {
        Book result = testObject.findById(persistedBook.getBookId());

        assertNotNull(result);
        assertEquals(persistedBook, result);
    }

    @Test
    @DisplayName("findAll return Collection of 1 book")
    void findAll() {
        int expectedSize = 1;
        Collection<Book> result = testObject.findAll();

        assertNotNull(result);
        assertEquals(expectedSize, result.size());
    }

    @Test
    @DisplayName("Given new book create should persist and return entity")
    void create() {
        Book book = new Book(0, "53534563", "Hibernate for beginners", 30);

        Book result = testObject.create(book);

        assertNotNull(result);
        assertTrue(result.getBookId() != 0);
    }

    @Test
    @DisplayName("Given updated persistedBook update should update fields and return entity")
    void update() {
        Book toUpdate = persistedBook;
        toUpdate.setMaxLoanDays(60);

        Book result = testObject.update(toUpdate);

        assertNotNull(result);
        assertEquals(60, result.getMaxLoanDays());
    }

    @Test
    @DisplayName("Given persistedBook.bookId delete should remove entity")
    void delete() {
        testObject.delete(persistedBook.getBookId());

        assertNull(em.find(Book.class, persistedBook.getBookId()));
    }
}