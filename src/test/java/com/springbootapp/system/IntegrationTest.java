package com.springbootapp.system;

import com.springbootapp.models.BloodPackage;
import com.springbootapp.models.BloodType;
import com.springbootapp.models.Donor;
import com.springbootapp.repositories.BloodPackageRepository;
import com.springbootapp.repositories.DonorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.springbootapp.controllers.DonorControllerTest.IdTest;
import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class IntegrationTest {

    private static final Long IdTest = 1L;

    @Autowired
    DonorRepository donors;

    @Autowired
    BloodPackageRepository packages;

    @Autowired
    DonorRepository testRepo;

    Donor donor;
    Donor donor1;

    @Autowired
    BloodPackageRepository packets;

    BloodPackage packet;

    @Test
    public void addDonor() {
        donor = new Donor();
        donor.setId(IdTest);
        donor.setAddress("testAddress");
        donor.setBirthDate(new Date(1999, 10, 10));
        donor.setFirstName("name");
        donor.setLastName("surname");
        donors.save(donor);
        assertThat(donors.findById(donor.getId()).getFirstName()).isEqualToIgnoringCase(donor.getFirstName());
    }

    @Test
    public void findDonors() {
        donor = new Donor();
        donor.setId(IdTest);
        donor.setAddress("testAddress");
        donor.setBirthDate(new Date(1999, 10, 10));
        donor.setFirstName("name");
        donor.setLastName("surname");
        donors.save(donor);

        donor1 = new Donor();
        donor1.setId(IdTest);
        donor1.setAddress("testAddress");
        donor1.setBirthDate(new Date(1999, 10, 10));
        donor1.setFirstName("name");
        donor1.setLastName("surname");
        donors.save(donor1);

        testRepo.save(donor1);
        testRepo.save(donor);
        assertThat(donors.findByLastName("surname")).isEqualTo(testRepo.findByLastName("surname"));
    }

    @Test
    public void updateDonor() {
        donor = new Donor();
        donor.setId(1L);
        donor.setAddress("donorAddress");
        donor.setBirthDate(new Date(1999, 10, 10));
        donor.setFirstName("name");
        donor.setLastName("surname");
        donors.save(donor);


        donor.setAddress("anotherAddress");
        donor.setId(donor.getId());
        donors.save(donor);

        assertThat(donors.findById(1L).getAddress()).isEqualToIgnoringCase("anotherAddress");
    }

    @Test
    public void addPackage(){
        packet = new BloodPackage();
        packet.setId(1L);
        packet.setShelfLife(new Date(2018,10,11));
        packet.setAcceptanceDate(new Date(2017,12,01));
        packet.setBloodType(BloodType.ABRhMinus);
        packet.setCapacityInLiters(3.6f);

        packets.save(packet);

        assertThat(packets.findById(1L).getAcceptanceDate()).isEqualTo(new Date(2017,12,01));
        assertThat(packets.findById(1L).getBloodType()).isEqualTo(BloodType.ABRhMinus);
        assertThat(packets.findById(1L).getCapacityInLiters()).isEqualTo(3.6f);
        assertThat(packets.findById(1L).getShelfLife()).isEqualTo(new Date(2018,10,11));
      }



}
