package info.elexis.server.core.connector.elexis.jpa.model.annotated;

import info.elexis.server.core.connector.elexis.jpa.model.annotated.Kontakt;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.2.v20151217-rNA", date="2016-03-15T09:14:29")
@StaticMetamodel(LabItem.class)
public class LabItem_ { 

    public static volatile SingularAttribute<LabItem, String> code;
    public static volatile SingularAttribute<LabItem, String> visible;
    public static volatile SingularAttribute<LabItem, String> title;
    public static volatile SingularAttribute<LabItem, String> type;
    public static volatile SingularAttribute<LabItem, Integer> priority;
    public static volatile SingularAttribute<LabItem, String> loinccode;
    public static volatile SingularAttribute<LabItem, Kontakt> labor;
    public static volatile SingularAttribute<LabItem, String> billingCode;
    public static volatile SingularAttribute<LabItem, String> referenceMale;
    public static volatile SingularAttribute<LabItem, String> unit;
    public static volatile SingularAttribute<LabItem, String> referenceFemaleOrTx;
    public static volatile SingularAttribute<LabItem, String> formula;
    public static volatile SingularAttribute<LabItem, String> digits;
    public static volatile SingularAttribute<LabItem, String> export;
    public static volatile SingularAttribute<LabItem, String> group;

}