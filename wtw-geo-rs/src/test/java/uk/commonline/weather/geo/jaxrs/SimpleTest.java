package uk.commonline.weather.geo.jaxrs;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SimpleTest /* extends JerseyTest */{

    @BeforeClass
    public static void startServer() throws Exception {
        // EmbeddedServer.startIfRequired();
    }

    // @Override
    // protected Application configure() {
    // return new GeoApplication();
    // }

    @Test
    public void dummy() throws Exception {

    }

    // @Test
    public void test() throws Exception {

        // URI uri =
        // UriBuilder.fromUri("http://localhost/").port(8080).path("wtwgeo").build();
        Server server = new Server(8080);
        Context root = new Context(server, "wtwgeo", Context.SESSIONS);
        root.addServlet(new ServletHolder(new ServletContainer(new GeoApplication())), "/webresources");
        server.start();
        // Create an HTTP server listening at port 8282
        // HttpServer server = HttpServer.create(new
        // InetSocketAddress(uri.getPort()), 0);
        // Create a handler wrapping the JAX-RS application
        // HttpHandler handler =
        // RuntimeDelegate.getInstance().createEndpoint(new GeoApplication(),
        // HttpHandler.class);
        // Map JAX-RS handler to the server root
        // server.createContext(uri.getPath(), handler);
        // System.out.println("uri.getPath():" + uri);
        // Start the server/
        try {
            Thread.sleep(100000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        Client client = ClientBuilder.newClient();
        // long region = geoLocationClient.getRegion(0, 0);
        // assertEquals("Invalid region", 1, region);

        // Valid URIs
        // assertEquals(200,
        // client.target("http://localhost:8080/wtwgeo/webresources/location/region/lat/0/long/0").request().get().getStatus());
        // assertEquals(200,
        // client.target("http://localhost:8282/customer/1234").request().get().getStatus());
        // assertEquals(200,
        // client.target("http://localhost:8282/customer?zip=75012").request().get().getStatus());
        // assertEquals(200,
        // client.target("http://localhost:8282/customer/search;firstname=Antonio;surname=Goncalves").request().get().getStatus());

        // Invalid URIs
        // assertEquals(404,
        // client.target("http://localhost:8282/customer/AGONCAL").request().get().getStatus());
        // assertEquals(404,
        // client.target("http://localhost:8282/customer/dummy/1234").request().get().getStatus());

        // Stop HTTP server
        // server.stop(0);
        // final String hello = target("hello").request().get(String.class);
        // assertEquals("Hello World!", hello);
    }

}