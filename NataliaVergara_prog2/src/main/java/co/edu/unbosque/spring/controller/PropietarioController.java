package co.edu.unbosque.spring.controller;

import java.sql.Date;
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
import co.edu.unbosque.spring.repository.PropietarioRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PropietarioController {
	@Autowired

	private PropietarioRepository propdao;

	@PostMapping(path = "/agregarPropietario")
	public ResponseEntity<String> add(@RequestParam String nombre, @RequestParam Integer cedula,
			@RequestParam Date fecha) {
		boolean re = false;
		try {
			if (propdao.findByCedula(cedula).isEmpty()) {
				Propietario prop = new Propietario();
				prop.setNombre(nombre);
				prop.setCedula(cedula);
				prop.setFechanac(fecha);
				propdao.save(prop);
				re = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("error en crear propietario");
		}
		if (re) {
			return ResponseEntity.status(HttpStatus.CREATED).body("PROPIETARIO CREADO (CODE 201)\n");
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("PROPIETARIO NO CREADO\n");
		}
	}

	@GetMapping("/listarPropietarios")
	public ResponseEntity<Iterable<Propietario>> listarTodos() {
		List<Propietario> all = (List<Propietario>) propdao.findAll();
		if (all.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(all);
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(all);
	}

}
