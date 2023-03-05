package com.beatdepot.controllers;

import com.beatdepot.dto.BeatDTO;
import com.beatdepot.dto.BeatMakerDTO;
import com.beatdepot.services.BeatMakerService;
import com.beatdepot.services.BeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/beat-maker")
public class BeatMakerController {

    @Autowired
    private BeatMakerService service;
    @Autowired
    private BeatService beatService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<BeatMakerDTO> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<BeatDTO> uploadBeat(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(beatService.uploadBeat(file));
    }
}
