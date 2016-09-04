package downloader.from.internet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloader extends Thread {
	private static final int DEFAULT_SIZE_PACKAGE = 2048;
	

	private long timeForThread;

	private int start;
	private int end;
	private String[] array;

	@Override
	public void run() {
		try {
			downloadFromUrls(this.array, this.start, this.end);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Downloader(String[] array, int start, int end) {
		super();
		this.start = start;
		this.end = end;
		this.array = array;
	}

	private void downloadFromUrls(String[] allOfUrls, int start, int end)
			throws MalformedURLException, IOException, FileNotFoundException {
		long currentStart = System.currentTimeMillis();
		InputStream internetStream = null;
		OutputStream outputStream = null;

		try {
			for (int index = start; index < end; index++) {
				URL url = new URL(allOfUrls[index]);
				internetStream = url.openStream();
				outputStream = new FileOutputStream("img" + (index++) + ".jpg");

				int length;
				byte[] buffer = new byte[DEFAULT_SIZE_PACKAGE];
				while ((length = internetStream.read(buffer)) != -1) {
					//System.out.println("Buffer Read of length: " + length);
					outputStream.write(buffer, 0, length);
				}
				//System.out.println("File saved");
			}
		} finally {
			internetStream.close();
			outputStream.close();
		}
		
		this.timeForThread = (System.currentTimeMillis() - currentStart);
		System.out.println("One thread Time :" + this.timeForThread);
	}

	public long getTimeForThread() {
		return timeForThread;
	}
}
