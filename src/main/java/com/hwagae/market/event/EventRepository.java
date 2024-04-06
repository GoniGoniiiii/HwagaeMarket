package com.hwagae.market.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity,Integer> {
    public List<EventEntity> findAllByOrderByEventNumDesc();
}