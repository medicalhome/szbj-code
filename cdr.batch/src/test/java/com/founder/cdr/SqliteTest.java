package com.founder.cdr;

import com.founder.cdr.entity.TestSqlite;
import com.founder.cdr.service.TestSqliteService;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.util.concurrent.CountDownLatch;

/**
 * 类描述
 * Created by masong1 on 2016/9/8.
 */

public class SqliteTest {
    public static void main(String[] args) throws InterruptedException {
//        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:beanRefContext.xml");
        GenericApplicationContext ctx = new GenericApplicationContext();
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
        xmlReader.loadBeanDefinitions(new ClassPathResource("beanRefContext.xml"));
        ctx.refresh();
        TestSqliteService testSqliteService = (TestSqliteService)ctx.getBean(TestSqliteService.class);
        TestSqlite testSqlite = new TestSqlite();
        testSqlite.setName("test");
        testSqlite.setAge(25);
        testSqlite.setAddress("beijing");

        long s = System.currentTimeMillis();
        int threadNum = 3;
        CountDownLatch threadSignal = new CountDownLatch(threadNum);
        for(int i=0;i<3;i++){
            new Thread( new SqlThread2(threadSignal,testSqliteService,i+"_")).start();
        }
        threadSignal.await();//等待所有子线程执行完
        System.out.println(Thread.currentThread().getName() + "结束."+(System.currentTimeMillis()-s));

    }
}
class SqlThread2 implements Runnable{
    private CountDownLatch threadsSignal;
    private TestSqliteService testSqliteService;
    private String name;

    public SqlThread2(CountDownLatch threadsSignal,TestSqliteService testSqliteService, String name) {
        this.threadsSignal = threadsSignal;
        this.testSqliteService = testSqliteService;
        this.name = name;
    }

    @Override
    public void run() {
        long s = System.currentTimeMillis();
        for(int i=0;i<100000;i++){
            testSqliteService.insert(name+i,1,"beijing");
        }
        System.out.println("total/thread:"+(System.currentTimeMillis()-s));
    }
}
