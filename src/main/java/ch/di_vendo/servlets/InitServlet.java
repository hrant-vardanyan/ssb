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

@WebServlet("/init")
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(InitServlet.class);

	protected void doGet(HttpServletRequest request, final HttpServletResponse response) throws ServletException,
			IOException {

		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
		singleThreadExecutor.submit(new Runnable() {

			@Override
			public void run() {
				try {

//					Start start = new Start();
//					start.init();
				} catch (Exception e) {
					LOGGER.error("error with init ", e);
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
