package com.presentation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.presentation.model.Template;

/**
 * @author HariharanB
 *
 */
@Repository
public interface ITemplateRepository extends JpaRepository<Template, Integer> {

	/**
	 * @return a list of templates that are available.
	 */
	@Query(value = "select template_name from template", nativeQuery = true)
	List<String> getTemplateByName();

	/**
	 * @param tempName
	 * @return the source path
	 */
	@Query(value="select source from template where template_name=?1", nativeQuery = true)
	String getSourcePath(String tempName);

	/**
	 * @param tempName
	 * @return the destination pah
	 */
	@Query(value="select destination from template where template_name=?1", nativeQuery = true)
	String getDestinationPath(String tempName);
}
