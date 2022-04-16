package org.example.core.main;

import org.example.core.config.AppConfig;
import org.example.core.domain.Student;
import org.example.core.service.StudentService;
import org.example.core.service.TestService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {

        try (
                AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
                ClassPathXmlApplicationContext ctx2 = new ClassPathXmlApplicationContext("conf/services.xml")
            )
        {
            TestService testService = ctx.getBean("testService", TestService.class);
            testService.test();

            TestService testService2 = ctx.getBean("superTestService", TestService.class);
            testService2.test();

            StudentService studentService = ctx2.getBean(StudentService.class);
            StudentService studentService2 = ctx2.getBean("argStudentService", StudentService.class);
            StudentService studentService3 = ctx2.getBean("noArgStudentService", StudentService.class);
            studentService.info();

            Student student = new Student("장수");
            studentService3.setStudent(student);
            System.out.println(studentService.getStudent().getName());
            System.out.println(studentService2.getStudent().getName());
            System.out.println(studentService3.getStudent().getName());
        }
    }
}
