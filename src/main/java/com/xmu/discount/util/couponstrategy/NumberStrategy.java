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
 * @date 2019/12/19 10:33
 */
@Getter
@Setter
@ToString
public final class NumberStrategy extends AbstractCouponStrategy {
    /**
     *满
     */
    private Integer threshold = 0;
    /**
     * 减
     */
    private BigDecimal offCash = BigDecimal.ZERO;


    @Override
    protected boolean isEnough(BigDecimal totalPrice, Integer totalQuantity) {
        return totalQuantity.compareTo(this.threshold) >= 0;
    }

    @Override
    protected BigDecimal getDealPrice(BigDecimal itemPrice, BigDecimal totalPrice) {
        return  itemPrice.subtract(this.offCash.multiply(itemPrice.divide(totalPrice, 3, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));
    }

    @Override
    protected BigDecimal getError(BigDecimal totalPrice, BigDecimal dealPrice) {
        return totalPrice.subtract(dealPrice).subtract(this.offCash);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NumberStrategy that = (NumberStrategy) o;
        return Objects.equals(threshold, that.threshold) &&
                Objects.equals(offCash, that.offCash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(threshold, offCash);
    }
}
