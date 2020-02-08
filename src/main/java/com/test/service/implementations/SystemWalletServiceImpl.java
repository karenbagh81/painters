package com.test.service.implementations;

import com.test.model.SystemWallet;
import com.test.repository.SystemWalletRepository;
import com.test.service.interfaces.SystemWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SystemWalletServiceImpl implements SystemWalletService {

    @Autowired
    private SystemWalletRepository systemWalletRepository;


    public void save(SystemWallet systemWallet) {
        systemWalletRepository.save(systemWallet);
    }

    public SystemWallet getById(int id){
        return systemWalletRepository.getById(id);
    }
}
