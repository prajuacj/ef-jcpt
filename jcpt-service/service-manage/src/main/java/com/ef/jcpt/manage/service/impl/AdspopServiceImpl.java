package com.ef.jcpt.manage.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ef.jcpt.common.constant.PopadsStatusConst;
import com.ef.jcpt.common.constant.ReqStatusConst;
import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.common.util.HttpClientUtils;
import com.ef.jcpt.common.util.StringUtil;
import com.ef.jcpt.core.sign.SHA1Util;
import com.ef.jcpt.manage.dao.PopadsInfoMapper;
import com.ef.jcpt.manage.dao.PopadsModelMapper;
import com.ef.jcpt.manage.dao.model.PopadsInfo;
import com.ef.jcpt.manage.dao.model.PopadsModel;
import com.ef.jcpt.manage.service.IAdspopService;
import com.ef.jcpt.manage.service.bo.AdspopPublishBo;

@Service
public class AdspopServiceImpl implements IAdspopService {

	@Autowired
	private PopadsModelMapper popadsModelMapper;

	@Autowired
	private PopadsInfoMapper popadsInfoMapper;

	@Value("${adspop.js.path}")
	private String jspath;

	@Value("${adspop.realse.path}")
	private String realsePath;

	@Value("${adspop.adv.push.key}")
	private String ADV_PUSH_KEY;

	@Value("${adspop.tar.domain}")
	private String tarDomain;

	@Value("${adspop.realse.url}")
	private String realseUrl;

	@Override
	public BasicServiceModel<String> getModeIdNamePair() {
		// TODO Auto-generated method stub
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		try {
			List<Map<Integer, String>> list = popadsModelMapper.getModelIdNamePair();
			bsm.setCode(ReqStatusConst.OK);
			bsm.setData(JSONObject.toJSONString(list));
			return bsm;
		} catch (Exception e) {
			e.printStackTrace();
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg(e.getMessage());
			return bsm;
		}
	}

