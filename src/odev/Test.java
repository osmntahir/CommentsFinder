package odev;

import java.io.IOException;

/**
 * 
 * 
 * @author Osman Tahir Özdemir  osman.ozdemir3@ogr.sakarya.edu.tr
 * @since 15/4/2023  11:59 PM
 * <p>
 * Yazilan programin test edildigi test programi
 * <p>
 *
 */

public class Test {

	public static void main(String[] args) throws IOException {
		
		
		Program program = new Program();
		program.setfileName(args[0]);
		
		program.FileReader(program.getfileName());

	}

}
