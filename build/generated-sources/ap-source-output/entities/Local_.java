package entities;

import entities.Evento;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-09-24T13:27:36")
@StaticMetamodel(Local.class)
public class Local_ { 

    public static volatile SingularAttribute<Local, String> sala;
    public static volatile SingularAttribute<Local, Integer> idlocal;
    public static volatile SingularAttribute<Local, String> nomeLocal;
    public static volatile CollectionAttribute<Local, Evento> eventoCollection;

}