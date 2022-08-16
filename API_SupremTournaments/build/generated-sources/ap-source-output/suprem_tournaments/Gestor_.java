package suprem_tournaments;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import suprem_tournaments.TorneosIndividuales;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-08-16T19:29:04", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Gestor.class)
public class Gestor_ { 

    public static volatile CollectionAttribute<Gestor, TorneosIndividuales> torneosIndividualesCollection;
    public static volatile SingularAttribute<Gestor, Integer> idGestor;
    public static volatile SingularAttribute<Gestor, String> contrasenya;
    public static volatile SingularAttribute<Gestor, String> nombre;
    public static volatile SingularAttribute<Gestor, String> email;

}