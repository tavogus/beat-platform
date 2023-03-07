package com.beatdepot.controllers;

import com.beatdepot.dto.BeatMakerDTO;
import com.beatdepot.services.BeatMakerService;
import com.beatdepot.services.BeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/beat-maker")
public class BeatMakerController {

    @Autowired
    private BeatMakerService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<BeatMakerDTO> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<BeatMakerDTO>>> findByFilter(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                          @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                                          @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                                          @RequestParam(value = "filter", required = false) String filter) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "userName"));
        return ResponseEntity.ok(service.findByFilter(filter, pageable));
    }
}
