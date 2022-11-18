package com.presentation.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.presentation.model.Field;
import com.presentation.model.PresentationRequest;
import com.presentation.model.Template;
import com.presentation.service.PresentationServiceImpl;

/**
 * The controller class that maps the url to the methods.
 * 
 * @author HariharanB
 *
 */
@RestController
@RequestMapping("/presentation")
public class PresentationController {

	private PresentationServiceImpl presentationService;
	@Value("${presentation.source.template}")
	private String sourcePath;
	@Value("${presentation.destination.template}")
	private String destinationPath;
	private Logger logger = LoggerFactory.getLogger(PresentationController.class);

	/**
	 * 
	 * @param pptService
	 */
	@Autowired
	public void setPptService(PresentationServiceImpl pptService) {
		this.presentationService = pptService;
	}

	/**
	 *  This method is responsible for downloading the output file 
	 *  
	 * @param response
	 * @param fileName
	 * @throws IOException
	 */
	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<Void> downloadResource(HttpServletResponse response,
			@PathVariable("fileName") String fileName) throws IOException {
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "Downloads the output file");
		header.add("info", "API-Download");
		logger.info("In Controller, Downloading " + fileName);
		String file = destinationPath + fileName;
		presentationService.downloadPresentation(response, file);
		return ResponseEntity.status(HttpStatus.OK).headers(header).build();

	}

	/**
	 * This method is responsible for getting all the templates details
	 * @return
	 */
	@GetMapping("/templates")
	public ResponseEntity<List<Template>> getTemplates() {
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "Gets all the template details");
		header.add("info", "API-Get Template details");
		logger.info("In Controller, Retreiving templates");
		List<Template> templates = presentationService.getAllTemplates();
		return ResponseEntity.ok().headers(header).body(templates);

	}

	/**
	 * This method is responsible for getting all the field details
	 * @param templateId
	 * @return
	 */
	@GetMapping("/fields/{templateId}")
	public ResponseEntity<List<Field>> getFields(@PathVariable("templateId") Integer templateId) {
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "Gets all the field details");
		header.add("info", "API-Get Field details");
		logger.info("In Controller, Retreiving fields");
		List<Field> fields = presentationService.getAllFields(templateId);
		return ResponseEntity.ok().headers(header).body(fields);

	}

	/**
	 * This method is responsible for filling the presentation with the values entered by the user
	 * @param request
	 * @throws IOException
	 */
	@PostMapping("/fill")
	public ResponseEntity<Void> fillPresentation(@RequestBody PresentationRequest request) throws IOException {
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "Fills the presentation with the values entered by the user");
		header.add("info", "API-fills the presentation");
		logger.info("In Controller, Processing the input request");
		List<Field> fields = request.getFields();
		Map<String, Object> model = fields.stream().collect(Collectors.toMap(Field::getFieldName, Field::getValue));
		presentationService.alterPresentation(request, model, sourcePath, destinationPath);
		return ResponseEntity.status(HttpStatus.OK).headers(header).build();

	}
}