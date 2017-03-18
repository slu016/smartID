package rw9;

import java.io.*;
import java.util.*;

public class ReadWriteFile {
	public BufferedReader bufread;
	public BufferedReader bufreadindex;
	// 指定文件路径和名称
	private String filepath = "C:/Users/Sidi Lu/Desktop/test/";
	private String path1 = filepath + "serial_number_index.txt";
	private File filename1 = new File(path1);
	private String path2 = filepath + "model_index.txt";
	private File filename2 = new File(path2);
	private String path3 = filepath + "smart_attribute_name_index.txt";
	private File filename3 = new File(path3);
	private String path4 = filepath + "smart_id_index.txt";
	private File filename4 = new File(path4);
	private String path5 = filepath + "smart_value_index.txt";
	private File filename5 = new File(path5);
	private String path6 = filepath + "system_serial_number_index.txt";
	private File filename6 = new File(path6);

	/** */

	public void creatTxtFile(File fn) throws IOException {
		if (!fn.exists()) {
			fn.createNewFile();
			System.err.println(fn + "已创建！ ");
		}
	}

	/**
	 * 读取文本文件 .
	 */
	public int readTxtFile(int n, File input_file, File output_file) {
		String read;
		int i = 0;
		FileReader fileread;
		try {
			fileread = new FileReader(input_file);
			bufread = new BufferedReader(fileread);
			
			List serial_number = new ArrayList();
			List model = new ArrayList();
			List smart_attribute_name = new ArrayList();
			List smart_id = new ArrayList();
			List smart_value = new ArrayList();
			List system_serial_number = new ArrayList();
			
			int serial_number_index = serial_number.size(), 
					model_index = model.size(),
					smart_attribute_name_index = smart_attribute_name.size(), 
					smart_id_index = smart_id.size(),
					smart_value_index = smart_value.size(),
					system_serial_number_index = system_serial_number.size();
			
			Index serial_number1 = new Index();
			Index model1 = new Index();
			Index smart_attribute_name1 = new Index();
			Index smart_id1 = new Index();
			Index smart_value1 = new Index();
			Index system_serial_number1 = new Index();
			
			if (n > 0) {
				serial_number = serial_number1.readIndex(filename1);
				model = model1.readIndex(filename2);
				smart_attribute_name = smart_attribute_name1.readIndex(filename3);
				smart_id = smart_id1.readIndex(filename4);
				smart_value = smart_value1.readIndex(filename5);
				system_serial_number = system_serial_number1.readIndex(filename6);
			}
			
			while ((read = bufread.readLine()) != null) {
				if (i < n - 1) {
					i++;
					continue;
				}
				String[] ss = new String[100];
				ss = read.split(",");
				if (ss.length != 15) {
					System.out.println("The length of " + (i + 1) + "th line doesn't match when using split");
					System.exit(0);
				}
				// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
				Index serial_number2 = new Index(serial_number,serial_number_index,ss[0],filename1);
				serial_number_index = serial_number2.getId();
				ss[0] = serial_number2.getNewStr();
				
				Index model2 = new Index(model,model_index,ss[2],filename2);
				model_index = model2.getId();
				ss[2] = model2.getNewStr();
				
				Index smart_attribute_name2 = new Index(smart_attribute_name,smart_attribute_name_index,ss[3],filename3);
				smart_attribute_name_index = smart_attribute_name2.getId();
				ss[3] = smart_attribute_name2.getNewStr();
				
				Index smart_id2 = new Index(smart_id,smart_id_index,ss[5],filename4);
				ss[5] = smart_id2.getNewStr();
				smart_id_index = smart_id2.getId();
				
				Index smart_value2 = new Index(smart_value,smart_value_index,ss[10],filename5);
				ss[10] = smart_value2.getNewStr();
				smart_value_index = smart_value2.getId();
				
				Index system_serial_number2 = new Index(system_serial_number,system_serial_number_index,ss[13],filename6);
				system_serial_number_index = system_serial_number2.getId();
				ss[13] = system_serial_number2.getNewStr();

				// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
				ReadWriteFile readWrtie = new ReadWriteFile();
				readWrtie.writeTxtFile(ss[0] + "," + ss[1] + "," + ss[2] + "," + ss[3] + "," + ss[4] + "," + ss[5] + ","
						+ ss[6] + "," + ss[7] + "," + ss[8] + "," + ss[9] + "," + ss[10] + "," + ss[11] + "," + ss[12]
						+ "," + ss[13] + "," + ss[14], output_file);
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Line number that occur error: " + i + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Line number that occur error: " + i + "\n");
		}
		return i;
	}

	/** */
	/**
	 * 写文件 .
	 */
	public void writeTxtFile(String newStr, File filename1) throws IOException {
		// 先读取原有文件内容，然后进行写入操作
		FileWriter writer;
		try {
			writer = new FileWriter(filename1, true);
			writer.write(newStr + "\r\n");
			writer.flush();
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}

	/** */
	/**
	 * main方法测试
	 * 
	 * @param s
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ReadWriteFile readWrite = new ReadWriteFile();
		int n = 0;
		if (args.length == 3) {
			if (!readWrite.isNumeric(args[2])) {
				System.out.printf("input is not an integer");
				System.exit(0);
			}
			n = Integer.parseInt(args[2]);// 传递参数
		}
		if (args.length != 2 && args.length != 3) {
			System.out.printf("Specify the input file and output file");
			System.exit(0);
		}
		String input_path = readWrite.filepath + args[0];
		File input_file = new File(input_path);
		String output_path = readWrite.filepath + args[1];
		File output_file = new File(output_path);
		// ReadWriteFile readWrite = new ReadWriteFile();
		readWrite.creatTxtFile(output_file);
		readWrite.creatTxtFile(readWrite.filename1);
		readWrite.creatTxtFile(readWrite.filename2);
		readWrite.creatTxtFile(readWrite.filename3);
		readWrite.creatTxtFile(readWrite.filename4);
		readWrite.creatTxtFile(readWrite.filename5);
		readWrite.creatTxtFile(readWrite.filename6);
		int i = readWrite.readTxtFile(n, input_file, output_file);
		System.out.printf("%d", i);
	}
}