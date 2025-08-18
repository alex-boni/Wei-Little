/**
 * 
 */
package Negocio.MaquinaJPA;

public class TRecreativa extends TMaquina {

	private Double coe_valor_div;
	private Double precio_mantenimiento;

	public TRecreativa() {
		super();
	}

	public TRecreativa(Integer id, String nombre, int id_modelo, String num_serie, int alquilado, int activo,
			Double precio_hora_actual, Double precio_mantenimiento, Double coe_valor_div) {
		super(id, nombre, id_modelo, num_serie, alquilado, precio_hora_actual, activo);
		this.precio_mantenimiento = precio_mantenimiento;
		this.coe_valor_div = coe_valor_div;
	}

	public Double get_coe_valor_div() {

		return this.coe_valor_div;
	}

	public Double get_precio_mantenimiento() {

		return this.precio_mantenimiento;
	}

	public void set_coe_valor_div(Double coe_valor_div) {
		this.coe_valor_div = coe_valor_div;
	}

	public void set_precio_mantenimiento(Double precio_mantenimiento) {
		this.precio_mantenimiento = precio_mantenimiento;
	}
}