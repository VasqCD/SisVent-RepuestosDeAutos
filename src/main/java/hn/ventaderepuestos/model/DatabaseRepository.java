package hn.ventaderepuestos.model;

import hn.ventaderepuestos.data.ProveedoresResponse;
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

	//https://apex.oracle.com
}
