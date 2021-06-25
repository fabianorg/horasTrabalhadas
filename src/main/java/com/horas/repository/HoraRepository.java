package com.horas.repository;

import com.horas.model.Hora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface HoraRepository extends JpaRepository<Hora, Long> {

	List<Hora> findByDataBetweenAndUsuarioId(Date date1, Date date2, Long id);
}
