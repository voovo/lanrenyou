package com.lanrenyou.admin.controller.audit;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import mybatis.framework.core.support.PageIterator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.reflect.TypeToken;
import com.lanrenyou.admin.controller.base.BaseController;
import com.lanrenyou.travel.enums.TravelInfoStatusEnum;
import com.lanrenyou.travel.model.IndexTravel;
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.travel.service.IIndexTravelService;
import com.lanrenyou.travel.service.ITravelContentService;
import com.lanrenyou.travel.service.ITravelInfoService;
import com.lanrenyou.user.enums.UserInfoStatusEnum;
import com.lanrenyou.user.enums.UserInfoTypeEnum;
import com.lanrenyou.user.enums.UserPlannerStatusEnum;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.model.UserPlanner;
import com.lanrenyou.user.service.IUserInfoService;
import com.lanrenyou.user.service.IUserPlannerService;
import com.lanrenyou.util.MailUtil;
import com.lanrenyou.util.StringUtil;
import com.lanrenyou.util.freemarker.FreemarkerUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


@Controller
@RequestMapping("/audit/index_data")
public class AuditIndexController extends BaseController {
	
	@Autowired
	private IUserPlannerService userPlannerService;
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@Autowired
	private ITravelInfoService travelInfoService;
	
	@Autowired
	private ITravelContentService travelContentService;
	
	@Autowired
	private IIndexTravelService indexTravelService;
	
	@RequestMapping(value="/list")
	public ModelAndView plannerList(
			@RequestParam(value="queryTid",required=false, defaultValue="-1") Integer tid,
			@RequestParam(value="queryType",required=false, defaultValue="0") Integer queryType,
			@RequestParam(value="pageNo",required=false, defaultValue="1") int pageNo,
			@RequestParam(value="pageSize",required=false, defaultValue="10") int pageSize
		){
		ModelAndView mav = new ModelAndView();
		if(null == queryType){
			queryType = 0;
		}
		if(null == tid){
			tid = 0;
		}
		mav.addObject("queryTid", tid);
		mav.addObject("queryType", queryType);
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		if(queryType == 0){
			mav.setViewName("/admin/audit/index_data_list");
			PageIterator<IndexTravel> pageIter = indexTravelService.pageQueryByTidSrcType(tid, null, pageNo, pageSize);
			if(null != pageIter && null != pageIter.getData() && pageIter.getData().size() > 0){
				mav.addObject("pageIter", pageIter);
				List<Integer> allTidList = new ArrayList<Integer>();
				for(IndexTravel it : pageIter.getData()){
					allTidList.add(it.getTid());
				}
				Map<Integer, TravelInfo> travelInfoMap = travelInfoService.getTravelInfoMapByTidList(allTidList);
				mav.addObject("travelInfoMap", travelInfoMap);
				Map<Integer, String> srcSourceUrlMap = getSrcSourceUrlMap(allTidList);
				Map<Integer, String> srcUrlMap = new HashMap<Integer, String>();
				for(IndexTravel it : pageIter.getData()){
					String srcUrl = srcSourceUrlMap.get(it.getTid());
					if(StringUtils.isNotBlank(srcUrl)){
						srcUrl = srcUrl.replace(".jpg", "_" + String.valueOf(it.getSrcType()) + ".jpg");
						srcUrlMap.put(it.getTid(), srcUrl);
					}
				}
				
				mav.addObject("srcUrlMap", srcUrlMap);
			}
		} else {
			mav.setViewName("/admin/audit/index_data_setting_list");
			PageIterator<TravelInfo> pageIter = travelInfoService.pageQueryByTidStatus(tid, TravelInfoStatusEnum.PASS.getValue(), pageNo, pageSize);
			if(null != pageIter && null != pageIter.getData()){
				mav.addObject("pageIter", pageIter);
				List<Integer> uidList = new ArrayList<Integer>();
				List<Integer> tidList = new ArrayList<Integer>();
				for(TravelInfo ti : pageIter.getData()){
					uidList.add(ti.getUid());
					tidList.add(ti.getId());
				}
				if(null != uidList && uidList.size() > 0){
					Map<Integer, UserInfo> userInfoMap = userInfoService.getUserInfoMapByUidList(uidList);
					mav.addObject("userInfoMap", userInfoMap);
				}
				Map<Integer, String> srcSourceUrlMap = getSrcSourceUrlMap(tidList);
				Map<Integer, String> srcUrlMap = new HashMap<Integer, String>();
				for(TravelInfo it : pageIter.getData()){
					String srcUrl = srcSourceUrlMap.get(it.getId());
					if(StringUtils.isNotBlank(srcUrl)){
						srcUrl = srcUrl.replace(".jpg", "_s.jpg");
						srcUrlMap.put(it.getId(), srcUrl);
					}
				}
				
				mav.addObject("srcUrlMap", srcUrlMap);
			}
			
		}
		
		return mav;
	}
	
	private Map<Integer, String> getSrcSourceUrlMap(List<Integer> tidList){
		if(null == tidList || tidList.size() <= 0){
			return null;
		}
		Map<Integer, String> srcSourceUrlMap = new HashMap<Integer, String>();
		List<TravelContent> travelContentList = travelContentService.getTravelContentListByTidList(tidList);
		for(TravelContent tc : travelContentList){
			List<Map<String, String>> list = gson.fromJson(tc.getContent(), new TypeToken<List<Map<String, String>>>(){}.getType());
			if(null != list && list.size() >= 1){
				Map<String, String> map = list.get(0);
				if(null != map){
					String img = map.get("src");
					if(StringUtils.isNotBlank(img) && (img.contains("wp-content/uploads") || img.contains("img.lanrenyou.com"))){
						if(!img.endsWith("_s.jpg")){
//							map.put("src", img);
							srcSourceUrlMap.put(tc.getTid(), img);
						} else {
							img = img.replace("_s.jpg", ".jpg");
//							map.put("src", img);
							srcSourceUrlMap.put(tc.getTid(), img);
						}
					}
				}
			}
		}
		return srcSourceUrlMap;
	}

