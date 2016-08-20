/*#
	        # Copyright 2016 Xoriant Corporation.
	        #
	        # Licensed under the Apache License, Version 2.0 (the "License");
	        # you may not use this file except in compliance with the License.
	        # You may obtain a copy of the License at
	        #
	        #     http://www.apache.org/licenses/LICENSE-2.0

	        #
	        # Unless required by applicable law or agreed to in writing, software
	        # distributed under the License is distributed on an "AS IS" BASIS,
	        # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	        # See the License for the specific language governing permissions and
	        # limitations under the License.
	        #
*/

package com.xor.xnaudioplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;


public class SongsManager {
	// SDCard Path
	final String MEDIA_PATH = new String( Environment.getDataDirectory() +"/");
	private ArrayList<HashMap<String, String >> songsList = new ArrayList<HashMap<String, String>>();
	Context context;
	
	// Constructor
	public SongsManager(Context context){
		this.context=context;
	}
	
	/**
	 * Function to read all mp3 files from sdcard
	 * and store the details in ArrayList
	 * */
	public ArrayList<HashMap<String, String>> getPlayList(){

		ContentResolver musicResolver = context.getContentResolver();
		Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
		//iterate over results if valid
		if(musicCursor!=null && musicCursor.moveToFirst()){


			//get columns
			int titleColumn = musicCursor.getColumnIndex
					(android.provider.MediaStore.Audio.Media.TITLE);
			int idColumn = musicCursor.getColumnIndex
					(android.provider.MediaStore.Audio.Media._ID);
			int artistColumn = musicCursor.getColumnIndex
					(android.provider.MediaStore.Audio.Media.ARTIST);
			String artistname = musicCursor.getString(musicCursor
					.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.ARTIST));
			//add songs to list
			do {
				HashMap<String, String> song = new HashMap<String, String>();
				long thisId = musicCursor.getLong(idColumn);
				String thisTitle = musicCursor.getString(titleColumn);
				String thisArtist = musicCursor.getString(artistColumn);
				//songList.add(new Song(thisId, thisTitle, thisArtist));
				song.put("songTitle", ""+thisTitle);
				song.put("songPath", ""+thisId);
				song.put("songArtist", ""+artistname);
				songsList.add(song);
			}
			while (musicCursor.moveToNext());
		}


		// return songs list array
		return songsList;
	}
	
	/**
	 * Class to filter files which are having .mp3 extension
	 * */
	class FileExtensionFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return (name.endsWith(".mp3") || name.endsWith(".MP3"));
		}
	}
}
