package dev.simplesolution.pdf.config;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

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
import org.xhtmlrenderer.swing.AWTFSImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.Barcode;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.BarcodeEAN;
import com.lowagie.text.pdf.PdfContentByte;

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
			
					 BarcodeEAN barcodeEAN = new BarcodeEAN();
					 
				        barcodeEAN.setCode("2222222220020");
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
			
			if ("QRCODE".equals(e.getAttribute("type"))) {
				try {
		
				    QRCodeWriter barcodeWriter = new QRCodeWriter();
				    BitMatrix bitMatrix = 
				      barcodeWriter.encode(e.getAttribute("src"), BarcodeFormat.QR_CODE, 250, 250);
				    ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
			        MatrixToImageConfig con = new MatrixToImageConfig( 0xFF000002 , 0xFFFFC041 ) ;
			        //BufferedImage ss = MatrixToImageWriter.toBufferedImage(bitMatrix);
			        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream,con);
			        byte[] pngData = pngOutputStream.toByteArray();
		
					final Image image = Image.getInstance(pngData);
					FSImage fsImage = new ITextFSImage(image);
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

	/*public static void createPdfEan13() throws IOException {
		  String barcode = "2222222220020";
		  BarcodeEAN pdf = new BarcodeEAN();
		  pdf.setCode(barcode);
		  pdf.setAltText("altText");
		  Image pdfImg = pdf.createAwtImage(Color.black, Color.white);
		  BufferedImage img = new BufferedImage(pdfImg.getWidth(null), pdfImg.getHeight(null),
		      BufferedImage.TYPE_INT_RGB);
		  Graphics g = img.getGraphics();
		  g.drawImage(pdfImg, 0, 0, Color.white, null);
		  OutputStream os = new BufferedOutputStream(new FileOutputStream("d:/ean-" + barcode + ".png"));
		  ImageIO.write(img, "PNG", os);
		}*/
}