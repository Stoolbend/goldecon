package org.goldecon.updater;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.entity.Player;
import org.goldecon.goldeconplus.Goldecon;

public class GoldeconUpdater
{
	Goldecon plugin;
	
	public GoldeconUpdater(Goldecon pl)
	{
		plugin = pl;
	}
	
	public void checkForUpdate(String branch, Player plr)
	{
		// Set for the manifest to download.
		try 
		{
			int dlState = downloadFile("https://raw.github.com/Stoolbend/goldecon/plus/goldecon+/updater/current.txt", "temp", "manifest.mf");
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	//Downloader class
	private int downloadFile(String url, String dir, String filename) throws IOException, MalformedURLException
	{
		BufferedInputStream in;
		URL urlU;
		URLConnection urlC;
		urlU = new URL(url);
		urlC = urlU.openConnection();
		int fileSize = urlC.getContentLength();
		in = new BufferedInputStream(new URL(url).openStream());
		//make folder/file
		if(!new File(dir).exists())
		{
			new File(dir).mkdir();
		}
		new File(dir + filename).createNewFile();
		FileOutputStream fos;
		fos = new FileOutputStream(dir + filename);
		BufferedOutputStream bout = new BufferedOutputStream(fos,fileSize);		
		byte data[] = new byte[1];
		
		while(in.read(data,0,1)>=0)
		{
			bout.write(data);
		}
		bout.close();
		in.close();
		return 1;
	}
}
