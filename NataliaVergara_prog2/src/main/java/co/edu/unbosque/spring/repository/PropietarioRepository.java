package co.edu.unbosque.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.spring.model.Propietario;

public interface PropietarioRepository extends CrudRepository<Propietario, Integer> {
	public List<Propietario> findAll();

	public Optional<Propietario> findByCedula(int cedula);
}
