package cn.gz.xchao.concurrent.chapter5;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String, BigInteger> {

	public BigInteger compute(String arg) throws InterruptedException {
		return new BigInteger(arg);
	}

}
