package com.youngtechcr.www.shoppingcart.item;

import com.youngtechcr.www.shoppingcart.ShoppingCart;
import com.youngtechcr.www.shoppingcart.item.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartItemsRepository
        extends JpaRepository<ShoppingCartItem, Integer> {

    Optional<List<ShoppingCartItem>> findByShoppingCart(
            ShoppingCart shoppingCart
    );

}
