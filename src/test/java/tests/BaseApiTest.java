package tests;

import com.github.javafaker.Faker;

public abstract class BaseApiTest<T> {
    protected T client;
    protected Faker faker;
}
