package controller.dto.accounts;

import model.AccountHolder;
import com.sun.istack.NotNull;
import javax.persistence.*;
import java.math.BigDecimal;

public class CheckingDto extends AccountDto{

    @NotNull
    private String secretKey;

    private BigDecimal monthlyMaintenanceFee;

    private BigDecimal minimumBalance;

    public CheckingDto() {
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public BigDecimal getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }
}