package fr.soprasteria.agircarcco.sefiab.repository;

import fr.soprasteria.agircarcco.sefiab.model.Dette;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetteRepository extends JpaRepository<Dette, Long> {

    @Query("SELECT e.name FROM Entreprise e JOIN e.dettes d WHERE d.amount > ?1")
    List<String> findNamesByDebtAmount(Double debtAmount);

    @Query("SELECT d FROM Dette d WHERE d.entreprise.name = ?1")
    List<Dette> findByEntrepriseName(String entrepriseName);

    @Query("SELECT d FROM Dette d JOIN FETCH d.entreprise e WHERE e.name = ?1")
    List<Dette> findByEntrepriseNameWithFetch(String entrepriseName);

    @Query("SELECT e, SUM(d.amount) FROM Dette d JOIN d.entreprise e GROUP BY e")
    List<Object[]> getTotalDebtByEntreprise();

    List<Dette> findByEntreprise(Entreprise entreprise);

    List<Dette> findByAmountGreaterThan(Double amount);

    List<Dette> findByAmountLessThan(Double amount);

    Optional<Dette> findTopByOrderByAmountDesc();

    Optional<Dette> findTopByOrderByAmountAsc();

    @Query("SELECT SUM(d.amount) FROM Dette d WHERE d.entreprise = :entreprise")
    Double sumAmountByEntreprise(@Param("entreprise") Entreprise entreprise);

    Long countByEntreprise(Entreprise entreprise);

    @Query("SELECT AVG(d.amount) FROM Dette d")
    Double findAverageDebt();

    List<Dette> findByAmountBetween(Double startAmount, Double endAmount);

    @Query("SELECT d.entreprise FROM Dette d WHERE d.amount = ?1")
    List<Entreprise> findEntreprisesWithExactDebtAmount(Double amount);

    Boolean existsByAmount(Double amount);
}

