package tests;

import com.github.javafaker.Faker;
import org.testng.annotations.BeforeClass;

public abstract class BaseApiTest<T> {
    protected T client;
    protected Faker faker;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        this.faker = new Faker();
    }
}
