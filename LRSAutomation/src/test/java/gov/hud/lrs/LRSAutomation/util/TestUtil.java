package gov.hud.lrs.LRSAutomation.util;

import java.util.Hashtable;

public class TestUtil {

	
	// finds if the test suite is runnable 
	public static boolean isSuiteRunnable(Xls_Reader xls , String suiteName){
		boolean isExecutable=false;
		for(int i=2; i <= xls.getRowCount("Test Suite") ;i++ ){
			String suite = xls.getCellData("Test Suite", "TSID", i);
			String runmode = xls.getCellData("Test Suite", "Runmode", i);
		
			if(suite.equalsIgnoreCase(suiteName)){
				if(runmode.equalsIgnoreCase("Y")){
					isExecutable=true;
				}else{
					isExecutable=false;
				}
			}

		}
		xls=null; 
		return isExecutable;
	
}
	
	
	public static boolean isTestCaseRunnable(Xls_Reader xls, String testCaseName){
		boolean isExecutable=false;
		for(int i=2; i<= xls.getRowCount("Test Cases") ; i++){
			//String tcid=xls.getCellData("Test Cases", "TCID", i);
			//String runmode=xls.getCellData("Test Cases", "Runmode", i);
			//System.out.println(tcid +" -- "+ runmode);
			
			
			if(xls.getCellData("Test Cases", "TCID", i).equalsIgnoreCase(testCaseName)){
				if(xls.getCellData("Test Cases", "Runmode", i).equalsIgnoreCase("Y")){
					isExecutable= true;
				}else{
					isExecutable= false;
				}
			}
		}
		
		return isExecutable;
		
	}

	
		public static boolean isExecutable( String testName,Xls_Reader xls){
			for(int rNum=2;rNum<=xls.getRowCount("Test Cases");rNum++){
				if(testName.equals(xls.getCellData("Test Cases", "TCID",rNum))){
					if(xls.getCellData("Test Cases","Runmode",rNum).equals("Y"))
						return true;
					else
						return false;
				}
			}
			return false;
		}
		
		
		// return the test data from a test in a 2 dim array
		
		public static Object[][] getData(String testCase,Xls_Reader xls){
	        System.out.println("*************");
	        // find the test in xls
	        // find number of cols in test
	        // number of rows in test
	        // print the data of test
	        // put the data of the test
	       
	      
	        int testCaseStartRowNum=0;
	        for(int rNum=1;rNum<=xls.getRowCount("Test Data");rNum++){
	            if(testCase.equals(xls.getCellData("Test Data", 0, rNum))){
	                testCaseStartRowNum = rNum;
	                break;
	            }
	        }
	        System.out.println("Test Starts from row -> "+ testCaseStartRowNum);
	      
	      
	        // total cols
	        int colStartRowNum=testCaseStartRowNum+1;
	        int cols=0;
	        while(!xls.getCellData("Test Data", cols, colStartRowNum).equals("")){
	            cols++;
	        }
	        System.out.println("Total cols in test -> "+ cols);
	      

	        // rows
	        int rowStartRowNum=testCaseStartRowNum+2;
	        int rows=0;
	        while(!xls.getCellData("Test Data", 0, (rowStartRowNum+rows)).equals("")){
	            rows++;
	        }
	        System.out.println("Total rows in test -> "+ rows);
	        Object[][] data = new Object[rows][1];
	        Hashtable<String,String> table=null;
	      
	        // print the test data
	        for(int rNum=rowStartRowNum;rNum<(rows+rowStartRowNum);rNum++){
	        table=new Hashtable<String,String>();
	            for(int cNum=0;cNum<cols;cNum++){
	                table.put(xls.getCellData("Test Data", cNum, colStartRowNum),xls.getCellData("Test Data", cNum, rNum));
	                //System.out.print(xls.getCellData("Test Data", cNum, rNum)+" - ");
	            }
	            data[rNum-rowStartRowNum][0]=table;
	            //System.out.println();
	        }

	        return data;// dummy
	      
	      
	      
	      
	    }

	  //true - Y
	    //false - N

		
		
		public static Object[][] getData(Xls_Reader xls , String testCaseName){
			// if the sheet is not present
			if(! xls.isSheetExist(testCaseName)){
				xls=null;
				return new Object[1][0];
			}
			
			
			int rows=xls.getRowCount(testCaseName);
			int cols=xls.getColumnCount(testCaseName);
			System.out.println("Rows are -- "+ rows);
			System.out.println("Cols are -- "+ cols);
			
		    Object[][] data =new Object[rows-1][cols-3];
			for(int rowNum=2;rowNum<=rows;rowNum++){
				for(int colNum=0;colNum<cols-3;colNum++){
					System.out.print(xls.getCellData(testCaseName, colNum, rowNum) + " -- ");
					data[rowNum-2][colNum] = xls.getCellData(testCaseName, colNum, rowNum);
				}
				System.out.println();
			}
			return data;
			
		}
		
		
		
		// checks Runmode for dataSet
		public static String[] getDataSetRunmodes(Xls_Reader xlsFile,String sheetName){
			String[] runmodes=null;
			if(!xlsFile.isSheetExist(sheetName)){
				xlsFile=null;
				sheetName=null;
				runmodes = new String[1];
				runmodes[0]="Y";
				xlsFile=null;
				sheetName=null;
				return runmodes;
			}
			runmodes = new String[xlsFile.getRowCount(sheetName)-1];
			for(int i=2;i<=runmodes.length+1;i++){
				runmodes[i-2]=xlsFile.getCellData(sheetName, "Runmode", i);
			}
			xlsFile=null;
			sheetName=null;
			return runmodes;
			
		}

		
		

		// update results for a particular data set	
		public static void reportDataSetResult(Xls_Reader xls, String testCaseName, int rowNum,String result){	
			xls.setCellData(testCaseName, "Results", rowNum, result);
		}
		
		// return the row num for a test
		public static int getRowNum(Xls_Reader xls, String id){
			for(int i=2; i<= xls.getRowCount("Test Cases") ; i++){
				String tcid=xls.getCellData("Test Cases", "TCID", i);
				
				if(tcid.equals(id)){
					xls=null;
					return i;
				}
				
				
			}
			
			return -1;
		}
		
	
}
