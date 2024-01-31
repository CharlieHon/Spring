package com.charlie.spring.aop.homework2;

import org.springframework.stereotype.Component;

@Component
public class MyCal implements Cal{
    @Override
    public int cal1(int n) {
        int result = 0;
        for (int i = 1; i <= n; i++) {
            result += i;
        }
        System.out.println("cal1 res~= " + result);
        return result;
    }

    @Override
    public long cal2(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        System.out.println("cal2 res~= " + result);
        return result;
    }
}
