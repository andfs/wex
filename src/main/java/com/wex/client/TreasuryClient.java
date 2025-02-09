package com.wex.client;

import com.wex.vo.response.ExchangeRateResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TreasuryClient {

    private final RawTreasuryClient rawTreasuryClient;

    public ExchangeRateResponseVO getRateOfExchange(String countryCurrency, LocalDate date) {
        LocalDate referenceDate = date.minusMonths(6);
        String filter = String.format("country_currency_desc:eq:%s,record_date:gte:%s,record_date:lte:%s",
                countryCurrency,
                referenceDate.format(DateTimeFormatter.ISO_DATE),
                date.format(DateTimeFormatter.ISO_DATE));

        return rawTreasuryClient.getRateOfExchange("country_currency_desc,exchange_rate,record_date", filter, "-record_date", 1);
    }
}
