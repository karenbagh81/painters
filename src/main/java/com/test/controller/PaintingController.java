package com.test.controller;

import com.test.service.interfaces.PaintingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/painting")
public class PaintingController {

    @Autowired
    private PaintingService paintingService;

    @PutMapping("/save")
    public ResponseEntity uploadFile(@RequestParam(value = "files") MultipartFile file,
                                     @RequestParam(value = "painterId") int painterId,
                                     @RequestParam(value = "price") BigDecimal price) {
        paintingService.uploadFile(file, painterId, price);
        return ResponseEntity.ok().build();
    }
}
