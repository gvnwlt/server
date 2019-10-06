/**
 * XML parser for configuration parameters.
 *
 * You can use this file with your web server.
 * 
 * This maps configuration parameters to a HashMap and are retrieved
 * through the following getter methods:
 *
 * 	public String getLogFile() 
 * 	public String getDocBase() 
 * 	public String getServerName() 
 *
 * Usage:
 * 	Configuration config = new Configuration(<XML configuration file>);
 *
 *	config.getLogFile();
 *	config.getDocBase();
 *	config.getServerName();
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */

import java.io.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import java.util.Map;
import java.util.HashMap;

public class Configuration extends DefaultHandler
{
    private Map map;
    private String configurationFile;

    /* Comment Here */ 
    // Reads in a configuration file in the constructor then parses the contents. The file
    // contents are stored in a string that is checked for exceptions. 
    public Configuration(String configurationFile) throws ConfigurationException {
	this.configurationFile = configurationFile;

	map = new HashMap();

	try {        
        	// Use the default (non-validating) parser
        	SAXParserFactory factory = SAXParserFactory.newInstance();

        	// Parse the input
        	SAXParser saxParser = factory.newSAXParser();
        	saxParser.parse( new File(configurationFile), this);
	}
	catch (javax.xml.parsers.ParserConfigurationException pce) {
		throw new ConfigurationException("javax.xml.parsers.ParserConfigurationException");
	}
	catch (org.xml.sax.SAXException se) {
		throw new ConfigurationException("org.xml.sax.SAXException");
	}
	catch (java.io.IOException ioe) {
		throw new ConfigurationException("java.io.IOException");
	}
    }


	/* Comment Here */
    // This method reads in the tokenized (essential) fields of the configuration
    // file that was stored as a string. 
    public void startElement(String namespaceURI,
                             String lName, 	
                             String qName, 	
                             Attributes attrs)	
    throws SAXException
    {
        String elementName = lName; // element name
        if ("".equals(elementName)) 
		elementName = qName; // namespaceAware = false

	/* Comment Here */
        // If attrs is not found to be null in the xml file, then for each attribute 
        // stored there, the attributes get stored in the hash map. 
        if (attrs != null) {
            for (int i = 0; i < attrs.getLength(); i++) {
                String aName = attrs.getLocalName(i); // Attr name 
                if ("".equals(aName)) 
			aName = attrs.getQName(i);

		/* Comment Here */
                // Storing elements of attrs into the hashmap. 
		map.put(elementName+"."+aName,attrs.getValue(i));
            }
        }
    }

	/* Comment Here */
        // Returns the hash map value for key "logfile.log"
	public String getLogFile() {
		return (String)map.get("logfile.log");
	}

	/* Comment Here */
        // Returns the hash map value for key "context.docBase"
	public String getDocBase() {
		return (String)map.get("context.docBase");
	}

	/* Comment Here */
        // Returns the hash map value for key "webserver.title"
	public String getServerName() {
		return (String)map.get("webserver.title");
	}
}
