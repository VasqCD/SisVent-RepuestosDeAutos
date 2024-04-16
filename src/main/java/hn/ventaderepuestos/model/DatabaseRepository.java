package hn.ventaderepuestos.model;

import hn.ventaderepuestos.data.*;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface DatabaseRepository {
	
	@Headers({
		"Accept: application/json",
		"User-Agent: Retrofit-Sample-App"
	})
	@GET("/pls/apex/cvasq/svra/Repuestos")
	Call<RepuestoResponse> consultarRepuesto();
	
	@Headers({
		"Accept: application/json",
		"User-Agent: Retrofit-Sample-App"
	})
	@POST("/pls/apex/cvasq/svra/Repuestos")
	Call<ResponseBody> crearRepuesto(@Body Repuesto nuevo);

	@Headers({
		"Accept: application/json",
		"User-Agent: Retrofit-Sample-App"
	})
	@PUT("/pls/apex/cvasq/svra/Repuestos")
	Call<ResponseBody> actualizarRepuesto(@Body Repuesto cambiar);

	@Headers({
		
			"User-Agent: Retrofit-Sample-App"
	})
	@DELETE("/pls/apex/cvasq/svra/Repuestos")
	Call<ResponseBody> eliminarRepuesto(@Query("repuestoid") int repuestoid);


	//vista de proveedores

	@Headers({
			"Accept: application/json",
			"User-Agent: Retrofit-Sample-App"
	})
	@GET("/pls/apex/cvasq/svra/Proveedores")
	Call<ProveedoresResponse> consultarProveedor();
	//https://apex.oracle.com/pls/apex/cvasq/svra/Proveedores

	@Headers({
		"Accept: application/json",
		"User-Agent: Retrofit-Sample-App"
	})
	@POST("/pls/apex/cvasq/svra/Proveedores")
	Call<ResponseBody> crearProveedor(@Body Proveedor nuevo);

	@Headers({
		"Accept: application/json",
		"User-Agent: Retrofit-Sample-App"
	})
	@PUT("/pls/apex/cvasq/svra/Proveedores")
	Call<ResponseBody> actualizarProveedor(@Body Proveedor cambiar);

	@Headers({
		"Accept: application/json",

	})
	@DELETE("/pls/apex/cvasq/svra/Proveedores")
	Call<ResponseBody> eliminarProveedor(@Query("proveedorid") int proveedorid);

	//Ordenes
	//https://apex.oracle.com/pls/apex/cvasq/svra/Ordenes
	@Headers({
			"Accept: application/json",
			"User-Agent: Retrofit-Sample-App"
	})
	@GET("/pls/apex/cvasq/svra/Ordenes")
	Call<OrdenesResponse> consultarOrden();

	@Headers({
		"Accept: application/json",
		"User-Agent: Retrofit-Sample-App"
	})
	@POST("/pls/apex/cvasq/svra/Ordenes")
	Call<ResponseBody> crearOrden(@Body Orden nuevo);

	@Headers({
		"Accept: application/json",
		"User-Agent: Retrofit-Sample-App"
	})
	@PUT("/pls/apex/cvasq/svra/Ordenes")
	Call<ResponseBody> actualizarOrden(@Body Orden cambiar);

	@Headers({
		"Accept: application/json",
		"User-Agent: Retrofit-Sample-App"
	})
	@DELETE("/pls/apex/cvasq/svra/Ordenes/")
	Call<ResponseBody> eliminarOrden(@Query("ordenid") int ordenid);


}
