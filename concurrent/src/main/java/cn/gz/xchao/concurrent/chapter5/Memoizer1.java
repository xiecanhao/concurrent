package cn.gz.xchao.concurrent.chapter5;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Memoizer1<A, V> implements Computable<A, V> {
	// private final Map<A, V> cache = new HashMap<A, V>();
	private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	private final Computable<A, V> c;

	public Memoizer1(Computable<A, V> c) {
		super();
		this.c = c;
	}

	/**
	 * 使用concurrentHashMap后，不需要加同步关键字
	 */
	// public V compute(A arg) throws InterruptedException {
	// V result = cache.get(arg);
	// if (result == null) {
	// result = c.compute(arg);
	// cache.put(arg, result);
	// }
	// return result;
	// }

	public V compute(final A arg) throws InterruptedException {
		Future<V> f = cache.get(arg);
		if (f == null) {
			Callable<V> eval = new Callable<V>() {
				public V call() throws InterruptedException {
					// TODO Auto-generated method stub
					return c.compute(arg);
				}
			};
			FutureTask<V> ft = new FutureTask<V>(eval);
			f = cache.putIfAbsent(arg, ft);
			if (f == null) {
				f = ft;
				ft.run();
			}
		}
		try {
			return f.get();
		} catch (CancellationException e) {
			cache.remove(arg, f);
		} catch (ExecutionException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

}
