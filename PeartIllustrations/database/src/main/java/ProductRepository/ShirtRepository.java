package ProductRepository;

import models.product.Shirt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShirtRepository extends JpaRepository<Shirt, Long> {
    @Query(value = "SELECT * FROM shirt WHERE LOWER(title) LIKE LOWER(CONCAT('%', :title, '%'))", nativeQuery = true)
    List<Shirt> findByTitleContainingIgnoreCase(String title);
}
