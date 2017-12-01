package com.springbootapp.models;

import org.junit.Test;
import org.springframework.util.SerializationUtils;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class SerializationTest {
    @Test
    public void serialization(){
        Donor donor = new Donor();
        donor.setId(1L);
        donor.setFirstName("name");
        donor.setLastName("surname");
        donor.setAddress("temporary");
        donor.setBirthDate(new Date(1999,19,19));

        Donor test = (Donor) SerializationUtils.deserialize(SerializationUtils.serialize(donor));
        assertThat(test.getFirstName()).isEqualToIgnoringCase(donor.getFirstName());
    }
}
