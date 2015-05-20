package sHuffman;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Form1 {

	private JFrame frame;
	private JTextField dataToCompress;
	private JTextField decompressedData;
	private TextArea compressed;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Form1 window = new Form1();
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
	public Form1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 645, 496);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton enterData = new JButton("Compress");
		enterData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(dataToCompress.getText().length() == 0)
					JOptionPane.showMessageDialog(null, "No data Entered");
				else
				{
					try {
						String code = compress(dataToCompress.getText());
						compressed.setText(code);
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		enterData.setBounds(25, 44, 108, 44);
		frame.getContentPane().add(enterData);
		
		dataToCompress = new JTextField();
		dataToCompress.setBounds(161, 38, 440, 56);
		frame.getContentPane().add(dataToCompress);
		dataToCompress.setColumns(10);
		
		JButton fileLoad = new JButton("load from file");
		fileLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Scanner input = new Scanner (new File ("in.txt"));
					String s = input.nextLine();
					dataToCompress.setText(s);
					input.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		fileLoad.setBounds(25, 125, 108, 44);
		frame.getContentPane().add(fileLoad);
		
		compressed = new TextArea();
		compressed.setBounds(161, 125, 440, 204);
		frame.getContentPane().add(compressed);
		
		JButton decomp = new JButton("Decompress");
		decomp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(compressed.getText().length() == 0)
					JOptionPane.showMessageDialog(null, "No data to decompress");
				else
				{
					try {
						String a = decompress(compressed.getText());
						decompressedData.setText(a);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		decomp.setBounds(25, 387, 108, 44);
		frame.getContentPane().add(decomp);
		
		decompressedData = new JTextField();
		decompressedData.setBounds(161, 379, 440, 60);
		frame.getContentPane().add(decompressedData);
		decompressedData.setColumns(10);
	}
	public static String compress(String data)throws IOException
	{
		Scanner input = new Scanner (new File ("in.txt"));
		PrintStream output = new PrintStream (new File ("tags.txt"));
		String code = "";
		ArrayList<Character> ch = new ArrayList<Character>();
		ArrayList<Integer> freq = new ArrayList<Integer>();
		int arr[] = new int[128];
		for(int i=0;i<data.length();i++)
		{
			char c = data.charAt(i);
			arr[(int)(c)]++;
		}
		for(int i=0;i<128;i++)
		{
			if(arr[i] > 0)
			{
				freq.add(arr[i]);
				ch.add((char)(i));
			}
		}
		if(freq.size() > 1)
		{
			for(int i=0;i<freq.size();i++)
			{
				for(int j=i+1;j<freq.size();j++)
				{
					if(freq.get(j) > freq.get(i))
					{
						int temp1;
						char temp2;
						temp1 = freq.get(i);
						freq.set(i, freq.get(j));
						freq.set(j, temp1);
						temp2 = ch.get(i);
						ch.set(i, ch.get(j));
						ch.set(j, temp2);
					}
				}
			}
		}
		String which = "";
		String codes[] = new String[ch.size()];
		String codess[] = new String[10];
		if(ch.size() == 1){
		
		for(int i=0;i<ch.size();i++)
		{
			codes[i] = "";
		}
		for(int i=0;i<ch.size();i++)
		{
			if(i == 0)
			{
				codes[0] = "1";
				continue;
			}
			for(int j=i; j<ch.size();j++)
			{
				codes[j]+="0";
			}
			if(i == ch.size()-1)
				break;
			codes[i]+="1";
		}
		for(int i=0; i<data.length(); i++)
		{
			for(int j=0; j<ch.size(); j++)
			{
				if(data.charAt(i) == ch.get(j)){
					code += codes[j];
				}
			}
		}
		which = "first";
		}
		else{
		ArrayList<String> chh = new ArrayList<String>();
		ArrayList<Integer> freqq = new ArrayList<Integer>();
		for(int i=0;i<ch.size(); i++)
		{
			String a = "";
			a+=ch.get(i);
			chh.add(a);
			freqq.add(freq.get(i));
		}
		
		String s = "";
		while(true)
		{
			if(chh.size() == 2)
				break;
			for(int j=chh.size()-1 ;; )
			{
				if(chh.get(j-1).length() > 1){
					if(chh.get(j).length() <= chh.get(j-1).length())
						s = chh.get(j) + chh.get(j-1);
					else
						s = chh.get(j-1) + chh.get(j);
				}
				else
				{
					s = chh.get(j-1) + chh.get(j);
				}
				chh.set(j-1, s);
				chh.remove(j);
				
				int x = freqq.get(j) + freqq.get(j-1);
				freqq.set(j-1, x);
				freqq.remove(j);
				
				break;
			}
			for(int i=0;i<freqq.size();i++)
			{
				for(int j=i+1;j<freqq.size();j++)
				{
					if(freqq.get(j) > freqq.get(i))
					{
						int temp1;
						String temp2;
						String a = "";
						temp1 = freqq.get(i);
						freqq.set(i, freqq.get(j));
						freqq.set(j, temp1);
						temp2 = chh.get(i);
						a += chh.get(j);
						chh.set(i, a);
						chh.set(j, temp2);
					}
				}
			}
			for(int i=0;i<chh.size(); i++)
				System.out.println(chh.get(i) + " " + freqq.get(i));
		}
		for(int i=0;i<chh.size(); i++)
			System.out.println(chh.get(i) + " " + freqq.get(i));
		ArrayList<Character> chrs = new ArrayList<Character>();
		codess = new String[chh.get(0).length() + chh.get(1).length()];
		for(int i=0;i<chh.get(0).length() + chh.get(1).length();i++)
		{
			codess[i] = "";
		}
		for(int i=0; i<chh.get(0).length(); i++)
		{
			for(int j=i; j<chh.get(0).length();j++)
			{
				codess[j]+="1";
			}
			if(i == chh.get(0).length()-1){
				chrs.add(chh.get(0).charAt(i));
				break;
			}
			codess[i]+="0";
			chrs.add(chh.get(0).charAt(i));
		}
		int siz = chh.get(0).length();
		for(int i=0; i<chh.get(1).length(); i++)
		{
			for(int j=i; j<chh.get(1).length();j++)
			{
				codess[j+siz]+="0";
			}
			if(i == chh.get(1).length()-1){
				chrs.add(chh.get(1).charAt(i));
				break;
			}
			codess[i+siz]+="1";
			chrs.add(chh.get(1).charAt(i));
		}
		//for(int i=0;i<ch.size(); i++)
			//System.out.println(ch.get(i));
		for(int i=0;i<chrs.size();i++)
		{
			ch.set(i, chrs.get(i));
		}
		//for(int i=0;i<chh.size();i++)
			//System.out.println(chh.get(i) + " " + freqq.get(i));
		
		for(int i=0; i<data.length(); i++)
		{
			for(int j=0; j<ch.size(); j++)
			{
				if(data.charAt(i) == ch.get(j)){
					code += codess[j];
				}
			}
		}
		which = "second";
		}
	//----------------------------------------------------------------	
		/*for(int i=0;i<freq.size();i++)
			output.println(ch.get(i) + " " + freq.get(i) + " " + codes[i]);*/
		
		/*output.println(Long.parseLong(dataa,2));
		input = new Scanner(new File ("out.txt"));
		String ss = Long.toBinaryString(input.nextLong());
		String codee = ss.substring(1);
		System.out.println(codee);*/
		DataOutputStream out = new DataOutputStream(new FileOutputStream("out.txt"));
		String sub = "";
		int  x =0;
		for(int i=0; i<code.length();i++)
		{
			sub+=code.charAt(i);
			x++;
			if(x == 9)
			{
				String subb = "1" + sub;
				out.writeInt(Integer.parseInt(subb));
				x = 0;
				sub = "";
			}
			else if(i == code.length()-1)
			{
				String subb = "1" + sub;
				out.writeInt(Integer.parseInt(subb));
				x = 0;
				sub = "";
				break;
			}
		}
		String codee = "";
		DataInputStream in = new DataInputStream(new FileInputStream("out.txt"));
		try {
		    while (true)	
		    {
		    	int xx = in.readInt();
		    	String a = String.valueOf(xx);
		    	String aa = a.substring(1);
		    	//System.out.println(aa);
		    	codee += aa;
		    }
		} catch (EOFException ignored) {
		}
		
		for(int i=0;i<ch.size();i++)
		{
			output.println(ch.get(i));
			if(which.equals("first"))
				output.println(codes[i]);
			else
				output.println(codess[i]);
		}
		in.close();
		out.close();
		input.close();
		output.close();
		return codee;
	}
	public static String decompress(String code)throws IOException
	{
		PrintStream output = new PrintStream (new File ("dout.txt"));
		String data = "";
		ArrayList<Character> ch = new ArrayList<Character>();
		ArrayList<String> codes = new ArrayList<String>();
		
		BufferedReader in = new BufferedReader(new FileReader("tags.txt"));
		int k = 1;
		while (in.ready()) 
		{
			String r = in.readLine();
			if(k % 2 !=0)
			{
				char c = r.charAt(0);
				ch.add(c);
			}
			else
			{
				codes.add(r);
			}
			k++;
		}
		/*for(int i=0;i<ch.size();i++)
			System.out.println(ch.get(i) + " " + codes.get(i));*/
		String buf = "";
		for(int i=0; i<code.length(); i++)
		{
			buf += code.charAt(i);
			for(int j=0; j<codes.size(); j++)
			{
				if(buf.equals(codes.get(j)))
				{
					data += ch.get(j);
					buf = "";
				}
			}
		}
		
		output.println(data);
		
		in.close();
		output.close();
		return data;
	}
}
