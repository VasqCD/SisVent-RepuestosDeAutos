package hn.ventaderepuestos.model;

import hn.ventaderepuestos.data.ProveedoresResponse;
import hn.ventaderepuestos.data.RepuestoResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

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


}
