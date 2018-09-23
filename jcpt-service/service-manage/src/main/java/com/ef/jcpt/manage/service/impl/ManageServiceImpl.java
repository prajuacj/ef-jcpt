package com.ef.jcpt.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ef.jcpt.common.constant.OperatoryStatusConst;
import com.ef.jcpt.common.constant.ReqStatusConst;
import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.common.util.BeanUtil;
import com.ef.jcpt.manage.dao.OperatorInfoMapper;
import com.ef.jcpt.manage.dao.PhoneSupportOperatorMapper;
import com.ef.jcpt.manage.dao.PhoneSupportStandardMapper;
import com.ef.jcpt.manage.dao.model.OperatorInfo;
import com.ef.jcpt.manage.dao.model.PhoneSupportOperator;
import com.ef.jcpt.manage.dao.model.PhoneSupportStandard;
import com.ef.jcpt.manage.service.IManageService;
import com.ef.jcpt.manage.service.bo.OperatorInfoBo;
import com.ef.jcpt.manage.service.bo.PhoneSupportStandardBo;

@Service
public class ManageServiceImpl implements IManageService {

	@Autowired
	private PhoneSupportStandardMapper phoneSupportStandardMapper;

	@Autowired
	private PhoneSupportOperatorMapper phoneSupportOperatorMapper;

	@Autowired
	private OperatorInfoMapper operatorInfoMapper;

	@Override
	public BasicServiceModel<String> savePhoneSupportStandard(PhoneSupportStandardBo bo) {
		// TODO Auto-generated method stub
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		try {
			String phoneModel = bo.getPhoneModel();
			int phoneModelCount = phoneSupportStandardMapper.selectPhoneSupportStandard(phoneModel);
			if (phoneModelCount > 0) {
				bsm.setCode(ReqStatusConst.OK);
				return bsm;
			} else {
				PhoneSupportStandard info = new PhoneSupportStandard();
				BeanUtil.copyProperties(bo, info);
				phoneSupportStandardMapper.insertSelective(info);
				bsm.setCode(ReqStatusConst.OK);
				bsm.setData(info.getPhoneModel());
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
	public BasicServiceModel<String> configPhoneSupportStandard(PhoneSupportStandardBo bo) {
		// TODO Auto-generated method stub
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		try {
			PhoneSupportStandard info = new PhoneSupportStandard();
			BeanUtil.copyProperties(bo, info);
			phoneSupportStandardMapper.insertSelective(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String phoneModel = bo.getPhoneModel();
		String standard = bo.getMobileNetworkStandard();
		List<Map<String, String>> list = phoneSupportStandardMapper.selectPhoneSupportOperator(phoneModel, standard);
		if ((null != list) && (list.size() > 0)) {
			for (Map<String, String> map : list) {
				Date curTime = new Date(System.currentTimeMillis());
				try {
					PhoneSupportOperator pso = new PhoneSupportOperator();
					pso.setCreateTime(curTime);
					pso.setOperatorStatus(OperatoryStatusConst.USED);
					pso.setOperatoryName(map.get("operator_name"));
					pso.setPhoneModel(map.get("phone_model"));
					pso.setSupportOperator(Integer.parseInt(map.get("id")));
					pso.setUpdateTime(curTime);
					pso.setUserNationCode(map.get("nation_code"));
					phoneSupportOperatorMapper.insertSelective(pso);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			bsm.setCode(ReqStatusConst.OK);
			return bsm;
		} else {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("手机支持的制式没有对应的运营商！");
			return bsm;
		}
	}

	@Override
	public BasicServiceModel<String> configOperatorSupportStandard(OperatorInfoBo bo) {
		// TODO Auto-generated method stub
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		try {
			OperatorInfo info = new OperatorInfo();
			BeanUtil.copyProperties(bo, info);
			int ret = operatorInfoMapper.insertSelective(info);
			if (ret == 1) {
				bsm.setCode(ReqStatusConst.OK);
				bsm.setData(String.valueOf(info.getId()));
				return bsm;
			} else {
				bsm.setCode(ReqStatusConst.FAIL);
				bsm.setMsg("运营商信息保存失败！");
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
	public int countPhoneModelSupportStandardByPage() {
		// TODO Auto-generated method stub
		return phoneSupportStandardMapper.countPhoneModelSupportStandardByPage();
	}

	@Override
	public BasicServiceModel<String> listPhoneModelSupportStandardByPage(int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		// TODO Auto-generated method stub
		int start = (pageIndex - 1) * pageSize;
		if (start < 0) {
			start = 0;
		}
		List<PhoneSupportStandard> list = phoneSupportStandardMapper.listPhoneModelSupportStandardByPage(start,
				pageSize);
		if (null != list) {
			List<PhoneSupportStandardBo> boList = new ArrayList<PhoneSupportStandardBo>();
			for (PhoneSupportStandard info : list) {
				PhoneSupportStandardBo bo = new PhoneSupportStandardBo();
				BeanUtils.copyProperties(info, bo);
				boList.add(bo);
			}
			JSONObject data = new JSONObject();
			data.put("data", boList);
			bsm.setData(data.toString());
		}
		bsm.setCode(ReqStatusConst.OK);

		return bsm;
	}

}
