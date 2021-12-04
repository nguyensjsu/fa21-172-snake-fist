package com.example.springpayments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsCommandRepository extends JpaRepository<PaymentsCommand,Long> {
}
