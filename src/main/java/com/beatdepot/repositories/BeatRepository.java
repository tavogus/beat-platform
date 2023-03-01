package com.beatdepot.repositories;

import com.beatdepot.models.Beat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BeatRepository extends JpaRepository<Beat, Long> {
    List<Beat> findByUserId(Long id);
}
