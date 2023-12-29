package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderItem;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemDTO {

    private Long id;

    private Product product;

    private Integer quantity;

    public OrderItemDTO() {
    }

    public OrderItemDTO(Long id, Product product, Integer quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public static OrderItemDTO from(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getId(),
                orderItem.getProduct(),
                orderItem.getQuantity()
        );
    }

    public static List<OrderItemDTO> from(List<OrderItem> orderItem) {
        return orderItem.stream().map(OrderItemDTO::from).collect(Collectors.toList());
    }
}
