package net.sf.wsutil;

/**
 * 
 *	This interface define action of generating javascript code.   
 *
 *	@version	0.1
 *
 */
public interface ICodeGenerator {
	
	/**
	 * Product javascript code uses to access web service. 
	 * 
	 * @return	string of code.
	 */
	public String writeCode();
}
