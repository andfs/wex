package com.wex.client;

import com.wex.vo.response.ExchangeRateResponseVO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/v1/accounting/od/rates_of_exchange")
public interface RawTreasuryClient {

    @GetExchange
    ExchangeRateResponseVO getRateOfExchange(@RequestParam(name = "fields") String fields, @RequestParam(name = "filter") String filter, @RequestParam(name = "sort") String sort, @RequestParam(name = "page[size]") int pageSize);
}
