package com.aol.cyclops.functions.collections.extensions.persistent.lazy;

import com.aol.cyclops.collections.extensions.CollectionX;
import com.aol.cyclops.collections.extensions.persistent.PVectorX;
import com.aol.cyclops.collections.extensions.standard.ListX;
import com.aol.cyclops.functions.collections.extensions.AbstractLazyTest;
import com.aol.cyclops.functions.collections.extensions.CollectionXTestsWithNulls;

public class LazyPVectorXTest extends AbstractLazyTest{

	@Override
	public <T> CollectionX<T> of(T... values) {
		return PVectorX.of(values);
	}
	/* (non-Javadoc)
	 * @see com.aol.cyclops.functions.collections.extensions.AbstractCollectionXTest#empty()
	 */
	@Override
	public <T> CollectionX<T> empty() {
		return PVectorX.empty();
	}
}