import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Frame1 {

	private JFrame frame;
	private JTextField dataToCompress;
	private JTextField decompressed;
	private TextArea compressed;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 window = new Frame1();
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
	public Frame1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 636, 496);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		dataToCompress = new JTextField();
		dataToCompress.setBounds(186, 31, 407, 74);
		frame.getContentPane().add(dataToCompress);
		dataToCompress.setColumns(10);

		JButton bCompress = new JButton("Compress");
		bCompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (dataToCompress.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "NO data entered!");
				} else {
					compressed.setText("");
					String data = dataToCompress.getText();
					ArrayList<Integer> arr = new ArrayList<Integer>();
					arr = compress(data);
					String show = "";
					for (int i = 0; i < arr.size(); i++) {
						show += arr.get(i).toString();
						show += '\n';
					}
					compressed.setText(show);
				}
			}
		});
		bCompress.setFont(new Font("Tahoma", Font.BOLD, 14));
		bCompress.setBounds(24, 44, 127, 49);
		frame.getContentPane().add(bCompress);

		compressed = new TextArea();
		compressed.setBounds(186, 154, 407, 160);
		frame.getContentPane().add(compressed);

		JButton bdecompress = new JButton("Decompress");
		bdecompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (compressed.getText().length() == 0) {
					JOptionPane.showMessageDialog(null,
							"NO data to decompress!");
				} else {
					ArrayList<Integer> arr = new ArrayList<Integer>();
					for (String line : compressed.getText().split("\n")) {
						arr.add(Integer.parseInt(line));
					}
					String dataa = decompress(arr);
					decompressed.setText(dataa);
				}
			}
		});
		bdecompress.setFont(new Font("Tahoma", Font.BOLD, 14));
		bdecompress.setBounds(24, 368, 126, 49);
		frame.getContentPane().add(bdecompress);

		decompressed = new JTextField();
		decompressed.setBounds(186, 357, 407, 69);
		frame.getContentPane().add(decompressed);
		decompressed.setColumns(10);
		
		JButton btnNewButton = new JButton("Load Data from file");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Scanner input = new Scanner (new File ("in.txt"));
					ArrayList<Integer> asci = new ArrayList<Integer>();
					String data = input.nextLine();
					dataToCompress.setText(data);
					input.close();
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(24, 196, 141, 81);
		frame.getContentPane().add(btnNewButton);
	}
	public static ArrayList<Integer> compress(String data) {
		ArrayList<Integer> asci = new ArrayList<Integer>();
		// Scanner input = new Scanner(System.in);
		// String data = input.nextLine();
		String buff = "";
		int k = 128;
		Map<Integer, String> dic = new HashMap<Integer, String>();
		for (int i = 0; i < data.length(); i++) {
			/*
			 * if(i == (data.length()-1)) { for(int key : dic.keySet()) { String
			 * value = dic.get(key); System.out.println(value + " " + key); } }
			 */
			buff += data.charAt(i);
			if (i == data.length() - 1) {
				if (buff.length() == 1) {
					asci.add((int) buff.charAt(0));
				} else if (check(buff, dic)) {
					for (int key : dic.keySet()) {
						String value = dic.get(key);
						if (value.equals(buff)) {
							asci.add(key);
							break;
						}
					}
					break;
				} else if (buff.length() == 2) {
					dic.put(k, buff);
					asci.add((int) buff.charAt(0));
					k++;
					buff = "";
					i--;
					continue;
				} else {
					String sub = buff.substring(0, (buff.length() - 1));
					for (int key : dic.keySet()) {
						String value = dic.get(key);
						if (value.equals(sub)) {
							asci.add(key);
							break;
						}
					}
					dic.put(k, buff);
					k++;
					buff = "";
					i--;
					continue;
				}
			} else if (buff.length() > 1) {
				if (check(buff, dic))
					continue;
				else {
					// System.out.println(buff);
					if (buff.length() == 2) {

						dic.put(k, buff);
						asci.add((int) buff.charAt(0));
					} else {
						// System.out.println(buff);
						String sub = buff.substring(0, (buff.length() - 1));

						for (int key : dic.keySet()) {
							String value = dic.get(key);
							if (value.equals(sub)) {
								// System.out.println(value + " " + sub);
								asci.add(key);
								break;
							}
						}
						dic.put(k, buff);
					}
					k++;
					buff = "";
					i--;
				}
			}

		}
		return asci;
	}

	public static boolean check(String buf, Map<Integer, String> dict) {
		for (int key : dict.keySet()) {
			String value = dict.get(key);
			if (value.equals(buf))
				return true;
		}
		return false;
	}

	public static String decompress(ArrayList<Integer> asc) {
		String buf = "";
		String dataa = "";
		int k = 128;
		int x = 0;
		Map<Integer, String> dictt = new HashMap<Integer, String>();
		for (int i = 0; i < asc.size(); i++) {
			if (asc.get(i) < 128) {
				x = (asc.get(i));
				char c;
				c = (char) x;
				dataa += c;
				if (buf.length() > 0) {
					dictt.put(k, (buf + c));
					k++;
				}
				buf = "";
				buf += c;
			} else if (asc.get(i) > 127) {
				String sub = "";
				boolean b = false;
				for (int key : dictt.keySet()) {
					String value = dictt.get(key);
					if (key == asc.get(i)) {
						sub = value;
						b = true;
						break;
					}
				}
				if (b) {
					dataa += sub;
					dictt.put(k, (buf + sub.charAt(0)));
					buf = sub;
					k++;
				} else {
					dataa += (buf + buf.charAt(0));
					dictt.put(k, (buf + buf.charAt(0)));
					buf = (buf + buf.charAt(0));
					k++;
				}

			}
			/*
			 * if(i == (asc.size()-1)) { for(int key : dictt.keySet()) { String
			 * value = dictt.get(key); System.out.println(value + " " + key); }
			 * }
			 */
		}
		return dataa;
	}
}
