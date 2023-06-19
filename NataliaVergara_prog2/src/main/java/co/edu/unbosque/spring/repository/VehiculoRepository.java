package co.edu.unbosque.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.spring.model.Vehiculo;

public interface VehiculoRepository extends CrudRepository<Vehiculo, Integer> {
	public List<Vehiculo> findAll();

	public Optional<Vehiculo> findByCedula(int cedula);

	public Optional<Vehiculo> findByPlaca(String placa);
}
