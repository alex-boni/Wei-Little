/**
 * 
 */
package Negocio.ModeloJPA;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import Negocio.MaquinaJPA.Maquina;
import java.util.Set;

@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "nombre") })
@Entity
@NamedQueries({
		@NamedQuery(name = "Negocio.ModeloJPA.Modelo.findByid", query = "select obj from Modelo obj where :id_modelo = obj.id_modelo "),
		@NamedQuery(name = "Negocio.ModeloJPA.Modelo.findByactivo", query = "select obj from Modelo obj where :activo = obj.activo "),
		@NamedQuery(name = "Negocio.ModeloJPA.Modelo.findBynombre", query = "select obj from Modelo obj where :nombre = obj.nombre "),
		@NamedQuery(name = "Negocio.ModeloJPA.Modelo.findByversion", query = "select obj from Modelo obj where :version = obj.version ") })
public class Modelo implements Serializable {

	private static final long serialVersionUID = 0;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id_modelo;

	private int activo;

	private String nombre;

	@Version
	private Integer version;

	@OneToMany(mappedBy = "modelo")
	private Set<Maquina> maquinas;

	@OneToMany(mappedBy = "modelo")
	private Set<VinculacionModeloProveedor> vinculaciones;

	public Modelo() {
	}

	public Modelo(TModelo otro) {
		this.activo = otro.get_activo();
		this.nombre = otro.get_nombre();
		this.id_modelo = otro.get_id();
		this.activo = 1;
	}

	public int get_id() {
		return this.id_modelo;
	}

	public int get_activo() {
		return this.activo;
	}

	public String get_nombre() {
		return this.nombre;
	}

	public void set_id(int id) {
		this.id_modelo = id;
	}

	public void set_activo(int activo) {
		this.activo = activo;
	}

	public void set_nombre(String nombre) {
		this.nombre = nombre;
	}

	public TModelo toTransfer() {

		TModelo model = new TModelo();

		model.set_activo(this.activo);
		model.set_nombre(this.nombre);
		model.set_id(this.id_modelo);

		return model;
	}

	public Set<VinculacionModeloProveedor> get_vinculaciones() {
		return this.vinculaciones;
	}

	public Set<Maquina> get_lista_maquinas() {
		return this.maquinas;
	}

	public void set_vinculaciones(Set<VinculacionModeloProveedor> vinculaciones) {
		this.vinculaciones = vinculaciones;
	}

	public void set_lista_maquinas(Set<Maquina> maquinas) {
		this.maquinas = maquinas;
	}

}