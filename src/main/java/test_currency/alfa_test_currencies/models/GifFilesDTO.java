package test_currency.alfa_test_currencies.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "id", "url" })
public class GifFilesDTO {
    private String id;
    private String url;
}
