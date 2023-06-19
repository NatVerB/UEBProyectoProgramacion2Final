package co.edu.unbosque.spring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Comparendo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int cedula;
	private String placa;
	private String tipo;
	private int valor;
	private boolean pagado;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCedula() {
		return cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}

	@Override
	public String toString() {
		return "Id: " + id + " Cedula: " + cedula + " Placa: " + placa + " Tipo: " + tipo + " Valor: " + valor
				+ " Pagado: " + pagado;
	}

}