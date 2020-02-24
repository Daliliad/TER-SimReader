package reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import utils.CellType;

public class Reader {
	private String path;
	private CellType cells;
	private int width;
	private int length;
	private int nbStates;
	private int t;
	private int[] colors;
	
	private FileInputStream fis;
	
	public Reader(String path) throws IOException {
		setPath(path);
	}
	
	public void setPath(String path) throws IOException {
		this.path = path;
		fis = new FileInputStream(path);
		readHeader();
	}
	
	public int readNextInt(int[] buff, int size) {
		byte[] b = new byte[size*4];
		try {
			fis.read(b);
		} catch (IOException e) {
			return -1;
		}
		ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer().get(buff);
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
		t++;
		fis.getChannel().position(getOffset(t));
		return readNextInt(matrice, width * length);
	}
	public int readPrevious(int[] matrice) throws IOException{
		if(t > 0)
			t--;
		
		fis.getChannel().position(getOffset(t));
		return readNextInt(matrice, width * length);
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

	public int[] getColors() {
		return colors;
	}
	
}
