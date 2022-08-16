package suprem_tournaments;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import suprem_tournaments.Solicitudes;
import suprem_tournaments.TorneosIndividuales;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-08-16T19:29:04", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(CombatesIndividuales.class)
public class CombatesIndividuales_ { 

    public static volatile SingularAttribute<CombatesIndividuales, Solicitudes> idSolicitud2;
    public static volatile SingularAttribute<CombatesIndividuales, Solicitudes> idSolicitud1;
    public static volatile SingularAttribute<CombatesIndividuales, Integer> idCombatesIndividuales;
    public static volatile SingularAttribute<CombatesIndividuales, TorneosIndividuales> idTorneoIndividual;
    public static volatile SingularAttribute<CombatesIndividuales, Solicitudes> idSolicitudGanador;

}