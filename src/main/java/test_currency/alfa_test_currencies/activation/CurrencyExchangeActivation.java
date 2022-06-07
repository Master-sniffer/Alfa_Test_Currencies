package test_currency.alfa_test_currencies.activation;

import feign.RetryableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import test_currency.alfa_test_currencies.retrieving.CurrencyExchangeRetrieveAPI;
import test_currency.alfa_test_currencies.models.CurrencyValuesDTO;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CurrencyExchangeActivation implements test_currency.alfa_test_currencies.services.CurrencyExchangeRatioService {

    private ConcurrentHashMap<String, BigDecimal> rates = new ConcurrentHashMap<>();

    private final CurrencyExchangeRetrieveAPI currencyExchangeRetrieveAPI;

    @Value("${Open_Exchange.api}")
    private String openExchangeKey;

    @Value("${Open_Exchange.curency}")
    private String baseCurrency;

    public CurrencyExchangeActivation(CurrencyExchangeRetrieveAPI currencyExchangeRetrieveAPI) {
        this.currencyExchangeRetrieveAPI = currencyExchangeRetrieveAPI;
    }

    @Override
    public Map<String, BigDecimal> GetAllExchangeRates() {
        CurrencyValuesDTO currencyValuesDto;
        try {
            currencyValuesDto = currencyExchangeRetrieveAPI.getAllCurrencies(openExchangeKey, baseCurrency);
        } catch (RetryableException exc) {
            return new HashMap<>();
        }
        return currencyValuesDto.getRates();
    }

    @Override
    public Map<String, BigDecimal> getAllYesterdayExchanges() {
        CurrencyValuesDTO currencyValuesDto;
        try {
            String date = getYesterdayDate();
            currencyValuesDto = currencyExchangeRetrieveAPI.getAllCurrenciesByDate(date, openExchangeKey, baseCurrency);
        } catch (RetryableException exc) {
            return new HashMap<>();
        }
        return currencyValuesDto.getRates();
    }

    private String getYesterdayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return sdf.format(cal.getTime());
    }

    @Override
    public BigDecimal getCurrencyExchangeRate(String currency) {
        CurrencyValuesDTO currencyValuesDto = currencyExchangeRetrieveAPI.getAllCurrencies(openExchangeKey, baseCurrency);
        return currencyValuesDto.getRates().get(currency);
    }

    @Override
    public BigDecimal getExchangeRatio(String currency) {

        BigDecimal newRate = getCurrencyExchangeRate(currency);
        if (newRate == null) {
            return null;
        }
        BigDecimal oldRate = rates.get(currency);
        if (oldRate == null) {
            return null;
        }
        return newRate.subtract(oldRate);
    }

    @Scheduled(fixedRate = 86400000)
    public void updateCurrencies() {
        Map<String, BigDecimal> resultRates = getAllYesterdayExchanges();
        rates.putAll(resultRates);
    }
}
