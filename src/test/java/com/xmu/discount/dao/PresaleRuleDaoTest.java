package com.xmu.discount.dao;

import com.xmu.discount.domain.PresaleRule;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Zhang Bingyuan
 * @version 1.0
 * @date 2019/12/18 19:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class PresaleRuleDaoTest {
    @Autowired
    PresaleRuleDao presaleRuleDao;

    static PresaleRule presaleRule;

    @Before
    public void before()
    {

    }
}
