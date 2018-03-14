package com.sl.v0.buglocation.datas;

import java.util.ArrayList;

public class Bug {

	private String BugId;
	private String Summary;
	private String Description;
	private ArrayList<String> FixedFiles;
	
	public final String getBugId()
	{
		return BugId;
	}
	public final void setBugId(String value)
	{
		BugId = value;
	}

	public final String getSummary()
	{
		return Summary;
	}
	public final void setSummary(String value)
	{
		Summary = value;
	}

	public final String getDescription()
	{
		return Description;
	}
	public final void setDescription(String value)
	{
		Description = value;
	}

	public final ArrayList<String> getFixedFiles()
	{
		return FixedFiles;
	}
	public final void setFixedFiles(ArrayList<String> value)
	{
		FixedFiles = value;
	}

	public Bug()
	{
		setFixedFiles(new ArrayList<String>());
	}
	
}
