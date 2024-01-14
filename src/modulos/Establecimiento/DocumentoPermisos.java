package modulos.Establecimiento;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Image;
import modulos.Alertas.GestionNotificaciones;
import modulos.Alertas.Notificacion;
import modulos.Usuario.PropietarioEstablecimiento;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DocumentoPermisos {
    private Notificacion notificacion;
    public void generarDocumentoPDF(PropietarioEstablecimiento propietarioEstablecimiento, Establecimiento establecimiento, boolean autorizado) {
        String fechaHora = obtenerFechaHoraActual();


        String estado = autorizado ? "Autorizacion" : "Negacion";
        String nombreArchivo = obtenerNombreArchivo(estado, establecimiento.getRuc(), fechaHora);

        crearDirectorioSiNoExiste();

        generarPDF(estado, autorizado, establecimiento.getRuc(), fechaHora, nombreArchivo);

        if(!GestionNotificaciones.getInstance().isNotificacionExistente(propietarioEstablecimiento.getCorreo(), estado + " del establecimiento con corte de fecha " + fechaHora)){
            GestionNotificaciones.getInstance().agregarNotificacion(propietarioEstablecimiento.getCorreo(), estado + " del establecimiento con corte de fecha " + fechaHora);
            notificacion = new Notificacion();
            notificacion.attachFile(nombreArchivo);
            notificacion.createEmail(notificacion.getDestinatariosNotificacion(propietarioEstablecimiento.getCorreo()), "Notificacion gestion de establecimientos y mascotas", estado + " del establecimiento con corte de fecha " + fechaHora);
            notificacion.sendEmail();
        }


    }

    private String obtenerFechaHoraActual() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

    private String obtenerNombreArchivo(String estado, String ruc, String fechaHora) {
        return System.getProperty("user.dir") + File.separator + "Permisos" + File.separator +
                estado + "_de_permiso_" + ruc + "_" + fechaHora.replaceAll("[/: ]", "_") + ".pdf";
    }

    private void crearDirectorioSiNoExiste() {
        String nombreDirectorio = System.getProperty("user.dir") + File.separator + "Permisos";
        File directorio = new File(nombreDirectorio);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
    }

    private void generarPDF(String estado, boolean autorizado, String ruc, String fechaHora, String nombreArchivo) {
        Document documento = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();

            agregarLogo(documento);
            agregarTitulo(documento);
            agregarFechaHora(documento, fechaHora);
            agregarTextoPrincipal(documento, autorizado, ruc);
            agregarTextoAgradecimiento(documento);

            documento.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void agregarTitulo(Document documento) throws DocumentException {
        Font tituloFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 20, BaseColor.BLACK);
        Paragraph titulo = new Paragraph("Solicitud de Permisos de Funcionamiento", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        documento.add(titulo);
    }

    private void agregarFechaHora(Document documento, String fechaHora) throws DocumentException {
        String FechaHoraTexto = "Cuenca, " + fechaHora +"\n";

        Font FechaHora = FontFactory.getFont(FontFactory.TIMES_ITALIC, 12, BaseColor.BLACK);
        Paragraph paragraph = new Paragraph(FechaHoraTexto, FechaHora);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.setSpacingAfter(10);

        documento.add(paragraph);

    }

    private void agregarTextoPrincipal(Document documento, boolean autorizado, String ruc) throws DocumentException {
        String textoPrincipal;
        if (autorizado) {
            textoPrincipal = "\n\nNos complace informarle que su solicitud de permisos de funcionamiento para el establecimiento de RUC " + ruc +" ha sido aprobada. Después de revisar los documentos, confirmamos que cumple con todas las regulaciones aplicables.\n" +
                    "\n" + "La aprobación es efectiva a partir de la fecha de esta carta. Le recordamos la importancia de cumplir continuamente con las normativas locales y mantener actualizados los documentos requeridos. Estamos aquí para ayudarle en caso de preguntas adicionales.";
        } else {
            textoPrincipal = "\n\nLamentamos informarle que, después de revisar detenidamente su solicitud de permisos de funcionamiento para el establecimiento de RUC " + ruc + ", no podemos otorgar la aprobación en este momento.\n" +
                    "\n" + "Durante la evaluación, se identificaron ciertos aspectos que requieren ajustes o documentación adicional. Le proporcionaremos detalles específicos sobre los puntos que necesitan ser abordados en una comunicación separada.\n" +
                    "\n" + "Le instamos a corregir las deficiencias señaladas y a presentar una solicitud revisada tan pronto como sea posible. Estamos disponibles para brindar orientación y aclarar cualquier pregunta que pueda tener sobre el proceso de solicitud.\n";
        }
        Font textoPrincipalFont = FontFactory.getFont(FontFactory.TIMES, 12, BaseColor.BLACK);
        Paragraph paragraph = new Paragraph(textoPrincipal, textoPrincipalFont);
        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
        paragraph.setSpacingAfter(10);
        documento.add(paragraph);
    }

    private void agregarTextoAgradecimiento(Document documento) throws DocumentException {
        String agradecimiento = "\n\nAgradecemos su comprensión y cooperación.";
        Font agradecimientoFont = FontFactory.getFont(FontFactory.TIMES_ITALIC, 12, BaseColor.BLACK);
        Paragraph agradecimientoParagraph = new Paragraph(agradecimiento, agradecimientoFont);
        agradecimientoParagraph.setAlignment(Element.ALIGN_JUSTIFIED);
        agradecimientoParagraph.setSpacingAfter(10);
        documento.add(agradecimientoParagraph);
    }


    private void agregarLogo(Document documento) throws DocumentException{

        try {
            // Ruta del archivo PNG del logo (ajusta la ruta según la ubicación de tu archivo)
            String rutaLogo = "src/vistas/Complements/CGA_Logo.png";
            Image logo = Image.getInstance(rutaLogo);
            logo.scaleToFit(100, 70);  // Ajusta el tamaño del logo según tus necesidades
            documento.add(logo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
