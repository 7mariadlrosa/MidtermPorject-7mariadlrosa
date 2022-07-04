package controller.dto.accounts;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class MoneyDto {

    @DecimalMin("0.00")
    private BigDecimal amount;

    public MoneyDto() {
    }

    public MoneyDto(@DecimalMin("0.00") BigDecimal amount) {
        setAmount(amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
