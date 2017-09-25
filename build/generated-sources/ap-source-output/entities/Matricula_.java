package entities;

import entities.Aluno;
import entities.Evento;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-09-25T09:31:31")
@StaticMetamodel(Matricula.class)
public class Matricula_ { 

    public static volatile SingularAttribute<Matricula, Evento> idevento;
    public static volatile SingularAttribute<Matricula, Integer> idmatricula;
    public static volatile SingularAttribute<Matricula, Aluno> idaluno;
    public static volatile SingularAttribute<Matricula, Boolean> pago;

}