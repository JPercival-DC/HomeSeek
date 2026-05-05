package com.homeseek26.homeseeksystem.repository;


import com.homeseek26.homeseeksystem.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {

}
