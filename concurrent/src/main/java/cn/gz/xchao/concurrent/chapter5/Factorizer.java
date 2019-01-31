package cn.gz.xchao.concurrent.chapter5;

import java.math.BigInteger;

//实现Servlet
public class Factorizer {
	private final Computable<BigInteger, BigInteger[]> c = new Computable<BigInteger, BigInteger[]>() {
		public BigInteger[] compute(BigInteger arg) {
			return factor(arg);
		}
	};
	private final Computable<BigInteger, BigInteger[]> cache = new Memoizer1<BigInteger, BigInteger[]>(
			c);

	public void service(Object req, Object resp) {
		try {
			BigInteger i = extractFromRequest(req);
			encodeIntoResponse(resp, cache.compute(i));
		} catch (Exception e) {
			encodeError(resp, "factorization interrupted");
		}
	}
}
