package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

public class ProductDTO {
    private long id;
    private String name;
    private int stock;
    private String image;
    private String manufacturerUsername;
    private long packageId;

    private PackageDTO packageDTO;

    public ProductDTO() {
    }

    public ProductDTO(long id, String name, int stock, String image, String manufacturerUsername, PackageDTO packageDTo) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.image = image;
        this.manufacturerUsername = manufacturerUsername;
        this.packageDTO = packageDTo;
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

    public PackageDTO getPackageDTO() {
        return packageDTO;
    }

    public void setPackageDTO(PackageDTO packageDTO) {
        this.packageDTO = packageDTO;
    }
    public String getImage() {
        return image;
    }

    public long getPackageId() {
        return packageId;
    }


}
