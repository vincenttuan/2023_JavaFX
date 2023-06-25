package jna;

public class SampleTest {

	public static void main(String[] args) {
		int initReturnValue = MyLibrary.INSTANCE.initFileTSE();
		System.out.println("initReturnValue = " + initReturnValue);
		
		if(initReturnValue == 1) {
        	for (int i=0; i<20_0000; i++) {
        		String json = MyLibrary.INSTANCE.getJsonPriceFileTSE();
        		if(json == null) continue;
        		System.out.println(i + ": " + json);
        		
        	}
        	//int endReturnValue = MyLibrary.INSTANCE.endFileTSE();
        	//System.out.println("endReturnValue = " + endReturnValue);
        }
		
		
	}

}
