package com.pisight.pimoney.web;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pisight.pimoney.dto.DocumentRequest;
import com.pisight.pimoney.dto.XMLDTO;
import com.pisight.pimoney.service.XMLEditorServiceImpl;
import com.pisight.pimoney.util.ScriptLogger;

@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/securek2")
public class XMLEditorController {

	@Autowired
	private XMLEditorServiceImpl xmlEditorServiceImpl;
	
	@RequestMapping(value = "/getXMLFileContent", method = RequestMethod.POST)
	public JSONObject getXMLFileContent(@RequestBody DocumentRequest request)  {
		
		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("getXMLFileContent Called");
		return xmlEditorServiceImpl.getXMLFileContent(request);
	}
	
	
	@RequestMapping(value = "/updateXMLFile", method = RequestMethod.POST)
	public JSONObject updateXMLFile(@RequestBody XMLDTO request)  {
		
		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("saveXMLFile Called");
		return xmlEditorServiceImpl.updateXMLFile(request);
	}
}
