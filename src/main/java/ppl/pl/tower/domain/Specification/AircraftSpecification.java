package ppl.pl.tower.domain.Specification;

import org.springframework.data.jpa.domain.Specification;
import ppl.pl.tower.domain.Exceptions.SearchCriteriaNotMatch;
import ppl.pl.tower.domain.Model.*;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AircraftSpecification implements Specification<Aircraft> {
    private final List<SearchCriteria> searchCriteriaList;

    public AircraftSpecification() {
        this.searchCriteriaList = new ArrayList<>();
    }


    public void add(SearchCriteria criteria) {
        searchCriteriaList.add(criteria);
    }


    @Override
    public Predicate toPredicate(Root<Aircraft> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria criteria : searchCriteriaList) {
            if (criteria.getAircraftColumnName().equals(AircraftColumnName.ID) ||
                    criteria.getAircraftColumnName().equals(AircraftColumnName.MODEL_NAME) ||
                    criteria.getAircraftColumnName().equals(AircraftColumnName.MANUFACTURER)) {
                if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
                    predicates.add(
                            criteriaBuilder.greaterThan(
                                    root.get(criteria.getAircraftColumnName().toString().toLowerCase(Locale.ROOT)),
                                    criteria.getValue().toString())
                    );
                } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
                    predicates.add(
                            criteriaBuilder.lessThan(
                                    root.get(criteria.getAircraftColumnName().toString().toLowerCase(Locale.ROOT)),
                                    criteria.getValue().toString())
                    );
                } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                    predicates.add(
                            criteriaBuilder.greaterThanOrEqualTo(
                                    root.get(criteria.getAircraftColumnName().toString().toLowerCase(Locale.ROOT)),
                                    criteria.getValue().toString()
                            )
                    );
                } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                    predicates.add(
                            criteriaBuilder.lessThanOrEqualTo(
                                    root.get(criteria.getAircraftColumnName().toString().toLowerCase(Locale.ROOT)),
                                    criteria.getValue().toString()
                            ));
                } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
                    predicates.add(
                            criteriaBuilder.notEqual(root.get(
                                    criteria.getAircraftColumnName().toString().toLowerCase(Locale.ROOT)), criteria.getValue())
                    );
                } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                    predicates.add(
                            criteriaBuilder.equal(root.get(
                                    criteria.getAircraftColumnName().toString().toLowerCase(Locale.ROOT)), criteria.getValue()));
                } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                    predicates.add(
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get(criteria.getAircraftColumnName().toString().toLowerCase(Locale.ROOT)))
                                    , "%" + criteria.getValue().toString().toLowerCase() + "%")
                    );
                } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
                    predicates.add(
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get(criteria.getAircraftColumnName().toString().toLowerCase(Locale.ROOT))),
                                    criteria.getValue().toString().toLowerCase() + "%")
                    );
                } else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
                    predicates.add
                            (criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get(criteria.getAircraftColumnName().toString().toLowerCase(Locale.ROOT))),
                                    "%" + criteria.getValue().toString().toLowerCase())
                            );
                } else if (criteria.getOperation().equals(SearchOperation.IN)) {
                    predicates.add(
                            criteriaBuilder.in(
                                    root.get(criteria.getAircraftColumnName().toString().toLowerCase(Locale.ROOT))
                            ).value(criteria.getValue())
                    );
                } else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
                    predicates.add(
                            criteriaBuilder.not(
                                    root.get(criteria.getAircraftColumnName().toString().toLowerCase(Locale.ROOT))
                            ).in(criteria.getValue()));
                } else {
                    throw new SearchCriteriaNotMatch("Search criteria not match");
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }

            if (criteria.getAircraftColumnName().equals(AircraftColumnName.IATA) ||
                    criteria.getAircraftColumnName().equals(AircraftColumnName.ICAO)) {
                Join<Aircraft, Code> aircraftCodeJoin = root.join(Aircraft_.code);



                if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
                    predicates.add(
                            criteriaBuilder.greaterThan(aircraftCodeJoin.get(criteria.getAircraftColumnName().label.toLowerCase(Locale.ROOT)), criteria.getValue().toString()));
                } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
                    predicates.add(
                            criteriaBuilder.lessThan(aircraftCodeJoin.get(criteria.getAircraftColumnName().label), criteria.getValue().toString()));
                } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                    predicates.add(
                            criteriaBuilder.greaterThanOrEqualTo(aircraftCodeJoin.get(criteria.getAircraftColumnName().label.toLowerCase(Locale.ROOT)), criteria.getValue().toString()));
                } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                    predicates.add(
                            criteriaBuilder.lessThanOrEqualTo(aircraftCodeJoin.get(criteria.getAircraftColumnName().label.toLowerCase(Locale.ROOT)), criteria.getValue().toString()));
                } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
                    predicates.add(
                            criteriaBuilder.notEqual(aircraftCodeJoin.get(criteria.getAircraftColumnName().label.toLowerCase(Locale.ROOT)), criteria.getValue().toString()));
                } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                    predicates.add(
                            criteriaBuilder.equal(aircraftCodeJoin.get(criteria.getAircraftColumnName().label.toLowerCase(Locale.ROOT)), criteria.getValue().toString()));
                } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                    predicates.add(
                            criteriaBuilder.like(aircraftCodeJoin.get(criteria.getAircraftColumnName().label.toLowerCase(Locale.ROOT)), "%" + criteria.getValue().toString() + "%"));
                } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
                    predicates.add(
                            criteriaBuilder.like(aircraftCodeJoin.get(criteria.getAircraftColumnName().label.toLowerCase(Locale.ROOT)), criteria.getValue().toString() + "%"));
                } else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
                    predicates.add
                            (criteriaBuilder.like(aircraftCodeJoin.get(criteria.getAircraftColumnName().label.toLowerCase(Locale.ROOT)), "%" + criteria.getValue().toString()));
                } else if (criteria.getOperation().equals(SearchOperation.IN)) {
                    predicates.add(
                            criteriaBuilder.in(aircraftCodeJoin.get(criteria.getAircraftColumnName().label.toLowerCase(Locale.ROOT))).value(criteria.getValue())
                    );
                } else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
                    predicates.add(
                            criteriaBuilder.not(aircraftCodeJoin.get(criteria.getAircraftColumnName().label.toLowerCase(Locale.ROOT))).in(criteria.getValue()));
                } else {
                    throw new SearchCriteriaNotMatch("Search criteria not match");
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
            if (criteria.getAircraftColumnName().equals(AircraftColumnName.ENGINE_TYPE)) {
                EngineType engineType = switch (criteria.getValue().toString()) {
                    case "JET" -> EngineType.JET;
                    case "TURBOPROP" -> EngineType.TURBOPROP;
                    default -> throw new SearchCriteriaNotMatch("Search criteria not match");
                };
                if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                    predicates.add(
                            criteriaBuilder.equal(root.get(Aircraft_.ENGINE_TYPE), engineType));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        }
        throw new SearchCriteriaNotMatch("Search criteria not match");
    }
}
