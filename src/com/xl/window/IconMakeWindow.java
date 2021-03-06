package com.xl.window;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.xl.util.FileUtils;

import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Dimension;
import com.xl.util.ImageUtil;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

public class IconMakeWindow extends JFrame implements Context{

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IconMakeWindow frame = new IconMakeWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public IconMakeWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		Box horizontalBox = Box.createHorizontalBox();
		contentPane.add(horizontalBox);
		
		textField = new JTextField();
		textField.setMaximumSize(new Dimension(2147483647, 26));
		textField.setToolTipText("图片路径 支持文件拖拽");
		horizontalBox.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("选择文件");
		horizontalBox.add(btnNewButton);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		contentPane.add(horizontalBox_1);
		
		JButton btnNewButton_1 = new JButton("生成安卓图标");
		horizontalBox_1.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("生成ios图标");
		horizontalBox_1.add(btnNewButton_2);
		dragFile(textField, new OnDragFile() {
			
			@Override
			public void onDragFile(List<File> list_file) {
				textField.setText(list_file.get(0).getAbsolutePath());
			}
		});
		
		btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String bmpname = textField.getText();
				//创建4个文件夹
				String dir = FileUtils.getDir(bmpname);
				String name = FileUtils.getName(bmpname);
				new File(dir+"/mipmap-mdpi").mkdirs();
				new File(dir+"/mipmap-hdpi").mkdirs();
				new File(dir+"/mipmap-xhdpi").mkdirs();
				new File(dir+"/mipmap-xxhdpi").mkdirs();
				new File(dir+"/mipmap-xxxhdpi").mkdirs();
				ImageUtil.zoomBitmapFromFile(bmpname, 48, 48, dir+"/mipmap-mdpi/"+name);
				ImageUtil.zoomBitmapFromFile(bmpname, 72, 72, dir+"/mipmap-hdpi/"+name);
				ImageUtil.zoomBitmapFromFile(bmpname, 96, 96, dir+"/mipmap-xhdpi/"+name);
				ImageUtil.zoomBitmapFromFile(bmpname, 144, 144, dir+"/mipmap-xxhdpi/"+name);
				ImageUtil.zoomBitmapFromFile(bmpname, 192, 192, dir+"/mipmap-xxxhdpi/"+name);
				Toast.makeText(IconMakeWindow.this, "转换完成").display();
			}
		});
		
		
	}
	
	
	
	 public void dragFile(Component c, final OnDragFile onDragFile)
     {
        new DropTarget(c,DnDConstants.ACTION_COPY_OR_MOVE,new DropTargetAdapter()
         {
            @Override
            public void drop(DropTargetDropEvent dtde)
            {
                try{
                    
                    if(dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
                     {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                        List<File>list=(List<File>)(dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
                        if(onDragFile!=null){
                        	onDragFile.onDragFile(list);
                        }
//                        String temp="";
//                        for(File file:list)
//                         {
//                            temp+=file.getAbsolutePath()+";\n";
//                            JOptionPane.showMessageDialog(null, temp);
//                            dtde.dropComplete(true);
//                            
//                         }
                        
                     }
                    
                    else
                     {
                        
                        dtde.rejectDrop();
                     }
                    
                }catch(Exception e){e.printStackTrace();}
                
            }
            
            
         });
        
        
     }
  
  public interface OnDragFile{
	  public void onDragFile(List<File> list_file);
  }

@Override
public File getFilesDir() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public File getCacheDir() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public File getExternalCacheDir() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public File[] getExternalMediaDirs() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String[] fileList() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public File getDir(String name, int mode) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Resources getResources() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public JFrame getJFrame() {
	// TODO Auto-generated method stub
	return this;
}

}
