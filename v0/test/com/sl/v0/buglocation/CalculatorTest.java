package com.sl.v0.buglocation;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sl.v0.datas.GlobalVar;

public class CalculatorTest {

	@Test
	public void testRun() {
		boolean[] method={true,true,true,true,true};
		new Calculator().Run(GlobalVar.codeFolderName,method);
	}

}
