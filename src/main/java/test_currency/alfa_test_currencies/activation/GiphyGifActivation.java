package test_currency.alfa_test_currencies.activation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test_currency.alfa_test_currencies.models.GifFilesDTO;
import test_currency.alfa_test_currencies.services.RandomGiphyService;
import test_currency.alfa_test_currencies.services.CurrencyExchangeRatioService;

import java.math.BigDecimal;

@Service
public class GiphyGifActivation implements test_currency.alfa_test_currencies.services.GiphyGifGetService {

    private final CurrencyExchangeRatioService currencyExchangeRatioService;
    private final RandomGiphyService randomGiphyService;

    @Autowired
    public GiphyGifActivation(CurrencyExchangeRatioService currencyExchangeRatioService, RandomGiphyService randomGiphyService) {
        this.currencyExchangeRatioService = currencyExchangeRatioService;
        this.randomGiphyService = randomGiphyService;
    }

    @Override
    public GifFilesDTO getGif(String currency) throws Exception {

        BigDecimal change = currencyExchangeRatioService.getExchangeRatio(currency.toUpperCase());
        if (change == null) {
            throw new Exception( "Could not get new currency ratio");
        }

        if (change.compareTo(BigDecimal.ZERO) > 0) {
            return (randomGiphyService.getRandomGif(Types.UP.getType()));
        } else if (change.compareTo(BigDecimal.ZERO) < 0) {
            return (randomGiphyService.getRandomGif(Types.DOWN.getType()));
        } else {
            return (randomGiphyService.getRandomGif(Types.NOCHANGE.getType()));
        }

    }
}
