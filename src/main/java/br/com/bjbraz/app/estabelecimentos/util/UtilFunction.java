package br.com.bjbraz.app.estabelecimentos.util;

public class UtilFunction {
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isBlanckOrNull(String s){
		if(s == null){
			return true;
		}
		
		if("".equals(s)){
			return true;
		}
		
		if("".equals(s.trim())){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNotBlanckOrNull(String s){
		return !isBlanckOrNull(s);
	}

}
