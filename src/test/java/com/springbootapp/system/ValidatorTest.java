package com.springbootapp.system;

import com.springbootapp.models.Donor;
import com.springbootapp.repositories.DonorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ValidatorTest {

    Donor donor;

    @Autowired
    DonorRepository repo;

    @Test(expected = ConstraintViolationException.class)
    public void validation(){
        donor = new Donor();
        donor.setId(1L);
        donor.setFirstName("Smith");
        repo.save(donor);
    }

}
