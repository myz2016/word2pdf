package com.fotic.it.support.word2pdf.api.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "WordForPDFWebService", targetNamespace = "http://xfire.webservice.com/WordForPDFWebService")
public interface WordForPDFWebService {
	@WebResult(name = "out", targetNamespace = "http://xfire.webservice.com/WordForPDFWebService")
	/*@RequestWrapper(localName = "CheckWordForPDF",
			targetNamespace = "http://xfire.webservice.com/WordForPDFWebService",
			className = "com.fotic.it.support.check.api.webservice.ws.service.CheckWordForPDF")*/
//	@WebMethod(action = "urn:CheckWordForPDF")
	@WebMethod
	/*@ResponseWrapper(localName = "CheckWordForPDFResponse",
			targetNamespace = "http://xfire.webservice.com/WordForPDFWebService/",
			className = "com.fotic.it.support.check.api.webservice.ws.service.SayHelloResponse")*/
	public String CheckWordForPDF(
            @WebParam(name = "in0", targetNamespace = "http://xfire.webservice.com/WordForPDFWebService") String sys,
            @WebParam(name = "in1", targetNamespace = "http://xfire.webservice.com/WordForPDFWebService") String id,
            @WebParam(name = "in2", targetNamespace = "http://xfire.webservice.com/WordForPDFWebService") String inputPath,
            @WebParam(name = "in3", targetNamespace = "http://xfire.webservice.com/WordForPDFWebService") String inputFileName,
            @WebParam(name = "in4", targetNamespace = "http://xfire.webservice.com/WordForPDFWebService") String outputFileName) throws Exception;

	@RequestWrapper(localName = "IstyleWordPDF",
			targetNamespace = "http://xfire.webservice.com/WordForPDFWebService",
			className = "com.fotic.it.support.check.api.webservice.ws.service.IstyleWordPDF")
	@WebResult(name="out", targetNamespace = "http://xfire.webservice.com/WordForPDFWebService")
	@WebMethod(action = "urn:IstyleWordPDF")
	@ResponseWrapper(localName = "IstyleWordPDFResponse",
			targetNamespace = "http://xfire.webservice.com/WordForPDFWebService/",
			className = "com.fotic.it.support.check.api.webservice.ws.service.IstyleWordPDFResponse")
	public String IstyleWordPDF(
			@WebParam(name = "in0", targetNamespace = "http://xfire.webservice.com/WordForPDFWebService") String inputPath,
			@WebParam(name = "in1", targetNamespace = "http://xfire.webservice.com/WordForPDFWebService") String inputFileName,
			@WebParam(name = "in2", targetNamespace = "http://xfire.webservice.com/WordForPDFWebService") String outputFileName ) throws Exception;
//	public String IstyleWordPDF(@WebParam(name = "xfire") IXfire xfire);
}
