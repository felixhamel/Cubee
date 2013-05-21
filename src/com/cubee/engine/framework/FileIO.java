package com.cubee.engine.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.cubee.engine.framework.interfaces.FileIOInterface;
import com.google.common.base.Preconditions;

public class FileIO implements FileIOInterface 
{
	private Context context = null;
	private AssetManager assets = null;
	private String externalStoragePath = null;
	
	public FileIO(Context context)
	{
		// Validation
		Preconditions.checkNotNull(context, "Can't accept a null context");
		
		// Setup
		this.context = context;
		this.assets = context.getAssets();
		this.externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	}
	
	@Override
	public InputStream readFile(String file) throws IOException 
	{
		// Validation
		Preconditions.checkNotNull(file, "The file path can't be null");
		Preconditions.checkArgument(file.length() > 0, "The file path can't be empty");
		Preconditions.checkArgument(new File(this.externalStoragePath + file).exists(), 
				"Can't read a file that do not exists ("+file+")");

		// Open the file and returns the InputStream
		return assets.open(file);
	}

	@Override
	public InputStream readAsset(String file) throws IOException 
	{
		// Validation
		Preconditions.checkNotNull(file, "The file path can't be null");
		Preconditions.checkArgument(file.length() > 0, "The file path can't be empty");
		Preconditions.checkArgument(new File(this.externalStoragePath + file).exists(), 
				"Can't read a file that do not exists ("+file+")");
		
		// Open the file and returns the InputStream
		return new FileInputStream(this.externalStoragePath + file);
	}
	
	@Override
	public OutputStream writeFile(String file) throws IOException 
	{
		// Validation
		Preconditions.checkNotNull(file, "The file path can't be null");
		Preconditions.checkArgument(file.length() > 0, "The file path can't be empty");
		
		// Create the File and returns the OutputStream
		return new FileOutputStream(this.externalStoragePath + file);
	}

	@Override
	public SharedPreferences getSharedPref() 
	{
		return PreferenceManager.getDefaultSharedPreferences(this.context);
	}
}
