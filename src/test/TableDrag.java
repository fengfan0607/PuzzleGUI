package test;
import javax.swing.*;
import java.awt.event.*;

import javax.swing.JTable;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.*;

class MyFrame extends JFrame
{
private JTable tb1;
private JTable tb2;	public MyFrame()
{
setBounds(0,0,800,600);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	Box box = Box.createVerticalBox();
 
//通过DefaultTableModel给tb1加点数据
box.add(new JLabel("拖拽源"));	String[][] tableDatas = {
new String[]{"张飞","男"},
new String[]{"关羽","男"},
new String[]{"刘备","男"},
new String[]{"貂蝉","女"}};	String[] title = {"姓名","性别"};
tb1 = new JTable(tableDatas,title);
box.add(new JScrollPane(tb1));	//将tb1 做成拖放源
DragSource dragSource = DragSource.getDefaultDragSource();
dragSource.createDefaultDragGestureRecognizer(tb1,
DnDConstants.ACTION_COPY_OR_MOVE,
new DragGestureListener()
{
public void dragGestureRecognized(DragGestureEvent event)
{
//当前选择中单元格的内容
TableModel tableModel = (TableModel) tb1.getModel();
int row = tb1.getSelectedRow(); 
int col = tb1.getSelectedColumn(); 
String s = (String)tableModel.getValueAt(row,col);
Transferable transferable = (Transferable) new DataFlavor(s);
event.startDrag(
DragSource.DefaultCopyDrop,
transferable);}
});	

box.add(new JLabel("拖拽目标"));	String[][] tableDatas2 = {new String[]{"",""},new String[]{"",""}};
tb2 = new JTable(tableDatas2,title);
box.add(new JScrollPane(tb2));
add(box);	

final TableModel tablemodel = tb2.getModel();
//将tb2做为拖放目标
new DropTarget(tb2,DnDConstants.ACTION_COPY,new DropTargetAdapter()
{
@Override
public  void drop(DropTargetDropEvent event)    
{
try
{
event.acceptDrop(DnDConstants.ACTION_COPY);
Transferable transferable = event.getTransferable();
DataFlavor[] flavors = transferable.getTransferDataFlavors();
//这里简单起见就取0号，
DataFlavor data = flavors[0];
String s = (String)transferable.getTransferData(data);
//得到当前的鼠标位置
Point p = event.getLocation();
//获得当前鼠标所在的行、列
int row = tb2.rowAtPoint(p);
int col = tb2.columnAtPoint(p);
tablemodel.setValueAt(s,row,col);
//免得阻塞进程
event.dropComplete(true);
}
catch (Exception e)
{
event.dropComplete(true);
e.printStackTrace();
}
}	
});	pack();
setVisible(true);
}
}
public class TableDrag 
{
public static void main(String[] args) 
{
new MyFrame();
System.out.println("Hello World!");
}
}