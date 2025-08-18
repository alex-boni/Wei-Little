/**
 * 
 */
package Negocio.MaquinaJPA;

public class TArcade extends TMaquina {

	private Double precio_pantalla;
	private Double coe_valor_mul;

	public TArcade(Integer id, String nombre, int id_modelo, String num_serie, int alquilado, Double precio_hora_actual,
			int activo, Double precio_pantalla, Double coe_valor_mul) {
		super(id, nombre, id_modelo, num_serie, alquilado, precio_hora_actual, activo);
		this.precio_pantalla = precio_pantalla;
		this.coe_valor_mul = coe_valor_mul;
	}

	public TArcade() {
		super();
	}

	public Double get_precio_pantalla() {

		return this.precio_pantalla;
	}

	public Double get_coe_valor_mul() {

		return coe_valor_mul;
	}

	public void set_precio_pantalla(Double precio_pantalla) {
		this.precio_pantalla = precio_pantalla;
	}

	public void set_coe_valor_mul(Double coe_valor_mul) {
		this.coe_valor_mul = coe_valor_mul;
	}

}