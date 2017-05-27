package com.bayviewglen.zork;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class Credits {
	public Credits() throws IOException{
		Desktop.getDesktop().open(new File("data/Nailing every high note in Take On Me"));
	}
}
