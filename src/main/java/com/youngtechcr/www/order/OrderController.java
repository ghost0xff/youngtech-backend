package com.youngtechcr.www.order;

import com.youngtechcr.www.customer.AuthenticationToCustomerConverter;
import com.youngtechcr.www.customer.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

    private final AuthenticationToCustomerConverter authToCustomerConvt;
    private final OrderService orderService;

    public OrderController(
            AuthenticationToCustomerConverter authToCustomerConvt,
            OrderService orderService) {
        this.authToCustomerConvt = authToCustomerConvt;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> makeOrder(Authentication authn) {
        Customer customer = authToCustomerConvt.convert(authn);
        orderService.makeOrder(customer);
        return null;
    }

}
