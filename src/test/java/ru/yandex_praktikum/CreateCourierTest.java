package ru.yandex_praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import ru.yandex_praktikum.clients.CourierClient;
import ru.yandex_praktikum.dataprovider.CourierProvider;
import ru.yandex_praktikum.pojo.CreateCourierRequest;
import ru.yandex_praktikum.pojo.LoginCourierRequest;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {
    private CourierClient courierClient = new CourierClient();
    private Integer id;

    @DisplayName("Тест кейс проверки создания курьера")
    @Description("Должен вернуться код 200 а в теле сообщение ок : true")
    @Test
    public void courierShouldBeCreated() {
        CreateCourierRequest createCourierRequest = CourierProvider.getRandomCreateCourierRequest();

        courierClient.create(createCourierRequest)
                .statusCode(SC_CREATED)
                .body("ok", Matchers.equalTo(true));

        LoginCourierRequest loginCourierRequest = LoginCourierRequest.from(createCourierRequest);

        id = courierClient.login(loginCourierRequest)
                .statusCode(SC_OK)
                .extract().jsonPath().get("id");
    }

    @DisplayName("Тест кейс проверки, что нельзя создать двух одинаковых курьеров")
    @Description("Должен вернуться код ошибки 409 и текст Этот логин уже используется.Попробуйте другой")
    @Test
    public void duplicateCourierShouldNotBeCreated() {
        CreateCourierRequest createCourierRequest = CourierProvider.getRandomCreateCourierRequest();
        courierClient.create(createCourierRequest);
        courierClient.create(createCourierRequest)
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @DisplayName("Тест кейс создания курьера без пароля")
    @Description("Должен вернуться код ошибки 400 и текст Недостаточно данных для создания учетной записи")
    @Test
    public void courierWithoutPasswordShouldNotBeCreated() {
        CreateCourierRequest createCourierRequest = new CreateCourierRequest();
        createCourierRequest.setLogin("AshotP");
        createCourierRequest.setPassword("");
        createCourierRequest.setFirstName("Ashot");
        courierClient.create(createCourierRequest)
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @DisplayName("Тест кейс создания курьера без логина")
    @Description("Должен вернуться код ошибки 400 и текст Недостаточно данных для создания учетной записи")
    @Test
    public void courierWithoutLoginShouldNotBeCreated() {
        CreateCourierRequest createCourierRequest = new CreateCourierRequest();
        createCourierRequest.setLogin("");
        createCourierRequest.setPassword("112233");
        createCourierRequest.setFirstName("Ashot");
        courierClient.create(createCourierRequest)
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown() {
        if (id != null) {
            courierClient.delete(id)
                    .statusCode(SC_OK);
        }
    }
}
