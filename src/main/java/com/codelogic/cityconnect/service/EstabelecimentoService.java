package com.codelogic.cityconnect.service;

import com.codelogic.cityconnect.dto.EstabelecimentoRequestDto;
import com.codelogic.cityconnect.dto.EstabelecimentoResponseDto;
import com.codelogic.cityconnect.dto.mapper.EstabelecimentoMapper;
import com.codelogic.cityconnect.exception.ResourceNotFoundException;
import com.codelogic.cityconnect.model.Endereco;
import com.codelogic.cityconnect.model.Estabelecimento;
import com.codelogic.cityconnect.model.Foto;
import com.codelogic.cityconnect.repository.EnderecoRepository;
import com.codelogic.cityconnect.repository.EstabelecimentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EstabelecimentoService extends GenericService<Estabelecimento, Long> {

    private final EstabelecimentoMapper estabelecimentoMapper;

    private final EnderecoRepository enderecoRepository;

    private final EstabelecimentoRepository estabelecimentoRepository;

    private final FotoService fotoService;


    public EstabelecimentoService(EstabelecimentoRepository estabelecimentoRepository,
                                  EnderecoRepository enderecoRepository,
                                  EstabelecimentoMapper estabelecimentoMapper,
                                  FotoService fotoService) {
        super(estabelecimentoRepository);
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.estabelecimentoMapper = estabelecimentoMapper;
        this.enderecoRepository = enderecoRepository;
        this.fotoService = fotoService;
    }

    public EstabelecimentoResponseDto salvar(EstabelecimentoRequestDto estabelecimentoRequestDto) throws IOException {

        Estabelecimento estabelecimento = estabelecimentoMapper.estabelecimentoDtoToEstabelecimento(estabelecimentoRequestDto);

        if (estabelecimentoRequestDto.getFotoPerfil() != null
                && !estabelecimentoRequestDto.getFotoPerfil().isEmpty()
                && estabelecimentoRequestDto.getFotoPerfil().getBytes().length > 0){
            Foto foto = new Foto(estabelecimentoRequestDto.getFotoPerfil().getBytes());
            fotoService.salvar(foto);
            estabelecimento.setFotoPerfil(foto);
        }
        List<Foto> fotosAmbienteBase64 = new ArrayList<>();

        if (estabelecimentoRequestDto.getFotosAmbiente() != null
                && !estabelecimentoRequestDto.getFotosAmbiente().isEmpty()){

            for (MultipartFile fotoAmbiente : estabelecimentoRequestDto.getFotosAmbiente()) {
                Foto foto = new Foto(fotoAmbiente.getBytes());
                fotoService.salvar(foto);
                fotosAmbienteBase64.add(foto);
            }
            estabelecimento.setFotosAmbiente(fotosAmbienteBase64);
        }

        Endereco endereco = estabelecimento.getEndereco();
        enderecoRepository.save(endereco);
        estabelecimento.setEndereco(endereco);

        Estabelecimento estabelecimentoSalvo = repository.save(estabelecimento);
        return estabelecimentoMapper.estabelecimentoToestabelecimentoResponseDto(estabelecimentoSalvo);
    }

    public void adicionarFotoPerfil(Long idEstabelecimento, Long idFoto){
        Estabelecimento estabelecimento = buscarPorId(idEstabelecimento).orElseThrow(()-> new ResourceNotFoundException("Estabelecimento não encotrado."));
        estabelecimento.getFotoPerfil().setId(idFoto);
        repository.save(estabelecimento);
    }

    @Override
    public Optional<T> buscarPorId(ID id) {
        estabelecimentoRepository.findByIdWithAvaliacoes(id);
    }

    public void adicionarFotoAmbiente(Long idEstabelecimento, MultipartFile fotoAmbiente) throws IOException {
        Estabelecimento estabelecimento = buscarPorId(idEstabelecimento).orElseThrow(()-> new ResourceNotFoundException("Estabelecimento não encotrado."));
        List<Foto> fotosAmbiente = estabelecimento.getFotosAmbiente();
        if (fotosAmbiente == null){
            fotosAmbiente = new ArrayList<>();
        }
        Foto foto = new Foto(fotoAmbiente.getBytes());
        fotoService.salvar(foto);
        fotosAmbiente.add(foto);
        estabelecimento.setFotosAmbiente(fotosAmbiente);
        repository.save(estabelecimento);
    }

    public List<Foto> getFotosAmbiente(Long estabelecimentoId) {
        Estabelecimento estabelecimento = repository.findById(estabelecimentoId).orElseThrow(()-> new ResourceNotFoundException("Estabelecimento não encotrado."));
        return estabelecimento.getFotosAmbiente();
    }
}
