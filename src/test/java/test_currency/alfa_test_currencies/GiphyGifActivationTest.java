package test_currency.alfa_test_currencies;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;
import test_currency.alfa_test_currencies.models.GifFilesDTO;
import test_currency.alfa_test_currencies.services.GiphyGifGetService;
import test_currency.alfa_test_currencies.services.RandomGiphyService;
import test_currency.alfa_test_currencies.services.CurrencyExchangeRatioService;
import test_currency.alfa_test_currencies.activation.Types;

import java.math.BigDecimal;

@SpringBootTest
public class GiphyGifActivationTest {

    @MockBean
    CurrencyExchangeRatioService currencyExchangeRatioService;
    @MockBean
    RandomGiphyService randomGiphyService;

    @Autowired
    private GiphyGifGetService giphyGifGetService;

    @Test
    void getGifTest() throws Exception {
        Mockito.when(currencyExchangeRatioService.getExchangeRatio("RUB")).thenReturn(new BigDecimal("0.0"));
        Mockito.when(currencyExchangeRatioService.getExchangeRatio("UKR")).thenReturn(BigDecimal.valueOf(+999.99));
        Mockito.when(currencyExchangeRatioService.getExchangeRatio("GBP")).thenReturn(BigDecimal.valueOf(-12345.56));
        Mockito.when(randomGiphyService.getRandomGif(Types.UP.getType())).thenReturn(new GifFilesDTO("1", "test"));
        Mockito.when(randomGiphyService.getRandomGif(Types.DOWN.getType())).thenReturn(new GifFilesDTO("2", "test"));
        Mockito.when(randomGiphyService.getRandomGif(Types.NOCHANGE.getType())).thenReturn(new GifFilesDTO("3", "test"));

        GifFilesDTO gif = giphyGifGetService.getGif("RUB");
        Assert.isTrue(gif.getId().equals("3"), "Needs 1 id");
        gif = giphyGifGetService.getGif("UKR");
        Assert.isTrue(gif.getId().equals("1"), "Needs 2 id");
        gif = giphyGifGetService.getGif("GBP");
        Assert.isTrue(gif.getId().equals("2"), "Needs 3 id");
    }

}
