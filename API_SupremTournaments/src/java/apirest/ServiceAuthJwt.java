/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package apirest;

import suprem_tournaments.Usuario;
import suprem_tournaments.GestorJpaController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import suprem_tournaments.Gestor;

/**
 * REST Web Service
 *
 * @author VICENTE
 */
@OpenAPIDefinition(
        info = @Info(
                description = "APIREST sobre la BD de suprem tournaments",
                version = "1.0.0",
                title = "Swagger Suprem Tournaments"
        )
)
@Tag(name = "AuthJWT",
        description = "Servicio para loguearse")
@Server(url = "/api/supremtournaments")
@Path("jwt")
public class ServiceAuthJwt {

    private static final String SECRET_KEY = "my_super_secret_key";
    private static final String APPNAME = "APPLOGIN-JWT";
    private static final String ISSUER = "balmis.com";
    private static final int EXPIRATION_MINUTES = 120;
    private static final String PERSISTENCE_UNIT = "API_SupremTournamentsPU";

    /**
     * Creates a new instance of AuthJWT
     */
    public ServiceAuthJwt() {
    }

    @Context
    private HttpServletRequest httpRequest;

    @Operation(
            summary = "Obtener datos de la sesion",
            description = "Get que te devuelve los datos de tu sesion"
    )
    @ApiResponse(responseCode = "200",
            description = "Sesion creada")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @GET
    @Path("create-session")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSession() {

        JsonObjectBuilder jsonOB = Json.createObjectBuilder();
        jsonOB.add("session", httpRequest.getSession().getId());
        JsonObject json = jsonOB.build();

        return Response
                .status(Response.Status.OK)
                .entity(json)
                .build();

    }

    @Operation(
            summary = "Obtener datos de la apikey",
            description = "Get que le mandas la apikey y un token y te da datos"
    )
    @ApiResponse(responseCode = "201",
            description = "Logueado")
    @ApiResponse(responseCode = "401",
            description = "No fue posible loguearse")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
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

    @Operation(
            summary = "Loguearse como gestor",
            description = "Post que le mandas un email y la contrase&ntilde;a del gestor para loguearse"
    )
    @ApiResponse(responseCode = "201",
            description = "Logueado")
    @ApiResponse(responseCode = "401",
            description = "No fue posible loguearse")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @POST
    @Path("auth")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response validar(Usuario usuario) {
        boolean validacion = usuarioValido(usuario);
        Response.Status status;
        if (validacion) {
            long tiempo = System.currentTimeMillis();
            Header header = Jwts.header();
            header.setType("JWT");
            String jwt = Jwts.builder()
                    .setHeader((Map<String, Object>) header)
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                    //.setId("session")
                    .setId(httpRequest.getSession().getId())
                    .setSubject(APPNAME)
                    .setIssuer(ISSUER)
                    .setIssuedAt(new Date(tiempo))
                    .setExpiration(new Date(tiempo + (EXPIRATION_MINUTES * 60 * 1000)))
                    .claim("usuario", usuario.getEmail())
                    .compact();

            JsonObject json = Json.createObjectBuilder()
                    .add("JWT", jwt).build();
            status = Response.Status.CREATED;
            return Response.status(status).entity(json).build();

        } else {
            status = Response.Status.UNAUTHORIZED;
            return Response.status(status).build();
        }

    }

    private boolean usuarioValido(Usuario usuario) {
        EntityManager em = null;
        EntityManagerFactory emf = null;
        List<Gestor> lista = null;
        Gestor obj = null;
        
        try{
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            GestorJpaController dao = new GestorJpaController(emf);
            em = dao.getEntityManager();
            
            TypedQuery<Gestor> tQuery = em.createNamedQuery("Gestor.findByEmail", Gestor.class).setParameter("email", usuario.getEmail());

            obj = tQuery.getSingleResult();
            if ((usuario.getEmail().equals(obj.getEmail())) && (usuario.getPassword().equals(obj.getContrasenya()))) 
            {
              return true;
            }                
        } 
        catch (Exception ex) {
            return false;
        }
        return false;
    }

    @Operation(
            summary = "Pate para checkear ApiKey",
            description = "Get que le mandas la apikey y te dice si es valida y mas datos sobre ella"
    )
    @ApiResponse(responseCode = "201",
            description = "Valido")
    @ApiResponse(responseCode = "406",
            description = "No acceptable la ApiKey")
    @ApiResponse(responseCode = "400",
            description = "Error al procesar la petici&oacute;n")
    @GET
    @Path("check")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getText(@QueryParam("apikey") String token) {

        Response.Status status = Response.Status.NOT_ACCEPTABLE;

        boolean validado = false;
        if (token != null) {
            JsonObject jsonJWT = validateJWT(token);
            if (jsonJWT.containsKey("validate")) {
                if (jsonJWT.getBoolean("validate")) {
                    status = Response.Status.ACCEPTED;
                }
            }
            return Response.status(status).entity(jsonJWT).build();
        } else {
            return Response.status(status).build();
        }

    }

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

}
