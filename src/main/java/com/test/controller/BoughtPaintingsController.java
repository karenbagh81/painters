package com.test.controller;


import com.test.exceptions.AccessDeniedException;
import com.test.exceptions.NotFoundException;
import com.test.service.interfaces.BoughtPaintingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/boughtPaintings")
public class BoughtPaintingsController {

    @Autowired
    private BoughtPaintingsService boughtPaintingsService;

    @PostMapping("/buy")
    public ResponseEntity buy(@RequestParam(value = "email") String email,
                              @RequestParam(value = "paintingId") int paintingId,
                              @RequestParam(value = "price") BigDecimal price,
                              @RequestParam(value = "confirmCode") String confirmCode,
                              @RequestParam(value = "currencyId") int currencyId) throws AccessDeniedException, NotFoundException {
        boughtPaintingsService.buy(email, paintingId, price, confirmCode, currencyId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/wallet")
    //@RolesAllowed(value = "ADMIN")
    public ResponseEntity paintingReceived(@RequestParam(value = "boughtPaintingsId") int boughtPaintingsId) throws NotFoundException {
        boughtPaintingsService.paintingReceived(boughtPaintingsId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check")
    public ResponseEntity check(@RequestParam(value = " paintingId") int paintingId,
                                @RequestParam("email") String email) throws NotFoundException {
        boughtPaintingsService.check(paintingId, email);
        return ResponseEntity.ok().build();
    }
}
