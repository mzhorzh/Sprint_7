package ru.yandex_praktikum.dataprovider;

import org.apache.commons.lang3.RandomStringUtils;
import ru.yandex_praktikum.pojo.CreateCourierRequest;

public class CourierProvider {
    public static CreateCourierRequest getRandomCreateCourierRequest() {
        CreateCourierRequest createCourierRequest = new CreateCourierRequest();
        createCourierRequest.setLogin(RandomStringUtils.randomAlphabetic(8));
        createCourierRequest.setPassword(RandomStringUtils.randomAlphabetic(8));
        createCourierRequest.setFirstName(RandomStringUtils.randomAlphabetic(8));
        return createCourierRequest;
    }
}
