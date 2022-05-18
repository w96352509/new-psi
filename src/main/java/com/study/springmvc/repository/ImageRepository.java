package com.study.springmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.study.springmvc.entity.Image;

@Repository()
public interface ImageRepository extends JpaRepository<Image, Long> {

	@Query( value = "UPDATE images SET path=? where id=?", nativeQuery = true )
	public void update(@Param("path") String path , @Param("id") Long id);
	
}
