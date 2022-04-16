package org.example.core.service.impl;

import org.example.core.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("testService")
public class TestServiceImpl implements TestService {
    @Qualifier("superTestService")
    @Autowired
    private TestService superTestService;

    public TestServiceImpl() {
        System.out.println("TestServiceImpl 기본 생성자가 자동으로 호출됨");
        System.out.println(this);
    }

    public TestServiceImpl(String name) {
        System.out.println(name + " 생성자가 호출됨!");
        System.out.println(this);
    }

    @Override
    public void test() {
        System.out.println("superTestService: " + superTestService);
        System.out.println("this: " + this);
    }
}
