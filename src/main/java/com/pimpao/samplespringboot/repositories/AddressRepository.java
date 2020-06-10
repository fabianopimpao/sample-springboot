package com.pimpao.samplespringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pimpao.samplespringboot.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
