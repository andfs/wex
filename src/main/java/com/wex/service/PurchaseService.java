package com.wex.service;

import com.wex.repository.PurchaseRepository;
import com.wex.exception.custom.PurchaseNotFoundException;
import com.wex.mapper.PurchaseMapper;
import com.wex.vo.CountryOrCurrencyVO;
import com.wex.vo.request.PurchaseRequestVO;
import com.wex.vo.response.PurchaseResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final CurrencyService currencyService;
    private final CountryCurrencyService countryCurrencyService;
    private final PurchaseMapper purchaseMapper;

    public Long savePurchase(PurchaseRequestVO purchaseRequestVO) {
        var purchase = purchaseMapper.toPurchase(purchaseRequestVO);
        purchase.setDate(LocalDate.now());
        var saved = purchaseRepository.save(purchase);
        return saved.getId();
    }

    public PurchaseResponseVO getPurchase(Long id, Locale locale, String country, String currency) {
        CountryOrCurrencyVO filter = null;
        if (country != null) {
            filter = countryCurrencyService.getDisplayCurrencyFromCountry(country);
        } else if (currency != null) {
            filter = countryCurrencyService.getDisplayCurrencyFromCurrency(currency);
        } else {
            filter = countryCurrencyService.getDisplayCurrencyFromLocale(locale);
        }
        return getPurchase(id, filter);
    }

    private PurchaseResponseVO getPurchase(Long id, CountryOrCurrencyVO country) {
        var purchase = purchaseRepository.findById(id).orElseThrow(PurchaseNotFoundException::new);
        var rate = currencyService.getExchangeRate(country, purchase.getDate());
        var convertedAmount = purchase.getAmount().multiply(rate).setScale(2, RoundingMode.HALF_UP);

        return new PurchaseResponseVO(purchase.getId(), purchase.getDescription(), purchase.getDate(), purchase.getAmount(), rate, convertedAmount);
    }
}
