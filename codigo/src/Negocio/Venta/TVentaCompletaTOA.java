package Negocio.Venta;

import java.util.Set;

import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;
import Negocio.Trabajador.TTrabajador;

public class TVentaCompletaTOA {

	private TVenta venta;

	private Set<TLineaVenta> lista_lineasVenta;

	private Set<TProductoEnPlataforma> lista_producto_plataforma;

	private TTrabajador trabajador;

	public void colocar_datos(TVenta venta, Set<TLineaVenta> lista_lineasVenta,
			Set<TProductoEnPlataforma> lista_producto_plataforma, TTrabajador trabajador) {

		this.venta = venta;
		this.lista_lineasVenta = lista_lineasVenta;
		this.lista_producto_plataforma = lista_producto_plataforma;
		this.trabajador = trabajador;
	}

	public void set_venta(TVenta venta) {

		this.venta = venta;
	}

	public void set_lista_lineasVenta(Set<TLineaVenta> lista_lineasVenta) {

		this.lista_lineasVenta = lista_lineasVenta;
	}

	public void set_lista_producto_plataforma(Set<TProductoEnPlataforma> lista_producto_plataforma) {

		this.lista_producto_plataforma = lista_producto_plataforma;
	}

	public void set_trabajador(TTrabajador trabajador) {

		this.trabajador = trabajador;
	}

	public TVenta get_venta() {

		return this.venta;
	}

	public Set<TLineaVenta> get_lista_lineasVenta() {

		return this.lista_lineasVenta;
	}

	public Set<TProductoEnPlataforma> get_lista_producto_plataforma() {

		return this.lista_producto_plataforma;
	}

	public TTrabajador get_trabajador() {

		return this.trabajador;
	}
}
