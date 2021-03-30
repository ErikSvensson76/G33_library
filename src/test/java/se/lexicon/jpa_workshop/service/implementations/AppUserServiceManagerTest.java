package se.lexicon.jpa_workshop.service.implementations;

import org.junit.jupiter.api.BeforeEach;
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
import se.lexicon.jpa_workshop.model.dto.AppUserDTO;
import se.lexicon.jpa_workshop.model.dto.DetailsDTO;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
class AppUserServiceManagerTest {

    @Autowired
    private AppUserServiceManager testObject;
    @Autowired
    private TestEntityManager em;

    AppUser persistedUser;

    @BeforeEach
    void setUp() {
        AppUser appUser = new AppUser(
                0,
                "nisse",
                "nisse2",
                LocalDate.parse("1998-01-01"),
                new Details(
                        0,
                        "nisse@gmail.com",
                        "Nisse Nys",
                        LocalDate.parse("1983-07-11")
                )
        );

        persistedUser = em.persistAndFlush(appUser);


    }

    @Test
    void findById() {
        int id = persistedUser.getAppUserId();
        AppUserDTO result = testObject.findById(id);

        assertNotNull(result);
        assertEquals(persistedUser.getAppUserId(), result.getAppUserId());
        assertEquals(persistedUser.getRegDate(), result.getRegDate());

        assertNotNull(result.getUserDetails());
        DetailsDTO detailsDTO = result.getUserDetails();
        assertEquals(persistedUser.getUserDetails().getDetailId(), detailsDTO.getDetailId());
        assertEquals(persistedUser.getUserDetails().getName(), detailsDTO.getName());
        assertEquals(persistedUser.getUserDetails().getBirthDate(), detailsDTO.getBirthDate());
    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void addLoan() {
    }

    @Test
    void terminateLoan() {
    }
}