package cn.gz.xchao.concurrent.chapter6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class LifecycleWebServer {
	private final ExecutorService exec = Executors.newFixedThreadPool(10);

	public void start() throws IOException {
		ServerSocket socket = new ServerSocket(80);
		while (!exec.isShutdown()) {
			try {
				final Socket conn = socket.accept();
				exec.execute(new Runnable() {
					public void run() {
						handleRequest(conn);
					}

				});
			} catch (RejectedExecutionException e) {
				if ((exec.isShutdown())) {
					System.err.println(
							"task submission rejected " + e.getMessage());
				}
			}
		}
	}

	public void stop() {
		exec.shutdown();
	}

	private void handleRequest(Socket conn) {
		if (isShutdown(conn)) {

		} else {
			// to do sth
		}
	}

	private boolean isShutdown(Socket conn) {
		// TODO Auto-generated method stub
		return false;
	}
}
