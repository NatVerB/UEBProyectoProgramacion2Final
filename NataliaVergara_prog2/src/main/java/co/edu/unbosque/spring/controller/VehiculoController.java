package co.edu.unbosque.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.spring.model.Propietario;
import co.edu.unbosque.spring.model.Vehiculo;
import co.edu.unbosque.spring.repository.PropietarioRepository;
import co.edu.unbosque.spring.repository.VehiculoRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class VehiculoController {
	@Autowired
	private VehiculoRepository vr;
	@Autowired
	public PropietarioRepository pr;

	@PostMapping(path = "/agregarVehiculo")
	public ResponseEntity<String> agregarV(@RequestParam Integer cedula, @RequestParam String placa,
			@RequestParam String marca, @RequestParam String modelo) {
		boolean re = false;
		Propietario p = pr.findByCedula(cedula).get();
		try {
			if (p != null) {
				Vehiculo v = new Vehiculo();
				v.setCedula(cedula);
				v.setPlaca(placa);
				v.setMarca(marca);
				v.setModelo(modelo);
				vr.save(v);
				re = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("error en crear vehiculo");
		}
		if (re) {
			return ResponseEntity.status(HttpStatus.CREATED).body("VEHICULO CREADO CODIGO 201\n");

		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("ERROR EN LA CREACION DEL VEHICULO\n");
		}
	}

	@GetMapping("/listarVehiculos")
	public ResponseEntity<Iterable<Vehiculo>> listarTodos() {
		List<Vehiculo> all = (List<Vehiculo>) vr.findAll();
		if (all.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(all);
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(all);
	}

	@GetMapping("/listarVehiculosPorPersona")
	public ResponseEntity<String> listarVehiculosPersona() {
		List<Vehiculo> lista = (List<Vehiculo>) vr.findAll();
		if (lista.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
					.body("NO HA SIDO POSIBLE LISTAR LOS VEHICULOS POR PERSONA");
		} else {
			String cadena = "Lista de Vehiculos por persona\n";
			ArrayList<Integer> cedulas = new ArrayList<>();
			for (int i = 0; i < lista.size(); i++) {
				if (cedulas.isEmpty()) {
					cedulas.add(lista.get(i).getCedula());
				} else {
					if (!cedulas.contains(lista.get(i).getCedula())) {
						cedulas.add(lista.get(i).getCedula());
					}
				}
			}

			for (int i = 0; i < cedulas.size(); i++) {
				for (int j = 0; j < lista.size(); j++) {
					if (cedulas.get(i) == lista.get(j).getCedula()) {
						cadena += lista.get(j).toString() + "\n";
					}
				}
				cadena += "\n\n";

			}
			return ResponseEntity.status(HttpStatus.FOUND).body(cadena);
		}
	}

}
