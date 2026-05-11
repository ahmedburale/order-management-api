package com.orderapi.dto;

import com.orderapi.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
@Schema(description = "Full order details")
public class OrderResponse {

    @Schema(description = "Unique order identifier", example = "1")
    private Long id;

    @Schema(description = "Customer name", example = "Alice Johnson")
    private String customerName;

    @Schema(description = "Product name", example = "Wireless Keyboard")
    private String product;

    @Schema(description = "Quantity ordered", example = "2")
    private Integer quantity;

    @Schema(description = "Current order status", example = "PENDING")
    private OrderStatus status;

    @Schema(description = "Order creation timestamp")
    private OffsetDateTime createdAt;

    @Schema(description = "Last update timestamp")
    private OffsetDateTime updatedAt;
}
