package me.test.playground.package2;

import me.test.playground.package1.Ref;
import me.test.playground.package1.Ref.UpdateStrategy;

public class OtherUpdateStrategy<E> extends UpdateStrategy<E> {

	public OtherUpdateStrategy(Ref<E> ref) {
		super(ref);
	}
	
	public void updateRefValue(E value) {
		this.updateValue(value);
	}

}
