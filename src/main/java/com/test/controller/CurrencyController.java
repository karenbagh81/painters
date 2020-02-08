package com.test.controller;

import com.test.model.xml.Currency;
import com.test.service.interfaces.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @PutMapping
    public ResponseEntity save() {
        currencyService.save();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getAll(Pageable pageable) {
       /* Sort sort = Sort.by("country");
        Pageable pageable = PageRequest.of(11, 10, sort);*/
        List<Currency> page = currencyService.getAll(pageable);
        return ResponseEntity.ok(page);
    }
}
