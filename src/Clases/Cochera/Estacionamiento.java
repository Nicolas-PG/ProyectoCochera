package Clases.Cochera;

import Clases.Comprobantes.ComprobanteEstacionamiento;
import Clases.Comprobantes.Ticket;
import Clases.Sistema.JsonUtiles;
import Clases.Sistema.PdfGenerator;
import Clases.Vehiculos.Auto;
import Clases.Vehiculos.Camioneta;
import Clases.Vehiculos.Moto;
import Clases.Vehiculos.Vehiculo;
import Excepciones.EspacioInsuficiente;
import Excepciones.MontoExcedido;
import Excepciones.PatenteNoExiste;
import Interfaces.IToJson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Esta clase define la estructura general del estacionamiento.
 * Esta contiene su nombre,un HashMap de vehiculos estacionados,
 * listado de comprobantes y recaudacion.
 * Nos permitara ingresar vehiculos, retirar vehiculos y verificar su recaudacion.
 * @author Nicolas Pulti, Ezequiel Della Santa, Luca Castelao
 * @version 20/06/2023/1.0

 */

public class Estacionamiento implements IToJson {

    private int capacidad;
    private String nombre;
    private HashMap<String, ComprobanteEstacionamiento> vehiculosEstacionados; ///Vehiculos en el momento, antes de ser retirados
    private double caja;
    private int nroComprobantesEmitidos;

    private List<Ticket> listaTickets;


    private static final double tarifaMoto = 200;
    private static final double tarifaAuto = 300;
    private static final double tarifaCamioneta = 500;



    public Estacionamiento() {
    }

    public Estacionamiento(int capacidad, String nombre) {
        this.capacidad = capacidad;
        this.nombre = nombre;
        this.vehiculosEstacionados = new HashMap<>();
        this.caja = 0;
        this.nroComprobantesEmitidos = 0;
        this.listaTickets = new ArrayList<>();

    }

    /**
     * Este metodo nos permite crear un comprobante en base al vehiculo ingresado
     * y los servicios solicitados, para tener un control de los autos estacionados.
     *
     * @param vehiculo Vehiculo a ingresar al estacionamiento.
     * @param servicio Servicios adicionales solicitados.
     * @return Retorna el comprobante con toda la informacion del ingreso a estacionamiento.
     * @throws EspacioInsuficiente Si no hay suficiente espacio para ingresar el vehiculo, arroja una excepcion.
     */

    public ComprobanteEstacionamiento ingresarVehiculo(Vehiculo vehiculo, SERVICIOS servicio) throws EspacioInsuficiente
    {
        if(this.capacidad >= vehiculo.getTamanio()){/// enviar throw de que no tenemos lugar si es mas grande el auto

            this.nroComprobantesEmitidos++;
            ComprobanteEstacionamiento autoAingresar = new ComprobanteEstacionamiento(this.nroComprobantesEmitidos, vehiculo, LocalDateTime.now(),servicio);
            this.capacidad -= vehiculo.getTamanio();

            this.vehiculosEstacionados.put(vehiculo.getPatente(),autoAingresar);

            return autoAingresar;
        }else {

            throw new EspacioInsuficiente();

        }


   }

    /**
     * Este metodo  busca un vehiculo por su patente y le permite la salida generando su ticket de facturacion,
     * al mismo tiempo que genera el ticket suma su valor a la caja y llama al metodo verificarCaja que verifica
     * que no exceda el monto permitido y sino arroja una excepcion.
     * @see Estacionamiento#devolverTicket(ComprobanteEstacionamiento)  Crea el ticket con los datos del auto
     * @see Estacionamiento#verificarCaja()  Verifica que la caja no exceda el monto.
     * @see PdfGenerator#generarFacturaPDF(Ticket)  Genera el pdf con los datos de facturacion.
     * @param patente Recibie la pantente del vehiculo que sera retirado.
     * @return Retonar un ticket con todos sus datos de facturacion.
     * @throws PatenteNoExiste Si la patente no existe  arroja una excepcion.
     */

   public Ticket salidaVehiculo(String patente) throws PatenteNoExiste
   {
        if(vehiculosEstacionados.containsKey(patente))
        {
            ComprobanteEstacionamiento comprobanteEntrada = vehiculosEstacionados.get(patente);
            Ticket ticket = devolverTicket(comprobanteEntrada);
            listaTickets.add(ticket);
            PdfGenerator pdfGenerator = new PdfGenerator();
            pdfGenerator.generarFacturaPDF(ticket);
            caja += ticket.getCostoTotal();
            verificarCaja();
            capacidad += comprobanteEntrada.getVehiculo().getTamanio();
            vehiculosEstacionados.remove(patente);

            return ticket;
        }
        else {

            throw  new PatenteNoExiste(patente);
        }

   }

