import java.awt.EventQueue;

import javax.swing.JFrame;

import java.util.*;
import java.io.*;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.TextArea;

public class Fram1 {

	private JFrame frame;
	private ArrayList<Integer> p = new ArrayList<Integer>();
	private ArrayList<Integer> l = new ArrayList<Integer>();
	private ArrayList<Character> c = new ArrayList<Character>();
	private JTextField dataEntered;
	private JButton btnNewButton_1;
	private JTextField decompData;
	private TextArea tags;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fram1 window = new Fram1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Fram1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 655, 425);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		dataEntered = new JTextField();
		dataEntered.setBounds(168, 14, 446, 38);
		frame.getContentPane().add(dataEntered);
		dataEntered.setColumns(10);
		
		JLabel lblEnterData = new JLabel("Enter Data:");
		lblEnterData.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblEnterData.setBounds(11, 11, 123, 38);
		frame.getContentPane().add(lblEnterData);
		
		JButton btnNewButton = new JButton("Compress");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(dataEntered.getText().length() == 0)
				{
					JOptionPane.showMessageDialog(null, "No Data Entered!");
				}
				else
				{
					tags.setText(null);
					String dataToCompress = dataEntered.getText();
					tags.setText(compress(dataToCompress));
				}
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		Image Img = new ImageIcon(this.getClass().getResource("/comp.png")).getImage();
		btnNewButton.setIcon(new ImageIcon(Img));
		btnNewButton.setBounds(11, 105, 210, 92);
		frame.getContentPane().add(btnNewButton);
		
		btnNewButton_1 = new JButton("Decompress");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tags.getText().length() == 0)
				{
					JOptionPane.showMessageDialog(null, "No Tags to decompress");
				}
				else{
					
					decompData.setText(decompress());
					//decompData.setText(tags.getText());
				}
			}
		});
		btnNewButton_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnNewButton_1.setBounds(11, 275, 150, 50);
		frame.getContentPane().add(btnNewButton_1);
		
		decompData = new JTextField();
		decompData.setBounds(196, 284, 418, 38);
		frame.getContentPane().add(decompData);
		decompData.setColumns(10);
		
		tags = new TextArea();
		tags.setBounds(281, 72, 333, 150);
		frame.getContentPane().add(tags);
		
		JButton btnNewButton_2 = new JButton("Decomp");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s = tags.getText();
				ArrayList<Integer> pp = new ArrayList<Integer>();
				ArrayList<Integer> ll = new ArrayList<Integer>();
				ArrayList<Character> cc = new ArrayList<Character>();
				String tt = "";
				tt+=s.charAt(0);
				pp.add(Integer.parseInt(tt));
				tt = "";
				tt+=s.charAt(2);
				ll.add(Integer.parseInt(tt));
				cc.add(s.charAt(4));
				for(int i=5 ;i<s.length(); i++)
				{
					String p = "";
					String l = "";
					String c = "";
					if(s.charAt(i) == ' ')
						i++;
					while(s.charAt(i)!= ' ')
					{
						//if(s.charAt(i) == ' ')
							//break;
						p+=s.charAt(i);
						i++;
					}
					if(s.charAt(i) == ' ')
						i++;
					pp.add(Integer.parseInt(p));
					while(s.charAt(i)!= ' ')
					{
						//if(s.charAt(i) == ' ')
							//break;
						l+=s.charAt(i);
						i++;
					}
					if(s.charAt(i) == ' ')
						i++;
					ll.add(Integer.parseInt(l));
					cc.add(s.charAt(i));
				}
				String decom = "";
				for(int i = 0; i < pp.size(); i++)
				{
					if(pp.get(i) == 0 && ll.get(i) == 0)
						decom += cc.get(i);
					else{
							int in = decom.length() - pp.get(i);
							for(int j = in; j < (in + ll.get(i) ); j++)
							{
								decom += decom.charAt(j);
							}
							if(cc.get(i) != '0')
								decom += cc.get(i);
					}
				}
				decompData.setText(decom);
			}
		});
		btnNewButton_2.setBounds(11, 227, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
	}
	public String compress(String data)
	{
		p.clear();
		l.clear();
		c.clear();
		String buff="";
		int index,ptr = 0;
		p.add(0);
		l.add(0);
		c.add(data.charAt(0));
		for(int i=1;i<data.length();i++)
		{
			buff+=data.charAt(i);
			index = check(data, buff, (i-buff.length()));
			if(index >-1)
			{
				ptr = (i - (buff.length()-1)) - index;
				if(i+1 == data.length() && buff.length() > 1)
				{
					p.add(ptr);
					l.add(buff.length());
					c.add('0');
					buff = "";
				}
			}
			else
			{
				if( buff.length() == 1)
				{
					p.add(0);
					l.add(0);
					c.add(data.charAt(i));
					buff = "";
				}
				else
				{
					p.add(ptr);
					l.add(buff.length()-1);
					c.add(data.charAt(i));
					buff = "";
				}
			}
			if(i+1 == data.length() && buff.length() == 1)
			{
				p.add(0);
				l.add(0);
				c.add(data.charAt(i));
				buff = "";
			}
		}
		String comp = "";
		for(int j=0;j<p.size();j++)
			comp += ("[ " + p.get(j) + ", " + l.get(j) + ", " + c.get(j) + " ]" + "\n");
		return comp;
	}
	public int check(String data, String buff, int i)
	{
		int size = buff.length(), count=0, k, j;
		String comp = "";
		for(j = i; j>=0;j--)
		{
			count = 0;
			comp = "";
			if(j - (size-1) < 0)
				break;
			for(k=j;;k--)
			{
				comp+=data.charAt(k);
				count++;
				if(count == size)
					break;
			}
			comp = new StringBuffer(comp).reverse().toString();
			if(comp.equals(buff))
			{
				return k;
			}
		}
		return -1;
	}
	public String decompress()
	{
		/*p.clear();
		l.clear();
		c.clear();
		String s = tags.getText();
		p.add((int) s.charAt(0));
		l.add((int) s.charAt(2));
		c.add(s.charAt(4));
		for(int j = 5; j<s.length() ; j++)
		{
			for(int k=j;;k++)
			{
				if(s.charAt(k) == ' ')
					
			}
		}*/
		String decom = "";
		for(int i = 0; i < p.size(); i++)
		{
			if(p.get(i) == 0 && l.get(i) == 0)
				decom += c.get(i);
			else{
					int in = decom.length() - p.get(i);
					for(int j = in; j < (in + l.get(i) ); j++)
					{
						decom += decom.charAt(j);
					}
					if(c.get(i) != '0')
						decom += c.get(i);
			}
		}
		return decom;
	}
}
