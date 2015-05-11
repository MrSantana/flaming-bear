import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class CurriculumVitae implements Serializable{
	
private static final long serialVersionUID = 1003450186326955000L;

private enum Color{Black,Grey,Orange,Purple,Red,Green,Blue};
private enum Style{Casual,Classic,Oldstyle,Banking};
private String firstName;
private String lastName;
private String style = new String( Style.Casual.toString().toLowerCase());
private String colorScheme = new String( Color.Black.toString().toLowerCase());;
private String layout = new String("geometry");
private String sectionCollection[];
private int sectionNumber;
private StringBuilder content = new StringBuilder("");


public CurriculumVitae(int sections){
	this.sectionNumber = sections;
	this.sectionCollection = new String[sections];
}


public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public void setStyle(String style) {
	this.style = style;
}

public void setColorScheme(String colorSheme) {
	this.colorScheme = colorSheme;
}

public void setLayout(String layout) {
	this.layout = layout;
}


public void setSection(int number, Section section){
	if(number <= sectionNumber && number  >= 0){
		this.sectionCollection[number] = section.toString();
	}
	
}

public String getCV() throws IncompleteCVException{
	if (!(style.equals("")) && !(colorScheme.equals("")) && !(layout.equals("")) ){
		content.append("\\documentclass[11pt, a4paper]{moderncv} \n\\moderncvtheme[" + colorScheme +"]" + "{" + style +"}");
		content.append("\n\\usepackage[german]{babel}\n\\usepackage[utf8]{inputenc}\n\\usepackage{" + layout + "}");
		content.append("\n\\firstname{" + firstName + "}" + "\n\\lasttname{" + lastName + "}\n\\begin{document}\n\\makecvtitle");
		for(int i = 0; i <= sectionCollection.length - 1; i++){
			content.append(sectionCollection[i]);
		}
		content.append("\n\\end{document}");
		
	}
	else {
		throw new IncompleteCVException();
	}
	
	
	
	return content.toString();
}

public void saveCV(File target){
	ObjectOutputStream out;
	try {
		out = new ObjectOutputStream(new GZIPOutputStream((new FileOutputStream(target))));
		//out = new ObjectOutputStream(new FileOutputStream(target));	
		out.writeObject(this);	
		out.close();
	}
catch (FileNotFoundException e) {
	e.printStackTrace();
} catch (IOException e) {
	e.printStackTrace();
}
}

public static CurriculumVitae loadCV(File source) {
	CurriculumVitae loaded = null;
	ObjectInputStream in;
	try {
		in= new ObjectInputStream(new GZIPInputStream((new FileInputStream(source))));
	    try {
			loaded = (CurriculumVitae)in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	    in.close();
		
	} catch (FileNotFoundException e) {	
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return loaded;
}

}
