package se.lexicon.jpa_workshop.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpa_workshop.model.Author;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public class AuthorDAORepository implements AuthorDAO{

    private final EntityManager entityManager;

    @Autowired
    public AuthorDAORepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public Author findById(int id) {
        return entityManager.find(Author.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Author> findAll() {
        return entityManager
                .createQuery("SELECT a FROM Author a", Author.class)
                .getResultList();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Author create(Author author) {
        if(author.getAuthorId() != 0) throw new IllegalArgumentException("Author author is already persisted in the database");
        entityManager.persist(author);
        return author;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Author update(Author author) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(int id) {

    }
}
