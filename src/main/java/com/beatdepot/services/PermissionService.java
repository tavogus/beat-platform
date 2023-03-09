package com.beatdepot.services;

import com.beatdepot.exceptions.ResourceNotFoundException;
import com.beatdepot.models.Permission;
import com.beatdepot.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository repository;

    public Permission findById(Long idPermission) {
        return repository.findById(idPermission).orElseThrow(() -> new ResourceNotFoundException("No permission found with this ID!"));
    }
}
