package pl.matcodem.accountquery.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.matcodem.accountcommon.dto.BaseResponse;
import pl.matcodem.accountquery.domain.BankAccount;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class AccountLookupResponse extends BaseResponse {
    private List<BankAccount> bankAccounts;

    public AccountLookupResponse(String message) {
        super(message);
    }
}
