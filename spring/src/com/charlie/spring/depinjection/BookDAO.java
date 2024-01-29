package com.charlie.spring.depinjection;

import org.springframework.stereotype.Repository;

@Repository
public class BookDAO extends BaseDAO<Book> {
    @Override
    public void save() {
        System.out.println("BookDAO 的save() ...");
    }
}
