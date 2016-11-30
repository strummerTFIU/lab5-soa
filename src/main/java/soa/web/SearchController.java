package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
public class SearchController {

	@Autowired
	  private ProducerTemplate producerTemplate;

	@RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {
		Map<String, Object> header = new HashMap<>();

		int indice = q.lastIndexOf("max:");
		if (indice != -1) {
			int max = Integer.parseInt((q.substring(indice + 4, q.length())));
			q = q.substring(0, indice - 1);
			header.put("CamelTwitterCount", max);
		}

		header.put("CamelTwitterKeywords", q);
        return producerTemplate.requestBodyAndHeaders("direct:search", "", header);
    }
}