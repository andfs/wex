package com.wex.controller;

import com.wex.service.PurchaseService;
import com.wex.vo.request.PurchaseRequestVO;
import com.wex.vo.response.PurchaseResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Locale;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
@Tag(name = "purchase", description = "The Purchase api.")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @Operation(operationId = "getPurchase", summary = "Get a transaction purchase by Id with the amount converted to a specified currency. For that its possible to pass the country of the currency or the currency code or if this fields are blank, the amount will be converted to the currency of the request's locale.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the purchase and it's amount conversion", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PurchaseResponseVO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid currency or country", content = @Content),
            @ApiResponse(responseCode = "404", description = "Purchase not found", content = @Content),
            @ApiResponse(responseCode = "424", description = "It was impossible to convert the purchase amount", content = @Content)})
    @GetMapping("/{id}")
    public PurchaseResponseVO getPurchase(@PathVariable Long id, Locale locale, @RequestParam(value = "country", required = false) String country, @RequestParam(value = "currency", required = false) String currency) {
        return purchaseService.getPurchase(id, locale, country, currency);
    }

    @Operation(operationId = "savePurchase", summary = "Save a new purchase.")
    @PostMapping
    public ResponseEntity<Void> savePurchase(@RequestBody PurchaseRequestVO purchaseRequestVO) {
        return ResponseEntity.created(URI.create(purchaseService.savePurchase(purchaseRequestVO).toString())).build();
    }
}
