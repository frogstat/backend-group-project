package se.yrgo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.data.LoanDao;
import se.yrgo.domain.Loan;

import java.util.List;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {
    private final LoanDao loanDao;

    public LoanServiceImpl(LoanDao loanDao) {
        this.loanDao = loanDao;
    }

    @Override
    public void save(Loan loan) {
        loanDao.save(loan);
    }

    @Override
    public void deleteById(long id) {
        loanDao.deleteById(id);
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanDao.getAllLoans();
    }

    @Override
    public List<Loan> getLateLoans() {
        return loanDao.getLateLoans();
    }

    @Override
    public Loan findById(long id) {
        return loanDao.findById(id);
    }
}
