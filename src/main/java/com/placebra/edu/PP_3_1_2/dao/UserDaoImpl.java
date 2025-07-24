package com.placebra.edu.PP_3_1_2.dao;

import com.placebra.edu.PP_3_1_2.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User findUserByUsername(String username) {
        try {
            return em.createQuery(
                            "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> findAllUsers() {
        List<User> allUsers = em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.roles", User.class)
                .getResultList();
        return allUsers;
    }

    @Override
    public void saveUser(User user) {
        em.persist(user);
    }

    @Override
    public void removeUserById(int id) {
        em.remove(em.getReference(User.class, id));
    }

    @Override
    public User findUserById(int id) {
        Query query = em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :id", User.class);
        query.setParameter("id", id);
        User user = (User) query.getSingleResult();
        return user;
    }
}
