package com.proyect.joyeria.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import com.proyect.joyeria.dto.PruebDto;

import mx.sat.cfd33.CMetodoPago;
import mx.sat.cfd33.CMoneda;
import mx.sat.cfd33.CTipoDeComprobante;
import mx.sat.cfd33.CTipoFactor;
import mx.sat.cfd33.CUsoCFDI;
import mx.sat.cfd33.Comprobante;
import mx.sat.cfd33.Comprobante.Conceptos;
import mx.sat.cfd33.Comprobante.Conceptos.Concepto;
import mx.sat.cfd33.Comprobante.Conceptos.Concepto.Impuestos;
import mx.sat.cfd33.Comprobante.Emisor;
import mx.sat.cfd33.Comprobante.Receptor;
import mx.sat.cfd33.ObjectFactory;




@Service
public class moduloPruebaImpl  implements moduloPrueba{
	
	public Iterable<PruebDto> prueba() {
		// TODO Auto-generated method stub
		List<PruebDto> prueb = new ArrayList<PruebDto>();
		PruebDto pru = new PruebDto();
		for (Integer i =0; i<=5; i++){
			pru.setPassword("pass1");
			pru.setUserId(i);
			pru.setUserName("usuario"+i);
			prueb.add(pru);
		}
		
		
		
		return prueb;
	}
	
	public void crearComprobante() throws Exception{
		
		XMLGregorianCalendar fecha = null;
		
		try{
			fecha = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			
		}catch(DatatypeConfigurationException ex){
			Logger.getLogger(moduloPruebaImpl.class.getName()).log(null, ex);
		}
			
		ObjectFactory of = new ObjectFactory();
		Comprobante xml = of.createComprobante();
		xml.setVersion("3.3");
		xml.setSerie("5");
		xml.setFolio("123");
		xml.setFecha(fecha);
		xml.setFormaPago("En una sola excibicion");
		xml.setSubTotal(new BigDecimal("500.00"));
		xml.setDescuento(new BigDecimal("0.00"));
		xml.setMoneda(CMoneda.MXN);
		xml.setTipoCambio(new BigDecimal(1));
		xml.setTotal(new BigDecimal("500.00"));
		xml.setTipoDeComprobante(CTipoDeComprobante.I);
		xml.setMetodoPago(CMetodoPago.PUE);
		xml.setLugarExpedicion("71200");
		
		
		//Crear Emisor
		
		xml.setEmisor(createEmisor(of));
		
		//Create Receptor
		xml.setReceptor(createReceptor(of));
		
		//Conceptos
		
		xml.setConceptos(createConceptos(of));
		
		xml.setImpuestos(createImpuestos(of));
		
		//Extraer archivos .cer y .key
		
		File cer = new File("C:/Users/vgc/Desktop/COMUNICADO3.3/Kit/CertificadoFirmadoPF.cer");
		File key = new File("C:/Users/vgc/Desktop/COMUNICADO3.3/Kit/LlavePkcs8PF.key");
		
		X509Certificate x509Certificate =getX509Certificate(cer);
		String certificado= getCertificadoBase64(x509Certificate);
		String noCertificado= getCertificadoBase64(x509Certificate);
		
		xml.setCertificado(certificado);
		xml.setNoCertificado(noCertificado);
		
	}
	
	private Emisor createEmisor(ObjectFactory of){
		Emisor emisor = of.createComprobanteEmisor();
		emisor.setRfc("REFG9211186GE");
		emisor.setNombre("Emilo Pruebas");
		emisor.setRegimenFiscal("621");
		
		return emisor;
		
	}
	
	private Receptor createReceptor(ObjectFactory of){
		Receptor receptor= of.createComprobanteReceptor();
		receptor.setRfc("REFG9211186GE");
		receptor.setNombre("Publico en General");
		receptor.setUsoCFDI(CUsoCFDI.G_01);
		return receptor;
		
		
	}
	
	private Conceptos createConceptos(ObjectFactory of){
		Conceptos conceptos = of.createComprobanteConceptos();
		List<Concepto> list =conceptos.getConcepto();
		
		Concepto c1 = of.createComprobanteConceptosConcepto();
		c1.setImporte(new BigDecimal("500.00"));
		c1.setValorUnitario(new BigDecimal("500.00"));
		c1.setUnidad("Kilogramo");
		c1.setClaveUnidad("KGM"); //Proporcionada por el SAT
		c1.setCantidad(new BigDecimal("1.00"));
		c1.setClaveProdServ("50111515"); // Clave de produto y servicio del catalogo del SAT
		c1.setDescripcion("Folio de pieza");
		c1.setImpuestos(createImpuestosConcepto(of));
		list.add(c1);
		
		
	
		return conceptos;
		
	}
	
	private Impuestos createImpuestosConcepto(ObjectFactory of){
		Comprobante.Conceptos.Concepto.Impuestos imp = of.createComprobanteConceptosConceptoImpuestos();
		//Bloque Impuestos Transladados
		Comprobante.Conceptos.Concepto.Impuestos.Traslados trs = of.createComprobanteConceptosConceptoImpuestosTraslados();
		List<Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado> list=trs.getTraslado();
		
		Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado t1 = of.createComprobanteConceptosConceptoImpuestosTrasladosTraslado();
			t1.setImporte(new BigDecimal("1.00"));
			t1.setTasaOCuota(new BigDecimal("0.160000"));
			t1.setTipoFactor(CTipoFactor.TASA);
			t1.setImpuesto("002");
			t1.setBase(new BigDecimal("500.00"));
			list.add(t1);
			
			imp.setTraslados(trs);
			
			return imp;
			
	}
	
	private Comprobante.Impuestos createImpuestos(ObjectFactory of){
		Comprobante.Impuestos impu = of.createComprobanteImpuestos();
		impu.setTotalImpuestosTrasladados(new BigDecimal("80.00"));
		
		//Comprobante para los impuestos transladados
		Comprobante.Impuestos.Traslados tras = of.createComprobanteImpuestosTraslados();
		List<Comprobante.Impuestos.Traslados.Traslado> list = tras.getTraslado();
		Comprobante.Impuestos.Traslados.Traslado t1 = of.createComprobanteImpuestosTrasladosTraslado();
		t1.setImporte(new BigDecimal("80.00"));
		t1.setTasaOCuota(new BigDecimal("0.1600000"));
		t1.setTipoFactor(CTipoFactor.TASA);
		t1.setImpuesto("002");
		list.add(t1);
		
		impu.setTraslados(tras);
		
		

		
		return impu;
		
	}
	
	//Metodos de sellado
	
	X509Certificate getX509Certificate(final File certificateFile) throws CertificateException,IOException{
		FileInputStream  is = null;
		try{
			is = new FileInputStream(certificateFile);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			return (X509Certificate)cf.generateCertificate(is);
		}finally{
			if(is != null){
				is.close();
			}
		}
		
	}
	
	private String getCertificadoBase64(final X509Certificate cert ) throws CertificateEncodingException{	
		return new String(Base64.encodeBase64(cert.getEncoded()));
	}
	
	@SuppressWarnings("unused")
	private String getNoCertificadoBase64(final X509Certificate cert ) throws CertificateEncodingException{
		BigInteger serial= cert.getSerialNumber();
		byte[] sArr = serial.toByteArray();
		StringBuilder buffer = new StringBuilder();
		for(int i=0; i<sArr.length; i++){
			buffer.append((char)sArr[i]);
		}
		return buffer.toString();
	}
	

}
