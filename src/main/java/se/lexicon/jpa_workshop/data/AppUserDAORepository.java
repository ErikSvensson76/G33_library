package se.lexicon.jpa_workshop.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpa_workshop.model.AppUser;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Optional;

@Repository
public class AppUserDAORepository implements AppUserDAO{

    private final EntityManager entityManager;

    @Autowired
    public AppUserDAORepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public AppUser findById(int id) {
        return entityManager.find(AppUser.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<AppUser> findAll() {
        return entityManager
                .createQuery("SELECT user FROM AppUser user", AppUser.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public AppUser findByUsername(String username){
        return entityManager.createQuery("SELECT user FROM AppUser user WHERE UPPER(user.username) = UPPER(:username)", AppUser.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public AppUser findByEmail(String email){
        return entityManager.createQuery("SELECT user FROM AppUser user WHERE UPPER(user.userDetails.email) = UPPER(:email)", AppUser.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AppUser create(AppUser appUser) {
        if(appUser.getAppUserId() != 0) throw new IllegalArgumentException("AppUser appUser is already persisted");
        entityManager.persist(appUser);
        return appUser;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AppUser update(AppUser appUser) {
        if(appUser.getAppUserId() == 0) throw new IllegalArgumentException("AppUser appUser is not persisted");
        return entityManager.merge(appUser);
    }

    @Override
    public void delete(int id) {
        Optional<AppUser> optional = Optional.ofNullable(findById(id));
        optional.ifPresent(entityManager::remove);
    }
}
