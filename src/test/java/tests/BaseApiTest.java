package tests;

import com.github.javafaker.Faker;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public abstract class BaseApiTest<T> {
    protected T client;
    protected Faker faker;

    @BeforeMethod(alwaysRun = true)
    public void setup(Method method) {
        this.faker = new Faker();
    }
}
