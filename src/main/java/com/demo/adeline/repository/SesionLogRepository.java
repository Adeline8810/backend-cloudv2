package com.demo.adeline.repository;



import com.demo.adeline.model.SesionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionLogRepository extends JpaRepository<SesionLog, Long> {
    // Aquí luego podrás hacer métodos como findByUsuarioId o contar visitas por país
}