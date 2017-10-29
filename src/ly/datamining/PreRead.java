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
 * ������Ҫ���ڶ����ݵĴ���������ת��Ϊ���֣�����ͳ��
 */
public class PreRead {
   
	/**
	 * ��ȡ���ݣ���������ת��Ϊ���֣����ں�����ͳ��
	 * @param fileIn ������ļ�����Ҫ��������ݣ�
	 * @param fileOut �������ļ����������ļ���
	 * @param type �Ƿ������
	 */
	public void readData(String fileIn,String fileOut,boolean type){
		
		File fis=new File(fileIn);
		InputStreamReader in=null;
		BufferedReader bf=null;
        
		FileWriter writer=null;
		BufferedWriter bWriter=null;
		
		if(fis.exists()&&fis.isFile()){
			try{
				//���ļ���д���ֽ����������ٽ��ֽ�������ת��Ϊ�ַ�������
				in = new InputStreamReader(new FileInputStream(fis),"UTF-8");
				/*��������д�뻺�棬Ϊ�����Ч�ʣ�����ÿ�ε���read(),reaLline()ֱ�Ӵ��ļ�
				*��ȡ�ֽ���ת��Ϊ�ַ���Ч�ʵ���
				*/
				bf = new BufferedReader(in);
				
				writer = new FileWriter(new File(fileOut));//ÿ�ζ�������д�룬�����Ǵ��ļ���ĩβд�룻
				bWriter = new BufferedWriter(writer);
				String line = null;
				while((line=bf.readLine())!=null){//ÿ�ζ�ȡһ���ı���
					//��ȥ��������
					if(type&&line.contains("?")) continue;
					String tmpStr=dataHandle(line);
					bWriter.write(tmpStr);
					bWriter.newLine();//д��һ���зָ���
					}
				}catch(Exception e){//�׳�UnsupportedEncodingException��ileNotFoundException
				System.out.println("��ȡ�ļ�����");
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
			System.out.println("�Ҳ����ļ�");
		}
		
	}
	
	/**
	 * ��ȡ�����ĵ�
	 * @param fileIn����Ĳ����ļ�
	 * @return �Լ��ϵ���ʽ��������
	 */
	 public ArrayList<ArrayList<String>> readTest(String fileIn){
		 ArrayList<ArrayList<String>> testList=new  ArrayList<ArrayList<String>>();
		
		 FileReader reader=null;
		 BufferedReader in=null;
		 
		 try{
			 reader=new FileReader(new File(fileIn));
			 in=new BufferedReader(reader);
			 String line=null;
			 while((line=in.readLine())!=null){//ÿ�ζ�ȡһ���ı���
				 ArrayList<String> list=new ArrayList<String>();
				 String[] arr=line.split(",");
				 for(int i=0;i<arr.length;i++){
					 list.add(arr[i]);
				}
				 testList.add(list);
			 }
			 
		 }catch(Exception e){
			 System.out.println("��ȡ�ļ�����");
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
	 * ���ݴ���������ת��Ϊ���֣�����ͳ�Ʋ�����,����ԭʼ�����е�fnlwgt��education_num��capital-gain
	 * capital-loss��Щ����
	 * @param str��Ҫ���������
	 * @return����������
	 */
	public String dataHandle(String str){
		String separator=",";//�ö�����Ϊ�ָ���
		String tmp[]=str.split(", ");//ԭʼ�������á�,���͡� ��(һ�����ź�һ���ո�)���ָ����ݵ�
		StringBuilder sb=new StringBuilder();
		sb.append(ageConversion(tmp[0])+separator);
		sb.append(workclassConversion(tmp[1])+separator);//workclass
    	sb.append("");//fnlwgt����
    	sb.append(educationConversion(tmp[3])+separator);//education
    	sb.append("");//education_num����
    	sb.append(maritalStatusConversion(tmp[5])+separator);//marital_status
    	sb.append(occupationConversion(tmp[6])+separator);//occupation
    	sb.append(relationshipConversion(tmp[7])+separator);//relationship
    	sb.append(raceConversion(tmp[8])+separator);//race
    	sb.append(sexConversion(tmp[9])+separator);//sex
    	sb.append("");//capital-gain
    	sb.append("");//capital-loss
    	sb.append(hoursPerWeekConversion(tmp[12])+separator);//hours-per-wee��ÿʮ��Сʱһ������
    	sb.append(nativeCountryConversion(tmp[13])+separator);//nativeCountry��ֻ��Ϊ���֣����������
    	sb.append(resultConversion(tmp[14]));
		return sb.toString();
	}
	
	/**
	 * age: continuous
	 * @param string ����
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
	 * @param string��������
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
	 * @param string ����
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
	 * @param string����״��
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
     * @param string ְҵ
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
	 * @param string ��ϵ
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
     * @param string ����
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
	 * @param string �Ա�
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
	 * @param string ÿ�ܹ���Сʱ��
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
	 *���ڹ���̫�࣬����ֻ�����Ƿ�Ϊ��������
	 *United-States, Cambodia, England, Puerto-Rico, Canada, Germany, Outlying-US(Guam-USVI-etc), India, Japan, Greece, South, China, Cuba, Iran,Honduras,
	 *Philippines, Italy, Poland, Jamaica, Vietnam, Mexico, Portugal, Ireland, France, Dominican-Republic, Laos, Ecuador, Taiwan, Haiti, Columbia, Hungary, Guatemala, Nicaragua, Scotland, Thailand, Yugoslavia, El-Salvador,
	 * Trinadad&Tobago, Peru, Hong, Holand-Netherlands.
	 * @param string ����
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
	 * �Ƿ񳬹�50k;
	 * @param string��н
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
