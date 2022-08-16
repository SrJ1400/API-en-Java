/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package apirest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import suprem_tournaments.TorneosIndividuales;
import suprem_tournaments.TorneosIndividualesJpaController;

/**
 * REST Web Service
 *
 * @author Jorge
 */
// SWAGGER -> ServiceREST TorneosIndividuales
@OpenAPIDefinition(
        info = @Info(
                description = "APIREST sobre la BD de suprem tournaments",
                version = "1.0.0",
                title = "Swagger Suprem Tournaments"
        )
)
@Tag(name = "TorneosIndividuales",
        description = "Datos de los torneos individuales")
@Server(url = "/api/supremtournaments") // para el Try it
// API -> ServiceREST torneoindividual
@Path("torneoindividual")
public class ServiceRESTTorneoIndividual {

    @Context
    private HttpServletRequest httpRequest;
    private static final String SECRET_KEY = "my_super_secret_key";
    private static final String PERSISTENCE_UNIT = "API_SupremTournamentsPU";

    /**
     * Creates a new instance of GenericResource
     */
    public ServiceRESTTorneoIndividual() {
    }

    // Metodos y propiedades necesarias para seguridad
        
   public JsonObject validateJWT(String token) {
        JsonObject json = null;
        JsonObjectBuilder jsonOB = Json.createObjectBuilder();
        try {
            String[] chunks = token.split("\\.");
            if (chunks.length == 3) {
                Base64.Decoder decoder = Base64.getDecoder();
                String header = new String(decoder.decode(chunks[0]));
                String payload = new String(decoder.decode(chunks[1]));
                String signature = chunks[2];

                JSONParser parser = new JSONParser();
                JSONObject jsonHeader = (JSONObject) parser.parse(header);
                String algoritmo = "";
                if (jsonHeader.containsKey("alg")) {
                    algoritmo = jsonHeader.get("alg").toString();
                }
                String tipo = "";
                if (jsonHeader.containsKey("typ")) {
                    tipo = jsonHeader.get("typ").toString();
                }

                if ((algoritmo.equals("HS256")) && (tipo.equals("JWT"))) {
                    try {
                        Claims claims = Jwts.parser()
                                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                                .parseClaimsJws(token)
                                .getBody();

                        // https://es.wikipedia.org/wiki/JSON_Web_Token
                        jsonOB.add("Subject", claims.getSubject());
                        jsonOB.add("Issuer", claims.getIssuer());
                        jsonOB.add("IssuedAt", claims.getIssuedAt().toString());
                        jsonOB.add("Expiration", claims.getExpiration().toString());
                        jsonOB.add("usuario", claims.get("usuario", String.class));
                        jsonOB.add("id_sesion_recibida", claims.getId());
                        //jsonOB.add("id_sesion_actual", "session");
                        //jsonOB.add("validate_session", "session".equals(claims.getId()));
                        jsonOB.add("id_sesion_actual", httpRequest.getSession().getId());
                        jsonOB.add("validate_session", httpRequest.getSession().getId().equals(claims.getId()));
                        long tiempo = System.currentTimeMillis();
                        jsonOB.add("validate_expiration", tiempo < claims.getExpiration().getTime());
                        //jsonOB.add("validate", ("session".equals(claims.getId())) && (tiempo < claims.getExpiration().getTime()));
                        jsonOB.add("validate", (httpRequest.getSession().getId().equals(claims.getId())) && (tiempo < claims.getExpiration().getTime()));
                        jsonOB.add("resul", "decrypted");
                        json = jsonOB.build();

                    } catch (io.jsonwebtoken.SignatureException ex) {
                        jsonOB.add("error", "apikey no válida");
                        json = jsonOB.build();
                    }
                } else {
                    jsonOB.add("error", "header no válido");
                    jsonOB.add("alg", algoritmo);
                    jsonOB.add("typ", tipo);
                    jsonOB.add("header", header);
                    json = jsonOB.build();
                }
            } else {
                jsonOB.add("error", "apikey no válida");
                json = jsonOB.build();
            }

        } catch (ParseException ex) {
            //Logger.getLogger(AuthJWT.class.getName()).log(Level.SEVERE, null, ex);
            jsonOB.add("error", "apikey sin estructura válida");
            json = jsonOB.build();
        } catch (Exception ex) {
            //Logger.getLogger(AuthJWT.class.getName()).log(Level.SEVERE, null, ex);
            jsonOB.add("error", "apikey sin estructura válida");
            json = jsonOB.build();
        }
        return json;

    }

