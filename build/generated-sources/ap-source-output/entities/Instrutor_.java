package entities;

import entities.EventosInstrutores;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-09-24T13:27:36")
@StaticMetamodel(Instrutor.class)
public class Instrutor_ { 

    public static volatile SingularAttribute<Instrutor, String> senha;
    public static volatile SingularAttribute<Instrutor, Boolean> tipo;
    public static volatile SingularAttribute<Instrutor, Boolean> administrador;
    public static volatile SingularAttribute<Instrutor, String> rg;
    public static volatile SingularAttribute<Instrutor, String> cpf;
    public static volatile SingularAttribute<Instrutor, String> orgaoExpeditor;
    public static volatile SingularAttribute<Instrutor, String> nome;
    public static volatile SingularAttribute<Instrutor, String> email;
    public static volatile CollectionAttribute<Instrutor, EventosInstrutores> eventosInstrutoresCollection;
    public static volatile SingularAttribute<Instrutor, Integer> idinstrutor;

}