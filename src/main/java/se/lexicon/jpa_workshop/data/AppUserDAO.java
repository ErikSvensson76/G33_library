package se.lexicon.jpa_workshop.data;

import se.lexicon.jpa_workshop.model.AppUser;

import java.util.Collection;

public interface AppUserDAO {
    AppUser findById(int id);
    Collection<AppUser> findAll();
    AppUser create(AppUser appUser);
    AppUser update(AppUser appUser);
    void delete(int id);
}
