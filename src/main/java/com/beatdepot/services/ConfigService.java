package com.beatdepot.services;

import com.beatdepot.repositories.ConfigRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    @Autowired
    private ConfigRespository respository;

    public String findValueByKey(String key) {
        return respository.findValueByKey(key);
    }
}
