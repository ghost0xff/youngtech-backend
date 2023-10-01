package com.youngtechcr.www.shoppingcart;

import com.youngtechcr.www.customer.Customer;
import com.youngtechcr.www.domain.TimestampedUtils;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.exceptions.custom.QuantityOfElementsException;
import com.youngtechcr.www.product.Product;
import com.youngtechcr.www.security.annotations.roles.CustomerRole;
import com.youngtechcr.www.shoppingcart.item.ShoppingCartItem;
import com.youngtechcr.www.shoppingcart.item.ShoppingCartItemsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository cartRepo;
    private final ShoppingCartItemsRepository itemsRepo;
    private static final Logger logger =
            LoggerFactory.getLogger(ShoppingCartService.class);

    public ShoppingCartService(
            ShoppingCartRepository cartRepo,
            ShoppingCartItemsRepository itemsRepo
    ) {
        this.cartRepo = cartRepo;
        this.itemsRepo = itemsRepo;
    }

    @CustomerRole
    @Transactional
    public ShoppingCart findCart(Customer customer) {
        return this
                .cartRepo
                .findByCustomer(customer)
                .orElseGet(() -> {
                    var cart = new ShoppingCart();
                    cart.setCustomer(customer);
                    cart.setItems(Collections.emptySet());
                    TimestampedUtils.setTimestampsToNow(cart);
                    var created = cartRepo.save(cart);
                    logger.trace("Created new cart since none existed for customer with id " + customer.getId());
                    return created;
                });
    }
    @CustomerRole
    @Transactional(readOnly = true)
    public Set<ShoppingCartItem> findItems(Customer customer) {
        var cart = this.findCart(customer);
        return cart.getItems();
    }

    @CustomerRole
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public ShoppingCartItem addItem(
            Customer customer,Product product, int quantity
    ) {
        // check if product already exists in cart
        ShoppingCart cart = findCart(customer);
        Optional<ShoppingCartItem> optionalItem = cart
                .getItems()
                .stream()
                .filter(item -> {
                    return item
                        .getProduct()
                        .getId()
                        .equals(product.getId());
                })
                .findFirst();

        boolean alreadyExistsInCart = optionalItem.isPresent();
        if (alreadyExistsInCart) {
            // augment quantity in cart
            ShoppingCartItem existingItem = optionalItem.orElseThrow();
            int newQuantity = existingItem.getQuantity() + quantity;
            int currentStock = product.getStock();

            existingItem.setQuantity(newQuantity);
            ShoppingCartItem modifiedItem = itemsRepo.save(existingItem);
            logger.trace("Augmented quantity of products in item with id "+ modifiedItem.getId());
            return modifiedItem;
        }
        // add new item to cart with specified quantity
        var item = new ShoppingCartItem();
        item.setProduct(product);
        item.setShoppingCart(cart);
        item.setQuantity(quantity);
        TimestampedUtils.setTimestampsToNow(item);
        ShoppingCartItem added = itemsRepo.save(item);
        logger.trace("Added a completely new item to cart with id " + cart.getId());
        return added;
    }


    @CustomerRole
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void removeItem(
       Customer customer,Product product, int quantity
    ) {
       ShoppingCart cart = findCart(customer);
       Optional<ShoppingCartItem> optionalItem = cart
                .getItems()
                .stream()
                .filter(item -> {
                    return item
                            .getProduct()
                            .getId()
                            .equals(product.getId());
                })
                .findFirst();

       boolean exitsInCart = optionalItem.isPresent();
       if(!exitsInCart)  {
           throw new NoDataFoundException(
               HttpErrorMessages
               .CANT_DELETE_ITEM_IF_NOT_PRESENT
           );
       }

       ShoppingCartItem existinItem = optionalItem.orElseThrow();
       if(quantity > existinItem.getQuantity()) {
            // cant delete more than have, dummy!
           throw new QuantityOfElementsException(
               HttpErrorMessages
               .CANT_DELETE_MORE_ITEMS_THAN_ARE_AVAILABLE
           );
        }

       int newQuantity = existinItem.getQuantity() - quantity;
       if (newQuantity == 0) {
            itemsRepo.delete(existinItem);
            logger.trace("Removed one item from shopping cart since quanity reached threashold");
            return;
       }
       existinItem.setQuantity(newQuantity);
       TimestampedUtils.updateTimeStamps(existinItem, existinItem.getCreatedAt() );
       itemsRepo.save(existinItem);
       logger.trace("Decreased quantity of one item in shopping cart with id " + cart.getId());
       return;
    }

}
