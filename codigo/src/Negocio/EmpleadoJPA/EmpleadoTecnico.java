package Negocio.EmpleadoJPA;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;

@Entity
@NamedQueries({
		@NamedQuery(name = "Negocio.EmpleadoJPA.EmpleadoTecnico.findByid_empleado", query = "select obj from EmpleadoTecnico obj where :id_empleado = obj.id_empleado "),
		@NamedQuery(name = "Negocio.EmpleadoJPA.EmpleadoTecnico.findByespecializacion", query = "select obj from EmpleadoTecnico obj where :especializacion = obj.especializacion ") })
public class EmpleadoTecnico extends Empleado implements Serializable {
	private static final long serialVersionUID = 0;

	public EmpleadoTecnico() {
	}

	private String especializacion;

	public String getEspecializacion() {
		return especializacion;
	}

	public void setEspecializacion(String especializacion) {
		this.especializacion = especializacion;
	}

	public TEmpleadoTecnico toTransfer() {

		TEmpleadoTecnico tecnico = new TEmpleadoTecnico();
		tecnico.setEspecializacion(this.especializacion);

		TEmpleado base = super.toTransfer();
		tecnico.set_activo(base.get_activo());
		tecnico.set_DNI(base.get_DNI());
		tecnico.set_nombre(base.get_nombre());
		tecnico.set_id_empleado(base.get_id_empleado());
		tecnico.set_salario(base.get_salario());

		return tecnico;
	}
}