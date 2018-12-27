package util;

import java.io.IOException;
import java.io.OutputStream;

class StringOutputStream extends OutputStream
{
    private StringBuilder string = new StringBuilder();
    
    @Override
    public void write(byte[] b, int off, int len) throws IOException
    {
    	for(int i = off; i < off + len; i++)
    		this.string.append((char) b[i]);
    }
    
    @Override
    public void write(int b) throws IOException
    {
        this.string.append((char) b);
    }
    
    @Override
    public String toString()
    {
        return this.string.toString();
    }
}