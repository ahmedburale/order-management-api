package com.orderapi.dto;

import com.orderapi.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Schema(description = "Request body for updating an existing order")
public class UpdateOrderRequest {

    @Schema(description = "Updated customer name", example = "Bob Smith")
    private String customerName;

    @Schema(description = "Updated product name", example = "Mechanical Keyboard")
    private String product;

    @Min(value = 1, message = "Quantity must be at least 1")
    @Schema(description = "Updated quantity", example = "3")
    private Integer quantity;

    @Schema(description = "New order status", example = "PROCESSING")
    private OrderStatus status;
}
