package com.pimpao.samplespringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pimpao.samplespringboot.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

}
