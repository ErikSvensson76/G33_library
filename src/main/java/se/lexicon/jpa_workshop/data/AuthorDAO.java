package se.lexicon.jpa_workshop.data;

import se.lexicon.jpa_workshop.model.Author;

import java.util.Collection;

public interface AuthorDAO {

    Author findById(int id);
    Collection<Author> findAll();
    Author create(Author author);
    Author update(Author author);
    void delete(int id);

}
