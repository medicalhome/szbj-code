package com.yly.cdr.resources;

import static org.junit.Assert.assertTrue;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.junit.Test;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

/**
 * 字典缓存WebService接口。 当CDR Batch中字典缓存时，同时更新CDR Portal中字典缓存。
 */
public class DictionaryCacheResourceTest {

	private URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/cdr").build();
	}

	@Test
	public void put() {
		ClientResponse response = null;
		try {
			System.out.println("CDR Portal字典缓存，正在更新...");
			response = Client.create().resource(getBaseURI()).path("webservice").path("dictionarycache").put(ClientResponse.class);
			System.out.println("CDR Portal字典缓存，更新成功。");
		} catch (Exception e) {
			System.out.println("CDR Portal字典缓存，更新失败！");
			e.printStackTrace();
		}
		assertTrue("Response status should be OK. Current value is \"" + response.getClientResponseStatus() + "\"", response.getClientResponseStatus() == ClientResponse.Status.OK);
	}
}
