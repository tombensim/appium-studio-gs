package logger;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class LoggerClass {
	private Logger logFile;
	FileHandler fh;
	
	
	public LoggerClass(String testName){
		
		try{
			
			logFile = Logger.getLogger(testName);
		    fh = new FileHandler(testName + ".txt");  
	        logFile.addHandler(fh);
	        
	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	}
	public void writeLine(String line){

	    try {  
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  

	        logFile.info(line);  

	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    }  
	}
}
