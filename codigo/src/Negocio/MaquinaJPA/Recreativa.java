/**
 * 
 */
package Negocio.MaquinaJPA;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;

@Entity
@NamedQueries({
		@NamedQuery(name = "Negocio.MaquinaJPA.Recreativa.findByid", query = "select obj from Recreativa obj where :id_maquina = obj.id_maquina "),
		@NamedQuery(name = "Negocio.MaquinaJPA.Recreativa.findByprecio_mantenimiento", query = "select obj from Recreativa obj where :precio_mantenimiento = obj.precio_mantenimiento "),
		@NamedQuery(name = "Negocio.MaquinaJPA.Recreativa.findBycoe_valor_div", query = "select obj from Recreativa obj where :coe_valor_div = obj.coe_valor_div ") })
public class Recreativa extends Maquina implements Serializable {

	private static final long serialVersionUID = 0;

	private Double precio_mantenimiento;

	private Double coe_valor_div;

	public Recreativa() {
	}

	public Recreativa(TRecreativa recreativa) {
		super(recreativa);
		this.precio_mantenimiento = recreativa.get_precio_mantenimiento();
		this.coe_valor_div = recreativa.get_coe_valor_div();
	}

	public Double get_precio_mantenimiento() {

		return precio_mantenimiento;
	}

	public void set_precio_mantenimiento(Double precio_mantenimiento) {

		this.precio_mantenimiento = precio_mantenimiento;
	}

	public Double get_coe_valor_div() {

		return coe_valor_div;
	}

	public void set_coe_valor_div(Double coe_valor_div) {

		this.coe_valor_div = coe_valor_div;
	}

	@Override
	public double coste_seguro() {
		return this.precio_mantenimiento / this.coe_valor_div;
	}

	@Override
	public TMaquina entityToTransfer() {
		TRecreativa recreativa = new TRecreativa();
		recreativa.set_precio_mantenimiento(this.precio_mantenimiento);
		recreativa.set_coe_valor_div(this.coe_valor_div);

		TMaquina base = super.entityToTransfer();
		recreativa.set_id(base.get_id());
		recreativa.set_id_modelo(base.get_id_modelo());
		recreativa.set_activo(base.get_activo());
		recreativa.set_alquilado(base.get_alquilado());
		recreativa.set_nombre(base.get_nombre());
		recreativa.set_num_serie(base.get_num_serie());
		recreativa.set_precio_hora_actual(base.get_precio_hora_actual());

		return recreativa;
	}

}