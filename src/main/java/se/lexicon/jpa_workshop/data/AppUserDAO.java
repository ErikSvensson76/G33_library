package se.lexicon.jpa_workshop.data;

import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpa_workshop.model.AppUser;

import java.util.Collection;

public interface AppUserDAO {
    AppUser findById(int id);
    Collection<AppUser> findAll();

    @Transactional(readOnly = true)
    AppUser findByUsername(String username);

    @Transactional(readOnly = true)
    AppUser findByEmail(String email);

    AppUser create(AppUser appUser);
    AppUser update(AppUser appUser);
    void delete(int id);
}
