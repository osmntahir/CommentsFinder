package odev;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;
import java.util.regex.Matcher;


/**
 * 
 * 
 * @author Osman Tahir Özdemir  osman.ozdemir3@ogr.sakarya.edu.tr
 * @since 15/4/2023  11:44 PM
 * <p>
 * Istenilen programi yazmak icin gerekli olan degisken ve fonksiyonlarin oldugu 
 * Program sinifi
 * <p>
 *
 */

public class Program extends Dosya {

	private	 int functioncontrol = 0;    
	private   int singlecommentscount =0;
	private   int javadocCount = 0;
	private   int multilineCommentsCount = 0;
     
  // kontrol degiskenleri
	private  boolean javadocMod = false;
	private   boolean multilineCommentMod = false;
	private   boolean singleCommentMod = false;
     private  boolean functionControl= false;

     // yedek string degiskenleri
     private String functionName = "";
     private String tempJavadoc = "";
     private String tempSingleComment= "";
     
     private String fileName;
     
 	
 	public String getfileName() {
 		return fileName;
 	}

 	public void setfileName(String fileName) {
 		this.fileName = fileName;
 	}
 	private void writeCommentCounts()
 	{
 		System.out.println("                      tekli yorum sayisi   : " + singlecommentscount);
         System.out.println("                      coklu yorum sayisi   : " + multilineCommentsCount);
         System.out.println("                      javadoc yorum sayisi : " + javadocCount);
         System.out.println("                      ------------------------");
 	}
     
     public void FileReader(String dosyaAdi) throws IOException
     {
    	 BufferedReader reader = new BufferedReader(new FileReader(dosyaAdi));
    	    String line = reader.readLine();
    	    
    	    FileWriter javadocWriter = new FileWriter("javadoc.txt");
    	    FileWriter multilineCommentWriter = new FileWriter("cokluyorum.txt");
    	    FileWriter singleCommentWriter = new FileWriter("tekliyorum.txt"); 
    	    
    	    while (line!=null) {
            	
            	Matcher classMatcher = classPattern.matcher(line);
                Matcher functionMatcher = functionPattern.matcher(line);
               
                // coklu yorum modu mu yoksa javadoc mu karar veren if kosulu
                if (!multilineCommentMod) {
                    if (line.contains("/**")) {
                    		
                    	javadocMod = true;
                        if (functionControl)
                        {
                        	javadocWriter.write("Fonksiyon Adi: " + functionName+"\n");
                        }
                        javadocCount++;
                    } else if (line.contains("/*")) {
                    	multilineCommentMod = true;
                    	singleCommentWriter.write("Fonksiyon Adi: " + functionName +"\n");
                        multilineCommentsCount++;
                    }
                }
                // javadoc ile ilgili islemler
             
                if (javadocMod) {
                	// javadoc fonksiyon icinde ise direkt dosyaya yazdirilir
                	if(functionControl)
                	{
                		String comment;
                		comment = line.replaceAll("[*/]","");
                		javadocWriter.write(comment.trim());
                     if (line.contains("*/")) {
                    	 javadocMod = false;
                            
                    	 tempJavadoc = "";
                    	 javadocWriter.write("\n----------------------------- \n");
                            }
                	}
                	// javadoc fonksiyon disinda ise yedek javadoca kaydedilir
                	else
                	{
                		String comment;
                		comment = line.replaceAll("[*/]","");
                		
                		comment = comment.trim();
                		
                		tempJavadoc += comment.trim();
                    			if (line.contains("*/")) {
                            	
                    				javadocMod = false;
                               
                            }
                    		
                	 }
                    
                   // coklu yorum modu ile ilgili islemler
                } else if (multilineCommentMod) {
                	String comment;
            		comment = line.replaceAll("[*/]","");
    	                if (line.contains("*/")) {
    	                	
    	                	multilineCommentMod = false; 
    	                    int commentindex =line.indexOf("*/");
    	                    comment = line.substring(0,commentindex);
    	                    comment = comment.replaceAll("[*/]","");
    	                  
    	                    
    	                    multilineCommentWriter.write(comment);
    	                   
    	                    multilineCommentWriter.write("\n----------------------\n");
    	                }
    	                else
    	                {
    	                	
    	                	multilineCommentWriter.write(comment); 
    	                }               
                   
                }
                // sinif eslesmesi bul
                if (classMatcher.find()) 
                {
                    System.out.println("Sinif: " + classMatcher.group(1));
                } 
                // fonksiyon eslesmesi bul
                else if (functionMatcher.find())
                {
                	functionControl =true;
                
                	functionName=functionMatcher.group(4);
                    System.out.println("              Fonksiyon: " + functionMatcher.group(4));
                    // yedek javadoc kontrol edilir
                    if(!(tempJavadoc.equals("")))
                    {
                    	javadocWriter.write("Fonksiyon: " + functionName+"\n");
                    	 
                    	javadocWriter.write(tempJavadoc.trim());
                   
                    	javadocWriter.write("\n--------------------------\n");
                        tempJavadoc ="";
                    }             
                    functioncontrol++;
                }
               
               
                // tekli Yorumları kontrol et
                 	if (line.contains("//") && functionControl ) {
                 		singleCommentMod = true;
                    		
                 		singleCommentWriter.write("Fonksiyon Adi: " + functionName +"\n");
                     tempSingleComment = line.substring(line.indexOf("//") + 2);
                     singleCommentWriter.write(tempSingleComment + "\n");
                    	singleCommentWriter.write("----------------------------" + "\n");
                      
                    	  singlecommentscount++;
                    }

                    // Fonksiyon bitişini kontrol et
                    if (line.contains("}") && functioncontrol ==1) {
                    	
                    	tempSingleComment = "";
                    	functionControl=false;
                        functioncontrol--;
                        writeCommentCounts();
                        singlecommentscount =0;
                        multilineCommentsCount =0;
                        javadocCount=0;
                      
                    }
                
                    line=reader.readLine();
            }
    	    singleCommentWriter.close();
            javadocWriter.close();
            multilineCommentWriter.close();
            System.out.println("Programdan cikmak icin herhangi bir tusa basiniz ");
            try (Scanner scanner = new Scanner(System.in)) {
    			scanner.nextLine();
    		}
     }
     
    
}

     
     
     
     
     
     
     
     
     
 
