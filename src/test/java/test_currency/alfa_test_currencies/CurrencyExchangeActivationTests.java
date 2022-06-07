package test_currency.alfa_test_currencies;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import test_currency.alfa_test_currencies.retrieving.CurrencyExchangeRetrieveAPI;
import test_currency.alfa_test_currencies.models.CurrencyValuesDTO;
import test_currency.alfa_test_currencies.services.CurrencyExchangeRatioService;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
public class CurrencyExchangeActivationTests {

    @MockBean
    CurrencyExchangeRetrieveAPI currencyExchangeRetrieveAPI;

    @Autowired
    private CurrencyExchangeRatioService currencyExchangeRatioService;

    @Value("${Open_Exchange.api}")
    private String apiKey;

    @Value("${Open_Exchange.curency}")
    private String base;

    @Test
    void testGetAllCurrencies() {
        Map<String, BigDecimal> currencies = new HashMap<>();
        currencies.put("RUB", new BigDecimal("52324"));
        currencies.put("RWF", new BigDecimal(10000000));
        currencies.put("SCR", new BigDecimal("78.3231"));

        Mockito.when(currencyExchangeRetrieveAPI.getAllCurrencies(apiKey, base)).thenReturn(new CurrencyValuesDTO(base, currencies));
        Map<String, BigDecimal> rates = currencyExchangeRatioService.GetAllExchangeRates();
        Assert.isTrue(rates.equals(currencies), "Needs non-equilty");
    }

    @Test
    void testGetCurrencyRate() {
        Map<String, BigDecimal> currencies = new HashMap<>();
        currencies.put("RUB", new BigDecimal("2.10"));
        currencies.put("GBP", new BigDecimal("78.07"));
        currencies.put("UKR", new BigDecimal("1"));

        Mockito.when(currencyExchangeRetrieveAPI.getAllCurrencies(apiKey, base)).thenReturn(new CurrencyValuesDTO("USD", currencies));
        BigDecimal rubRate = currencyExchangeRatioService.getCurrencyExchangeRate("RUB");
        BigDecimal rwfRate = currencyExchangeRatioService.getCurrencyExchangeRate("GBP");
        BigDecimal sdgRate = currencyExchangeRatioService.getCurrencyExchangeRate("UKR");

        Assert.isTrue(
                rubRate.compareTo(new BigDecimal("2.10")) == 0,
                "Rate should be equal to mocked rate");
        Assert.isTrue(
                rwfRate.compareTo(new BigDecimal("78.07")) == 0,
                "Rate should be equal to mocked rate");
        Assert.isTrue(
                sdgRate.compareTo(new BigDecimal("1")) == 0,
                "Rate should be equal to mocked rate");
    }

    @Test
    void testGetCurrencyDiff() {
        Map<String, BigDecimal> currentCurrency = new HashMap<>();
        currentCurrency.put("RUB", new BigDecimal("62.00"));
        currentCurrency.put("GBP", new BigDecimal("0.79"));
        currentCurrency.put("UKR", new BigDecimal("29.55"));

        ConcurrentHashMap<String, BigDecimal> oldCurrency = new ConcurrentHashMap<>();
        oldCurrency.put("RUB", new BigDecimal("75.292"));
        oldCurrency.put("GBP", new BigDecimal("0.80"));
        oldCurrency.put("UKR", new BigDecimal("27.898"));

        Field field = ReflectionUtils.findField(currencyExchangeRatioService.getClass(), "rates");
        assert field != null;
        field.setAccessible(true);
        ReflectionUtils.setField(field, currencyExchangeRatioService, oldCurrency);
        Mockito.when(currencyExchangeRetrieveAPI.getAllCurrencies(apiKey, base)).thenReturn(new CurrencyValuesDTO(base, currentCurrency));
        BigDecimal rub = currencyExchangeRatioService.getExchangeRatio("RUB");
        BigDecimal rwf = currencyExchangeRatioService.getExchangeRatio("GBP");
        BigDecimal scr = currencyExchangeRatioService.getExchangeRatio("UKR");

        Assert.isTrue(
                rub.compareTo(new BigDecimal(0)) == 0,
                "Needs equilty");
        Assert.isTrue(
                rwf.compareTo(new BigDecimal(0)) < 0,
                "Needs equilty");
        Assert.isTrue(
                scr.compareTo(new BigDecimal(0)) > 0,
                "Needs equilty");
    }
}
