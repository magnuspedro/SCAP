package entities;

import entities.Aluno;
import entities.DataEvento;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-09-24T13:27:36")
@StaticMetamodel(ChamadaEvento.class)
public class ChamadaEvento_ { 

    public static volatile SingularAttribute<ChamadaEvento, Boolean> situacao;
    public static volatile SingularAttribute<ChamadaEvento, Integer> idchamada;
    public static volatile SingularAttribute<ChamadaEvento, Date> hora;
    public static volatile SingularAttribute<ChamadaEvento, DataEvento> iddataEvento;
    public static volatile SingularAttribute<ChamadaEvento, Aluno> idaluno;

}