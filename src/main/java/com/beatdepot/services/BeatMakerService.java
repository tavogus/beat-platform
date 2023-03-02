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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BeatMakerService {

    private Logger logger = Logger.getLogger(BeatMakerService.class.getName());

    @Autowired
    private UserRepository repository;

    @Autowired
    private BeatRepository beatRepository;

    public BeatMakerDTO findById(Long id) {
        logger.info("Finding one beat maker!");

        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No beat maker found for this ID!"));
        var dto = this.populateResponse(entity);
        dto.add(linkTo(methodOn(BeatMakerController.class).findById(id)).withSelfRel());
        return dto;
    }

    public BeatMakerDTO preSave(BeatMakerInputDTO beatMakerInput) {
        if (beatMakerInput == null) throw new RequiredObjectIsNullException();

        logger.info("Creating new Beat Maker!");

        var existingBeatMaker = repository.findByUsername(beatMakerInput.getUserName());
        if (existingBeatMaker != null) {
            throw new BusinessException("Already Exists a beat maker with this user name!");
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