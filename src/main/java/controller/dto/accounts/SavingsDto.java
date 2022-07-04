package controller.dto.accounts;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SavingsDto extends AccountDto {

    @NotNull
    private String secretKey;

    @DecimalMax(value = "1000.00", message = "LESS THAN 1000.00")
    @DecimalMin(value = "100.00", message = "MORE THAN 100.00")
    private BigDecimal minimumBalance;

    @DecimalMax(value = "0.5", message = "LESS THAN 0.5")
    @DecimalMin(value = "0" , message = "MORE THAN 0")
    private BigDecimal interestRate;

    public SavingsDto() {
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}