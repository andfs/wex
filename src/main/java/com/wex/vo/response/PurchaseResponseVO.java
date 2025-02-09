package com.wex.vo.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PurchaseResponseVO(Long id, String description, LocalDate date, BigDecimal amount, BigDecimal exchangeRate, BigDecimal convertedAmount) {
}
