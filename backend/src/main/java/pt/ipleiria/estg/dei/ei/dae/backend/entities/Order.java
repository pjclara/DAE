package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class Order extends Versionable{
    private List<Product> products;
    @NotNull
    private Package transportPackage;
    @NotNull
    private boolean isDelivered;

    public Order() {
    }
    public Order(List<Product> products, Package transportPackage) {
        this.products = products;
        this.transportPackage = transportPackage;
        this.isDelivered = false;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Package getTransportPackage() {
        return transportPackage;
    }

    public void setTransportPackage(Package transportPackage) {
        this.transportPackage = transportPackage;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }
}
