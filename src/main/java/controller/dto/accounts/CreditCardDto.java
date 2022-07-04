package controller.dto.accounts;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class CreditCardDto extends AccountDto {

    @DecimalMin(value = "100.00", message = "MORE THAN 100.00")
    private BigDecimal creditLimit;

    @DecimalMax(value = "0.2", message = "LESS THAN 0.2")
    @DecimalMin(value = "0.1", message = "MORE THAN 0.1")
    private BigDecimal interestRate;

    public CreditCardDto() {
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
