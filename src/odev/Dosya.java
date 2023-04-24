package odev;

import java.util.regex.Pattern;
/**
 * 
 * 
 * @author Osman Tahir Özdemir  osman.ozdemir3@ogr.sakarya.edu.tr
 * @since 14/4/2023  10:50 AM
 * <p>
 * Sinif ve Fonksiyonlari bulan desenlerin saklandigi Dosya sinifi
 * <p>
 *
 */
public class Dosya {
	
     // sinif bulan desen        
	protected Pattern classPattern = Pattern.compile("class\\s+(\\w+)"); 
    
    // fonksiyon bulan desen
    protected Pattern functionPattern = Pattern.compile("(public|private|protected)?\\s*(static)?\\s*(\\w+)\\s+(\\w+)\\s*\\(");
   
   
   
}
