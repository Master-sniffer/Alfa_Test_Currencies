package test_currency.alfa_test_currencies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients("test_currency.alfa_test_currencies.retrieving")
@SpringBootApplication
@EnableScheduling

public class AlfaTestCurrenciesApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlfaTestCurrenciesApplication.class, args);
    }

}
