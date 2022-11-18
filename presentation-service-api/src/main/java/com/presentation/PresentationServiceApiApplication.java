package com.presentation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 
 * @author HariharanB
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class PresentationServiceApiApplication implements CommandLineRunner {

//	private IPresentationService presentationService;
//
//	@Autowired
//	public void setPresentationService(IPresentationService presentationService) {
//		this.presentationService = presentationService;
//	}

	public static void main(String[] args) {
		SpringApplication.run(PresentationServiceApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Template templateOne = new Template("Bullet", "TemplateOne.pptx", "output1.pptx",
//				Arrays.asList(new Field("heading"), new Field("bullets"), new Field("pass")));
//		presentationService.addTemplate(templateOne);
//
//		Template templateTwo = new Template("Training", "TemplateTwo.pptx", "output2.pptx",
//				Arrays.asList(new Field("header"), new Field("monthOne"), new Field("monthTwo"),
//						new Field("monthThree"), new Field("monthFour"), new Field("courseOne"), new Field("courseTwo"),
//						new Field("courseThree"), new Field("courseFour"), new Field("company"), new Field("pass")));
//		presentationService.addTemplate(templateTwo);
//
//		Template templateThree = new Template("Environment", "TemplateThree.pptx", "output3.pptx", Arrays.asList(new Field("bighead"), new Field("findOne"),
//				new Field("findTwo"), new Field("findThree"), new Field("footer"), new Field("pass")));
//		presentationService.addTemplate(templateThree);
//
//		Template templateFour = new Template("Quote", "TemplateFour.pptx", "output4.pptx",
//				Arrays.asList(new Field("quote"), new Field("addon"), new Field("pass")));
//		presentationService.addTemplate(templateFour);
	}

}
