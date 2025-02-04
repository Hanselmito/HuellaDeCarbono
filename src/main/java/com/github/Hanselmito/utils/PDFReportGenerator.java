package com.github.Hanselmito.utils;

import com.github.Hanselmito.entities.Huella;
import com.github.Hanselmito.entities.Recomendacion;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDFReportGenerator {

    public void generateReport(String dest, List<Huella> huellas, List<Recomendacion> recomendaciones) throws IOException {
        File file = new File(dest);
        file.getParentFile().mkdirs();

        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Reporte de Huella de Carbono"));

        Table table = new Table(new float[]{4, 4, 4, 4});
        table.addHeaderCell("Fecha");
        table.addHeaderCell("Valor");
        table.addHeaderCell("Actividad");
        table.addHeaderCell("Unidad");

        for (Huella huella : huellas) {
            table.addCell(huella.getFecha().toString());
            table.addCell(huella.getValor().toString());
            table.addCell(huella.getIdActividad().getNombre());
            table.addCell(huella.getUnidad());
        }

        document.add(table);

        document.add(new Paragraph("Recomendaciones"));

        for (Recomendacion recomendacion : recomendaciones) {
            document.add(new Paragraph(recomendacion.getDescripcion()));
        }

        document.close();
    }
}