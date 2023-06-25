package jna;

public class SampleTest {

	public static void main(String[] args) {
		int initReturnValue = MyLibrary.INSTANCE.initFileTSE();
		System.out.println("initReturnValue = " + initReturnValue);
		
		int endReturnValue = MyLibrary.INSTANCE.endFileTSE();
		System.out.println("endReturnValue = " + endReturnValue);
		
	}

}