    /**
     * Esta funcion crea un ticket con los datos que recibe del comprobante.
     * @see Estacionamiento#tarifaAplicar(Vehiculo) Metodo que aplica la tarifa dependiendo del tipo de vehiculo que sea
     * @param autoSalida Comprobante que dentro tiene el vehiculo y sera retirado para generar el ticket.
     * @return Retorna el ticket creado.
     */

   public Ticket devolverTicket(ComprobanteEstacionamiento autoSalida){

        Ticket ticket = new Ticket(autoSalida.getNroComprobante(), autoSalida.getVehiculo(),autoSalida.getHorarioYFechaEntrada(),
                autoSalida.getServicioAdicional(),tarifaAplicar(autoSalida.getVehiculo()));

        return ticket;

   }


    /**
     * Metodo que aplica la tarifa dependiendo del tipo de vehiculo que sea
     *
     * @param vehiculo Vehiculo con todos sus datos
     * @return Retorna la tarifa del vehiculo
     */
   public double tarifaAplicar(Vehiculo vehiculo){
        if (vehiculo instanceof Moto){

            return tarifaMoto;
        } else if (vehiculo instanceof Auto) {

            return tarifaAuto;
        }else{

            return tarifaCamioneta;
        }
   }

    /**
     * Pasa los datos del mapa a una lista.
     * @return Retorna la lista cargada con los datos
     */
   public List<ComprobanteEstacionamiento> mapaToLista(){

        List<ComprobanteEstacionamiento> comprobanteEstacionamientos = new ArrayList<>();
       Iterator it = vehiculosEstacionados.entrySet().iterator();

       String respuesta = "";
       while (it.hasNext()){


           Map.Entry aux = (Map.Entry) it.next();
           comprobanteEstacionamientos.add((ComprobanteEstacionamiento) aux.getValue());



       }

       return comprobanteEstacionamientos;

   }

    /**
     * Este metodo recorre la lista y va concatenando sus datos en un string.
     * @see Estacionamiento#mapaToLista()
     * @return Retorna el string concatenado con todos los datos.
     */
   public String listarComprobantes(){

        List<ComprobanteEstacionamiento> listaComprobantes = mapaToLista();
        String respuesta = "";

        for (ComprobanteEstacionamiento c : listaComprobantes){


            respuesta += " "  + c.toString() + "\n";

        }

        return respuesta;
   }


    /**
     * Este metodo verifica que la caja no exceda el monto permitido.
     */
   public void verificarCaja(){

        if (caja >= 100000){

            try {
                throw  new MontoExcedido(caja);

            }catch (MontoExcedido e){

                System.out.println(e.getMessage());

            }

        }
   }

    /**
     * Este metodo setea la caja en 0.
     */
   public void cerrarCaja(){
        caja = 0;
   }

    /**
     * Este metodo pasa todos los datos del estacionamiento a JSON.
     * @see Estacionamiento#mapaToLista()
     * @return Retorna el JSONObjetc con todos los datos del estacionamiento.
     * @throws JSONException
     */

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray arrayComprobante = new JSONArray();
        JSONArray arrayTicket = new JSONArray();

        List<ComprobanteEstacionamiento> comprobantes = mapaToLista();

        jsonObject.put("nombre",nombre);
        jsonObject.put("capacidad",capacidad);
        jsonObject.put("caja",caja);
        jsonObject.put("nroComprobantesEmitidos",nroComprobantesEmitidos);
        jsonObject.put("tarifaMoto",tarifaMoto);
        jsonObject.put("tarifaAuto",tarifaAuto);
        jsonObject.put("tarifaCamioneta",tarifaCamioneta);

        for (ComprobanteEstacionamiento comprobante : comprobantes){
            arrayComprobante.put(comprobante.toJson());
        }

        jsonObject.put("comprobantes",arrayComprobante);

        for (Ticket ticket : listaTickets){
            arrayTicket.put(ticket.toJson());
        }

        jsonObject.put("tickets",arrayTicket);

