package test_currency.alfa_test_currencies.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "base", "rates" })
public class CurrencyValuesDTO {
    private String base;
    private Map<String, BigDecimal> rates;
}
