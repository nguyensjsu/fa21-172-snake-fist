package com.example.springbackoffice;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {

}
