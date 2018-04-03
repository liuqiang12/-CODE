
package com.idc.cxf.isp.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.idc.cxf.isp.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AdditionYyyy_QNAME = new QName("http://service.isp.cxf.idc.com/", "addition_yyyy");
    private final static QName _AdditionYyyyResponse_QNAME = new QName("http://service.isp.cxf.idc.com/", "addition_yyyyResponse");
    private final static QName _CreateISPEventService_QNAME = new QName("http://service.isp.cxf.idc.com/", "createISPEventService");
    private final static QName _CreateISPEventServiceResponse_QNAME = new QName("http://service.isp.cxf.idc.com/", "createISPEventServiceResponse");
    private final static QName _XmlToFile_QNAME = new QName("http://service.isp.cxf.idc.com/", "xmlToFile");
    private final static QName _XmlToFileResponse_QNAME = new QName("http://service.isp.cxf.idc.com/", "xmlToFileResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.idc.cxf.isp.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AdditionYyyy }
     * 
     */
    public AdditionYyyy createAdditionYyyy() {
        return new AdditionYyyy();
    }

    /**
     * Create an instance of {@link AdditionYyyyResponse }
     * 
     */
    public AdditionYyyyResponse createAdditionYyyyResponse() {
        return new AdditionYyyyResponse();
    }

    /**
     * Create an instance of {@link CreateISPEventService }
     * 
     */
    public CreateISPEventService createCreateISPEventService() {
        return new CreateISPEventService();
    }

    /**
     * Create an instance of {@link CreateISPEventServiceResponse }
     * 
     */
    public CreateISPEventServiceResponse createCreateISPEventServiceResponse() {
        return new CreateISPEventServiceResponse();
    }

    /**
     * Create an instance of {@link XmlToFile }
     * 
     */
    public XmlToFile createXmlToFile() {
        return new XmlToFile();
    }

    /**
     * Create an instance of {@link XmlToFileResponse }
     * 
     */
    public XmlToFileResponse createXmlToFileResponse() {
        return new XmlToFileResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AdditionYyyy }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.isp.cxf.idc.com/", name = "addition_yyyy")
    public JAXBElement<AdditionYyyy> createAdditionYyyy(AdditionYyyy value) {
        return new JAXBElement<AdditionYyyy>(_AdditionYyyy_QNAME, AdditionYyyy.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AdditionYyyyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.isp.cxf.idc.com/", name = "addition_yyyyResponse")
    public JAXBElement<AdditionYyyyResponse> createAdditionYyyyResponse(AdditionYyyyResponse value) {
        return new JAXBElement<AdditionYyyyResponse>(_AdditionYyyyResponse_QNAME, AdditionYyyyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateISPEventService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.isp.cxf.idc.com/", name = "createISPEventService")
    public JAXBElement<CreateISPEventService> createCreateISPEventService(CreateISPEventService value) {
        return new JAXBElement<CreateISPEventService>(_CreateISPEventService_QNAME, CreateISPEventService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateISPEventServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.isp.cxf.idc.com/", name = "createISPEventServiceResponse")
    public JAXBElement<CreateISPEventServiceResponse> createCreateISPEventServiceResponse(CreateISPEventServiceResponse value) {
        return new JAXBElement<CreateISPEventServiceResponse>(_CreateISPEventServiceResponse_QNAME, CreateISPEventServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XmlToFile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.isp.cxf.idc.com/", name = "xmlToFile")
    public JAXBElement<XmlToFile> createXmlToFile(XmlToFile value) {
        return new JAXBElement<XmlToFile>(_XmlToFile_QNAME, XmlToFile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XmlToFileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.isp.cxf.idc.com/", name = "xmlToFileResponse")
    public JAXBElement<XmlToFileResponse> createXmlToFileResponse(XmlToFileResponse value) {
        return new JAXBElement<XmlToFileResponse>(_XmlToFileResponse_QNAME, XmlToFileResponse.class, null, value);
    }

}