	@Override
	public BasicServiceModel<String> publishAdspop(AdspopPublishBo bo) {
		// TODO Auto-generated method stub
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		Date curTime = new Date(System.currentTimeMillis());
		try {
			int modelId = Integer.parseInt(bo.getModelId());
			PopadsModel model = popadsModelMapper.selectByPrimaryKey(modelId);
			String modelContent = model.getModelContent();
			String popUrl = bo.getTaskUrl();
			String imgsBase64 = getImgStr(bo.getTaskImageFilePath());
			String adspopContent = modelContent.replaceAll("\\$\\|linkurl\\|\\$", popUrl)
					.replaceAll("\\$\\|linkimg\\|\\$", imgsBase64);

			File pathDir = new File(jspath);
			if (!pathDir.exists()) {// 如果文件夹不存在
				pathDir.mkdirs();// 创建文件夹
			}

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

			int intervalTime = -1;
			Date validStartTime = curTime;
			Date validEndTime = curTime;
			String intervalTimeStr = bo.getIntervalTime();
			String validStartTimeStr = bo.getValidStartTime();
			String validEndTimeStr = bo.getValidEndTime();

			try {
				if (StringUtil.isNotEmpty(intervalTimeStr)) {
					intervalTime = Integer.parseInt(intervalTimeStr);
				}
				if (StringUtil.isNotEmpty(validStartTimeStr)) {
					validStartTime = sf.parse(validStartTimeStr);
				}
				if (StringUtil.isNotEmpty(validEndTimeStr)) {
					validEndTime = sf.parse(validEndTimeStr);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			PopadsInfo record = new PopadsInfo();

			record.setCreateTime(curTime);
			record.setModelId(modelId);
			record.setPublishPhone(bo.getPublishPhone());
			record.setPublishUser(bo.getPublishUser());
			record.setTaskContent(adspopContent);
			record.setTaskDesc(bo.getTaskDesc());
			record.setTaskImgs(bo.getTaskImageFilePath());
			record.setTaskName(bo.getTaskName());
			record.setTaskStatus(PopadsStatusConst.SUBMIT);
			record.setTaskUrl(bo.getTaskUrl());
			record.setUpdateTime(curTime);
			record.setIntervalTime(intervalTime);
			record.setProvince(bo.getProvince());
			record.setCity(bo.getCity());
			record.setValidEndTime(validEndTime);
			record.setValidStartTime(validStartTime);

			int keyId = popadsInfoMapper.insertSelective(record);

			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(pathDir + "/" + keyId + ".txt"));
				bw.write(adspopContent);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			bsm.setCode(ReqStatusConst.OK);
			return bsm;
		} catch (Exception e) {
			e.printStackTrace();
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg(e.getMessage());
			return bsm;
		}
	}

	/**
	 * 将图片转换成Base64编码
	 * 
	 * @param imgFile 待处理图片
	 * @return
	 */
	public static String getImgStr(String imgFile) {
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理

		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(Base64.encodeBase64(data));
	}

	@Override
	public BasicServiceModel<String> audit(int popadsId, String auditStatus, String auditUser, String auditAdvise) {
		// TODO Auto-generated method stub
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		try {
			int ret = popadsInfoMapper.audit(popadsId, auditStatus, auditUser, auditAdvise);
			if (ret == 1) {
				bsm.setCode(ReqStatusConst.OK);
				return bsm;
			} else {
				bsm.setCode(ReqStatusConst.FAIL);
				bsm.setMsg("审核更新数据库失败！");
				return bsm;
			}
		} catch (Exception e) {
			e.printStackTrace();
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg(e.getMessage());
			return bsm;
		}
	}

	@Override
	public BasicServiceModel<String> realse(int[] popadsIds) {
		// TODO Auto-generated method stub
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		long curTime = System.currentTimeMillis();

		if ((null != popadsIds) && (popadsIds.length > 0)) {

			List<Integer> list = new ArrayList<Integer>();
			for (Integer popadsId : popadsIds) {
				list.add(popadsId);
			}

			popadsInfoMapper.updateRealseStatusByBatch(list);

			File pathDir = new File(realsePath + curTime + System.getProperty("file.separator"));
			if (!pathDir.exists()) {// 如果文件夹不存在
				pathDir.mkdirs();// 创建文件夹
			}
			try {
				String issueFileName = pathDir.getAbsolutePath() + System.getProperty("file.separator") + "issue";
				FileWriter issue = new FileWriter(issueFileName);
				for (int popadsId : popadsIds) {
					issue.write(popadsId + ".txt");
					issue.write(System.getProperty("line.separator"));
				}
				issue.close();

				String[] fileNames = new String[popadsIds.length + 1];
				fileNames[0] = issueFileName;
				for (int i = 0; i < popadsIds.length; i++) {
					fileNames[i + 1] = jspath + popadsIds[i] + ".txt";
				}
				String targzipFilePath = pathDir + System.getProperty("file.separator") + curTime + ".tar";
				String targzipFileName = targzipFilePath + ".gz";
				CompressedFiles_Gzip(fileNames, targzipFilePath, targzipFileName);

				String durl = tarDomain + curTime + ".tar.gz";
				String signature = SHA1Util.getSha1(curTime + ADV_PUSH_KEY + durl);

				Map<String, String> map = new HashMap<String, String>();
				map.put("timestamp", String.valueOf(curTime));
				map.put("durl", durl);
				map.put("signature", signature);

				sendHttpRequest(map);

				bsm.setCode(ReqStatusConst.OK);
				return bsm;
			} catch (Exception e) {
				e.printStackTrace();
				bsm.setCode(ReqStatusConst.FAIL);
				bsm.setMsg(e.getMessage());
				return bsm;
			}
		} else {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("需要发布的广告为空");
			return bsm;
		}
	}

	private void sendHttpRequest(Map<String, String> params) {
		// TODO Auto-generated method stub
		try {
			String retStr = HttpClientUtils.requestPost(realseUrl, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void CompressedFiles_Gzip(String[] compressFilesNames, String targzipFilePath,
			String targzipFileName) {
		byte[] buf = new byte[1024]; // 设定读入缓冲区尺寸
		try {
			// 建立压缩文件输出流
			FileOutputStream fout = new FileOutputStream(targzipFilePath);
			// 建立tar压缩输出流
			TarArchiveOutputStream tout = new TarArchiveOutputStream(fout);
			for (String filename : compressFilesNames) {
				File tarEnFile = new File(filename);
				// 打开需压缩文件作为文件输入流
				FileInputStream fin = new FileInputStream(filename); // filename是文件全路径
				TarArchiveEntry tarEn = new TarArchiveEntry(tarEnFile); // 此处必须使用new TarEntry(File file);
				tarEn.setName(tarEnFile.getName()); // 此处需重置名称，默认是带全路径的，否则打包后会带全路径
				tout.putArchiveEntry(tarEn);
				int num;
				while ((num = fin.read(buf)) != -1) {
					tout.write(buf, 0, num);
				}
				tout.closeArchiveEntry();
				fin.close();
			}
			tout.close();
			fout.close();

			// 建立压缩文件输出流
			FileOutputStream gzFile = new FileOutputStream(targzipFilePath + ".gz");
			// 建立gzip压缩输出流
			GZIPOutputStream gzout = new GZIPOutputStream(gzFile);
			// 打开需压缩文件作为文件输入流
			FileInputStream tarin = new FileInputStream(targzipFilePath); // targzipFilePath是文件全路径
			int len;
			while ((len = tarin.read(buf)) != -1) {
				gzout.write(buf, 0, len);
			}
			gzout.close();
			gzFile.close();
			tarin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int countPopadsByPage(String taskName, String taskStatus, int modelId) {
		// TODO Auto-generated method stub
		return popadsInfoMapper.countPopadsByPage(taskName, taskStatus, modelId);
	}

	@Override
	public BasicServiceModel<String> listPopadsByPage(String taskName, String taskStatus, int modelId, int pageIndex,
			int pageSize) {
		// TODO Auto-generated method stub
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		// TODO Auto-generated method stub
		int start = (pageIndex - 1) * pageSize;
		if (start < 0) {
			start = 0;
		}
		List<PopadsInfo> list = popadsInfoMapper.listPopadsByPage(taskName, taskStatus, modelId, start, pageSize);
		if (null != list) {
			JSONObject data = new JSONObject();
			data.put("data", list);
			bsm.setData(data.toJSONString());
		}
		bsm.setCode(ReqStatusConst.OK);

		return bsm;
	}

	@Override
	public BasicServiceModel<String> updatePopads(AdspopPublishBo bo) {
		// TODO Auto-generated method stub
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		Date curTime = new Date(System.currentTimeMillis());
		try {
			int modelId = Integer.parseInt(bo.getModelId());
			PopadsModel model = popadsModelMapper.selectByPrimaryKey(modelId);
			String modelContent = model.getModelContent();
			String popUrl = bo.getTaskUrl();
			String imgsBase64 = getImgStr(bo.getTaskImageFilePath());
			String adspopContent = modelContent.replaceAll("\\$\\|linkurl\\|\\$", popUrl)
					.replaceAll("\\$\\|linkimg\\|\\$", imgsBase64);

			File pathDir = new File(jspath);
			if (!pathDir.exists()) {// 如果文件夹不存在
				pathDir.mkdirs();// 创建文件夹
			}

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

			int intervalTime = -1;
			Date validStartTime = curTime;
			Date validEndTime = curTime;
			String intervalTimeStr = bo.getIntervalTime();
			String validStartTimeStr = bo.getValidStartTime();
			String validEndTimeStr = bo.getValidEndTime();

			try {
				if (StringUtil.isNotEmpty(intervalTimeStr)) {
					intervalTime = Integer.parseInt(intervalTimeStr);
				}
				if (StringUtil.isNotEmpty(validStartTimeStr)) {
					validStartTime = sf.parse(validStartTimeStr);
				}
				if (StringUtil.isNotEmpty(validEndTimeStr)) {
					validEndTime = sf.parse(validEndTimeStr);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			PopadsInfo record = new PopadsInfo();

			record.setId(bo.getId());
			record.setModelId(modelId);
			record.setPublishPhone(bo.getPublishPhone());
			record.setPublishUser(bo.getPublishUser());
			record.setTaskContent(adspopContent);
			record.setTaskDesc(bo.getTaskDesc());
			record.setTaskImgs(bo.getTaskImageFilePath());
			record.setTaskName(bo.getTaskName());
//			record.setTaskStatus(PopadsStatusConst.SUBMIT);
			record.setTaskUrl(bo.getTaskUrl());
			record.setUpdateTime(curTime);
			record.setIntervalTime(intervalTime);
			record.setProvince(bo.getProvince());
			record.setCity(bo.getCity());
			record.setValidEndTime(validEndTime);
			record.setValidStartTime(validStartTime);

			int keyId = popadsInfoMapper.updateByPrimaryKeySelective(record);

			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(pathDir + "/" + keyId + ".txt"));
				bw.write(adspopContent);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			bsm.setCode(ReqStatusConst.OK);
			return bsm;
		} catch (Exception e) {
			e.printStackTrace();
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg(e.getMessage());
			return bsm;
		}
	}
}
