package org.example.currencyspringbot.Privat;

import java.util.List;
import java.util.stream.Collectors;

// PrettyRateResponseService - Симпатичный ставка ответ услуга
public class PrettyRateResponseService {

    private static String RESPONSE_TEMPLATE = "cur курс buy / sell . \n";

    public static String formRateRecponse(List<CurrencyRateDto> rates) {

        String res;

        res = rates.stream()
                .map(item -> RESPONSE_TEMPLATE.replace("cur", item.getCurrency().toString())
                        .replace("buy", item.getBuyRate().toString())
                        .replace("sell", item.getSellRate().toString()))
                .collect(Collectors.joining());
//        System.out.println(res);
        return res;
    }
}
