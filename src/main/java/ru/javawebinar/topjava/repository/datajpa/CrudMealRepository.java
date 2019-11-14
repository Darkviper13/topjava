package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    int delete(@Param("id") int id,
               @Param("userId") int userId);

    @Modifying
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId")
    List<Meal> getAll(@Param("userId") int userId);

    @Modifying
    @Query("SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    Meal get(@Param("id") int id,
             @Param("userId") int userId);

    @Query("SELECT m FROM Meal m " +
            "WHERE m.user.id=:userId AND m.dateTime >= :startDate AND m.dateTime < :endDate")
    List<Meal> getBetween(@Param("starDate") LocalDate startDate,
                          @Param("endDate") LocalDate endDate,
                          @Param("userId") int userId);
}