	@RequestMapping(value="/add", method=RequestMethod.GET)
	@ResponseBody
	public String add(@RequestParam(value="tid", required=true) Integer tid, @RequestParam(value="srcType", required=true) Character srcType){
		Map<String, Object> map = new HashMap<String, Object>();
		if(null == tid){
			map.put("status", "n");
			map.put("info", "未指定游记ID");
			return gson.toJson(map);
		}
		if(null == srcType){
			map.put("status", "n");
			map.put("info", "未指定图片类型");
			return gson.toJson(map);
		}
		TravelInfo travelInfo = travelInfoService.getTravelInfoById(tid);
		if(null == travelInfo){
			map.put("status", "n");
			map.put("info", "根据游记ID未查询到游记信息");
			return gson.toJson(map);
		}
		IndexTravel it = indexTravelService.getByTid(tid);
		if(null != it){
			map.put("status", "n");
			map.put("info", "该游记已经被设为首页展示");
			return gson.toJson(map);
		}
		
		TravelContent tc = travelContentService.getTravelContentByTid(tid);
		List<Map<String, String>> list = gson.fromJson(tc.getContent(), new TypeToken<List<Map<String, String>>>(){}.getType());
		String firstSrcUrl = null;
		if(null != list && list.size() >= 1){
			Map<String, String> srcmap = list.get(0);
			if(null != map){
				String img = srcmap.get("src");
				if(StringUtils.isNotBlank(img) && (img.contains("wp-content/uploads") || img.contains("img.lanrenyou.com"))){
					if(!img.endsWith("_s.jpg")){
						firstSrcUrl = img;
					} else {
						firstSrcUrl = img.replace("_s.jpg", ".jpg");
					}
				}
			}
		}
		if(StringUtil.isBlank(firstSrcUrl)){
			map.put("status", "n");
			map.put("info", "未找到游记图片");
			return gson.toJson(map);
		}
		try {
			if(firstSrcUrl.contains("wp-content/uploads") && !firstSrcUrl.startsWith("http")){
				firstSrcUrl = "/ROOT/www/www_8000" + firstSrcUrl;
			} else if (firstSrcUrl.startsWith("http://img.lanrenyou.com")){
				firstSrcUrl = "/ROOT/www/img_8010/" + firstSrcUrl.substring(26);
			}
			cropImageForIndex(firstSrcUrl);
		} catch (IOException e) {
			e.printStackTrace();
			map.put("status", "n");
			map.put("info", "图片裁剪出错");
			return gson.toJson(map);
		}
		
		
		it = new IndexTravel();
		it.setTid(tid);
		it.setSrcType(srcType);
		it.setIsTop(0);
		it.setSort(0);
		int result = indexTravelService.addIndexTravel(it);
		if(result > 0){
			map.put("status", "y");
			map.put("info", "操作成功");
			return gson.toJson(map);
		} else {
			map.put("status", "n");
			map.put("info", "操作失败，请稍后再试");
			return gson.toJson(map);
		}
	}
	
	@RequestMapping(value="/remove", method=RequestMethod.GET)
	@ResponseBody
	public String remove(@RequestParam(value="tid", required=true) Integer tid){
		Map<String, Object> map = new HashMap<String, Object>();
		if(null == tid){
			map.put("status", "n");
			map.put("info", "未指定游记ID");
			return gson.toJson(map);
		}
		IndexTravel it = indexTravelService.getByTid(tid);
		if(null == it){
			map.put("status", "n");
			map.put("info", "该游记没有被设为首页展示");
			return gson.toJson(map);
		}
		int result = indexTravelService.deleteByTid(tid);
		
		if(result > 0){
			map.put("status", "y");
			map.put("info", "操作成功");
			return gson.toJson(map);
		} else {
			map.put("status", "n");
			map.put("info", "操作失败，请稍后再试");
			return gson.toJson(map);
		}
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
//			String toPath696x468 = srcPath.substring(0, srcPath.lastIndexOf('.')) + "_il.jpg";
//			cropImage(operateImgPath, toPath696x468, x, y, toWidth, toHeight, srcPath.substring(srcPath.lastIndexOf('.') + 1), "jpeg");
			
			toWidth = 696;
			toHeight = 222;
			x = (w - toWidth)/2;
			y = (h - toHeight)/2;
			String toPath696x222 = srcPath.substring(0, srcPath.lastIndexOf('.')) + "_f.jpg";
			cropImage(operateImgPath, toPath696x222, x, y, toWidth, toHeight, srcPath.substring(srcPath.lastIndexOf('.') + 1), "jpeg");
			
//			String toPath336x222 = srcPath.substring(0, srcPath.lastIndexOf('.')) + "_is.jpg";
//			reduceImageEqualProportion(toPath696x468, toPath336x222, 2);
			
			toWidth = 336;
			toHeight = 468;
			x = (w - toWidth)/2;
			y = (h - toHeight)/2;
			String toPath336x468 = srcPath.substring(0, srcPath.lastIndexOf('.')) + "_n.jpg";
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

