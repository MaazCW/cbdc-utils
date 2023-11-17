package com.opl.cbdc.utils.common;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.codehaus.jackson.map.DeserializationConfig;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

public class MultipleJSONObjectHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(MultipleJSONObjectHelper.class);

	public MultipleJSONObjectHelper() {

	}

	@SuppressWarnings("unchecked")
	public static <T> T getNestedObject(String data, String key, Class<?> clazz) throws IOException {
		org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
		org.codehaus.jackson.JsonNode node = mapper.readTree(data);
		return (T) mapper.readValue(node.get(key), clazz);
	}
	

	@SuppressWarnings("unchecked")
	public static <T> T getObject(String data, String key, Class<?> clazz,String type) throws IOException {
		if("xml".equalsIgnoreCase(type)) {
			data = XML.toJSONObject(data).toString();
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		if (key == null) {
			return (T) mapper.convertValue(data, clazz);
		} else {
			JsonNode node = mapper.readTree(data);
			JsonNode jsonNode = node.get(key);
			return (T) mapper.convertValue(jsonNode, clazz);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getObject(String data, String key, Class<?> clazz) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		if (key == null) {
			return (T) mapper.convertValue(data, clazz);
		} else {
			JsonNode node = mapper.readTree(data);
			return (T) mapper.convertValue(node.get(key), clazz);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getObject(String data, Class<?> clazz) throws IOException {
		org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return (T) mapper.readValue(data, clazz);
	}

	public static List getListOfObjects(String data, String key, Class<?> clazz) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
		if (key != null) {
			JsonNode node = mapper.readTree(data);
			return mapper.readValue(node.get(key).toString(),
					mapper.getTypeFactory().constructCollectionType(List.class, clazz));
		} else {
			return mapper.readValue(data, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getObjectFromString(String data, Class<?> clazz) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return (T) mapper.readValue(data, clazz);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getObjectFromMap(Map map, Class<?> clazz) throws IOException {
		final ObjectMapper mapper = new ObjectMapper(); // jackson's
		mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return (T) mapper.convertValue(map, clazz);
	}

	public static String getStringfromObject(Object object) throws IOException {
		if (object != null) {
			return new ObjectMapper().writeValueAsString(object);
		} else {
			return "{}";
		}
	}

	/*
	 * @SuppressWarnings("unchecked") public static <T extends List<?>> T
	 * getListFromObject(Object obj) { return (T) obj; }
	 *
	 * public static <T> T getListFromMap(List<Map<String, Object>> map, Class<?>
	 * clazz) throws IOException { final ObjectMapper mapper = new ObjectMapper();
	 * // jackson's return mapper.readValue(map,
	 * mapper.getTypeFactory().constructCollectionType(List.class, clazz)); }
	 */

	public static String getStringfromListOfObject(List<?> list) throws IOException {
		if (!OPLUtils.isListNullOrEmpty(list)) {
			final StringWriter sw = new StringWriter();
			new ObjectMapper().writeValue(sw, list);
			return sw.toString();
		} else {
			return "[]";
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getObjectFromStringExtraConfig(String data, Class<?> clazz) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
		return (T) mapper.readValue(data, clazz);
	}

	public static <T> T convertJSONToObject(String response, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
			mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return (T) mapper.readValue(response, clazz);
		} catch (Exception e) {
			logger.error("Exception convertJSONToObject :: ", e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getObjectFromObject(Object data, Class<?> clazz) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return (T) mapper.convertValue(data, clazz);
	}

    public static <T> T getObjectExtraConfig(String data, String key, Class<?> clazz, String type) throws IOException {
        if ("xml".equalsIgnoreCase(type)) {
            data = XML.toJSONObject(data).toString();
            logger.info("data after converting XML to JSON==>" + data);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (key == null) {
            return (T) mapper.readValue(data, clazz);
        } else {
            JsonNode node = mapper.readTree(data);
            JsonNode jsonNode = node.get(key);
            return (T) mapper.convertValue(jsonNode, clazz);
        }
    }

    public static <T> T getObjectByReadValue(String data, String key, Class<?> clazz, String type) throws IOException {
        if ("xml".equalsIgnoreCase(type)) {
            data = XML.toJSONObject(data).toString();
            logger.info("data after converting XML to JSON==>" + data);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (key == null) {
            return (T) mapper.readValue(data, clazz);
        } else {
            JsonNode node = mapper.readTree(data);
            JsonNode jsonNode = node.get(key);
            return (T) mapper.convertValue(jsonNode, clazz);
        }
    }

	public static String XmlToJson(String xmlString) throws IOException {
		if (!OPLUtils.isObjectNullOrEmpty(xmlString)) {
			JSONObject jObject = XML.toJSONObject(xmlString);
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Object json = mapper.readValue(jObject.toString(), Object.class);
			return mapper.writeValueAsString(json);
		} else {
			return "{}";
		}
	}
	public static Map<String, Object> getMapFromString(String value) throws IOException {
		return value != null ? (Map)(new ObjectMapper()).readValue(value, Map.class) : null;
	}
	
	 public static <T> T xmlStringToPojo(String input,Class<T> clazz) {
	    	XmlMapper xmlMapper = new XmlMapper();
		    try {
		    	xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				return xmlMapper.readValue(input, clazz);
			} catch (Exception e) {
					
			}
		    return null;
		}
	    
	    public static String pojoToXMLString(Object object) {
	    	if(OPLUtils.isObjectNullOrEmpty(object)) {
	    		return null;
	    	}
			JacksonXmlModule xmlModule = new JacksonXmlModule();
			xmlModule.setDefaultUseWrapper(false);
			ObjectMapper objectMapper = new XmlMapper(xmlModule);
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			try {
				return objectMapper.writeValueAsString(object);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return null;
		}
	    
	    
	    @SuppressWarnings("unchecked")
		public static <T> T getObjectFromStringTryCache(String data, Class<?> clazz){
	    	try {
	    		ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				return (T) mapper.readValue(data, clazz);
			} catch (Exception e) {
				
				return null;
			}
			
		}
	    
		@SuppressWarnings("unchecked")
		public static <T> T getObjectFromObjects(Object data, Class<?> clazz) throws IOException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			return (T) mapper.convertValue(data, clazz);
		}
}