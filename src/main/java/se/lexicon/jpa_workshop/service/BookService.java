package se.lexicon.jpa_workshop.service;

import se.lexicon.jpa_workshop.model.dto.AuthorDTO;
import se.lexicon.jpa_workshop.model.dto.BookDTO;
import se.lexicon.jpa_workshop.model.dto.BookDTOForm;

import java.util.Collection;

public interface BookService {
    BookDTO findById(int id);
    Collection<BookDTO> findAll();
    Collection<BookDTO> findByAuthor(int authorId);
    Collection<BookDTO> findByAuthorName(String name);
    Collection<BookDTO> findByOnLoanStatus(boolean onLoan);
    int create(BookDTOForm form);
    int update(int bookId, BookDTOForm form);
    int addAuthor(int bookId, int authorId);
    int removeAuthorFromBook(int bookId, int authorId);

}
