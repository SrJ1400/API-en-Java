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
import java.util.Base64;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import suprem_tournaments.SolicitudesContinuas;
import suprem_tournaments.SolicitudesContinuasJpaController;
import suprem_tournaments.TorneosIndividuales;

/**
 * REST Web Service
 *
 * @author Jorge
 */
// SWAGGER -> ServiceREST SolicitudesContinuas
@OpenAPIDefinition(
        info = @Info(
                description = "APIREST sobre la BD de suprem tournaments",
                version = "1.0.0",
                title = "Swagger Suprem Tournaments"
        )
)
@Tag(name = "SolicitudesContinuas",
        description = "Datos de las Solicitudes continuas")
@Server(url = "/api/supremtournaments") 
// API -> ServiceREST torneoindividual
@Path("solicitudescontinuas")
public class ServiceRESTSolicitudesContinuas {

    @Context
    private UriInfo context;

    private static final String PERSISTENCE_UNIT = "API_SupremTournamentsPU";

    /**
     * Creates a new instance of SolicitudescontinuasResource
     */
    public ServiceRESTSolicitudesContinuas() {
    }

    // Metodos y propiedades necesarias para seguridad
    @Context
    private HttpServletRequest httpRequest;
    private static final String SECRET_KEY = "my_super_secret_key";

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
            summary = "Datos de solicitudes continuas",
            description = "Obtiene los datos todos las solicitudescontinuas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = SolicitudesContinuas.class)
                )
            }
    )
    @ApiResponse(responseCode = "204", description = "No hay datos que mostrar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("apikey") String token) {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        List<SolicitudesContinuas> lista = null;
        Response response;
        if (getData(token).getStatus() != 200) {
            response = getData(token);
        } else {
            try {
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
                SolicitudesContinuasJpaController dao = new SolicitudesContinuasJpaController(emf);
                lista = dao.findSolicitudesContinuasEntities();
                if (lista == null) {
                    statusResul = Response.Status.NO_CONTENT;
                }
            } catch (Exception ex) {
                statusResul = Response.Status.BAD_REQUEST;
            } finally {
                emf.close();
                response = Response
                        .status(statusResul)
                        .entity(lista)
                        .build();
                return response;
            }
        }
        return response;
    }

    @Operation(
            summary = "Datos de solicitud continua",
            description = "Obtiene los datos de una solicitud continua en concreto"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = SolicitudesContinuas.class)
                )
            }
    )
    @ApiResponse(responseCode = "404", description = "No hay datos que mostrar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") int id, @QueryParam("apikey") String token) {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        SolicitudesContinuas obj = null;
        Response response;
        if (getData(token).getStatus() != 200) {
            response = getData(token);
        } else {
            try {
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
                SolicitudesContinuasJpaController dao = new SolicitudesContinuasJpaController(emf);
                obj = dao.findSolicitudesContinuas(id);
                if (obj == null) {
                    statusResul = Response.Status.NOT_FOUND;
                }
            } catch (Exception ex) {
                statusResul = Response.Status.BAD_REQUEST;
            } finally {
                emf.close();
                response = Response
                        .status(statusResul)
                        .entity(obj)
                        .build();
                return response;
            }
        }
        return response;
    }

    @Operation(
            summary = "Editar una solicitud continua",
            description = "Editar una solicitud continua"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = SolicitudesContinuas.class)
                )
            }
    )
    @ApiResponse(responseCode = "404", description = "No existe el dato a editar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(SolicitudesContinuas solicitudesContinuas, @QueryParam("apikey") String token) {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        Response response;
        if (getData(token).getStatus() != 200) {
            response = getData(token);
        } else {
            try {
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
                SolicitudesContinuasJpaController dao = new SolicitudesContinuasJpaController(emf);
                SolicitudesContinuas solicitudConntinuaFound = dao.findSolicitudesContinuas(solicitudesContinuas.getIdSolicitudesContinuas());
                if (solicitudConntinuaFound != null) {
                    dao.edit(solicitudesContinuas);
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
            summary = "Crear una solicitud continua",
            description = "Crear una solicitud continua"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = SolicitudesContinuas.class)
                )
            }
    )
    @ApiResponse(responseCode = "204", description = "No hay datos que mostrar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(SolicitudesContinuas solicitudesContinuas, @QueryParam("apikey") String token) {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.CREATED;
        Response response;
        if (getData(token).getStatus() != 200) {
            response = getData(token);
        } else {
            try {
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
                SolicitudesContinuasJpaController dao = new SolicitudesContinuasJpaController(emf);
                SolicitudesContinuas solicitudContinuaFound = dao.findSolicitudesContinuas(solicitudesContinuas.getIdSolicitudesContinuas());
                if (solicitudContinuaFound == null) {
                    dao.create(solicitudesContinuas);
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
            summary = "Eliminar una solicitud continua",
            description = "Eliminar una solicitud continua en concreto"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Acci&oacute;n realizada con &eacute;xito",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = SolicitudesContinuas.class)
                )
            }
    )
    @ApiResponse(responseCode = "404", description = "No hay datos que borrar")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(
            @PathParam("id") int id, @QueryParam("apikey") String token
    ) {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        Response response;
        if (getData(token).getStatus() != 200) {
            response = getData(token);
        } else {
            try {
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
                SolicitudesContinuasJpaController dao = new SolicitudesContinuasJpaController(emf);
                SolicitudesContinuas solicitudContinuaFound = dao.findSolicitudesContinuas(id);
                if (solicitudContinuaFound != null) {
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
