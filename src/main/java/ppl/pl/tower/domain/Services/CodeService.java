package ppl.pl.tower.domain.Services;

import org.springframework.stereotype.Service;
import ppl.pl.tower.domain.Repository.CodeRepo;

@Service
public class CodeService {
    private final CodeRepo codeRepo;

    public CodeService(CodeRepo codeRepo) {
        this.codeRepo = codeRepo;
    }
}
