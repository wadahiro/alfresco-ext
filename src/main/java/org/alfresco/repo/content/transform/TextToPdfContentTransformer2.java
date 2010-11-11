package org.alfresco.repo.content.transform;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.TransformationOptions;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class TextToPdfContentTransformer2 extends AbstractContentTransformer2 {
	private float fontSize = 11;// default
	private String fontName = "HeiseiKakuGo-W5";// default
	private String encoding = "UniJIS-UCS2-H";// default
	private boolean fontEmbedded = BaseFont.NOT_EMBEDDED;// default

	public TextToPdfContentTransformer2() {
	}

	public void setTrueTypeFont(String fontName) {
		this.fontName = fontName;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setFontEmbedded(boolean fontEmbedded) {
		this.fontEmbedded = fontEmbedded;
	}

	/**
	 * Only supports Text to PDF
	 */
	public boolean isTransformable(String sourceMimetype,
			String targetMimetype, TransformationOptions options) {
		if ((!MimetypeMap.MIMETYPE_TEXT_PLAIN.equals(sourceMimetype)
				&& !MimetypeMap.MIMETYPE_TEXT_CSV.equals(sourceMimetype) && !MimetypeMap.MIMETYPE_XML
				.equals(sourceMimetype))
				|| !MimetypeMap.MIMETYPE_PDF.equals(targetMimetype)) {
			// only support (text/plain OR text/csv OR text/xml) to
			// (application/pdf)
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected void transformInternal(ContentReader reader,
			ContentWriter writer, TransformationOptions options)
			throws Exception {

		BufferedReader in = null;
		OutputStream os = null;
		Document document = null;
		try {
			String line = null;
			Rectangle pagesize = PageSize.A4;
			Font f = new Font(BaseFont.createFont(fontName, encoding,
					fontEmbedded), fontSize);
			document = new Document(pagesize, 72, 36, 36, 36);

			in = new BufferedReader(new InputStreamReader(
					reader.getContentInputStream(), reader.getEncoding()));

			os = writer.getContentOutputStream();

			PdfWriter.getInstance(document, os);
			document.open();
			while ((line = in.readLine()) != null) {
				if ("".equals(line)) {
					document.add(new Paragraph(12, "\r\n"));
				} else {
					document.add(new Paragraph(12, line, f));
				}
			}

		} finally {
			if (document != null) {
				document.close();
			}
			if (in != null) {
				try {
					in.close();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}
}
