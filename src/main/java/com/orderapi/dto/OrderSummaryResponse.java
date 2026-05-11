package com.orderapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Aggregate order counts by status")
public class OrderSummaryResponse {

    @Schema(description = "Total number of orders across all statuses")
    private long total;

    @Schema(description = "Orders with status PENDING")
    private long pending;

    @Schema(description = "Orders with status PROCESSING")
    private long processing;

    @Schema(description = "Orders with status SHIPPED")
    private long shipped;

    @Schema(description = "Orders with status DELIVERED")
    private long delivered;

    @Schema(description = "Orders with status CANCELLED")
    private long cancelled;
}
