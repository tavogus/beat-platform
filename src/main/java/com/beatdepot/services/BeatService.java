package com.beatdepot.services;

import com.beatdepot.controllers.BeatMakerController;
import com.beatdepot.dto.BeatDTO;
import com.beatdepot.exceptions.ResourceNotFoundException;
import com.beatdepot.models.Beat;
import com.beatdepot.models.User;
import com.beatdepot.repositories.BeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BeatService {

    @Autowired
    private BeatRepository repository;
    @Autowired
    private S3Service s3Service;

    @Autowired
    PagedResourcesAssembler<BeatDTO> assembler;

    public BeatDTO uploadBeat(MultipartFile file) {
        String url = s3Service.uploadFile(file);

        Long userId = this.getAuthUser().getId();

        Beat beat = new Beat();
        beat.setUploadedAt(LocalDateTime.now());
        beat.setUserId(userId);
        beat.setUrl(url);

        var entity = repository.save(beat);
        var dto = this.populateResponse(entity);
        dto.add(linkTo(methodOn(BeatMakerController.class).findBeatById(dto.getId())).withSelfRel());
        return dto;
    }

    public BeatDTO findBeatById(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No movie found for this ID!"));
        var dto = this.populateResponse(entity);
        dto.add(linkTo(methodOn(BeatMakerController.class).findBeatById(id)).withSelfRel());
        return dto;
    }

    private BeatDTO populateResponse(Beat beat) {
        BeatDTO dto = new BeatDTO();
        dto.setId(beat.getId());
        dto.setUrl(beat.getUrl());
        dto.setUploadedAt(beat.getUploadedAt());
        dto.setTags(beat.getTags());
        return dto;
    }

    private User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
