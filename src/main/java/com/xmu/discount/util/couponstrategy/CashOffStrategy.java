package com.xmu.discount.util.couponstrategy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Zhang Bingyuan
 * @version 1.0
 * @date 2019/12/19 10:35
 */
@Getter
@Setter
@ToString
public class CashOffStrategy {
    double threshold;
    double offCash;
}
