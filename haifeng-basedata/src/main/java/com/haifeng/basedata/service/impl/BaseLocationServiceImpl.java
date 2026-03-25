package com.haifeng.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haifeng.basedata.domain.*;
import com.haifeng.basedata.dto.BaseLocationOprDTO;
import com.haifeng.basedata.mapper.BaseLocationMapper;
import com.haifeng.basedata.service.*;
import com.haifeng.common.enums.BaseLocationOperationEnum;
import com.haifeng.common.enums.PositionTypeEnum;
import com.haifeng.common.exception.ServiceException;
import com.haifeng.common.utils.MessageUtils;
import com.haifeng.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class BaseLocationServiceImpl extends ServiceImpl<BaseLocationMapper, BaseLocation>
        implements IBaseLocationService {
    @Autowired
    private BaseLocationMapper mapper;
    @Autowired
    private IBaseLocationLockService lockService;
    @Autowired
    private IBaseLocationContainerService locationContainerService;
    @Autowired
    private IBaseContainerService containerService;
    @Autowired
    private IBaseLocationStationService locationStationService;
    @Autowired
    private IBasePointLocationService pointLocationService;
    @Autowired
    private IBaseStationService stationService;
    @Autowired
    private IBasePointService pointService;

    @Override
    @Transactional
    public void insertLocation(BaseLocation location) {
        checkLocationCodeUnique(location);
        if (StringUtils.isBlank(location.getAllowContainerModel())) {
            throw new ServiceException(MessageUtils.message("location.allow.container.model.absence"));
        }
        mapper.insert(location);

        if (StringUtils.isNotBlank(location.getStationId())) {// 关联工作站
            insertLocationStation(location);
        }
        if (location.getPointLocationList() != null && !location.getPointLocationList().isEmpty()) {// 关联点位
            insertPointLocation(location);
        }
    }

    private void insertLocationStation(BaseLocation location) {
        BaseLocationStation locationStation = new BaseLocationStation();
        locationStation.setLocationId(location.getId());
        locationStation.setStationId(Long.valueOf(location.getStationId()));
        locationStationService.insertLocationStation(locationStation);
    }

    private void insertPointLocation(BaseLocation location) {
        for (BasePointLocation pointLocation : location.getPointLocationList()) {
            pointLocation.setLocationId(location.getId());
            pointLocationService.insertPointLocation(pointLocation);
        }
    }

    @Override
    @Transactional
    public void updateLocation(BaseLocation location) {
        checkLocationCodeUnique(location);
        mapper.updateById(location);
        // 修改时判断工作站库位关联
        BaseLocationStation locationStation = new BaseLocationStation();
        locationStation.setLocationId(location.getId());
        locationStationService.deleteLocationStation(locationStation);
        if (StringUtils.isNotBlank(location.getStationId())) {
            insertLocationStation(location);
        }
        // 修改时判断点位库位关联
        pointLocationService.deleteByLocationId(location.getId());
        if (location.getPointLocationList() != null && !location.getPointLocationList().isEmpty()) {
            insertPointLocation(location);
        }
    }

    @Override
    public void deleteLocation(Long[] ids) {
        for (Long id : ids) {
            BaseLocation location = mapper.selectById(id);
            if (location != null) {
                List<BaseLocationContainer> containers = locationContainerService.selectLocationContainerList(BaseLocationContainer.builder().locationCode(location.getLocationCode()).build());
                if (containers != null && !containers.isEmpty()) {
                    throw new ServiceException(MessageUtils.message("location.container.exists.forbid.delete"));
                }
            }
        }
        mapper.deleteByIds(Arrays.asList(ids));
    }

    @Override
    public BaseLocation selectLocationById(Long id) {
        BaseLocation location = mapper.selectById(id);
        if (location != null) {
            // 查出工作站库位关联信息
            BaseLocationStation paramLocationStation = new BaseLocationStation();
            paramLocationStation.setLocationId(id);
            List<BaseLocationStation> stationList = locationStationService.selectLocationStationList(paramLocationStation);
            if (stationList != null && !stationList.isEmpty()) {
                Long stationId = stationList.getFirst().getStationId();
                location.setStationId(String.valueOf(stationId));
                BaseStation station = stationService.selectStationById(stationId);
                if (station != null) {
                    location.setStationCode(station.getStationCode());
                }
            }

            // 查出点位库位关联信息
            BasePointLocation paramPointLocation = new BasePointLocation();
            paramPointLocation.setLocationId(id);
            List<BasePointLocation> pointLocationList = pointLocationService.selectPointLocationList(paramPointLocation);
            if (pointLocationList != null && !pointLocationList.isEmpty()) {
                for (BasePointLocation ppl : pointLocationList) {
                    if (ppl.getPointId() != null) {
                        BasePoint point = pointService.selectPointById(ppl.getPointId());
                        if (point != null) {
                            ppl.setAppCode(point.getAppCode());
                            ppl.setPointCode(point.getPointCode());
                        }
                    }
                }
            }
            location.setPointLocationList(pointLocationList);
        }
        return location;
    }

    @Override
    public List<BaseLocation> selectLocationList(BaseLocation location) {
        return mapper.selectLocationList(location);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void locationOpr(BaseLocationOprDTO oprDTO) {
        log.info("库位统一操作入参{}", oprDTO.toString());
        locOprVerify(oprDTO);
        executeLocOpr(oprDTO);
        if (oprDTO.getIsNotify()) {
            // 通知下游。。。。。（后面补）
        }
    }

    private void locOprVerify(BaseLocationOprDTO oprDTO) {
        // 校验库位号和库位操作
        if (StringUtils.isBlank(oprDTO.getLocationCode()) || Objects.isNull(oprDTO.getOpr())) {
            throw new ServiceException(MessageUtils.message("location.opr.data.absence"));
        }
        QueryWrapper<BaseLocation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BaseLocation::getLocationCode, oprDTO.getLocationCode());
        BaseLocation location = mapper.selectOne(wrapper);
        if (location == null) {
            throw new ServiceException(MessageUtils.message("location.code.not.exist"));
        }
        // 接绑定时需要校验容器号
        if (oprDTO.getOpr() == BaseLocationOperationEnum.unbind
                || oprDTO.getOpr() == BaseLocationOperationEnum.unbindAndUnlock
                || oprDTO.getOpr() == BaseLocationOperationEnum.unbindAndLock) {
            validateContainerCode(oprDTO.getContainerCode(), oprDTO.getOpr());
        }
        // 锁定时需要校验锁定源
        if (oprDTO.getOpr() == BaseLocationOperationEnum.lock
                || oprDTO.getOpr() == BaseLocationOperationEnum.unbindAndLock
                || oprDTO.getOpr() == BaseLocationOperationEnum.bindAndLock) {
            validateLockSource(oprDTO.getLockSource(), oprDTO.getOpr());
        }
        // 绑定需要校容器号，容器准入模型
        if (oprDTO.getOpr() == BaseLocationOperationEnum.bind
                || oprDTO.getOpr() == BaseLocationOperationEnum.bindAndLock
                || oprDTO.getOpr() == BaseLocationOperationEnum.bindAndUnlock) {
            validateContainerCode(oprDTO.getContainerCode(), oprDTO.getOpr());
            BaseContainer container = containerService.selectContainerByCode(oprDTO.getContainerCode());
            if (container == null) {
                throw new ServiceException(MessageUtils.message("container.code.not.exist"));
            }
            String containerModelCode = container.getContainerModelCode();
            String containerModel = location.getAllowContainerModel();
            if (StringUtils.isBlank(containerModel)) {
                throw new ServiceException(MessageUtils.message("location.allow.container.model.absence"));
            }
            List<String> containerModelList = StringUtils.str2List(containerModel, ",");
            if (!containerModelList.contains(containerModelCode)) {
                throw new ServiceException(MessageUtils.message("location.allow.container.model.not.match"));
            }
        }

    }

    private void executeLocOpr(BaseLocationOprDTO oprDTO) {
        BaseLocationOperationEnum operation = oprDTO.getOpr();
        String locationCode = oprDTO.getLocationCode();
        String containerCode = oprDTO.getContainerCode();
        String lockSource = oprDTO.getLockSource();

        switch (operation) {
            case BaseLocationOperationEnum.bind:
                updateCtrLoc(locationCode, containerCode,false);
                locationContainerService.insertLocationContainer(buildLocationContainer(locationCode, containerCode));
                break;

            case BaseLocationOperationEnum.unbind:
                updateCtrLoc(locationCode, containerCode,true);
                locationContainerService.deleteLocationContainer(buildLocationContainer(locationCode, containerCode));
                break;

            case BaseLocationOperationEnum.lock:
                lockService.insertLocationLock(buildLocationLock(locationCode, lockSource));
                break;

            case BaseLocationOperationEnum.unlock:
                lockService.deleteLocationLock(buildLocationLock(locationCode, lockSource));
                break;

            case BaseLocationOperationEnum.bindAndLock:
                updateCtrLoc(locationCode, containerCode,false);
                locationContainerService.insertLocationContainer(buildLocationContainer(locationCode, containerCode));
                lockService.insertLocationLock(buildLocationLock(locationCode, lockSource));
                break;

            case BaseLocationOperationEnum.bindAndUnlock:
                updateCtrLoc(locationCode, containerCode,false);
                locationContainerService.insertLocationContainer(buildLocationContainer(locationCode, containerCode));
                lockService.deleteLocationLock(buildLocationLock(locationCode, lockSource));
                break;

            case BaseLocationOperationEnum.unbindAndUnlock:
                updateCtrLoc(locationCode, containerCode,true);
                locationContainerService.deleteLocationContainer(buildLocationContainer(locationCode, containerCode));
                lockService.deleteLocationLock(buildLocationLock(locationCode, lockSource));
                break;

            case BaseLocationOperationEnum.unbindAndLock:
                updateCtrLoc(locationCode, containerCode,true);
                locationContainerService.deleteLocationContainer(buildLocationContainer(locationCode, containerCode));
                lockService.insertLocationLock(buildLocationLock(locationCode, lockSource));
                break;

            default:
                log.error("库位不支持此操作类型：{}", operation);
        }
        log.info("库位操作成功完成，位置编码：{}，操作类型：{}", locationCode, operation);
    }

    private void updateCtrLoc(String locationCode, String containerCode,Boolean isClean){

        if (isClean){
            BaseContainer baseContainer = new BaseContainer();
            baseContainer.setContainerCode(containerCode);
            baseContainer.setStationCode( null);
            baseContainer.setPositionCode(null);
            baseContainer.setPositionType(null);
            containerService.updateContainerByCtrCode(baseContainer);
        }else {

            BaseContainer baseContainer = new BaseContainer();
            QueryWrapper<BaseLocation> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(BaseLocation::getLocationCode,locationCode);
            BaseLocation location = mapper.selectOne(wrapper);
            List<BaseLocationStation> stationList = locationStationService.selectLocationStationList(BaseLocationStation.builder().locationId(location.getId()).build());
            if (!stationList.isEmpty()){
                BaseLocationStation baseLocationStation = stationList.getFirst();
                BaseStation station = stationService.selectStationById(baseLocationStation.getStationId());
                baseContainer.setStationCode(station.getStationCode());
            }
            baseContainer.setContainerCode(containerCode);
            baseContainer.setPositionCode(locationCode);
            baseContainer.setPositionType(PositionTypeEnum.LOCATION.getCode());
            containerService.updateContainerByCtrCode(baseContainer);
        }
    }

    private BaseLocationContainer buildLocationContainer(String locationCode, String containerCode) {
        return BaseLocationContainer.builder()
                .locationCode(locationCode)
                .containerCode(containerCode)
                .build();
    }

    private BaseLocationLock buildLocationLock(String locationCode, String lockSource) {
        return BaseLocationLock.builder()
                .locationCode(locationCode)
                .lockSource(lockSource)
                .build();
    }

    private void validateContainerCode(String containerCode, BaseLocationOperationEnum operation) {
        if (StringUtils.isBlank(containerCode)) {
            throw new ServiceException(MessageUtils.message("location.container.code.absence", operation));
        }
    }

    private void validateLockSource(String lockSource, BaseLocationOperationEnum operation) {
        if (StringUtils.isBlank(lockSource)) {
            throw new ServiceException(MessageUtils.message("location.lock.source.absence", operation));
        }
    }

    private void checkLocationCodeUnique(BaseLocation location) {
        if (StringUtils.isBlank(location.getLocationCode())) {
            return;
        }
        QueryWrapper<BaseLocation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BaseLocation::getLocationCode, location.getLocationCode());
        if (location.getId() != null) {
            wrapper.lambda().ne(BaseLocation::getId, location.getId());
        }
        if (mapper.selectCount(wrapper) > 0) {
            throw new ServiceException(MessageUtils.message("location.code.exists"));
        }
    }

}
