package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import java.io.Serializable;

public class ProductDTO {
    private long id;
    private String name;
    private int stock;
    private int orderId;
    private String manufacturerUsername;
    private long packageId;

    public ProductDTO() {
    }

    public ProductDTO(long id, String name, int stock, String manufacturerUsername) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.manufacturerUsername = manufacturerUsername;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getManufacturerUsername() {
        return manufacturerUsername;
    }

    public void setManufacturerUsername(String manufacturerUsername) {
        this.manufacturerUsername = manufacturerUsername;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }
}