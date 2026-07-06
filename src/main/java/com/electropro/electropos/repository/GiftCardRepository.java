package com.electropro.electropos.repository;

import com.electropro.electropos.entity.GiftCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public interface GiftCardRepository extends JpaRepository<GiftCard, Integer> {
    Optional<GiftCard> findByCode(String code);

    List<GiftCard> findAllByStatus(String status);


}
