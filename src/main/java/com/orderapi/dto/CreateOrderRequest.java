package com.orderapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request body for creating a new order")
public class CreateOrderRequest {

    @NotBlank(message = "Customer name is required")
    @Schema(description = "Full name of the customer", example = "Alice Johnson")
    private String customerName;

    @NotBlank(message = "Product is required")
    @Schema(description = "Name of the product ordered", example = "Wireless Keyboard")
    private String product;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Schema(description = "Number of units ordered", example = "2")
    private Integer quantity;
}
