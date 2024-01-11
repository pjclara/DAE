package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import java.util.List;

public class OrderItemDTO {

    private Long id;
    private Integer quantity;

    private ProductDTO productDTO;

    public OrderItemDTO(long id, int quantity, ProductDTO productDTO) {
        this.id = id;
        this.productDTO = productDTO;
        this.quantity = quantity;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }


}
