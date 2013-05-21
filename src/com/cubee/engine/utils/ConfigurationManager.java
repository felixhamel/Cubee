package com.cubee.engine.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.cubee.engine.AppStructure;
import com.google.common.base.Preconditions;

public class ConfigurationManager 
{
	private static ConfigurationManager instance = null;
	private Map<String, String> values = new HashMap<String, String>();
	private String configFilePath = "";
	
	public ConfigurationManager(String configFilePath)
	{
		// Validation
		Preconditions.checkNotNull(configFilePath);
		Preconditions.checkArgument(configFilePath.length() > 0);
		
		this.configFilePath = configFilePath;
		
		this.parseConfigurationFile();
	}
	
	public static ConfigurationManager getInstance()
	{
		if(instance == null)
		{
			System.out.println("config file: " + AppStructure.CONFIG_FILE);
			instance = new ConfigurationManager(AppStructure.CONFIG_FILE);
		}
		else
		{
			System.out.println("instance already created");
		}
		return instance;
	}
	
	public void saveConfig()
	{
		System.out.println("started writing config file.");
		FileWriter fWriter = null;
		BufferedWriter writer = null; 
		try {
			  System.out.println("opening file");
			  File configFile = new File(this.configFilePath);
			  if(!configFile.exists())
			  {
				  configFile.createNewFile();
				  System.out.println("creating new file");
			  }
			  fWriter = new FileWriter(this.configFilePath);
			  writer = new BufferedWriter(fWriter);
			  System.out.println("opened file");
			  //WRITES 1 LINE TO FILE AND CHANGES LINE
			  for(Entry<String, String> entries : values.entrySet())
			  {
				  writer.write(entries.getKey() + ":" + entries.getValue());
				  System.out.println("wrote: " + entries.getKey() + ":" + entries.getValue());
				  writer.newLine();
			  }
		   writer.close();
		} catch (Exception e) {
			System.out.println("can't write to file " + this.configFilePath);
		}
	}
	
	private void parseConfigurationFile()
	{
		// Check if the config file exists
		File configFile = new File(this.configFilePath);
		System.out.println(this.configFilePath);
		if(configFile.exists())
		{
			System.out.println("CONFIG FILE: " + configFile.getAbsolutePath());
			try
			{
				FileInputStream fileInStream = new FileInputStream(configFile);
				DataInputStream dataInStream = new DataInputStream(fileInStream);
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInStream));
				String readedLine = "";

				while ((readedLine = bufferedReader.readLine()) != null)   
				{
					this.addValue(readedLine);
				}
				
				bufferedReader.close();
				dataInStream.close();
				fileInStream.close();
			}
			catch (Exception e)
			{
				System.err.println("Error: " + e.getMessage());
			}
		}
		else
		{
			System.out.println("CONFIG FILE NOT FOUND: " + configFile.getAbsolutePath());
			this.setupDefaultSettings();
		}
	}
	
	private void addValue(String line)
	{ 
		if(!line.contains("#") || line.length() < 4) // Comments
		{
			String key = line.substring(0, line.indexOf(":"));
			String value = line.substring(line.indexOf(":")+1, line.lastIndexOf(";"));
			
			this.values.put(key, value);
		}
	}
	
	private void setupDefaultSettings()
	{
		values.put("ACTIVATE_HIGHSCORES", "true");
		values.put("ACTIVATE_ACCELEROMETER", "true");
		values.put("ACTIVATE_DIRECTIONAL_TOUCHPAD","false");
		values.put("ACTIVATE_SOUND", "false");
		
	}
	
	public boolean getValue(String key)
	{
		// Validation
		Preconditions.checkNotNull(key, "The key can't be null");
		Preconditions.checkArgument(key.length() > 0, "The key must not be empty (" + key + ")");
		
		System.out.println(key + " value :" + this.values.get(key) == "true");
		
		if(this.values.containsKey(key))
		{
			if(this.values.get(key) == "true")
			{
				System.out.println(key + " value :" + this.values.get(key));
				return true;
			}
		}
		return false;
	}
	
	public void setValue(String key, boolean value)
	{
		String newValue;
		if(value)
		{
			newValue = "true";
			System.out.println(key + " set to true");
		}
		else
		{
			newValue= "false";
			System.out.println(key + " set to false");
		}
		this.values.put(key, newValue);
	}
}
