package hn.ventaderepuestos.services;

import hn.ventaderepuestos.data.Repuesto;
import hn.ventaderepuestos.data.SampleBookRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SampleBookService {

    private final SampleBookRepository repository;

    public SampleBookService(SampleBookRepository repository) {
        this.repository = repository;
    }

    public Optional<Repuesto> get(Long id) {
        return repository.findById(id);
    }

    public Repuesto update(Repuesto entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Repuesto> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Repuesto> list(Pageable pageable, Specification<Repuesto> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
