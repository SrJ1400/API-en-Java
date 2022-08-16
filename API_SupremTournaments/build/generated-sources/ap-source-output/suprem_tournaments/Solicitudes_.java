package suprem_tournaments;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import suprem_tournaments.CombatesIndividuales;
import suprem_tournaments.TorneosIndividuales;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-08-16T19:29:04", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Solicitudes.class)
public class Solicitudes_ { 

    public static volatile SingularAttribute<Solicitudes, String> datosPublicos;
    public static volatile SingularAttribute<Solicitudes, String> estado;
    public static volatile SingularAttribute<Solicitudes, String> datos;
    public static volatile SingularAttribute<Solicitudes, Date> fechaNacimiento;
    public static volatile SingularAttribute<Solicitudes, TorneosIndividuales> idTorneoIndividual;
    public static volatile CollectionAttribute<Solicitudes, CombatesIndividuales> combatesIndividualesCollection1;
    public static volatile CollectionAttribute<Solicitudes, CombatesIndividuales> combatesIndividualesCollection2;
    public static volatile CollectionAttribute<Solicitudes, CombatesIndividuales> combatesIndividualesCollection;
    public static volatile SingularAttribute<Solicitudes, Integer> idSolicitudes;
    public static volatile SingularAttribute<Solicitudes, String> nombreSolicitudes;
    public static volatile SingularAttribute<Solicitudes, String> fotoUrlSolicitudes;

}