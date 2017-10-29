package ly.datamining;

import java.io.File;

public class Test {
	public static void main(String[] args){
		String url="1/1.txt";
		File fis=new File(url);
		System.out.println("是否是文件"+fis.isFile());
		 System.out.println("文件是否存在"+fis.exists());
	}

}
