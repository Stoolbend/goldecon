/* Copyright (c) 2011, Tom Schoepe (Kierrow)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of the <ORGANIZATION> nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.kierrow.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * The <b>SaveManager</b> class lets you load and save instances of the <b>Save</b> class,
 * that are used to store whatever data you wish (see <b>{@link Save}</b> for more information).
 * 
 * @author Tom Schoepe (Kierrow)
 * @version 1
 * 
 * @see Save
 */
public final class SaveManager {
	///////////////////////////////////
	private static final String EXTENSION = ".bin";
	
	///////////////////////////////////
	private SaveManager() {}
	
	///////////////////////////////////
	/**
	 * This method allows for easy loading of files.<br>
	 * If the requested file is not found, or is corrupted in any way, an empty save will be returned.<br>
	 * <br>
	 * Note: 'filename' does not mean the entire path to the file must be passed. The path and file extension will be added automatically, only the name must be taken care of...
	 * 
	 * @param filename the location of the file
	 * @return the saved {@link Save} or an empty one, if the file is not found
	 */
	@SuppressWarnings("unchecked")
	public static final <T extends Serializable> Save<T> load(JavaPlugin plugin, String filename) {
		String path = "plugins" + File.separator + plugin.getDescription().getName() + File.separator;
		
		File file = new File(path + filename + EXTENSION);
		
		if (!file.exists()) {
			new File(file.getParent()).mkdirs();
			
			file.setExecutable(true);
			file.setReadable(true);
			file.setWritable(true);
		}
		
		try {
			ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(file));
			
			Object o = inStream.readObject();
			
			inStream.close();
			
			if (o instanceof Save) {
				try {
					return (Save<T>) o;
				} catch (ClassCastException ccex) {
					return createEmptySave(plugin, filename);
				}
			} else {
				return createEmptySave(plugin, filename);
			}
		} catch (IOException ioex) {
			file.delete();
		} catch (ClassNotFoundException cnfex) {
			file.delete();
		}
		
		return createEmptySave(plugin, filename);
	}
	
	/**
	 * Saves the passed {@link Save}.
	 * 
	 * @param save the Save that will be stored
	 */
	public static final <T extends Serializable> void save(JavaPlugin plugin, Save<T> save) {
		String path = "plugins" + File.separator + plugin.getDescription().getName() + File.separator;
		
		if (save == null)
			throw new NullPointerException();
		
		try {
			ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(path + save.getFileName() + EXTENSION));
			
			outStream.writeObject(save);
			
			outStream.flush();
			outStream.close();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}
	
	///////////////////////////////////
	/**
	 * Creates a new, empty save and stores it using the save method.
	 * This is called by the load method if there is need for a new, empty save.
	 * So if there is definitely an empty save needed, using this method is recommended as it is more resource-saving.
	 * 
	 * @param plugin the plugin this Save is assigned to
	 * @param filename the name of the file
	 * @return a new, empty save
	 */
	@SuppressWarnings("deprecation")
	public static final <T extends Serializable> Save<T> createEmptySave(JavaPlugin plugin, String filename) {
		Save<T> newSave = new Save<T>(filename);
		save(plugin, newSave);
		return newSave;
	}
}