package Presentacion.FactoriaPresentacion;

import Presentacion.*;
import Presentacion.AlquilerJPA.VistaAbrirAlquiler;
import Presentacion.AlquilerJPA.VistaAlquiler;
import Presentacion.AlquilerJPA.VistaAnyadirMaquinaAlquiler;
import Presentacion.AlquilerJPA.VistaBuscarAlquiler;
import Presentacion.AlquilerJPA.VistaCancelarAlquiler;
import Presentacion.AlquilerJPA.VistaCerrarAlquiler;
import Presentacion.AlquilerJPA.VistaEliminarMaquinaCarrito;
import Presentacion.ClienteJPA.VistaAltaCliente;
import Presentacion.ClienteJPA.VistaBajaCliente;
import Presentacion.ClienteJPA.VistaBuscarCliente;
import Presentacion.ClienteJPA.VistaCliente;
import Presentacion.ClienteJPA.VistaListarTodoCliente;
import Presentacion.ClienteJPA.VistaModificarCliente;
import Presentacion.EmpleadoJPA.VistaAltaEmpleado;
import Presentacion.EmpleadoJPA.VistaBajaEmpleado;
import Presentacion.EmpleadoJPA.VistaBuscarEmpleado;
import Presentacion.EmpleadoJPA.VistaEmpleado;
import Presentacion.EmpleadoJPA.VistaListarTodoEmpleado;
import Presentacion.EmpleadoJPA.VistaModificarEmpleado;
import Presentacion.AlquilerJPA.VistaListarAlquileres;
import Presentacion.AlquilerJPA.VistaListarAlquileresPorCliente;
import Presentacion.AlquilerJPA.VistaListarAlquileresPorEmpleado;
import Presentacion.AlquilerJPA.VistaModificarAlquiler;
import Presentacion.Habilidad.*;
import Presentacion.ModeloJPA.*;
import Presentacion.MaquinaJPA.VistaAltaMaquina;
import Presentacion.MaquinaJPA.VistaBajaMaquina;
import Presentacion.MaquinaJPA.VistaBuscarMaquina;
import Presentacion.MaquinaJPA.VistaListarTodasMaquinas;
import Presentacion.MaquinaJPA.VistaListarTodoMaquinasPorModelo;
import Presentacion.MaquinaJPA.VistaMaquina;
import Presentacion.MaquinaJPA.VistaModificarMaquina;
import Presentacion.Plataforma.*;
import Presentacion.Producto.VProducto;
import Presentacion.Producto.VistaAltaProducto;
import Presentacion.Producto.VistaBajaProducto;
import Presentacion.Producto.VistaBuscarProducto;
import Presentacion.Producto.VistaListarPorTipoProducto;
import Presentacion.Producto.VistaModificarProducto;
import Presentacion.Producto.VistarListarTodoProducto;
import Presentacion.ProductoEnPlataforma.VAltaProductoEnPlataforma;
import Presentacion.ProductoEnPlataforma.VBajaProductoEnPlataforma;
import Presentacion.ProductoEnPlataforma.VBuscarProductoEnPlataforma;
import Presentacion.ProductoEnPlataforma.VCalcularCantidadProductoEnPlataforma;
import Presentacion.ProductoEnPlataforma.VListarProductoEnPlataforma;
import Presentacion.ProductoEnPlataforma.VModificarProductoEnPlataforma;
import Presentacion.ProductoEnPlataforma.VProductoEnPlataforma;
import Presentacion.ProveedorJPA.VistaAltaProveedor;
import Presentacion.ProveedorJPA.VistaBajaProveedor;
import Presentacion.ProveedorJPA.VistaBuscarProveedor;
import Presentacion.ProveedorJPA.VistaListarProveedor;
import Presentacion.ProveedorJPA.VistaListarProveedorPorModelo;
import Presentacion.ProveedorJPA.VistaModificarProveedor;
import Presentacion.ProveedorJPA.VistaProveedor;
import Presentacion.Trabajador.VistaAltaTrabajador;
import Presentacion.Trabajador.VistaBajaTrabajador;
import Presentacion.Trabajador.VistaBuscarTrabajador;
import Presentacion.Trabajador.VistaBuscarTrabajadorHabilidad;
import Presentacion.Trabajador.VistaBuscarTrabajadorTipo;
import Presentacion.Trabajador.VistaDesvincularHabilidad;
import Presentacion.Trabajador.VistaListarTodoTrabajador;
import Presentacion.Trabajador.VistaModificarTrabajador;
import Presentacion.Trabajador.VistaTrabajador;
import Presentacion.Trabajador.VistaVincularHabilidad;
import Presentacion.Venta.*;

