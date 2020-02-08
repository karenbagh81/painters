package com.test.repository;

import com.test.model.BoughtPaintings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BoughtPaintingsRepository extends JpaRepository<BoughtPaintings, Integer> {

    BoughtPaintings getById(int boughtPaintingsId);
}
