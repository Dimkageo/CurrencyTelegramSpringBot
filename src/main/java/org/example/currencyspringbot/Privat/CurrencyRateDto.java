package org.example.currencyspringbot.Privat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Універсальний вид для валют будь якого банку
 */
@Data // гетери, сетери, ...
@AllArgsConstructor // конструктор з параметрами
@NoArgsConstructor // конструктор без параметрів
// CurrencyRateDto - Валютна ставка ДТО
public class CurrencyRateDto {
//    private Currency baseCurrency;
    private Currency currency;
    private BigDecimal buyRate;
    private BigDecimal sellRate;
}
