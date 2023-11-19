package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.validation.constraints.NotNull;


public class Product extends Versionable{
    @NotNull
    private String name;
    @NotNull
    private Package productPackage;

    public Product() {
    }

    public Product(String name, Package productPackage) {
        this.name = name;
        this.productPackage = productPackage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Package getProductPackage() {
        return productPackage;
    }

    public void setProductPackage(Package productPackage) {
        this.productPackage = productPackage;
    }
}
