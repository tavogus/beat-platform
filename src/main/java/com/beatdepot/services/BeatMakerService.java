package com.beatdepot.services;

import com.beatdepot.controllers.BeatMakerController;
import com.beatdepot.dto.BeatDTO;
import com.beatdepot.dto.BeatMakerDTO;
import com.beatdepot.dto.BeatMakerInputDTO;
import com.beatdepot.exceptions.BusinessException;
import com.beatdepot.exceptions.RequiredObjectIsNullException;
import com.beatdepot.exceptions.ResourceNotFoundException;
import com.beatdepot.models.Beat;
import com.beatdepot.models.User;
import com.beatdepot.repositories.BeatRepository;
import com.beatdepot.repositories.UserRepository;
import com.beatdepot.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BeatMakerService {

    private final Logger logger = Logger.getLogger(BeatMakerService.class.getName());

    @Autowired
    private UserRepository repository;

    @Autowired
    private BeatRepository beatRepository;

    @Autowired
    PagedResourcesAssembler<BeatMakerDTO> assembler;

    public BeatMakerDTO findById(Long id) {
        logger.info("Finding one beat maker!");

        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No beat maker found for this ID!"));
        var dto = this.populateResponse(entity);
        dto.add(linkTo(methodOn(BeatMakerController.class).findById(id)).withSelfRel());
        return dto;
    }

    public PagedModel<EntityModel<BeatMakerDTO>> findByFilter(String filter, Pageable pageable) {
        logger.info("Finding all beat makers by filter!");

        var beatMakersPage = repository.findByFilter(filter, pageable);

        var beatMakersDtos = beatMakersPage.map(this::populateResponse);
        beatMakersDtos.map(p -> p.add(linkTo(methodOn(BeatMakerController.class).findById(p.getId())).withSelfRel()));

        Link findByFilterLink = linkTo(
                methodOn(BeatMakerController.class)
                        .findByFilter(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc",
                                filter)
        ).withSelfRel();

        return assembler.toModel(beatMakersDtos, findByFilterLink);
    }

    public BeatMakerDTO preSave(BeatMakerInputDTO beatMakerInput) {
        if (beatMakerInput == null) throw new RequiredObjectIsNullException();

        logger.info("Creating new Beat Maker!");

        var existingUserName = repository.findByUsername(beatMakerInput.getUserName());
        if (existingUserName != null) {
            throw new BusinessException("Already Exists a beat maker with this user name!");
        }

        var existingEmail = repository.findByEmail(beatMakerInput.getEmail());
        if (existingEmail != null) {
            throw new BusinessException("Already Exists a beat maker with this email!");
        }

        var entity = this.save(beatMakerInput);

        var dto = this.populateResponse(entity);
        dto.add(linkTo(methodOn(BeatMakerController.class).findById(dto.getId())).withSelfRel());
        return dto;
    }

    private User save(BeatMakerInputDTO beatMakerInput) {
        User beatMaker = new User();
        beatMaker.setUserName(beatMakerInput.getUserName());
        beatMaker.setEmail(beatMakerInput.getEmail());
        beatMaker.setPassword(PasswordEncoder.encodePassword(beatMakerInput.getPassword()));
        beatMaker.setAccountNonExpired(true);
        beatMaker.setAccountNonLocked(true);
        beatMaker.setCredentialsNonExpired(true);
        beatMaker.setEnabled(true);
        beatMaker.setDescription(beatMakerInput.getDescription());

        return repository.save(beatMaker);
    }

    private BeatMakerDTO populateResponse(User beatMaker) {
        BeatMakerDTO dto = new BeatMakerDTO();
        dto.setId(beatMaker.getId());
        dto.setEmail(beatMaker.getEmail());
        dto.setUserName(beatMaker.getUserName());
        dto.setDescription(beatMaker.getDescription());

        List<Beat> beats = beatRepository.findByUserId(beatMaker.getId());
        if (!beats.isEmpty()) {
            List<BeatDTO> beatListDTO = new ArrayList<>();
            for (Beat beat : beats) {
                BeatDTO beatDTO = new BeatDTO();
                beatDTO.setId(beat.getId());
                beatDTO.setUrl(beat.getUrl());
                beatDTO.setTitle(beat.getTitle());
                beatDTO.setTags(beat.getTags());
                beatDTO.setUploadedAt(beat.getUploadedAt());

                beatListDTO.add(beatDTO);
            }
            dto.setBeats(beatListDTO);
        } else {
            dto.setBeats(new ArrayList<>());
        }

        return dto;
    }
}