package com.test.util;

import com.test.enums.Status;
import com.test.model.AbstractModel;
import com.test.service.interfaces.EmailService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Helper {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public void register(AbstractModel abstractModel) {
        abstractModel.setPassword(encoder.encode(abstractModel.getPassword()));
        abstractModel.setStatus(Status.UNVERIFIED);
        resetVerification(abstractModel);
    }

    @Transactional
    public void resetVerification(AbstractModel abstractModel) {
        abstractModel.setVerificationTime(System.currentTimeMillis());
        RandomString randomString = new RandomString();
        abstractModel.setVerification(randomString.nextString());
       /* String text = ("Your verification code is: " + abstractModel.getVerification());
        emailService.sendSimpleMessage(abstractModel.getEmail(), "Please verify your email", text);*/
    }

    public void endPoint(AbstractModel abstractModel) {
        new Helper().resetPasswordCode(abstractModel);
        abstractModel.setStatus(Status.UNVERIFIED);
        String text = ("Your verification code is: " + abstractModel.getResetPasswordCode());
        emailService.sendSimpleMessage(abstractModel.getEmail(), "Please verify your email", text);
    }

    public void resetPasswordCode(AbstractModel abstractModel) {
        RandomString randomString = new RandomString();
        abstractModel.setResetPasswordCode(randomString.nextString());
        abstractModel.setResetPasswordTime(System.currentTimeMillis());
    }
}
