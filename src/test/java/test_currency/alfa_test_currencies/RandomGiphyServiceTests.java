package test_currency.alfa_test_currencies;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;
import test_currency.alfa_test_currencies.retrieving.GifFileRetrieveAPI;
import test_currency.alfa_test_currencies.models.GifFilesDTO;
import test_currency.alfa_test_currencies.models.GifFileReply;
import test_currency.alfa_test_currencies.services.RandomGiphyService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RandomGiphyServiceTests {

    @MockBean
    GifFileRetrieveAPI gifFileRetrieveAPI;

    @Autowired
    private RandomGiphyService randomGiphyService;

    @Value("${Giphy_gif.api}")
    private String giphyApiKey;

    @Test
    void testGetGifs() {
        List<GifFilesDTO> gifs = new ArrayList<>();
        gifs.add(new GifFilesDTO("1", "http:test.com"));
        Mockito.when(gifFileRetrieveAPI.getGifs(giphyApiKey, "poor", 1)).thenReturn(new GifFileReply(gifs));

        List<GifFilesDTO> myGifs = randomGiphyService.getGifeFile("poor");

        Assert.isTrue(myGifs.equals(gifs), "Needs equilty");
    }

    @Test
    void testGetRandomGif() throws Exception {
        List<GifFilesDTO> gifs = new ArrayList<>();
        gifs.add(new GifFilesDTO("1", "http:test.com"));
        gifs.add(new GifFilesDTO("2", "http:test.com"));
        gifs.add(new GifFilesDTO("3", "http:test.com"));
        Mockito.when(gifFileRetrieveAPI.getGifs(giphyApiKey, "poor", 50)).thenReturn(new GifFileReply(gifs));

        GifFilesDTO myGif = randomGiphyService.getRandomGif("poor");
        Assert.isTrue(myGif.getId().equals("1") ||
                myGif.getId().equals("2") ||
                myGif.getId().equals("3"), "Needs id 1/2/3");
    }
}
