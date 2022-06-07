package test_currency.alfa_test_currencies.services;

import test_currency.alfa_test_currencies.models.GifFilesDTO;

import java.util.List;

public interface RandomGiphyService {
    List<GifFilesDTO> getGifeFile(String search);
    GifFilesDTO getRandomGif(String search) throws Exception;
}
