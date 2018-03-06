package com.sl.v0.views;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.eclipse.swt.widgets.Composite;

public class ColumnSort extends JPanel{
    public ColumnSort() {
        super(new BorderLayout());
        JTable table = new JTable(new MyTableModel());
        add(new JScrollPane(table), BorderLayout.CENTER);
        TableSorter ts = new TableSorter(table.getModel(), table.getTableHeader());
        table.setModel(ts);
    }
 
    /*View2中调用*/
//    public static void createAndShowGUI() {
//        JFrame f = new JFrame("ColumnModelTest");
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.getContentPane().add(new ColumnSort(), BorderLayout.CENTER);
//        f.pack();
//        f.setVisible(true);
//    }
 
    /*测试用*/
//    public static void main(String args[]) {
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
// 
//            @Override
//            public void run() {
//                createAndShowGUI();
//            }
//        });
// 
//    }
 
    public static class MyTableModel implements TableModel {
 
    	 Object[][] p = {
	                {
	                        new Boolean(false), "a.java", "60", "1", "103", "25", "55"
	                }, {
	                        new Boolean(false), "b.java", "30", "2", "102", "35", "56"
	                }, {
	                        new Boolean(false), "c.java", "50", "4", "101", "14", "67"
	                }, {
	                        new Boolean(false), "d.java", "10", "3", "100", "34", "66"
	                }
	        };
	        String[] n = {
	                "选择", "文件", "VSM", "LSI", "NGD", "PMI", "JSM"};
 
        public int getColumnCount() {
            return n.length;
        }
 
        public int getRowCount() {
            return p.length;
        }
 
        public String getColumnName(int col) {
            return n[col];
        }
 
        public Object getValueAt(int row, int col) {
            return p[row][col];
        }
 
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
 
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }
 
        public void setValueAt(Object value, int row, int col) {
            p[row][col] = value;
            fireTableCellUpdated(row, col);
        }
 
        private void fireTableCellUpdated(int row, int col) {
            // TODO Auto-generated method stub
 
        }
 
        public void addTableModelListener(TableModelListener l) {
 
        }
 
        public void removeTableModelListener(TableModelListener l) {
 
        }
    }
}
