package com.pisight.pimoney.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.models.Container;

public class ContainerDeserializer extends StdDeserializer<Container>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1749542865626497894L;
	public ContainerDeserializer() { 
        this(null); 
    } 
 
    public ContainerDeserializer(Class<?> vc) { 
        super(vc); 
    }

	@SuppressWarnings("unchecked")
	@Override
	public Container deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		
		JsonNode node = jp.getCodec().readTree(jp);
		
		String tag = node.get("tag").asText();
		
		ScriptLogger.writeInfo("tag:: " + tag);
		
		ObjectMapper mapper = new ObjectMapper();
		
		Class cls = null;
		
		try {
			cls = Class.forName(Constants.getTagContainerMap().get(tag));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			ScriptLogger.writeError("Error ", e);
		}
		
		return (Container) mapper.readValue(jp, cls);
	}
}
