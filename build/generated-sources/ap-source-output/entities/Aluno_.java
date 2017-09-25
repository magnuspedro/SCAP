package entities;

import entities.Chamada;
import entities.ChamadaEvento;
import entities.Matricula;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-09-25T09:36:16")
@StaticMetamodel(Aluno.class)
public class Aluno_ { 

    public static volatile SingularAttribute<Aluno, Boolean> externo;
    public static volatile SingularAttribute<Aluno, String> instituicao;
    public static volatile SingularAttribute<Aluno, String> nome;
    public static volatile SingularAttribute<Aluno, Integer> idaluno;
    public static volatile SingularAttribute<Aluno, String> ra;
    public static volatile SingularAttribute<Aluno, String> rg;
    public static volatile CollectionAttribute<Aluno, Chamada> chamadaCollection;
    public static volatile SingularAttribute<Aluno, String> curso;
    public static volatile SingularAttribute<Aluno, String> cpf;
    public static volatile CollectionAttribute<Aluno, ChamadaEvento> chamadaEventoCollection;
    public static volatile SingularAttribute<Aluno, String> orgaoExpeditor;
    public static volatile CollectionAttribute<Aluno, Matricula> matriculaCollection;
    public static volatile SingularAttribute<Aluno, String> email;

}