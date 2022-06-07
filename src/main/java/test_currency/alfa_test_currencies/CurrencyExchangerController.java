package test_currency.alfa_test_currencies;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test_currency.alfa_test_currencies.models.GifFilesDTO;
import test_currency.alfa_test_currencies.services.GiphyGifGetService;

@RestController
@RequestMapping("/api")
public class CurrencyExchangerController {

    private final GiphyGifGetService giphyGifGetService;

    @Autowired
    public CurrencyExchangerController(GiphyGifGetService giphyGifGetService) {
        this.giphyGifGetService = giphyGifGetService;
    }


    @GetMapping("/get-currency-gif/{currency}")
    public ResponseEntity<GifFilesDTO> getCurrencyGif(@PathVariable(value = "currency") String currency) throws Exception {
        return new ResponseEntity<>(giphyGifGetService.getGif(currency), HttpStatus.OK);
    }
}

