package com.sl.v0.buglocation;

import java.io.File;
import java.util.*;

import com.sl.v0.buglocation.datas.Utility;

public class Calculator extends BaseExecutable
{

	private static final String VsmCompletedFile = "CompletedVsm.txt";
	private static final String LsiCompletedFile = "CompletedLsi.txt";
	private static final String JsmCompletedFile = "CompletedJsm.txt";
	private static final String PmiCompletedFile = "CompletedPmi.txt";
	private static final String NgdCompletedFile = "CompletedNgd.txt";

	private static boolean _cleanPrevious;
	private static boolean _runVsm;
	private static boolean _runLsi;
	private static boolean _runJsm;
	private static boolean _runPmi;
	private static boolean _runNgd;

	private static final String VsmFileName = "Results\\Vsm.txt";
	private static final String LsiOutputFolderName = "Results\\Lsi\\";
	private static final String JsmFileName = "Results\\Jsm.txt";
	private static final String PmiFileName = "Results\\Pmi.txt";
	private static final String NgdFileName = "Results\\Ngd.txt";

	@Override
	public void Execute()
	{
		_cleanPrevious = Utility.CleanPrevious;
		_runVsm = Utility.RunVsm;
		_runLsi = Utility.RunLsi;
		_runJsm = Utility.RunJsm;
		_runPmi = Utility.RunPmi;
		_runNgd = Utility.RunNgd;

	}

}
