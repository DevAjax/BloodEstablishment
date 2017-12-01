package com.springbootapp.repositories;

import com.springbootapp.models.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DonorRepository extends CrudRepository<Donor, Long> {
    List<Donor> findByLastName(String lastName);

    Donor findById(Long id);
}
