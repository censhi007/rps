package net.sf.wsutil;

import java.io.PrintStream;

/**
 *  Write log information.Support redirection of log output. 
 *
 */
class LogWriter {

	// log information output,the default is stdout,
	// if you set logger null with method setLoggerWriter,
	// no log information print out
	private static PrintStream logger = System.out;

	
	public static void setLogWriter(PrintStream pst){
		logger = pst;
	}
	
	/**
	 * print program running information
	 * @param log
	 */
	public static  void writeLog(String log){
		if (logger != null){
			logger.println(log);
		}
	}
	
	
	public static void writeLog(Throwable t){
		if (logger != null){
			for (StackTraceElement ste :t.getStackTrace()){
				logger.println(ste.toString());
			}
		}
	}

}
