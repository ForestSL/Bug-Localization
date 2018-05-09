package com.sl.v0.buglocation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.sl.v0.buglocation.datas.Bug;

public class DataCreatorTest {

	@Test
	public void testGetDatas() {
		new DataCreator().GetDatas();
	}

	@Test
	public void testGetBugs() {
		List<Bug> bugs=new ArrayList<Bug>();
		String filepath="E:\\BugLocation\\Source\\BugReport\\cxf-2.7.11.xml";
		bugs=new DataCreator().GetBugs(filepath);
		assertNotNull(bugs);
	}

}
