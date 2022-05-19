package ppl.pl.tower.domain.Specification;

import org.springframework.data.jpa.domain.Specification;
import ppl.pl.tower.domain.Exceptions.AircraftNotFoundException;
import ppl.pl.tower.domain.Model.*;

import javax.persistence.EntityManager;
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
                    throw new AircraftNotFoundException("Search criteria not match");
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
            if (criteria.getAircraftColumnName().equals(AircraftColumnName.CODE_IATA) ||
                    criteria.getAircraftColumnName().equals(AircraftColumnName.CODE_ICAO)) {
                String tempColumnName = "";
                if (criteria.getAircraftColumnName().equals(AircraftColumnName.CODE_IATA)) tempColumnName = Code_.IATA;
                if (criteria.getAircraftColumnName().equals(AircraftColumnName.CODE_ICAO)) tempColumnName = Code_.ICAO;
                Join<Aircraft, Code> aircraftCodeJoin = root.join(Aircraft_.code);
                if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
                    predicates.add(
                            criteriaBuilder.greaterThan(aircraftCodeJoin.get(tempColumnName), criteria.getValue().toString()));
                } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
                    predicates.add(
                            criteriaBuilder.lessThan(aircraftCodeJoin.get(tempColumnName), criteria.getValue().toString()));
                } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                    predicates.add(
                            criteriaBuilder.greaterThanOrEqualTo(aircraftCodeJoin.get(tempColumnName), criteria.getValue().toString()));
                } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                    predicates.add(
                            criteriaBuilder.lessThanOrEqualTo(aircraftCodeJoin.get(tempColumnName), criteria.getValue().toString()));
                } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
                    predicates.add(
                            criteriaBuilder.notEqual(aircraftCodeJoin.get(tempColumnName), criteria.getValue().toString()));
                } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                    predicates.add(
                            criteriaBuilder.equal(aircraftCodeJoin.get(tempColumnName), criteria.getValue().toString()));
                } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                    predicates.add(
                            criteriaBuilder.like(aircraftCodeJoin.get(tempColumnName), "%" + criteria.getValue().toString().toLowerCase() + "%"));
                } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
                    predicates.add(
                            criteriaBuilder.like(aircraftCodeJoin.get(tempColumnName), criteria.getValue().toString().toLowerCase() + "%"));
                } else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
                    predicates.add
                            (criteriaBuilder.like(aircraftCodeJoin.get(tempColumnName), "%" + criteria.getValue().toString().toLowerCase()));
                } else if (criteria.getOperation().equals(SearchOperation.IN)) {
                    predicates.add(
                            criteriaBuilder.in(aircraftCodeJoin.get(tempColumnName)).value(criteria.getValue())
                    );
                } else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
                    predicates.add(
                            criteriaBuilder.not(aircraftCodeJoin.get(tempColumnName)).in(criteria.getValue()));
                } else {
                    throw new AircraftNotFoundException("Search criteria not match");
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
            if (criteria.getAircraftColumnName().equals(AircraftColumnName.ENGINE_TYPE)) {
                EngineType engineType = null;
                switch(criteria.getValue().toString()) {
                    case "JET":
                        engineType = EngineType.JET;
                        break;
                    case "TURBOPROP":
                        engineType = EngineType.TURBOPROP;
                        break;
                    default:
                        throw new AircraftNotFoundException("Search criteria not match");
                }
                if (criteria.getOperation().equals(SearchOperation.EQUAL) && engineType != null) {
                    predicates.add(
                            criteriaBuilder.equal(root.get(Aircraft_.ENGINE_TYPE), engineType));
                } else {

                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        }
        return null;
    }

    public static Specification<Aircraft> getAircraftByCode(String iataString) {
        return (root, query, criteriaBuilder) -> {
            Join<Aircraft, Code> codeListJoin = root.join(Aircraft_.code);
            return criteriaBuilder.like(codeListJoin.get(Code_.IATA), "%" + iataString + "%");
        };
    }
}
