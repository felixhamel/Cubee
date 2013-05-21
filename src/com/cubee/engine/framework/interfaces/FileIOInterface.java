package com.cubee.engine.framework.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.SharedPreferences;

public interface FileIOInterface {

	InputStream readFile(String file) throws IOException;
	OutputStream writeFile(String file) throws IOException;
	InputStream readAsset(String file) throws IOException;
	SharedPreferences getSharedPref();
}
