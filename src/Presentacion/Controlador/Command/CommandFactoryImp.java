package Presentacion.Controlador.Command;

import Presentacion.Controlador.Command.CommandAlquilerJPA.AbrirAlquilerCommand;
import Presentacion.Controlador.Command.CommandAlquilerJPA.AnyadirMaquinaAlquilerCommand;
import Presentacion.Controlador.Command.CommandAlquilerJPA.BuscarAlquilerCommand;
import Presentacion.Controlador.Command.CommandAlquilerJPA.CancelarAlquilerCommand;
import Presentacion.Controlador.Command.CommandAlquilerJPA.CerrarAlquilerCommand;
import Presentacion.Controlador.Command.CommandAlquilerJPA.EliminarAlquilerCommand;
import Presentacion.Controlador.Command.CommandAlquilerJPA.EliminarMaquinaCarritoCommand;
import Presentacion.Controlador.Command.CommandAlquilerJPA.ListarAlquileresCommand;
import Presentacion.Controlador.Command.CommandAlquilerJPA.ListarAlquileresPorClienteCommand;
import Presentacion.Controlador.Command.CommandAlquilerJPA.ListarAlquileresPorEmpleadoCommand;
import Presentacion.Controlador.Command.CommandAlquilerJPA.ModificarAlquilerCommand;
import Presentacion.Controlador.Command.CommandAlquilerJPA.PasarCarritoACerrarAlquilerCommand;
import Presentacion.Controlador.Command.CommandAlquilerJPA.PasarCarritoAEliminarAlquilerCommand;
import Presentacion.Controlador.Command.CommandAlquilerJPA.PasarCarritoAInsertarAlquilerCommand;
import Presentacion.Controlador.Command.CommandClienteJPA.AltaClienteCommand;
import Presentacion.Controlador.Command.CommandClienteJPA.BajaClienteCommand;
import Presentacion.Controlador.Command.CommandClienteJPA.BuscarClienteCommand;
import Presentacion.Controlador.Command.CommandClienteJPA.ListarTodoClienteCommand;
import Presentacion.Controlador.Command.CommandClienteJPA.ModificarClienteCommand;
import Presentacion.Controlador.Command.CommandEmpleadoJPA.*;
import Presentacion.Controlador.Command.CommandHabilidad.*;
import Presentacion.Controlador.Command.CommandMaquinaJPA.AltaMaquinaCommand;
import Presentacion.Controlador.Command.CommandMaquinaJPA.BajaMaquinaCommand;
import Presentacion.Controlador.Command.CommandMaquinaJPA.BuscarMaquinaCommand;
import Presentacion.Controlador.Command.CommandMaquinaJPA.ListarMaquinaCommand;
import Presentacion.Controlador.Command.CommandMaquinaJPA.ListarMaquinaPorModeloCommand;
import Presentacion.Controlador.Command.CommandMaquinaJPA.ModificarMaquinaCommand;
import Presentacion.Controlador.Command.CommandModeloJPA.AltaModeloCommand;
import Presentacion.Controlador.Command.CommandModeloJPA.BajaModeloCommand;
import Presentacion.Controlador.Command.CommandModeloJPA.BuscarModeloCommand;
import Presentacion.Controlador.Command.CommandModeloJPA.CalcularCosteSeguroModeloCommand;
import Presentacion.Controlador.Command.CommandModeloJPA.DesvincularProveedorModeloCommand;
import Presentacion.Controlador.Command.CommandModeloJPA.ListarPorProveedorModeloCommand;
import Presentacion.Controlador.Command.CommandModeloJPA.ListarTodoModeloCommand;
import Presentacion.Controlador.Command.CommandModeloJPA.ModificarModeloCommand;
import Presentacion.Controlador.Command.CommandModeloJPA.VincularProveedorModeloCommand;
import Presentacion.Controlador.Command.CommandProductoPlataforma.*;
import Presentacion.Controlador.Command.CommandProveedorJPA.AltaProveedorCommand;
import Presentacion.Controlador.Command.CommandProveedorJPA.BajaProveedorCommand;
import Presentacion.Controlador.Command.CommandProveedorJPA.BuscarProveedorCommand;
import Presentacion.Controlador.Command.CommandProveedorJPA.ListarProveedorCommand;
import Presentacion.Controlador.Command.CommandProveedorJPA.ListarProveedorPorModeloCommand;
import Presentacion.Controlador.Command.CommandProveedorJPA.ModificarProveedorCommand;
import Presentacion.Controlador.Command.CommandTrabajador.*;
import Presentacion.Controlador.Command.CommandPlataforma.*;
import Presentacion.Controlador.Command.CommandProducto.*;
import Presentacion.Controlador.Command.CommandVenta.*;
import Presentacion.FactoriaPresentacion.Evento;

