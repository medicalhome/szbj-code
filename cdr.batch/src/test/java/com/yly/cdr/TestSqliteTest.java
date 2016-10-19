package com.yly.cdr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yly.cdr.entity.TestSqlite;
import com.yly.cdr.service.TestSqliteService;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 类描述
 * Created by  on 2016/9/6.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beanRefContext.xml")
public class TestSqliteTest {
    @Autowired
    private TestSqliteService testSqliteService;

    @Test
    public void test() throws InterruptedException {
        TestSqlite testSqlite = new TestSqlite();
        testSqlite.setName("test");
        testSqlite.setAge(25);
        testSqlite.setAddress("beijing");

        long s = System.currentTimeMillis();
        int threadNum = 3;
        CountDownLatch threadSignal = new CountDownLatch(threadNum);
        for(int i=0;i<threadNum;i++){
            new Thread( new SqlThread(threadSignal,testSqliteService,i+"_")).start();
        }
        threadSignal.await();//等待所有子线程执行完
        System.out.println(Thread.currentThread().getName() + "结束."+(System.currentTimeMillis()-s));

        /*testSqliteService.insert("test",25,"beijing");
        List<TestSqlite> list = testSqliteService.getTestSqliteByAge(25);
        for (TestSqlite t : list){
            System.out.println("id="+t.getId()+"|name="+t.getName()+"|age="+t.getAge()+"|address="+t.getAddress());
        }*/
    }
}
class SqlThread implements Runnable{
    private CountDownLatch threadsSignal;
    private TestSqliteService testSqliteService;
    private String name;

    public SqlThread(CountDownLatch threadsSignal,TestSqliteService testSqliteService, String name) {
        this.threadsSignal = threadsSignal;
        this.testSqliteService = testSqliteService;
        this.name = name;
    }

    @Override
    public void run() {
        long s = System.currentTimeMillis();
        for(int i=0;i<500;i++){
            testSqliteService.insert(name+i,1,"beijing");
//            List<TestSqlite> list = testSqliteService.getTestSqliteByAge(1);
//            System.out.println("size="+list.size());
        }
        threadsSignal.countDown();
        System.out.println("total/thread:"+(System.currentTimeMillis()-s));
    }
}