package br.com.sample.controller;

import static capital.scalable.restdocs.AutoDocumentation.description;
import static capital.scalable.restdocs.AutoDocumentation.methodAndPath;
import static capital.scalable.restdocs.AutoDocumentation.pathParameters;
import static capital.scalable.restdocs.AutoDocumentation.requestFields;
import static capital.scalable.restdocs.AutoDocumentation.requestParameters;
import static capital.scalable.restdocs.AutoDocumentation.responseFields;
import static capital.scalable.restdocs.AutoDocumentation.sectionBuilder;
import static capital.scalable.restdocs.SnippetRegistry.AUTHORIZATION;
import static capital.scalable.restdocs.SnippetRegistry.CURL_REQUEST;
import static capital.scalable.restdocs.SnippetRegistry.DESCRIPTION;
import static capital.scalable.restdocs.SnippetRegistry.HTTP_RESPONSE;
import static capital.scalable.restdocs.SnippetRegistry.METHOD_PATH;
import static capital.scalable.restdocs.SnippetRegistry.PATH_PARAMETERS;
import static capital.scalable.restdocs.SnippetRegistry.REQUEST_FIELDS;
import static capital.scalable.restdocs.SnippetRegistry.REQUEST_PARAMETERS;
import static capital.scalable.restdocs.SnippetRegistry.RESPONSE_FIELDS;
import static capital.scalable.restdocs.jackson.JacksonResultHandlers.prepareJackson;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.limitJsonArrayLength;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.replaceBinaryContent;
import static java.lang.System.getProperty;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.restdocs.cli.CliDocumentation.curlRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerControllerTest {

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation(getProperty("org.springframework.restdocs.outputDir"));

	@Autowired
	private WebApplicationContext context;

	@Autowired
	protected ObjectMapper objectMapper;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = webAppContextSetup(this.context)
				.alwaysDo(prepareJackson(this.objectMapper))
				.alwaysDo(document("{method-name}",
						preprocessRequest(),
						preprocessResponse(
								replaceBinaryContent(),
								limitJsonArrayLength(this.objectMapper),
								prettyPrint())))
				.apply(documentationConfiguration(this.restDocumentation)
						.snippets()
						.withDefaults(
								curlRequest(),
								httpRequest(),
								httpResponse(),
								requestFields(),
								responseFields(),
								pathParameters(),
								requestParameters(),
								description(),
								methodAndPath(),
								sectionBuilder()
										.snippetNames(
												METHOD_PATH,
												DESCRIPTION,
												CURL_REQUEST,
												AUTHORIZATION,
												PATH_PARAMETERS,
												REQUEST_PARAMETERS,
												REQUEST_FIELDS,
												RESPONSE_FIELDS,
												HTTP_RESPONSE)
										.skipEmpty(true)
										.build()))
				.build();
	}

	@Test
	public void customers() throws Exception {
		this.mockMvc.perform(get("/customers"))
				.andExpect(status().isOk());
	}

}