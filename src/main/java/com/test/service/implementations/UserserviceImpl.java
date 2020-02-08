package com.test.service.implementations;

import com.test.enums.Status;
import com.test.exceptions.DuplicateException;
import com.test.exceptions.InvalidParamException;
import com.test.exceptions.NotFoundException;
import com.test.model.User;
import com.test.repository.UserRepository;
import com.test.service.interfaces.UserService;
import com.test.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserserviceImpl implements UserService {

    public static final long CURRENTY_FOR_HOURS = 12 * 60 * 60 * 1000;

    @Autowired
    private Helper helper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveUser(User user) {
        User user1 = userRepository.getByEmail(user.getEmail());
        if (user1 != null) {
            throw new DuplicateException("Duplicated user data");
        }
        userRepository.save(user);
    }

    public User getById(int id) throws NotFoundException {
        User user = userRepository.getById(id);
        if (user == null) {
            throw new NotFoundException("user not found");
        }
        return user;
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }


    @Transactional
    public void update(User user) {
        User user1 = userRepository.getByEmail(user.getEmail());
        user1.setEmail(user.getEmail());
        user1.setPhone(user.getPhone());
        user1.setName(user.getName());
        user1.setWallet(user.getWallet());
        userRepository.save(user1);
    }

    public User getByEmail(String email) throws NotFoundException{
        User user = userRepository.getByEmail(email);
        if (user == null) {
            throw new NotFoundException("User is not found");
        }
        return user;
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        return page;
    }


    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void register(User user) throws NotFoundException{
        if (getByEmail(user.getEmail()) != null){
            throw new DuplicateException("Duplicated user data");
        }
        helper.register(user);
        userRepository.save(user);
    }

    @Transactional
    public void verify(String email, String verification) {
        User user = userRepository.getByEmail(email);
        if (verification.equals(user.getVerification())) {
            if ((System.currentTimeMillis() - user.getVerificationTime()) >= CURRENTY_FOR_HOURS) {
                helper.resetVerification(user);
                userRepository.save(user);
            } else {
                user.setStatus(Status.VERIFIED);
                user.setVerification(null);
                userRepository.save(user);
            }
        } else
            throw new InvalidParamException("confirm verification code");
    }

    public User login(String email, String password) {
        User user = userRepository.getByEmail(email);
        if (encoder.matches(password, user.getPassword())) {
            if (user.getStatus() == Status.UNVERIFIED) {
                throw new InvalidParamException("confirm verification code");
            }
            return user;
        } else
            throw new InvalidParamException("incorrect email or password");
    }

    @Transactional
    public void saveAuthority(User user) {
        User user1 = userRepository.getByEmail(user.getEmail());
        user1.setAuthority(user.getAuthority());
        userRepository.save(user1);
    }

    @Transactional
    public void endPoint(String email) {
        User user = userRepository.getByEmail(email);
        helper.endPoint(user);
        userRepository.save(user);
    }

    @Transactional
    public void resetPassword(String email, String resetPasswordCode, String newPassword) {
        User user = userRepository.getByEmail(email);
        if (resetPasswordCode.equals(user.getResetPasswordCode())) {
            if ((System.currentTimeMillis() - user.getResetPasswordTime()) >= CURRENTY_FOR_HOURS) {
                helper.resetPasswordCode(user);
                userRepository.save(user);
            } else {
                user.setPassword(encoder.encode(newPassword));
                user.setVerificationTime(System.currentTimeMillis());
                user.setStatus(Status.VERIFIED);
                user.setVerification(null);
                user.setResetPasswordCode(null);
                user.setResetPasswordTime(0);
                userRepository.save(user);
            }
        } else
            throw new InvalidParamException("confirm resetPasswordCode");
    }
}
