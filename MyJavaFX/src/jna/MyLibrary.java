package jna;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface MyLibrary extends Library {
	MyLibrary INSTANCE = Native.load("C:\\MDPacket\\price.dll", MyLibrary.class);
	
	int initFileTSE();
	String getJsonPriceFileTSE();
	int endFileTSE();
	
}
