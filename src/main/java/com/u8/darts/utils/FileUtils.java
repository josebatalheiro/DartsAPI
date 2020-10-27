package com.u8.darts.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static String readFileFromClasspath(String filePath) {

        return readFileFromClasspath(FileUtils.class.getClassLoader(), filePath);
    }

    public static String readFileFromClasspath(ClassLoader classLoader, String filePath) {

        try (InputStream input = getFileInputStreamFromClasspath(classLoader, filePath)) {
            return streamToString(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream getFileInputStreamFromClasspath(String filePath) {

        return getFileInputStreamFromClasspath(FileUtils.class.getClassLoader(), filePath);
    }

    public static InputStream getFileInputStreamFromClasspath(ClassLoader classLoader, String filePath) {

        InputStream input = classLoader.getResourceAsStream(filePath);
        if (input == null) {
            input = classLoader.getResourceAsStream("/" + filePath);
            if (input == null) {
                throw new RuntimeException("File '" + filePath + "' not found in classpath.");
            }
        }
        return input;
    }

    public static void writeStreamToFile(InputStream data, File file) {

        try (OutputStream output = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = data.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not find file.", e);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file.", e);
        }
    }

    public static String streamToString(InputStream input) {

        try (Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
            char[] buffer = new char[1024];
            StringBuilder output = new StringBuilder();
            int read;
            while ((read = reader.read(buffer)) != -1) {
                output.append(buffer, 0, read);
            }
            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error reading input stream.", e);
        }
    }
}