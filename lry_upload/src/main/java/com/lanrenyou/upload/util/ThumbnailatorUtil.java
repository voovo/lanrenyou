package com.lanrenyou.upload.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ThumbnailatorUtil {
	
	public static void main(String[] args) throws IOException{
		long startTime = System.currentTimeMillis();
		String srcPath = "D:/tmp/img/2014/f.jpg";
		String targetPath = "D:/tmp/img/2014/f_l.jpg";
		
		int width = 696;
		int height = 468;
		cropForSize(srcPath, targetPath, width, height);
		
		targetPath = "D:/tmp/img/2014/f_s.jpg";
		width = 336;
		height = 222;
		cropForSize(srcPath, targetPath, width, height);
		long endTime = System.currentTimeMillis();
		System.out.println("New: "+(endTime - startTime) + "ms");		// New: 32705ms
//		System.exit(0);
	}

	public static void cropForSize(String srcFilePath, String targetFilePath, int width, int height){
		try {
			File srcFile = new File(srcFilePath);
			Image srcImage = ImageIO.read(srcFile);
			if(null == srcImage){
				return;
			}
			int w = srcImage.getWidth(null);
			if(w > width * 1.1){
				Thumbnails.of(srcFilePath).size(width, height).toFile(targetFilePath);
			} else {
				copyFile(new File(srcFilePath), new File(targetFilePath));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static void corpForScale(String srcFile, String targetFile, float scale){
		try {
			Thumbnails.of(srcFile).scale(scale).toFile(targetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	// 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
    	if(targetFile.exists()){
    		return;
    	}
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
	
	
	
	public static void mainTest() throws IOException{
		//1、指定大小进行缩放 
		//size(宽度, 高度)  
		  
		/*   
		 * 若图片横比200小，高比300小，不变   
		 * 若图片横比200小，高比300大，高缩小到300，图片比例不变   
		 * 若图片横比200大，高比300小，横缩小到200，图片比例不变   
		 * 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300   
		 */   
		Thumbnails.of("images/a380_1280x1024.jpg")
				.size(200, 300)
				.toFile("c:/a380_200x300.jpg");  
		  
		Thumbnails.of("images/a380_1280x1024.jpg")
				.size(2560, 2048)
				.toFile("c:/a380_2560x2048.jpg");
		
		
		
		//2、按照比例进行缩放 
		//scale(比例)  
		Thumbnails.of("images/a380_1280x1024.jpg")   
		        .scale(0.25f)  
		        .toFile("c:/a380_25%.jpg");  
		  
		Thumbnails.of("images/a380_1280x1024.jpg")   
		        .scale(1.10f)  
		        .toFile("c:/a380_110%.jpg");  
		
		
		
		
		//3、不按照比例，指定大小进行缩放 
		//keepAspectRatio(false) 默认是按照比例缩放的  
		Thumbnails.of("images/a380_1280x1024.jpg")   
		        .size(200, 200)   
		        .keepAspectRatio(false)   
		        .toFile("c:/a380_200x200.jpg");  
		
		
		//4、旋转 
		//rotate(角度),正数：顺时针 负数：逆时针  
		Thumbnails.of("images/a380_1280x1024.jpg")   
		        .size(1280, 1024)  
		        .rotate(90)   
		        .toFile("c:/a380_rotate+90.jpg");   
		  
		Thumbnails.of("images/a380_1280x1024.jpg")   
		        .size(1280, 1024)  
		        .rotate(-90)   
		        .toFile("c:/a380_rotate-90.jpg");   
		
		
		
		//5、水印 
		//watermark(位置，水印图，透明度)  
		Thumbnails.of("images/a380_1280x1024.jpg")   
		        .size(1280, 1024)  
		        .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("images/watermark.png")), 0.5f)   
		        .outputQuality(0.8f)   
		        .toFile("c:/a380_watermark_bottom_right.jpg");  
		  
		Thumbnails.of("images/a380_1280x1024.jpg")   
		        .size(1280, 1024)  
		        .watermark(Positions.CENTER, ImageIO.read(new File("images/watermark.png")), 0.5f)   
		        .outputQuality(0.8f)   
		        .toFile("c:/a380_watermark_center.jpg");  
		
		
		
		//6、裁剪 
		//sourceRegion()  
		  
		//图片中心400*400的区域  
		Thumbnails.of("images/a380_1280x1024.jpg")  
		        .sourceRegion(Positions.CENTER, 400,400)  
		        .size(200, 200)  
		        .keepAspectRatio(false)   
		        .toFile("c:/a380_region_center.jpg");  
		  
		//图片右下400*400的区域  
		Thumbnails.of("images/a380_1280x1024.jpg")  
		        .sourceRegion(Positions.BOTTOM_RIGHT, 400,400)  
		        .size(200, 200)  
		        .keepAspectRatio(false)   
		        .toFile("c:/a380_region_bootom_right.jpg");  
		  
		//指定坐标  
		Thumbnails.of("images/a380_1280x1024.jpg")  
		        .sourceRegion(600, 500, 400, 400)  
		        .size(200, 200)  
		        .keepAspectRatio(false)   
		        .toFile("c:/a380_region_coord.jpg");  
		
		
		
		//7、转化图像格式 
		//outputFormat(图像格式)  
		Thumbnails.of("images/a380_1280x1024.jpg")   
		        .size(1280, 1024)  
		        .outputFormat("png")   
		        .toFile("c:/a380_1280x1024.png");   
		  
		Thumbnails.of("images/a380_1280x1024.jpg")   
		        .size(1280, 1024)  
		        .outputFormat("gif")
		        .toFile("c:/a380_1280x1024.gif");
		
		
		
		
		//8、输出到OutputStream 
		//toOutputStream(流对象)  
		OutputStream os = new FileOutputStream("c:/a380_1280x1024_OutputStream.png");  
		Thumbnails.of("images/a380_1280x1024.jpg")   
		        .size(1280, 1024)  
		        .toOutputStream(os);  


		//9、输出到BufferedImage 
		//asBufferedImage() 返回BufferedImage  
		BufferedImage thumbnail = Thumbnails.of("images/a380_1280x1024.jpg")   
		        .size(1280, 1024)  
		        .asBufferedImage();  
		ImageIO.write(thumbnail, "jpg", new File("c:/a380_1280x1024_BufferedImage.jpg"));   
	}
}
