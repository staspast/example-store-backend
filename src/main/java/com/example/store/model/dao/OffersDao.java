package com.example.store.model.dao;

import com.example.store.model.entity.Offer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
public interface OffersDao extends CrudRepository<Offer, Long> {

    @Query("select offer from Offer as offer " +
            "where (:title IS null OR offer.title = :title)" +
            "and ((:dateFrom is null or :dateTo is null) or offer.dateAdded between :dateFrom and :dateTo)")
    List<Offer> search(@Param("title") String title,
                       @Param("dateFrom")LocalDateTime dateFrom,
                       @Param("dateTo") LocalDateTime dateTo);
}
