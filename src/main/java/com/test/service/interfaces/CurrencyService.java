package com.test.service.interfaces;

import com.test.model.xml.Currency;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CurrencyService {

    void save();

    List<Currency> getAll(Pageable pageable);

    Currency getById(int id);
}
