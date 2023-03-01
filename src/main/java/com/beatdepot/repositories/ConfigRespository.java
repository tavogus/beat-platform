package com.beatdepot.repositories;

import com.beatdepot.models.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConfigRespository extends JpaRepository<Config, String> {
    @Query("select c.value from Config c where c.key = :key")
    String findValueByKey(@Param("key") String key);
}
