package com.pimpao.samplespringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pimpao.samplespringboot.domain.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
