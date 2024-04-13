package com.eCommerce.inventoryservice.service;

import java.util.List;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eCommerce.inventoryservice.dto.InventoryResponse;
import com.eCommerce.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode){

        log.info("WAIT STARTED");
        Thread.sleep(10000);
        log.info("WAIT ENDED");

        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
        .map(inventory -> 
            InventoryResponse.builder()
                .skuCode(inventory.getSkuCode())
                .isInStock(inventory.getQuantity() > 0)
                .build()
        
        ).toList();
    }

}
