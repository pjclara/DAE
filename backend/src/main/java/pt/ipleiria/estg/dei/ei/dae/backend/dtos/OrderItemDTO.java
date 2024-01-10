package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderItem;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemDTO {

    private Long id;

    private Long productId;

    private String productName;

    private String productImage;

    private String productPackageMaterial;

    private Integer quantity;



    public OrderItemDTO() {
    }

    public OrderItemDTO(Long id, String productName , String productImage, String productPackageMaterial,Integer quantity) {
        this.id = id;
        this.productName = productName;
        this.productImage = productImage;
        this.productPackageMaterial = productPackageMaterial;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return productName;
    }

    public void setProduct(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductPackageMaterial() {
        return productPackageMaterial;
    }

    public void setProductPackageMaterial(String productPackageMaterial) {
        this.productPackageMaterial = productPackageMaterial;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }


}
