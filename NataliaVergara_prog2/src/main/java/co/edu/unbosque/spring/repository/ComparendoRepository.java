package co.edu.unbosque.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.spring.model.Comparendo;

public interface ComparendoRepository extends CrudRepository<Comparendo, Integer> {
	public List<Comparendo> findAll();

	public Optional<Comparendo> findByCedula(int cedula);

	public List<Comparendo> findByPlaca(String placa);
}
