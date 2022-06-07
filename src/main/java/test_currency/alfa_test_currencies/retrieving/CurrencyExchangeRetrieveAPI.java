package test_currency.alfa_test_currencies.retrieving;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import test_currency.alfa_test_currencies.models.CurrencyValuesDTO;

@FeignClient(name = "${Open_Exchange.name}", url = "${Open_Exchange.link}")
public interface CurrencyExchangeRetrieveAPI {

    @GetMapping("/api/latest.json")
    CurrencyValuesDTO getAllCurrencies(@RequestParam(value = "app_id") String appId,
                                       @RequestParam(value = "base") String base);

    @GetMapping("/api/historical/{date}.json")
    CurrencyValuesDTO getAllCurrenciesByDate(@PathVariable(name = "date") String date,
                                             @RequestParam(value = "app_id") String appId,
                                             @RequestParam(value = "base") String base);
}
