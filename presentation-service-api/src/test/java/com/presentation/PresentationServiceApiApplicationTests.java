package com.presentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.presentation.repository.IFieldRepository;
import com.presentation.repository.ITemplateRepository;
import com.presentation.service.PresentationServiceImpl;

@SpringBootTest
@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
class PresentationServiceApiApplicationTests {
	@Mock
	private IFieldRepository fieldRepository;
	@Mock
	private ITemplateRepository templateRepository;
	@InjectMocks
	private PresentationServiceImpl presentationServiceImpl;
	
	@Test
	@DisplayName("Test Source Path")
	public void getSourcepathTest() {
		when(templateRepository.getSourcePath("Bullet")).thenReturn("TemplateOne.pptx");
		when(templateRepository.getSourcePath("Training")).thenReturn("TemplateTwo.pptx");

		assertEquals("TemplateOne.pptx",presentationServiceImpl.getSourcePath("Bullet"));
		assertNotEquals("TemplateThree.pptx", presentationServiceImpl.getSourcePath("Training"));

	}                     
	
	@Test
	@DisplayName("Test field names")
	public void getFieldByNameTest(){
		when(fieldRepository.getFieldByName("Bullet")).thenReturn(Stream.of("heading","bullets","pass").collect(Collectors.toList()));
		when(fieldRepository.getFieldByName("Quote")).thenReturn(Stream.of("quote","addon","pass").collect(Collectors.toList()));

		
		List<String> expectedEquals = Stream.of("heading","bullets","pass").collect(Collectors.toList());
		List<String> expectedNotEquals = Stream.of("quote","bullets","pass").collect(Collectors.toList());
		assertEquals(expectedEquals, presentationServiceImpl.getFieldByName("Bullet"));
		assertNotEquals(expectedNotEquals, presentationServiceImpl.getFieldByName("Quote"));

	}
	
	@Test
	@DisplayName("Test target file does not exists")
	public void downloadPresentationTest() {
		assertThrows(FileNotFoundException.class, ()->{presentationServiceImpl.readingExistingSlideShow("hi.pptx");});
	}
}
