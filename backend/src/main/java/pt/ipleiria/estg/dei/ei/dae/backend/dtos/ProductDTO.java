package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

public class ProductDTO {
    private long id;
    private String name;
    private int stock;
    private String image;
    private String manufacturerUsername;

    public ProductDTO(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public ProductDTO(long id, String name, int stock, String image, String manufacturerUsername) {
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
