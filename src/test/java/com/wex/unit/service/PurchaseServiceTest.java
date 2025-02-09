package com.wex.unit.service;

import com.wex.repository.PurchaseRepository;
import com.wex.entity.Purchase;
import com.wex.exception.custom.PurchaseNotFoundException;
import com.wex.mapper.PurchaseMapper;
import com.wex.service.CountryCurrencyService;
import com.wex.service.CurrencyService;
import com.wex.service.PurchaseService;
import com.wex.vo.CountryOrCurrencyVO;
import com.wex.vo.request.PurchaseRequestVO;
import com.wex.vo.response.PurchaseResponseVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private CurrencyService currencyService;

    @Mock
    private PurchaseMapper purchaseMapper;

    @Mock
    private CountryCurrencyService countryCurrencyService;

    @InjectMocks
    private PurchaseService purchaseService;

    @Test
    void savePurchaseTest() {
        var purchaseRequestVO = new PurchaseRequestVO("Test Purchase", 100.00);
        var purchase = buildPurchase();
        when(purchaseMapper.toPurchase(purchaseRequestVO)).thenReturn(purchase);
        when(purchaseRepository.save(purchase)).thenReturn(purchase);

        Long savedId = purchaseService.savePurchase(purchaseRequestVO);

        assertNotNull(savedId);
        assertEquals(1L, savedId);
        verify(purchaseMapper, times(1)).toPurchase(purchaseRequestVO);
        verify(purchaseRepository, times(1)).save(purchase);
    }

    @Test
    void getPurchase_withCountryTest() {
        var countryOrCurrencyVO = new CountryOrCurrencyVO("Brazil-Real");
        var purchase = buildPurchase();
        var purchaseResponseVO = buildPurchaseResponseVO();
        var country = "US";

        when(countryCurrencyService.getDisplayCurrencyFromCountry(country)).thenReturn(countryOrCurrencyVO);
        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));
        when(currencyService.getExchangeRate(countryOrCurrencyVO, purchase.getDate())).thenReturn(new BigDecimal("1.20"));

        PurchaseResponseVO result = purchaseService.getPurchase(1L, Locale.US, country, null);

        assertPurchase(result, purchaseResponseVO);
        var countryCaptor = ArgumentCaptor.forClass(String.class);
        verify(countryCurrencyService, times(1)).getDisplayCurrencyFromCountry(countryCaptor.capture());
        assertEquals(country, countryCaptor.getValue());
    }

    @Test
    void getPurchase_withCurrencyTest() {
        var countryOrCurrencyVO = buildCountryOrCurrencyVO();
        var purchase = buildPurchase();
        var purchaseResponseVO = buildPurchaseResponseVO();
        var currency = "USD";

        when(countryCurrencyService.getDisplayCurrencyFromCurrency(currency)).thenReturn(countryOrCurrencyVO);
        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));
        when(currencyService.getExchangeRate(countryOrCurrencyVO, purchase.getDate())).thenReturn(new BigDecimal("1.20"));

        PurchaseResponseVO result = purchaseService.getPurchase(1L, Locale.US, null, currency);

        assertPurchase(result, purchaseResponseVO);
        var currencyCaptor = ArgumentCaptor.forClass(String.class);
        verify(countryCurrencyService, times(1)).getDisplayCurrencyFromCurrency(currencyCaptor.capture());
        assertEquals(currency, currencyCaptor.getValue());
    }

    @Test
    void getPurchase_withLocaleTest() {
        var countryOrCurrencyVO = buildCountryOrCurrencyVO();
        var purchase = buildPurchase();
        var purchaseResponseVO = buildPurchaseResponseVO();
        var locale = Locale.US;

        when(countryCurrencyService.getDisplayCurrencyFromLocale(locale)).thenReturn(countryOrCurrencyVO);
        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));
        when(currencyService.getExchangeRate(countryOrCurrencyVO, purchase.getDate())).thenReturn(new BigDecimal("1.20"));

        PurchaseResponseVO result = purchaseService.getPurchase(1L, locale, null, null);

        assertPurchase(result, purchaseResponseVO);
        var localeCaptor = ArgumentCaptor.forClass(Locale.class);
        verify(countryCurrencyService, times(1)).getDisplayCurrencyFromLocale(localeCaptor.capture());
        assertEquals(locale, localeCaptor.getValue());
    }

    @Test
    void getPurchaseNotFoundTest() {
        when(purchaseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PurchaseNotFoundException.class, () -> purchaseService.getPurchase(1L, Locale.US, null, null));
    }

    private void assertPurchase(PurchaseResponseVO result, PurchaseResponseVO purchaseResponseVO) {
        assertNotNull(result);
        assertEquals(purchaseResponseVO.id(), result.id());
        assertEquals(purchaseResponseVO.description(), result.description());
        assertEquals(purchaseResponseVO.date(), result.date());
        assertEquals(purchaseResponseVO.amount(), result.amount());
        assertEquals(purchaseResponseVO.exchangeRate(), result.exchangeRate());
        assertEquals(purchaseResponseVO.convertedAmount(), result.convertedAmount());
    }

    private PurchaseResponseVO buildPurchaseResponseVO() {
        return new PurchaseResponseVO(1L, "Test Purchase", LocalDate.now(), new BigDecimal("100.00"), new BigDecimal("1.20"), new BigDecimal("120.00"));
    }

    private CountryOrCurrencyVO buildCountryOrCurrencyVO() {
        return new CountryOrCurrencyVO("Brazil-Real");
    }

    private Purchase buildPurchase() {
        return new Purchase(1L, "Test Purchase", new BigDecimal("100.00"), LocalDate.now());
    }
}
