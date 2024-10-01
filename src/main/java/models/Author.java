package models;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Builder.Default
    private int id = Faker.instance().number().numberBetween(500, 700);
    @Builder.Default
    private int idBook = Faker.instance().number().numberBetween(1, 10);
    @Builder.Default
    private String firstName = Faker.instance().name().firstName();
    @Builder.Default
    private String lastName = Faker.instance().name().lastName();
}
