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

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A <b>Save</b> represents a collection of data, that works similar to the {@link java.util.HashMap}, but with fixed types, optimized for storage in files.
 * There may be an infinite amount of entries in each Save. Each entry has a String as a key, and every key may be assigned a value.
 * The type of that value, the stored data, can be freely chosen. Note that the type and all its members <i>must</i> implement {@link java.io.Serializable}.<br>
 * <br>
 * For example, if you have the following code...<br>
 * <code>
 * SaveManager saveManager = new SaveManager(my_plugin);<br>
 * Save&lt;Integer&gt; save = saveManager.load("my_save");<br>
 * </code>
 * then <b>save</b> will be using Integer as a data type.<br>
 * <br>
 * See {@link http://www.kierrow.net/projects/io-toolkit} for further information.<br>
 * <br>
 * Also note that this class does not have a public constructor. That is because every Save must be constructed using the SaveManager's load method.
 * 
 * @author Tom Schoepe (Kierrow)
 * @version 1
 * 
 * @param <T> the type of data stored in this save (Note: the type and all its members <i>must</i> implement {@link java.io.Serializable} in order for the {@link SaveManager} to function properly.
 * 
 * @see SaveManager
 */
public final class Save<T extends Serializable> implements Serializable {
	///////////////////////////////////
	private static final long serialVersionUID = 1L;
	
	///////////////////////////////////
	private final String filename;
	
	///////////////////////////////////
	private ArrayList<SaveSet> sets;
	
	///////////////////////////////////
	/**
	 * Constructs a new Save.
	 * It is recommended to use the {@link SaveManager}s methods to create Saves.<br>
	 * <br>
	 * <font color="red">
	 * <b>Only</b> use this constructor yourself when you do not plan on storing this Save in a file, but only want to use it as a replacement for {@link java.util.HashMap}, which is not recommended.
	 * </font>
	 * 
	 * @param filename
	 * @deprecated
	 */
	@Deprecated
	public Save(String filename) {
		this.filename = filename;
		
		sets = new ArrayList<SaveSet>();
	}
	
	///////////////////////////////////
	/**
	 * Assigns a value to a (String) key. Note here that the key is <b><i>not</i> case-sensitive</b>.
	 * 
	 * @param key the key
	 * @param value the value
	 */
	public void set(String key, T value) {
		key = key.toLowerCase();
		
		for (int i = 0; i < sets.size(); i++) {
			if (sets.get(i).getKey().equalsIgnoreCase(key)) {
				SaveSet set = sets.get(i);
				set.setValue(value);
				
				sets.set(i, set);
				
				return;
			}
		}
		
		sets.add(new SaveSet(key, value));
	}
	
	/**
	 * Removes an entry entirely from the Save.
	 * If the representative entry does not exist, the request will simply be ignored.
	 * 
	 * @param key the key of the entry to remove.
	 */
	public void remove(String key) {
		key = key.toLowerCase();
		
		for (int i = 0; i < sets.size(); i++) {
			if (sets.get(i).getKey().equalsIgnoreCase(key)) {
				sets.remove(i);
			}
		}
	}
	
	/**
	 * Returns the value assigned to the key, if an entry, that uses that key, exists.
	 * Otherwise <b>null</b> will be returned.
	 * 
	 * @param key the key to get the value from
	 * @return the value 
	 */
	public T get(String key) {
		key = key.toLowerCase();
		
		for (int i = 0; i < sets.size(); i++) {
			if (sets.get(i).getKey().equalsIgnoreCase(key)) {
				return sets.get(i).getValue();
			}
		}
		
		return null;
	}
	
	/**
	 * Checks whether or not an entry with the passed key exists in this Save.
	 * 
	 * @param key the key to the entry
	 * @return <code>true</code> if there is an entry with the passed key, otherwise <code>false</code>
	 */
	public boolean contains(String key) {
		if (get(key) == null) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns all the values this Save contains.<br>
	 * <br>
	 * Note that the array that need to be passed MUST have the length returned by getSize(), or more, in order to have enough space for the entire content of the Save.
	 * 
	 * @param typearray an array of the type this Save stores
	 * @return all values
	 */
	public T[] getAllValues(T[] typearray) {
		for (int i = 0; (i < typearray.length) && (i < sets.size()); i++) {
			typearray[i] = sets.get(i).getValue();
		}
		
		return typearray;
	}
	
	/**
	 * Returns all the keys this Save contains.
	 * 
	 * @return all keys
	 */
	public String[] getAllKeys() {
		String[] result = new String[sets.size()];
		
		for (int i = 0; i < sets.size(); i++) {
			result[i] = sets.get(i).getKey();
		}
		
		return result;
	}
	
	/**
	 * Returns the amount of entries in this Save.
	 * This is important for the getAllValues() method any might as well have some other uses.
	 * 
	 * @return the amount of entries.
	 */
	public int getSize() {
		return sets.size();
	}
	
	///////////////////////////////////
	String getFileName() {
		return filename;
	}
	
	///////////////////////////////////
	private final class SaveSet implements Serializable {
		///////////////////////////////
		private static final long serialVersionUID = 1L;
		
		///////////////////////////////
		private String key;
		private T value;
		
		///////////////////////////////
		private SaveSet(String key, T value) {
			this.key = key;
			this.value = value;
		}
		
		///////////////////////////////
		private String getKey() {
			return key;
		}
		
		private T getValue() {
			return value;
		}
		
		private void setValue(T value) {
			this.value = value;
		}
	}
}