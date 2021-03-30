package se.lexicon.jpa_workshop.data;

import se.lexicon.jpa_workshop.model.Details;

import java.util.Collection;

public interface DetailsDAO {
    Details findById(int id);
    Collection<Details> findAll();
    Details create(Details details);
    Details update(Details details);
    void delete(int id);
}
