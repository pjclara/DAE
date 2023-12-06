package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class PackageBean {

    @PersistenceContext
    private EntityManager em;

    public void create(long code, String type, String material) { //  List<Sensor> sensorData
        var package_ = new Package(code, type, material); // o nome esta como 'package_' pois dava conflito sem o '_' ----> ver depois
        em.persist(package_);
    }

    public List<Package> all() {
        return em.createNamedQuery("getAllPackages", Package.class).getResultList();
    }

    public Package find(long packageCode) {
        return em.find(Package.class, packageCode);
    }

    public Package findOrFail(long packageCode) throws MyEntityNotFoundException {
        var package_ = find(packageCode);
        if (package_ == null) {
            throw new MyEntityNotFoundException("Package with code '" + packageCode + "' not found");
        }
        return package_;
    }

    public void update(long code, String type, String material) throws MyEntityNotFoundException {
        var package_ = findOrFail(code);

        em.lock(package_, LockModeType.OPTIMISTIC);

        package_.setPackagingType(type);
        package_.setPackagingMaterial(material);
        // ver sobre os sensores -- ver se existe, se nao existir adicionar -- ver codigo abaixo..?

        // a "lazy way" that avoids querying the course every time we do an update to the student
        /*if (!Objects.equals(student.getCourse().getCode(), courseCode)) {
            student.setCourse(courseBean.findOrFail(courseCode));
        }*/
    }

    public void remove(Long code) throws MyEntityNotFoundException {
        em.remove(findOrFail(code));
    }

}
