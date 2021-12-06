package br.com.alura.loja;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {
	
	public static HttpServer server;
	
	public static void main(String[] args) {
		inicializa();
		System.out.println("Servidor rodando na porta 8080");
		
		try {
			finaliza();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void inicializa() {
		URI uri = URI.create("http://localhost:8080/");
		ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");
		
		server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
	}
	
	public static void finaliza() throws IOException {
		System.in.read();
		server.stop();
	}

}
