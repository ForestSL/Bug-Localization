package com.sl.v0.views;

import java.text.Collator;
import java.util.Locale;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
 
public class TableColumnSorter {
	
	/*table��������*/
	public static void addSorter(final Table table, final TableColumn column) {  
		column.addListener(SWT.Selection, new Listener() {
		    boolean isAscend = true; // ������������
		    public void handleEvent(Event e) {
		    	int columnIndex = getColumnIndex(table, column);
		    	TableItem[] items = table.getItems();
		    	//ʹ��ð�ݷ���������
		    	for (int i = 1; i < items.length; i++) {
		    		String strvalue2 = items[i].getText(columnIndex);
		    		if(strvalue2.equalsIgnoreCase("")){
		    			//����������еĿ���Ŀʱ����ֹͣ���¼���������Ŀ
		    			break;
		    		}
		    		for (int j = 0; j < i; j++) {
		    			String strvalue1 = items[j].getText(columnIndex);
		    			//���ַ�����������ת��Ϊfloat����
		    			float numbervalue1=Float.valueOf(strvalue1);
		    			float numbervalue2=Float.valueOf(strvalue2);
		    			boolean isLessThan =false;
		    			if(numbervalue2<numbervalue1){
		    				isLessThan =true;
		    			}    
		    			if ((isAscend && isLessThan)
		    					|| (!isAscend && !isLessThan)){
		    				String[] values = getTableItemText(table, items[i]);
		    				Object obj = items[i].getData();
		    				items[i].dispose();
		    				TableItem item = new TableItem(table, SWT.NONE, j);
		    				item.setText(values);
		    				item.setData(obj);
		    				items = table.getItems();
		    				break;
		    			}
		    		}
		    	}
		    	table.setSortColumn(column);
		    	table.setSortDirection((isAscend ? SWT.UP : SWT.DOWN));
		    	isAscend = !isAscend;
		    }
		});
    }  
	
	/*table��������*/
	public static void removeSorter(final Table table, final TableColumn column) {  
		column.addListener(SWT.Selection, new Listener() {
		    boolean isAscend = false; // ���ս�������
		    public void handleEvent(Event e) {
		    	int columnIndex = getColumnIndex(table, column);
		    	TableItem[] items = table.getItems();
		    	//ʹ��ð�ݷ���������
		    	for (int i = 1; i < items.length; i++) {
		    		String strvalue2 = items[i].getText(columnIndex);
		    		if(strvalue2.equalsIgnoreCase("")){
		    			//����������еĿ���Ŀʱ����ֹͣ���¼���������Ŀ
		    			break;
		    		}
		    		for (int j = 0; j < i; j++) {
		    			String strvalue1 = items[j].getText(columnIndex);
		    			//���ַ�����������ת��Ϊfloat����
		    			float numbervalue1=Float.valueOf(strvalue1);
		    			float numbervalue2=Float.valueOf(strvalue2);
		    			boolean isLessThan =false;
		    			if(numbervalue2<numbervalue1){
		    				isLessThan =true;
		    			}    
		    			if ((isAscend && isLessThan)
		    					|| (!isAscend && !isLessThan)){
		    				String[] values = getTableItemText(table, items[i]);
		    				Object obj = items[i].getData();
		    				items[i].dispose();
		    				TableItem item = new TableItem(table, SWT.NONE, j);
		    				item.setText(values);
		    				item.setData(obj);
		    				items = table.getItems();
		    				break;
		    			}
		    		}
		    	}
		    	table.setSortColumn(column);
		    	table.setSortDirection((isAscend ? SWT.UP : SWT.DOWN));
		    	isAscend = !isAscend;
		    }
		});
    } 
  
    public static int getColumnIndex(Table table, TableColumn column) {  
        TableColumn[] columns = table.getColumns();  
        for (int i = 0; i < columns.length; i++) {  
            if (columns[i].equals(column))  
                return i;  
        }  
        return -1;  
    }  
  
    public static String[] getTableItemText(Table table, TableItem item) {  
        int count = table.getColumnCount();  
        String[] strs = new String[count];  
        for (int i = 0; i < count; i++) {  
            strs[i] = item.getText(i);  
        }  
        return strs;  
    }  

 
}
