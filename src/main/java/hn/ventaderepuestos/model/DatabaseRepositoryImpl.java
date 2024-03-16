package hn.ventaderepuestos.model;

import java.io.IOException;

import hn.ventaderepuestos.data.ProveedoresResponse;
import hn.ventaderepuestos.data.RepuestoResponse;
import retrofit2.Call;
import retrofit2.Response;

public class DatabaseRepositoryImpl {
	
	private static DatabaseRepositoryImpl INSTANCE;
	private DatabaseClient client;
	
	private DatabaseRepositoryImpl(String url, Long timeout) {
		client = new DatabaseClient(url, timeout);
	}
	
	public static DatabaseRepositoryImpl getInstance(String url, Long timeout) {
		if(INSTANCE == null) {
			synchronized (DatabaseRepositoryImpl.class) {
				if(INSTANCE == null) {
					INSTANCE = new DatabaseRepositoryImpl(url, timeout);
				}
			}
		}
		return INSTANCE;
	}
	
	public ProveedoresResponse consultarProveedor() throws IOException {
		Call<ProveedoresResponse> call = client.getDB().consultarProveedor();
		Response<ProveedoresResponse> response = call.execute();//AQUI ES DONDE SE LLAMA A LA BASE DE DATOS
		if(response.isSuccessful()){
			return response.body();
		}else {
			return null;
		}
	}

	public RepuestoResponse consultarRepuesto() throws IOException{
		Call<RepuestoResponse> call = client.getDB().consultarRepuesto();
		Response<RepuestoResponse> response = call.execute();//AQUI ES DONDE SE LLAMA A LA BASE DE DATOS
		if(response.isSuccessful()){
			return response.body();
		}else {
			return null;
		}
	}

}
