package controller.dto.accounts;

import javax.validation.constraints.NotNull;

public class StudentCheckingDto extends AccountDto{

    @NotNull
    private String secretKey;

    public StudentCheckingDto() {
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}