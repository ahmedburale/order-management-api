package com.orderapi.controller;

import com.orderapi.dto.*;
import com.orderapi.enums.OrderStatus;
import com.orderapi.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order lifecycle management")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "List all orders", description = "Returns all orders. Optionally filter by status.")
    @ApiResponse(responseCode = "200", description = "Orders retrieved")
    public ResponseEntity<List<OrderResponse>> listOrders(
            @Parameter(description = "Filter by order status")
            @RequestParam(required = false) OrderStatus status) {
        return ResponseEntity.ok(orderService.listOrders(status));
    }

    @PostMapping
    @Operation(summary = "Create an order", description = "Creates a new order with PENDING status.")
    @ApiResponse(responseCode = "201", description = "Order created",
            content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    @ApiResponse(responseCode = "400", description = "Validation failed")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(req));
    }

    @GetMapping("/summary")
    @Operation(summary = "Order summary", description = "Returns aggregate order counts grouped by status.")
    @ApiResponse(responseCode = "200", description = "Summary retrieved",
            content = @Content(schema = @Schema(implementation = OrderSummaryResponse.class)))
    public ResponseEntity<OrderSummaryResponse> getSummary() {
        return ResponseEntity.ok(orderService.getSummary());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an order", description = "Returns a single order by ID.")
    @ApiResponse(responseCode = "200", description = "Order found")
    @ApiResponse(responseCode = "404", description = "Order not found")
    public ResponseEntity<OrderResponse> getOrder(
            @Parameter(description = "Order ID") @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update an order", description = "Updates any combination of fields on an existing order.")
    @ApiResponse(responseCode = "200", description = "Order updated")
    @ApiResponse(responseCode = "404", description = "Order not found")
    public ResponseEntity<OrderResponse> updateOrder(
            @Parameter(description = "Order ID") @PathVariable Long id,
            @Valid @RequestBody UpdateOrderRequest req) {
        return ResponseEntity.ok(orderService.updateOrder(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order", description = "Permanently removes an order.")
    @ApiResponse(responseCode = "204", description = "Order deleted")
    @ApiResponse(responseCode = "404", description = "Order not found")
    public ResponseEntity<Void> deleteOrder(
            @Parameter(description = "Order ID") @PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
