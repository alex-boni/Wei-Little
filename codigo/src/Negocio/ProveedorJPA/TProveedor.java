/**
 * 
 */
package Negocio.ProveedorJPA;

/**
 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
 * 
 * @author Alex
 * @generated "UML a JPA
 *            (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
public class TProveedor {

	private int id_proveedor;

	private String nombre;
	private String CIF;
	private int activo;

	public int get_id_proveedor() {
		return this.id_proveedor;
	}

	public String get_nombre() {
		return this.nombre;
	}

	public String get_CIF() {
		return this.CIF;
	}

	public int get_activo() {
		return this.activo;
	}

	public void set_id_proveedor(int id) {
		this.id_proveedor = id;
	}

	public void set_nombre(String nombre) {
		this.nombre = nombre;
	}

	public void set_CIF(String cif) {
		this.CIF = cif;
	}

	public void set_activo(int activo) {
		this.activo = activo;
	}
}