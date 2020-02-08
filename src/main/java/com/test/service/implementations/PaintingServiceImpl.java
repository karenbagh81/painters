package com.test.service.implementations;

import com.test.exceptions.NotFoundException;
import com.test.model.Painter;
import com.test.model.Painting;
import com.test.repository.PaintingRepository;
import com.test.service.interfaces.PainterService;
import com.test.service.interfaces.PaintingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class PaintingServiceImpl implements PaintingService {

    private final Logger logger = LoggerFactory.getLogger(PaintingServiceImpl.class);

    private String path = "C:\\Users\\marat.bagdasaryan\\Desktop\\Images";

    @Autowired
    private PaintingRepository paintingRepository;

    @Autowired
    private PainterService painterService;

    @Override
    public Painting getById(int id) throws NotFoundException {
        Painting painting =  paintingRepository.getById(id);
        if (painting == null){
            throw new NotFoundException("painting not found");
        }
        return painting;
    }

    public void save(Painting painting){
        paintingRepository.save(painting);
    }

    @Transactional
    public void uploadFile(MultipartFile file, int painterId, BigDecimal price) {
        try {
            Path copyLocation = Paths.get(path + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            Painting painting = new Painting();
            painting.setName(file.getOriginalFilename());
            painting.setPath(path + "\\" + file.getOriginalFilename());
            painting.setPrice(price);
            painting.setSold(false);
            Painter painter = painterService.getById(painterId);
            List<Painting> list = painter.getPaintings();
            list.add(painting);
            painter.setPaintings(list);
            painterService.update(painter);
        } catch (IOException e) {
            logger.info("file upload error");
        } catch (NotFoundException e) {
            logger.info("not found");
        }
    }

    @Override
    public Painting getByConfirmCode(String confirmCode) {
        return paintingRepository.getByConfirmCode(confirmCode);
    }
}
