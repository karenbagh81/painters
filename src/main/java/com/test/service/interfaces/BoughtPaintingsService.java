package com.test.service.interfaces;

import com.test.exceptions.AccessDeniedException;
import com.test.exceptions.NotFoundException;
import com.test.model.BoughtPaintings;

import java.math.BigDecimal;

public interface BoughtPaintingsService {


    void buy(String email, int paintingId, BigDecimal price, String confirmCode, int currencyId) throws AccessDeniedException, NotFoundException;

    void check(int paintingId, String email) throws NotFoundException;

    void paintingReceived(int boughtPaintingsId) throws NotFoundException;

    BoughtPaintings getById(int boughtPaintingsId) throws NotFoundException;
}
