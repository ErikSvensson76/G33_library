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
import se.lexicon.jpa_workshop.model.Details;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
class AppUserDAORepositoryTest {

    @Autowired private AppUserDAORepository testObject;
    @Autowired private TestEntityManager em;

    private AppUser persistedAppUser;

    @BeforeEach
    void setUp() {
        AppUser appUser = new AppUser();
        appUser.setUsername("test");
        appUser.setPassword("1234");
        appUser.setRegDate(LocalDate.parse("2021-03-05"));
        Details details = new Details();
        details.setName("Nils Nilsson");
        details.setEmail("nisse@gmail.com");
        details.setBirthDate(LocalDate.parse("1979-03-11"));
        appUser.setUserDetails(details);

        persistedAppUser = em.persistAndFlush(appUser);
    }

    @AfterEach
    void tearDown() {
        em.flush();
    }

    @Test
    @DisplayName("Given persistedAppUser.appUserId findById should return entity")
    void findById() {
        AppUser result = testObject.findById(persistedAppUser.getAppUserId());

        assertNotNull(result);
        assertEquals(persistedAppUser, result);
    }

    @Test
    @DisplayName("findAll should return collection of 1")
    void findAll() {
        int expectedSize = 1;
        Collection<AppUser> result = testObject.findAll();

        assertNotNull(result);
        assertEquals(expectedSize, result.size());
    }

    @Test
    @DisplayName("Given new AppUser create should persist and return entity")
    void create() {
        Details details = new Details();
        details.setEmail("test@test.com");
        details.setName("Test Testsson");
        details.setBirthDate(LocalDate.parse("2000-01-01"));
        AppUser appUser = new AppUser();
        appUser.setUserDetails(details);
        appUser.setUsername("testy");
        appUser.setPassword("1234");
        appUser.setRegDate(LocalDate.parse("2020-03-05"));

        AppUser result = testObject.create(appUser);

        assertNotNull(result);
        assertTrue(result.getAppUserId() != 0);
    }

    @Test
    @DisplayName("Given updated persistedAppUser update should return entity with updated fields")
    void update() {
        AppUser toUpdate = persistedAppUser;
        toUpdate.setPassword("pa$$word");
        toUpdate.getUserDetails().setEmail("nils@hotmail.com");

        AppUser result = testObject.update(toUpdate);

        assertNotNull(result);
        assertEquals(toUpdate.getPassword(),result.getPassword());
        assertEquals(toUpdate.getUserDetails().getEmail(), result.getUserDetails().getEmail());
    }

    @Test
    @DisplayName("Given persistedAppUser.appUserId delete should remove entity")
    void delete() {
        testObject.delete(persistedAppUser.getAppUserId());

        assertNull(em.find(AppUser.class, persistedAppUser.getAppUserId()));
    }
}