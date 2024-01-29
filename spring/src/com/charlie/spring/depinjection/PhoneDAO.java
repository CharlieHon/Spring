package com.charlie.spring.depinjection;

import org.springframework.stereotype.Repository;

@Repository
public class PhoneDAO extends BaseDAO<Phone> {
    @Override
    public void save() {
        System.out.println("PhoneDAO save() ...");
    }
}
