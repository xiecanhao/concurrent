package cn.gz.xchao.concurrent.chapter6;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class QueueingFuture<V> extends FutureTask<V> {

	BlockingQueue<QueueingFuture> completionQueue = new ArrayBlockingQueue<QueueingFuture>(
			10);

	public QueueingFuture(Callable<V> c) {
		super(c);
	}

	public QueueingFuture(Runnable t, V r) {
		super(t, r);
	}

	protected void done() {
		completionQueue.add(this);
	}
}
