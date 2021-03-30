package se.lexicon.jpa_workshop.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appUserId;
    @Column(unique = true)
    private String username;
    private String password;
    private LocalDate regDate;
    @OneToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @JoinColumn(table = "app_user", name = "details_id")
    private Details userDetails;

    @OneToMany(
            cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE},
            fetch = FetchType.LAZY,
            mappedBy = "borrower"
    )
    private List<BookLoan> loans;

    public AppUser(int appUserId, String username, String password, LocalDate regDate, Details userDetails) {
        this.appUserId = appUserId;
        this.username = username;
        this.password = password;
        this.regDate = regDate;
        this.userDetails = userDetails;
    }

    public AppUser() {
    }

    public int getAppUserId() {
        return appUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public Details getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(Details userDetails) {
        this.userDetails = userDetails;
    }

    public List<BookLoan> getLoans() {
        if(loans == null) loans = new ArrayList<>();
        return loans;
    }

    public void setLoans(List<BookLoan> loans) {
        if(loans == null) loans = new ArrayList<>();
        if(loans.isEmpty()){
            if(this.loans != null){
                for(BookLoan loan : this.loans){
                    loan.setBorrower(null);
                }
            }
        }else{
            if(this.loans == null) this.loans = new ArrayList<>();
            for(BookLoan loan : loans){
                if(!this.loans.contains(loan)){
                    loan.setBorrower(this);
                }
            }
        }
        this.loans = loans;
    }

    public void addLoans(BookLoan...loans){
        if(loans.length != 0){
            if(this.loans == null) this.loans = new ArrayList<>();
            for(BookLoan loan : loans){
                if(!this.loans.contains(loan)){
                    this.loans.add(loan);
                    loan.setBorrower(this);
                }
            }
        }
    }

    public void removeLoans(BookLoan...loans){
        if(loans.length != 0){
            if(this.loans == null) this.loans = new ArrayList<>();
            for(BookLoan loan : loans){
                if(this.loans.remove(loan)){
                    loan.setBorrower(null);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return appUserId == appUser.appUserId && Objects.equals(regDate, appUser.regDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appUserId, regDate);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "appUserId=" + appUserId +
                ", regDate=" + regDate +
                '}';
    }
}
