package com.sl.v0.datas;

/*�����û���ѡʹ�õ�bug��λ����*/

public class Choice {
	String[] method={"VSM","LSI","JSM","NGD","PMI"};
	boolean[] choose={true,true,true,true,true};
	
	public String[] getMethod() {
		return method;
	}
	public void setMethod(String[] method) {
		this.method = method;
	}
	public boolean[] getChoose() {
		return choose;
	}
	public void setChoose(boolean[] choose) {
		this.choose = choose;
	}
	
	public boolean methodChoice(int index){
		return choose[index];
	}

}
