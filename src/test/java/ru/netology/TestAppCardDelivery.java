package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.nio.channels.Selector;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


class TestAppCardDelivery {

    public String generateDate(int days, String format){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(format));
    }


    @Test
    void shouldAppCardDelivery() {

        String planningDate=generateDate(4,"dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Самара");
        $("[data-test-id='date'] .input__control")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE)
                        .setValue(planningDate);
        $("[data-test-id='name'] .input__control").setValue("Иванов Иван");
        $("[data-test-id='phone'] .input__control").setValue("+79270000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__content")
                .should(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .should(Condition.visible);
    }
}
