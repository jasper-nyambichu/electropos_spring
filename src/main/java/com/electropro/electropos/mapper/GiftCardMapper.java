package com.electropro.electropos.mapper;

import com.electropro.electropos.dto.GiftCardDto;
import com.electropro.electropos.dto.GiftCardResponseDto;
import com.electropro.electropos.entity.GiftCard;
import org.springframework.stereotype.Service;

@Service
public class GiftCardMapper {

    public GiftCard toGiftCard(GiftCardDto dto) {
        var giftCard = new GiftCard();
        giftCard.setInitialValue(dto.initialValue());
        giftCard.setCurrentBalance(dto.initialValue());
        giftCard.setExpiryDate(dto.expiryDate());
        giftCard.setStatus("ACTIVE");
        return giftCard;
    }

    public GiftCardResponseDto toGiftCardResponseDto(GiftCard giftCard) {
        return new GiftCardResponseDto(
                giftCard.getId(),
                giftCard.getCode(),
                giftCard.getInitialValue(),
                giftCard.getCurrentBalance(),
                giftCard.getExpiryDate(),
                giftCard.getStatus()
        );
    }
}