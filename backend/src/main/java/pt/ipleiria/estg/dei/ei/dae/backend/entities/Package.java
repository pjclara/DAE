package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;


@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPackages",
                query = "SELECT p FROM Package p ORDER BY p.packagingType"
        ),

        @NamedQuery(
                name = "getAllRoleTypePackages",
                query = "SELECT p FROM Package p WHERE p.packagingType IN :rolesTypes ORDER BY p.packagingType"
        ),
        @NamedQuery(
                name = "getPackageByType",
                query = "SELECT p FROM Package p WHERE p.packagingType = :packagingType")
})
public class Package extends Versionable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TBL_METADATA_ID_SEQ")
    @Column(name="id")
    private Long id;
    @NotNull
    private PackagingType packagingType;  // [1º,2º,3º(Produto) ou encomenda / transporte]
    @NotNull
    private String packagingMaterial;

    @OneToOne(mappedBy = "productPackage")
    private Product product;

    @OneToOne(mappedBy = "orderPackage")
    private Orderr order;

    //@OneToMany
    @OneToMany(mappedBy = "packagging", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Sensor> sensors;

    public Package() {
        this.sensors =  new ArrayList<>();
    }


    public Package(PackagingType packagingType, String packagingMaterial) {
        this.packagingType = packagingType;
        this.packagingMaterial = packagingMaterial;
        this.sensors =  new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PackagingType getPackagingType() {
        return packagingType;
    }

    public void setPackagingType(PackagingType packagingType) {
        this.packagingType = packagingType;
    }

    public String getPackagingMaterial() {
        return packagingMaterial;
    }

    public void setPackagingMaterial(String packagingMaterial) {
        this.packagingMaterial = packagingMaterial;
    }

    public Orderr getOrder() {
        return order;
    }

    public void setOrder(Orderr order) {
        this.order = order;
        if (order != null) {
            order.setOrderPackage(this);
        }
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void addSensor(Sensor sensor) {
        if (sensor == null || sensors.contains(sensor)) {
            return;
        }
        sensors.add(sensor);
        sensor.setPackaging(this); // Link the sensor to the package
    }

    public void removeSensor(Sensor sensor) {
        if (sensor == null || !sensors.contains(sensor)) {
            return;
        }
        sensors.remove(sensor);
        sensor.setPackaging(null); // Unlink the sensor from the package
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        if (product != null) {
            product.setProductPackage(this);
        }
    }
}
