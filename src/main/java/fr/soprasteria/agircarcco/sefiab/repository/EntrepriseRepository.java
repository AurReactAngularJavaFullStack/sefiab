package fr.soprasteria.agircarcco.sefiab.repository;

import fr.soprasteria.agircarcco.sefiab.model.Dette;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
    @Query("SELECT e.name FROM Entreprise e WHERE e.debtAmount > ?1")
    List<String> findNamesByDebtAmount(Double debtAmount);


    @Query("SELECT e FROM Entreprise e WHERE e.debtAmount = (SELECT MAX(e2.debtAmount) FROM Entreprise e2)")
    Optional<Entreprise> findWithHighestDebt();

    @Query("SELECT e FROM Entreprise e ORDER BY e.debtAmount DESC")
    List<Entreprise> findAllOrderByDebtDesc();

    Optional<Entreprise> findByName(String name);
    Optional<Entreprise> findById(Long id);
    List<Entreprise> findByDebtAmountGreaterThan(Double debtAmount);
    List<Entreprise> findByDebtAmountLessThan(Double debtAmount);
    List<Entreprise> findByDebtAmountBetween(Double startAmount, Double endAmount);
    List<Entreprise> findByNameContaining(String namePart);
    List<Entreprise> findByNameStartingWith(String namePrefix);
    List<Entreprise> findByNameEndingWith(String nameSuffix);
    List<Entreprise> findByDettesIn(List<Dette> dettes);
    boolean existsByName(String name);
}

