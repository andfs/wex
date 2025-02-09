package com.wex.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
@Builder
public class ExchangeRateResponseVO {
    private final List<ExchangeRateData> data;
    private final Meta meta;

    @Getter
    @ToString
    @Builder
    public static class ExchangeRateData {
        @JsonProperty("country_currency_desc")
        private final String countryCurrencyDesc;

        @JsonProperty("exchange_rate")
        private final BigDecimal exchangeRate;

        @JsonProperty("record_date")
        private final LocalDate recordDate;
    }

    @Getter
    @ToString
    @Builder
    public static class Meta {
        private final int page;

        @JsonProperty("total_pages")
        private final int totalPages;

        @JsonProperty("total_count")
        private final int totalCount;
    }
}
