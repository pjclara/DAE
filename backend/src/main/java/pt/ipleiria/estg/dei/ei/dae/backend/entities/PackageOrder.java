package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@NamedQuery(name = "getAllPackageOrders", query = "SELECT pp FROM PackageOrder pp ORDER BY pp.id")
public class PackageOrder extends Package {

    @OneToMany(mappedBy = "packageOrder", cascade = CascadeType.REMOVE)
    private List<Orderr> orderrs;

    public PackageOrder() {
    }

    public PackageOrder(PackagingType packagingType, String packagingMaterial) {
        super(packagingType, packagingMaterial);
    }

    public List<Orderr> getOrderrs() {
        return orderrs;
    }

    public void setOrderrs(List<Orderr> orderrs) {
        this.orderrs = orderrs;
    }

    public void addOrderr(Orderr orderr) {
        this.orderrs.add(orderr);
        orderr.setPackageOrder(this);
    }

    public void removeOrderr(Orderr orderr) {
        this.orderrs.remove(orderr);
        orderr.setPackageOrder(null);
    }



}
