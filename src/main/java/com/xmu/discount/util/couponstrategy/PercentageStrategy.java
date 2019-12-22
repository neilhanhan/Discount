package com.xmu.discount.util.couponstrategy;

import com.xmu.discount.util.couponstrategy.abstractcouponstrategy.AbstractCouponStrategy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author Zhang Bingyuan
 * @version 1.0
 * @date 2019/12/19 10:35
 */
@Getter
@Setter
@ToString
public final class PercentageStrategy extends AbstractCouponStrategy {
    /**
     *满
     */
    private BigDecimal threshold = BigDecimal.ZERO;
    /**
     * 优惠比例
     */
    private BigDecimal percentage = BigDecimal.valueOf(1.0);

    @Override
    protected boolean isEnough(BigDecimal totalPrice, Integer totalQuantity) {
        return  (totalPrice.compareTo(this.threshold) >=0);
    }

    @Override
    protected BigDecimal getDealPrice(BigDecimal itemPrice, BigDecimal totalPrice) {
        return  this.percentage.multiply(itemPrice).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    protected BigDecimal getError(BigDecimal totalPrice, BigDecimal dealPrice) {
        return dealPrice.subtract(totalPrice.multiply(this.percentage).setScale(2, RoundingMode.HALF_UP));
    }



    public PercentageStrategy() {
    }

    @Override
    public String toString() {
        return "PercentageStrategy{" +
                "threshold=" + threshold +
                ", percentage=" + percentage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        PercentageStrategy that = (PercentageStrategy) o;
        return Objects.equals(threshold, that.threshold) &&
                Objects.equals(percentage, that.percentage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(threshold, percentage);
    }

    public PercentageStrategy(BigDecimal threshold, BigDecimal percentage) {
        this.threshold = threshold;
        this.percentage = percentage;
    }

    public BigDecimal getThreshold() {
        return threshold;
    }

    public void setThreshold(BigDecimal threshold) {
        this.threshold = threshold;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }
}
