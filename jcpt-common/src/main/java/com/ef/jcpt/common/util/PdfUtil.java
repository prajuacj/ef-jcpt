/**
 * @Project: agreement-web
 * @Package com.hehenian.agreement.utils
 * @Title: PdfUtil.java
 * @Description: TODO
 * 
 * @author: zhanbmf
 * @date 2015-10-21 下午7:15:28
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0
 */
package com.ef.jcpt.common.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;


public class PdfUtil {

	/**
	 * pdf转图片
	 * @author: zhanbmf
	 * @date 2017-3-14 下午2:49:42
	 */
	public static byte[] pdfToPic(InputStream pdfFile) {
		try {
			return pdfToPic(PDDocument.load(pdfFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * pdf转图片
	 * @author: zhanbmf
	 * @date 2017-3-14 下午2:49:42
	 */
	public static byte[] pdfToPic(File pdfFile) {
		try {
			return pdfToPic(PDDocument.load(pdfFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * pdf转图片
	 * @author: zhanbmf
	 * @date 2017-3-14 下午2:49:42
	 */
	public static byte[] pdfToPic(PDDocument pdDocument) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		List<BufferedImage> piclist = new ArrayList<BufferedImage>();
		try {
			PDFRenderer renderer = new PDFRenderer(pdDocument);
			for (int i = 0; i < pdDocument.getNumberOfPages(); i++) {//
				// 0 表示第一页，300 表示转换 dpi，越大转换后越清晰，相对转换速度越慢
				BufferedImage image = renderer.renderImageWithDPI(i, 108);
				piclist.add(image);
			}
			// 总高度 总宽度 临时的高度 , 或保存偏移高度 临时的高度，主要保存每个高度
			int height = 0, width = 0, _height = 0, __height = 0,
			// 图片的数量
			picNum = piclist.size();
			// 保存每个文件的高度
			int[] heightArray = new int[picNum];
			// 保存图片流
			BufferedImage buffer = null;
			// 保存所有的图片的RGB
			List<int[]> imgRGB = new ArrayList<int[]>();
			// 保存一张图片中的RGB数据
			int[] _imgRGB;
			for (int i = 0; i < picNum; i++) {
				buffer = piclist.get(i);
				heightArray[i] = _height = buffer.getHeight();// 图片高度
				if (i == 0) {
					// 图片宽度
					width = buffer.getWidth();
				}
				// 获取总高度
				height += _height;
				// 从图片中读取RGB
				_imgRGB = new int[width * _height];
				_imgRGB = buffer.getRGB(0, 0, width, _height, _imgRGB, 0, width);
				imgRGB.add(_imgRGB);
			}

			// 设置偏移高度为0
			_height = 0;
			// 生成新图片
			BufferedImage imageResult = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			int[] lineRGB = new int[8 * width];
			int c = new Color(128, 128, 128).getRGB();
			for (int i = 0; i < lineRGB.length; i++) {
				lineRGB[i] = c;
			}
			for (int i = 0; i < picNum; i++) {
				__height = heightArray[i];
				// 计算偏移高度
				if (i != 0) _height += __height;
				imageResult.setRGB(0, _height, width, __height, imgRGB.get(i), 0, width); // 写入流中

				// 模拟页分隔
				if (i > 0) {
					imageResult.setRGB(0, _height + 2, width, 8, lineRGB, 0, width);
				}
			}
			// 写流
			ImageIO.write(imageResult, "jpg", baos);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			IOUtils.closeQuietly(baos);
		}
		
		return baos.toByteArray();
	}
	
	/**
	 * @param args
	 * @author: zhanbmf
	 * @throws Exception 
	 * @date 2015-10-21 下午7:15:28
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		/*waterMark("D:\\workspace\\hehenian-agreement\\agreement-web\\target\\hehenian-agreement\\s_3\\static\\files\\3329\\8001345\\Fixed_555555555555556.pdf",
				"D:\\workspace\\hehenian-agreement\\agreement-web\\src\\main\\webapp\\tempfiles\\electronic.png",
				"D:\\workspace\\hehenian-agreement\\agreement-web\\target\\hehenian-agreement\\s_3\\static\\files\\3329\\8001345\\311_112.pdf", "钱生花", 0);*/
		
		byte[] baos = pdfToPic(new File("D:\\workspace\\guide\\share\\xxx.pdf"));
		FileOutputStream fos = new FileOutputStream(new File("D:\\workspace\\guide\\share\\xxx.jpg"));
		fos.write(baos);
		fos.flush();
	}

}
