package src;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DomWriter {
    private Document _document;

    public void write(Group group, String fileName, String dtdFile) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        _document = builder.newDocument();

        Element root = _document.createElement("gorup");
        _document.appendChild(root);

        int len = group.students.size();
        for(int i = 0; i < len; i++) {
            root.appendChild(getStudent(group.students.get(i)));
        }

        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dtdFile);
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource sourse = new DOMSource(_document);

        File file = new File(fileName);

        StreamResult result = new StreamResult(file);

        transformer.transform(sourse, result);
    }

    private Node getStudent(Student student) {
        Element node = _document.createElement("student");

        node.appendChild(getInnerElement("firstName", student.firstName));
        node.appendChild(getInnerElement("lastName", student.lastName));
        node.appendChild(getInnerElement("groupNumber", student.groupNumber));
        
        int len = student.subjects.size();
        for(int i = 0; i < len; i++) {
            node.appendChild(getSubject(student.subjects.get(i)));
        }

        node.appendChild(getInnerElement("avg", Double.toString(student.avg)));
        return node;
    }

    private Node getSubject(Subject subject) {
        Element node = _document.createElement("subject");
        node.appendChild(getInnerElement("title", subject.title));
        node.appendChild(getInnerElement("mark", Integer.toString(subject.mark)));
        return node;
    }

    private Element getInnerElement(String elemName, String value) {
        Element element = _document.createElement(elemName);
        element.appendChild(_document.createTextNode(value));
        return element;
    }
}