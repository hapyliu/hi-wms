package com.haifeng.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haifeng.basedata.domain.BaseArea;
import com.haifeng.basedata.domain.BasePoint;
import com.haifeng.basedata.domain.BasePointLocation;
import com.haifeng.basedata.domain.BasePointStation;
import com.haifeng.basedata.mapper.BasePointMapper;
import com.haifeng.basedata.service.IBasePointLocationService;
import com.haifeng.basedata.service.IBasePointService;
import com.haifeng.basedata.service.IBasePointStationService;
import com.haifeng.common.exception.ServiceException;
import com.haifeng.common.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class BasePointServiceImpl extends ServiceImpl<BasePointMapper, BasePoint> implements IBasePointService {
    @Autowired
    private BasePointMapper mapper;
    @Autowired
    private IBasePointStationService pointStationService;
    @Autowired
    private IBasePointLocationService pointLocationService;

    @Override
    @Transactional
    public void insertPoint(BasePoint point) {
        if (!checkPointUnique(point)) {
            throw new ServiceException(MessageUtils.message("point.code.supplier.exits"));
        }
        mapper.insert(point);
        if (Objects.nonNull(point.getStationId())) {
            BasePointStation pointStation = new BasePointStation();
            pointStation.setPointId(point.getId());
            pointStation.setStationId(point.getStationId());
            pointStationService.insertPointStation(pointStation);
        }
    }

    @Override
    @Transactional
    public void updatePoint(BasePoint point) {
        BasePoint old = mapper.selectById(point.getId());
        if (old != null && (!old.getPointCode().equals(point.getPointCode())
                || !old.getAppCode().equals(point.getAppCode()))) {
            if (!checkPointUnique(point)) {
                throw new ServiceException(MessageUtils.message("point.code.supplier.exits"));
            }
        }
        mapper.updateById(point);
        BasePointStation deleteObj = new BasePointStation();
        deleteObj.setPointId(point.getId());
        pointStationService.deletePointStation(deleteObj);
        if (Objects.nonNull(point.getStationId())) {
            BasePointStation pointStation = new BasePointStation();
            pointStation.setPointId(point.getId());
            pointStation.setStationId(point.getStationId());
            pointStationService.insertPointStation(pointStation);
        }
    }

    @Override
    public void deletePoint(Long[] ids) {
        // 判断库位点位关联表是否有绑定关系存在，如果存在则提示解除绑定
        Arrays.stream(ids).forEach(id -> {
            if (pointLocationService.countByPointId(id) > 0) {
                throw new ServiceException(MessageUtils.message("point.delete.location.exits"));
            }
        });
        mapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public BasePoint selectPointById(Long id) {
        BasePoint point = mapper.selectById(id);
        if (point != null){
            BasePointStation pointStation = pointStationService.selectByPointId(point.getId());
            if (pointStation != null){
                point.setStationCode(pointStation.getStationCode());
                point.setStationId(pointStation.getStationId());
            }
        }
        return point;
    }

    @Override
    public List<BasePoint> selectPointList(BasePoint point) {
        QueryWrapper<BasePoint> wrapper = new QueryWrapper<>();
        if (point.getPointCode() != null) {
            wrapper.lambda().like(BasePoint::getPointCode, point.getPointCode());
        }
        if (point.getPointName() != null) {
            wrapper.lambda().like(BasePoint::getPointName, point.getPointName());
        }
        if (point.getPointType() != null) {
            wrapper.lambda().eq(BasePoint::getPointType, point.getPointType());
        }
        if (point.getAppCode() != null) {
            wrapper.lambda().eq(BasePoint::getAppCode, point.getAppCode());
        }
        wrapper.lambda().orderByDesc(BasePoint::getCreateTime);
        return mapper.selectList(wrapper);
    }

    public Boolean checkPointUnique(BasePoint point) {
        QueryWrapper<BasePoint> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BasePoint::getPointCode, point.getPointCode())
                .eq(BasePoint::getAppCode, point.getAppCode());
        return mapper.selectCount(wrapper) == 0;
    }

    @Override
    public String importPoint(List<BasePoint> pointList, boolean updateSupport, String operName) {
        if (pointList == null || pointList.isEmpty()) {
            throw new ServiceException(MessageUtils.message("point.import.empty"));
        }
        int successNum = 0;
        for (int i = 0; i < pointList.size(); i++) {
            BasePoint point = pointList.get(i);
            try {
                boolean exists = !checkPointUnique(point);
                if (!exists) {
                    point.setCreateBy(operName);
                    this.insertPoint(point);
                    successNum++;
                } else if (updateSupport) {
                    QueryWrapper<BasePoint> wrapper = new QueryWrapper<>();
                    wrapper.lambda().eq(BasePoint::getPointCode, point.getPointCode())
                            .eq(BasePoint::getAppCode, point.getAppCode());
                    BasePoint existPoint = mapper.selectOne(wrapper);
                    point.setId(existPoint.getId());
                    point.setUpdateBy(operName);
                    this.updatePoint(point);
                    successNum++;
                } else {
                    throw new ServiceException(
                            MessageUtils.message("point.import.exist.item", i + 1, point.getPointCode()));
                }
            } catch (Exception e) {
                // 判断如果是已提示的特定异常则原样抛出，否则包裹成带行号的异常
                if (e instanceof ServiceException) {
                    throw e;
                }
                throw new ServiceException(
                        MessageUtils.message("point.import.fail.item", i + 1, point.getPointCode(), e.getMessage()));
            }
        }
        return MessageUtils.message("point.import.success.total", successNum);
    }
}
