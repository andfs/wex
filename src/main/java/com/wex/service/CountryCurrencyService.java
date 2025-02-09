package com.wex.service;

import com.wex.exception.custom.InvalidCountryException;
import com.wex.exception.custom.InvalidCurrencyException;
import com.wex.exception.custom.InvalidLocaleException;
import com.wex.vo.CountryOrCurrencyVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;

@Service
public class CountryCurrencyService {

    public CountryOrCurrencyVO getDisplayCurrencyFromLocale(Locale locale) {
        if(validateLocale(locale)) {
            var currency = Currency.getInstance(locale).getDisplayName(Locale.US).split(" ");
            var value = locale.getDisplayCountry(Locale.US) + "-" + currency[currency.length - 1];
            return new CountryOrCurrencyVO(value);
        }
        throw new InvalidLocaleException();
    }

    public CountryOrCurrencyVO getDisplayCurrencyFromCountry(String country) {
        var currentLocale = Arrays.stream(Locale.getAvailableLocales())
                .filter(locale -> locale.getDisplayCountry(Locale.US).toLowerCase(Locale.ROOT).equals(country.toLowerCase(Locale.ROOT)))
                .findFirst().orElseThrow(InvalidCountryException::new);
        return getDisplayCurrencyFromLocale(currentLocale);
    }

    public CountryOrCurrencyVO getDisplayCurrencyFromCurrency(String currency) {
        Currency currentCurrency = null;
        try {
            currentCurrency = Currency.getInstance(currency);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidCurrencyException();
        }
        for(Locale locale : Locale.getAvailableLocales()) {
            if(validateLocale(locale)) {
                Currency localeCurrency = Currency.getInstance(locale);
                if(localeCurrency.equals(currentCurrency)) {
                    return getDisplayCurrencyFromLocale(locale);
                }
            }
        }
        throw new InvalidCurrencyException();
    }

    private boolean validateLocale(Locale locale) {
        if (StringUtils.hasText(locale.toString()) && StringUtils.hasText(locale.getISO3Country())) {
            return true;
        }
        return false;
    }
}
