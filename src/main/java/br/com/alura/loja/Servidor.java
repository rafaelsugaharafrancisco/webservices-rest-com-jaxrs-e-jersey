package br.com.alura.loja;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {
	
	public static void main(String[] args) throws IOException {
		HttpServer server = inicializa();
		
		System.out.println("Servidor rodando na porta 8080");
		System.in.read(); // Leitura da tecla ENTER
		
		server.stop(); 
	}
	
	public static HttpServer inicializa() {
		URI uri = URI.create("http://localhost:8080/");
		
		ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");
		
		return GrizzlyHttpServerFactory.createHttpServer(uri, config);
	}
}
