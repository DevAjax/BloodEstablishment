package com.springbootapp.repositories;

import com.springbootapp.models.BloodPackage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ajax on 25.10.2017.
 */
public interface BloodPackageRepository extends CrudRepository<BloodPackage, Long> {
    BloodPackage findById(Long id);
}
