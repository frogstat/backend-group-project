package se.yrgo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.data.BorrowerDao;
import se.yrgo.domain.Borrower;

import java.util.List;

@Service
@Transactional
public class BorrowerServiceImpl implements BorrowerService {
    private final BorrowerDao borrowerDao;

    public BorrowerServiceImpl(BorrowerDao borrowerDao) {
        this.borrowerDao = borrowerDao;
    }

    @Override
    public void delete(long id) {
        borrowerDao.deleteById(id);
    }

    @Override
    public void save(Borrower borrower) {
        borrowerDao.save(borrower);
    }

    @Override
    public Borrower findById(long id) {
        return borrowerDao.findById(id);
    }

    @Override
    public List<Borrower> findAll() {
        return borrowerDao.findAll();
    }

    @Override
    public Borrower findByEmail(String email) {
        return borrowerDao.findByEmail(email);
    }
}
