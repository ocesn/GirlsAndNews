package com.chinamade.hall.grilsandnews.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class FileUtils {

	private static final String TAG = "SZU_FileUtils";

	/**
	 * 检测SD卡是否存在
	 * 
	 * @return
	 */
	public static boolean isSdcardExist() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}


    /**
     * @Description 获取sdcard可用空间的大小
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long getSDFreeSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        long blockSize = sf.getBlockSize();
        long freeBlocks = sf.getAvailableBlocks();
        // return freeBlocks * blockSize; //单位Byte
        // return (freeBlocks * blockSize)/1024; //单位KB
        return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
    }

    /**
     * @Description 获取sdcard容量
     * @return
     */
    @SuppressWarnings({
            "deprecation", "unused"
    })
    private static long getSDAllSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        long blockSize = sf.getBlockSize();
        long allBlocks = sf.getBlockCount();
        // 返回SD卡大小
        // return allBlocks * blockSize; //单位Byte
        // return (allBlocks * blockSize)/1024; //单位KB
        return (allBlocks * blockSize) / 1024 / 1024; // 单位MB
    }

	/**
	 * 创建根目录
	 * 
	 * @param path
	 *            目录路径
	 */
	public static void createDirFile(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	/**
	 * 创建文件
	 * 
	 * @param path
	 *            文件路径
	 * @return 创建的文件
	 */
	public static File createNewFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				return null;
			}
		}
		return file;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *            文件夹的路径
	 */
	public static void delFolder(String folderPath) {
		delAllFile(folderPath);
		String filePath = folderPath;
		filePath = filePath.toString();
		File myFilePath = new File(filePath);
		myFilePath.delete();
	}

	/**
	 * 删除路径下所有文件
	 * 
	 * @param path
	 *            文件的路径
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);
				delFolder(path + "/" + tempList[i]);
			}
		}
	}

	/**
	 * 获取文件的Uri
	 * 
	 * @param path
	 *            文件的路径
	 * @return
	 */
	public static Uri getUriFromFile(String path) {
		File file = new File(path);
		return Uri.fromFile(file);
	}

	/**
	 * 换算文件大小
	 * 
	 * @param size
	 * @return
	 */
	public static String formatFileSize(long size) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "未知大小";
		if (size < 1024) {
			fileSizeString = df.format((double) size) + "B";
		} else if (size < 1048576) {
			fileSizeString = df.format((double) size / 1024) + "K";
		} else if (size < 1073741824) {
			fileSizeString = df.format((double) size / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) size / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 通过路径获得文件名字
	 * 
	 * @param path
	 * @return
	 */
	public static String getNameByPath(String path)
	{
		return path.substring(path.lastIndexOf(File.separator) + 1);
	}

	/**
	 * 通过判断文件是否存在
	 * 
	 * @param path
	 * @return
	 */

	public static boolean isFileExists(String path)
	{
		try
		{
			File file = new File(path);
			if (!file.exists())
			{
				return false;
			}

		} catch (Exception e)
		{
			// TODO: handle exception
			Log.d(TAG, "判断文件");
			return false;
		}
		return true;
	}

	/**
	 * 获得SD卡路径
	 * 
	 * @Description 如没有SD返回手机内部存储路径
	 * @param
	 * @return String
	 */
	public static String getSDPath()
	{
		File sdDir = null;
		// 判断sd卡是否存在
		boolean sdCardExist = isSdcardExist(); 
		if (sdCardExist)
		{
			sdDir = Environment.getExternalStorageDirectory();// 获取SD根目录
			return sdDir.toString();
		}
		else {
			return Environment.getDataDirectory().getAbsolutePath();//获取内部存储根目录
		}
	}

	/**
	 * 把指定名称的内容存在指定路径
	 * 
	 */
	public static final void saveToSDCard(String directory, String filename, String filecontent) {
		try {
			
			File fileDirectory = new File(directory);
			if (!fileDirectory.exists()) {
				fileDirectory.mkdirs();
			}
			File file = new File(directory, filename);
			FileOutputStream outStream = new FileOutputStream(file);
			outStream.write(filecontent.getBytes("UTF8"));
			outStream.close();
		} catch (Exception e) {
			
		}
	}

	/**
	 * 把数据流存储到指定的路径
	 * 
	 */
	public static final void saveToSDCard(String path, String filename, InputStream inputStream) {
		try {
			File directory = new File(path);
			if (!directory.exists()) {
				directory.mkdir();
			}
			File file = new File(filename);
			
			inputStream2File(inputStream, file);
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 读取指定路径名称的文件
	 * 
	 */
	public static final String readSDFile(String directory, String fileName) {

		try {
			String result = null;
			File file = new File(directory, fileName);
	        FileInputStream fileInputStream = new FileInputStream(file);
	        int length = fileInputStream.available();
	        byte [] buffer = new byte[length];
	        fileInputStream.read(buffer);
	        //result = EncodingUtils.getString(buffer, "UTF8");
			result = new String(buffer,"utf-8");
	        fileInputStream.close();
	        return result;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static final void deleteSDFile(String directory, String fileName) {
		File file = new File(directory, fileName);
		if (file.exists()) {
			file.delete();
		}
	}
	
	/**
	 * 删除指定路径文件
	 * 
	 */
	public static final void deleteDirectory(File file) {
		if (file.isFile()) {  
			file.delete();  
			return;  
		}   
		if (file.isDirectory()) {  
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {  
				file.delete();  
				return;  
			}  
	    	     
			for (int i = 0; i < childFiles.length; i++) {  
				deleteDirectory(childFiles[i]);  
            }  
            file.delete();  
        }  
	}

	/**
	 * 把文件读取到数据流中
	 * 
	 * @param ins   数据流
	 * @param file   文件
	 */
	public static void inputStream2File(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
			    os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**  
     * 文件转化为字节数组  
     * @param 
     */   
    public static byte[] file2Bytes(File f) {
        if (f == null) {   
            return null;   
        }   
        try {
			FileInputStream stream = new FileInputStream(f);
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = stream.read()) != -1) {
				bytestream.write(ch);
			}
			byte bytedata[] = bytestream.toByteArray();
			stream.close();
			bytestream.close();
			return bytedata;
        } catch (IOException e) {
        }   
        return null;   
    }   
  
    /** 
     * 把字节数组保存为一个文件  
     * @param
     */   
    public static File bytes2File(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {   
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);   
        } catch (Exception e) {
            e.printStackTrace();   
        } finally {   
            if (stream != null) {   
                try {   
                    stream.close();   
                } catch (IOException e1) {
                    e1.printStackTrace();   
                }   
            }   
        }   
        return file;   
    }   
    
    /** 
     * 将字符串写入指定文件(当指定的父路径中文件夹不存在时，会最大限度去创建，以保证保存成功！) 
     * 
     * @param res         原字符串 
     * @param filePath 文件路径 
     * @return 成功标记 
     */ 
    public static boolean string2File(String res, String filePath) {
            boolean flag = true; 
            BufferedReader bufferedReader = null;
            BufferedWriter bufferedWriter = null;
            try { 
                    File distFile = new File(filePath);
                    if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs(); 
                    bufferedReader = new BufferedReader(new StringReader(res));
                    bufferedWriter = new BufferedWriter(new FileWriter(distFile));
                    char buf[] = new char[1024];         //字符缓冲区 
                    int len; 
                    while ((len = bufferedReader.read(buf)) != -1) { 
                            bufferedWriter.write(buf, 0, len); 
                    } 
                    bufferedWriter.flush(); 
                    bufferedReader.close(); 
                    bufferedWriter.close(); 
            } catch (IOException e) {
                    e.printStackTrace(); 
                    flag = false; 
                    return flag; 
            } finally { 
                    if (bufferedReader != null) { 
                            try { 
                                    bufferedReader.close(); 
                            } catch (IOException e) {
                                    e.printStackTrace(); 
                            } 
                    } 
            } 
            return flag; 
    }
    
    /** 
     * 文本文件转换为指定编码的字符串 
     * 
     * @param file         文本文件 
     * @param encoding 编码类型 
     * @return 转换后的字符串 
     * @throws IOException
     */ 
    public static String file2String(File file, String encoding) {
            InputStreamReader reader = null;
            StringWriter writer = new StringWriter();
            try { 
                    if (encoding == null || "".equals(encoding.trim())) { 
                            reader = new InputStreamReader(new FileInputStream(file));
                    } else { 
                            reader = new InputStreamReader(new FileInputStream(file), encoding);
                    } 
                    //将输入流写入输出流 
                    char[] buffer = new char[1024]; 
                    int n = 0; 
                    while (-1 != (n = reader.read(buffer))) { 
                            writer.write(buffer, 0, n); 
                    } 
            } catch (Exception e) {
                    e.printStackTrace(); 
                    return null; 
            } finally { 
                    if (reader != null) 
                            try { 
                                    reader.close(); 
                            } catch (IOException e) {
                                    e.printStackTrace(); 
                            } 
            } 
            //返回转换结果 
            if (writer != null) 
                    return writer.toString(); 
            else return null; 
    }
    
    /**  
     * 从字节数组获取对象  
     * @param
     */   
    public static Object getObjectFromBytes(byte[] objBytes) throws Exception {
        if (objBytes == null || objBytes.length == 0) {   
            return null;   
        }   
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        return oi.readObject();   
    }   
  
    /** 
     * 从对象获取一个字节数组  
     * @param
     */   
    public static byte[] getBytesFromObject(Serializable obj) throws Exception {
        if (obj == null) {   
            return null;   
        }   
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);   
        return bo.toByteArray();   
    }   
    
    /**
    * 将文件转成base64 字符串
    * @author lk
    * @param path 文件路径
    * @return
    * @throws Exception
    * @since JDK 1.6
    */
    public static String encodeBase64File(String path)  {
    	try {
    		File file = new File(path);
    	    FileInputStream inputFile = new FileInputStream(file);
    	    byte[] buffer = new byte[(int)file.length()];
    	    inputFile.read(buffer);
    	            inputFile.close();
    	            return Base64.encodeToString(buffer, Base64.DEFAULT);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
    * 将base64字符解码保存文件
    * @param base64Code 编码后的字串
    * @param savePath  文件保存路径
    * @throws Exception
    * @since JDK 1.6
    */
    public static void decoderBase64File(String base64Code, String savePath) {
    	try {
    		 byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
    		    FileOutputStream out = new FileOutputStream(savePath);
    		    out.write(buffer);
    		    out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			savePath = null;
		}
    }

	/**
	 * 将文件下载到SD卡缓存起来。
	 * 
	 * @param fileUrl   文件的URL地址。
	 * @param filePath   文件的下载地址。
	 */
	public static boolean downloadFile(String fileUrl, String filePath) {
		boolean result = false;
		HttpURLConnection con = null;
		InputStream fis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		File file = null;
		try {
			URL url = new URL(fileUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5 * 1000);
			con.setReadTimeout(15 * 1000);
			con.setDoInput(true);
			//con.setDoOutput(true);
			fis = con.getInputStream();
			bis = new BufferedInputStream(fis);
			file = new File(filePath);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			byte[] b = new byte[1024];
			int length;
			while ((length = bis.read(b)) != -1) {
				bos.write(b, 0, length);
				bos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
				if (con != null) {
					con.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (file != null) {
			result = true;
		}
		return result;
	}


	/**
	 * 读取Assets文件夹下的文件。
	 *
	 * @param fileName 文件名。
	 */
	public static String getStringFromAssets(Context context, String fileName) {
		StringBuffer stringBuffer;

		AssetManager assetManager = context.getAssets();
		try {
			InputStream is = assetManager.open(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			stringBuffer = new StringBuffer();
			String str = null;
			while((str = br.readLine()) != null){
				stringBuffer.append(str);
			}

			return stringBuffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
