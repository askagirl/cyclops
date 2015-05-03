package com.aol.cyclops.comprehensions.comprehenders;

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import com.aol.cyclops.comprehensions.Comprehender;

public class IntStreamComprehender implements Comprehender<IntStream> {

	@Override
	public Object filter(IntStream t, Predicate p) {
		return t.filter(test->p.test(test));
	}

	@Override
	public Object map(IntStream t, Function fn) {
		return t.map(i->(int)fn.apply(i));
	}

	@Override
	public IntStream flatMap(IntStream t, Function fn) {
		return t.flatMap( i-> (IntStream)fn.apply(i));
	}

}