    /*@GET
    @Produces(MediaType.APPLICATION_JSON)*/
    public Response getData(@QueryParam("apikey") String token) {
        Response response;
        Response.Status statusResul;

        boolean validado = false;
        String usuario = "";
        if (token != null) {
            JsonObject jsonJWT = validateJWT(token);
            if (jsonJWT.containsKey("validate")) {
                if (jsonJWT.getBoolean("validate")) {
                    validado = true;
                    usuario = jsonJWT.getString("usuario");
                } else {
                    System.out.println(jsonJWT.getString("id_sesion_recibida"));
                    System.out.println(jsonJWT.getString("id_sesion_actual"));
                }
            }
        }

        if (!validado) {
            statusResul = Response.Status.FORBIDDEN;

            JsonObjectBuilder jsonOB = Json.createObjectBuilder();
            jsonOB.add("mensaje", "Debes identificarte");
            JsonObject json = jsonOB.build();

            response = Response
                    .status(statusResul)
                    .entity(json)
                    .build();

        } else {
            statusResul = Response.Status.OK;

            JsonObjectBuilder jsonOB = Json.createObjectBuilder();
            jsonOB.add("mensaje", "Lo puedes ver");
            jsonOB.add("usuario", usuario);
            JsonObject json = jsonOB.build();

            response = Response
                    .status(statusResul)
                    .entity(json)
                    .build();
        }

        return response;

    }

    
    //Fin seguridad
    @Operation(
            summary = "Datos Torneos individuales",
            description = "Obtiene los datos todos los Torneos individuales"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TorneosIndividuales.class)
                )
            }
    )
    @ApiResponse(responseCode = "204", description = "No hay datos que mostrar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
// API -> GET /torneoindividual
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        List<TorneosIndividuales> lista = null;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            TorneosIndividualesJpaController dao = new TorneosIndividualesJpaController(emf);
            lista = dao.findTorneosIndividualesEntities();
            if (lista == null) {
                statusResul = Response.Status.NO_CONTENT;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(lista)
                    .build();
            return response;
        }
    }

    @Operation(
            summary = "Datos de Torneo individuale",
            description = "Obtiene los datos del Torneo individuale concretado"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TorneosIndividuales.class)
                )
            }
    )
    @ApiResponse(responseCode = "404", description = "No hay datos que mostrar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
// API -> GET /torneoindividual/{id}
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") int id) {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        TorneosIndividuales obj = null;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            TorneosIndividualesJpaController dao = new TorneosIndividualesJpaController(emf);
            obj = dao.findTorneosIndividuales(id);
            if (obj == null) {
                statusResul = Response.Status.NOT_FOUND;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(obj)
                    .build();
            return response;
        }
    }

    // Path especiales para el sort del programa
    @Operation(
            summary = "Datos de Torneos individuales",
            description = "Obtiene los datos de los Torneos individuales que no sean menores de edad"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TorneosIndividuales.class)
                )
            }
    )
    @ApiResponse(responseCode = "204", description = "No hay datos que mostrar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @GET
    @Path("/edad/false")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByEdadFalse() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Response.Status statusResul = Response.Status.OK;
        List<TorneosIndividuales> lista = null;
        //EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            TorneosIndividualesJpaController dao = new TorneosIndividualesJpaController(emf);
            em = dao.getEntityManager();

            TypedQuery<TorneosIndividuales> tQuery = em.createNamedQuery("TorneosIndividuales.findByMenoresEdad", TorneosIndividuales.class).setParameter("menoresEdad", false);

            lista = tQuery.getResultList();

            if (lista == null) {
                statusResul = Response.Status.NO_CONTENT;
            }

        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(lista)
                    .build();
            return response;
        }
    }

    @Operation(
            summary = "Datos de Torneos individuales",
            description = "Obtiene los datos de los Torneos individuales que sean menores de edad"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TorneosIndividuales.class)
                )
            }
    )
    @ApiResponse(responseCode = "204", description = "No hay datos que mostrar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @GET
    @Path("/edad/true")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByEdadTrue() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Response.Status statusResul = Response.Status.OK;
        List<TorneosIndividuales> lista = null;
        //EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            TorneosIndividualesJpaController dao = new TorneosIndividualesJpaController(emf);
            em = dao.getEntityManager();

            TypedQuery<TorneosIndividuales> tQuery = em.createNamedQuery("TorneosIndividuales.findByMenoresEdad", TorneosIndividuales.class).setParameter("menoresEdad", true);

            lista = tQuery.getResultList();

            if (lista == null) {
                statusResul = Response.Status.NO_CONTENT;
            }

        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(lista)
                    .build();
            return response;
        }
    }

    @Operation(
            summary = "Datos de Torneos individuales",
            description = "Obtiene los datos de los Torneos individuales que sean de un gestor en concreto"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TorneosIndividuales.class)
                )
            }
    )
    @ApiResponse(responseCode = "204", description = "No hay datos que mostrar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @GET
    @Path("/gestor/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByGestor(@PathParam("nombre") String nombre) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Response.Status statusResul = Response.Status.OK;
        List<TorneosIndividuales> lista = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            TorneosIndividualesJpaController dao = new TorneosIndividualesJpaController(emf);
            em = dao.getEntityManager();

            TypedQuery<TorneosIndividuales> tQuery = em.createNamedQuery("TorneosIndividuales.findByNombreGestor", TorneosIndividuales.class).setParameter("nombreCreador", nombre);

            lista = tQuery.getResultList();

            if (lista == null) {
                statusResul = Response.Status.NO_CONTENT;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(lista)
                    .build();
            return response;
        }
    }

    @Operation(
            summary = "Datos de Torneos individuales",
            description = "Obtiene los datos de los Torneos individuales que sean del email de un gestor en concreto"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TorneosIndividuales.class)
                )
            }
    )
    @ApiResponse(responseCode = "204", description = "No hay datos que mostrar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @GET
    @Path("/gestoremail/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByEmailGestor(@PathParam("email") String email) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Response.Status statusResul = Response.Status.OK;
        List<TorneosIndividuales> lista = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            TorneosIndividualesJpaController dao = new TorneosIndividualesJpaController(emf);
            em = dao.getEntityManager();

            TypedQuery<TorneosIndividuales> tQuery = em.createNamedQuery("TorneosIndividuales.findByEmailGestor", TorneosIndividuales.class).setParameter("emailCreador", email);

            lista = tQuery.getResultList();

            if (lista == null) {
                statusResul = Response.Status.NO_CONTENT;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(lista)
                    .build();
            return response;
        }
    }

    @Operation(
            summary = "Datos de Torneos individuales",
            description = "Obtiene los datos de los Torneos individuales ordenados por fecha finalizacion descendentemente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TorneosIndividuales.class)
                )
            }
    )
    @ApiResponse(responseCode = "204", description = "No hay datos que mostrar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @GET
    @Path("/fechafinalizacion/desc")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByFechaFinalizacionDesc() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Response.Status statusResul = Response.Status.OK;
        List<TorneosIndividuales> lista = null;
        //EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            TorneosIndividualesJpaController dao = new TorneosIndividualesJpaController(emf);
            em = dao.getEntityManager();

            TypedQuery<TorneosIndividuales> tQuery = em.createNamedQuery("TorneosIndividuales.findByFechaFinalizacionDesc", TorneosIndividuales.class);

            lista = tQuery.getResultList();

            if (lista == null) {
                statusResul = Response.Status.NO_CONTENT;
            }

        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(lista)
                    .build();
            return response;
        }
    }

    @Operation(
            summary = "Datos de Torneos individuales",
            description = "Obtiene los datos de los Torneos individuales ordenados por fecha finalizacion ascendente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TorneosIndividuales.class)
                )
            }
    )
    @ApiResponse(responseCode = "204", description = "No hay datos que mostrar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @GET
    @Path("/fechafinalizacion/asc")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByFechaFinalizacionAsc() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Response.Status statusResul = Response.Status.OK;
        List<TorneosIndividuales> lista = null;
        //EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            TorneosIndividualesJpaController dao = new TorneosIndividualesJpaController(emf);
            em = dao.getEntityManager();

            TypedQuery<TorneosIndividuales> tQuery = em.createNamedQuery("TorneosIndividuales.findByFechaFinalizacionAsc", TorneosIndividuales.class);

            lista = tQuery.getResultList();

            if (lista == null) {
                statusResul = Response.Status.NO_CONTENT;
            }

        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(lista)
                    .build();
            return response;
        }
    }

    @Operation(
            summary = "Datos de Torneos individuales",
            description = "Obtiene los datos de los Torneos individuales de un nivel en concreto"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TorneosIndividuales.class)
                )
            }
    )
    @ApiResponse(responseCode = "204", description = "No hay datos que mostrar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @GET
    @Path("/nivel/{nivel}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByNivel(@PathParam("nivel") int nivel) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Response.Status statusResul = Response.Status.OK;
        List<TorneosIndividuales> lista = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            TorneosIndividualesJpaController dao = new TorneosIndividualesJpaController(emf);
            em = dao.getEntityManager();

            TypedQuery<TorneosIndividuales> tQuery = em.createNamedQuery("TorneosIndividuales.findByNivel", TorneosIndividuales.class).setParameter("nivel", nivel);

            lista = tQuery.getResultList();

            if (lista == null) {
                statusResul = Response.Status.NO_CONTENT;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(lista)
                    .build();
            return response;
        }
    }

    @Operation(
            summary = "Datos de Torneos individuales",
            description = "Obtiene los datos de los Torneos individuales ordenados por fecha nivel descendentemente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TorneosIndividuales.class)
                )
            }
    )
    @ApiResponse(responseCode = "204", description = "No hay datos que mostrar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @GET
    @Path("/nivel/desc")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByNivelDesc() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Response.Status statusResul = Response.Status.OK;
        List<TorneosIndividuales> lista = null;
        //EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            TorneosIndividualesJpaController dao = new TorneosIndividualesJpaController(emf);
            em = dao.getEntityManager();

            TypedQuery<TorneosIndividuales> tQuery = em.createNamedQuery("TorneosIndividuales.findByNivelDesc", TorneosIndividuales.class);

            lista = tQuery.getResultList();

            if (lista == null) {
                statusResul = Response.Status.NO_CONTENT;
            }

        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(lista)
                    .build();
            return response;
        }
    }

    @Operation(
            summary = "Datos de Torneos individuales",
            description = "Obtiene los datos de los Torneos individuales ordenados por fecha finalizacion ascendentemente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TorneosIndividuales.class)
                )
            }
    )
    @ApiResponse(responseCode = "204", description = "No hay datos que mostrar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @GET
    @Path("/nivel/asc")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByNivelAsc() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Response.Status statusResul = Response.Status.OK;
        List<TorneosIndividuales> lista = null;
        //EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            TorneosIndividualesJpaController dao = new TorneosIndividualesJpaController(emf);
            em = dao.getEntityManager();

            TypedQuery<TorneosIndividuales> tQuery = em.createNamedQuery("TorneosIndividuales.findByNivelAsc", TorneosIndividuales.class);

            lista = tQuery.getResultList();

            if (lista == null) {
                statusResul = Response.Status.NO_CONTENT;
            }

        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(lista)
                    .build();
            return response;
        }
    }

    @Operation(
            summary = "Datos de Torneos individuales",
            description = "Obtiene los datos de los Torneos individuales con plazas libres"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TorneosIndividuales.class)
                )
            }
    )
    @ApiResponse(responseCode = "204", description = "No hay datos que mostrar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @GET
    @Path("/plazaslibres")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByPlazasLibres() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Response.Status statusResul = Response.Status.OK;
        List<TorneosIndividuales> lista = null;
        //EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            TorneosIndividualesJpaController dao = new TorneosIndividualesJpaController(emf);
            em = dao.getEntityManager();

            TypedQuery<TorneosIndividuales> tQuery = em.createNamedQuery("TorneosIndividuales.findByPlazasLibres", TorneosIndividuales.class);

            lista = tQuery.getResultList();

            if (lista == null) {
                statusResul = Response.Status.NO_CONTENT;
            }

        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(lista)
                    .build();
            return response;
        }
    }

    @Operation(
            summary = "Datos de Torneos individuales",
            description = "Obtiene los datos de los Torneos individuales con plazas libres y fecha posterior a la actual"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TorneosIndividuales.class)
                )
            }
    )
    @ApiResponse(responseCode = "204", description = "No hay datos que mostrar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @GET
    @Path("/fechaposterioractualyplazaslibres")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByFechaActualAndPlazasLibres() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Response.Status statusResul = Response.Status.OK;
        List<TorneosIndividuales> lista = null;
        //EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            TorneosIndividualesJpaController dao = new TorneosIndividualesJpaController(emf);
            em = dao.getEntityManager();

            TypedQuery<TorneosIndividuales> tQuery = em.createNamedQuery("TorneosIndividuales.findByPlazasLibresAndAfterDate", TorneosIndividuales.class).setParameter("fecha", Date.from(Instant.now()));//LocalDateTime.now()

            lista = tQuery.getResultList();

            if (lista == null) {
                statusResul = Response.Status.NO_CONTENT;
            }

        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(lista)
                    .build();
            return response;
        }
    }

    @Operation(
            summary = "Crear un Torneo individual",
            description = "Crear un Torneo individual"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TorneosIndividuales.class)
                )
            }
    )
    @ApiResponse(responseCode = "302", description = "Ya existe un torneo con esa id")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(TorneosIndividuales torneoIndividuale, @QueryParam("apikey") String token) {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.CREATED;

        Response response;

        if (getData(token).getStatus() != 200) {
            response = getData(token);
        } else {
            try {
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
                TorneosIndividualesJpaController dao = new TorneosIndividualesJpaController(emf);
                TorneosIndividuales empFound = dao.findTorneosIndividuales(torneoIndividuale.getIdTorneoIndividual());
                if (empFound == null) {
                    dao.create(torneoIndividuale);
                } else {
                    statusResul = Response.Status.FOUND;
                }
            } catch (Exception ex) {
                statusResul = Response.Status.BAD_REQUEST;
            } finally {
                emf.close();
                response = Response
                        .status(statusResul)
                        .build();
                return response;
            }
        }
        return response;
    }

    @Operation(
            summary = "Editar un Torneo individual",
            description = "Editar un Torneo individual"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TorneosIndividuales.class)
                )
            }
    )
    @ApiResponse(responseCode = "404", description = "No hay datos que mostrar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(TorneosIndividuales torneosIndividuales, @QueryParam("apikey") String token) {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        Response response;

        if (getData(token).getStatus() != 200) {
            response = getData(token);
        } else {
            try {
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
                TorneosIndividualesJpaController dao = new TorneosIndividualesJpaController(emf);
                TorneosIndividuales empFound = dao.findTorneosIndividuales(torneosIndividuales.getIdTorneoIndividual());
                if (empFound != null) {
                    empFound.setNombreTorneoIndividual(torneosIndividuales.getNombreTorneoIndividual());
                    empFound.setFotoUrlTorneoIndividual(torneosIndividuales.getFotoUrlTorneoIndividual());
                    empFound.setDescripcionCorta(torneosIndividuales.getDescripcionCorta());
                    empFound.setDescripcionCompleta(torneosIndividuales.getDescripcionCompleta());
                    empFound.setNivel(torneosIndividuales.getNivel());
                    empFound.setSolicitudesMaximos(torneosIndividuales.getSolicitudesMaximos());
                    empFound.setFechaFinalizacion(torneosIndividuales.getFechaFinalizacion());
                    empFound.setFechaInicio(torneosIndividuales.getFechaInicio());
                    empFound.setMenoresEdad(torneosIndividuales.getMenoresEdad());

                    dao.edit(empFound);
                } else {
                    statusResul = Response.Status.NOT_FOUND;
                }
            } catch (Exception ex) {
                statusResul = Response.Status.BAD_REQUEST;
            } finally {
                emf.close();
                response = Response
                        .status(statusResul)
                        .build();
                return response;
            }
        }
        return response;

    }

    @Operation(
            summary = "Borrar un Torneo individuale",
            description = "Borrar un Torneo individuale"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TorneosIndividuales.class)
                )
            }
    )
    @ApiResponse(responseCode = "404", description = "No hay datos que mostrar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(
            @PathParam("id") int id, @QueryParam("apikey") String token) {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        Response response;

        if (getData(token).getStatus() != 200) {
            response = getData(token);
        } else {
            try {
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
                TorneosIndividualesJpaController dao = new TorneosIndividualesJpaController(emf);
                TorneosIndividuales empFound = dao.findTorneosIndividuales(id);
                if (empFound != null) {
                    dao.destroy(id);
                } else {
                    statusResul = Response.Status.NOT_FOUND;
                }
            } catch (Exception ex) {
                statusResul = Response.Status.BAD_REQUEST;
            } finally {
                emf.close();
                response = Response
                        .status(statusResul)
                        .build();
                return response;
            }
        }
        return response;
    }

}
