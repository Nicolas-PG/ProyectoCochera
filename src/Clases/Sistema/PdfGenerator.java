package Clases.Sistema;

import Clases.Comprobantes.Ticket;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.FileOutputStream;

/**
 * Esta clase implementa la libreria IText para poder generar un archivo PDF
 *
 */
public class PdfGenerator extends PdfPageEventHelper {


    public PdfGenerator() {
    }

    /**
     * Este metodo recibe un ticket y crea un archivo PDF con sus datos agregandolos linea por linea.
     * @see PdfGenerator#onEndPage(PdfWriter, Document)
     * @see PdfGenerator#agregarCelda(PdfPTable, String, String)
     * @param ticket Ticket con sus datos cargados
     */
    public void generarFacturaPDF(Ticket ticket) {

        Document document = new Document();

        try {

            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("factura.pdf"));
            pdfWriter.setPageEvent(this);

            document.open();

            Paragraph encabezado = new Paragraph("Factura");
            encabezado.setAlignment(Element.ALIGN_CENTER);
            document.add(encabezado);

            LineSeparator separador = new LineSeparator();
            document.add(new Chunk(separador));


            document.add(new Paragraph("Número de comprobante: " + ticket.getNroComprobante()));
            document.add(Chunk.NEWLINE);

            document.add(new Chunk("Vehículo: "));

            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(50);
            table.setHorizontalAlignment(Element.ALIGN_LEFT);

            agregarCelda(table, "Patente", ticket.getVehiculo().getPatente());
            agregarCelda(table, "Modelo", ticket.getVehiculo().getModelo());
            agregarCelda(table, "Marca", ticket.getVehiculo().getMarca());


            document.add(table);


            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Servicio adicional: " + ticket.getServicioAdicional().getInfo() +" "+ "$"+ticket.getServicioAdicional().getCosto() ));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Tarifa del vehículo: " + "$"+ticket.getTarifaVehiculo()));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Fecha y hora de entrada: " + ticket.getHorarioYFechaEntrada().toString()));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Fecha y hora de salida: " + ticket.getHorarioSalida().toString()));
            document.add(Chunk.NEWLINE);
            document.add(new Chunk(separador));
            document.add(new Paragraph("Costo total: " + "$"+ticket.getCostoTotal() + "     " + ticket.tarifaDiferentesMoneda(ticket.getCostoTotal())));
            document.add(new Chunk(separador));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            document.close();
        }
    }



    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();


        Phrase footer = new Phrase("© 2023 Grandpa's - Página " + writer.getPageNumber(), FontFactory.getFont(FontFactory.HELVETICA, 14));
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer, (document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 10, 0);
    }

    /**
     * Agrega celdas a una tabla con los datos que recibe por parametro.
     * @param table Tabla donde agregara los datos,
     * @param etiqueta Nombre de los datos,
     * @param valor Datos que agregara.
     */
    private static void agregarCelda(PdfPTable table, String etiqueta, String valor) {
        PdfPCell celdaEtiqueta = new PdfPCell(new Paragraph(etiqueta));
        PdfPCell celdaValor = new PdfPCell(new Paragraph(valor));
        table.addCell(celdaEtiqueta);
        table.addCell(celdaValor);
    }

}

