package com.charlie.spring.bean;

import java.util.List;

public class BookStore {
    // 书
    private List<String> bookList;

    // 无参构造器 如果没有其它的构造器,该无参构造器可以不写.,如果有其它的构造器,则必须显式的定义
    public BookStore() {}

    @Override
    public String toString() {
        return "BookStore{" +
                "bookList=" + bookList +
                '}';
    }

    public List<String> getBookList() {
        return bookList;
    }

    public void setBookList(List<String> bookList) {
        this.bookList = bookList;
    }
}
