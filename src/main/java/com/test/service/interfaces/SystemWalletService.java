package com.test.service.interfaces;


import com.test.model.SystemWallet;

public interface SystemWalletService {


    void save(SystemWallet systemWallet);

    SystemWallet getById(int id);
}
