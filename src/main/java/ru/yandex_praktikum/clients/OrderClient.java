package ru.yandex_praktikum.clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex_praktikum.pojo.CreateOrderRequest;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {
    @Step("Создание заказа")
    public ValidatableResponse create(CreateOrderRequest createOrderRequest) {
        return given()
                .spec(getSpec())
                .body(createOrderRequest)
                .when()
                .post("/api/v1/orders")
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList(){
        return given()
                .spec(getSpec())
                .when()
                .get("/api/v1/orders")
                .then();
    }
}
