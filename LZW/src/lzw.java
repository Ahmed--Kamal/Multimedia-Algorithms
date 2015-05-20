import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
public class lzw
{
	public static void main(String[] args) throws IOException
	{
		
		//Scanner input = new Scanner (new File ("in.txt"));
		//PrintStream  output = new PrintStream (new File ("out.txt"));
		/*ArrayList<Integer> asci = new ArrayList<Integer>();
		String data = input.nextLine();
		asci = compress(data);
		for(int i=0;i<asci.size();i++)
		{
			//System.out.println(asci.get(i));
			output.println(asci.get(i));
		}
		//System.out.println(decompress(asci));
		output.println(decompress(asci));*/
		Frame1 f = new Frame1();
		writeTagsOnFile(f);
		//writeDataOnFile(f);
		f.main(null);
		//input.close();
		//output.close();
	}
	public static void writeTagsOnFile(Frame1 ff)throws IOException
	{
		Scanner input = new Scanner (new File ("in.txt"));
		PrintStream  output = new PrintStream (new File ("out.txt"));
		ArrayList<Integer> asci = new ArrayList<Integer>();
		String data = input.nextLine();
		asci = ff.compress(data);
		for(int i=0;i<asci.size();i++)
		{
			output.println(asci.get(i));
		}
		input.close();
		output.close();
	}
	public static void writeDataOnFile(Frame1 ff)throws IOException
	{
		Scanner input = new Scanner (new File ("in.txt"));
		PrintStream  output = new PrintStream (new File ("out.txt"));
		ArrayList<Integer> arr = new ArrayList<Integer>();
		while (input.hasNextLine())
		{
			arr.add((int) input.nextInt());
		}
		String data = ff.decompress(arr);
		output.println(data);
		input.close();
		output.close();
	}
}