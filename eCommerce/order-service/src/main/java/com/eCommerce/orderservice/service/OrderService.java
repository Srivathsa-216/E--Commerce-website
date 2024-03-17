package com.eCommerce.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.eCommerce.orderservice.dto.InventoryResponse;
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
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDto()
            .stream().map(this::mapToDto)
            .toList();

        order.setOrderLineItems(orderLineItems);

        List<String> skuCodes = order.getOrderLineItems().stream()
                    .map(OrderLineItems::getSkuCode)
                    .toList();

        //Call Inventory Service in order to check wheather there is 
        // Suffiecent stock
        InventoryResponse[] inventoryResponsesArray = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponsesArray)
            .allMatch(InventoryResponse::isInStock);

        
        if( allProductsInStock ){
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
