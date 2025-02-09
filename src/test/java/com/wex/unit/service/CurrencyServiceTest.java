package com.wex.unit.service;

import com.wex.client.TreasuryClient;
import com.wex.exception.custom.NoResultException;
import com.wex.service.CurrencyService;
import com.wex.vo.CountryOrCurrencyVO;
import com.wex.vo.response.ExchangeRateResponseVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CurrencyServiceTest {

    @Mock
    private TreasuryClient treasuryClient;

    @InjectMocks
    private CurrencyService currencyService;

    @Test
    void getExchangeRateTest() {
        ExchangeRateResponseVO result = ExchangeRateResponseVO.builder()
                .data(List.of(
                    ExchangeRateResponseVO.ExchangeRateData.builder()
                            .countryCurrencyDesc("desc")
                            .exchangeRate(BigDecimal.valueOf(2.0))
                            .recordDate(LocalDate.now())
                            .build()
                ))
                .build();
        when(treasuryClient.getRateOfExchange(anyString(), any())).thenReturn(result);

        var exchange = currencyService.getExchangeRate(new CountryOrCurrencyVO("value"), LocalDate.now());
        assertEquals(result.getData().getFirst().getExchangeRate(), exchange);
    }

    @Test
    void getExchangeRateTest_noResult() {
        ExchangeRateResponseVO result = ExchangeRateResponseVO.builder()
                .data(List.of())
                .build();
        when(treasuryClient.getRateOfExchange(anyString(), any())).thenReturn(result);

        assertThrows(NoResultException.class, () -> currencyService.getExchangeRate(new CountryOrCurrencyVO("value"), LocalDate.now()));
    }

    @Test
    void getExchangeRateTest_countryOrCurrencyNull() {
        assertThrows(NoResultException.class, () -> currencyService.getExchangeRate(null, LocalDate.now()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "", " "})
    void getExchangeRateTest_countryOrCurrencyInvalid(String value) {
        assertThrows(NoResultException.class, () -> currencyService.getExchangeRate(new CountryOrCurrencyVO(value), LocalDate.now()));
    }

    @Test
    void getExchangeRateTest_dateNull() {
        assertThrows(NoResultException.class, () -> currencyService.getExchangeRate(new CountryOrCurrencyVO("Brazil-Real"), null));
    }
}
