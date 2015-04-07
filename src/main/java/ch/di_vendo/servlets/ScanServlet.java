package ch.di_vendo.servlets;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet("/scan")
public class ScanServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ScanServlet.class);

	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
			IOException {

		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
		singleThreadExecutor.submit(new Runnable() {

			@Override
			public void run() {
				try {
					// Start start = new Start();
					// start.startCrawling();

				} catch (Exception e) {
					LOGGER.error("error with scan ", e);
				}
			}
		});
		response.getWriter().print("Finished ! ! !");
		singleThreadExecutor.shutdown();
		try {
			singleThreadExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOGGER.error(" fixedThreadPool !!!", e);
		}

	}

}
