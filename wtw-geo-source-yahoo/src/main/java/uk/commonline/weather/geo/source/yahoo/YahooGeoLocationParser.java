package uk.commonline.weather.geo.source.yahoo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import uk.commonline.weather.model.Location;

public class YahooGeoLocationParser implements ErrorHandler {

    private static Logger log = Logger.getLogger(YahooGeoLocationParser.class);

    static void listNodes(Node node, String indent) {
        String nodeName = node.getNodeName();
        System.out.println(indent + nodeName + " Node, type is " + node.getClass().getName() + ":");
        System.out.println(indent + " " + node);

        NodeList list = node.getChildNodes();
        if (list.getLength() > 0) {
            System.out.println(indent + "Child Nodes of " + nodeName + " are:");
            for (int i = 0; i < list.getLength(); i++)
                listNodes(list.item(i), indent + " ");
        }
    }

    @Override
    public void error(SAXParseException spe) {
        System.out.println("Error at line " + spe.getLineNumber());
        System.out.println(spe.getMessage());
    }

    @Override
    public void fatalError(SAXParseException spe) throws SAXException {
        System.out.println("Fatal error at line " + spe.getLineNumber());
        System.out.println(spe.getMessage());
        throw spe;
    }

    public Location parsePlace(InputStream inputStream) throws Exception {

        Location location = null;

        // Process response
        Document response = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);

        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();

        // Get all search Result nodes
        NodeList nodes = (NodeList) xPath.evaluate("/place", response, XPathConstants.NODESET);
        int nodeCount = nodes.getLength();

        // iterate over search Result nodes
        for (int i = 0; i < nodeCount; i++) {
            // Get each xpath expression as a string
            String woeid = (String) xPath.evaluate("woeid", nodes.item(i), XPathConstants.STRING);
            String country = (String) xPath.evaluate("country", nodes.item(i), XPathConstants.STRING);
            String city = (String) xPath.evaluate("locality1", nodes.item(i), XPathConstants.STRING);
            String postal = (String) xPath.evaluate("postal", nodes.item(i), XPathConstants.STRING);
            String latitude = (String) xPath.evaluate("centroid/latitude", nodes.item(i), XPathConstants.STRING);
            String longitude = (String) xPath.evaluate("centroid/longitude", nodes.item(i), XPathConstants.STRING);
            location = new Location();
            location.setSourceid(woeid);
            location.setName(city);
            location.setType("city");
            location.setRegion(woeid);
            location.setCountry(country);
            location.setPostal(postal);
            location.setLatitude(Double.parseDouble(latitude));
            location.setLongitude(Double.parseDouble(longitude));
        }
        return location;
    }

    public Location parsePlaceQuery(InputStream inputStream) throws Exception {

        Location location = null;

        // Process response
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        // factory.setValidating(true);
        DocumentBuilder builder;
        Document response = null;
        builder = factory.newDocumentBuilder();
        builder.setErrorHandler(this);
        response = builder.parse(inputStream);

        // create an XPathFactory
        XPathFactory xFactory = XPathFactory.newInstance();

        // create an XPath object
        XPath xPath = xFactory.newXPath();

        // normalize text representation
        response.getDocumentElement().normalize();
        System.out.println("Root element of the doc is " + response.getDocumentElement().getNodeName() + ", doc:"
                + response.getDocumentElement().toString());

        // Get all search Result nodes
        NodeList nodes = (NodeList) xPath.compile("//Result").evaluate(response, XPathConstants.NODESET);

        int nodeCount = nodes.getLength();

        // iterate over search Result nodes
        for (int i = 0; i < nodeCount; i++) {
            // Get each xpath expression as a string
            String woeid = (String) xPath.evaluate("woeid", nodes.item(i), XPathConstants.STRING);
            String country = (String) xPath.evaluate("country", nodes.item(i), XPathConstants.STRING);
            String city = (String) xPath.evaluate("city", nodes.item(i), XPathConstants.STRING);
            String postal = (String) xPath.evaluate("uzip", nodes.item(i), XPathConstants.STRING);
            String latitude = (String) xPath.evaluate("latitude", nodes.item(i), XPathConstants.STRING);
            String longitude = (String) xPath.evaluate("longitude", nodes.item(i), XPathConstants.STRING);
            location = new Location();
            location.setSourceid(woeid);
            location.setName(city);
            location.setType("city");
            location.setRegion(woeid);
            location.setCountry(country);
            location.setPostal(postal);
            location.setLatitude(Double.parseDouble(latitude));
            location.setLongitude(Double.parseDouble(longitude));
        }
        return location;
    }

    public List<Location> parsePlaces(InputStream inputStream) throws Exception {

        List<Location> locations = new ArrayList<Location>();
        Location location = null;

        // Process response
        Document response = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);

        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();

        // Get all search Result nodes
        NodeList nodes = (NodeList) xPath.evaluate("/places/place", response, XPathConstants.NODESET);
        int nodeCount = nodes.getLength();

        // iterate over search Result nodes
        for (int i = 0; i < nodeCount; i++) {
            // Get each xpath expression as a string
            String woeid = (String) xPath.evaluate("woeid", nodes.item(i), XPathConstants.STRING);
            String country = (String) xPath.evaluate("country", nodes.item(i), XPathConstants.STRING);
            String city = (String) xPath.evaluate("locality1", nodes.item(i), XPathConstants.STRING);
            String postal = (String) xPath.evaluate("postal", nodes.item(i), XPathConstants.STRING);
            String latitude = (String) xPath.evaluate("centroid/latitude", nodes.item(i), XPathConstants.STRING);
            String longitude = (String) xPath.evaluate("centroid/longitude", nodes.item(i), XPathConstants.STRING);
            location = new Location();
            location.setName(city);
            location.setType("city");
            location.setRegion(woeid);
            location.setCountry(country);
            location.setPostal(postal);
            location.setLatitude(Double.parseDouble(latitude));
            location.setLongitude(Double.parseDouble(longitude));
            locations.add(location);
        }
        return locations;
    }

    @Override
    public void warning(SAXParseException spe) {
        System.out.println("Warning at line " + spe.getLineNumber());
        System.out.println(spe.getMessage());
    }
}
