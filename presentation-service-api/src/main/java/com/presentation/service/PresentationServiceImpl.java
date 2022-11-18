package com.presentation.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.presentation.exception.FieldNotFoundException;
import com.presentation.exception.TemplateNotFoundException;
import com.presentation.model.Field;
import com.presentation.model.PresentationRequest;
import com.presentation.model.Template;
import com.presentation.repository.IFieldRepository;
import com.presentation.repository.ITemplateRepository;

/**
 * @author HariharanB
 *
 */
@Service
public class PresentationServiceImpl implements IPresentationService {

	private ITemplateRepository templateRepository;
	private IFieldRepository fieldRepository;
	private Logger logger = LoggerFactory.getLogger(PresentationServiceImpl.class);

	@Autowired
	public void setTemplateRepository(ITemplateRepository templateRepository) {
		this.templateRepository = templateRepository;
	}

	/**
	 * @param fieldRepository
	 */
	@Autowired
	public void setFieldRepository(IFieldRepository fieldRepository) {
		this.fieldRepository = fieldRepository;
	}

	/**
	 * This method is responsible for
	 * 
	 * @param template
	 */
	@Override
	public Template addTemplate(Template template) {
		return templateRepository.save(template);
	}

	/**
	 * This method is responsible for getting all the field details that are matches the entered template id.
	 * 
	 * @param tempId
	 * 
	 */
	@Override
	public List<Field> getAllFields(Integer tempId) {
		return fieldRepository.getAllFields(tempId);
	}

	/**
	 * This method is responsible for getting all the field details that are matches the entered template name.
	 * 
	 * @param tempName
	 */
	@Override
	public List<String> getFieldByName(String tempName) {
		return fieldRepository.getFieldByName(tempName);
	}

	/**
	 * This method is responsible for getting all the template names that are stored in the database.
	 * 
	 */
	@Override
	public List<String> getTemplateNames() {
		return templateRepository.getTemplateByName();
	}

	/**
	 * This method is responsible for getting all the template details that are stored in the database.
	 * 
	 */
	@Override
	public List<Template> getAllTemplates() {
		return templateRepository.findAll();
	}

	/**
	 * This method is responsible for getting the source path from where the file needs to be re getting the destination path where the file needs to be retrieved for processing 
	 *  
	 * @param tempName
	 */
	@Override
	public String getSourcePath(String tempName) {
		return templateRepository.getSourcePath(tempName);
	}
	
	/**
	 * This method is responsible for getting the destination path where the file needs to be saved after processing
	 */
	@Override
	public String getDestinationPath(String tempName) {
		return templateRepository.getDestinationPath(tempName);
	}
	
	/**
	 * This method is responsible for loading the template file to modify the
	 * contents as per the WebServiceRequest.
	 * 
	 * @param fileLocation
	 * @throws IOException 
	 */
	@Override
	public XMLSlideShow readingExistingSlideShow(String fileLocation) throws IOException{
		return new XMLSlideShow(new FileInputStream(fileLocation));
	}

