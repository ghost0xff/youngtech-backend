package com.youngtechcr.www.order;

import com.youngtechcr.www.customer.AuthenticationToCustomerConverter;
import com.youngtechcr.www.customer.Customer;
import com.youngtechcr.www.http.ResponseEntityUtils;
import com.youngtechcr.www.order.item.OrderItem;
import com.youngtechcr.www.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

    private final AuthenticationToCustomerConverter authToCustomerConvt;
    private final OrderService orderService;
    private final ProductService productService;

    public OrderController(
            AuthenticationToCustomerConverter authToCustomerConvt,
            OrderService orderService,
            ProductService productService
    ) {
        this.authToCustomerConvt = authToCustomerConvt;
        this.orderService = orderService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Order> makeOrder(Authentication authn) {
        Customer customer = authToCustomerConvt.convert(authn);
        Order order = orderService.makeOrder(customer);
        return ResponseEntityUtils.created(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> checkOrders(Authentication authn) {
        Customer customer = authToCustomerConvt.convert(authn);
        return ResponseEntity.ok(customer.getOrders());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Order> checkOrder(
            Authentication authn,
            @PathVariable int id
    ) {
        Customer customer = authToCustomerConvt.convert(authn);
        var order = orderService.checkOrder(customer, id);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Order> cancelOrder(
            Authentication authn,
            @PathVariable int id
    ) {
        Customer customer = authToCustomerConvt.convert(authn);
        orderService.cancelOrder(customer, id);
        return ResponseEntity.noContent().build();
    };

    @GetMapping(path = "/{id}/items")
    public ResponseEntity<List<OrderItem>> checkItems(
            Authentication authn,
            @PathVariable(name = "id") int orderId
    ) {
       Customer customer = authToCustomerConvt.convert(authn);
       List<OrderItem> items = orderService.checkItems(customer, orderId);
       return ResponseEntity.ok(items);
    }

    @DeleteMapping(path = "/{id}/items")
    public ResponseEntity<OrderItem> removeItems(
            Authentication authn,
            @PathVariable(name = "id") int orderId,
            @RequestParam("pid") int productId,
            @RequestParam(
                name = "quantity",
                required = false,
                defaultValue = "1"
            ) int quantity
    ) {
        Customer customer = authToCustomerConvt.convert(authn);
        Order order = orderService.checkOrder(customer, orderId);
        var prod = productService.findById(productId);
        orderService.removeItem(order, prod , quantity);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/{id}/delivered")
    public ResponseEntity<Order> markAsDelivered(
            Authentication authn,
            @PathVariable int id
    ) {
        Customer customer = authToCustomerConvt.convert(authn);
        var delivered = orderService.deliver(customer, id);
        return ResponseEntityUtils.created(delivered);
    }


    public ResponseEntity<?> checkoutInfo(
            Authentication authn
    ) {
        Customer customer = authToCustomerConvt.convert(authn);
        orderService.checkoutInfo(customer);


        return ResponseEntity.ok("");
    }
}
