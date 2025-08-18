package Negocio.EmpleadoJPA;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.NamedQueries;
import Negocio.AlquilerJPA.Alquiler;

import javax.persistence.Version;
import javax.persistence.InheritanceType;
import javax.persistence.Inheritance;

@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "DNI") })
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@NamedQueries({
		@NamedQuery(name = "Negocio.EmpleadoJPA.Empleado.findByid_empleado", query = "select obj from Empleado obj where :id_empleado = obj.id_empleado "),
		@NamedQuery(name = "Negocio.EmpleadoJPA.Empleado.findBynombre", query = "select obj from Empleado obj where :nombre = obj.nombre "),
		@NamedQuery(name = "Negocio.EmpleadoJPA.Empleado.findByDNI", query = "select obj from Empleado obj where :DNI = obj.DNI "),
		@NamedQuery(name = "Negocio.EmpleadoJPA.Empleado.findBysalario", query = "select obj from Empleado obj where :salario = obj.salario "),
		@NamedQuery(name = "Negocio.EmpleadoJPA.Empleado.findByactivo", query = "select obj from Empleado obj where :activo = obj.activo "),
		@NamedQuery(name = "Negocio.EmpleadoJPA.Empleado.findByversion", query = "select obj from Empleado obj where :version = obj.version ") })
public class Empleado implements Serializable {
	private static final long serialVersionUID = 0;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id_empleado;
	private String nombre;
	private String DNI;
	private double salario;
	private int activo;

	@OneToMany(mappedBy = "empleado")
	private Set<Alquiler> alquileres;

	@Version
	private int version;

	public Empleado() {
	}

	public Empleado(TEmpleado tEmpleado) {
		this.id_empleado = tEmpleado.get_id_empleado();
		this.nombre = tEmpleado.get_nombre();
		this.DNI = tEmpleado.get_DNI();
		this.salario = tEmpleado.get_salario();
		this.activo = tEmpleado.get_activo();
	}

	public TEmpleado toTransfer() {
		TEmpleado tEmpleado = new TEmpleado();
		tEmpleado.set_activo(activo);
		tEmpleado.set_DNI(DNI);
		tEmpleado.set_nombre(nombre);
		tEmpleado.set_salario(salario);
		tEmpleado.set_id_empleado(id_empleado);
		return tEmpleado;
	}

	public int get_id_empleado() {
		return this.id_empleado;
	}

	public int get_version() {
		return this.version;
	}

	public String get_nombre() {
		return this.nombre;
	}

	public String get_DNI() {
		return this.DNI;
	}

	public Double get_salario() {
		return this.salario;
	}

	public int get_activo() {
		return this.activo;
	}

	public Set<Alquiler> get_facturas_empleado() {
		return this.alquileres;
	}

	public void set_id_empleado(Integer id_empleado) {
		this.id_empleado = id_empleado;
	}

	public void set_nombre(String nombre) {
		this.nombre = nombre;
	}

	public void set_DNI(String DNI) {
		this.DNI = DNI;
	}

	public void set_salario(double salario) {
		this.salario = salario;
	}

	public void set_activo(int activo) {
		this.activo = activo;
	}

	public void set_facturas_empleado(Set<Alquiler> facturas_empleado) {
		this.alquileres = facturas_empleado;
	}

}