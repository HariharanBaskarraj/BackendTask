package com.presentation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.presentation.model.Field;

/**
 * @author HariharanB
 *
 */
@Repository
public interface IFieldRepository extends JpaRepository<Field, Integer> {

	/**
	 * @param tempId
	 * @return the list of field names  that are in the matching template by id
	 */
	@Query(value = "select * from field where temp_id = ?1", nativeQuery = true)
	List<Field> getAllFields(int tempId);
	
	/**
	 * @param tempName
	 * @return the list of field names  that are in the matching template by name
	 */
	@Query(value="select f.field_name from field f inner join template t where f.temp_id=t.template_id and t.template_name=?1", nativeQuery = true)
	List<String> getFieldByName(String tempName);
}
 