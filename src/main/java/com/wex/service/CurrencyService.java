package com.wex.service;

import com.wex.client.TreasuryClient;
import com.wex.exception.custom.NoResultException;
import com.wex.vo.CountryOrCurrencyVO;
import com.wex.vo.response.ExchangeRateResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final TreasuryClient treasuryClient;

    @Cacheable(value = "exchangeRate", key = "#countryOrCurrency.value() + '_' + #purchaseDate")
    public BigDecimal getExchangeRate(CountryOrCurrencyVO countryOrCurrency, LocalDate purchaseDate) {
        if(countryOrCurrency == null || !StringUtils.hasText(countryOrCurrency.value()) || purchaseDate == null) {
            throw new NoResultException();
        }
        ExchangeRateResponseVO result = treasuryClient.getRateOfExchange(countryOrCurrency.value(), purchaseDate);

        if(result == null || result.getData().isEmpty() || result.getData().getFirst().getExchangeRate() == null) {
            throw new NoResultException();
        }
        return result.getData().getFirst().getExchangeRate();
    }
}
