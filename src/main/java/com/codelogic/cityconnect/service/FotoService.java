package com.codelogic.cityconnect.service;

import com.codelogic.cityconnect.model.Foto;
import com.codelogic.cityconnect.repository.FotoRepository;
import org.springframework.stereotype.Service;

@Service
public class FotoService extends GenericService<Foto, Long> {

    public FotoService(FotoRepository fotoRepository) {
        super(fotoRepository);
    }
}
