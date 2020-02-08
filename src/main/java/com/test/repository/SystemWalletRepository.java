package com.test.repository;


import com.test.model.SystemWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SystemWalletRepository extends JpaRepository<SystemWallet, BigDecimal> {

    SystemWallet getById(int id);
}
