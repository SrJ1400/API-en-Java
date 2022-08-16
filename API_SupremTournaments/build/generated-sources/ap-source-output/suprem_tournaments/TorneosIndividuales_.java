package suprem_tournaments;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import suprem_tournaments.CombatesIndividuales;
import suprem_tournaments.Gestor;
import suprem_tournaments.Solicitudes;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-08-16T19:29:04", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(TorneosIndividuales.class)
public class TorneosIndividuales_ { 

    public static volatile SingularAttribute<TorneosIndividuales, String> descripcionCorta;
    public static volatile SingularAttribute<TorneosIndividuales, Integer> idTorneoIndividual;
    public static volatile SingularAttribute<TorneosIndividuales, String> nombreTorneoIndividual;
    public static volatile SingularAttribute<TorneosIndividuales, String> fotoUrlTorneoIndividual;
    public static volatile SingularAttribute<TorneosIndividuales, Gestor> idGestor;
    public static volatile SingularAttribute<TorneosIndividuales, Date> fechaFinalizacion;
    public static volatile SingularAttribute<TorneosIndividuales, Boolean> menoresEdad;
    public static volatile SingularAttribute<TorneosIndividuales, String> descripcionCompleta;
    public static volatile SingularAttribute<TorneosIndividuales, Integer> solicitudesMaximos;
    public static volatile SingularAttribute<TorneosIndividuales, Date> fechaInicio;
    public static volatile CollectionAttribute<TorneosIndividuales, CombatesIndividuales> combatesIndividualesCollection;
    public static volatile SingularAttribute<TorneosIndividuales, Integer> nivel;
    public static volatile CollectionAttribute<TorneosIndividuales, Solicitudes> solicitudesCollection;

}