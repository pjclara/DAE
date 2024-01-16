package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderItem;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.UnitProduct;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemDTO {

    private Long id;
    private Integer quantity;

    private UnitProductDTO unitProductDTO;

    public OrderItemDTO(long id, int quantity, UnitProductDTO unitProductDTO) {
        this.id = id;
        this.unitProductDTO = unitProductDTO;
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

    public UnitProductDTO getUnitProductDTO() {
        return unitProductDTO;
    }

    public void setUnitProductDTO(UnitProductDTO unitProductDTO) {
        this.unitProductDTO = unitProductDTO;
    }

    public static List<OrderItemDTO> toDTOs(List<OrderItem> orderItems) {
        return orderItems.stream().map(OrderItemDTO::toDTO).collect(Collectors.toList());
    }

    public static OrderItemDTO toDTO(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getId(),
                orderItem.getQuantity(),
                UnitProductDTO.toDTO(orderItem.getUnitProduct())
        );
    }
}
