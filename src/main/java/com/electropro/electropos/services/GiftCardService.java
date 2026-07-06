package com.electropro.electropos.services;

import com.electropro.electropos.dto.GiftCardDto;
import com.electropro.electropos.dto.GiftCardResponseDto;
import com.electropro.electropos.mapper.GiftCardMapper;
import com.electropro.electropos.repository.GiftCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GiftCardService {

    private final GiftCardRepository giftCardRepository;
    private final GiftCardMapper giftCardMapper;

    public GiftCardService(GiftCardRepository giftCardRepository, GiftCardMapper giftCardMapper) {
        this.giftCardRepository = giftCardRepository;
        this.giftCardMapper = giftCardMapper;
    }

    public GiftCardResponseDto saveGiftCard(GiftCardDto dto) {
        var giftCard = giftCardMapper.toGiftCard(dto);
        giftCard.setCode("GC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        var savedGiftCard = giftCardRepository.save(giftCard);
        return giftCardMapper.toGiftCardResponseDto(savedGiftCard);
    }

    public List<GiftCardResponseDto> findAllGiftCards() {
        return giftCardRepository.findAll()
                .stream()
                .map(giftCardMapper::toGiftCardResponseDto)
                .collect(Collectors.toList());
    }

    public GiftCardResponseDto findGiftCardByCode(String code) {
        return giftCardRepository.findByCode(code)
                .map(giftCardMapper::toGiftCardResponseDto)
                .orElse(null);
    }

    public GiftCardResponseDto redeemGiftCard(String code) {
        var existing = giftCardRepository.findByCode(code).orElse(null);
        if (existing == null) return null;
        existing.setCurrentBalance(java.math.BigDecimal.ZERO);
        existing.setStatus("REDEEMED");
        var updated = giftCardRepository.save(existing);
        return giftCardMapper.toGiftCardResponseDto(updated);
    }

    public List<GiftCardResponseDto> findAllByStatus(String status) {
        return giftCardRepository.findAllByStatus(status)
                .stream()
                .map(giftCardMapper::toGiftCardResponseDto)
                .collect(Collectors.toList());
    }
}