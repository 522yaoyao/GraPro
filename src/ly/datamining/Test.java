package ly.datamining;

import java.io.File;

public class Test {
	public static void main(String[] args){
		String url="1/1.txt";
		File fis=new File(url);
		System.out.println("�Ƿ����ļ�"+fis.isFile());
		 System.out.println("�ļ��Ƿ����"+fis.exists());
	}

}
