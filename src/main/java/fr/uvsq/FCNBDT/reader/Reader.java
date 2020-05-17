package fr.uvsq.FCNBDT.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import fr.uvsq.FCNBDT.utils.CellType;

public class Reader {
	private String path;
	private CellType cells;
	private int width;
	private int length;
	private int nbStates;
	private int t;
	private int tmax;
	private int[] colors;
	
	private FileInputStream fis;
	private Map<Integer, String> logs = null;
	
	public Reader(String path) throws IOException {
		setPath(path);
	}
	
	public void setPath(String path) throws IOException {
		this.path = path;
		fis = new FileInputStream(path);
		File f;
		BufferedReader l;
		String s;
		if((f = new File(
				Paths.get(Paths.get(path).toAbsolutePath().getParent().toString(),
						"logs.txt").toString())
				).exists()) {
			l = new BufferedReader(new FileReader(f));
			logs = new HashMap<Integer, String>();
			while((s = l.readLine()) != null) {
				int i = 0;
				boolean debut = true;
				while (i < s.length() && 
				        ((debut && s.charAt(i) == '-') || Character.isDigit(s.charAt(i)))) {
				    i++;
				    debut = false;
				}
				logs.put(Integer.parseInt(s.substring(0, i)), s.substring(i, s.length()));
			}
		}
		readHeader();
		this.tmax = fis.available()/(width * length * 4);
	}

    public int readNextInt(int[] buff, int size) throws IOException {
		byte[] b = new byte[size*4];
		if(fis.getChannel().size() - fis.getChannel().position() < size)
			return -1;
		try {
			if(fis.read(b) < size*4)
				return -1;
		} catch (IOException e) {
			return -1;
		}
		ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer().get(buff);
		//ByteBuffer.wrap(b).asIntBuffer().get(buff);
		return 0;
	}
	
	public void readHeader() throws IOException {
		int[] buff = new int[4];
		fis.getChannel().position(0);
		readNextInt(buff, 4);
		cells = ((buff[0] & 1) == 1)?CellType.SQUARE:CellType.HEXAGONE;
		width = buff[1];
		length = buff[2];
		nbStates = buff[3];
		buff = new int[nbStates];
		readNextInt(buff, nbStates);
		colors = buff;
		t = -1;
	}
	
	public long getOffset(int t) {
		return (4 + nbStates + (t) * width * length) * 4;
	}
	
	public int readNext(int[] matrice) throws IOException{
		//System.out.println(fis.available()/(width * length * 4));
		if(fis.available() >= width * length * 4)
			t++;
		else 
			return -1;
		fis.getChannel().position(getOffset(t));
		return readNextInt(matrice, width * length);
	}
	
	public int readPrevious(int[] matrice) throws IOException{
		if(t > 0)
			t--;
		
		fis.getChannel().position(getOffset(t));
		return readNextInt(matrice, width * length);
	}
	
	public int readExactTime(int[] matrice, int time) throws IOException {
	    if(time < 0 || time > tmax)
	        return -1;
	    if(time == this.t)
	        return 0;
	    
	    this.t = time;
	    fis.getChannel().position(getOffset(t));
        return readNextInt(matrice, width * length);
	}
	
	public boolean logExist(int line) {
		if(logs == null)
			return false;
		return logs.containsKey(line);
	}
	
	public String getLog(int line) {
		if(logs == null)
			return null;
		return logs.get(line);
	}

	public CellType getCellType() {
		return cells;
	}
	
	public String getPath() {
		return path;
	}
	
	public int getWidth() {
		return width;
	}

	public int getLength() {
		return length;
	}

	public int getNbStates() {
		return nbStates;
	}

	public void setT(int t) {
		this.t = t;
	}

	public int getT() {
		return t;
	}
	
	public int getTmax() {
        return tmax;
    }

	public int[] getColors() {
		return colors;
	}
	
}
