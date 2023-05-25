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

public class LoginCourierTest {
    private CourierClient courierClient = new CourierClient();
    private Integer id;

    @DisplayName("Тест кейс проверки, что курьер может залогиниться")
    @Description("Должен вернуться код 200 и должен вернуться id курьера")
    @Test
    public void courierShouldBeLogin() {
        CreateCourierRequest createCourierRequest = CourierProvider.getRandomCreateCourierRequest();

        courierClient.create(createCourierRequest);

        LoginCourierRequest loginCourierRequest = LoginCourierRequest.from(createCourierRequest);

        id = courierClient.login(loginCourierRequest)
                .statusCode(SC_OK)
                .extract().jsonPath().get("id");
    }

    @DisplayName("Тест кейс проверки что курьер не может залогинится с невалидным паролем")
    @Description("Должен вернуться код ошибки 404 и текст Учетная запись не найдена")
    @Test
    public void courierShouldNotBeLoginWithInvalidPassword() {
        CreateCourierRequest createCourierRequest = new CourierProvider().getRandomCreateCourierRequest();
        LoginCourierRequest loginCourierRequest = LoginCourierRequest.from(createCourierRequest);
        courierClient.create(createCourierRequest);
        createCourierRequest.setPassword("wrong_password");
        courierClient.login(LoginCourierRequest.from(createCourierRequest))
                .statusCode(SC_NOT_FOUND)
                .body("message", Matchers.equalTo("Учетная запись не найдена"));
    }

    @DisplayName("Тест кейс проверки что курьер не может залогинится с невалидным логином")
    @Description("Должен вернуться код ошибки 404 и текст Учетная запись не найдена")
    @Test
    public void courierShouldNotBeLoginWithInvalidLogin() {
        CreateCourierRequest createCourierRequest = new CourierProvider().getRandomCreateCourierRequest();
        LoginCourierRequest loginCourierRequest = LoginCourierRequest.from(createCourierRequest);
        courierClient.create(createCourierRequest);
        createCourierRequest.setLogin("wrong_login");
        courierClient.login(LoginCourierRequest.from(createCourierRequest))
                .statusCode(SC_NOT_FOUND)
                .body("message", Matchers.equalTo("Учетная запись не найдена"));
    }

    @DisplayName("Тест кейс проверки что запрос возвращает ошибку если логинится без логина")
    @Description("Должен вернуться код ошибки 400 и текст Недостаточно данных для входа")
    @Test
    public void courierShouldNotBeLoginWithNoLogin() {
        CreateCourierRequest createCourierRequest = new CourierProvider().getRandomCreateCourierRequest();
        LoginCourierRequest loginCourierRequest = LoginCourierRequest.from(createCourierRequest);
        courierClient.create(createCourierRequest);
        createCourierRequest.setLogin("");
        courierClient.login(LoginCourierRequest.from(createCourierRequest))
                .statusCode(SC_BAD_REQUEST)
                .body("message", Matchers.equalTo("Недостаточно данных для входа"));
    }

    @DisplayName("Тест кейс проверки что запрос возвращает ошибку если логинится без пароля")
    @Description("Должен вернуться код ошибки 400 и текст Недостаточно данных для входа")
    @Test
    public void courierShouldNotBeLoginWithNoPassword() {
        CreateCourierRequest createCourierRequest = new CourierProvider().getRandomCreateCourierRequest();
        LoginCourierRequest loginCourierRequest = LoginCourierRequest.from(createCourierRequest);
        courierClient.create(createCourierRequest);
        createCourierRequest.setPassword("");
        courierClient.login(LoginCourierRequest.from(createCourierRequest))
                .statusCode(SC_BAD_REQUEST)
                .body("message", Matchers.equalTo("Недостаточно данных для входа"));
    }

    @After
    public void tearDown() {
        if (id != null) {
            courierClient.delete(id)
                    .statusCode(SC_OK);
        }
    }
}
