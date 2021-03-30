package se.lexicon.jpa_workshop.service;

import se.lexicon.jpa_workshop.model.dto.AppUserDTO;
import se.lexicon.jpa_workshop.model.dto.AppUserDTOForm;

import java.util.Collection;

public interface AppUserService {
    AppUserDTO findById(int id);
    Collection<AppUserDTO> findAll();
    int create(AppUserDTOForm form);
    int update(int userId, AppUserDTOForm form);
    int addLoan(int userId, int bookId);
    int terminateLoan(int loanId);
}
