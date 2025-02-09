package com.wex.vo.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record PurchaseRequestVO(@NotEmpty String description, @DecimalMin(value = "1.0") double amount) {
}
