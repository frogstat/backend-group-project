package se.yrgo.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import se.yrgo.domain.Borrower;
import se.yrgo.exception.NotFoundException;
import jakarta.persistence.NoResultException;

import java.util.List;

@Repository
public class BorrowerDaoJpaImpl implements BorrowerDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Borrower borrower) {
        em.persist(borrower);
    }

    @Override
    public void deleteById(long id) {
        Borrower borrower = em.find(Borrower.class, id);
        if (borrower == null) {
            throw new NotFoundException("No borrower with id " + id + " registered in the database.");
        }
        em.remove(borrower);
    }

    @Override
    public Borrower findById(long id) {
        Borrower borrower = em.find(Borrower.class, id);

        if (borrower == null) {
            throw new NotFoundException("No borrower found with id: " + id);
        }
        return borrower;
    }

    @Override
    public Borrower findByEmail(String email) {
        try {
            return em.createQuery(
                            "select b from Borrower b where b.email = :email",
                            Borrower.class)
                    .setParameter("email", email)
                    .getSingleResult();

        } catch (NoResultException e) {
            throw new NotFoundException("No borrower found connected to email: " + email);
        }
    }

    @Override
    public List<Borrower> findAll() {
        return em.createQuery("select b from Borrower b", Borrower.class)
                .getResultList();

    }
}
