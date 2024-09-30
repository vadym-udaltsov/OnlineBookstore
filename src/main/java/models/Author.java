package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {
    private int id;
    private int idBook;

    @Builder.Default
    private String firstName = Faker.instance().name().firstName();
    @Builder.Default
    private String lastName =  Faker.instance().name().lastName();
}
