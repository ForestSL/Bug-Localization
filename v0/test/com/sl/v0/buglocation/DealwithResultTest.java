package com.sl.v0.buglocation;

import static org.junit.Assert.*;

import org.junit.Test;

public class DealwithResultTest {

	@Test
	public void testExecute() {
		boolean[] method={true,true,true,true,true};
		new DealwithResult().execute(method);
	}

}
