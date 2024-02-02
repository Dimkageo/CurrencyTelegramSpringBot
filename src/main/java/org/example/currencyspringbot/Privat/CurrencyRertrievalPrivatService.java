package org.example.currencyspringbot.Privat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервіс який звідкилясь дістає курс валют
 */
public class CurrencyRertrievalPrivatService implements CurrencyRertrievalService {

    private static final String URL_0 = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=11";
    private static final String URL_1 = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5";
    private static  String url = null;
    @Override
    public List<CurrencyRateDto> getCurencyRates(int i){
        if (i==1){
            url=URL_1;
        }else {
            url=URL_0;
        }

        try {
            String response = Jsoup.connect(url).ignoreContentType(true).get().body().text();
            List<CurrencyRatePrivateResponseDto> responseDtos = convertResponseToList(response);
//            System.out.println(responseDtos);
            // робимо красиво
            List<CurrencyRateDto> list = new ArrayList<>();
            for (CurrencyRatePrivateResponseDto dto : responseDtos) {
                CurrencyRateDto currencyRateDto = new CurrencyRateDto(dto.getCcy(), dto.getBuy(), dto.getSale());
                list.add(currencyRateDto);
            }
//            System.out.println("---------list = " + list);
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private List<CurrencyRatePrivateResponseDto> convertResponseToList(String response){
        Type type = TypeToken.getParameterized(List.class, CurrencyRatePrivateResponseDto.class).getType();
        Gson gson = new Gson();
        return gson.fromJson(response,type);
    }
}
