package com.wex.unit.client;

import com.wex.client.RawTreasuryClient;
import com.wex.client.TreasuryClient;
import com.wex.vo.response.ExchangeRateResponseVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TreasuryClientTest {

    @Mock
    private RawTreasuryClient rawTreasuryClient;

    @InjectMocks
    private TreasuryClient treasuryClient;

    @Test
    void testGetRateOfExchange() {
        LocalDate date = LocalDate.of(2023, 10, 1);
        String countryCurrency = "Brazil-Real";
        ExchangeRateResponseVO expectedResponse = ExchangeRateResponseVO.builder()
                .data(List.of(ExchangeRateResponseVO.ExchangeRateData.builder()
                        .recordDate(date)
                        .exchangeRate(new BigDecimal("5.20"))
                        .countryCurrencyDesc(countryCurrency)
                        .build()))
                .build();

        when(rawTreasuryClient.getRateOfExchange(
                eq("country_currency_desc,exchange_rate,record_date"),
                eq("country_currency_desc:eq:Brazil-Real,record_date:gte:2023-04-01,record_date:lte:2023-10-01"),
                eq("-record_date"),
                eq(1)
        )).thenReturn(expectedResponse);

        ExchangeRateResponseVO result = treasuryClient.getRateOfExchange(countryCurrency, date);

        assertNotNull(result);
        assertEquals(expectedResponse.getData().size(), result.getData().size());
        assertEquals(expectedResponse.getData().get(0).getCountryCurrencyDesc(), result.getData().get(0).getCountryCurrencyDesc());
        assertEquals(expectedResponse.getData().get(0).getExchangeRate(), result.getData().get(0).getExchangeRate());
        assertEquals(expectedResponse.getData().get(0).getRecordDate(), result.getData().get(0).getRecordDate());

        verify(rawTreasuryClient, times(1)).getRateOfExchange(
                "country_currency_desc,exchange_rate,record_date",
                "country_currency_desc:eq:Brazil-Real,record_date:gte:2023-04-01,record_date:lte:2023-10-01",
                "-record_date",
                1
        );
    }
}
