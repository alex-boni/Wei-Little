package Negocio.EmpleadoJPA;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.NamedQuery;

import javax.persistence.NamedQueries;

@Entity
@NamedQueries({
		@NamedQuery(name = "Negocio.EmpleadoJPA.EmpleadoDependiente.findByid_empleado", query = "select obj from EmpleadoDependiente obj where :id_empleado = obj.id_empleado "),
		@NamedQuery(name = "Negocio.EmpleadoJPA.EmpleadoDependiente.findByidioma", query = "select obj from EmpleadoDependiente obj where :idioma = obj.idioma ") })
public class EmpleadoDependiente extends Empleado implements Serializable {
	private static final long serialVersionUID = 0;

	public EmpleadoDependiente() {
	}

	private String idioma;

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public TEmpleadoDependiente toTransfer() {

		TEmpleadoDependiente dependiente = new TEmpleadoDependiente();
		dependiente.setIdioma(this.idioma);

		TEmpleado base = super.toTransfer();
		dependiente.set_activo(base.get_activo());
		dependiente.set_DNI(base.get_DNI());
		dependiente.set_nombre(base.get_nombre());
		dependiente.set_id_empleado(base.get_id_empleado());
		dependiente.set_salario(base.get_salario());

		return dependiente;
	}
}