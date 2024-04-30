package com.example.projetpayment.repository;

import com.example.projetpayment.entities.Payment;
import com.example.projetpayment.entities.PaymentStatut;
import com.example.projetpayment.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStudentCode(String Code);
    List<Payment> findByStatus(PaymentStatut status);
    List<Payment> findByType(PaymentType type);
}
