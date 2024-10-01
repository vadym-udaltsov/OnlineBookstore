package models;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Builder.Default
    private int id = Faker.instance().number().numberBetween(500, 700);
    @Builder.Default
    private String title = Faker.instance().book().title();
    @Builder.Default
    private String description = Faker.instance().lorem().sentence();
    @Builder.Default
    private int pageCount = Faker.instance().number().numberBetween(100, 500);
    @Builder.Default
    private String excerpt = Faker.instance().lorem().sentence();
    @Builder.Default
    private String publishDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
}