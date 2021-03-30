package se.lexicon.jpa_workshop.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpa_workshop.model.Details;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Optional;

@Repository
public class DetailsDAORepository implements DetailsDAO {

    private final EntityManager entityManager;

    @Autowired
    public DetailsDAORepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public Details findById(int id) {
        return entityManager.find(Details.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Details> findAll() {
        return entityManager
                .createQuery("SELECT d FROM Details d", Details.class)
                .getResultList();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Details create(Details details) {
        if(details.getDetailId() != 0) throw new IllegalArgumentException("Details details is already in the database");
        entityManager.persist(details);
        return details;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Details update(Details details) {
        if(details.getDetailId() == 0) throw new IllegalArgumentException("Details details is not yet persisted");
        return entityManager.merge(details);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(int id) {
        Optional<Details> optional = Optional.ofNullable(findById(id));
        optional.ifPresent(entityManager::remove);
    }
}
