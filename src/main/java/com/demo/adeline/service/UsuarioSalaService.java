package com.demo.adeline.service;



import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.adeline.model.UsuarioSala;
import com.demo.adeline.model.UsuarioSalaDTO;
import com.demo.adeline.repository.UsuarioSalaRepository;




@Service
public class UsuarioSalaService {

    private final UsuarioSalaRepository repository;

    public UsuarioSalaService(UsuarioSalaRepository repository) {
        this.repository = repository;
    }

    public void entrar(UsuarioSalaDTO dto){

        if(repository.findBySalaIdAndUsuarioId(dto.getSalaId(),dto.getUsuarioId()).isPresent()){
            return;
        }
 
        UsuarioSala usuario=new UsuarioSala();

        usuario.setSalaId(dto.getSalaId());
        usuario.setUsuarioId(dto.getUsuarioId());
        usuario.setUsuarioNombre(dto.getUsuarioNombre());
        usuario.setUsuarioFoto(dto.getUsuarioFoto());
        usuario.setFechaIngreso(LocalDateTime.now());

        repository.save(usuario);

    }

    public void salir(Long salaId,Long usuarioId){

        repository.deleteBySalaIdAndUsuarioId(salaId,usuarioId);

    }

    public List<UsuarioSala> listar(Long salaId){

        return repository.findBySalaId(salaId);

    }

}