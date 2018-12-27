package util;

import java.io.IOException;
import java.io.InputStream;

class StringInputStream extends InputStream
{
	private String content;
	private int index;
	
	public StringInputStream(String content)
	{
		this.content = content;
		this.index = 0;
	}
	
	@Override
	public int read() throws IOException
	{
		if(index == content.length())
			return -1;
		index++;
		return content.toCharArray()[index-1];
	}
}