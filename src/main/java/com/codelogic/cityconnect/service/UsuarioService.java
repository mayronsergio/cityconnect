package com.codelogic.cityconnect.service;

import com.codelogic.cityconnect.exception.BusinessExcetion;
import com.codelogic.cityconnect.model.Usuario;
import com.codelogic.cityconnect.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends GenericService<Usuario, Long> {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        super(usuarioRepository);
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario salvar(Usuario entidade) {
        Usuario usuario = usuarioRepository.findByEmail(entidade.getEmail());
        if (usuario != null){
            throw new BusinessExcetion("Este email já está cadastrado");
        }
        entidade.setPassword(new BCryptPasswordEncoder().encode(entidade.getPassword()));
        return super.salvar(entidade);
    }
}
