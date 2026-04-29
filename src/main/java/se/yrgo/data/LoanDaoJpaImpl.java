package se.yrgo.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import se.yrgo.domain.Loan;
import se.yrgo.exception.NotFoundException;

import java.util.List;

@Repository
public class LoanDaoJpaImpl implements LoanDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Loan loan) {
        em.persist(loan);
    }

    @Override
    public void deleteById(long id) {
        Loan loan = em.find(Loan.class, id);

        if (loan == null) {
            throw new NotFoundException("No loan found with id: " + id);
        }
        em.remove(loan);
    }

    @Override
    public List<Loan> getAllLoans() {
        return em.createQuery("select l from Loan l", Loan.class)
                .getResultList();
    }

    @Override
    public List<Loan> getLateLoans() {
        return em.createQuery("select l from Loan l where l.returnDate is null and l.dueDate < current_date", Loan.class)
                .getResultList();
    }

    @Override
    public Loan findById(long id) {
        Loan loan = em.find(Loan.class, id);

        if (loan == null) {
            throw new NotFoundException("No loan found with id: " + id);
        }
        return loan;
    }
}
