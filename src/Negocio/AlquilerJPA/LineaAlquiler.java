
package Negocio.AlquilerJPA;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.persistence.NamedQueries;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import Negocio.MaquinaJPA.Maquina;

@Entity
@NamedQueries({
		@NamedQuery(name = "Negocio.AlquilerJPA.LineaAlquiler.findByid_linea_alquiler", query = "select obj from LineaAlquiler obj where :id_linea_alquiler = obj.id_linea_alquiler "),
		@NamedQuery(name = "Negocio.AlquilerJPA.LineaAlquiler.findByversion", query = "select obj from LineaAlquiler obj where :version = obj.version "),
		@NamedQuery(name = "Negocio.AlquilerJPA.LineaAlquiler.findByduracion", query = "select obj from LineaAlquiler obj where :duracion = obj.duracion "),
		@NamedQuery(name = "Negocio.AlquilerJPA.LineaAlquiler.findBydevuelto", query = "select obj from LineaAlquiler obj where :devuelto = obj.devuelto "),
		@NamedQuery(name = "Negocio.AlquilerJPA.LineaAlquiler.findByprecio", query = "select obj from LineaAlquiler obj where :precio = obj.precio "),
		@NamedQuery(name = "Negocio.AlquilerJPA.LineaAlquiler.findByalquiler", query = "select obj from LineaAlquiler obj where :alquiler = obj.alquiler "),
		@NamedQuery(name = "Negocio.AlquilerJPA.LineaAlquiler.findBymaquina", query = "select obj from LineaAlquiler obj where :maquina = obj.maquina ") })
public class LineaAlquiler implements Serializable {

	private static final long serialVersionUID = 0;

	@EmbeddedId
	private LineaAlquilerID id_linea_alquiler;

	@Version
	private Integer version;

	private int duracion;
	private int devuelto;
	private double precio;

	@ManyToOne
	@MapsId
	private Alquiler alquiler;

	@ManyToOne
	@MapsId
	private Maquina maquina;

	public LineaAlquiler() {
	}

	public LineaAlquiler(TLineaAlquiler tLineaAlquiler) {
//		this.id = tLineaAlquiler.get_id_alquiler();
		this.duracion = tLineaAlquiler.get_duracion();
		this.devuelto = tLineaAlquiler.get_devuelto();
		this.precio = tLineaAlquiler.get_precio();
	}

	public LineaAlquiler(Alquiler alquiler, Maquina maquina) {
		this.id_linea_alquiler = new LineaAlquilerID(alquiler.get_id_alquiler(), maquina.get_id());
		this.alquiler = alquiler;
		this.maquina = maquina;
	}

	public int get_version() {
		return this.version;
	}

	public int get_duracion() {
		return this.duracion;
	}

	public int get_devuelto() {
		return this.devuelto;
	}

	public Alquiler get_alquiler() {
		return this.alquiler;
	}

	public double get_precio() {
		return this.precio;
	}

	public Maquina get_maquina() {
		return this.maquina;

	}

	public LineaAlquilerID get_id_linea_alquiler() {
		return this.id_linea_alquiler;
	}

	public void set_version(int v) {
		this.version = v;
	}

	public void set_duracion(int d) {
		this.duracion = d;
	}

	public void set_devuelto(int dev) {
		this.devuelto = dev;
	}

	public void set_alquiler(Alquiler alquiler) {
		this.alquiler = alquiler;
	}

	public void set_precio(double p) {
		this.precio = p;

	}

	public void set_maquina(Maquina maquina) {
		this.maquina = maquina;

	}

	public void set_id_linea_alquiler(LineaAlquilerID id_linea_alquiler) {
		this.id_linea_alquiler = id_linea_alquiler;
	}

	public TLineaAlquiler toTransfer() {
		TLineaAlquiler tLineaAlquiler = new TLineaAlquiler();
		tLineaAlquiler.set_id_alquiler(this.id_linea_alquiler.get_id_alquiler());
		tLineaAlquiler.set_id_maquina(this.id_linea_alquiler.get_id_maquina());
		tLineaAlquiler.set_duracion(this.duracion);
		tLineaAlquiler.set_devuelto(this.devuelto);
		tLineaAlquiler.set_precio(this.precio);
		return tLineaAlquiler;
	}
}