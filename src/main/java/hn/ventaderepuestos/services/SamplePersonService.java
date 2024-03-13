package hn.ventaderepuestos.services;

import hn.ventaderepuestos.data.Proveedor;
import hn.ventaderepuestos.data.SamplePersonRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SamplePersonService {

    private final SamplePersonRepository repository;

    public SamplePersonService(SamplePersonRepository repository) {
        this.repository = repository;
    }

    public Optional<Proveedor> get(Long id) {
        return repository.findById(id);
    }

    public Proveedor update(Proveedor entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Proveedor> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Proveedor> list(Pageable pageable, Specification<Proveedor> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
