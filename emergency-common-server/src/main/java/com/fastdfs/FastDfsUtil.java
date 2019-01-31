package com.fastdfs;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.*;
import java.util.Arrays;

/**
 * @author LI.HU
 * @date 2019/1/21   16:58
 * <p>Description:
 * Fast方法体
 * </p>
 */
public class FastDfsUtil {

    /**
     * 基本操作
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //加载配置文件的方式
        String configFileName = "src\\main\\resources\\static\\client.conf";
        try {
            ClientGlobal.init(configFileName);
        }catch(Exception e){
            e.printStackTrace();
        }
        File file = new File("C:\\Users\\TIG\\Desktop\\kafka\\3.jpeg");
        //返回储存路径:group1 M00/00/00/wKhuW1Vmj6KAZ09pAAC9przUxEk788.jpg
        String[] files =  uploadFile(file, "test.jpg", file.length());
        System.out.println(Arrays.asList(files));
//        下载文件
        downloadFile(files[0],files[1]);
        //查看文件信息
        getFileInfo(files[0],files[1]);
        getFileMate(files[0],files[1]);
        deleteFile(files[0],files[1]);
    }

    /**
     * 上传文件
     */
    public static String[] uploadFile(File file, String uploadFileName, long fileLength) throws IOException {
        System.out.println("上传文件=======================");
        byte[] fileBuff = getFileBuffer(new FileInputStream(file), fileLength);
        String[] files = null;
        String fileExtName = "";
        if (uploadFileName.contains(".")) {
            fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
        } else {
            System.out.println("Fail to upload file, because the format of filename is illegal.");
            return null;
        }

        // 建立连接
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient client = new StorageClient(trackerServer, storageServer);
        // 设置元信息
        NameValuePair[] metaList = new NameValuePair[3];
        metaList[0] = new NameValuePair("fileName", uploadFileName);
        metaList[1] = new NameValuePair("fileExtName", fileExtName);
        metaList[2] = new NameValuePair("fileLength", String.valueOf(fileLength));

        // 上传文件
        try {
            files = client.upload_file(fileBuff, fileExtName, metaList);
        } catch (Exception e) {
            System.out.println("Upload file \"" + uploadFileName + "\"fails");
        }
        trackerServer.close();
        return files;
    }
    private static byte[] getFileBuffer(InputStream inStream, long fileLength) throws IOException {

        byte[] buffer = new byte[256 * 1024];
        byte[] fileBuffer = new byte[(int) fileLength];

        int count = 0;
        int length = 0;

        while ((length = inStream.read(buffer)) != -1) {
            for (int i = 0; i < length; ++i) {
                fileBuffer[count + i] = buffer[i];
            }
            count += length;
        }
        return fileBuffer;
    }

    //下载文件
    public static void downloadFile(String groupName,String filepath) throws Exception{
        System.out.println("下载文件=======================");
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;

        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        byte[] b = storageClient.download_file(groupName, filepath);
        System.out.println("文件大小:"+b.length);
        String fileName = "src/main/resources/test1.jpg";
        FileOutputStream out = new FileOutputStream(fileName);
        out.write(b);
        out.flush();
        out.close();
    }

    //查看文件信息
    public static void getFileInfo(String groupName,String filepath) throws Exception{
        System.out.println("获取文件信息=======================");
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;

        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        FileInfo fi = storageClient.get_file_info(groupName,filepath);
        System.out.println("所在服务器地址:"+fi.getSourceIpAddr());
        System.out.println("文件大小:"+fi.getFileSize());
        System.out.println("文件创建时间:"+fi.getCreateTimestamp());
        System.out.println("文件CRC32 signature:"+fi.getCrc32());
    }

    public static void getFileMate(String groupName,String filepath) throws Exception{
        System.out.println("获取文件Mate=======================");
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;

        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        NameValuePair nvps[] = storageClient.get_metadata(groupName,filepath);
        for (NameValuePair nvp : nvps) {
            System.out.println(nvp.getName() + ":" + nvp.getValue());
        }
    }

    public static void deleteFile(String groupName,String filepath) throws Exception{
        System.out.println("删除文件=======================");
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        int i = storageClient.delete_file(groupName, filepath);
        System.out.println(i == 0 ? "删除成功" : "删除失败:" + i);
    }
}
