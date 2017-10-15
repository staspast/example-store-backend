package com.example.store.model.dao;

import com.example.store.model.entity.Offer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OffersDao extends CrudRepository<Offer, Long> {
}
