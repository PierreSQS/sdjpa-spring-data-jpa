package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import java.util.Collections;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Modified by Pierrot on 7/22/22.
 */
@Component
public class AuthorDaoHibernate implements AuthorDao {

    private final EntityManagerFactory emf;

    public AuthorDaoHibernate(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    @Override
    public List<Author> findAllAuthorsByLastName(String lastname, Pageable pageable) {
        EntityManager em = getEntityManager();

        try {
            String jpql = "SELECT a FROM Author a where a.lastName = :lastName ";

            if (pageable.getSort().getOrderFor("firstname") != null) {
                jpql = jpql + " order by a.firstName " + Objects.requireNonNull(pageable.getSort().getOrderFor("firstname"))
                        .getDirection().name();
            }

            TypedQuery<Author> query = em.createQuery(jpql, Author.class);

            query.setParameter("lastName", lastname);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());

            return query.getResultList();
        } catch (NullPointerException npe){
            return Collections.emptyList();
        }finally {
            em.close();
        }
    }

    @Override
    public Author findAuthorById(Long id) {
        return null;
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }
}
