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
		@NamedQuery(name = "Negocio.MaquinaJPA.Arcade.findByid", query = "select obj from Arcade obj where :id_maquina = obj.id_maquina "),
		@NamedQuery(name = "Negocio.MaquinaJPA.Arcade.deleteByid", query = "DELETE FROM Arcade a WHERE a.id_maquina = :id_maquina"),
		@NamedQuery(name = "Negocio.MaquinaJPA.Arcade.findByprecio_pantalla", query = "select obj from Arcade obj where :precio_pantalla = obj.precio_pantalla "),
		@NamedQuery(name = "Negocio.MaquinaJPA.Arcade.findBycoe_valor_mul", query = "select obj from Arcade obj where :coe_valor_mul = obj.coe_valor_mul ") })
public class Arcade extends Maquina implements Serializable {

	private static final long serialVersionUID = 0;

	private Double precio_pantalla;

	private Double coe_valor_mul;

	public Arcade() {
	}

	public Arcade(TArcade arcade) {
		super(arcade);
		this.precio_pantalla = arcade.get_precio_pantalla();
		this.coe_valor_mul = arcade.get_coe_valor_mul();
	}

	public Double get_precio_pantalla() {

		return precio_pantalla;
	}

	public void set_precio_pantalla(Double precio_pantalla) {
		this.precio_pantalla = precio_pantalla;
	}

	public Double get_coe_valor_mul() {
		return coe_valor_mul;
	}

	public void set_coe_valor_mul(Double coe_valor_mul) {
		this.coe_valor_mul = coe_valor_mul;
	}

	@Override
	public double coste_seguro() {
		return this.precio_pantalla * this.coe_valor_mul;
	}

	@Override
	public TMaquina entityToTransfer() {
		TArcade arcade = new TArcade();
		arcade.set_precio_pantalla(this.precio_pantalla);
		arcade.set_coe_valor_mul(this.coe_valor_mul);

		TMaquina base = super.entityToTransfer();
		arcade.set_id(base.get_id());
		arcade.set_id_modelo(base.get_id_modelo());
		arcade.set_activo(base.get_activo());
		arcade.set_alquilado(base.get_alquilado());
		arcade.set_nombre(base.get_nombre());
		arcade.set_num_serie(base.get_num_serie());
		arcade.set_precio_hora_actual(base.get_precio_hora_actual());

		return arcade;
	}
}