// repository/TransporteurRepository.java
package com.cmg.back.repository;

import com.cmg.back.model.Transporteur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransporteurRepository extends JpaRepository<Transporteur, Long> {
}
