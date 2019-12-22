package com.xmu.discount.controller;

import com.xmu.discount.domain.PresaleRule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Zhang Bingyuan
 * @version 1.0
 * @date 2019/12/20 22:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PresaleRuleControllerTest {
    @Autowired
    PresaleRuleController presaleRuleController;
    static PresaleRule presaleRule = new PresaleRule();

    @Before
    public void before() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.parse("2019-09-28 17:07:05", df);
        LocalDateTime endTime = LocalDateTime.parse("2020-09-28 17:07:05", df);
        LocalDateTime adEndTime = LocalDateTime.parse("2020-02-28 17:07:05", df);


        presaleRule.setStartTime(startTime);
        presaleRule.setEndTime(endTime);
        presaleRule.setAdEndTime(localDateTime);
        presaleRule.setFinalStartTime(adEndTime);
        presaleRule.setFinalPayment(new BigDecimal(956));
        presaleRule.setDeposit(new BigDecimal(100));
        presaleRule.setGoodsId(324);
        presaleRule.setStatusCode(true);
    }

    @Test
    public void addPresaleRule() {
        for (int i = 0; i < 100; i++) {
            Object object = presaleRuleController.addPresaleRule(presaleRule);
        }
    }

    @Test
    public void getPresaleRuleVoByInf() {
        presaleRuleController.getPresaleRuleVoByInf(324,3,6);
    }

    @Test
    public void updatePresaleRuleById() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.parse("2019-09-28 17:07:05", df);
        LocalDateTime endTime = LocalDateTime.parse("2020-09-28 17:07:05", df);
        LocalDateTime adEndTime = LocalDateTime.parse("2020-02-28 17:07:05", df);

        PresaleRule presaleRule = new PresaleRule();
        presaleRule.setStartTime(startTime);
        presaleRule.setEndTime(endTime);
        presaleRule.setAdEndTime(localDateTime);
        presaleRule.setFinalStartTime(adEndTime);
        presaleRule.setFinalPayment(new BigDecimal(20000));
        presaleRule.setDeposit(new BigDecimal(100));
        presaleRule.setGoodsId(324);
        presaleRule.setStatusCode(true);
        presaleRule.setGmtCreate(startTime);
        presaleRule.setBeDeleted(false);
        Object object = presaleRuleController.updatePresaleRuleById(2, presaleRule);
        System.out.println(object);
    }

    @Test
    public void getPresaleRuleVoById() {
        Object object = presaleRuleController.getPresaleRuleVoById(2);
        System.out.println(object);
    }
}
