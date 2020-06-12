package com.pimpao.samplespringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pimpao.samplespringboot.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
