/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apirest;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author VRICO
 */
@javax.ws.rs.ApplicationPath("supremtournaments")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(apirest.ServiceAuthJwt.class);
        resources.add(apirest.ServiceRESTCombatesindividuales.class);
        resources.add(apirest.ServiceRESTGestor.class);
        resources.add(apirest.ServiceRESTSolicitudes.class);
        resources.add(apirest.ServiceRESTSolicitudesContinuas.class);
        resources.add(apirest.ServiceRESTTorneoIndividual.class);
    }
    
}
