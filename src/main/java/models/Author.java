package models;

import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Author {
    private int id;
    private int idBook;

    @Builder.Default
    private String firstName = Faker.instance().name().firstName();
    @Builder.Default
    private String lastName = Faker.instance().name().lastName();
}
