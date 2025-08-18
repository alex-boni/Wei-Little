/**
 * 
 */
package Negocio.MaquinaJPA;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import java.util.Set;
import Negocio.ModeloJPA.Modelo;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import Negocio.AlquilerJPA.LineaAlquiler;
import javax.persistence.InheritanceType;
import javax.persistence.Inheritance;

@Inheritance(strategy = InheritanceType.JOINED)
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "num_serie") })
@Entity
@NamedQueries({
		@NamedQuery(name = "Negocio.MaquinaJPA.Maquina.findByid", query = "select obj from Maquina obj where :id_maquina = obj.id_maquina "),
		@NamedQuery(name = "Negocio.MaquinaJPA.Maquina.findBynum_serie", query = "select obj from Maquina obj where :num_serie = obj.num_serie "),
		@NamedQuery(name = "Negocio.MaquinaJPA.Maquina.findBynombre", query = "select obj from Maquina obj where :nombre = obj.nombre "),
		@NamedQuery(name = "Negocio.MaquinaJPA.Maquina.findByprecio_hora_actual", query = "select obj from Maquina obj where :precio_hora_actual = obj.precio_hora_actual "),
		@NamedQuery(name = "Negocio.MaquinaJPA.Maquina.findByalquilado", query = "select obj from Maquina obj where :alquilado = obj.alquilado "),
		@NamedQuery(name = "Negocio.MaquinaJPA.Maquina.findByactivo", query = "select obj from Maquina obj where :activo = obj.activo "),
		@NamedQuery(name = "Negocio.MaquinaJPA.Maquina.findByversion", query = "select obj from Maquina obj where :version = obj.version ") })
public class Maquina implements Serializable {

	private static final long serialVersionUID = 0;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id_maquina;

	private String num_serie;

	private String nombre;

	private Double precio_hora_actual;

	private int alquilado;

	private int activo;

	@Version
	private Integer version;

	@ManyToOne
	private Modelo modelo;

	@OneToMany(mappedBy = "maquina")
	private Set<LineaAlquiler> lineasAlquiler;

	public Maquina() {

	}

	public Maquina(TMaquina maquina) {

		this.id_maquina = maquina.get_id();
		this.nombre = maquina.get_nombre();
		this.alquilado = maquina.get_alquilado();
		this.num_serie = maquina.get_num_serie();
		this.precio_hora_actual = maquina.get_precio_hora_actual();
		this.activo = maquina.get_activo();
	}

	public TMaquina entityToTransfer() {

		TMaquina tmaquina = new TMaquina();
		tmaquina.set_id(this.get_id());
		tmaquina.set_id_modelo(this.get_Modelo().get_id());
		tmaquina.set_activo(this.activo);
		tmaquina.set_alquilado(this.alquilado);
		tmaquina.set_nombre(this.nombre);
		tmaquina.set_num_serie(this.num_serie);
		tmaquina.set_precio_hora_actual(this.precio_hora_actual);

		return tmaquina;

	}

	public Integer get_id() {

		return id_maquina;
	}

	public String get_num_serie() {

		return num_serie;
	}

	public String get_nombre() {

		return nombre;
	}

	public Double get_precio_hora_actual() {

		return precio_hora_actual;
	}

	public int get_alquilado() {

		return alquilado;
	}

	public int get_activo() {

		return activo;
	}

	public Modelo get_Modelo() {

		return modelo;

	}

	// PODRIA SOBRAR
	public Integer get_version() {
		return version;
	}

	public void set_id(Integer id_maquina) {
		this.id_maquina = id_maquina;
	}

	public void set_num_serie(String num_serie) {
		this.num_serie = num_serie;
	}

	public void set_nombre(String nombre) {
		this.nombre = nombre;
	}

	public void set_precio_hora_actual(Double precio_hora_actual) {
		this.precio_hora_actual = precio_hora_actual;
	}

	public void set_alquilado(int alquilado) {
		this.alquilado = alquilado;
	}

	public void set_activo(int activo) {
		this.activo = activo;
	}

	public void set_Modelo(Modelo modelo) {

		this.modelo = modelo;
	}

	public double coste_seguro() {
		return 0;
	}
}