package com.link.linkbackend.service.impl;

import com.link.linkbackend.domain.OrderItem;
import com.link.linkbackend.domain.Product;
import com.link.linkbackend.exception.ObjectNotFoundException;
import com.link.linkbackend.repository.OrderRepository;
import com.link.linkbackend.repository.ProductRepository;
import com.link.linkbackend.service.OrderService;
import com.link.linkbackend.service.dto.ItemDTO;
import com.link.linkbackend.service.dto.OrderDTO;
import com.link.linkbackend.service.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    @Override
    public void placeOrder(OrderDTO orderDTO) {
        log.info("Saving order = {} ", orderDTO);
        var orderItems = createItems(orderDTO.getItems());
        var order = orderMapper.toEntity(orderDTO).setItems(orderItems);
        orderRepository.save(order);
    }

    private List<OrderItem> createItems(List<ItemDTO> orderItemsDto) {

        if (CollectionUtils.isEmpty(orderItemsDto)) {
            throw new IllegalArgumentException("List of order items is empty!");
        }

        var products = findProducts(orderItemsDto);

        List<OrderItem> orderItems = new ArrayList<>();

        for (ItemDTO orderItemDTO : orderItemsDto) {

            Product product = products.stream()
                    .distinct()
                    .filter(p -> p.getId().equals(orderItemDTO.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new ObjectNotFoundException("Product with ID " + orderItemDTO.getProductId() + " not found"));

            var item = new OrderItem()
                    .setQuantity(orderItemDTO.getQuantity())
                    .setDescription(orderItemDTO.getDescription())
                    .setProduct(product);
            orderItems.add(item);
        }
        return orderItems;
    }

    private List<Product> findProducts(List<ItemDTO> orderItemsDto) {

        List<Long> productIds = orderItemsDto.stream()
                .map(ItemDTO::getProductId)
                .filter(Objects::nonNull)
                .toList();

        List<Product> products = productRepository.findAllById(productIds);

        if (CollectionUtils.isEmpty(products)) {
            throw new ObjectNotFoundException("No products found for the given product IDs: " + productIds);
        }

        return products;

    }

    @Override
    public List<OrderDTO> findAll() {
        log.info("Recovering all orders...");
        return orderRepository.findAll().stream().map(orderMapper::toDto).toList();
    }

    @Override
    public Page<OrderDTO> findByPage(Pageable pageable) {
        log.info("Recovering list of orders page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return orderRepository.findAll(pageable).map(orderMapper::toDto);
    }
}
