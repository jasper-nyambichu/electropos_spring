package com.electropro.electropos.repository;

import com.electropro.electropos.entity.BusinessSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends JpaRepository<BusinessSettings, Integer> {
}