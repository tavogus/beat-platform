package com.beatdepot.controllers;

import com.beatdepot.dto.BeatDTO;
import com.beatdepot.services.BeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/beat")
public class BeatController {

    @Autowired
    private BeatService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<BeatDTO> findBeatById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.findBeatById(id));
    }

    @PostMapping
    public ResponseEntity<BeatDTO> addBeatInformations(@RequestBody BeatDTO beatDTO) {
        return ResponseEntity.ok(service.addBeatInformations(beatDTO));
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<BeatDTO> uploadBeat(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(service.uploadBeat(file));
    }
}
