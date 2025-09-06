package com.megaorders.services;

import com.megaorders.models.Item;
import com.megaorders.models.Product;
import com.megaorders.models.enums.DeliveryStatus;
import com.megaorders.repositories.ItemRepository;
import com.megaorders.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ItemRepository itemRepository, ProductRepository productRepository) {
        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
    }
}
