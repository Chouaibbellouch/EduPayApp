package com.example.projetpayment.web;

import com.example.projetpayment.entities.Payment;
import com.example.projetpayment.entities.PaymentStatut;
import com.example.projetpayment.entities.PaymentType;
import com.example.projetpayment.entities.Student;
import com.example.projetpayment.repository.PaymentRepository;
import com.example.projetpayment.repository.StudentRepository;
import com.example.projetpayment.services.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
public class PaymentRestController {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;
    private PaymentService paymentService;

    public PaymentRestController(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }
    @GetMapping(path = "/payments")
    public List<Payment> allPayments() {
        return paymentRepository.findAll();
    }
    @GetMapping(path = "/students/{code}/payments")
    public List<Payment> paymentsByStudent(@PathVariable String code) {
        return paymentRepository.findByStudentCode(code);
    }
    @GetMapping(path = "/payments/byStatus")
    public List<Payment> paymentsByStatus(@RequestParam PaymentStatut status) {
        return paymentRepository.findByStatus(status);
    }
    @GetMapping(path = "/payments/byType")
    public List<Payment> paymentsByType(@RequestParam PaymentType type) {
        return paymentRepository.findByType(type);
    }
    @GetMapping(path = "/payments/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentRepository.findById(id).get();
    }
    @GetMapping(path = "/students")
    public List<Student> allStudents(){
        return studentRepository.findAll();
    }
    @GetMapping(path = "/students/{code}")
    public Student getStudentByCode(@PathVariable String code) {
        return studentRepository.findByCode(code);
    }
    @GetMapping(path = "/studentsByProgramId")
    public List<Student> getStudentsByProgramId(@RequestParam String programId) {
        return studentRepository.findByProgramId(programId);
    }
    @PutMapping("/payments/{id}")
    public Payment updatePaymentStatus(@RequestParam PaymentStatut paymentStatut, @PathVariable Long id) {
        return this.paymentService.updatePaymentStatus(paymentStatut, id);
    }
    @PostMapping(path = "/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment createPayment(@RequestParam MultipartFile file, LocalDate date, double amount,
                                 PaymentType type, String studentCode ) throws IOException {
        return this.paymentService.createPayment(file, date, amount, type, studentCode);
    }
    @GetMapping(path = "/paymentFile/{paymentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {
        return paymentService.getPaymentFile(paymentId);
    }
}
