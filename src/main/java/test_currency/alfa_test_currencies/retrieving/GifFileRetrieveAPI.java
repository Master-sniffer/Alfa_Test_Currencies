package test_currency.alfa_test_currencies.retrieving;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import test_currency.alfa_test_currencies.models.GifFileReply;

@FeignClient(name = "${Giphy_gif.name}", url = "${Giphy_gif.link}")
public interface GifFileRetrieveAPI {

    @GetMapping("/v1/gifs/search")
    GifFileReply getGifs(@RequestParam(value = "api_key") String apiKey,
                         @RequestParam(value = "q") String q,
                         @RequestParam(value = "limit") int limit);
}
