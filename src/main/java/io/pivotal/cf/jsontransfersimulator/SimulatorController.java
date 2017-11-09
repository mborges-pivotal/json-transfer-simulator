package io.pivotal.cf.jsontransfersimulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class SimulatorController {

	static final Logger LOG = LoggerFactory.getLogger(SimulatorController.class);
	
	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping("/query")
	public String query(@RequestParam(value = "size", defaultValue = "1") int sizeInKb) {
		return "run a query and measure it";
	}

	@RequestMapping("/generate")
	public String generate(@RequestParam(value = "authors", defaultValue = "1") int numberOfAuthors) {

		String result = createJson(numberOfAuthors).toString();
		LOG.info("size of json in bytes: {}", result.length());

		return result;
	}
		
	////////////////////
	// Helper methods
	////////////////////

	private ArrayNode createJson(int authorNodes) {

		ArrayNode arrayNode = mapper.createArrayNode();

		long start = System.currentTimeMillis();
		
		ArrayNode authorsArray = mapper.createArrayNode();
		for (int i = 1; i <= authorNodes; i++) {
			ObjectNode author1 = mapper.createObjectNode();
			author1.put("firstName", "Hamidul");
			author1.put("middleName", "N" + i);
			author1.put("lastName", "Islam");
			authorsArray.add(author1);
		}
		
		long end = System.currentTimeMillis();
		
		ObjectNode authorArrayNode = mapper.createObjectNode();
		authorArrayNode.putPOJO("authors", authorsArray);

		ObjectNode perfNode = mapper.createObjectNode();
		perfNode.put("authors", ""+authorNodes);
		perfNode.put("authorArrayGenerationInMs", ""+(end-start));
		arrayNode.add(perfNode);
		
		arrayNode.add(authorArrayNode);

		return arrayNode;
	}

}
