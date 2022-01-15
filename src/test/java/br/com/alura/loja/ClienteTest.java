package br.com.alura.loja;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;


public class ClienteTest {
	
	private HttpServer server;
	private Client client;
	private WebTarget target;
	
	@Before
	public void inicio() {
		this.server = Servidor.inicializa();
		this.client = ClientBuilder.newClient();		
		this.target = this.client.target("http://localhost:8080");		
	}
	
	@After
	public void fim() {
		this.server.stop();
	}
	
	@Test
	public void testaConexaoComServidor() {
		WebTarget target = this.client.target("http://www.mocky.io");
		String conteudo = target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class);
		
		assertTrue(conteudo.contains("<rua>Rua Vergueiro 3185"));
	}
	
	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
		String conteudo = this.target.path("/carrinhos/1").request().get(String.class);
		Carrinho carrinho = (Carrinho)new XStream().fromXML(conteudo);
		
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
	}
	
	@Test
	public void testaAdicionaCarrinho() {
		Carrinho carrinho = new Carrinho().adiciona(new Produto(314L, "iphone 9", 3300.00, 1));
		carrinho.para("Rua Odemis, 292", "Sao Paulo");
		
		Entity<String> entity = Entity.entity(carrinho.toXML(), MediaType.APPLICATION_XML);
		
		Response response = this.target.path("/carrinhos").request().post(entity);
		
		assertEquals(201, response.getStatus());
		
		String location = response.getHeaderString("Location");
		String conteudo = this.client.target(location).request().get(String.class);
		
		assertTrue(conteudo.contains("iphone 9"));
	}

}
