package com.beatdepot.controllers;

import com.beatdepot.dto.BeatDTO;
import com.beatdepot.services.BeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/beat")
public class BeatController {

    @Autowired
    private BeatService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<BeatDTO> findBeatById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.findBeatById(id));
    }
}
