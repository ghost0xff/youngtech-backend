package com.youngtechcr.www.shoppingcart;


import com.youngtechcr.www.customer.AuthenticationToCustomerConverter;
import com.youngtechcr.www.http.ResponseEntityUtils;
import com.youngtechcr.www.product.ProductService;
import com.youngtechcr.www.shoppingcart.item.ShoppingCartItem;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/shopping-cart")
public class ShoppingCartController {

   private final ShoppingCartService cartService;
   private final ProductService productService;
   private final AuthenticationToCustomerConverter
           authToCustomerConvt;

   public ShoppingCartController(
           ProductService productService,
           ShoppingCartService cartService,
           AuthenticationToCustomerConverter authToCustomerConvt
   ) {
       this.productService = productService;
       this.cartService = cartService;
       this.authToCustomerConvt = authToCustomerConvt;
   }

    @GetMapping
    public ResponseEntity<ShoppingCart> findMyCart(Authentication authn) {
       var customer = authToCustomerConvt.convert(authn);
       return ResponseEntity.ok(cartService.findCart(customer));
    }

    @GetMapping(path = "/items")
    public ResponseEntity<Set<ShoppingCartItem>> findMyItems(
            Authentication authn
    ) {
        var customer = authToCustomerConvt.convert(authn);
        return ResponseEntity.ok(cartService.findItems(customer));
    }

    @PostMapping(path = "/items")
    public ResponseEntity<ShoppingCartItem> addToCart(
            Authentication authn,
            @RequestParam("pid") int productId,
            @RequestParam(
                    name = "quantity",
                    required = false,
                    defaultValue = "1"
            ) int quantity
    ) {
        var customer = authToCustomerConvt.convert(authn);
        var product = productService.findById(productId);
        var cartItem = cartService.addItem(
                customer,
                product,
                quantity
        );
        return ResponseEntityUtils.created(cartItem);
    }


    @DeleteMapping(path = "/items")
    public ResponseEntity<ShoppingCartItem> removeFromCart(
            Authentication authn,
            @RequestParam("pid") int productId,
            @RequestParam(
                    name = "quantity",
                    required = false,
                    defaultValue = "1"
            ) int quantity
    ) {
        var customer = authToCustomerConvt.convert(authn);
        var product = productService.findById(productId);
        cartService.removeItem(
                customer,
                product,
                quantity
        );
        return ResponseEntity.noContent().build();
    }


}
