package se.yrgo.services;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.data.BorrowerDao;
import se.yrgo.domain.Borrower;
import se.yrgo.exception.UserAlreadyExistsException;

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
        try {
            borrowerDao.save(borrower);
        } catch (PersistenceException e){
            throw new UserAlreadyExistsException("User with email " + borrower.getEmail() + " already exists");
        }
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
        try {
            return borrowerDao.findByEmail(email);
        } catch ( Exception e) {
            return null;
        }
    }
}
