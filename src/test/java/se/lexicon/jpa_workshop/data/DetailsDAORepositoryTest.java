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
import se.lexicon.jpa_workshop.model.Details;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
class DetailsDAORepositoryTest {

    @Autowired private DetailsDAO testObject;
    @Autowired private TestEntityManager em;

    private Details persistedObject;

    @BeforeEach
    void setUp() {
        Details details = new Details();
        details.setName("Ola Olsson");
        details.setEmail("ola@gmail.com");
        details.setBirthDate(LocalDate.parse("1991-04-16"));
        persistedObject = em.persistAndFlush(details);
    }

    @AfterEach
    void tearDown() {
        em.flush();
    }

    @Test
    @DisplayName("Given persistedObject.detailsId findById return entity")
    void findById() {
        Details result = testObject.findById(persistedObject.getDetailId());

        assertNotNull(result);
        assertEquals(persistedObject, result);
    }

    @Test
    @DisplayName("findAll return collection of 1 object")
    void findAll() {
        int expectedSize = 1;

        Collection<Details> result = testObject.findAll();

        assertNotNull(result);
        assertEquals(expectedSize, result.size());
    }

    @Test
    @DisplayName("Given new details create should persist and return entity")
    void create() {
        Details details = new Details();
        details.setName("Test Testsson");
        details.setEmail("test.testsson@hotmail.com");
        details.setBirthDate(LocalDate.parse("2000-01-01"));

        Details result = testObject.create(details);

        assertNotNull(result);
        assertTrue(result.getDetailId() != 0);
    }

    @Test
    @DisplayName("Given updated persistedDetails update should result with updated fields")
    void update() {
        Details toUpdate = this.persistedObject;
        toUpdate.setName("Ola Svensson");

        Details result = testObject.update(toUpdate);

        assertNotNull(result);
        assertEquals(persistedObject.getName(), result.getName());
    }

    @Test
    @DisplayName("Given persistedObject.detailsId delete should delete object")
    void delete() {
        testObject.delete(persistedObject.getDetailId());

        assertNull(em.find(Details.class, persistedObject.getDetailId()));
    }
}