package com.test.service.implementations;

import com.test.enums.Status;
import com.test.exceptions.DuplicateException;
import com.test.exceptions.InvalidParamException;
import com.test.exceptions.NotFoundException;
import com.test.model.AbstractModel;
import com.test.model.Painter;
import com.test.model.User;
import com.test.repository.PainterRepository;
import com.test.service.interfaces.PainterService;
import com.test.service.interfaces.UserService;
import com.test.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;


@Service
public class PainterServiceImpl implements PainterService {


    public static final long CURRENTY_FOR_HOURS = 12 * 60 * 60 * 1000;

    @Autowired
    private PainterRepository painterRepository;

    @Autowired
    private Helper helper;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    public void delete(int id) {
        painterRepository.deleteById(id);
    }

    @Transactional
    public void update(Painter painter) throws NotFoundException {
        Painter painter1 = painterRepository.getByEmail(painter.getEmail());
        if (painter1 == null) {
            throw new NotFoundException("User is not found");
        }
        painter1.setName(painter.getName());
        painter1.setPhone(painter.getPhone());
        painterRepository.save(painter1);
    }

    public Painter getByEmail(String email) {
        return painterRepository.getByEmail(email);
    }

    public List<Painter> getAll() {
        return painterRepository.findAll();
    }

    public Painter getById(int id) {
        return painterRepository.getById(id);
    }

    @Transactional
    public void register(AbstractModel abstractModel) throws NotFoundException {
        int phone = abstractModel.getPhone();
        String email = abstractModel.getEmail();
        String password = abstractModel.getPassword();
        String name = abstractModel.getName();

        User user1 = userService.getByEmail(email);
        User user = new User();
        if (user1 == null) {
            user.setPassword(password);
            user.setEmail(email);
            user.setPhone(phone);
            user.setName(name);
            helper.register(user);
            userService.saveUser(user);
        } else {
            Painter painter = new Painter();
            painter.setPhone(user1.getPhone());
            painter.setName(user1.getName());
            painter.setPassword(user1.getPassword());
            painter.setEmail(user1.getEmail());
            painter.setUser(user1);
            helper.register(painter);
            painterRepository.save(painter);
            return;
        }
        Painter painter1 = painterRepository.getByEmail(email);
        if (painter1 != null) {
            throw new DuplicateException("Duplicated painter data");
        }
        Painter painter = new Painter();
        painter.setPhone(phone);
        painter.setName(name);
        painter.setPassword(password);
        painter.setEmail(email);
        painter.setUser(user);
        helper.register(painter);
        painterRepository.save(painter);
    }

    @Transactional
    public void verify(String email, String verification) throws NotFoundException {
        Painter painter = painterRepository.getByEmail(email);
        if (painter != null) {
            if (verification.equals(painter.getVerification())) {
                if ((System.currentTimeMillis() - painter.getVerificationTime()) >= CURRENTY_FOR_HOURS) {
                    helper.resetVerification(painter);
                    painterRepository.save(painter);
                } else {
                    painter.setStatus(Status.VERIFIED);
                    painter.setVerification(null);
                    painterRepository.save(painter);
                }
            } else
                throw new InvalidParamException("Confirm verification code");
        } else
            throw new NotFoundException("Painter is not found");
    }

    public Painter login(String email, String password) throws NotFoundException {
        Painter painter = painterRepository.getByEmail(email);
        if (painter != null) {
            if (encoder.matches(password, painter.getPassword())) {
                if (painter.getStatus() == Status.UNVERIFIED) {
                    throw new InvalidParamException("Confirm verification code");
                }
                return painter;
            } else
                throw new InvalidParamException("Incorrect email or password");
        } else
            throw new NotFoundException("Painter is not found");
    }

    @Transactional
    public void savePainting(Painter painter) throws NotFoundException {
        Painter painter1 = painterRepository.getByEmail(painter.getEmail());
        if (painter1 == null) {
            throw new NotFoundException("Painter is not found");
        }
        painter1.setPaintings(painter.getPaintings());
        painterRepository.save(painter1);
    }

    @Transactional
    public void endPoint(String email) throws NotFoundException {
        Painter painter = painterRepository.getByEmail(email);
        if (painter == null) {
            throw new NotFoundException("Painter is not found");
        } else {
            helper.endPoint(painter);
            painterRepository.save(painter);
        }
    }

    @Transactional
    public void resetPassword(String email, String resetPasswordCode, String newPassword) throws NotFoundException {
        Painter painter = painterRepository.getByEmail(email);
        if (painter != null) {
            if (resetPasswordCode.equals(painter.getResetPasswordCode())) {
                if ((System.currentTimeMillis() - painter.getResetPasswordTime()) >= CURRENTY_FOR_HOURS) {
                    helper.resetPasswordCode(painter);
                    painterRepository.save(painter);
                } else {
                    painter.setPassword(encoder.encode(newPassword));
                    painter.setVerificationTime(System.currentTimeMillis());
                    painter.setStatus(Status.VERIFIED);
                    painter.setVerification(null);
                    painter.setResetPasswordCode(null);
                    painterRepository.save(painter);
                }
            } else
                throw new InvalidParamException("Confirm resetPasswordCode");
        } else
            throw new NotFoundException("Painter is not found");
    }

    @Transactional
    public List<Painter> getByIdAndEmail(int id, String email) {
        String hql = "from Painter p where p.id = :id and p.email = :email";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", id);
        query.setParameter("email", email);
        List<Painter> painters = query.getResultList();
        return painters;
    }
}
