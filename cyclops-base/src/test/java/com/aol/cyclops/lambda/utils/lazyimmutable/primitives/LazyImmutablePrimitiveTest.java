package com.aol.cyclops.lambda.utils.lazyimmutable.primitives;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

import lombok.val;

import org.junit.Test;

import com.aol.cyclops.lambda.utils.LazyImmutable;
import com.aol.cyclops.lambda.utils.LazyImmutableSetMoreThanOnceException;

public class LazyImmutablePrimitiveTest {

	@Test
	public void testSetOnceInt() {
		LazyImmutableInt value = new LazyImmutableInt();
		IntSupplier s = () -> value.setOnce(10).get();
		assertThat(s.getAsInt(), is(10));
		assertThat(value.get(), is(10));
	}

	@Test
	public void testSetOnce2Attempts() {
		LazyImmutableInt value = new LazyImmutableInt();
		IntSupplier s = () -> value.setOnce(10).get();
		value.setOnce(20); // First time set
		try {
			s.getAsInt();
		} catch (LazyImmutableSetMoreThanOnceException e) {
		}

		assertThat(value.get(), is(20));
	}

	@Test(expected = LazyImmutableSetMoreThanOnceException.class)
	public void testSetOnce2AttemptsException() {
		LazyImmutableInt value = new LazyImmutableInt();
		IntSupplier s = () -> value.setOnce(10).get();
		value.setOnce(20); // first time set
		s.getAsInt();

		fail("Exception expected");

	}

	@Test
	public void testEqualsFalse() {
		val value = new LazyImmutableInt();
		value.setOnce(10);
		val value2 = new LazyImmutableInt();
		value2.setOnce(20);
		assertThat(value, not(equalTo(value2)));
	}

	@Test
	public void testEqualsTrue() {
		val value = new LazyImmutableInt();
		value.setOnce(10);
		val value2 = new LazyImmutableInt();
		value2.setOnce(10);
		assertThat(value, equalTo(value2));
	}

	@Test
	public void testHashcodeFalse() {
		val value = new LazyImmutableInt();
		value.setOnce(10);
		val value2 = new LazyImmutableInt();
		value2.setOnce(20);
		assertThat(value.hashCode(), not(equalTo(value2.hashCode())));
	}

	@Test
	public void testHashcodeTrue() {
		val value = new LazyImmutableInt();
		value.setOnce(10);
		val value2 = new LazyImmutableInt();
		value2.setOnce(10);
		assertThat(value.hashCode(), equalTo(value2.hashCode()));
	}

	@Test
	public void testMapUninitialized() {
		val value = new LazyImmutableInt();
		val value2 = value.map(i -> i + 10);
		assertThat(value, equalTo(value2));
	}

	@Test
	public void testMap2() {
		val value = new LazyImmutableInt();
		value.setOnce(10);
		val value2 = value.map(i -> i + 10);
		assertThat(value2.get(), equalTo(20));
	}

	@Test
	public void testFlatMapUinitialized() {
		val value = new LazyImmutableInt();
		val value2 = value.flatMap(i -> LazyImmutableInt.of(i + 10));
		assertThat(value, equalTo(value2));
	}

	@Test
	public void testFlatMap2() {
		val value = new LazyImmutableInt();
		value.setOnce(10);
		val value2 = value.flatMap(i -> LazyImmutableInt.of(i + 10));
		assertThat(value2.get(), equalTo(20));
	}

	/*@Test
	public void testLeftIdentity() {
		int a = 10;
		Function<Integer, LazyImmutable<Integer>> f = i -> LazyImmutableInt.of(i + 10);
		assertThat(LazyImmutableInt.of(a).flatMap(f), equalTo(f.apply(10)));
	}*/
	
	@Test
	public void testRightIdentityUnitialised() {
		val m = new LazyImmutableInt();
		
		assertThat(m.flatMap(LazyImmutableInt::of), equalTo(m));
	}

}
