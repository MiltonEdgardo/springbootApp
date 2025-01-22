package com.springboot.springbootApp.dao;

import com.lambdaworks.crypto.SCryptUtil;
import com.springboot.springbootApp.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao{

    int INDEX_ZERO = 0;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<User> getUsers() {
        String query = "FROM User";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void delete(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public void signInUsers(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUserByCredentials(User user) {
        String query = "FROM User WHERE email = :email";
        List<User> userList = entityManager.createQuery(query)
                .setParameter("email", user.getEmail())
                .getResultList();
        
        if (userList.isEmpty()) {
            return null;
        }

        String passwordHashed = userList.get(INDEX_ZERO).getPassword();
        if (SCryptUtil.check(user.getPassword(), passwordHashed)) {

            return userList.get(INDEX_ZERO);

        }
        return null;
    }
}
