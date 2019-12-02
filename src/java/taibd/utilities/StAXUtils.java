/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.utilities;

import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author HOME
 */
public class StAXUtils {

    public static XMLStreamReader getXMLStreamReader(InputStream stream)
            throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        XMLStreamReader reader = factory.createXMLStreamReader(stream);
        return reader;
    }

    public static String getTextContentUsingStAXCursor(String elementName, XMLStreamReader reader) throws XMLStreamException {
        String result = "";
        if (reader == null) {
            return "";
        }
        if (elementName == null) {
            return "";
        }
        if (elementName.trim().isEmpty()) {
            return "";
        }

        while (reader.hasNext()) {
            int cursor = reader.getEventType();
            if (cursor == XMLStreamConstants.START_ELEMENT) {
                String tagName = reader.getLocalName();
                if (elementName.equals(tagName)) {
                    if(reader.next() == XMLStreamConstants.CHARACTERS){
                        result = reader.getText(); //info set
                        reader.next();
                    } else{
                        result = "";
                    }
                    return result;
//                    reader.nextTag();
                }
            }
            reader.next();
        }//end while
        return result;
    }

    public static XMLEventReader getXMLEventReader(InputStream is) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        XMLEventReader reader = factory.createXMLEventReader(is, "UTF-8");
        return reader;
    }

    public static XMLEventWriter getXMLEventWriter(OutputStream os) throws XMLStreamException {
        XMLOutputFactory factory = XMLOutputFactory.newFactory();
        XMLEventWriter writer = factory.createXMLEventWriter(os, "UTF-8");
        return writer;
    }

    public static String getAttributeUsingStAXIterator(XMLEventReader reader, String element,
            String namespaceURI, String attrName)
            throws XMLStreamException {
        if (reader != null) {
            while (reader.hasNext()) {
                XMLEvent event = reader.peek();
                if (event.isStartElement()) {
                    StartElement start = (StartElement) event;
                    if (start.getName().getLocalPart().equals(element)) {
                        Attribute attr = start.getAttributeByName(new QName(namespaceURI, attrName));
                        if (attr != null) {
                            String value = attr.getValue();
                            return value;
                        }
                    }
                }
                reader.nextEvent();
            }
        }
        return "";
    }

    public static String getTextContentUsingStAXIterator(XMLEventReader reader, String elementName) throws XMLStreamException {
        if (reader != null) {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement start = (StartElement) event;
                    if (start.getName().getLocalPart().equals(elementName)) {
                        event = reader.nextEvent();
                        Characters chars = (Characters) event;
                        String value = chars.getData();
                        reader.nextEvent();
                        return value;
                    }
                }
            }
        }
        return "";
    }

}
