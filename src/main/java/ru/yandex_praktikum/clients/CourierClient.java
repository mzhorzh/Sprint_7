package ru.yandex_praktikum.clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex_praktikum.pojo.CreateCourierRequest;
import ru.yandex_praktikum.pojo.LoginCourierRequest;

import static io.restassured.RestAssured.given;

public class CourierClient extends BaseClient {
    @Step("Создание курьера")
    public ValidatableResponse create(CreateCourierRequest createCourierRequest){
        return given()
                .spec(getSpec())
                .body(createCourierRequest)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    @Step("Логин курьера")
    public ValidatableResponse login(LoginCourierRequest loginCourierRequest){
        return given()
                .spec(getSpec())
                .body(loginCourierRequest)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse delete(int id){
        return given()
                .spec(getSpec())
                .pathParam("id", id)
                .when()
                .delete("/api/v1/courier/{id}")
                .then();
    }
}
