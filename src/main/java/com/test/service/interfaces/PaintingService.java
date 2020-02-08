package com.test.service.interfaces;

import com.test.exceptions.NotFoundException;
import com.test.model.Painting;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public interface PaintingService {


    void uploadFile(MultipartFile file, int id, BigDecimal price);

    Painting getById(int id) throws NotFoundException;

    Painting getByConfirmCode(String confirmCode);

    void save(Painting painting);
}
