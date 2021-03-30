package se.lexicon.jpa_workshop.service.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpa_workshop.data.AppUserDAO;
import se.lexicon.jpa_workshop.data.BookDAO;
import se.lexicon.jpa_workshop.data.BookLoanDAO;
import se.lexicon.jpa_workshop.exception.AppResourceNotFoundException;
import se.lexicon.jpa_workshop.exception.BookOnLoanException;
import se.lexicon.jpa_workshop.model.AppUser;
import se.lexicon.jpa_workshop.model.Book;
import se.lexicon.jpa_workshop.model.BookLoan;
import se.lexicon.jpa_workshop.model.Details;
import se.lexicon.jpa_workshop.model.dto.AppUserDTO;
import se.lexicon.jpa_workshop.model.dto.AppUserDTOForm;
import se.lexicon.jpa_workshop.service.AppUserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class AppUserServiceManager implements AppUserService {

    private final BookDAO bookDAO;
    private final BookLoanDAO bookLoanDAO;
    private final AppUserDAO appUserDAO;
    private final ModelMapper modelMapper;

    @Autowired
    public AppUserServiceManager(BookDAO bookDAO, BookLoanDAO bookLoanDAO, AppUserDAO appUserDAO, ModelMapper modelMapper) {
        this.bookDAO = bookDAO;
        this.bookLoanDAO = bookLoanDAO;
        this.appUserDAO = appUserDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public AppUserDTO findById(int id) {
        AppUser appUser = appUserDAO.findById(id);
        if(appUser == null) throw new AppResourceNotFoundException("Could not find user with id: " + id);

        return modelMapper.map(appUser, AppUserDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<AppUserDTO> findAll() {
        Collection<AppUser> appUsers = appUserDAO.findAll();
        Collection<AppUserDTO> appUserDTOS = new ArrayList<>();
        for(AppUser appUser : appUsers){
            appUserDTOS.add(modelMapper.map(appUser, AppUserDTO.class));
        }
        return appUserDTOS;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int create(AppUserDTOForm form) {
        if(form == null) throw new IllegalArgumentException("Create aborted: form was null");
        AppUser appUser = new AppUser();
        appUser.setUsername(form.getUsername());
        appUser.setPassword(form.getPassword());
        appUser.setRegDate(LocalDate.now());
        Details details = new Details(
                0,
                form.getEmail().trim(),
                form.getName().trim(),
                form.getBirthDate()
        );
        appUser.setUserDetails(details);

        AppUser persisted = appUserDAO.create(appUser);

        return persisted.getAppUserId();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int update(int userId, AppUserDTOForm form) {
        AppUser appUser = appUserDAO.findById(userId);
        if(appUser == null) throw new AppResourceNotFoundException("Update aborted could not find user with id: " + userId);

        appUser.getUserDetails().setName(form.getName().trim());
        appUser.getUserDetails().setEmail(form.getEmail().trim());
        appUser.getUserDetails().setBirthDate(form.getBirthDate());

        AppUser updated = appUserDAO.update(appUser);

        return updated.getAppUserId();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int addLoan(int userId, int bookId) {
        //Transaction starts here..

        //AppUser enters persistence context when fetched
        AppUser appUser = appUserDAO.findById(userId);
        //Book enters persistence context when fetched
        Book book = bookDAO.findById(bookId);

        if(appUser == null) throw new AppResourceNotFoundException("Adding loan aborted could not find user with id: " + userId);
        if(book == null) throw new AppResourceNotFoundException("Adding loan aborted could not find book with id: " + bookId);

        if(book.isOnLoan()){
            throw new BookOnLoanException("Adding loan aborted book is already on loan");
        }

        //Creating empty BookLoan is not synced to the database
        BookLoan bookLoan = new BookLoan();
        bookLoan.setLoanDate(LocalDate.now());
        bookLoan.setDueDate(LocalDate.now().plusDays(book.getMaxLoanDays()));
        //BookLoan enters persistence context when persisted
        bookLoan = bookLoanDAO.create(bookLoan);

        //All changes on entities in persisted context are going to be updated in the database 'automagically' at end of transaction
        bookLoan.setBook(book);
        book.setOnLoan(true);
        appUser.addLoans(bookLoan);

        //Transaction ends here
        return appUser.getAppUserId();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int terminateLoan(int loanId) {
        BookLoan bookLoan = bookLoanDAO.findById(loanId);
        if(bookLoan == null) throw new AppResourceNotFoundException("Could not find loan with id: " + loanId);

        Book book = bookLoan.getBook();

        bookLoan.setReturned(true);
        book.setOnLoan(false);

        return bookLoan.getBorrower().getAppUserId();
    }
}
