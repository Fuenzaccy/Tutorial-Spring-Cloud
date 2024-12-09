package com.ccsw.tutorial_loan.loan;

import com.ccsw.tutorial_loan.loan.model.Loan;
import com.ccsw.tutorial_loan.loan.model.LoanDto;
import com.ccsw.tutorial_loan.loan.model.LoanSearchDto;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface LoanService {
    Loan get(Long id);

    List<Loan> findAll();

    void save(Long id, LoanDto loanDto);

    Page<Loan> findPage(LoanSearchDto dto, String titleGame, String clientName, Date date);

    void save(LoanDto dto) throws Exception;

    void delete(Long id);

    Page<LoanDto> findPage(LoanSearchDto dto);

    Page<LoanDto> findLoansFiltered(LoanSearchDto dto);


}