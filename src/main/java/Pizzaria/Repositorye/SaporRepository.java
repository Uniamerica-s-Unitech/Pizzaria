package Pizzaria.Repositorye;

import Pizzaria.Entiny.Sapor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaporRepository extends JpaRepository<Sapor,Long> {
}
