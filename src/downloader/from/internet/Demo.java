package downloader.from.internet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Demo {

	public static void main(String[] args) throws IOException, FileException {

		File f = createFIle();

		try {
			readAndDownloadFromFileWithNumberOfThreads(f, 3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void readAndDownloadFromFileWithNumberOfThreads(File f, int numberOfThreads)
			throws FileNotFoundException, IOException, MalformedURLException, InterruptedException {

		FileInputStream stream = new FileInputStream(f);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

		List<String> allOfUrls = new LinkedList<String>();
		String x;
		int index = 0;

		while ((x = reader.readLine()) != null) {
			allOfUrls.add(x);
		}

		int partsOfText = (allOfUrls.size() / numberOfThreads);

		String[] arrayUrls = new String[allOfUrls.size()];
		arrayUrls = allOfUrls.toArray(arrayUrls);
		List<Downloader> threads = new ArrayList<Downloader>(10);
		for (int innerIndex = 0; innerIndex < numberOfThreads; innerIndex++) {
			Downloader aDownloader = new Downloader(arrayUrls, innerIndex * partsOfText,
					((innerIndex + 1) * partsOfText) - 1);
			Thread aThread = new Thread(aDownloader);
			threads.add(aDownloader);
			aThread.start();
			aThread.join();
		}
		
		long timeForAll = 0;
		for (Downloader t : threads) {
			timeForAll += t.getTimeForThread();
		}
		System.out.println("all time is " + timeForAll);
	}

	private static File createFIle() throws FileException {
		File file = new File("test.txt");

		if (!file.exists()) {
			throw new FileException("nqma takuyv fail");
		}
		return file;
	}
}
