package se.lexicon.jpa_workshop.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpa_workshop.model.BookLoan;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Repository
public class BookLoanDAORepository implements BookLoanDAO{

    private final EntityManager entityManager;

    @Autowired
    public BookLoanDAORepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public BookLoan findById(int id) {
        return entityManager.find(BookLoan.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<BookLoan> findAll() {
        return entityManager
                .createQuery("SELECT loan FROM BookLoan loan", BookLoan.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Collection<BookLoan> findAllCurrentLoans(int appUserId){
        LocalDate date = LocalDate.now();
        return entityManager.createQuery("SELECT loan FROM BookLoan loan WHERE (:date BETWEEN loan.loanDate AND loan.dueDate) AND (loan.borrower.appUserId = :id)", BookLoan.class)
                .setParameter("date", date)
                .setParameter("id", appUserId)
                .getResultList();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BookLoan create(BookLoan bookLoan) {
        if(bookLoan.getLoanId() != 0) throw new IllegalArgumentException("BookLoan bookLoan is already persisted to the database");
        entityManager.persist(bookLoan);
        return bookLoan;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BookLoan update(BookLoan bookLoan) {
        if(bookLoan.getLoanId() == 0) throw new IllegalArgumentException("BookLoan bookLoan is not yet persisted");
        return entityManager.merge(bookLoan);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(int id) {
        Optional.ofNullable(findById(id)).ifPresent(entityManager::remove);
    }
}
