package com.youngtechcr.www.order;

import com.youngtechcr.www.customer.Customer;
import com.youngtechcr.www.domain.TimestampedUtils;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.exceptions.custom.InvalidElementException;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.exceptions.custom.QuantityOfElementsException;
import com.youngtechcr.www.order.item.OrderItem;
import com.youngtechcr.www.order.item.OrderItemRepository;
import com.youngtechcr.www.product.Product;
import com.youngtechcr.www.sale.Sale;
import com.youngtechcr.www.sale.SaleRepository;
import com.youngtechcr.www.security.annotations.roles.CustomerRole;
import com.youngtechcr.www.security.annotations.roles.EmployeeRole;
import com.youngtechcr.www.shoppingcart.ShoppingCart;
import com.youngtechcr.www.shoppingcart.item.ShoppingCartItem;
import com.youngtechcr.www.shoppingcart.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final ShoppingCartService cartService;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemsRepo;
    private final SaleRepository saleRepo;

    public OrderService(
            ShoppingCartService cartService,
            OrderRepository orderRepo,
            OrderItemRepository orderItemsRepo,
            SaleRepository saleRepo
    ) {
        this.cartService = cartService;
        this.orderRepo = orderRepo;
        this.orderItemsRepo = orderItemsRepo;
        this.saleRepo =  saleRepo;
    }


    @Transactional(readOnly = true)
    public Order checkOrder(Customer customer, int orderId) {
        return customer
            .getOrders()
            .stream()
            .filter(order -> order.getId().equals(orderId))
            .findAny()
            .orElseThrow( () -> new NoDataFoundException(
                HttpErrorMessages
                    .NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND
            ));
    }

    @CustomerRole
    @Transactional
    public List<OrderItem> checkItems(Customer customer, int orderId) {
        return customer
                .getOrders()
                .stream()
                .filter(order -> order.getId().equals(orderId))
                .findAny()
                .orElseThrow( () -> new NoDataFoundException(
                        HttpErrorMessages
                                .NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND
                ))
                .getItems();
    }

    @CustomerRole
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Order makeOrder(Customer customer) {
        Order computed = computeChekoutDetails(customer);
        orderRepo.save(computed);
        // pending the EMAIL integration
        return computed;
     }

     @CustomerRole
     @Transactional(readOnly = true)
     public Order computeChekoutDetails(Customer customer) {
         ShoppingCart cart = cartService.findCart(customer);
         List<ShoppingCartItem> cartItems =  cart.getItems();

         if(cartItems.isEmpty()) {
             logger.debug("Can't order from empty cart");
             throw new QuantityOfElementsException(
                     HttpErrorMessages.CANT_ORDER_EMPTY_SHOPPING_CART
             );
         }

         float subtotal = 0;
         float totalDiscCurrency = 0;
         var order = new Order();
         var orderItems = new ArrayList<OrderItem>();
         for(var item : cartItems) {
             Product product = item.getProduct();
             int currentStock = product.getStock();
             int quantity = item.getQuantity();
             if(quantity > currentStock) {
                 logger.debug("Can't order requestes quantity, not enough stock");
                 throw new QuantityOfElementsException(
                         HttpErrorMessages.INSUFFICIENT_STOCK
                 );
             }
             float individualPrice = product.getPrice();
             float individualDiscount = individualPrice / 100 * product.getDiscountPercentage();
             float itemPrice = product.getPrice() * quantity;

             subtotal += itemPrice;
             totalDiscCurrency += individualDiscount * quantity;

             var orderItem = new OrderItem();
             orderItem.setProduct(item.getProduct());
             orderItem.setOrder(order);
             orderItem.setQuantity(item.getQuantity());
             TimestampedUtils.setTimestampsToNow(orderItem);
             orderItems.add(orderItem);
         }
         float total = subtotal - totalDiscCurrency;
         float iva = total / 100 * 13;
         total += iva;
         order.setTotal(total);
         order.setSubtotal(subtotal);
         order.setTotalDiscountCurrency(totalDiscCurrency);
         order.setIvaPercentage(13);

         //-------------------------------------------------------------------------
         /*
          * SET DELIVERY DATE
          *   - Only on fridays
          *   - Only from 7am - 11am
          *   - if ordered on friday => deliver same day
          *   - if ordered anyother day => deliver next friday
          *
          * This rules are applied because of the requirements of
          * the project (it's a school project from a subject I only
          * have classes on fridays [7am-11am], and therefore
          * the "store" would only be open in that schedule
          * */
         var orderDate = LocalDateTime.now();
         boolean isFriday = orderDate.getDayOfWeek() == DayOfWeek.FRIDAY;
         var friday7am = orderDate.withHour(7).withMinute(0).withSecond(0).withNano(0);
         var friday11am = orderDate.withHour(11).withMinute(0).withSecond(0).withNano(0);
         TemporalAdjuster deliveryDateAdjuster = null;
         LocalDateTime from = null;
         LocalDateTime to =  null;
         if(isFriday && orderDate.isAfter(friday7am) && orderDate.isBefore(friday11am)) {
             deliveryDateAdjuster = TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY);
             from = orderDate; // <-- Literally from NOW :'v
             to = orderDate.with(deliveryDateAdjuster).withHour(11).withMinute(0)
                     .withSecond(0).withNano(0); // ^^^ this friday at 11am
         } else {
             deliveryDateAdjuster = TemporalAdjusters.next(DayOfWeek.FRIDAY);
             from = orderDate.with(deliveryDateAdjuster).withHour(7).withMinute(0)
                     .withSecond(0).withNano(0); // next friday at 7am
             to = orderDate.with(deliveryDateAdjuster).withHour(11).withMinute(0)
                     .withSecond(0).withNano(0); // next friday at 11am
         }
         //-------------------------------------------------------------------------

         order.setOrderDate(orderDate);
         order.setDeliveryTo(to);
         order.setDeliveryFrom(from);
         order.setCustomer(customer);
         order.setItems(orderItems);
         TimestampedUtils.setTimestampsToNow(order);
         return order;
     }

    @CustomerRole
    @Transactional
    public void cancelOrder(Customer customer, int orderId) {
        Order toBeCanceled = customer
                .getOrders()
                .stream()
                .filter(order -> order.getId().equals(orderId))
                .findAny()
                .orElseThrow( () -> new NoDataFoundException(
                        HttpErrorMessages
                                .NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND
                ));
        var now = LocalDateTime.now();
        if (!now.isBefore(toBeCanceled.getDeliveryFrom())) {
            throw new InvalidElementException(
                    HttpErrorMessages.CANT_REMOVE_ORDER_ITEM_AFTER_DELIVER
            );
        }
        toBeCanceled.setCanceled(true);
        orderRepo.save(toBeCanceled);
    }


    @CustomerRole
    @Transactional
    public void removeItem(Order order, Product product, int quantity) {
        var now = LocalDateTime.now();
        if (!now.isBefore(order.getDeliveryFrom())) {
            throw new InvalidElementException(
                    HttpErrorMessages.CANT_REMOVE_ORDER_ITEM_AFTER_DELIVER
            );
        }
        var items = order.getItems();
        var item = items
            .stream()
            .filter(p -> p.getProduct().getId().equals(product.getId()))
            .findAny()
            .orElseThrow( () -> new NoDataFoundException(
            HttpErrorMessages
              .NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND
            ));

        if(quantity > item.getQuantity()) {
            throw new QuantityOfElementsException(
                    HttpErrorMessages
                            .CANT_DELETE_MORE_ITEMS_THAN_ARE_AVAILABLE
            );
        }
        int newQuantity = item.getQuantity() - quantity;
        if(newQuantity > 0) {
            orderItemsRepo.save(item);
        } else {
            orderItemsRepo.delete(item);
        }
    }

    @EmployeeRole
    @Transactional
    public Order deliver(Customer customer, int orderId) {
        Order order = customer
                .getOrders()
                .stream()
                .filter(o -> o.getId().equals(orderId))
                .findAny()
                .orElseThrow( () -> new NoDataFoundException(
                        HttpErrorMessages
                                .NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND
                ));
        order.setDelivered(true);

        var sale = new Sale();
        sale.setOrder(order);
        float iva = order.getTotal() / 100 * 13; // 13%
        sale.setReveneu(order.getTotal() - iva);
        sale.setTaxDebt(iva);

        // timestamps should be updated
        TimestampedUtils.setTimestampsToNow(sale);
        orderRepo.save(order);
        saleRepo.save(sale);
        return order;
    }

}
