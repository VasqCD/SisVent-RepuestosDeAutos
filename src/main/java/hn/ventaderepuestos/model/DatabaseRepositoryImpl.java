package hn.ventaderepuestos.model;

import java.io.IOException;

import hn.ventaderepuestos.data.*;
import okhttp3.ResponseBody;
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

	public RepuestoResponse consultarRepuesto() throws IOException{
		Call<RepuestoResponse> call = client.getDB().consultarRepuesto();
		Response<RepuestoResponse> response = call.execute();//AQUI ES DONDE SE LLAMA A LA BASE DE DATOS
		if(response.isSuccessful()){
			return response.body();
		}else {
			return null;
		}
	}
	
	public boolean crearRepuesto(Repuesto nuevo) throws IOException{
		Call<ResponseBody> call = client.getDB().crearRepuesto(nuevo);
		Response<ResponseBody> response = call.execute();//AQUI ES DONDE SE LLAMA A LA BASE DE DATOS
		return response.isSuccessful();
	}

	public boolean actualizarRepuesto(Repuesto cambiar) throws IOException{
		Call<ResponseBody> call = client.getDB().actualizarRepuesto(cambiar);
		Response<ResponseBody> response = call.execute();//AQUI ES DONDE SE LLAMA A LA BASE DE DATOS
		return response.isSuccessful();
	}

	public boolean eliminarRepuesto(int repuestoid) throws IOException{
		Call<ResponseBody> call = client.getDB().eliminarRepuesto(repuestoid);
		Response<ResponseBody> response = call.execute();
		return response.isSuccessful();
	}


	//PARA PROVEEDORES
	public ProveedoresResponse consultarProveedor() throws IOException {
		Call<ProveedoresResponse> call = client.getDB().consultarProveedor();
		Response<ProveedoresResponse> response = call.execute();//AQUI ES DONDE SE LLAMA A LA BASE DE DATOS
		if(response.isSuccessful()){
			return response.body();
		}else {
			return null;
		}
	}
	
	public boolean crearProveedor(Proveedor nuevo) throws IOException{
		Call<ResponseBody> call = client.getDB().crearProveedor(nuevo);
		Response<ResponseBody> response = call.execute();//AQUI ES DONDE SE LLAMA A LA BASE DE DATOS
		return response.isSuccessful();
	}

	public boolean actualizarProveedor(Proveedor cambiar) throws IOException{
		Call<ResponseBody> call = client.getDB().actualizarProveedor(cambiar);
		Response<ResponseBody> response = call.execute();//AQUI ES DONDE SE LLAMA A LA BASE DE DATOS
		return response.isSuccessful();
	}

	public boolean eliminarProveedor(int proveedorid) throws IOException{
		Call<ResponseBody> call = client.getDB().eliminarProveedor(proveedorid);
		Response<ResponseBody> response = call.execute();
		return response.isSuccessful();
	}

	public GaleriaRepuestoResponse consultarGaleriaRepuesto() throws IOException{
		Call<GaleriaRepuestoResponse> call = client.getDB().consultarGaleriaRepuesto();
		Response<GaleriaRepuestoResponse> response = call.execute();//AQUI ES DONDE SE LLAMA A LA BASE DE DATOS
		if(response.isSuccessful()){
			return response.body();
		}else {
			return null;
		}
	}

}
