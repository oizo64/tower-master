package ppl.pl.tower.domain.Specification;

import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.domain.Specification;
import ppl.pl.tower.domain.Model.Aircraft;
import ppl.pl.tower.domain.Model.AircraftColumnName;
import ppl.pl.tower.domain.Model.SearchCriteria;
import ppl.pl.tower.domain.Model.SearchOperation;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class AircraftSpecification implements Specification<Aircraft> {
    private List<SearchCriteria> searchCriteriaList;

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
            if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
                predicates.add(
                        criteriaBuilder.greaterThan(
                                root.get(criteria.getAircraftColumnName()),
                                criteria.getValue().toString())
                );
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
                predicates.add(
                        criteriaBuilder.lessThan(
                                root.get(criteria.getAircraftColumnName()),
                                criteria.getValue().toString())
                );
            } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get(criteria.getAircraftColumnName()),
                                criteria.getValue().toString()
                        )
                );
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get(criteria.getAircraftColumnName()),
                                criteria.getValue().toString()
                        ));
            } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
                predicates.add(
                        criteriaBuilder.notEqual(root.get(
                                criteria.getAircraftColumnName()), criteria.getValue())
                );
            } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                predicates.add(
                        criteriaBuilder.equal(root.get(
                                criteria.getAircraftColumnName()), criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get(criteria.getAircraftColumnName()))
                                , "%" + criteria.getValue().toString().toLowerCase() + "%")
                );
            } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get(criteria.getAircraftColumnName())),
                                criteria.getValue().toString().toLowerCase() + "%")
                );
            } else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
                predicates.add
                        (criteriaBuilder.like(
                                criteriaBuilder.lower(root.get(criteria.getAircraftColumnName())),
                                "%" + criteria.getValue().toString().toLowerCase())
                        );
            } else if (criteria.getOperation().equals(SearchOperation.IN)) {
                predicates.add(
                        criteriaBuilder.in(
                                root.get(criteria.getAircraftColumnName())
                        ).value(criteria.getValue())
                );
            } else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
                predicates.add(
                        criteriaBuilder.not(
                                root.get(criteria.getAircraftColumnName())
                        ).in(criteria.getValue()));
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
