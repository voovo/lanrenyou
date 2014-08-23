package com.lanrenyou.util;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtils {
	
	public static void main(String[] args) throws IOException{
		cropImageForTravel("D:/tmp/WZN_8951.jpg");
	}
	
	public static void cropImageForTravel(String srcPath) throws IOException{
		File srcFile = new File(srcPath);
		Image srcImage = ImageIO.read(srcFile);
		if(null != srcImage){
			int w = srcImage.getWidth(null);
			if(w > 800){
				// 等比压缩
				float radio = w / 696; 
				String lPath = srcPath.substring(0, srcPath.lastIndexOf('.')) + "_l.jpg";
				reduceImageEqualProportion(srcPath, lPath, radio);
				radio = w / 336; 
				String sPath = srcPath.substring(0, srcPath.lastIndexOf('.')) + "_s.jpg";
				reduceImageEqualProportion(srcPath, sPath, radio);
				return;
			} 
		}
		String sJPGPath = srcPath.substring(0, srcPath.lastIndexOf('.')) + "_s.jpg";
		String lJPGPath = srcPath.substring(0, srcPath.lastIndexOf('.')) + "_l.jpg";
		ImageUtils.copyFile(new File(srcPath), new File(sJPGPath));
		ImageUtils.copyFile(new File(srcPath), new File(lJPGPath));
	}
	
	
	public static void cropImageForIndex(String srcPath) throws IOException{
		File srcFile = new File(srcPath);
		Image srcImage = ImageIO.read(srcFile);
		if(null == srcImage){
			return;
		}
		int w = srcImage.getWidth(null);
		int h = srcImage.getHeight(null);
		String operateImgPath = srcPath;
		if(w > 1000 && h > 661){
			// 先等比压缩再裁剪
			float radio = w / 800; 
			String tmpPath = srcPath.substring(0, srcPath.lastIndexOf('.')) + "_tmp.jpg";
			reduceImageEqualProportion(srcPath, tmpPath, radio);
			srcFile = new File(tmpPath);
			srcImage = ImageIO.read(srcFile);
			w = srcImage.getWidth(null);
			h = srcImage.getHeight(null);
			operateImgPath = tmpPath;
		}
			
		if(w >= 696 && h >= 468){
			int toWidth = 696;
			int toHeight = 468;
			
			int x = 0;
			int y = 0;
			x = (w - toWidth)/2;
			y = (h - toHeight)/2;
			String toPath696x468 = srcPath.substring(0, srcPath.lastIndexOf('.')) + "_il.jpg";
			cropImage(operateImgPath, toPath696x468, x, y, toWidth, toHeight, srcPath.substring(srcPath.lastIndexOf('.') + 1), "jpeg");
			
			toWidth = 696;
			toHeight = 222;
			x = (w - toWidth)/2;
			y = (h - toHeight)/2;
			String toPath696x222 = srcPath.substring(0, srcPath.lastIndexOf('.')) + "_in.jpg";
			cropImage(operateImgPath, toPath696x222, x, y, toWidth, toHeight, srcPath.substring(srcPath.lastIndexOf('.') + 1), "jpeg");
			
			String toPath336x222 = srcPath.substring(0, srcPath.lastIndexOf('.')) + "_is.jpg";
			reduceImageEqualProportion(toPath696x468, toPath336x222, 2);
			
			toWidth = 336;
			toHeight = 468;
			x = (w - toWidth)/2;
			y = (h - toHeight)/2;
			String toPath336x468 = srcPath.substring(0, srcPath.lastIndexOf('.')) + "_iv.jpg";
			cropImage(operateImgPath, toPath336x468, x, y, toWidth, toHeight, srcPath.substring(srcPath.lastIndexOf('.') + 1), "jpeg");
		}
	}
	
	/**
	 * 对图片裁剪，并把裁剪新图片保存
	 * 
	 * @param srcPath
	 *            读取源图片路径
	 * @param toPath
	 *            写入图片路径
	 * @param x
	 *            剪切起始点x坐标
	 * @param y
	 *            剪切起始点y坐标
	 * @param width
	 *            剪切宽度
	 * @param height
	 *            剪切高度
	 * @param fileSuffix
	 *            读取图片后缀
	 * @param writeImageFormat
	 *            写入图片格式
	 * @throws IOException
	 */
	protected static void cropImage(String srcPath, String toPath, int x, int y,
			int width, int height, String fileSuffix,
			String writeImageFormat) throws IOException {
		FileInputStream fis = null;
		ImageInputStream iis = null;
		try {
			// 读取图片文件
			fis = new FileInputStream(srcPath);
			Iterator<ImageReader> it = ImageIO.getImageReadersBySuffix(fileSuffix);
			ImageReader reader = (ImageReader) it.next();
			// 获取图片流
			iis = ImageIO.createImageInputStream(fis);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			// 定义一个矩形
			Rectangle rect = new Rectangle(x, y, width, height);
			// 提供一个 BufferedImage，将其用作解码像素数据的目标。
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			// 保存新图片
			ImageIO.write(bi, writeImageFormat, new File(toPath));
		} catch(IIOException e) {	
//			e.printStackTrace();
			copyFile(new File(srcPath), new File(toPath));
		} finally {
			if (fis != null)
				fis.close();
			if (iis != null)
				iis.close();
		}
	}

	/**
	 * 按倍率缩小图片
	 * 
	 * @param srcImagePath
	 *            读取图片路径
	 * @param toImagePath
	 *            写入图片路径
	 * @param widthRatio
	 *            宽度缩小比例
	 * @param heightRatio
	 *            高度缩小比例
	 * @throws IOException
	 */
	protected static void reduceImageByRatio(String srcImagePath, String toImagePath,
			int widthRatio, int heightRatio) throws IOException {
		FileOutputStream out = null;
		try {
			// 读入文件
			File file = new File(srcImagePath);
			// 构造Image对象
			BufferedImage src = javax.imageio.ImageIO.read(file);
			int width = src.getWidth();
			int height = src.getHeight();
			// 缩小边长
			BufferedImage tag = new BufferedImage(width / widthRatio, height
					/ heightRatio, BufferedImage.TYPE_INT_RGB);
			// 绘制 缩小 后的图片
			tag.getGraphics().drawImage(src, 0, 0, width / widthRatio,
					height / heightRatio, null);
			out = new FileOutputStream(toImagePath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 长高等比例缩小图片
	 * 
	 * @param srcImagePath
	 *            读取图片路径
	 * @param toImagePath
	 *            写入图片路径
	 * @param ratio
	 *            缩小比例
	 * @throws IOException
	 */
	protected static void reduceImageEqualProportion(String srcImagePath,
			String toImagePath, float ratio) throws IOException {
		FileOutputStream out = null;
		try {
			// 读入文件
			File file = new File(srcImagePath);
			// 构造Image对象
			BufferedImage src = javax.imageio.ImageIO.read(file);
			int width = src.getWidth();
			int height = src.getHeight();
			// 缩小边长
			BufferedImage tag = new BufferedImage((int)(width / ratio),
					(int)(height / ratio), BufferedImage.TYPE_INT_RGB);
			// 绘制 缩小 后的图片
			tag.getGraphics().drawImage(src, 0, 0, (int)(width / ratio),
					(int)(height / ratio), null);
			out = new FileOutputStream(toImagePath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
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

}
