package ru.yandex_praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.Test;
import ru.yandex_praktikum.clients.OrderClient;

public class ListOrdersTest {
    private OrderClient orderClient = new OrderClient();

    @DisplayName("Тест кейс проверки получения списка заказов")
    @Description("Должен вернуться код 200 и в теле ответа должен вернуться список заказов")
    @Test
    public void getOrderListShouldBeVisible(){
        orderClient.getOrderList()
                .statusCode(200)
                .body("orders", Matchers.notNullValue());
    }
}
