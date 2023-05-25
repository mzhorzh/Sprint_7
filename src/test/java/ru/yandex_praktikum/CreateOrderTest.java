package ru.yandex_praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex_praktikum.clients.OrderClient;
import ru.yandex_praktikum.pojo.CreateOrderRequest;

import java.time.LocalDate;
import java.util.List;

import static org.apache.http.HttpStatus.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final CreateOrderRequest createOrderRequest;
    private OrderClient orderClient = new OrderClient();

    public CreateOrderTest(CreateOrderRequest createOrderRequest) {
        this.createOrderRequest = createOrderRequest;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderRequest() {
        return new Object[][]{
                {new CreateOrderRequest("Ашот", "Петрович", "Тверская 3", "5", "+79030000000", 4, String.valueOf(LocalDate.now()), "Новый, для развоза шаурмы", List.of("GREY"))},
                {new CreateOrderRequest("Тестотвый", "Заказ", "Варшшавское шоссе 110", "7", "+79261001010", 2, String.valueOf(LocalDate.now()), "Хочу самокат в течении часа", List.of("BLACK"))},
                {new CreateOrderRequest("Роман", "Тестов", "Овражная 2", "2", "+791260010203", 1, String.valueOf(LocalDate.now()), "У подъезда", List.of("GREY", "BLACK"))},
                {new CreateOrderRequest("Тест", "Тестовиич", "Косыгина 6", "16", "+74951112233", 7, String.valueOf(LocalDate.now()), "Не звонить", List.of())},
        };
    }

    @DisplayName("Параметризированный тест кейс проверки создания заказа")
    @Description("Должен вернуться код 201 и тело ответа содержит track")
    @Test
    public void orderShouldBeCreated() {
        orderClient.create(createOrderRequest)
                .statusCode(SC_CREATED)
                .body("track", Matchers.notNullValue());
    }
}
