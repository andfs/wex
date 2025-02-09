package com.wex.unit.service;

import com.wex.exception.custom.InvalidCountryException;
import com.wex.exception.custom.InvalidCurrencyException;
import com.wex.exception.custom.InvalidLocaleException;
import com.wex.service.CountryCurrencyService;
import com.wex.vo.CountryOrCurrencyVO;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CountryCurrencyServiceTest {
    private CountryCurrencyService countryCurrencyService = new CountryCurrencyService();

    private static final String CURRENCY_RESULT = "Brazil-Real";

    @Test
    void getDisplayCurrencyFromLocale() {
        var locale = Locale.of("pt", "BR");
        CountryOrCurrencyVO result = countryCurrencyService.getDisplayCurrencyFromLocale(locale);
        assertEquals(CURRENCY_RESULT, result.value());
    }

    @Test
    void getDisplayCurrencyFromLocale_invalidLocale() {
        var locale = Locale.of("pt");
        assertThrows(InvalidLocaleException.class, () -> countryCurrencyService.getDisplayCurrencyFromLocale(locale));
    }

    @Test
    void getDisplayCurrencyFromCountry() {
        CountryOrCurrencyVO result = countryCurrencyService.getDisplayCurrencyFromCountry("Brazil");
        assertEquals(CURRENCY_RESULT, result.value());
    }

    @Test
    void getDisplayCurrencyFromCountry_invalidCountry() {
        assertThrows(InvalidCountryException.class, () -> countryCurrencyService.getDisplayCurrencyFromCountry("Brasil"));
    }

    @Test
    void getDisplayCurrencyFromCurrency() {
        CountryOrCurrencyVO result = countryCurrencyService.getDisplayCurrencyFromCurrency("BRL");
        assertEquals(CURRENCY_RESULT, result.value());
    }

    @Test
    void getDisplayCurrencyFromCurrency_invalidCurrency() {
        assertThrows(InvalidCurrencyException.class, () -> countryCurrencyService.getDisplayCurrencyFromCurrency("BR"));
    }
}
