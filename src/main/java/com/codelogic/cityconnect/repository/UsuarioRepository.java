package com.codelogic.cityconnect.repository;

import com.codelogic.cityconnect.model.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends GenericRepository<Usuario, Long>{
    Usuario findByEmail(String email);
}
