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
import suprem_tournaments.Gestor;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
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
import suprem_tournaments.GestorJpaController;
import suprem_tournaments.TorneosIndividuales;

/**
 * REST Web Service
 *
 * @author Jorge
 */
@OpenAPIDefinition(
        info = @Info(
                description = "APIREST sobre la BD de suprem tournaments",
                version = "1.0.0",
                title = "Swagger Suprem Tournaments"
        )
)
@Tag(name = "Gestor",
        description = "Datos de los gestores")
@Server(url = "/api/supremtournaments") // para el Try it
@Path("gestor")
public class ServiceRESTGestor {

    @Context
    private UriInfo context;

    private static final String PERSISTENCE_UNIT = "API_SupremTournamentsPU";

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
    /**
     * Creates a new instance of ServiceRESTGestor
     */
    public ServiceRESTGestor() {
    }

    @Operation(
            summary = "Datos de gestores",
            description = "Obtiene los datos todos los gestores"
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        List<Gestor> lista = null;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            GestorJpaController dao = new GestorJpaController(emf);
            lista = dao.findGestorEntities();
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

    /*@Operation(
            summary = "Datos de un gestor en concreto",
            description = "Obtiene los datos de un gestor en concreto"
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
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") int id) {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        Gestor obj = null;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            GestorJpaController dao = new GestorJpaController(emf);
            obj = dao.findGestor(id);
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
    }*/
    @Operation(
            summary = "Datos de un gestor en concreto",
            description = "Obtiene los datos de un gestor en concreto"
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
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneByEmail(@PathParam("email") String email, @QueryParam("apikey") String token) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Response.Status statusResul = Response.Status.OK;
        Response response;
        Gestor obj = null;
        if (getData(token).getStatus() != 200) {
            response = getData(token);
        } else {
            try {
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
                GestorJpaController dao = new GestorJpaController(emf);

                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
                em = dao.getEntityManager();

                TypedQuery<Gestor> tQuery = em.createNamedQuery("Gestor.findByEmail", Gestor.class).setParameter("email", email);

                obj = tQuery.getSingleResult();

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
            summary = "Crear un gestor",
            description = "Crear un gestor"
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
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(Gestor emp) {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.CREATED;
        EntityManager em = null;
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        List<Gestor> lista = null;

        try {
            GestorJpaController dao = new GestorJpaController(emf);
            Gestor empFound = dao.findGestor(emp.getIdGestor());

            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            em = dao.getEntityManager();

            TypedQuery<Gestor> tQuery = em.createNamedQuery("Gestor.findByEmail", Gestor.class).setParameter("email", emp.getEmail());

            lista = tQuery.getResultList();

            if (empFound == null && lista.isEmpty()) {
                dao.create(emp);
            } else {
                statusResul = Response.Status.FOUND;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .build();
            return response;
        }
    }

}
