package cn.gz.xchao.concurrent.chapter6;

import java.util.Timer;
import java.util.TimerTask;

public class OutOftime {
	public static void main(String[] args) throws InterruptedException {
		Timer timer = new Timer();
		timer.schedule(new ThrowTask(), 1);
		Thread.currentThread().sleep(1000);
		timer.schedule(new ThrowTask(), 1);
		Thread.currentThread().sleep(5000);
	}

	static class ThrowTask extends TimerTask {
		@Override
		public void run() {
			throw new RuntimeException();
		}
	}
}
