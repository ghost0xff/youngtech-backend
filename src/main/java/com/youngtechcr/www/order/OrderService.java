package com.youngtechcr.www.order;

import com.youngtechcr.www.customer.Customer;
import com.youngtechcr.www.domain.TimestampedUtils;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.exceptions.custom.QuantityOfElementsException;
import com.youngtechcr.www.order.item.OrderItem;
import com.youngtechcr.www.order.item.OrderItemRepository;
import com.youngtechcr.www.product.Product;
import com.youngtechcr.www.shoppingcart.ShoppingCart;
import com.youngtechcr.www.shoppingcart.item.ShoppingCartItem;
import com.youngtechcr.www.shoppingcart.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final ShoppingCartService cartService;
    private final OrderItemRepository orderItemsRepo;

    public OrderService(
            ShoppingCartService cartService,
            OrderItemRepository orderItemsRepo
    ) {
        this.cartService = cartService;
        this.orderItemsRepo = orderItemsRepo;
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Order makeOrder(Customer customer) {
         ShoppingCart cart = cartService.findCart(customer);
         Set<ShoppingCartItem> cartItems =  cart.getItems();

         if(cartItems.isEmpty()) {
             throw new QuantityOfElementsException(
                 HttpErrorMessages.CANT_ORDER_EMPTY_SHOPPING_CART
             );
         }

        float subtotal = 0;
        float totalDiscount = 0;
        var order = new Order();
        var orderItems = new ArrayList<OrderItem>();
        for(var item : cartItems) {
            Product product = item.getProduct();
            int currentStock = product.getStock();
            int quantity = item.getQuantity();
            if(quantity > currentStock) {
                throw new QuantityOfElementsException(
                        HttpErrorMessages.INSUFFICIENT_STOCK
                );
            }
            float individualPrice = product.getPrice();
            float individualDiscount = individualPrice / 100 * product.getDiscountPercentage();
            float itemPrice = (individualPrice - individualDiscount) * quantity;

            subtotal += individualPrice;
            totalDiscount += individualDiscount;

            var orderItem = new OrderItem();
            orderItem.setProduct(item.getProduct());
            orderItem.setOrder(order);
            orderItem.setQuantity(item.getQuantity());
            TimestampedUtils.setTimestampsToNow(orderItem);
            orderItems.add(orderItem);
        }
        float total = subtotal - totalDiscount;
        float iva = total / 100 * 13;
        total += iva;

        order.setTotal(total);
        order.setSubtotal(subtotal);
        order.setIvaPercentage(13);
        order.setOrderDate(LocalDateTime.now());

        order.setDeliveryDate(null);

        order.setCustomer(customer);
        order.setItems(orderItems);
        TimestampedUtils.setTimestampsToNow(order);

        return order;
     }

}
