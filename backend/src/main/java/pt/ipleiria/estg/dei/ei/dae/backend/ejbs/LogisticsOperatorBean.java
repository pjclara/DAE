package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.LogisticsOperator;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Orderr;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Hasher;

import java.util.List;

@Stateless
public class LogisticsOperatorBean {

    @PersistenceContext
    private EntityManager entityManager;
    private Hasher hasher = new Hasher();

    public void create(String username, String password, String name, String email, String role) {
        var logisticsOperator = new LogisticsOperator(username, hasher.hash(password), name, email, role);
        entityManager.persist(logisticsOperator);
    }

    public List<LogisticsOperator> all() {
        return entityManager.createNamedQuery("getAllLogisticsOperators", LogisticsOperator.class).getResultList();
    }

    public LogisticsOperator find(String username) {
        return entityManager.find(LogisticsOperator.class, username);
    }

    public void delete(String username) {
        var logisticsOperator = find(username);
        if (logisticsOperator != null) {
            entityManager.remove(logisticsOperator);
        }
    }

    public void update(String username, String password, String name, String email, String role) {
        var logisticsOperator = find(username);
        if (logisticsOperator != null) {
            logisticsOperator.setPassword(password);
            logisticsOperator.setName(name);
            logisticsOperator.setEmail(email);
            logisticsOperator.setRole(role);
            entityManager.merge(logisticsOperator);
        }
    }


    public List<Orderr> getLogisticsOperatorOrders(String username) {
        LogisticsOperator logisticsOperator = entityManager.find(LogisticsOperator.class, username);
        if (logisticsOperator != null) throw new IllegalArgumentException("Logistics Operator not found");

        //get orders from logistics operator

        Query query = entityManager.createQuery("SELECT o FROM Orderr o WHERE o.logisticsOperators.username = :username");
        query.setParameter("username", username);

        return query.getResultList();





    }
}
