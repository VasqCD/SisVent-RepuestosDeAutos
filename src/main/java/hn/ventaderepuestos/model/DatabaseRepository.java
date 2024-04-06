package hn.ventaderepuestos.model;

import hn.ventaderepuestos.data.Proveedor;
import hn.ventaderepuestos.data.ProveedoresResponse;
import hn.ventaderepuestos.data.Repuesto;
import hn.ventaderepuestos.data.RepuestoResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DatabaseRepository {
	
	@Headers({
		"Accept: application/json",
		"User-Agent: Retrofit-Sample-App"
	})
	@GET("/pls/apex/cvasq/svra/Proveedor")
	Call<ProveedoresResponse> consultarProveedor();
	//https://apex.oracle.com/pls/apex/cvasq/svra/Proveedor
	
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
	@POST("/pls/apex/cvasq/svra/Proveedor")
	Call<ResponseBody> crearProveedor(@Body Proveedor nuevo);


}