public class CommandFactoryImp extends CommandFactory {

	@Override
	public Command getCommand(Evento event) {

		Command command = null;

		switch (event) {

		// -------------------------------------//PLATAFORMA//--------------------------//

		case ALTA_PLATAFORMA:
			command = new AltaPlataformaCommand();
			break;
		case BAJA_PLATAFORMA:
			command = new BajaPlataformaCommand();
			break;
		case MOSTRAR_PLATAFORMA:
			command = new BuscarPlataformaCommand();
			break;
		case MODIFICAR_PLATAFORMA:
			command = new ModificarPlataformaCommand();
			break;
		case MOSTRAR_ALL_PLATAFORMAS:
			command = new ListarTodasPlataformaCommand();
			break;

		// -------------------------------------//VENTA//-------------------------------//

		case ABRIR_VENTA_NEGOCIO:
			command = new AbrirVentaCommand();
			break;
		case INSERTAR_PP_EN_VENTA_NEGOCIO:
			command = new AñadirProductoVentaCommand();
			break;
		case ELIMINAR_PP_EN_VENTA_NEGOCIO:
			command = new EliminarProductoVentaCommand();
			break;
		case PASAR_CARRITO_A_INSERTAR:
			command = new PasarCarritoAInsertarVentaCommand();
			break;
		case PASAR_CARRITO_A_ELIMINAR:
			command = new PasarCarritoAEliminarVentaCommand();
			break;
		case PASAR_CARRITO_A_CERRAR:
			command = new PasarCarritoACerrarCommand();
			break;
		case CERRAR_VENTA_NEGOCIO:
			command = new CerrarVentaCommand();
			break;
		case BAJA_VENTA_NEGOCIO:
			command = new BajaVentaCommand();
			break;
		case MODIFICAR_VENTA_NEGOCIO:
			command = new ModificarVentaCommand();
			break;
		case DEVOLVER_VENTA_NEGOCIO:
			command = new DevolverVentaCommand();
			break;
		case BUSCAR_VENTA_NEGOCIO:
			command = new BuscarVentaCommand();
			break;
		case LISTAR_VENTAS_ALL_NEGOCIO:
			command = new ListarTodoVentaCommand();
			break;
		case LISTAR_VENTAS_X_TRABAJADOR_NEGOCIO:
			command = new ListarPorTrabajadorVentaCommand();
			break;
		case LISTAR_VENTAS_X_PRODUCTO_EN_PLATAFORMA_NEGOCIO:
			command = new ListarPorProductoEnPlataformaVentaCommand();
			break;
		case CALCULAR_TOTAL_POR_PRODUCTOPLATAFORMA_QUERY:
			command = new CalcularQueryVentaCommand();
			break;
		// -------------------------------------//TRABAJADOR//-------------------------------------//
		case ALTA_TRABAJADOR:
			command = new AltaTrabajadorCommand();
			break;
		case BAJA_TRABAJADOR:
			command = new BajaTrabajadorCommand();
			break;
		case MODIFICAR_TRABAJADOR:
			command = new ModificarTrabajadorCommand();
			break;
		case BUSCAR_TRABAJADOR:
			command = new BuscarTrabajadorCommand();
			break;
		case LISTAR_TRABAJADOR_X_HABILIDAD:
			command = new ListarTrabajadorHabilidadCommand();
			break;
		case LISTAR_TRABAJADOR_ALL:
			command = new ListarTodoTrabajadorCommand();
			break;
		case LISTAR_TRABAJADOR_X_TIPO:
			command = new ListarTrabajadorTipoCommand();
			break;
		case VINCULAR_HABILIDAD_TRABAJADOR:
			command = new VincularHabilidadCommand();
			break;
		case DESVINCULAR_HABILIDAD_TRABAJADOR:
			command = new DesvincularHabilidadCommand();
			break;

		// -------------------------------------//PRODUCTO EN
		// PLATAFORMA//-------------------------------//

		case ALTA_PRODUCTO_PLATAFORMA:
			command = new AltaProductoEnPlataformaCommand();
			break;
		case BAJA_PRODUCTO_PLATAFORMA:
			command = new BajaProductoEnPlataformaCommand();
			break;
		case BUSCAR_PRODUCTO_PLATAFORMA:
			command = new BuscarProductoEnPlataformaCommand();
			break;
		case CALCULAR_CANTIDAD_PRODUCTO_EN_PLATAFORMA:
			command = new CalcularCantidadProductoEnPlataformaCommand();
			break;
		case LISTAR_PRODUCTO_PLATAFORMA_ALL:
			command = new ListarProductoEnPlataformaCommand();
			break;
		case LISTAR_PP_X_PLATAFORMA:
			command = new ListarPorPlataformaCommand();
			break;
		case LISTAR_PP_X_PRODUCTO:
			command = new ListarPorProductoCommand();
			break;
		case MODIFICAR_PRODUCTO_PLATAFORMA:
			command = new ModificarProductoEnPlataformaCommand();
			break;

		// -------------------------------------//HABILIDAD//---------------------------//

		case ALTA_HABILIDAD:
			command = new AltaHabilidadCommand();
			break;
		case BAJA_HABILIDAD:
			command = new BajaHabilidadCommand();
			break;
		case MODIFICAR_HABILIDAD:
			command = new ModificarHabilidadCommand();
			break;
		case BUSCAR_HABILIDAD:
			command = new BuscarHabilidadCommand();
			break;
		case LISTAR_HABILIDAD_ALL:
			command = new ListarTodoHabilidadCommand();
			break;
		case LISTAR_HABILIDAD_DEL_TRABAJADOR:
			command = new ListarPorTrabajadorHabilidadCommand();
			break;

		// -------------------------------------//PRODUCTO//-------------------------------------//

		case ALTA_PRODUCTO:
			command = new AltaProductoCommand();
			break;
		case BAJA_PRODUCTO:
			command = new BajaProductoCommand();
			break;
		case MODIFICAR_PRODUCTO:
			command = new modificarProductoCommand();
			break;
		case BUSCAR_PRODUCTO:
			command = new BuscarProductoCommand();
			break;
		case LISTAR_PRODUCTO_ALL:
			command = new listarTodoProductoCommand();
			break;
		case LISTAR_PRODUCTOS_X_TIPO:
			command = new listarPorTipoProductoCommand();
			break;

		// -------------------------------------//ALQUILER//-------------------------------//
		case ABRIR_ALQUILER_NEGOCIO:
			command = new AbrirAlquilerCommand();
			break;
		case INSERTAR_MAQUINA_EN_ALQUILER_NEGOCIO:
			command = new AnyadirMaquinaAlquilerCommand();
			break;
		case ELIMINAR_MAQUINA_EN_ALQUILER_NEGOCIO:
			command = new EliminarMaquinaCarritoCommand();
			break;
		case PASAR_CARRITO_ALQUILER_A_INSERTAR:
			command = new PasarCarritoAInsertarAlquilerCommand();
			break;
		case PASAR_CARRITO_ALQUILER_A_ELIMINAR:
			command = new PasarCarritoAEliminarAlquilerCommand();
			break;
		case PASAR_CARRITO_ALQUILER_A_CERRAR:
			command = new PasarCarritoACerrarAlquilerCommand();
			break;
		case CERRAR_ALQUILER_NEGOCIO:
			command = new CerrarAlquilerCommand();
			break;
		case BAJA_ALQUILER_NEGOCIO:
			command = new EliminarAlquilerCommand();
			break;
		case MODIFICAR_ALQUILER_NEGOCIO:
			command = new ModificarAlquilerCommand();
			break;
		case CANCELAR_ALQUILER_NEGOCIO:
			command = new CancelarAlquilerCommand();
			break;
		case BUSCAR_ALQUILER_NEGOCIO:
			command = new BuscarAlquilerCommand();
			break;
		case LISTAR_ALQUILERES_ALL_NEGOCIO:
			command = new ListarAlquileresCommand();
			break;
		case LISTAR_ALQUILERES_X_EMPLEADO_NEGOCIO:
			command = new ListarAlquileresPorEmpleadoCommand();
			break;
		case LISTAR_ALQUILERES_X_CLIENTE_NEGOCIO:
			command = new ListarAlquileresPorClienteCommand();
			break;

		// -------------------------------------//MODELO//-------------------------------//
		case ALTA_MODELO_NEGOCIO:
			command = new AltaModeloCommand();
			break;
		case BAJA_MODELO_NEGOCIO:
			command = new BajaModeloCommand();
			break;
		case BUSCAR_MODELO_NEGOCIO:
			command = new BuscarModeloCommand();
			break;
		case CALCULAR_COSTE_SEGURO_MODELO_NEGOCIO:
			command = new CalcularCosteSeguroModeloCommand();
			break;
		case DESVINCULAR_PROVEEDOR_MODELO_NEGOCIO:
			command = new DesvincularProveedorModeloCommand();
			break;
		case LISTAR_MODELO_X_PROVEEDOR_NEGOCIO:
			command = new ListarPorProveedorModeloCommand();
			break;
		case LISTAR_MODELO_ALL_NEGOCIO:
			command = new ListarTodoModeloCommand();
			break;
		case MODIFICAR_MODELO_NEGOCIO:
			command = new ModificarModeloCommand();
			break;
		case VINCULAR_PROVEEDOR_MODELO_NEGOCIO:
			command = new VincularProveedorModeloCommand();
			break;
		// -------------------------------------//EMPLEADO//-------------------------------------//

		case ALTA_EMPLEADO:
			command = new AltaEmpleadoCommand();
			break;
		case BAJA_EMPLEADO:
			command = new BajaEmpleadoCommand();
			break;
		case MODIFICAR_EMPLEADO:
			command = new ModificarEmpleadoCommand();
			break;
		case BUSCAR_EMPLEADO:
			command = new BuscarEmpleadoCommand();
			break;
		case LISTAR_EMPLEADO_ALL:
			command = new ListarEmpleadosCommand();
			break;

		default:
			break;

		// -------------------------------------//CLIENTE//-------------------------------------//

		case ALTA_CLIENTE:
			command = new AltaClienteCommand();
			break;
		case BAJA_CLIENTE:
			command = new BajaClienteCommand();
			break;
		case MODIFICAR_CLIENTE:
			command = new ModificarClienteCommand();
			break;
		case BUSCAR_CLIENTE:
			command = new BuscarClienteCommand();
			break;
		case LISTAR_CLIENTES_ALL:
			command = new ListarTodoClienteCommand();
			break;

		// -------------------------------------//MAQUINA//-------------------------------------//

		case ALTA_MAQUINA_NEGOCIO:
			command = new AltaMaquinaCommand();
			break;
		case BAJA_MAQUINA_NEGOCIO:
			command = new BajaMaquinaCommand();
			break;
		case MODIFICAR_MAQUINA_NEGOCIO:
			command = new ModificarMaquinaCommand();
			break;
		case BUSCAR_MAQUINA_NEGOCIO:
			command = new BuscarMaquinaCommand();
			break;
		case LISTAR_MAQUINAS_ALL_NEGOCIO:
			command = new ListarMaquinaCommand();
			break;
		case LISTAR_MAQUINAS_X_MODELO_NEGOCIO:
			command = new ListarMaquinaPorModeloCommand();
			break;

		// -------------------------------------//PROVEEDOR//-------------------------------------//

		case ALTA_PROVEEDOR_NEGOCIO:
			command = new AltaProveedorCommand();
			break;
		case BAJA_PROVEEDOR_NEGOCIO:
			command = new BajaProveedorCommand();
			break;
		case BUSCAR_PROVEEDOR_NEGOCIO:
			command = new BuscarProveedorCommand();
			break;
		case LISTAR_PROVEEDORES_ALL_NEGOCIO:
			command = new ListarProveedorCommand();
			break;
		case LISTAR_PROVEEDORES_MODELO_ALL_NEGOCIO:
			command = new ListarProveedorPorModeloCommand();
			break;
		case MODIFICAR_PROVEEDOR_NEGOCIO:
			command = new ModificarProveedorCommand();
			break;
		}

		return command;
	}

}
