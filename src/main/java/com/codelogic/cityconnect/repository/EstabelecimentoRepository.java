package com.codelogic.cityconnect.repository;

import com.codelogic.cityconnect.model.Estabelecimento;
import org.springframework.stereotype.Repository;

@Repository
public interface EstabelecimentoRepository extends GenericRepository<Estabelecimento, Long>{

    @Query("SELECT e FROM Estabelecimento e LEFT JOIN FETCH e.avaliacoes WHERE e.id = :id")
    Optional<Estabelecimento> findByIdWithAvaliacoes(@Param("id") Long id);
}