public class FactoriaVistasImp extends FactoriaVistas {

	public IGUI generarVistas(Evento e) {
		switch (e) {

		/*--------- VISTA PRINCIPAL ---------*/
		case VISTA_PRINCIPAL:
			return new VistaPrincipal();

		/*-------- PLATAFORMA ---------------*/
		case PLATAFORMA:
			return new VistaPlataforma();
		case VALTA_PLATAFORMA:
		case RES_ALTA_PLATAFORMA_OK:
		case RES_ALTA_PLATAFORMA_KO:
			return new VistaAltaPlataforma();
		case VBAJA_PLATAFORMA:
		case RES_BAJA_PLATAFORMA_OK:
		case RES_BAJA_PLATAFORMA_KO:
			return new VistaBajaPlataforma();
		case VMODIFICAR_PLATAFORMA:
		case RES_MODIFICAR_PLATAFORMA_OK:
		case RES_MODIFICAR_PLATAFORMA_KO:
		case RES_MODIFICAR_PLATAFORMA_KO_NOID:
		case RES_MODIFICAR_PLATAFORMA_KO_SAMENAME:
			return new VistaModificarPlataforma();
		case VMOSTRAR_PLATAFORMA:
		case RES_MOSTRAR_PLATAFORMA_OK:
		case RES_MOSTRAR_PLATAFORMA_KO:
			return new VistaBuscarPlataforma();
		case VMOSTRAR_ALL_PLATAFORMAS:
		case RES_MOSTRAR_ALL_PLATAFORMAS_OK:
		case RES_MOSTRAR_ALL_PLATAFORMAS_KO:
			return new VistaListarTodoPlataforma();

		/*--------- VISTAS DE VENTA ---------*/
		case VENTA:
			return new VVenta();
		case ABRIR_VENTA_VISTA:
		case RES_ABRIR_VENTA_OK:
		case RES_ABRIR_VENTA_KO:
			return new VAbrirVenta();
		case INSERTAR_PP_EN_VENTA_VISTA:
		case RES_INSERTAR_PP_EN_VENTA_OK:
		case RES_INSERTAR_PP_EN_VENTA_KO:
		case RES_PASAR_CARRITO_A_INSERTAR_OK:
		case RES_PASAR_CARRITO_A_INSERTAR_KO:
			return new VAnadirProductoVenta();
		case BAJA_VENTA_VISTA:
		case RES_BAJA_VENTA_OK:
		case RES_BAJA_VENTA_KO:
			return new VBajaVenta();
		case ELIMINAR_PP_EN_VENTA_VISTA:
		case RES_ELIMINAR_PP_EN_VENTA_OK:
		case RES_ELIMINAR_PP_EN_VENTA_KO:
		case RES_PASAR_CARRITO_A_ELIMINAR_OK:
		case RES_PASAR_CARRITO_A_ELIMINAR_KO:
			return new VEliminarProductoVenta();
		case CERRAR_VENTA_VISTA:
		case RES_CERRAR_VENTA_OK:
		case RES_CERRAR_VENTA_KO:
		case RES_PASAR_CARRITO_A_CERRAR_OK:
		case RES_PASAR_CARRITO_A_CERRAR_KO:
			return new VCerrarVenta();
		case BUSCAR_VENTA_VISTA:
		case RES_BUSCAR_VENTA_OK:
		case RES_BUSCAR_VENTA_KO:
			return new VBuscarVenta();
		case DEVOLVER_VENTA_VISTA:
		case RES_DEVOLVER_VENTA_OK:
		case RES_DEVOLVER_VENTA_KO:
			return new VDevolverVenta();
		case LISTAR_VENTAS_ALL_VISTA:
		case RES_LISTAR_VENTAS_ALL_OK:
		case RES_LISTAR_VENTAS_ALL_KO:
			return new VListarTodoVenta();
		case LISTAR_VENTAS_X_TRABAJADOR_VISTA:
		case RES_LISTAR_VENTAS_X_TRABAJADOR_OK:
		case RES_LISTAR_VENTAS_X_TRABAJADOR_KO:
			return new VListarPorTrabajadorVenta();
		case LISTAR_VENTAS_X_PRODUCTO_EN_PLATAFORMA_VISTA:
		case RES_LISTAR_VENTAS_X_PRODUCTO_EN_PLATAFORMA_OK:
		case RES_LISTAR_VENTAS_X_PRODUCTO_EN_PLATAFORMA_KO:
			return new VListarPorProductoEnPlataforma();
		case MODIFICAR_VENTA_VISTA:
		case RES_MODIFICAR_VENTA_OK:
		case RES_MODIFICAR_VENTA_KO:
			return new VModificarVenta();
		case CALCULAR_TOTAL_POR_PRODUCTOPLATAFORMA_QUERY_VISTA:
		case RES_CALCULAR_TOTAL_POR_PRODUCTOPLATAFORMA_QUERY_OK:
		case RES_CALCULAR_TOTAL_POR_PRODUCTOPLATAFORMA_QUERY_KO:
			return new VQueryVenta();

		/*--------- VISTAS DE PRODUCTO ---------*/
		case VALTA_PRODUCTO:
		case RES_ALTA_PRODUCTO_KO:
		case RES_ALTA_PRODUCTO_OK:
			return new VistaAltaProducto();
		case VBAJA_PRODUCTO:
		case RES_BAJA_PRODUCTO_KO:
		case RES_BAJA_PRODUCTO_OK:
			return new VistaBajaProducto();
		case VMODIFICAR_PRODUCTO:
		case RES_MODIFICAR_PRODUCTO_KO:
		case RES_MODIFICAR_PRODUCTO_OK:
			return new VistaModificarProducto();
		case VBUSCAR_PRODUCTO:
		case RES_BUSCAR_PRODUCTO_KO:
		case RES_BUSCAR_PRODUCTO_OK:
			return new VistaBuscarProducto();
		case VLISTAR_PRODUCTOS_X_TIPO:
		case RES_LISTAR_PRODUCTOS_X_TIPO_KO:
		case RES_LISTAR_PRODUCTOS_X_TIPO_OK:
			return new VistaListarPorTipoProducto();
		case VLISTAR_PRODUCTO_ALL:
		case RES_LISTAR_PRODUCTO_ALL_KO:
		case RES_LISTAR_PRODUCTO_ALL_OK:
			return new VistarListarTodoProducto();
		case PRODUCTO:
			return new VProducto();

		/*--------- VISTAS DE TRABAJADOR ---------*/

		case VALTA_TRABAJADOR:
		case RES_ALTA_TRABAJADOR_OK:
		case RES_ALTA_TRABAJADOR_KO:
			return new VistaAltaTrabajador();
		case VBAJA_TRABAJADOR:
		case RES_BAJA_TRABAJADOR_OK:
		case RES_BAJA_TRABAJADOR_KO:
			return new VistaBajaTrabajador();
		case VMODIFICAR_TRABAJADOR:
		case RES_MODIFICAR_TRABAJADOR_OK:
		case RES_MODIFICAR_TRABAJADOR_KO:
			return new VistaModificarTrabajador();
		case VMOSTRAR_TRABAJADOR:
		case RES_BUSCAR_TRABAJADOR_OK:
		case RES_BUSCAR_TRABAJADOR_KO:
			return new VistaBuscarTrabajador();
		case VBUSCAR_TRABAJADOR_X_TIPO:
		case RES_LISTAR_TRABAJADOR_X_TIPO_SUPERVISOR_OK:
		case RES_LISTAR_TRABAJADOR_X_TIPO_KO:
		case RES_LISTAR_TRABAJADOR_X_TIPO_VENDEDOR_OK:
			return new VistaBuscarTrabajadorTipo();
		case VMOSTRAR_TRABAJADOR_X_HABILIDAD:
		case RES_LISTAR_TRABAJADOR_X_HABILIDAD_OK:
		case RES_LISTAR_TRABAJADOR_X_HABILIDAD_KO:
			return new VistaBuscarTrabajadorHabilidad();
		case VMOSTRAR_ALL_TRABAJADOR:
		case RES_LISTAR_TRABAJADOR_ALL_OK:
		case RES_LISTAR_TRABAJADOR_ALL_KO:
			return new VistaListarTodoTrabajador();
		case VVINCULAR_HABILIDAD_TRABAJADOR:
		case RES_VINCULAR_HABILIDAD_TRABAJADOR_OK:
		case RES_VINCULAR_HABILIDAD_TRABAJADOR_KO:
			return new VistaVincularHabilidad();
		case VDESVINCULAR_HABILIDAD_TRABAJADOR:
		case RES_DESVINCULAR_HABILIDAD_TRABAJADOR_OK:
		case RES_DESVINCULAR_HABILIDAD_TRABAJADOR_KO:
			return new VistaDesvincularHabilidad();
		case TRABAJADOR:
			return new VistaTrabajador();

		/*--------- VISTAS DE PRODUCTO EN PLATAFORMA ---------*/
		case PRODUCTO_EN_PLATAFORMA:
			return new VProductoEnPlataforma();
		case VALTA_PRODUCTO_PLATAFORMA:
		case RES_ALTA_PRODUCTO_PLATAFORMA_OK:
		case RES_ALTA_PRODUCTO_PLATAFORMA_KO:
			return new VAltaProductoEnPlataforma();
		case VBAJA_PRODUCTO_PLATAFORMA:
		case RES_BAJA_PRODUCTO_PLATAFORMA_OK:
		case RES_BAJA_PRODUCTO_PLATAFORMA_KO:
			return new VBajaProductoEnPlataforma();
		case VMODIFICAR_PRODUCTO_PLATAFORMA:
		case RES_MODIFICAR_PRODUCTO_PLATAFORMA_OK:
		case RES_MODIFICAR_PRODUCTO_PLATAFORMA_KO:
			return new VModificarProductoEnPlataforma();
		case VBUSCAR_PRODUCTO_PLATAFORMA:
		case RES_BUSCAR_PRODUCTO_PLATAFORMA_OK:
		case RES_BUSCAR_PRODUCTO_PLATAFORMA_KO:
			return new VBuscarProductoEnPlataforma();
		case VLISTAR_TODO_PP:
		case RES_LISTAR_PRODUCTO_PLATAFORMA_ALL_OK:
		case RES_LISTAR_PRODUCTO_PLATAFORMA_ALL_KO:
		case RES_LISTAR_PP_X_PRODUCTO_OK:
		case RES_LISTAR_PP_X_PRODUCTO_KO:
		case RES_LISTAR_PP_X_PLATAFORMA_OK:
		case RES_LISTAR_PP_X_PLATAFORMA_KO:
			return new VListarProductoEnPlataforma();
		case VCALCULAR_CANTIDAD_PRODUCTO_EN_PLATAFORMA:
		case RES_CALCULAR_CANTIDAD_PRODUCTO_EN_PLATAFORMA_OK:
		case RES_CALCULAR_CANTIDAD_PRODUCTO_EN_PLATAFORMA_KO:
			return new VCalcularCantidadProductoEnPlataforma();

		/*--------- VISTAS DE HABILIDAD ---------*/
		case HABILIDAD:
			return new VistaHabilidad();
		case VALTA_HABILIDAD:
		case RES_ALTA_HABILIDAD_OK:
		case RES_ALTA_HABILIDAD_KO:
			return new VistaAltaHabilidad();
		case VBAJA_HABILIDAD:
		case RES_BAJA_HABILIDAD_OK:
		case RES_BAJA_HABILIDAD_KO:
			return new VistaBajaHabilidad();
		case VMODIFICAR_HABILIDAD:
		case RES_MODIFICAR_HABILIDAD_OK:
		case RES_MODIFICAR_HABILIDAD_KO:
		case RES_MODIFICAR_HABILIDAD_KOSAMENAME:
		case RES_MODIFICAR_HABILIDAD_KONOID:
			return new VistaModificarHabilidad();
		case VBUSCAR_HABILIDAD:
		case RES_BUSCAR_HABILIDAD_OK:
		case RES_BUSCAR_HABILIDAD_KO:
			return new VistaBuscarHabilidad();
		case VLISTAR_HABILIDAD_ALL:
		case RES_LISTAR_HABILIDAD_ALL_OK:
		case RES_LISTAR_HABILIDAD_ALL_KO:
			return new VistaListarTodoHabilidad();
		case VLISTAR_HABILIDAD_DEL_TRABAJADOR:
		case RES_LISTAR_HABILIDAD_DEL_TRABAJADOR_OK:
		case RES_LISTAR_HABILIDAD_DEL_TRABAJADOR_KO:
			return new VistaListarTodoHabilidadPorTrabajador();
		/*--------- VISTAS DE MODELO ---------*/

		case MODELO:
			return new VistaModelo();
		case ALTA_MODELO_VISTA:
		case RES_ALTA_MODELO_OK:
		case RES_ALTA_MODELO_KO:
			return new VistaAltaModelo();
		case BAJA_MODELO_VISTA:
		case RES_BAJA_MODELO_OK:
		case RES_BAJA_MODELO_KO:
		case RES_BAJA_MODELO_MAQUINAS_ACTIVAS_KO:
		case RES_BAJA_MODELO_VINCULACIONES_PROVEEDOR_KO:
			return new VistaBajaModelo();
		case MODIFICAR_MODELO_VISTA:
		case RES_MODIFICAR_MODELO_OK:
		case RES_MODIFICAR_MODELO_KO:
			return new VistaModificarModelo();
		case BUSCAR_MODELO_VISTA:
		case RES_BUSCAR_MODELO_OK:
		case RES_BUSCAR_MODELO_KO:
			return new VistaBuscarModelo();
		case LISTAR_MODELO_ALL_VISTA:
		case RES_LISTAR_MODELO_ALL_OK:
		case RES_LISTAR_MODELO_ALL_KO:
			return new VistaListarModelos();
		case LISTAR_MODELO_X_PROVEEDOR_VISTA:
		case RES_LISTAR_MODELO_OK_VACIO:
		case RES_LISTAR_MODELO_X_PROVEEDOR_OK:
		case RES_LISTAR_MODELO_X_PROVEEDOR_KO:
			return new VistaListarModelosPorProveedor();
		case VINCULAR_PROVEEDOR_MODELO_VISTA:
		case RES_VINCULAR_PROVEEDOR_MODELO_OK:
		case RES_VINCULAR_PROVEEDOR_MODELO_KO:
			return new VistaVincularModeloProveedor();
		case DESVINCULAR_PROVEEDOR_MODELO_VISTA:
		case RES_DESVINCULAR_PROVEEDOR_MODELO_OK:
		case RES_DESVINCULAR_PROVEEDOR_MODELO_KO:
			return new VistaDesvincularModeloProveedor();
		case CALCULAR_COSTE_SEGURO_MODELO_VISTA:
		case RES_CALCULAR_COSTE_SEGURO_MODELO_OK:
		case RES_CALCULAR_COSTE_SEGURO_MODELO_KO:
			return new VistaCalcularSeguroModelo();
		/*--------- VISTAS DE ALQUILER ---------*/

		case ALQUILER:
			return new VistaAlquiler();
		case ABRIR_ALQUILER_VISTA:
		case RES_ABRIR_ALQUILER_OK:
		case RES_ABRIR_ALQUILER_KO:
			return new VistaAbrirAlquiler();
		case INSERTAR_MAQUINA_EN_ALQUILER_VISTA:
		case RES_INSERTAR_MAQUINA_EN_ALQUILER_OK:
		case RES_INSERTAR_MAQUINA_EN_ALQUILER_KO:
		case RES_PASAR_CARRITO_ALQUILER_A_INSERTAR_OK:
		case RES_PASAR_CARRITO_ALQUILER_A_INSERTAR_KO:
			return new VistaAnyadirMaquinaAlquiler();
		case ELIMINAR_MAQUINA_EN_ALQUILER_VISTA:
		case RES_ELIMINAR_MAQUINA_EN_ALQUILER_OK:
		case RES_ELIMINAR_MAQUINA_EN_ALQUILER_KO:
		case RES_PASAR_CARRITO_ALQUILER_A_ELIMINAR_OK:
		case RES_PASAR_CARRITO_ALQUILER_A_ELIMINAR_KO:
			return new VistaEliminarMaquinaCarrito();
		case CERRAR_ALQUILER_VISTA:
		case RES_CERRAR_ALQUILER_OK:
		case RES_CERRAR_ALQUILER_KO:
		case RES_CERRAR_ALQUILER_KO_CARRITO_VACIO:
		case RES_CERRAR_ALQUILER_KO_NOCLIENTE:
		case RES_CERRAR_ALQUILER_KO_NOEMPLEADO:
		case RES_PASAR_CARRITO_ALQUILER_A_CERRAR_OK:
		case RES_PASAR_CARRITO_ALQUILER_A_CERRAR_KO:
			return new VistaCerrarAlquiler();
		case BUSCAR_ALQUILER_VISTA:
		case RES_BUSCAR_ALQUILER_OK:
		case RES_BUSCAR_ALQUILER_KO:
			return new VistaBuscarAlquiler();
		case CANCELAR_ALQUILER_VISTA:
		case RES_CANCELAR_ALQUILER_OK:
		case RES_CANCELAR_ALQUILER_KO_NO_ALQUILER:
		case RES_CANCELAR_ALQUILER_KO_NO_LINEA_ALQUILER:
		case RES_CANCELAR_ALQUILER_KO_MAQUINA_YA_DEVUELTA:
		case RES_CANCELAR_ALQUILER_KO_MAQUINA_NO_ENCONTRADA:
			return new VistaCancelarAlquiler();
		case LISTAR_ALQUILERES_ALL_VISTA:
		case RES_LISTAR_ALQUILERES_ALL_OK:
		case RES_LISTAR_ALQUILERES_ALL_KO:
			return new VistaListarAlquileres();
		case LISTAR_ALQUILERES_X_EMPLEADO_VISTA:
		case RES_LISTAR_ALQUILERES_X_EMPLEADO_OK:
		case RES_LISTAR_ALQUILERES_X_EMPLEADO_KO:
			return new VistaListarAlquileresPorEmpleado();
		case LISTAR_ALQUILERES_X_CLIENTE_VISTA:
		case RES_LISTAR_ALQUILERES_X_CLIENTE_OK:
		case RES_LISTAR_ALQUILERES_X_CLIENTE_KO:
			return new VistaListarAlquileresPorCliente();
		case MODIFICAR_ALQUILER_VISTA:
		case RES_MODIFICAR_ALQUILER_OK:
		case RES_MODIFICAR_ALQUILER_KO:
		case RES_MODIFICAR_ALQUILER_KO_NOALQUILER:
		case RES_MODIFICAR_ALQUILER_KO_NOCLIENTE:
		case RES_MODIFICAR_ALQUILER_KO_NOEMPLEADO:
			return new VistaModificarAlquiler();

		/*--------- VISTAS DE CLIENTE ---------*/
		case CLIENTE:
			return new VistaCliente();
		case VALTA_CLIENTE:
		case RES_ALTA_CLIENTE_OK:
		case RES_ALTA_CLIENTE_KO:
			return new VistaAltaCliente();
		case VBAJA_CLIENTE:
		case RES_BAJA_CLIENTE_OK:
		case RES_BAJA_CLIENTE_KO:
			return new VistaBajaCliente();
		case VBUSCAR_CLIENTE:
		case RES_BUSCAR_CLIENTE_OK:
		case RES_BUSCAR_CLIENTE_KO:
			return new VistaBuscarCliente();
		case VMODIFICAR_CLIENTE:
		case RES_MODIFICAR_CLIENTE_OK:
		case RES_MODIFICAR_CLIENTE_KO:
			return new VistaModificarCliente();
		case VLISTAR_CLIENTES_ALL:
		case RES_LISTAR_CLIENTES_ALL_OK:
		case RES_LISTAR_CLIENTES_ALL_KO:
			return new VistaListarTodoCliente();
		/*--------- VISTAS DE MAQUINA ---------*/
		case MAQUINA:
			return new VistaMaquina();

		case ALTA_MAQUINA_VISTA:
		case RES_ALTA_MAQUINA_OK:
		case RES_ALTA_MAQUINA_KO:

			return new VistaAltaMaquina();

		case BAJA_MAQUINA_VISTA:
		case RES_BAJA_MAQUINA_OK:
		case RES_BAJA_MAQUINA_KO:

			return new VistaBajaMaquina();

		case MODIFICAR_MAQUINA_VISTA:
		case RES_MODIFICAR_MAQUINA_OK:
		case RES_MODIFICAR_MAQUINA_KO:

			return new VistaModificarMaquina();

		case BUSCAR_MAQUINA_VISTA:
		case RES_BUSCAR_MAQUINA_OK:
		case RES_BUSCAR_MAQUINA_KO:

			return new VistaBuscarMaquina();

		case LISTAR_MAQUINAS_ALL_VISTA:
		case RES_LISTAR_MAQUINAS_ALL_OK:
		case RES_LISTAR_MAQUINAS_ALL_KO:

			return new VistaListarTodasMaquinas();

		case LISTAR_MAQUINAS_X_MODELO_VISTA:
		case RES_LISTAR_MAQUINAS_X_MODELO_OK:
		case RES_LISTAR_MAQUINAS_X_MODELO_KO:

			return new VistaListarTodoMaquinasPorModelo();

		/*--------- VISTAS DE EMPLEADO ---------*/
		case EMPLEADO:
			return new VistaEmpleado();
		case VALTA_EMPLEADO:
		case RES_ALTA_EMPLEADO_OK:
		case RES_ALTA_EMPLEADO_KO:
			return new VistaAltaEmpleado();
		case VBAJA_EMPLEADO:
		case RES_BAJA_EMPLEADO_OK:
		case RES_BAJA_EMPLEADO_KO:
			return new VistaBajaEmpleado();
		case VMODIFICAR_EMPLEADO:
		case RES_MODIFICAR_EMPLEADO_OK:
		case RES_MODIFICAR_EMPLEADO_KO:
			return new VistaModificarEmpleado();
		case VBUSCAR_EMPLEADO:
		case RES_BUSCAR_EMPLEADO_OK:
		case RES_BUSCAR_EMPLEADO_KO:
			return new VistaBuscarEmpleado();
		case VLISTAR_EMPLEADO_ALL:
		case RES_LISTAR_EMPLEADO_ALL_OK:
		case RES_LISTAR_EMPLEADO_ALL_OK_VACIO:
		case RES_LISTAR_EMPLEADO_ALL_KO:
			return new VistaListarTodoEmpleado();

		/*--------- VISTAS DE PROVEEDOR ---------*/
		case PROVEEDOR:
			return new VistaProveedor();
		case ALTA_PROVEEDOR_VISTA:
		case RES_ALTA_PROVEEDOR_OK:
		case RES_ALTA_PROVEEDOR_KO:
			return new VistaAltaProveedor();
		case BAJA_PROVEEDOR_VISTA:
		case RES_BAJA_PROVEEDOR_OK:
		case RES_BAJA_PROVEEDOR_KO:
			return new VistaBajaProveedor();
		case BUSCAR_PROVEEDOR_VISTA:
		case RES_BUSCAR_PROVEEDOR_OK:
		case RES_BUSCAR_PROVEEDOR_KO:
			return new VistaBuscarProveedor();
		case LISTAR_PROVEEDOR_ALL_VISTA:
		case RES_LISTAR_PROVEEDORES_ALL_OK:
		case RES_LISTAR_PROVEEDORES_ALL_KO:
			return new VistaListarProveedor();
		case LISTAR_PROVEEDOR_MODELO_ALL_VISTA:
		case RES_LISTAR_PROVEEDORES_MODELO_ALL_OK:
		case RES_LISTAR_PROVEEDORES_MODELO_ALL_KO:
			return new VistaListarProveedorPorModelo();
		case MODIFICAR_PROVEEDOR_VISTA:
		case RES_MODIFICAR_PROVEEDOR_OK:
		case RES_MODIFICAR_PROVEEDOR_KO:
			return new VistaModificarProveedor();

		default:
			return null;
		}
	}
}
