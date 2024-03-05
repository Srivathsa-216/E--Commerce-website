package com.eCommerce.orderservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.eCommerce.orderservice.dto.OrderLineItemsDto;
import com.eCommerce.orderservice.dto.OrderRequest;
import com.eCommerce.orderservice.model.Order;
import com.eCommerce.orderservice.model.OrderLineItems;
import com.eCommerce.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {


    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDto()
            .stream().map(this::mapToDto)
            .toList();

        order.setOrderLineItems(orderLineItems);

        //Call Inventory Service in order to check wheather there is 
        // Suffiecent stock
        Boolean result = webClient.get()
                    .uri("http://localhost:8082/api/inventory/")
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

        if( result ){
            orderRepository.save(order);
        }
        else{
            throw new IllegalArgumentException("Product is Out of Stock or available lesser compared to the requested quantity, Please Try again after some time");
        }
        
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    } 

}
