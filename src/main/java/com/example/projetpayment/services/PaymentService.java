package com.example.projetpayment.services;

import com.example.projetpayment.entities.Payment;
import com.example.projetpayment.entities.PaymentStatut;
import com.example.projetpayment.entities.PaymentType;
import com.example.projetpayment.entities.Student;
import com.example.projetpayment.repository.PaymentRepository;
import com.example.projetpayment.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {
    private PaymentRepository paymentRepository;
    private StudentRepository studentRepository;

    public PaymentService(PaymentRepository paymentRepository, StudentRepository studentRepository) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
    }
    public Payment createPayment( MultipartFile file, LocalDate date, double amount,
                                 PaymentType type, String studentCode ) throws IOException {
        Path folderPath = Paths.get(System.getProperty("user.home"),"Bureau", "payments");
        if(!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
        }
        String fileName = UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"),"Bureau", "payments", fileName+".pdf");
        Files.copy(file.getInputStream(), filePath);
        Student student = studentRepository.findByCode(studentCode);
        Payment payment = Payment.builder()
                .date(date).type(type).student(student)
                .amount(amount)
                .file(filePath.toUri().toString())
                .status(PaymentStatut.CREATED)
                .build();
        return paymentRepository.save(payment);
    }
    public Payment updatePaymentStatus(PaymentStatut paymentStatut, Long id) {
        Payment payment = paymentRepository.findById(id).get();
        payment.setStatus(paymentStatut);
        return paymentRepository.save(payment);
    }
    public byte[] getPaymentFile(Long paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }
}
