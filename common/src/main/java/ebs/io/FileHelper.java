package ebs.io;

import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.common.io.Resources;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Aleksey Dubov
 * Date: 14/12/29
 * Time: 21:29
 * Copyright (c) 2014
 */
public class FileHelper {
	public static void saveURLtoFile(String url, String file) throws IOException {
		Resources.asByteSource(new URL(url)).copyTo(Files.asByteSink(new File(file), FileWriteMode.APPEND));
	}
}
