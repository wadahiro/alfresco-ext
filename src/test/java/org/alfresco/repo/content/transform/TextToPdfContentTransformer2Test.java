package org.alfresco.repo.content.transform;

import java.io.File;

import org.alfresco.repo.content.filestore.FileContentReader;
import org.alfresco.repo.content.filestore.FileContentWriter;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.TransformationOptions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TextToPdfContentTransformer2Test {

	@BeforeClass
	public static void setup() {
		new File("target/outMS932.pdf").delete();
		new File("target/outMS932EmbeddedFont.pdf").delete();
		new File("target/outUTF8.pdf").delete();
	}

	@Test
	public void txt2pdfMS932() throws Exception {

		ContentReader reader = new FileContentReader(new File(
				"src/test/resources/sampleMS932.txt"));
		reader.setMimetype("text/plain");
		reader.setEncoding("MS932");

		ContentWriter writer = new FileContentWriter(new File(
				"target/outMS932.pdf"));
		writer.setMimetype("application/pdf");

		TextToPdfContentTransformer2 transformer = new TextToPdfContentTransformer2();
		transformer.transform(reader, writer, new TransformationOptions());
	}

	@Test
	public void txt2pdfUTF8() throws Exception {

		ContentReader reader = new FileContentReader(new File(
				"src/test/resources/sampleUTF8.txt"));
		reader.setMimetype("text/plain");
		reader.setEncoding("UTF-8");

		ContentWriter writer = new FileContentWriter(new File(
				"target/outUTF8.pdf"));
		writer.setMimetype("application/pdf");

		TextToPdfContentTransformer2 transformer = new TextToPdfContentTransformer2();
		transformer.transform(reader, writer, new TransformationOptions());
	}

	@Test
	public void txt2pdfMS932EmbeddedFont() throws Exception {

		ContentReader reader = new FileContentReader(new File(
				"src/test/resources/sampleMS932.txt"));
		reader.setMimetype("text/plain");
		reader.setEncoding("MS932");

		ContentWriter writer = new FileContentWriter(new File(
				"target/outMS932EmbeddedFont.pdf"));
		writer.setMimetype("application/pdf");

		TextToPdfContentTransformer2 transformer = new TextToPdfContentTransformer2();
		transformer.setFontEmbedded(true);
		transformer.setEncoding("Identity-H");
		transformer.setTrueTypeFont("lib/sazanami-mincho.ttf");

		transformer.transform(reader, writer, new TransformationOptions());
	}
}
