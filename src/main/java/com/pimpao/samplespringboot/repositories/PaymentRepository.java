package com.pimpao.samplespringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pimpao.samplespringboot.domain.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
