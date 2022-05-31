package ppl.pl.tower.domain.Model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Do uzupełnienia/Rozszerzenia
 *
 * Stworzyć klasyczny moduł typu CRUD dla przedstawionego poniżej modelu.
 * Moduł powinnien udostępniać RESTowe api oraz umożliwiać komunikację z resztą aplikacji
 * Dane powinny być pobierane z bazy danych wpiętej do aplikacji
 *
 */
@Entity
@Table(name = "Aircraft")
@NamedQueries({
        @NamedQuery(name = "Aircraft.findByModelNameIgnoreCaseOrderByModelNameAsc", query = "select a from Aircraft a where upper(a.modelName) = upper(:modelName) order by a.modelName")
})
@Getter
@Setter
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Code code;

    private String modelName;
    private String manufacturer;
    @Enumerated(EnumType.STRING)
    private EngineType engineType;
}
