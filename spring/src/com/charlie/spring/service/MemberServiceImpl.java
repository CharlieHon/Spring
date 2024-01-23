package com.charlie.spring.service;

import com.charlie.spring.dao.MemberDAOImpl;

public class MemberServiceImpl {
    private MemberDAOImpl memberDAO;

    public MemberServiceImpl() {
        System.out.println("MemberServiceImpl 构造器...");
    }

    public void add() {
        System.out.println("MemberServiceImpl add() 方法...");
        memberDAO.add();
    }

    public MemberDAOImpl getMemberDAO() {
        return memberDAO;
    }

    public void setMemberDAO(MemberDAOImpl memberDAO) {
        this.memberDAO = memberDAO;
    }
}
