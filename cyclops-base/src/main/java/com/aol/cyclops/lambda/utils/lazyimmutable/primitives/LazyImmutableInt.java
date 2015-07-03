package com.aol.cyclops.lambda.utils.lazyimmutable.primitives;

import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;

import com.aol.cyclops.lambda.utils.LazyImmutableSetMoreThanOnceException;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class LazyImmutableInt {
	/**
	 * A class that represents an 'immutable' value that is generated inside a
	 * lambda expression, but is accessible outside it
	 * 
	 * It will only allow it's value to be set once. Unfortunately the compiler
	 * won't be able to tell if setOnce is called more than once
	 * 
	 * example usage
	 * 
	 * <pre>
	 * {@code
	 * public static <T> Supplier<T> memoiseSupplier(Supplier<T> s){
	 * 			LazyImmutable<T> lazy = LazyImmutable.def();
	 * 			return () -> lazy.computeIfAbsent(s);
	 * 		}
	 * }
	 * </pre>
	 * 
	 * Has map and flatMap methods, but is not a Monad (see example usage above
	 * for why, it is the initial mutation that is valuable).
	 * 
	 * @author johnmcclean
	 *
	 * @param <T>
	 */
	private int value;
	private boolean set = false;

	public LazyImmutableInt() {
	}

	/**
	 * @return Current value
	 */
	public int get() {
		return value;
	}

	/**
	 * Create an intermediate unbound (or unitialised) ImmutableClosedValue)
	 *
	 * @return unitialised ImmutableClosedValue
	 */
	public static LazyImmutableInt unbound() {
		return new LazyImmutableInt();
	}

	/**
	 * @param value
	 *            Create an initialised ImmutableClosedValue with specified
	 *            value
	 * @return Initialised ImmutableClosedValue
	 */
	public static LazyImmutableInt of(int value) {
		LazyImmutableInt v = new LazyImmutableInt();
		v.setOnce(value);
		return v;
	}

	/**
	 * @return a defined, but unitialised LazyImmutable
	 */
	public static LazyImmutableInt def() {
		return new LazyImmutableInt();
	}

	/**
	 * Map the value stored in this Immutable Closed Value from one Value to
	 * another If this is an unitiatilised ImmutableClosedValue, an
	 * uninitialised closed value will be returned instead
	 * 
	 * @param fn
	 *            Mapper function
	 * @return new ImmutableClosedValue with new mapped value
	 */
	public LazyImmutableInt map(IntUnaryOperator fn) {
		if (!set)
			return (LazyImmutableInt) this;
		else
			return LazyImmutableInt.of(fn.applyAsInt(value));
	}

	/**
	 * FlatMap the value stored in Immutable Closed Value from one Value to
	 * another If this is an unitiatilised ImmutableClosedValue, an
	 * uninitialised closed value will be returned instead
	 * 
	 * @param fn
	 *            Flat Mapper function
	 * @return new ImmutableClosedValue with new mapped value
	 */
	public LazyImmutableInt flatMap(IntFunction<LazyImmutableInt> fn) {
		if (!set)
			return (LazyImmutableInt) this;
		else
			return fn.apply(value);
	}

	/**
	 * 
	 * Set the value of this ImmutableClosedValue If it has already been set
	 * will throw an exception
	 * 
	 * @param val
	 *            Value to set to
	 * @return Current set Value
	 */
	public synchronized LazyImmutableInt setOnce(int val)
			throws LazyImmutableSetMoreThanOnceException {
		if (!this.set) {
			this.value = val;
			this.set = true;
			return this;
		}
		throw new LazyImmutableSetMoreThanOnceException("Current value "
				+ this.value + " attempt to reset to " + val);
	}

	private synchronized int setOnceFromSupplier(IntSupplier lazy) {

		if (!this.set) {
			this.value = lazy.getAsInt();
			this.set = true;
			return this.value;
		}

		return this.value;

	}

	/**
	 * Get the current value or set if it has not been set yet
	 * 
	 * @param lazy
	 *            Supplier to generate new value
	 * @return Current value
	 */
	public int computeIfAbsent(IntSupplier lazy) {
		if (set)
			return value;
		return setOnceFromSupplier(lazy);
	}
}
