package com.github.Hanselmito.utils;

import com.github.Hanselmito.entities.Habito;
import com.github.Hanselmito.entities.Huella;
import com.github.Hanselmito.entities.Recomendacion;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PDFReportGenerator {

    public void generateReport(String dest, List<Huella> huellas, List<Recomendacion> recomendaciones, List<Habito> habitos) throws IOException {
        File file = new File(dest);
        file.getParentFile().mkdirs();

        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Reporte de Huella de Carbono"));

        Map<String, List<Huella>> huellasPorActividad = huellas.stream()
                .collect(Collectors.groupingBy(huella -> huella.getIdActividad().getNombre()));

        for (Map.Entry<String, List<Huella>> entry : huellasPorActividad.entrySet()) {
            List<Huella> huellasDeActividad = entry.getValue();

            Table table = new Table(new float[]{1, 4, 4, 4, 4});
            table.setWidth(100);

            table.addHeaderCell("Imagen");
            table.addHeaderCell("Usuario");
            table.addHeaderCell("Actividad");
            table.addHeaderCell("Valor");
            table.addHeaderCell("Fecha");

            for (Huella huella : huellasDeActividad) {
                Image image = new Image(ImageDataFactory.create(getClass().getResource("/com/github/Hanselmito/Icon/Huella.png")));
                table.addCell(new Cell().add(image.setAutoScale(true)));

                table.addCell(new Cell().add(new Paragraph(huella.getIdUsuario().getNombre())));
                table.addCell(new Cell().add(new Paragraph(huella.getIdActividad().getNombre())));
                table.addCell(new Cell().add(new Paragraph(huella.getValor().toString() + " " + huella.getUnidad())));
                table.addCell(new Cell().add(new Paragraph(huella.getFecha().toString())));
            }

            document.add(table);
        }

        document.add(new Paragraph("Recomendaciones"));

        for (Habito habito : habitos) {
            String habitoInfo = String.format("Actividad: %s, Frecuencia: %d, Tipo: %s, Última Fecha: %s",
                    habito.getIdActividad().getNombre(),
                    habito.getFrecuencia(),
                    habito.getTipo().toString(),
                    habito.getUltimaFecha().toString());
            document.add(new Paragraph(habitoInfo));

            List<Recomendacion> recomendacionesPorHabito = recomendaciones.stream()
                    .filter(recomendacion -> recomendacion.getIdCategoria().getId().equals(habito.getIdActividad().getIdCategoria().getId()))
                    .collect(Collectors.toList());

            int index = 1;
            for (Recomendacion recomendacion : recomendacionesPorHabito) {
                String recomendacionInfo = String.format("%d- %s", index, recomendacion.getDescripcion());
                document.add(new Paragraph(recomendacionInfo));
                index++;
            }
        }

        document.close();
    }
}