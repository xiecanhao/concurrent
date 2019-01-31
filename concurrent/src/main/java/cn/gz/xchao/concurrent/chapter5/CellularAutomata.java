package cn.gz.xchao.concurrent.chapter5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CellularAutomata {
	private final Board mainBoard;
	private final CyclicBarrier barrier;
	private final Worker[] workers;

	public CellularAutomata(Board board) {
		this.mainBoard = board;
		int count = Runtime.getRuntime().availableProcessors();
		this.barrier = new CyclicBarrier(count, new Runnable() {
			public void run() {
				mainBoard.commitNewValues();
			}
		});
		this.workers = new Worker[count];
		for (int i = 0; i < count; i++)
			workers[i] = new Worker(mainBoard.getSubBoard(count, i));
	}

	private class Worker implements Runnable {
		private final Board board;

		public Worker(Board board) {
			super();
			this.board = board;
		}

		public void run() {
			while (!board.hasConverged()) {
				for (int x = 0; x < board.getMaxX(); x++)
					for (int y = 0; y < board.getMaxY(); y++)
						board.setNewValue(x, y, computeValue(x, y));
				try {
					barrier.await();
				} catch (InterruptedException e) {
					return;
				} catch (BrokenBarrierException ex) {
					return;
				}
			}

		}

		private Object computeValue(int x, int y) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	public void start() {
		for (int i = 0; i < workers.length; i++)
			new Thread(workers[i]).start();
		mainBoard.waitForConvergence();
	}

}
