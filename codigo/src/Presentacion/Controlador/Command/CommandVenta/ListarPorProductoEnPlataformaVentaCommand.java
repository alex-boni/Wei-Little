package Presentacion.Controlador.Command.CommandVenta;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Venta.TLineaVenta;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class ListarPorProductoEnPlataformaVentaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		Set<TLineaVenta> setVentaProducto = FactoriaServicioAplicacion.getInstancia().generaSAVenta()
				.listar_por_producto_en_plataforma_venta((int) transfer);
		if (setVentaProducto != null)
			return new Context(Evento.RES_LISTAR_VENTAS_X_PRODUCTO_EN_PLATAFORMA_OK, setVentaProducto);
		else
			return new Context(Evento.RES_LISTAR_VENTAS_X_PRODUCTO_EN_PLATAFORMA_KO, setVentaProducto);
	}
}
