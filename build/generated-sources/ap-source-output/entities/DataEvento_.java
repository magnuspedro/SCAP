package entities;

import entities.Chamada;
import entities.ChamadaEvento;
import entities.Evento;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-09-24T13:27:36")
@StaticMetamodel(DataEvento.class)
public class DataEvento_ { 

    public static volatile SingularAttribute<DataEvento, Evento> idevento;
    public static volatile SingularAttribute<DataEvento, Boolean> aberto;
    public static volatile SingularAttribute<DataEvento, Date> data;
    public static volatile CollectionAttribute<DataEvento, Chamada> chamadaCollection;
    public static volatile CollectionAttribute<DataEvento, ChamadaEvento> chamadaEventoCollection;
    public static volatile SingularAttribute<DataEvento, Integer> iddataEvento;
    public static volatile SingularAttribute<DataEvento, Date> horaFechamento;
    public static volatile SingularAttribute<DataEvento, Date> horaAberto;

}