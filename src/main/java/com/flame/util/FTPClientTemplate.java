package com.flame.util;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

/**
 * FTP客户端
 * 
 * @author summersun_ym
 * @version $Id: FTPClientTemplate.java 2010-11-22 上午12:54:47 $
 */
public class FTPClientTemplate {
    //---------------------------------------------------------------------
    // Instance data
    //---------------------------------------------------------------------
    /** logger */
    protected final Logger         log                  = Logger.getLogger(getClass());
    private ThreadLocal<FTPClient> ftpClientThreadLocal = new ThreadLocal<FTPClient>();

    private String                 host;
    private int                    port;
    private String                 username;
    private String                 password;

    private boolean                binaryTransfer       = true;
    private boolean                passiveMode          = true;
    private String                 encoding             = "GBK";
    private int                    clientTimeout        = 1000 * 30;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBinaryTransfer() {
        return binaryTransfer;
    }

    public void setBinaryTransfer(boolean binaryTransfer) {
        this.binaryTransfer = binaryTransfer;
    }

    public boolean isPassiveMode() {
        return passiveMode;
    }

    public void setPassiveMode(boolean passiveMode) {
        this.passiveMode = passiveMode;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public int getClientTimeout() {
        return clientTimeout;
    }

    public void setClientTimeout(int clientTimeout) {
        this.clientTimeout = clientTimeout;
    }

    //---------------------------------------------------------------------
    // private method
    //---------------------------------------------------------------------
    /**
     * 返回一个FTPClient实例
     * 
     * @throws RuntimeException
     * @throws IOException 
     */
    private FTPClient getFTPClient() throws RuntimeException, IOException {
        if (ftpClientThreadLocal.get() != null && ftpClientThreadLocal.get().isConnected()) {
            return ftpClientThreadLocal.get();
        } else {
            FTPClient ftpClient = new FTPClient(); //构造一个FtpClient实例
            ftpClient.setControlEncoding(encoding); //设置字符集
            
            connect(ftpClient); //连接到ftp服务器
    
            //设置为passive模式
            if (passiveMode) {
                ftpClient.enterLocalPassiveMode();
            }
            setFileType(ftpClient); //设置文件传输类型
    
            try {
                ftpClient.setSoTimeout(clientTimeout);
            } catch (SocketException e) {
                throw new RuntimeException("Set timeout error.", e);
            }
            ftpClientThreadLocal.set(ftpClient);
            return ftpClient;
        }
    }

    /**
     * 设置文件传输类型
     * 
     * @throws RuntimeException
     * @throws IOException
     */
    private void setFileType(FTPClient ftpClient) throws RuntimeException {
        try {
        	ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//            if (binaryTransfer) {
//            } else {
//                ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);
//            }
        } catch (IOException e) {
            throw new RuntimeException("Could not to set file type.", e);
        }
    }

    /**
     * 连接到ftp服务器
     * 
     * @param ftpClient
     * @return 连接成功返回true，否则返回false
     * @throws RuntimeException
     */
    private boolean connect(FTPClient ftpClient) throws RuntimeException {
        try {
            ftpClient.connect(host, port);

            // 连接后检测返回码来校验连接是否成功
            int reply = ftpClient.getReplyCode();

            if (FTPReply.isPositiveCompletion(reply)) {
                //登陆到ftp服务器
                if (ftpClient.login(username, password)) {
                    setFileType(ftpClient);
                    return true;
                }
            } else {
                ftpClient.disconnect();
                throw new RuntimeException("FTP server refused connection.");
            }
        } catch (IOException e) {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect(); //断开连接
                } catch (IOException e1) {
                    throw new RuntimeException("Could not disconnect from server.", e1);
                }

            }
            throw new RuntimeException("Could not connect to server.", e);
        }
        return false;
    }


    //---------------------------------------------------------------------
    // public method
    //---------------------------------------------------------------------
    /**
     * 断开ftp连接
     * 
     * @throws RuntimeException
     */
    public void disconnect() throws RuntimeException {
        try {
            FTPClient ftpClient = getFTPClient();
            ftpClient.logout();
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
                ftpClient = null;
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not disconnect from server.", e);
        }
    }
    
    public boolean rmdir(String pathname) throws RuntimeException {
        return rmdir(pathname, null);
    }
    
    /**
     * 在ftp服务器端删除目录
     * 
     * 该方法执行完后将自动关闭当前连接
     * 
     * @param pathname
     * @return
     * @throws RuntimeException
     */
    public boolean rmdir(String pathname, String workingDirectory) throws RuntimeException {
        return rmdir(pathname, workingDirectory, true);
    }
    
    /**
     * 在ftp服务器端删除目录
     * 
     * @param pathname
     * @param autoClose 是否自动关闭当前连接
     * @return
     * @throws RuntimeException
     */
    public boolean rmdir(String pathname, String workingDirectory, boolean autoClose) throws RuntimeException {
        try {
            getFTPClient().changeWorkingDirectory(workingDirectory);
            return getFTPClient().removeDirectory(pathname);
        } catch (IOException e) {
            throw new RuntimeException("Could not rmdir.", e);
        } finally {
            if (autoClose) {
                disconnect(); //断开连接
            }
        }
    }
    
    public boolean mkdir(String pathname) throws RuntimeException {
        return mkdir(pathname, null);
    }
    
    /**
     * 在ftp服务器端创建目录（不支持一次创建多级目录）
     * 
     * 该方法执行完后将自动关闭当前连接
     * 
     * @param pathname
     * @return
     * @throws RuntimeException
     */
    public boolean mkdir(String pathname, String workingDirectory) throws RuntimeException {
        return mkdir(pathname, workingDirectory, true);
    }
    
    /**
     * 在ftp服务器端创建目录（不支持一次创建多级目录）
     * 
     * @param pathname
     * @param autoClose 是否自动关闭当前连接
     * @return
     * @throws RuntimeException
     */
    public boolean mkdir(String pathname, String workingDirectory, boolean autoClose) throws RuntimeException {
        try {
            getFTPClient().changeWorkingDirectory(workingDirectory);
            return getFTPClient().makeDirectory(pathname);
        } catch (IOException e) {
            throw new RuntimeException("Could not mkdir.", e);
        } finally {
            if (autoClose) {
                disconnect(); //断开连接
            }
        }
    }

    /**
     * 上传一个本地文件到远程指定文件
     * 
     * @param remoteAbsoluteFile 远程文件名(包括完整路径)
     * @param localAbsoluteFile 本地文件名(包括完整路径)
     * @return 成功时，返回true，失败返回false
     * @throws RuntimeException
     */
    public boolean put(String remoteAbsoluteFile,InputStream input, String localAbsoluteFile) throws RuntimeException {
        return put(remoteAbsoluteFile,input, localAbsoluteFile, true);
    }

    /**
     * 上传一个本地文件到远程指定文件
     * 
     * @param remoteAbsoluteFile 远程文件名(包括完整路径)
     * @param localAbsoluteFile 本地文件名(包括完整路径)
     * @param autoClose 是否自动关闭当前连接
     * @return 成功时，返回true，失败返回false
     * @throws RuntimeException
     */
    public boolean put(String remoteAbsoluteFile, InputStream input,String localAbsoluteFile, boolean autoClose) throws RuntimeException {
        //InputStream input = null;
        try {
            // 处理传输
            //input = new FileInputStream(file);
            getFTPClient().storeFile(remoteAbsoluteFile, input);
            log.debug("put " + localAbsoluteFile);
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("local file not found.", e);
        } catch (IOException e) {
            throw new RuntimeException("Could not put file to server.", e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (Exception e) {
                throw new RuntimeException("Couldn't close FileInputStream.", e);
            }
            if (autoClose) {
                disconnect(); //断开连接
            }
        }
    }

    /**
     * 下载一个远程文件到本地的指定文件
     * 
     * @param remoteAbsoluteFile 远程文件名(包括完整路径)
     * @param localAbsoluteFile 本地文件名(包括完整路径)
     * @return 成功时，返回true，失败返回false
     * @throws RuntimeException
     */
    public boolean get(String remoteAbsoluteFile, String localAbsoluteFile) throws RuntimeException {
        return get(remoteAbsoluteFile, localAbsoluteFile, true);
    }

    /**
     * 下载一个远程文件到本地的指定文件
     * 
     * @param remoteAbsoluteFile 远程文件名(包括完整路径)
     * @param localAbsoluteFile 本地文件名(包括完整路径)
     * @param autoClose 是否自动关闭当前连接
     * 
     * @return 成功时，返回true，失败返回false
     * @throws RuntimeException
     */
    public boolean get(String remoteAbsoluteFile, String localAbsoluteFile, boolean autoClose) throws RuntimeException {
        OutputStream output = null;
        try {
            output = new FileOutputStream(localAbsoluteFile);
            return get(remoteAbsoluteFile, output, autoClose);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("local file not found.", e);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("Couldn't close FileOutputStream.", e);
            }
        }
    }

    /**
     * 下载一个远程文件到指定的流 处理完后记得关闭流
     * 
     * @param remoteAbsoluteFile
     * @param output
     * @return
     * @throws RuntimeException
     */
    public boolean get(String remoteAbsoluteFile, OutputStream output) throws RuntimeException {
        return get(remoteAbsoluteFile, output, true);
    }

    /**
     * 下载一个远程文件到指定的流 处理完后记得关闭流
     * 
     * @param remoteAbsoluteFile
     * @param output
     * @param delFile
     * @return
     * @throws RuntimeException
     */
    public boolean get(String remoteAbsoluteFile, OutputStream output, boolean autoClose) throws RuntimeException {
        try {
            FTPClient ftpClient = getFTPClient();
            // 处理传输
            return ftpClient.retrieveFile(remoteAbsoluteFile, output);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't get file from server.", e);
        } finally {
            if (autoClose) {
                disconnect(); //关闭链接
            }
        }
    }

    /**
     * 从ftp服务器上删除一个文件
     * 该方法将自动关闭当前连接
     * 
     * @param delFile
     * @return
     * @throws RuntimeException
     */
    public boolean delete(String delFile) throws RuntimeException {
        return delete(delFile, true);
    }
    
    /**
     * 从ftp服务器上删除一个文件
     * 
     * @param delFile
     * @param autoClose 是否自动关闭当前连接
     * 
     * @return
     * @throws RuntimeException
     */
    public boolean delete(String delFile, boolean autoClose) throws RuntimeException {
        try {
            getFTPClient().deleteFile(delFile);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Couldn't delete file from server.", e);
        } finally {
            if (autoClose) {
                disconnect(); //关闭链接
            }
        }
    }
    
    /**
     * 批量删除
     * 该方法将自动关闭当前连接
     * 
     * @param delFiles
     * @return
     * @throws RuntimeException
     */
    public boolean delete(String[] delFiles) throws RuntimeException {
        return delete(delFiles, true);
    }

    /**
     * 批量删除
     * 
     * @param delFiles
     * @param autoClose 是否自动关闭当前连接
     * 
     * @return
     * @throws RuntimeException
     */
    public boolean delete(String[] delFiles, boolean autoClose) throws RuntimeException {
        try {
            FTPClient ftpClient = getFTPClient();
            for (String s : delFiles) {
                ftpClient.deleteFile(s);
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Couldn't delete file from server.", e);
        } finally {
            if (autoClose) {
                disconnect(); //关闭链接
            }
        }
    }

    /**
     * 列出远程默认目录下所有的文件
     * 
     * @return 远程默认目录下所有文件名的列表，目录不存在或者目录下没有文件时返回0长度的数组
     * @throws RuntimeException
     */
    public String[] listNames() throws RuntimeException {
        return listNames(null, true);
    }
    
    public String[] listNames(boolean autoClose) throws RuntimeException {
        return listNames(null, autoClose);
    }

    /**
     * 列出远程目录下所有的文件
     * 
     * @param remotePath 远程目录名
     * @param autoClose 是否自动关闭当前连接
     * 
     * @return 远程目录下所有文件名的列表，目录不存在或者目录下没有文件时返回0长度的数组
     * @throws RuntimeException
     */
    public String[] listNames(String remotePath, boolean autoClose) throws RuntimeException {
        try {
            String[] listNames = getFTPClient().listNames(remotePath);
            return listNames;
        } catch (IOException e) {
            throw new RuntimeException("列出远程目录下所有的文件时出现异常", e);
        } finally {
            if (autoClose) {
                disconnect(); //关闭链接
            }
        }
    }

    public static void main(String[] args) throws RuntimeException, InterruptedException {
//    	ExecutorService executorService=Executors.newFixedThreadPool(2);
//
//		Future<String> future = executorService.submit(new Callable<String>() {
// 
//            /**
//             * 回调方法
//             */
//            @Override
//            public String call() throws Exception {
// 
//            	FTPClientTemplate ftp = new FTPClientTemplate();
//				ftp.setHost("192.168.0.151");
//				ftp.setPort(21);
//				ftp.setUsername("ftpuser");
//				ftp.setPassword("admin");
//				ftp.setBinaryTransfer(false);
//				ftp.setPassiveMode(false);
//				ftp.setEncoding("GBK");
//				File file = new File("F:/a.xls");
//				FileInputStream in = new FileInputStream(file);
//				boolean ret = ftp.put("/upload/a.xls", in,"F:/apache-maven-3.1.0.rar");
//                return "";
//            }
//        });
//		
			
//			Future<String> future2 = executorService.submit(new Callable<String>() {
//				 
//	            /**
//	             * 回调方法
//	             */
//	            @Override
//	            public String call() throws Exception {
//	 
//	            	FTPClientTemplate ftp = new FTPClientTemplate();
//					ftp.setHost("192.168.0.151");
//					ftp.setPort(21);
//					ftp.setUsername("ftpuser");
//					ftp.setPassword("admin");
//					ftp.setBinaryTransfer(false);
//					ftp.setPassiveMode(false);
//					ftp.setEncoding("utf-8");
//					File file = new File("F:/jdk-7u51-windows-x64.exe");
//					//boolean ret = ftp.put("/upload/jdk-7u51-windows-x64.exe",file, "F:/jdk-7u51-windows-x64.exe");
//	                return "";
//	            }
//	        });
//			
//				try {
//					System.out.println(future2.get());
//					System.out.println(future.get());
//
//				} catch (ExecutionException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
		
//		executorService.submit(new Runnable(){
//			@Override
//			public void run() {
//				
//				FTPClientTemplate ftp = new FTPClientTemplate();
//				ftp.setHost("192.168.0.151");
//				ftp.setPort(21);
//				ftp.setUsername("ftpuser");
//				ftp.setPassword("admin");
//				ftp.setBinaryTransfer(false);
//				ftp.setPassiveMode(false);
//				ftp.setEncoding("utf-8");
//				boolean ret = ftp.put("/upload/jdk-7u51-windows-x64.exe", "F:/jdk-7u51-windows-x64.exe");
//				System.out.println(ret);
//			}
//		});
	try {

    	FTPClientTemplate ftp = new FTPClientTemplate();
//		ftp.setHost("192.168.0.151");
    	ftp.setHost("111.231.110.149");
		ftp.setPort(21);
		ftp.setUsername("anonymous");
		ftp.setPassword("admin");
		ftp.setBinaryTransfer(false);
		ftp.setPassiveMode(false);
		ftp.setEncoding("utf-8");
		
		
		FTPClient client =  ftp.getFTPClient();
		
		String fileName ="3d53828a-f930-4349-b73d-170bff4f94c4.svg"; 
		
		StringBuffer resultBuffer = new StringBuffer();  
        InputStream in = null;  
        FTPClient ftpClient = null;  
        ftpClient = client;
        ftpClient.setControlEncoding("UTF-8"); // 中文支持  
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
        ftpClient.enterLocalPassiveMode();  
        ftpClient.changeWorkingDirectory("/upload");  
        in = ftpClient.retrieveFileStream(fileName);  
        if (in != null) {  
            BufferedReader br = new BufferedReader(new InputStreamReader(in));  
            String data = null;  
            try {  
                while ((data = br.readLine()) != null) {  
                    resultBuffer.append(data + "\n");  
                }  
                System.out.println(resultBuffer);
            } catch (IOException e) {  
               System.out.println("er");
            }finally{  
                try {  
                    ftpClient.disconnect();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }
		
		
		
		
	} catch (Exception e) {
		// TODO: handle exception
	}
//		File file = new File("F:/a.xls");
//		FileInputStream in = null;
//		try {
//			in = new FileInputStream(file);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.out.println(ftp.put("/upload/as.xls",in, "F:/a.xls"));
//		System.out.println(ftp.delete("upload/979d1bd3-2fb1-440d-a8d5-0360c841aa67.docx"));
		
//		System.out.println(ftp.rmdir("asde/sad"));
        //ftp.disconnect();
        //ftp.mkdir("user-upload1");
        //ftp.disconnect();
        
        //String[] aa = {"/group/tbdev/query/user-upload/123.txt", "/group/tbdev/query/user-upload/SMTrace.txt"};
        //ftp.delete(aa);
    }
    
//    public static ArrayList<String[]> csv(InputStream in) {  
//        ArrayList<String[]> csvList = new ArrayList<String[]>();  
//        if (null != in) {  
//            CsvReader reader = new CsvReader(in, ',', Charset.forName("UTF-8"));  
//            try {  
//                //遍历每一行，若有#注释部分，则不处理，若没有，则加入csvList  
//                while (reader.readRecord()) {  
//                    if (!reader.getValues()[0].contains("#"))// 清除注释部分  
//                    {  
//                        //获取的为每一行的信息，以数组的形式  
//                        csvList.add(reader.getValues());  
//                    }  
//                }  
//            } catch (IOException e) {  
//                e.printStackTrace();  
//            }  
//  
//            reader.close();  
//        }  
//        return csvList;  
//    }  
}
