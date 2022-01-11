package src;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomReader {
    public Group read(String fileName) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(fileName));

        Element root = document.getDocumentElement();
        NodeList studentList = root.getElementsByTagName("student");
        int studentsCount = studentList.getLength();
        Group group = new Group() { 
            {
                students = new ArrayList<Student>(studentsCount);
            }
        };

        for(int i = 0; i < studentsCount; i++) {
            Node studNode = studentList.item(i);

            if(studNode.getNodeType() == Node.ELEMENT_NODE) {
                Element studElem = (Element) studNode;       

                group.students.add(new Student() {
                    {
                        firstName = studElem.getElementsByTagName("firstName").item(0).getTextContent();
                        lastName = studElem.getElementsByTagName("lastName").item(0).getTextContent();
                        groupNumber = studElem.getElementsByTagName("groupNumber").item(0).getTextContent();
                        subjects = getSubjects(studElem.getElementsByTagName("subject"));
                        avg = Double.parseDouble(studElem.getElementsByTagName("avg").item(0).getTextContent());
                    }
                });
            }
        }

        return group;
    }

    private List<Subject> getSubjects(NodeList subjects)
    {
        int len = subjects.getLength();
        List<Subject> list = new ArrayList<Subject>(len);

        for(int i = 0; i < len; i++) {
            Node subject = subjects.item(i);

            if(subject.getNodeType() == Node.ELEMENT_NODE) {
                Element subElem = (Element) subject;

                list.add(new Subject() {
                    {
                        title = subElem.getElementsByTagName("title").item(0).getTextContent();
                        mark = Integer.parseInt(subElem.getElementsByTagName("mark").item(0).getTextContent());
                    }
                });
            }
        }

        return list;
    }
}