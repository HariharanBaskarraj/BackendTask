package com.presentation.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xslf.usermodel.XMLSlideShow;

import com.presentation.model.Field;
import com.presentation.model.PresentationRequest;
import com.presentation.model.Template;

/**
 * @author HariharanB
 *
 */
public interface IPresentationService {
	Template addTemplate(Template template);

	XMLSlideShow readingExistingSlideShow(String fileLocation) throws IOException;

	List<Template> getAllTemplates();

	List<String> getTemplateNames();

	String getSourcePath(String tempName);

	String getDestinationPath(String tempName);

	List<String> getFieldByName(String tempName);

	List<Field> getAllFields(Integer tempId);

	void alterPresentation(PresentationRequest request, Map<String, Object> model, String source, String destination)
			throws IOException;

	void downloadPresentation(HttpServletResponse response, String fileName) throws IOException;
}
