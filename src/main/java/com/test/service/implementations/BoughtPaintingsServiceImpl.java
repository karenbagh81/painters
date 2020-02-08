package com.test.service.implementations;

import com.test.enums.BoughtStatus;
import com.test.exceptions.AccessDeniedException;
import com.test.exceptions.NotFoundException;
import com.test.model.*;
import com.test.repository.BoughtPaintingsRepository;
import com.test.service.interfaces.*;
import net.bytebuddy.utility.RandomString;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;


@Service
public class BoughtPaintingsServiceImpl implements BoughtPaintingsService {


    private final org.slf4j.Logger logger = LoggerFactory.getLogger(BoughtPaintingsServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PaintingService paintingService;

    @Autowired
    private BoughtPaintingsRepository boughtPaintingsRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SystemWalletService systemWalletService;

    @Autowired
    private CurrencyService currencyService;

    @Value("${System.Wallet.Id}")
    private int systemWalletId;


    @Transactional
    public void check(int paintingId, String email) throws NotFoundException {
        Painting painting = paintingService.getById(paintingId);
        RandomString randomString = new RandomString(4);
        String code = randomString.nextString();
        while ((paintingService.getByConfirmCode(code)) != null) {
            code = randomString.nextString();
        }
        painting.setConfirmCode(code);
        String text = ("Your confirm code is: " + code);
        emailService.sendSimpleMessage(email, "Please confirm your confirmCode", text);
    }

    @Transactional
    public void buy(String email, int paintingId, BigDecimal price, String confirmCode, int currencyId)
            throws AccessDeniedException, NotFoundException {

        Painting painting = paintingService.getById(paintingId);
        User user = userService.getByEmail(email);
        Wallet wallet = user.getWallet();
        BoughtPaintings boughtPaintings = new BoughtPaintings();

        if (painting.getConfirmCode() == null) {
            throw new NotFoundException("Confirm code not found");
        }
        if (!painting.getConfirmCode().equals(confirmCode)) {
            throw new AccessDeniedException("Confirm code is false");
        }
        if (painting.isSold()) {
            throw new AccessDeniedException("Painting is sold");
        }
        if ((wallet.getBalance().compareTo(painting.getPrice())) < 0) {
            throw new AccessDeniedException("Not enough money");
        }
        if ((price.compareTo(painting.getPrice().multiply(new BigDecimal(0.30), MathContext.DECIMAL32))) >= 0 &&
                (price.compareTo(painting.getPrice().multiply(new BigDecimal(0.50)))) <= 0) {
            boughtPaintings.setStatus(BoughtStatus.PREPAYMENT);
        } else {
            throw new AccessDeniedException("Amount must be between 30% and 50%");
        }
        boughtPaintings.setUser(user);
        boughtPaintings.setPrice(price);
        boughtPaintings.setPainting(painting);
        boughtPaintings.setDate(new Date());
        boughtPaintingsRepository.save(boughtPaintings);
        painting.setSold(true);
        payAmount(painting, user, price, currencyId);
    }

    private void payAmount(Painting painting, User user, BigDecimal price, int currencyId) {
        Wallet userWallet = user.getWallet();
        userWallet.setBalance(userWallet.getBalance().subtract(painting.getPrice()));

        Painter painter = painting.getPainter();
        User painterUser = painter.getUser();
        Wallet painterWallet = painterUser.getWallet();
        painterWallet.setBalance(painterWallet.getBalance().add(price));
        BigDecimal restMoney = painting.getPrice().subtract(price);
        systemWallet(restMoney, currencyId);
    }

    private void systemWallet(BigDecimal money, int currencyId) {
        SystemWallet wallet = systemWalletService.getById(systemWalletId);
        wallet.setBalance(money);
        wallet.setCurrency(currencyService.getById(currencyId));
    }

    @Transactional
    public void paintingReceived(int boughtPaintingsId) throws NotFoundException {

        BoughtPaintings boughtPaintings = getById(boughtPaintingsId);
        Painting painting = boughtPaintings.getPainting();
        Wallet userWallet = painting.getPainter().getUser().getWallet();
        BigDecimal paintingPrice = painting.getPrice();
        SystemWallet systemWallet = systemWalletService.getById(systemWalletId);
        BigDecimal money = paintingPrice.multiply(new BigDecimal(0.10), MathContext.DECIMAL32);
        BigDecimal bd = systemWallet.getBalance().subtract(money);
        userWallet.setBalance(userWallet.getBalance().add(bd));
        systemWallet.setBalance(money);

        boughtPaintings.setStatus(BoughtStatus.FINISHED);
        boughtPaintings.setDate(new Date());
        boughtPaintings.setPrice(painting.getPrice());
    }

    @Override
    public BoughtPaintings getById(int boughtPaintingsId) throws NotFoundException {
        BoughtPaintings boughtPaintings = boughtPaintingsRepository.getById(boughtPaintingsId);
        if (boughtPaintings == null) {
            throw new NotFoundException("BoughtPaintings not found");
        }
        return boughtPaintings;
    }
}
