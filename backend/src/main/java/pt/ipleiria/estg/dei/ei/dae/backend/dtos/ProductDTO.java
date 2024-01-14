package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import java.io.Serializable;

public class ProductDTO implements Serializable {
    private long id;
    private String name;
    private int stock;
    private String image;
    private String manufacturerUsername;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, int stock, String image, String manufacturerUsername) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.image = image;
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

    public String getManufacturerUsername() {
        return manufacturerUsername;
    }

    public void setManufacturerUsername(String manufacturerUsername) {
        this.manufacturerUsername = manufacturerUsername;
    }

    public String getImage() {
        return image;
    }


}
