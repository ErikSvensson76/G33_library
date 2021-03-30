package se.lexicon.jpa_workshop.model.dto;

import java.io.Serializable;

public class BookDTO implements Serializable {
    private int bookId;
    private String isbn;
    private String title;
    private int maxLoanDays;
    private boolean onLoan;

    public BookDTO(int bookId, String isbn, String title, int maxLoanDays, boolean onLoan) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.maxLoanDays = maxLoanDays;
        this.onLoan = onLoan;
    }

    public BookDTO() {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMaxLoanDays() {
        return maxLoanDays;
    }

    public void setMaxLoanDays(int maxLoanDays) {
        this.maxLoanDays = maxLoanDays;
    }

    public boolean isOnLoan() {
        return onLoan;
    }

    public void setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
    }
}
