package co.edu.unbosque.spring.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.spring.model.Comparendo;
import co.edu.unbosque.spring.repository.ComparendoRepository;
import co.edu.unbosque.spring.repository.VehiculoRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ComparendoController {
	@Autowired
	private ComparendoRepository cr;
	@Autowired
	private VehiculoRepository vr;

	@PostMapping("/agregarComparendo")
	public ResponseEntity<String> agregar(@RequestParam Integer cedula, @RequestParam String placa,
			@RequestParam String tipo) {
		boolean re = false;
		try {
			if (vr.findByPlaca(placa).get() != null) {
				Comparendo c = new Comparendo();
				c.setCedula(cedula);
				c.setPlaca(placa);
				c.setTipo(tipo);
				int valor = precio(tipo);

				c.setValor(valor);
				c.setPagado(false);
				cr.save(c);
				re = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("error en crear comparendo");
		}
		if (re) {
			return ResponseEntity.status(HttpStatus.CREATED).body("COMPARENDO CREADO CODIGO 201\n");
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("COMPARENDO NO CREADO\n");
		}
	}

	@GetMapping("/listarComparendos")
	public ResponseEntity<Iterable<Comparendo>> listarTodos() {
		List<Comparendo> all = (List<Comparendo>) cr.findAll();
		if (all.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(all);
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(all);
	}

	@PutMapping("/modificarComparendo")
	public ResponseEntity<String> actualizar(@RequestParam String placa, @RequestParam String tipo,
			@RequestParam boolean pagado) {
		List<Comparendo> comp = cr.findByPlaca(placa);
		boolean re = false;
		try {
			if (comp.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("ERROR");
			} else {
				for (int i = 0; i < comp.size(); i++) {
					if (comp.get(i).getTipo().equalsIgnoreCase(tipo)) {
						comp.get(i).setPagado(pagado);
						cr.save(comp.get(i));
						re = true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("error en modificar comparendo");
		}

		if (re) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("MODIFICADO");

		} else {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("NO PUDO SER MODIFICADO");

		}
	}

	@GetMapping("/mostrarValorTotalComparendos")
	public ResponseEntity<String> valorComparendos() {
		List<Comparendo> lista = (List<Comparendo>) cr.findAll();
		if (lista.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("NO HA SIDO POSIBLE HALLAR EL VALOR A PAGAR");
		} else {
			String cadena = "Lista Comparendos\n";
			BigInteger valor = BigInteger.ZERO;
			for (int i = 0; i < lista.size(); i++) {
				cadena += "Id: " + lista.get(i).getId() + " Cedula: " + lista.get(i).getCedula() + " Placa: "
						+ lista.get(i).getPlaca() + " Tipo: " + lista.get(i).getTipo() + " Valor: "
						+ lista.get(i).getValor() + "\n";
				valor = valor.add(BigInteger.valueOf(lista.get(i).getValor()));
			}
			cadena += "\n VALOR A RECAUDAR AL PAGAR TODAS LAS MULTAS:\n $" + valor + " pesos Colombianos.";
			return ResponseEntity.status(HttpStatus.FOUND).body(cadena);
		}
	}

	@GetMapping("/listarComparendosPorPersona")
	public ResponseEntity<String> listarComparendosPersona() {
		List<Comparendo> lista = (List<Comparendo>) cr.findAll();
		if (lista.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
					.body("NO HA SIDO POSIBLE LISTAR LOS COMPARENDOS POR PERSONA");
		} else {
			String cadena = "Lista de Comparendos por persona\n";
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

	static private int precio(String tipo) {
		int sm = 38666;
		int valor = 1;
		if (tipo.equalsIgnoreCase("D02")) {
			valor = 30;
		} else if (tipo.equalsIgnoreCase("B01")) {
			valor = 8;
		} else if (tipo.equalsIgnoreCase("D04")) {
			valor = 30;
		} else if (tipo.equalsIgnoreCase("C02")) {
			valor = 15;
		} else if (tipo.equalsIgnoreCase("D03")) {
			valor = 30;
		} else if (tipo.equalsIgnoreCase("D05")) {
			valor = 30;
		} else if (tipo.equalsIgnoreCase("D16")) {
			valor = 30;
		} else if (tipo.equalsIgnoreCase("C29")) {
			valor = 15;
		} else if (tipo.equalsIgnoreCase("A05")) {
			valor = 4;
		} else if (tipo.equalsIgnoreCase("A08")) {
			valor = 4;
		} else if (tipo.equalsIgnoreCase("A07")) {
			valor = 4;
		} else if (tipo.equalsIgnoreCase("A06")) {
			valor = 4;
		} else if (tipo.equalsIgnoreCase("B04")) {
			valor = 8;
		} else if (tipo.equalsIgnoreCase("B08")) {
			valor = 8;
		} else if (tipo.equalsIgnoreCase("B09")) {
			valor = 8;
		} else if (tipo.equalsIgnoreCase("B06")) {
			valor = 8;
		} else if (tipo.equalsIgnoreCase("B11")) {
			valor = 8;
		} else if (tipo.equalsIgnoreCase("B19")) {
			valor = 8;
		} else if (tipo.equalsIgnoreCase("B21")) {
			valor = 8;
		} else if (tipo.equalsIgnoreCase("B22")) {
			valor = 8;
		} else if (tipo.equalsIgnoreCase("C01")) {
			valor = 15;
		} else if (tipo.equalsIgnoreCase("C03")) {
			valor = 15;
		} else if (tipo.equalsIgnoreCase("C15")) {
			valor = 15;
		} else if (tipo.equalsIgnoreCase("C13")) {
			valor = 15;
		} else if (tipo.equalsIgnoreCase("C17")) {
			valor = 15;
		} else if (tipo.equalsIgnoreCase("D13")) {
			valor = 30;
		} else if (tipo.equalsIgnoreCase("D17")) {
			valor = 30;
		} else if (tipo.equalsIgnoreCase("E01")) {
			valor = 45;
		} else if (tipo.equalsIgnoreCase("E02")) {
			valor = 45;
		} else if (tipo.equalsIgnoreCase("B18")) {
			valor = 8;
		}
		return valor * sm;
	}
}
