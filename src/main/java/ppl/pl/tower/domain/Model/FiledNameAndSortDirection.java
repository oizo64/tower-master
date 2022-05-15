package ppl.pl.tower.domain.Model;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class FiledNameAndSortDirection {
    private String searchString;
    private String orderColumn;
    private Sort.Direction direct;
}