        return jsonObject;

    }

    /**
     * Graba un JSON en un archivo.
     * @param json
     * @param nombre Nombre del archivo.
     */

    public  void guardarJsonEstacionamiento (JSONObject json ,String nombre){

        JsonUtiles.grabar(json,nombre);

    }

    /**
     * Esta funcion trae del archivo el JSON con todos los datos del estacionamiento.
     * @param archivo Nombre del archivo.
     * @see JsonUtiles#leer(String)
     * @see Estacionamiento#vehiculoPorTamanio(JSONObject)
     * @see Estacionamiento#tipoServicio(JSONObject)
     * @throws JSONException
     */
    public void backupEstacionamiento(String archivo) throws JSONException {

        JSONObject jsonObject = new JSONObject(JsonUtiles.leer(archivo));

        this.capacidad = jsonObject.getInt("capacidad");
        this.nombre = jsonObject.getString("nombre");
        this.nroComprobantesEmitidos = jsonObject.getInt("nroComprobantesEmitidos");
        this.caja=jsonObject.getDouble("caja");
        JSONArray mapa = jsonObject.getJSONArray("comprobantes");
        for (int i=0; i<mapa.length(); i++){

            JSONObject aux = mapa.getJSONObject(i);
            ComprobanteEstacionamiento comprobante = new ComprobanteEstacionamiento();
            comprobante.setNroComprobante(aux.getInt("nroComprobante"));

            String stringAux = aux.getString("horarioYFechaEntrada");
            comprobante.setHorarioYFechaEntrada(comprobante.stringAFecha(stringAux)); // pasa de una fecha el granpa se la saco de la galera

            JSONObject vehiculo = aux.getJSONObject("vehiculo");
            comprobante.setVehiculo(vehiculoPorTamanio(vehiculo));

            JSONObject servicio = aux.getJSONObject("servicio");
            comprobante.setServicioAdicional(tipoServicio(servicio));

            vehiculosEstacionados.put(comprobante.getVehiculo().getPatente(),comprobante);

        }

        JSONArray ticket = jsonObject.getJSONArray("tickets");

        for (int i=0; i<ticket.length(); i++){

            JSONObject ticketJson = ticket.getJSONObject(i);
            Ticket ticketSetear = new Ticket();

            ticketSetear.setNroComprobante(ticketJson.getInt("nroComprobante"));
            ticketSetear.setTarifaVehiculo(ticketJson.getDouble("tarifaVehiculo"));
            ticketSetear.setCostoTotal(ticketJson.getDouble("costoTotal"));
            ticketSetear.setHorarioYFechaEntrada(ticketSetear.stringAFecha(ticketJson.getString("horarioYFechaEntrada")));
            ticketSetear.setHorarioSalida(ticketSetear.stringAFecha(ticketJson.getString("horarioSalida")));

            JSONObject vehiculoTicket = ticketJson.getJSONObject("vehiculo");
            ticketSetear.setVehiculo(vehiculoPorTamanio(vehiculoTicket));

            JSONObject servicioTicket = ticketJson.getJSONObject("servicio");
            ticketSetear.setServicioAdicional(tipoServicio(servicioTicket));

            this.listaTickets.add(ticketSetear);
        }


    }

    /**
     *Esta funcion recibe un JSONOject con los datos del vehiculo y dependiendo su tamaño
     * retorna un tipo de vehiculo.
     *
     * @param vehiculo Objeto JSON con los datos deol vehiculo
     * @return Instancia de vehiculo
     * @throws JSONException
     */
    public Vehiculo vehiculoPorTamanio(JSONObject vehiculo) throws JSONException {

        if (vehiculo.getDouble("tamanio") == 0.5){

            Moto moto = new Moto(vehiculo.getString("patente"),vehiculo.getString("modelo"),vehiculo.getString("marca"));
            return moto;
        } else if (vehiculo.getDouble("tamanio") == 1.0) {

            Auto auto = new Auto(vehiculo.getString("patente"),vehiculo.getString("modelo"),vehiculo.getString("marca"));
            return auto;

        } else if (vehiculo.getDouble("tamanio") == 2.0) {

            Camioneta camioneta = new Camioneta(vehiculo.getString("patente"),vehiculo.getString("modelo"),vehiculo.getString("marca"));
            return camioneta;

        }else {
            return null;
        }


    }

    public int getCapacidad() {
        return capacidad;
    }


    public boolean patenteValida(String patente){


        return vehiculosEstacionados.containsKey(patente);
    }

    /**
     * Esta funcion recibe un JSONObject con los datos del servicio adicional
     * y verifica que tipo de servicio es con un equals y lo retorna
     * @param servicio JSONObject con los datos del servicio
     * @return Tipo de servicio
     * @throws JSONException
     */
    public SERVICIOS tipoServicio(JSONObject servicio) throws JSONException {
        if (servicio.getString("info").equals("Cambio aceite")){

            return SERVICIOS.CAMBIOACEITE;
        } else if (servicio.getString("info").equals("Aspirado")) {

            return SERVICIOS.ASPIRADO;

        } else if (servicio.getString("info").equals("Lavado")) {

            return SERVICIOS.LAVADO;

        } else if (servicio.getString("info").equals("Completo")) {

            return SERVICIOS.COMPLETO;

        } else if (servicio.getString("info").equals("Sin servicio")) {

            return SERVICIOS.NINGUNO;
        }else {

            return null;
        }
    }

    public double getCaja() {
        return caja;
    }



}