	/**
	 * This method is responsible for filling up the values in the template that is
	 * given in the WebServiceRequest.
	 * 
	 * @param request
	 * @param model       The map that contains the field name and value to be
	 *                    inserted as a key value pair.
	 * @param source      The source file path
	 * @param destination The destination file path
	 * @throws IOException When the files are not available,
	 *                     {@link FieldNotFoundException} is thrown.
	 */
	@Override
	public void alterPresentation(PresentationRequest request, Map<String, Object> model, String source,
			String destination) throws IOException {
		List<String> keys = List.copyOf(model.keySet());
		logger.info("list of keys generated");
		List<String> templates = getTemplateNames();
		logger.info("list of template names generated");
		List<String> fields = getFieldByName(request.getTemplateName());
		logger.info("got the list of fields that match with the template name");

		/*
		 * This conditional is responsible for checking whether the template name that
		 * is given in the WebServiceRequest is available in the database.
		 */
		if (!templates.contains(request.getTemplateName())) {
			logger.debug("Template is not found");
			throw new TemplateNotFoundException("The entered template is not available.");
		}

		/*
		 * This loop is responsible for checking whether the fields that are given in
		 * the WebServiceRequest are available in the presentation template.
		 */
		for (int j = 0; j < fields.size() - 1; j++) {
			if (!fields.contains(keys.get(j))) {
				logger.debug("Field is not found");
				throw new FieldNotFoundException("The entered field is not available in the slide.");
			}
		}

		// Template One is matched.
		if (request.getTemplateName().equals("Bullet")) {
			logger.info("Filling the presentation one");
			XMLSlideShow presentation = readingExistingSlideShow(source +getSourcePath(request.getTemplateName()));
			List<XSLFSlide> slides = presentation.getSlides();
			Iterator<String> fieldMapping = fields.iterator();
			while (fieldMapping.hasNext()) {
				String field = fieldMapping.next();
				XSLFSlide slide = slides.get(0);
				for (int i = 0; i < fields.size(); i++) {
					slide.getPlaceholder(i).clearText();
					String value = (String) model.get(field);
					if (!value.contains(":")) {
						slide.getPlaceholder(i).addNewTextParagraph().addNewTextRun().setText(value);
					} else {
						List<String> delList = Arrays.asList(value.split(":"));
						Iterator<String> it = delList.iterator();
						while (it.hasNext()) {
							XSLFTextParagraph paragraph = slide.getPlaceholder(i).addNewTextParagraph();
							paragraph.setBullet(true);
							paragraph.setIndent(-25.2);
							XSLFTextRun run = paragraph.addNewTextRun();
							run.setText(it.next());
						}
						break;
					}
					field = fieldMapping.next();
					if (fieldMapping.next() == "pass")
						break;
				}
			}
			FileOutputStream out = new FileOutputStream(new File(destination +getDestinationPath(request.getTemplateName())));
			logger.info("Writing the presentation one");
			presentation.write(out);
		}

		// Template Two is matched.
		else if (request.getTemplateName().equals("Training")) {
			logger.info("Filling the presentation two");
			XMLSlideShow presentation = readingExistingSlideShow(source +getSourcePath(request.getTemplateName()));
			List<XSLFSlide> slides = presentation.getSlides();
			Iterator<String> fieldMapping = fields.iterator();
			while (fieldMapping.hasNext()) {
				String field = fieldMapping.next();
				XSLFSlide slide = slides.get(0);
				for (int i = 0; i < fields.size(); i++) {
					slide.getPlaceholder(i).clearText();
					String value = (String) model.get(field);
					if (!value.contains(":")) {
						slide.getPlaceholder(i).addNewTextParagraph().addNewTextRun().setText(value);
					} else {
						List<String> delList = Arrays.asList(value.split(":"));
						Iterator<String> it = delList.iterator();
						while (it.hasNext()) {
							XSLFTextParagraph paragraph = slide.getPlaceholder(i).addNewTextParagraph();
							paragraph.setBullet(true);
							paragraph.setIndent(-25.2);
							XSLFTextRun run = paragraph.addNewTextRun();
							run.setText(it.next());
						}
					}
					field = fieldMapping.next();
					if (!fieldMapping.hasNext())
						break;
				}
			}
			FileOutputStream out = new FileOutputStream(new File(destination +getDestinationPath(request.getTemplateName())));
			logger.info("Writing the presentation two");
			presentation.write(out);
		}

		// Template Three is matched.
		else if (request.getTemplateName().equals("Environment")) {
			logger.info("Filling the presentation three");
			XMLSlideShow presentation = readingExistingSlideShow(source +getSourcePath(request.getTemplateName()));
			List<XSLFSlide> slides = presentation.getSlides();
			Iterator<String> fieldMapping = fields.iterator();
			while (fieldMapping.hasNext()) {
				String field = fieldMapping.next();
				XSLFSlide slide = slides.get(0);
				for (int i = 0; i < fields.size(); i++) {
					slide.getPlaceholder(i).clearText();
					String value = (String) model.get(field);
					if (!value.contains(":")) {
						slide.getPlaceholder(i).addNewTextParagraph().addNewTextRun().setText(value);
					} else {
						List<String> delList = Arrays.asList(value.split(":"));
						Iterator<String> it = delList.iterator();
						while (it.hasNext()) {
							XSLFTextParagraph paragraph = slide.getPlaceholder(i).addNewTextParagraph();
							paragraph.setBullet(true);
							paragraph.setIndent(-25.2);
							XSLFTextRun run = paragraph.addNewTextRun();
							run.setText(it.next());
						}
					}
					field = fieldMapping.next();
					if (!fieldMapping.hasNext())
						break;
				}
			}
			FileOutputStream out = new FileOutputStream(new File(destination +getDestinationPath(request.getTemplateName())));
			logger.info("Writing the presentation three");
			presentation.write(out);
		}

		// Template Four is matched.
		else if (request.getTemplateName().equals("Quote")) {
			logger.info("Filling the presentation four");
			XMLSlideShow presentation = readingExistingSlideShow(source +getSourcePath(request.getTemplateName()));
			List<XSLFSlide> slides = presentation.getSlides();
			Iterator<String> fieldMapping = fields.iterator();
			while (fieldMapping.hasNext()) {
				String field = fieldMapping.next();
				XSLFSlide slide = slides.get(0);
				for (int i = 0; i < fields.size() + 1; i += 2) {
					slide.getPlaceholder(i).clearText();
					String value = (String) model.get(field);
					if (!value.contains(":")) {
						slide.getPlaceholder(i).addNewTextParagraph().addNewTextRun().setText(value);
					} else {
						List<String> delList = Arrays.asList(value.split(":"));
						Iterator<String> it = delList.iterator();
						while (it.hasNext()) {
							XSLFTextParagraph paragraph = slide.getPlaceholder(i).addNewTextParagraph();
							paragraph.setBullet(true);
							paragraph.setIndent(-25.2);
							XSLFTextRun run = paragraph.addNewTextRun();
							run.setText(it.next());
						}
						break;
					}
					field = fieldMapping.next();
					if (!fieldMapping.hasNext())
						break;
				}
			}
			FileOutputStream out = new FileOutputStream(new File(destination +getDestinationPath(request.getTemplateName())));
			logger.info("Writing the presentation four");
			presentation.write(out);
			out.close();
		}
	}

	/**
	 * This method is responsible for downloading the output presentation file and
	 * save it in the Downloads folder of the local computer.
	 * 
	 * @param response
	 * @param fileName The name of the output file
	 * @throws IOException If the file is not available, then
	 *                     {@link FileNotFoundException} is thrown
	 */
	@Override
	public void downloadPresentation(HttpServletResponse response, String fileName) throws IOException {
		File file = new File(fileName);
		logger.info("Checking whether the file is present");
		if (file.exists()) {
			logger.info("Determining the mime type");
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} else {
			logger.debug("File is not found");
			throw new FileNotFoundException(fileName + " does not exists in the path specified");
		}
	}


}
