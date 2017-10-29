package ly.datamining;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * 此类主要用于对数据的处理，将数据转化为数字，便于统计
 */
public class PreRead {
   
	/**
	 * 读取数据，并将数据转化为数字，便于后续的统计
	 * @param fileIn 读入的文件（需要处理的数据）
	 * @param fileOut 读出的文件（处理后的文件）
	 * @param type 是否除干扰
	 */
	public void readData(String fileIn,String fileOut,boolean type){
		
		File fis=new File(fileIn);
		InputStreamReader in=null;
		BufferedReader bf=null;
        
		FileWriter writer=null;
		BufferedWriter bWriter=null;
		
		if(fis.exists()&&fis.isFile()){
			try{
				//将文件流写入字节输入流，再将字节输入流转化为字符输入流
				in = new InputStreamReader(new FileInputStream(fis),"UTF-8");
				/*将输入流写入缓存，为了提高效率，否则每次调用read(),reaLline()直接从文件
				*读取字节再转化为字符，效率低下
				*/
				bf = new BufferedReader(in);
				
				writer = new FileWriter(new File(fileOut));//每次都是重新写入，而不是从文件的末尾写入；
				bWriter = new BufferedWriter(writer);
				String line = null;
				while((line=bf.readLine())!=null){//每次读取一个文本行
					//除去干扰数据
					if(type&&line.contains("?")) continue;
					String tmpStr=dataHandle(line);
					bWriter.write(tmpStr);
					bWriter.newLine();//写入一个行分隔符
					}
				}catch(Exception e){//抛出UnsupportedEncodingException、ileNotFoundException
				System.out.println("读取文件出错");
				e.printStackTrace();
			}finally{
				try {
					bf.close();
					in.close();
					bWriter.flush();
					bWriter.close();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else{
			System.out.println("找不到文件");
		}
		
	}
	
	/**
	 * 读取测试文档
	 * @param fileIn读入的测试文件
	 * @return 以集合的形式返回数据
	 */
	 public ArrayList<ArrayList<String>> readTest(String fileIn){
		 ArrayList<ArrayList<String>> testList=new  ArrayList<ArrayList<String>>();
		
		 FileReader reader=null;
		 BufferedReader in=null;
		 
		 try{
			 reader=new FileReader(new File(fileIn));
			 in=new BufferedReader(reader);
			 String line=null;
			 while((line=in.readLine())!=null){//每次读取一个文本行
				 ArrayList<String> list=new ArrayList<String>();
				 String[] arr=line.split(",");
				 for(int i=0;i<arr.length;i++){
					 list.add(arr[i]);
				}
				 testList.add(list);
			 }
			 
		 }catch(Exception e){
			 System.out.println("读取文件出错");
			 e.printStackTrace();
		 }finally{
			 try {
				in.close();
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
		return testList;
	 }
	 
	/**
	 * 数据处理，将数据转化为数字，便于统计操作，,忽略原始数据中的fnlwgt、education_num、capital-gain
	 * capital-loss这些特征
	 * @param str需要处理的数据
	 * @return处理后的数据
	 */
	public String dataHandle(String str){
		String separator=",";//用逗号作为分隔符
		String tmp[]=str.split(", ");//原始数据是用”,“和” “(一个逗号和一个空格)来分隔数据的
		StringBuilder sb=new StringBuilder();
		sb.append(ageConversion(tmp[0])+separator);
		sb.append(workclassConversion(tmp[1])+separator);//workclass
    	sb.append("");//fnlwgt忽略
    	sb.append(educationConversion(tmp[3])+separator);//education
    	sb.append("");//education_num忽略
    	sb.append(maritalStatusConversion(tmp[5])+separator);//marital_status
    	sb.append(occupationConversion(tmp[6])+separator);//occupation
    	sb.append(relationshipConversion(tmp[7])+separator);//relationship
    	sb.append(raceConversion(tmp[8])+separator);//race
    	sb.append(sexConversion(tmp[9])+separator);//sex
    	sb.append("");//capital-gain
    	sb.append("");//capital-loss
    	sb.append(hoursPerWeekConversion(tmp[12])+separator);//hours-per-wee：每十个小时一个区间
    	sb.append(nativeCountryConversion(tmp[13])+separator);//nativeCountry：只分为两种，美国和外国
    	sb.append(resultConversion(tmp[14]));
		return sb.toString();
	}
	
	/**
	 * age: continuous
	 * @param string 年龄
	 * @return
	 */
	private int ageConversion(String string) {
		if (!string.contains("?")) {
			int ageTemp = Integer.parseInt(string);
			if (ageTemp<10) {
				return 1;
			} else if (ageTemp<20) {
				return 2;
			} else if (ageTemp<30) {
				return 3;
			} else if (ageTemp<40) {
				return 4;
			} else if (ageTemp<50) {
				return 5;
			} else if (ageTemp<60) {
				return 6;
			} else if (ageTemp<70) {
				return 7;
			} else if (ageTemp<80) {
				return 8;
			} else if (ageTemp<90) {
				return 9;
			} else {
				return 10;
			}
		}
		return 0;
	}
	
	/**
	 *  Private, Self-emp-not-inc, Self-emp-inc, Federal-gov, Local-gov, State-gov, Without-pay, Never-worked.
	 * @param string工作类型
	 * @return
	 */
	private int workclassConversion(String string) {
		if (!string.contains("?")) {
			if (string.contains("Private")) {
				return 1;
			} else if (string.contains("Self-emp-not-inc")) {
				return 2;
			} else if (string.contains("Self-emp-inc")) {
				return 3;
			} else if (string.contains("Federal-gov")) {
				return 4;
			} else if (string.contains("Local-gov")) {
				return 5;
			} else if (string.contains("State-gov")) {
				return 6;
			} else if (string.contains("Without-pay")) {
				return 7;
			} else if (string.contains("Never-worked")) {
				return 8;
			}
		}
		return 0;
	}
	
	/**
	 * Bachelors, Some-college, 11th, HS-grad, Prof-school, Assoc-acdm, Assoc-voc, 9th, 7th-8th, 12th, Masters,
	 *  1st-4th, 10th, Doctorate, 5th-6th, Preschool.
	 * @param string 教育
	 * @return
	 */
	private int educationConversion(String string) {
		if (!string.contains("?")) {
			if (string.contains("Bachelors")) {
				return 1;
			} else if (string.contains("Some-college")) {
				return 2;
			} else if (string.contains("11th")) {
				return 3;
			} else if (string.contains("HS-grad")) {
				return 4;
			} else if (string.contains("Prof-school")) {
				return 5;
			} else if (string.contains("Assoc-acdm")) {
				return 6;
			} else if (string.contains("Assoc-voc")) {
				return 7;
			} else if (string.contains("9th")) {
				return 8;
			} else if (string.contains("7th-8th")) {
				return 9;
			} else if (string.contains("12th")) {
				return 10;
			} else if (string.contains("Masters")) {
				return 11;
			} else if (string.contains("1st-4th")) {
				return 12;
			} else if (string.contains("10th")) {
				return 13;
			} else if (string.contains("Doctorate")) {
				return 14;
			} else if (string.contains("5th-6th")) {
				return 15;
			} else if (string.contains("Preschool")) {
				return 16;
			}
		}
		return 0;
	}
	
	/**
	 * Married-civ-spouse, Divorced, Never-married, Separated, Widowed, Married-spouse-absent, Married-AF-spouse.
	 * @param string婚姻状况
	 * @return
	 */
	private int maritalStatusConversion(String string) {
		if (!string.contains("?")) {
			if (string.contains("Married-civ-spouse")) {
				return 1;
			} else if (string.contains("Divorced")) {
				return 2;
			} else if (string.contains("Never-married")) {
				return 3;
			} else if (string.contains("Separated")) {
				return 4;
			} else if (string.contains("Widowed")) {
				return 5;
			} else if (string.contains("Married-spouse-absent")) {
				return 6;
			} else if (string.contains("Married-AF-spouse")) {
				return 7;
			}
		}
		return 0;	
	}
	
    /**
     * Tech-support, Craft-repair, Other-service, Sales, Exec-managerial, Prof-specialty, Handlers-cleaners, Machine-op-inspct, Adm-clerical, Farming-fishing,
     *  Transport-moving, Priv-house-serv, Protective-serv, Armed-Forces.
     * @param string 职业
     * @return
     */
	private int occupationConversion(String string) {
		if (!string.contains("?")) {
			if (string.contains("Tech-support")) {
				return 1;
			} else if (string.contains("Craft-repair")) {
				return 2;
			} else if (string.contains("Other-service")) {
				return 3;
			} else if (string.contains("Sales")) {
				return 4;
			} else if (string.contains("Exec-managerial")) {
				return 5;
			} else if (string.contains("Prof-specialty")) {
				return 6;
			} else if (string.contains("Handlers-cleaners")) {
				return 7;
			} else if (string.contains("Machine-op-inspct")) {
				return 8;
			} else if (string.contains("Adm-clerical")) {
				return 9;
			} else if (string.contains("Farming-fishing")) {
				return 10;
			} else if (string.contains("Transport-moving")) {
				return 11;
			} else if (string.contains("Priv-house-serv")) {
				return 12;
			} else if (string.contains("Protective-serv")) {
				return 13;
			} else if (string.contains("Armed-Forces")) {
				return 14;
			}
		}
		return 0;
	}
	
	/**
	 * Wife, Own-child, Husband, Not-in-family, Other-relative, Unmarried.
	 * @param string 关系
	 * @return
	 */
	private int relationshipConversion(String string) {
		if (!string.contains("?")) {
			if (string.contains("Wife")) {
				return 1;
			} else if (string.contains("Own-child")) {
				return 2;
			} else if (string.contains("Husband")) {
				return 3;
			} else if (string.contains("Not-in-family")) {
				return 4;
			} else if (string.contains("Other-relative")) {
				return 5;
			} else if (string.contains("Unmarried")) {
				return 6;
			}
		}
		return 0;	
	}

    /**
     * White, Asian-Pac-Islander, Amer-Indian-Eskimo, Other, Black.
     * @param string 种族
     * @return
     */
	private int raceConversion(String string) {
		if (!string.contains("?")) {
			if (string.contains("White")) {
				return 1;
			} else if (string.contains("Asian-Pac-Islander")) {
				return 2;
			} else if (string.contains("Amer-Indian-Eskimo")) {
				return 3;
			} else if (string.contains("Other")) {
				return 4;
			} else if (string.contains("Black")) {
				return 5;
			}
		}
		return 0;	
	}
	
	/**
	 * Female, Male.
	 * @param string 性别
	 * @return
	 */
	private int sexConversion(String string) {
		if (!string.contains("?")) {
			if (string.contains("Female")) {
				return 1;
			} else if (string.contains("Male")) {
				return 2;
			}
		}
		return 0;	
	}
	
	/**
	 *  continuous
	 * @param string 每周工作小时数
	 * @return
	 */
	private int hoursPerWeekConversion(String string) {
		if (!string.contains("?")) {
			int workHourTemp = Integer.parseInt(string);
			if (workHourTemp<10) {
				return 1;
			} else if (workHourTemp<20) {
				return 2;
			} else if (workHourTemp<30) {
				return 3;
			} else if (workHourTemp<40) {
				return 4;
			} else if (workHourTemp<50) {
				return 5;
			} else if (workHourTemp<60) {
				return 6;
			} else if (workHourTemp<70) {
				return 7;
			} else if (workHourTemp<80) {
				return 8;
			} else {
				return 9;
			}
		}
		return 0;
	}
	
	/**
	 *由于国籍太多，这里只区别是否为美国国籍
	 *United-States, Cambodia, England, Puerto-Rico, Canada, Germany, Outlying-US(Guam-USVI-etc), India, Japan, Greece, South, China, Cuba, Iran,Honduras,
	 *Philippines, Italy, Poland, Jamaica, Vietnam, Mexico, Portugal, Ireland, France, Dominican-Republic, Laos, Ecuador, Taiwan, Haiti, Columbia, Hungary, Guatemala, Nicaragua, Scotland, Thailand, Yugoslavia, El-Salvador,
	 * Trinadad&Tobago, Peru, Hong, Holand-Netherlands.
	 * @param string 国籍
	 * @return
	 */
	private int nativeCountryConversion(String string) {
		if (!string.contains("?")) {
			if (string.contains("United-States")) {
				return 1;
			} else {
				return 0;
			}
		}
		return 0;	
	}
	
	/**
	 * 是否超过50k;
	 * @param string年薪
	 * @return
	 */
	private String resultConversion(String string) {
		if (string.contains(">50K")) {
			return "yes";
		} else {
			return "no";
		}
	}
}
