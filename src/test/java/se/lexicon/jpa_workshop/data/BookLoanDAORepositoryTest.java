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
import se.lexicon.jpa_workshop.model.AppUser;
import se.lexicon.jpa_workshop.model.Book;
import se.lexicon.jpa_workshop.model.BookLoan;
import se.lexicon.jpa_workshop.model.Details;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
class BookLoanDAORepositoryTest {

    @Autowired private BookLoanDAORepository testObject;
    @Autowired private TestEntityManager em;

    private BookLoan persistedLoan;

    @BeforeEach
    void setUp() {
        Book persistedBook = em.persist(new Book(0, "34235255", "Test", 15));

        Details details = new Details();
        details.setEmail("test@test.com");
        details.setName("Test Testsson");
        details.setBirthDate(LocalDate.parse("2000-01-01"));

        AppUser appUser = new AppUser();
        appUser.setUsername("testy");
        appUser.setPassword("1234");
        appUser.setRegDate(LocalDate.parse("2021-03-05"));
        appUser.setUserDetails(details);
        AppUser persistedAppUser = em.persist(appUser);

        persistedLoan = em.persist(
                new BookLoan(0, LocalDate.parse("2020-03-05"), LocalDate.parse("2020-04-05"), false, persistedAppUser, persistedBook)
        );
        em.flush();
    }

    @AfterEach
    void tearDown() {
        em.flush();
    }

    @Test
    @DisplayName("Given persistedLoan.loanId findById return entity")
    void findById() {
        BookLoan result = testObject.findById(persistedLoan.getLoanId());

        assertNotNull(result);
        assertEquals(persistedLoan, result);
    }

    @Test
    @DisplayName("findAll return collection of 1 BookLoan")
    void findAll() {
        int expectedSize = 1;

        Collection<BookLoan> result = testObject.findAll();

        assertNotNull(result);
        assertEquals(expectedSize, result.size());
    }

    @Test
    @DisplayName("Given new bookLoan create return persisted entity")
    void create() {
        BookLoan bookLoan = new BookLoan();
        bookLoan.setLoanDate(LocalDate.parse("2020-03-05"));
        bookLoan.setDueDate(LocalDate.parse("2020-04-05"));
        bookLoan.setReturned(false);
        bookLoan.setBook(em.persist(new Book(0, "552322", "JPA for beginners", 31)));

        AppUser appUser = new AppUser(0, "testy2", "1234", LocalDate.parse("2020-03-05"), null);
        appUser.setUserDetails(new Details(0, "test2@test.com", "Test Tester", LocalDate.parse("2000-01-01")));

        bookLoan.setBorrower(em.persist(appUser));

        BookLoan result = testObject.create(bookLoan);

        assertNotNull(result);
        assertTrue(result.getLoanId() != 0);
    }

    @Test
    @DisplayName("Given updated persistedLoan update should update and return entity")
    void update() {
        BookLoan toUpdate = persistedLoan;
        toUpdate.setReturned(true);

        BookLoan result = testObject.update(toUpdate);

        assertNotNull(result);
        assertTrue(result.isReturned());
    }

    @Test
    @DisplayName("Given persistedLoan.loanId delete should remove entity")
    void delete() {
        testObject.delete(persistedLoan.getLoanId());

        assertNull(em.find(BookLoan.class, persistedLoan.getLoanId()));
    }
}