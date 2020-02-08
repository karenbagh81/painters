package com.test.service.implementations;

import com.test.model.xml.Currency;
import com.test.model.xml.CurrencyArray;
import com.test.repository.CurrencyRepository;
import com.test.service.interfaces.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    @Autowired
    private CurrencyRepository currencyRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public Currency getById(int id) {
        return currencyRepository.getById(id);
    }

    @Transactional
    public void save() {
        try {
            JAXBContext context = JAXBContext.newInstance(CurrencyArray.class);
            Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
            File file = new File(getClass().getClassLoader().getResource("currency.xml").getFile());
            CurrencyArray currencyArray = (CurrencyArray) jaxbUnmarshaller.unmarshal(file);
            List<Currency> list = currencyArray.getCurrencies();
            ArrayList<Currency> arrayList = new ArrayList<>();
            for (Currency currency : list) {
                arrayList.add(currency);
            }
            currencyRepository.saveAll(arrayList);
        } catch (JAXBException e) {
            logger.info("Could not unmarshalling");
        }
    }

    @Transactional
    public List<Currency> getAll(Pageable pageable) {
        String[] sort = pageable.getSort().toString().split(":");
        String s = "";
        for (String s1 : sort) {
            s += s1;
        }
        String hql = "from Currency order by " + s;
        Query query = entityManager.createQuery(hql);
        int size = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        query.setFirstResult(pageNumber * size);
        query.setMaxResults(size);
        List<Currency> currencies = query.getResultList();
        return currencies;
    }
}

