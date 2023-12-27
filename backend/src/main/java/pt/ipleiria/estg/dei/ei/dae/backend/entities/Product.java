package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import javax.sound.midi.Sequence;
import java.util.List;

@Entity
@NamedQuery(name = "getAllProducts", query = "SELECT p FROM Product p ORDER BY p.name")
public class Product extends Versionable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TBL_METADATA_ID_SEQ")
    @Column(name="id")
    private Long id;

    @NotNull
    private String name;
    private String image;
    @NotNull
    private int stock;
    @NotNull
    @ManyToOne
    private Manufacturer manufacturer;
    @OneToOne
    private Package productPackage;

    public Product() {
    }

    public Product(String name, int stock, Manufacturer manufacturer) {
        this.name = name;
        this.stock = stock;
        this.manufacturer = manufacturer;
        this.productPackage = null;
        this.image = null;
    }
    public Product(String name, int stock, Manufacturer manufacturer, Package productPackage) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.manufacturer = manufacturer;
        this.productPackage = productPackage;
        this.image = null;
    }

    public Product(String name, int stock, String image, Manufacturer manufacturer) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.manufacturer = manufacturer;
        this.productPackage = null;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Package getProductPackage() {
        return productPackage;
    }

    public void setProductPackage(Package productPackage) {
        this.productPackage = productPackage;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
