package com.horas.service;

import com.horas.model.Hora;
import com.horas.repository.HoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HoraService {

	@Autowired
	private HoraRepository horaRepository;
	
	public Hora salvar(Hora hora) {
		return horaRepository.save(hora);
	}
	
	public Optional<Hora> buscar(Long id) {
		return horaRepository.findById(id);
	}
	
	public void excluir(Long id) {
		horaRepository.deleteById(id);
	}
	
	public List<Hora> listar(){
		return horaRepository.findAll();
	}
	
	public List<Hora> listarPorPeriodo(Date date1, Date date2, Long id) {
		return horaRepository.findByDataBetweenAndUsuarioId(date1, date2, id);
	}
}

