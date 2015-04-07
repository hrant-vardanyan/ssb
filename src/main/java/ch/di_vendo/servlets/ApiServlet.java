package ch.di_vendo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import ch.di_vendo.dao.OlxDAO;
import ch.di_vendo.model.Ssb;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class ApiServlet
 */
@WebServlet("/api/*")
public class ApiServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();

	/**
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json; charset=UTF-8");

		// Get the request URL
		String requestURL = request.getRequestURL().toString();

		// Get the params
		String requestParams = StringUtils.substringAfter(requestURL, "ssb/api/").trim();
		
		if (requestParams.matches("\\d+")) {
			Ssb ssb = OlxDAO.getInstance().idQuery(Integer.parseInt(requestParams));
			if(ssb == null){
				response.getWriter().println("{ no such job }");
			}else{
				response.getWriter().println(jobToJsonObject(ssb));
			}
			return;
		}
		
		
		if(requestParams.equals("numofjobs")){
			response.getWriter().println("{\"jobs\": \"" + OlxDAO.getInstance().countOfJobs() + "\"}");
			return;
		}
		
		// Choose API method to call

		if (requestParams.startsWith("syncfrom/") || requestParams.startsWith("deletedsince/")) {
			
			if (requestParams.matches("syncfrom/\\d+")) {
				try {
					String timeStampStr = StringUtils.substringAfter(requestParams, "syncfrom/").trim();
					long timestamp = Long.parseLong(timeStampStr);
					List<Ssb> syncfromQuery = OlxDAO.getInstance().syncfromQuery(timestamp);
					JsonArray jsonArraySecond = jobListToJsonArray(syncfromQuery);
					response.getWriter().print(jsonArraySecond.toString());
				} catch (NumberFormatException e) {
					response.getWriter().print("{ error }");
				}
			}else if (requestParams.matches("syncfrom/\\d+/\\d+/\\d+")) {
				
				try {
					String params = StringUtils.substringAfter(requestParams, "syncfrom/").trim();
					String timeStampStr = StringUtils.substringBefore(params, "/").trim();
					long timestamp = Long.parseLong(timeStampStr);
					String skipAndLimit = StringUtils.substringAfter(requestParams, String.valueOf(timestamp) + "/").trim();
					String skipStr = StringUtils.substringBefore(skipAndLimit, "/").trim();
					String limitStr = StringUtils.substringAfter(skipAndLimit, skipStr + "/").trim();
					int skip = Integer.parseInt(skipStr);
					int limit = Integer.parseInt(limitStr);
					List<Ssb> skipLimitSyncfromQuery = OlxDAO.getInstance().skipLimitSyncfromQuery(skip, limit, timestamp);
					JsonArray jsonArraySecond = jobListToJsonArray(skipLimitSyncfromQuery);
					response.getWriter().print(jsonArraySecond.toString());
				} catch (Exception e) {
					e.printStackTrace();
					response.getWriter().print("{ error }");
				}
				
				
			}else if (requestParams.matches("syncfrom/\\d+/numofjobs")) {
				String timeStampStr = StringUtils.substringBetween(requestParams, "syncfrom/","/numofjobs").trim();
				long timestamp = Long.parseLong(timeStampStr);
				List<Ssb> syncfromQuery = OlxDAO.getInstance().syncfromQuery(timestamp);
				response.getWriter().println("{\"jobs\": \"" + syncfromQuery.size() + "\"}");
				
			} else if (!requestParams.contains("deletedsince/")) {
				response.getWriter().print("{ error }");
			}
			
			
			if (requestParams.matches("deletedsince/\\d+")) {
				try {
					String timeStampStr = StringUtils.substringAfter(requestParams, "deletedsince/").trim();
					long timestamp = Long.parseLong(timeStampStr);
					List<Ssb> deletedsinceQueryList = OlxDAO.getInstance().deletedsinceQuery(timestamp);
					JsonArray jsonArraySecond = jobListToJsonArray(deletedsinceQueryList);
					response.getWriter().print(jsonArraySecond.toString());
				} catch (NumberFormatException e) {
					response.getWriter().print("{ error }");
				}
			} else if (requestParams.matches("deletedsince/\\d+/\\d+/\\d+")) {
				
				try {
					String params = StringUtils.substringAfter(requestParams, "deletedsince/").trim();
					String timeStampStr = StringUtils.substringBefore(params, "/").trim();
					long timestamp = Long.parseLong(timeStampStr);
					String skipAndLimit = StringUtils.substringAfter(requestParams, String.valueOf(timestamp) + "/").trim();
					String skipStr = StringUtils.substringBefore(skipAndLimit, "/").trim();
					String limitStr = StringUtils.substringAfter(skipAndLimit, skipStr + "/").trim();
					int skip = Integer.parseInt(skipStr);
					int limit = Integer.parseInt(limitStr);
					List<Ssb> skipLimitSyncfromQuery = OlxDAO.getInstance().skipLimitdeletedsinceQuery(skip, limit, timestamp);
					JsonArray jsonArraySecond = jobListToJsonArray(skipLimitSyncfromQuery);
					response.getWriter().print(jsonArraySecond.toString());
				} catch (Exception e) {
					response.getWriter().print("{ error }");
				}
				
				
			}else if (requestParams.matches("deletedsince/\\d+/numofjobs")) {
				String timeStampStr = StringUtils.substringBetween(requestParams, "deletedsince/","/numofjobs").trim();
				long timestamp = Long.parseLong(timeStampStr);
				List<Ssb> deletedsinceQuery = OlxDAO.getInstance().deletedsinceQuery(timestamp);
				response.getWriter().println("{\"jobs\": \"" + deletedsinceQuery.size() + "\"}");
				
			} else if (!requestParams.contains("syncfrom/")) {
				response.getWriter().print("{ error }");
			}

		} else if (requestParams.matches("\\d+/\\d+")) {
			try {
				String skipStr = StringUtils.substringBefore(requestParams, "/").trim();
				String limitStr = StringUtils.substringAfter(requestParams, "/").trim();
				if (limitStr.contains("/")) {
					limitStr = limitStr.replaceAll("/", "").trim();
				}
				int skip = Integer.parseInt(skipStr);
				int limit = Integer.parseInt(limitStr);
				List<Ssb> skipLimitQueryList = OlxDAO.getInstance().skipLimitQuery(skip, limit);
				JsonArray jsonArrayFirst = jobListToJsonArray(skipLimitQueryList);
				response.getWriter().print(jsonArrayFirst.toString());
			} catch (NumberFormatException e) {
				response.getWriter().print("{ error }");
			}
		} else if (requestParams.startsWith("")) {

		} else {
			response.getWriter().print("{ error }");
		}

	}

	/**
	 * Casts list of jobs to json array.
	 * 
	 * @param jobList
	 *            List of jobs to cast.
	 * @return Json array of jobs
	 */
	private JsonArray jobListToJsonArray(List<Ssb> jobList) {
		JsonElement jobJson = gson.toJsonTree(jobList, new TypeToken<List<Ssb>>() {
		}.getType());
		if (!jobJson.isJsonArray()) {
			// fail appropriately
		}
		JsonArray jsonArray = jobJson.getAsJsonArray();
		return jsonArray;
	}
	
	/**
	 * 
	 * @param ssb
	 * @return
	 */
	private String jobToJsonObject(Ssb ssb) {

		Gson gson = new Gson();

		String json = gson.toJson(ssb);

		return json;
	}


}
