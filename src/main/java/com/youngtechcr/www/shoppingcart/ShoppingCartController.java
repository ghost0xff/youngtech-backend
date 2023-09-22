package com.youngtechcr.www.shoppingcart;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/shopping-cart")
public class ShoppingCartController {

    public ShoppingCart findMyCart() {

        return null;
    }

}
