package se.lexicon.jpa_workshop.service.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpa_workshop.data.AuthorDAO;
import se.lexicon.jpa_workshop.data.BookDAO;
import se.lexicon.jpa_workshop.exception.AppResourceNotFoundException;
import se.lexicon.jpa_workshop.model.Author;
import se.lexicon.jpa_workshop.model.Book;
import se.lexicon.jpa_workshop.model.dto.AuthorDTO;
import se.lexicon.jpa_workshop.model.dto.BookDTO;
import se.lexicon.jpa_workshop.model.dto.BookDTOForm;
import se.lexicon.jpa_workshop.service.BookService;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class BookServiceManager implements BookService {

    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;
    private final ModelMapper modelMapper;

    @Autowired
    public BookServiceManager(BookDAO bookDAO, AuthorDAO authorDAO, ModelMapper modelMapper) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO findById(int id) {
        Book book = bookDAO.findById(id);
        if(book == null) throw new AppResourceNotFoundException("Could not find book with id: " + id);
        return modelMapper.map(book, BookDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<BookDTO> findAll() {
        Collection<Book> books = bookDAO.findAll();
        Collection<BookDTO> bookDTOS = new ArrayList<>();
        for(Book book : books){
            bookDTOS.add(modelMapper.map(book, BookDTO.class));
        }
        return bookDTOS;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<BookDTO> findByAuthor(int authorId) {
        Collection<Book> books = bookDAO.findByAuthorId(authorId);
        Collection<BookDTO> bookDTOS = new ArrayList<>();
        for(Book book : books){
            bookDTOS.add(modelMapper.map(book, BookDTO.class));
        }
        return bookDTOS;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<BookDTO> findByAuthorName(String name) {
        Collection<Book> books = bookDAO.findByAuthorName(name);
        Collection<BookDTO> bookDTOS = new ArrayList<>();
        for(Book book : books){
            bookDTOS.add(modelMapper.map(book, BookDTO.class));
        }
        return bookDTOS;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<BookDTO> findByOnLoanStatus(boolean onLoan) {
        Collection<Book> books = bookDAO.findByOnLoanStatus(onLoan);
        Collection<BookDTO> bookDTOS = new ArrayList<>();
        for(Book book : books){
            bookDTOS.add(modelMapper.map(book, BookDTO.class));
        }
        return bookDTOS;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int create(BookDTOForm form) {
        if(form == null) throw new IllegalArgumentException("Create aborted form was null");
        Book book = new Book();
        book.setTitle(form.getTitle().trim());
        book.setIsbn(form.getIsbn());
        book.setMaxLoanDays(form.getMaxLoanDays());

        Book persisted = bookDAO.create(book);

        return persisted.getBookId();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int update(int bookId, BookDTOForm form) {
        if(form == null) throw new IllegalArgumentException("Update aborted form was " + null);
        Book book = bookDAO.findById(bookId);
        if(book == null) throw new AppResourceNotFoundException("Update aborted could not find book with id: " + bookId);

        book.setIsbn(form.getIsbn());
        book.setTitle(form.getTitle());
        book.setMaxLoanDays(form.getMaxLoanDays());

        book = bookDAO.update(book);
        return book.getBookId();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int addAuthor(int bookId, int authorId) {
        Book book = bookDAO.findById(bookId);
        if(book == null) throw new AppResourceNotFoundException("Operation aborted could not find book with id: " + bookId);
        Author author = authorDAO.findById(authorId);
        if(author == null) throw new AppResourceNotFoundException("Operation aborted could not find author with id: " + authorId);

        book.addAuthor(author);

        book = bookDAO.update(book);
        return book.getBookId();
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int removeAuthorFromBook(int bookId, int authorId) {
        Book book = bookDAO.findById(bookId);
        if(book == null) throw new AppResourceNotFoundException("Operation aborted could not find book with id: " + bookId);
        Author author = authorDAO.findById(authorId);
        if(author == null) throw new AppResourceNotFoundException("Operation aborted could not find author with id: " + authorId);

        book.removeAuthor(author);

        book = bookDAO.update(book);

        return book.getBookId();
    }
}
