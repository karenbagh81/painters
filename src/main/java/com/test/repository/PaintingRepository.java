package com.test.repository;

import com.test.model.Painting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaintingRepository extends JpaRepository<Painting, Integer> {

    Painting getById(int id);

    Painting getByConfirmCode(String confirmCode);

}
