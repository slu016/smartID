package rw9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Index {
	private List m_list = new ArrayList();
	private int m_index;
	private String m_newStr;
	private File m_filename;
	
	public Index(List list, int index, String newStr, File filename) throws IOException {
		this.m_list = list;
		this.m_index = index;
		this.m_newStr = newStr;//后来维度的指定改为可选。如果用户没有指定，则从point数据自动判断维度
		this.m_filename = filename;
		
		addIndex();
	}
	
	public Index() {
		
	}
	
	@SuppressWarnings("unchecked")
	public void addIndex() throws IOException {
		if (!m_list.contains(m_newStr)) {
			m_list.add(m_newStr);
			writeIndexFile();
			m_newStr = m_index + "";
			m_index++;
		} else {
			m_newStr = m_list.indexOf(m_newStr) + "";
		}
	}
	 
	public void writeIndexFile() throws IOException {
		// 先读取原有文件内容，然后进行写入操作
		FileWriter writer;
		try {
			String indexS = m_index + "";
			writer = new FileWriter(m_filename, true);
			writer.write(m_newStr + "," + indexS + "\r\n");
			writer.flush();
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List readIndex(File filename) throws IOException {
		String readindex;
		List list = new ArrayList();
		FileReader filereadindex = new FileReader(filename);
		BufferedReader bufreadindex = new BufferedReader(filereadindex);
		String[] ssindex = new String[2];
		
		while ((readindex = bufreadindex.readLine()) != null) {
			ssindex = readindex.split(",");
			list.add(ssindex[0]);
		}
		return list;
	}


	public void setList(List list) {
		this.m_list = list;
	}

	public void setId(int m_index) {
		this.m_index = m_index;
	}
	
	public List getList() {
		return m_list;
	}

	public int getId() {
		return m_index;
	}
	
	public String getNewStr() {
		return m_newStr;
	}

	public void setNewStr(String m_newStr) {
		this.m_newStr = m_newStr;
	}
}
