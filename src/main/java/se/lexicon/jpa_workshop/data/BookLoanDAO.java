package se.lexicon.jpa_workshop.data;

import se.lexicon.jpa_workshop.model.BookLoan;

import java.util.Collection;

public interface BookLoanDAO {
    BookLoan findById(int id);
    Collection<BookLoan> findAll();
    BookLoan create(BookLoan bookLoan);
    BookLoan update(BookLoan bookLoan);
    void delete(int id);
}
