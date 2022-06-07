package test_currency.alfa_test_currencies.services;

import test_currency.alfa_test_currencies.models.GifFilesDTO;

public interface GiphyGifGetService {
    GifFilesDTO getGif(String currency) throws Exception;
}
