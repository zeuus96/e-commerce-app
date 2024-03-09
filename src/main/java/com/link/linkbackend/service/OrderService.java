package com.link.linkbackend.service;

import com.link.linkbackend.service.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    void placeOrder(OrderDTO orderDTO);

    List<OrderDTO> findAll();

    Page<OrderDTO> findByPage(Pageable pageable);
}
