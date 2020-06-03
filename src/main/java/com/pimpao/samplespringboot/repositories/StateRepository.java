package com.pimpao.samplespringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pimpao.samplespringboot.domain.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {

}
