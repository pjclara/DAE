package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.User;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Hasher;

@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager em;
    @Inject

    private Hasher hasher;
    public User find(String username) {
        return em.find(User.class, username);
    }
    public User findOrFail(String username) {
        var user = em.getReference(User.class, username);
        Hibernate.initialize(user);
        return user;
    }
    public boolean canLogin(String username, String password) {
        var user = find(username);
        return user != null && user.getPassword().equals(hasher.hash(password));
    }
}