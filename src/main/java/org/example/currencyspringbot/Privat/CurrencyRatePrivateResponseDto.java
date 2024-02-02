package org.example.currencyspringbot.Privat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * конвертує json рядок в об’єкт
 */
@Getter
@Setter
@ToString
public class CurrencyRatePrivateResponseDto {
    private Currency ccy;
    private Currency base_ccy;
    private BigDecimal sale;
    private BigDecimal buy;

}
