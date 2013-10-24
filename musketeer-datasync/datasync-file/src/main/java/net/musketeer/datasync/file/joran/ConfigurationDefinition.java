package net.musketeer.datasync.file.joran;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "configuration")
public class ConfigurationDefinition extends AbstractDefinition {

	@XmlElement
	private String dir;

	public String getDir() {
		return this.dir;
	}

}