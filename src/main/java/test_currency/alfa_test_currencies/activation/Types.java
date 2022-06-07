package test_currency.alfa_test_currencies.activation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Types {
    UP("rich"),
    DOWN("poor"),
    NOCHANGE("equal");

    private String type;
}
