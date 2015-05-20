import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import javax.imageio.ImageIO;
public class uniformQuantizer
{
	public static int width=0;
    public static int height=0;
	public static void main(String[] args)throws IOException
	{
		compress();
		decompress("outputCode.txt", "lenaa.jpg");
		/*for(int i=0;i<height; i++)
		{
			for(int j=0;j<width; j++)
				out.print(imgCode[i][j] + " ");
			out.println();
		}
		int x[][] = new int[height][width];
		Scanner inn = new Scanner(new File ("out.txt"));
		PrintStream outt = new PrintStream(new File ("outt.txt"));
		for(int i=0;i<height; i++)
		{
			for(int j=0;j<width; j++)
				x[i][j] = inn.nextInt();
		}
		writeImage(x, "lenaaa.jpg");
		for(int i=0;i<height; i++)
		{
			for(int j=0;j<width; j++)
				outt.print(x[i][j]);
			outt.println();
		}*/
	}
	public static int[][] compress()throws IOException
	{
		Scanner in = new Scanner(new File ("in.txt"));
		PrintStream outt = new PrintStream(new File ("out.txt"));
		PrintStream out = new PrintStream(new File ("outputCode.txt"));
		int levels = in.nextInt();
		String imageName = in.next();
		int imgCode[][] = readImage(imageName);
		for(int i=0;i<height; i++)
		{
			for(int j=0;j<width; j++)
				outt.print(imgCode[i][j] + " ");
			outt.println();
		}
		int fullScale = 256;
		int step = fullScale / levels;
		ArrayList<Integer> q = new ArrayList<Integer>();
		ArrayList<Integer> min = new ArrayList<Integer>();
		ArrayList<Integer> max = new ArrayList<Integer>();
		int compArray[][] = new int[height][width];
		int start = 0;
		for(int i=0; i<levels; i++)
		{
			min.add(start);
			start += step;
			max.add(start - 1);
			q.add((min.get(i) + max.get(i) + 1) / 2);
		}
		out.println(height + " " + width);
		for(int i=0; i<height; i++)
		{
			for(int j=0; j<width; j++)
			{
				for(int k=0; k<levels; k++)
				{
					if((imgCode[i][j] >= min.get(k)) && (imgCode[i][j] <= max.get(k))){
						compArray[i][j] = q.get(k);
						out.print(compArray[i][j]);
						break;
					}
				}
				out.print(" ");
			}
			out.println();
		}
		PrintStream outQuantizer = new PrintStream(new File ("quantizer.txt"));
		for(int i=0;i<levels; i++)
			outQuantizer.println(q.get(i) + "\t" + min.get(i) + "\t" + max.get(i));
		//writeImage(compArray, "lola.jpg");
		out.flush();
		outt.flush();
		outQuantizer.flush();
		in.close();
		out.close();
		outt.close();
		outQuantizer.close();
		MSE(imgCode, compArray, height, width);
		return compArray;
	}
	public static void decompress(String fileName, String imageName)throws IOException
	{
		File compFile = new File(fileName);
		Scanner input = new Scanner(compFile);
		Scanner inn = new Scanner(new File ("outputCode.txt"));
		int height = inn.nextInt();
		int width = inn.nextInt();
		int compCode[][] = new int[height][width];
		for(int i=0; i<height; i++)
		{
			for(int j=0; j<width; j++)
			{
				compCode[i][j] = inn.nextInt();
			}
		}
		writeImage(compCode, imageName);
		inn.close();
		input.close();
	}
	public static void MSE(int[][] original, int[][] comp, int h, int w)
	{
		double  mse = 0;
		for(int i=0; i<h; i++)
		{
			for(int j=0; j<w; j++)
			{
				mse += ((original[i][j] - comp[i][j]) * (original[i][j] - comp[i][j]));
			}
		}
		mse/= (double)(h*w);
		System.out.println("MSE = " + mse);
		
	}
	public static int[][] readImage(String filePath)
    {
        File file=new File(filePath);
        BufferedImage image=null;
        try
        {
            image=ImageIO.read(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

          width=image.getWidth();
          height=image.getHeight();
        int[][] pixels=new int[height][width];

        for(int x=0;x<width;x++)
        {
            for(int y=0;y<height;y++)
            {
                int rgb=image.getRGB(x, y);
                int alpha=(rgb >> 24) & 0xff;
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = (rgb >> 0) & 0xff;

                pixels[y][x]=r;
            }
        }

        return pixels;
    }
	
	public static void writeImage(int[][] pixels,String outputFilePath)
    {
        File fileout=new File(outputFilePath);
        BufferedImage image2=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB );

        for(int x=0;x<width ;x++)
        {
            for(int y=0;y<height;y++)
            {
                image2.setRGB(x,y,(pixels[y][x]<<16)|(pixels[y][x]<<8)|(pixels[y][x]));
            }
        }
        try
        {
            ImageIO.write(image2, "jpg", fileout);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}