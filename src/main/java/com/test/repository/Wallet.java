package com.test.repository;

import com.test.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
interface WalletRepository extends JpaRepository<Wallet, BigDecimal> {
}
