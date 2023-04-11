package com.example.employez.repository;

import com.example.employez.domain.entity_class.CacheData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheDataRepository extends CrudRepository<CacheData,String> {

}
