package com.electropro.electropos.controller;

import com.electropro.electropos.dto.GiftCardDto;
import com.electropro.electropos.dto.GiftCardResponseDto;
import com.electropro.electropos.services.GiftCardService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GiftCardController {

    private final GiftCardService giftCardService;

    public GiftCardController(GiftCardService giftCardService) {
        this.giftCardService = giftCardService;
    }

    @PostMapping("/gift-cards")
    public GiftCardResponseDto saveGiftCard(
            @RequestBody GiftCardDto dto
    ) {
        return giftCardService.saveGiftCard(dto);
    }

    @GetMapping("/gift-cards")
    public List<GiftCardResponseDto> findAllGiftCards() {
        return giftCardService.findAllGiftCards();
    }

    @GetMapping("/gift-cards/{gift-card-code}")
    public GiftCardResponseDto findGiftCardByCode(
            @PathVariable("gift-card-code") String code
    ) {
        return giftCardService.findGiftCardByCode(code);
    }

    @PatchMapping("/gift-cards/{gift-card-code}/redeem")
    public GiftCardResponseDto redeemGiftCard(
            @PathVariable("gift-card-code") String code
    ) {
        return giftCardService.redeemGiftCard(code);
    }
}