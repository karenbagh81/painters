package com.test.service.interfaces;

import com.test.exceptions.NotFoundException;
import com.test.model.AbstractModel;
import com.test.model.Painter;

import java.util.List;

public interface PainterService {
    Painter getById(int id);

    void delete(int id);

    void update(Painter painter) throws NotFoundException;

    Painter getByEmail(String email);

    List<Painter> getAll();

    void register(AbstractModel abstractModel) throws NotFoundException;

    void verify(String email, String verification) throws NotFoundException;

    void savePainting(Painter painter) throws NotFoundException;

    Painter login(String email, String password) throws NotFoundException;

    void resetPassword(String email, String resetPasswordCode, String newPassword) throws NotFoundException;

    void endPoint(String email) throws NotFoundException;

    List<Painter> getByIdAndEmail(int id, String email);
}
