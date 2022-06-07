package test_currency.alfa_test_currencies.services;

import java.math.BigDecimal;
import java.util.Map;

public interface CurrencyExchangeRatioService {

    Map<String, BigDecimal> GetAllExchangeRates();

    Map<String, BigDecimal> getAllYesterdayExchanges();

    BigDecimal getCurrencyExchangeRate(String currency);

    BigDecimal getExchangeRatio(String currency);
}
