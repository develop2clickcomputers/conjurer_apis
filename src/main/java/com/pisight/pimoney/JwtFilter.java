package com.pisight.pimoney;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.conn.HttpHostConnectException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.dto.AuthenticationRequestWrapper;
import com.pisight.pimoney.repository.entities.User;
import com.pisight.pimoney.service.K2ServiceImpl;
import com.pisight.pimoney.util.PiHttpClient;
import com.pisight.pimoney.util.ScriptLogger;
import com.pisight.pimoney.util.WebUtil;

@Component("JwtFilter")
public class JwtFilter extends GenericFilterBean {

	@Autowired
	private K2ServiceImpl k2ServiceImpl;
	
	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {

		final AuthenticationRequestWrapper request = new AuthenticationRequestWrapper((HttpServletRequest) req);
		final HttpServletResponse response = (HttpServletResponse) res;
		final String token = request.getHeader("authorization");
		String requestUrl = request.getRequestURI();
		
		String platform = setPlatform(requestUrl);
		
		if ("OPTIONS".equals(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			chain.doFilter(req, res);
			return;
		}
		
		JSONObject jsonObject = parserRequest(request, response);
		String userId = (String) jsonObject.get("userId");
		
		String url = k2ServiceImpl.getConfigurationValue(Constants.AUTH_SERVICE,Constants.AUTH_SERVICE_URL) + "verifyConnection";
		
		PiHttpClient client;
		try {
			client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);
			client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
			client.setDataField("userId", userId);
			client.setDataField("platform", platform);
			client.addHeader("secretkey", Constants.getSECRET_KEY());
			client.addHeader("Authorization", token);
			

			String result = client.getResponseForPostRequest();
			ObjectMapper mapper = new ObjectMapper();
			JSONObject verifyTokenResponse = mapper.readValue(result, JSONObject.class);
			
			if(verifyTokenResponse != null){
				
				int status = (int) verifyTokenResponse.get("status");
				if(status == 0) {
					
					// Check for User Already User of PiMoney if not Create Entry
					synchronized (User.class) {
						User user = k2ServiceImpl.fetchUserById(UUID.fromString(userId));
						if(user == null) {
							
							ScriptLogger.writeInfo("Creating User.....");
							User usr = new User();
							usr.setId(UUID.fromString(userId));
							usr.setUsername((String)verifyTokenResponse.get("message"));
							usr.setPreferredCurrency("SGD");
							k2ServiceImpl.saveUser(usr);						
						}
					}
					chain.doFilter(request, res);
				}else {
					ScriptLogger.writeError("Invalid Request............");
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
				}
			}
		}catch (HttpHostConnectException e) {
			ScriptLogger.writeInfo("Auth Server Connection Exception");
			ScriptLogger.writeError("Exception e = ", e);
			response.setStatus(HttpServletResponse.SC_REQUEST_TIMEOUT);
			response.sendError(HttpServletResponse.SC_REQUEST_TIMEOUT, "Unable to connect host");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ScriptLogger.writeError("Exception e = ", e);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		}
	}
	
	private String setPlatform(String requestUrl) {
		
		if (requestUrl.contains("/api/v1/everest")) {
			ScriptLogger.writeInfo("#### Everest Api Call ########");
			return Constants.AUCTOR_PLATFORM_KEY;
		}
		return Constants.CONJURER_PLATFORM_KEY;
	}

	private JSONObject parserRequest(AuthenticationRequestWrapper request, HttpServletResponse response) throws IOException {
		StringBuilder jb = new StringBuilder();
		try {
			InputStream is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			int i = isr.read();
			while (i != -1) {
				jb.append((char) i);
				i = isr.read();
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		}
		return WebUtil.convertToJSONObject(jb.toString());
	}
}
