package entities;

import entities.Aluno;
import entities.DataEvento;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-09-25T09:31:31")
@StaticMetamodel(Chamada.class)
public class Chamada_ { 

    public static volatile SingularAttribute<Chamada, Integer> idchamada;
    public static volatile SingularAttribute<Chamada, DataEvento> iddataEvento;
    public static volatile SingularAttribute<Chamada, Aluno> idaluno;
    public static volatile SingularAttribute<Chamada, Integer> faltas;

}