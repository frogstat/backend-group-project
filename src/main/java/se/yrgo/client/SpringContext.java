package se.yrgo.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContext {

    private static final ApplicationContext context =
            new ClassPathXmlApplicationContext("application.xml");

    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }
}