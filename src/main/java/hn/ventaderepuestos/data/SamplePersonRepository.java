package hn.ventaderepuestos.data;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SamplePersonRepository
        extends
            JpaRepository<Proveedor, Long>,
            JpaSpecificationExecutor<Proveedor> {

}
