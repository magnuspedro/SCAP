package entities;

import entities.DataEvento;
import entities.EventosInstrutores;
import entities.Local;
import entities.Matricula;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-09-25T09:36:16")
@StaticMetamodel(Evento.class)
public class Evento_ { 

    public static volatile SingularAttribute<Evento, Integer> idevento;
    public static volatile SingularAttribute<Evento, String> tipo;
    public static volatile SingularAttribute<Evento, Date> horaTermino;
    public static volatile ListAttribute<Evento, DataEvento> dataEventoCollection;
    public static volatile SingularAttribute<Evento, Local> idlocal;
    public static volatile SingularAttribute<Evento, String> nome;
    public static volatile CollectionAttribute<Evento, Matricula> matriculaCollection;
    public static volatile SingularAttribute<Evento, Date> horaInicio;
    public static volatile CollectionAttribute<Evento, EventosInstrutores> eventosInstrutoresCollection;
    public static volatile SingularAttribute<Evento, String> descricao;
    public static volatile SingularAttribute<Evento, Integer> vagasTotais;

}