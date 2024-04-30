package com.example.projetpayment;

import com.example.projetpayment.entities.Payment;
import com.example.projetpayment.entities.PaymentStatut;
import com.example.projetpayment.entities.PaymentType;
import com.example.projetpayment.entities.Student;
import com.example.projetpayment.repository.PaymentRepository;
import com.example.projetpayment.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class ProjetPaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetPaymentApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, PaymentRepository paymentRepository){
        return args -> {
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .name("chouaib").age(20).code("12345").programId("ING2")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .name("aymane").age(21).code("23456").programId("ING2")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .name("ilias").age(22).code("34567").programId("ING1")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .name("naoufal").age(22).code("45678").programId("ING1")
                    .build());
            PaymentType[] paymentTypes = PaymentType.values();
            Random random = new Random();
            studentRepository.findAll().forEach(student -> {
                int index = random.nextInt(paymentTypes.length);
                for (int i=0; i<10; i++) {
                    Payment payment = Payment.builder()
                            .amount(1000+(int)(Math.random()*100))
                            .type(paymentTypes[index])
                            .status(PaymentStatut.CREATED)
                            .date(LocalDate.now())
                            .student(student)
                            .build();
                    paymentRepository.save(payment);
                }
            });
        };
    }
}
