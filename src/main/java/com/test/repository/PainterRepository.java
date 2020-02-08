package com.test.repository;

import com.test.model.Painter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PainterRepository extends JpaRepository<Painter, Integer> {

    Painter getById(int id);

    Painter getByEmail(String email);

    Painter getByVerification(String verification);
}
