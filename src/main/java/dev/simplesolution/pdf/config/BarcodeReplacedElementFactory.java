package dev.simplesolution.pdf.config;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextReplacedElementFactory;
import org.xhtmlrenderer.render.BlockBox;


import com.lowagie.text.Image;
import com.lowagie.text.pdf.Barcode;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.BarcodeEAN;

public class BarcodeReplacedElementFactory extends ITextReplacedElementFactory {

	public BarcodeReplacedElementFactory(ITextOutputDevice outputDevice) {
		super(outputDevice);
	}

	@Override
	public ReplacedElement createReplacedElement(LayoutContext c, BlockBox box,
			UserAgentCallback uac, int cssWidth, int cssHeight) {

		Element e = box.getElement();
		if (e == null) {
			return null;
		}

		String nodeName = e.getNodeName();
		if (nodeName.equals("img")) {
			System.out.println(e.getAttribute("type"));
			if ("code128".equals(e.getAttribute("type"))) {
				try {
					/*
					 * EAN13Writer barcodeWriter = new EAN13Writer(); BitMatrix bitMatrix;
					 * BufferedImage img=null; try { bitMatrix = barcodeWriter.encode("TEst",
					 * BarcodeFormat.EAN_13, 300, 150); img=
					 * MatrixToImageWriter.toBufferedImage(bitMatrix); } catch (WriterException ex)
					 * { // TODO Auto-generated catch block ex.printStackTrace(); }
					 */

					Barcode128 code = new Barcode128();
					code.setCode(e.getAttribute("src"));
					FSImage fsImage = new ITextFSImage(Image.getInstance(
							code.createAwtImage(Color.BLACK, Color.WHITE),
							Color.WHITE));
					/*
					 * FSImage fsImage = new ITextFSImage(Image.getInstance(
					 * code.createAwtImage(Color.BLACK, Color.WHITE), Color.WHITE));
					 */
					if (cssWidth != -1 || cssHeight != -1) {
						fsImage.scale(cssWidth, cssHeight);
					}
					return new ITextImageElement(fsImage);
				} catch (Throwable e1) {
					return null;
				}
			} 
			if ("EAN".equals(e.getAttribute("type"))) {
				try {
			
					
					/* EAN13Writer barcodeWriter = new EAN13Writer(); BitMatrix bitMatrix =
					  barcodeWriter.encode(e.getAttribute("src"), BarcodeFormat.EAN_13, 300, 150);
					 
					 BufferedImage ss = MatrixToImageWriter.toBufferedImage(bitMatrix);*/
					 
					/*QRCodeWriter barcodeWriter = new QRCodeWriter();
				    BitMatrix bitMatrix = 
				      barcodeWriter.encode(e.getAttribute("src"), BarcodeFormat.QR_CODE, 200, 200);

				    BufferedImage ss = MatrixToImageWriter.toBufferedImage(bitMatrix);*/
					 BarcodeEAN barcodeEAN = new BarcodeEAN();
				        barcodeEAN.setCode(e.getAttribute("src"));
				        barcodeEAN.setCodeType(Barcode.EAN13);
					FSImage fsImage = new ITextFSImage(Image.getInstance(
							barcodeEAN.createAwtImage(Color.BLACK, Color.WHITE),
							Color.WHITE));
					/*
					 * FSImage fsImage = new ITextFSImage(Image.getInstance(
					 * code.createAwtImage(Color.BLACK, Color.WHITE), Color.WHITE));
					 */
					if (cssWidth != -1 || cssHeight != -1) {
						fsImage.scale(cssWidth, cssHeight);
					}
					return new ITextImageElement(fsImage);
				} catch (Throwable e1) {
					return null;
				}
			} 
		}

		return super.createReplacedElement(c, box, uac, cssWidth, cssHeight);
	}

	
}