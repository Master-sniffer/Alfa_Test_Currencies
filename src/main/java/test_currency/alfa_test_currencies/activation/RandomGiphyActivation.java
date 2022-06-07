package test_currency.alfa_test_currencies.activation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import test_currency.alfa_test_currencies.retrieving.GifFileRetrieveAPI;
import test_currency.alfa_test_currencies.models.GifFilesDTO;
import test_currency.alfa_test_currencies.models.GifFileReply;
import test_currency.alfa_test_currencies.services.RandomGiphyService;

import java.util.List;
import java.util.Random;

@Service
public class RandomGiphyActivation implements RandomGiphyService {

    @Autowired
    private GifFileRetrieveAPI gifFileRetrieveAPI;
    @Value("${Giphy_gif.api}")
    private String giphyApiKey;

    @Override
    public List<GifFilesDTO> getGifeFile(String search) {
        GifFileReply gifs = gifFileRetrieveAPI.getGifs(giphyApiKey, search, 25);
        return gifs.getData();
    }

    @Override
    public GifFilesDTO getRandomGif(String search) throws Exception {

        List<GifFilesDTO> gifs = getGifeFile(search);
        if (CollectionUtils.isEmpty(gifs)) {
            throw new Exception("Gif Service not Available. gifs are empty");
        }

        Random random = new Random();
        return gifs.get(random.nextInt(gifs.size()));

    }
}
