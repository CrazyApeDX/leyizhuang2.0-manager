package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.beust.jcommander.internal.Lists;
import com.ynyes.lyz.entity.AllocationTypeEnum;
import com.ynyes.lyz.entity.TdAllocation;
import com.ynyes.lyz.entity.TdAllocationCallRecord;
import com.ynyes.lyz.entity.TdAllocationDetail;
import com.ynyes.lyz.entity.TdAllocationQuery;
import com.ynyes.lyz.entity.TdAllocationTrail;
import com.ynyes.lyz.entity.TdDiySiteInventory;
import com.ynyes.lyz.entity.TdDiySiteInventoryLog;
import com.ynyes.lyz.interfaces.service.TdEbsSenderService;
import com.ynyes.lyz.repository.TdAllocationCallRecordRepo;
import com.ynyes.lyz.repository.TdAllocationDetailRepo;
import com.ynyes.lyz.repository.TdAllocationRepo;
import com.ynyes.lyz.repository.TdAllocationTrailRepo;
import com.ynyes.lyz.repository.TdDiySiteInventoryLogRepo;
import com.ynyes.lyz.repository.TdDiySiteInventoryRepo;
import com.ynyes.lyz.repository.TdManagerRepo;

@Service
public class TdAllocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TdAllocationService.class);

    @Autowired
    TdEbsSenderService tdEbsSenderService;

    @Autowired
    private TdAllocationRepo tdAllocationRepo;

    @Autowired
    private TdAllocationDetailRepo tdAllocationDetailRepo;

    @Autowired
    private TdAllocationTrailRepo tdAllocationTrailRepo;

    @Autowired
    private TdDiySiteInventoryRepo tdDiySiteInventoryRepo;

    @Autowired
    private TdDiySiteInventoryLogRepo tdDiySiteInventoryLogRepo;

    @Autowired
    TdManagerRepo tdManagerRepo;

    @Autowired
    private TdAllocationCallRecordRepo tdAllocationCallRecordRepo;

    @Transactional(value = TxType.REQUIRED)
    public TdAllocation insert(TdAllocation allocation, String operaterdBy) {
        Date now = new Date();
        allocation.setCreatedBy(operaterdBy);
        allocation.setCreatedTime(now);
        allocation.setUpdatedBy(operaterdBy);
        allocation.setUpdatedTime(now);
        allocation.setStatus(AllocationTypeEnum.NEW.getValue());
        allocation = tdAllocationRepo.save(allocation);

        List<TdAllocationDetail> details = Lists.newArrayList();
        Long id = allocation.getId();
        for (TdAllocationDetail detail : allocation.getDetails()) {
            if (null != detail.getGoodId()) {
                detail.setAllocationId(id);
                detail.setRealNum(detail.getNum());
                details.add(detail);
            }
        }
        tdAllocationDetailRepo.save(details);
        allocation = tdAllocationRepo.save(allocation);

        TdAllocationTrail trail = new TdAllocationTrail();
        trail.setAllocationId(id);
        trail.setOperatedBy(operaterdBy);
        trail.setOperatedTime(now);
        trail.setOperation(AllocationTypeEnum.NEW.getName());
        tdAllocationTrailRepo.save(trail);
        return allocation;
    }

    @Transactional(value = TxType.REQUIRED)
    public void update(TdAllocation allocation, String operaterdBy) {
        Date now = new Date();
        Long id = allocation.getId();
        allocation.setUpdatedBy(operaterdBy);
        allocation.setUpdatedTime(now);
        allocation = tdAllocationRepo.save(allocation);

        tdAllocationDetailRepo.deleteByAllocationId(id);
        for (TdAllocationDetail detail : allocation.getDetails()) {
            detail.setAllocationId(id);
        }
        tdAllocationDetailRepo.save(allocation.getDetails());

        List<TdAllocationTrail> trails = tdAllocationTrailRepo.findByAllocationId(id);
        if (null != trails && trails.size() > 0) {
            TdAllocationTrail trail = trails.get(0);
            trail.setOperatedBy(operaterdBy);
            trail.setOperatedTime(now);
            tdAllocationTrailRepo.save(trail);
        }
    }

    /**
     * 获取一条门店调拨记录，包括调拨产品明细和调拨轨迹
     * 
     * @param id
     * @return
     */
    public TdAllocation findOne(Long id) {
        if (null == id) {
            return null;
        }
        TdAllocation allocation = tdAllocationRepo.findOne(id);
        allocation.setDetails(tdAllocationDetailRepo.findByAllocationId(id));
        allocation.setTrails(tdAllocationTrailRepo.findByAllocationId(id));
        return allocation;
    }

    /**
     * 获取一条门店调拨记录
     * 
     * @param id
     * @return
     */
    public TdAllocation get(Long id) {
        if (null == id) {
            return null;
        }
        return tdAllocationRepo.findOne(id);
    }

    /**
     * 以分页方式查询门店调拨记录
     * 
     * @param page
     * @param size
     * @param condition
     * @return
     */
    public Page<TdAllocation> findAllocation(int page, int size, TdAllocationQuery condition) {
        List<Order> orders = Lists.newArrayList();
        orders.add(new Order(Direction.ASC, "status"));
        orders.add(new Order(Direction.DESC, "updatedTime"));
        Specification<TdAllocation> spec = buildSpecification(condition);
        return tdAllocationRepo.findAll(spec, new PageRequest(page, size, new Sort(orders)));
    }

    /**
     * 创建动态查询条件组合.
     */
    private Specification<TdAllocation> buildSpecification(final TdAllocationQuery allocation) {
        Specification<TdAllocation> spec = new Specification<TdAllocation>() {
            public Predicate toPredicate(Root<TdAllocation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Integer allocationType = allocation.getAllocationType();
                List<Predicate> list = Lists.newArrayList();

                if (null != allocationType) {
                    if (1 == allocationType) {
                        if (null == allocation.getDiySiteId()) {
                            list.add(cb.and(root.get("allocationFrom").as(Long.class).in(allocation.getDiySiteIds())));
                        } else {
                            list.add(cb.and(cb.equal(root.get("allocationFrom").as(Long.class), allocation.getDiySiteId())));
                        }
                    } else {
                        if (null == allocation.getDiySiteId()) {
                            list.add(cb.and(root.get("allocationTo").as(Long.class).in(allocation.getDiySiteIds())));
                        } else {
                            list.add(cb.and(cb.equal(root.get("allocationTo").as(Long.class), allocation.getDiySiteId())));
                        }
                    }
                } else {
                    if (null == allocation.getDiySiteId()) {
                        Predicate pre1 = cb.or(root.get("allocationFrom").as(Long.class).in(allocation.getDiySiteIds()));
                        Predicate pre2 = cb.or(root.get("allocationTo").as(Long.class).in(allocation.getDiySiteIds()));
                        list.add(cb.and(cb.or(pre1, pre2)));
                    } else {
                        Predicate pre1 = cb.equal(root.get("allocationFrom").as(Long.class), allocation.getDiySiteId());
                        Predicate pre2 = cb.equal(root.get("allocationTo").as(Long.class), allocation.getDiySiteId());
                        list.add(cb.and(cb.or(pre1, pre2)));
                    }
                }

                if (null != allocation.getStatus()) {
                    list.add(cb.and(cb.equal(root.get("status").as(Integer.class), allocation.getStatus())));
                }

                if (null != allocation.getStartTime()) {
                    list.add(cb.and(cb.greaterThanOrEqualTo(root.get("updatedTime").as(Date.class), allocation.getStartTime())));
                }

                if (null != allocation.getEndTime()) {
                    list.add(cb.and(cb.lessThanOrEqualTo(root.get("updatedTime").as(Date.class), allocation.getEndTime())));
                }

                if (StringUtils.isNotBlank(allocation.getNumber())) {
                    list.add(cb.and(cb.like(root.get("number").as(String.class), "%" + allocation.getNumber() + "%")));
                }

                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        return spec;
    }

    /**
     * 取消调拨单
     * 
     * @param id
     * @param username
     */
    @Transactional(value = TxType.REQUIRED)
    public void cancel(TdAllocation allocation, String username) {
        Date now = new Date();
        allocation.setUpdatedBy(username);
        allocation.setUpdatedTime(now);
        allocation.setStatus(AllocationTypeEnum.CANCELLED.getValue());
        allocation = tdAllocationRepo.save(allocation);

        TdAllocationTrail trail = new TdAllocationTrail();
        trail.setAllocationId(allocation.getId());
        trail.setOperatedBy(username);
        trail.setOperatedTime(now);
        trail.setOperation(AllocationTypeEnum.CANCELLED.getName());
        tdAllocationTrailRepo.save(trail);
    }

    /**
     * 调拨单出库
     * 
     * @param id
     * @param detailsJson
     * @param username
     * @throws Exception
     */
    @Transactional(value = TxType.REQUIRED)
    public void send(TdAllocation allocation, String realNums, String username) throws Exception {
        List<Long> nums = JSONArray.parseArray(realNums, Long.class);
        List<TdAllocationDetail> details = tdAllocationDetailRepo.findByAllocationId(allocation.getId());
        boolean changeNumFlag = false;
        for (int i = 0; i < details.size(); i++) {
            TdAllocationDetail detail = details.get(i);
            if (detail.getNum() != nums.get(i)) {
                detail.setRealNum(nums.get(i));
                changeNumFlag = true;
            }
        }

        // 修改本地
        Date now = new Date();
        for (TdAllocationDetail detail : details) {
            int count = tdDiySiteInventoryRepo.updateInventory(allocation.getAllocationFrom(), detail.getGoodId(),
                    -detail.getRealNum());
            if (count > 0) {
                TdDiySiteInventory tdDiySiteInventory = tdDiySiteInventoryRepo.findByGoodsIdAndDiySiteId(detail.getGoodId(),
                        allocation.getAllocationFrom());
                if (tdDiySiteInventory != null && tdDiySiteInventory.getInventory() != null
                        && tdDiySiteInventory.getInventory() >= 0) {
                    TdDiySiteInventoryLog tdDiySiteInventoryLog = new TdDiySiteInventoryLog();
                    tdDiySiteInventoryLog.setAfterChange(tdDiySiteInventory.getInventory());
                    tdDiySiteInventoryLog.setChangeDate(now);
                    tdDiySiteInventoryLog.setChangeType("门店调拨出库");
                    tdDiySiteInventoryLog.setChangeValue(-detail.getRealNum());
                    tdDiySiteInventoryLog.setDiySiteId(allocation.getAllocationFrom());
                    tdDiySiteInventoryLog.setDiySiteTitle(allocation.getAllocationFromName());
                    tdDiySiteInventoryLog.setGoodsId(detail.getGoodId());
                    tdDiySiteInventoryLog.setGoodsSku(detail.getGoodSku());
                    tdDiySiteInventoryLog.setGoodsTitle(detail.getGoodTitle());
                    tdDiySiteInventoryLog.setManager(username);
                    tdDiySiteInventoryLog.setRegionId(tdDiySiteInventory.getRegionId());
                    tdDiySiteInventoryLog.setRegionName(allocation.getCityName());
                    tdDiySiteInventoryLog.setOrderNumber(allocation.getNumber());
                    tdDiySiteInventoryLog.setDescription("门店调拨");
                    tdDiySiteInventoryLogRepo.save(tdDiySiteInventoryLog);
                } else {
                    throw new RuntimeException(detail.getGoodSku() + "产品库存不足，不允许调拨");
                }
            } else {
                throw new RuntimeException(detail.getGoodSku() + "产品库存不足，不允许调拨");
            }
        }
        if (changeNumFlag) {
            tdAllocationDetailRepo.save(details);
        }

        allocation.setUpdatedBy(username);
        allocation.setUpdatedTime(now);
        allocation.setStatus(AllocationTypeEnum.SENT.getValue());
        allocation = tdAllocationRepo.save(allocation);

        TdAllocationTrail trail = new TdAllocationTrail();
        trail.setAllocationId(allocation.getId());
        trail.setOperatedBy(username);
        trail.setOperatedTime(now);
        trail.setOperation(AllocationTypeEnum.SENT.getName());
        tdAllocationTrailRepo.save(trail);

        // 异步调用EBS，通知库存变化
        allocation.setDetails(details);
        tdEbsSenderService.sendAllocationToEBSAndRecord(allocation);
    }

    /**
     * 调拨单入库
     * 
     * @param id
     * @param username
     */
    @Transactional(value = TxType.REQUIRED)
    public void receive(TdAllocation allocation, String username) {
        // 修改本地
        Date now = new Date();
        for (TdAllocationDetail detail : tdAllocationDetailRepo.findByAllocationId(allocation.getId())) {
            // 更新库存表
            int count = tdDiySiteInventoryRepo.updateInventory(allocation.getAllocationTo(), detail.getGoodId(),
                    detail.getRealNum());
            TdDiySiteInventory tdDiySiteInventory;
            // 无此商品库存
            if (count == 0) {
                tdDiySiteInventory = this.copyTdDiySiteInventoryFromAnother(
                        tdDiySiteInventoryRepo.findByGoodsIdAndDiySiteId(detail.getGoodId(), allocation.getAllocationFrom()));
                tdDiySiteInventory.setDiySiteId(allocation.getAllocationTo());
                tdDiySiteInventory.setDiyCode(tdManagerRepo.findByUsernameAndIsEnableTrue(username).getDiyCode());
                tdDiySiteInventory.setDiySiteName(allocation.getAllocationToName());
                tdDiySiteInventory.setInventory(detail.getRealNum());
                tdDiySiteInventoryRepo.save(tdDiySiteInventory);
            } else {
                tdDiySiteInventory = tdDiySiteInventoryRepo.findByGoodsIdAndDiySiteId(detail.getGoodId(),
                        allocation.getAllocationTo());
            }

            TdDiySiteInventoryLog tdDiySiteInventoryLog = new TdDiySiteInventoryLog();
            tdDiySiteInventoryLog.setAfterChange(tdDiySiteInventory.getInventory());
            tdDiySiteInventoryLog.setChangeDate(now);
            tdDiySiteInventoryLog.setChangeType("门店调拨入库");
            tdDiySiteInventoryLog.setChangeValue(detail.getRealNum());
            tdDiySiteInventoryLog.setDiySiteId(allocation.getAllocationTo());
            tdDiySiteInventoryLog.setDiySiteTitle(allocation.getAllocationToName());
            tdDiySiteInventoryLog.setGoodsId(detail.getGoodId());
            tdDiySiteInventoryLog.setGoodsSku(detail.getGoodSku());
            tdDiySiteInventoryLog.setGoodsTitle(detail.getGoodTitle());
            tdDiySiteInventoryLog.setManager(username);
            tdDiySiteInventoryLog.setRegionId(tdDiySiteInventory.getRegionId());
            tdDiySiteInventoryLog.setRegionName(allocation.getCityName());
            tdDiySiteInventoryLog.setOrderNumber(allocation.getNumber());
            tdDiySiteInventoryLog.setDescription("门店调拨");
            tdDiySiteInventoryLogRepo.save(tdDiySiteInventoryLog);
        }

        allocation.setUpdatedBy(username);
        allocation.setUpdatedTime(now);
        allocation.setStatus(AllocationTypeEnum.ENTERED.getValue());
        allocation = tdAllocationRepo.save(allocation);

        TdAllocationTrail trail = new TdAllocationTrail();
        trail.setAllocationId(allocation.getId());
        trail.setOperatedBy(username);
        trail.setOperatedTime(now);
        trail.setOperation(AllocationTypeEnum.ENTERED.getName());
        tdAllocationTrailRepo.save(trail);

        // 异步调用EBS，通知库存变化
        tdEbsSenderService.sendAllocationReceivedToEBSAndRecord(allocation);
    }

    /**
     * 将失败的调拨记录重新发送到EBS
     * 
     */
    @Transactional(value = TxType.NOT_SUPPORTED)
    public void resendAllAllocation() {
        // 失败的调拨单ID集合
        List<Long> faildIds = Lists.newArrayList();

        // 查找所有失败的调拨单头记录和商品记录
        List<TdAllocationCallRecord> headerRecords = tdAllocationCallRecordRepo.findByType(1);
        LOGGER.info("Resend allocaton header record, count=" + headerRecords.size());
        for (TdAllocationCallRecord record : headerRecords) {
            Map<String, Object> result = tdEbsSenderService.sendFaildAllocationToEBS(record);
            if ((Boolean) result.get("success")) {
                tdAllocationCallRecordRepo.delete(record.getId());
            } else {
                Date now = new Date();
                record.setUpdatedTime(now);
                record.setMsg((String) result.get("msg"));
                record.setTimes(record.getTimes() + 1);
                tdAllocationCallRecordRepo.save(record);
                faildIds.add(record.getAllocationId());
            }
        }
        
        // 查找所有失败的调拨入库记录
        List<TdAllocationCallRecord> receiveRecords = tdAllocationCallRecordRepo.findByType(3);
        LOGGER.info("Resend allocaton receive record, count=" + receiveRecords.size());
        for (TdAllocationCallRecord record : receiveRecords) {
            if (!faildIds.contains(record.getAllocationId())) {
                Map<String, Object> result = tdEbsSenderService.sendFaildAllocationToEBS(record);
                if ((Boolean) result.get("success")) {
                    tdAllocationCallRecordRepo.delete(record.getId());
                } else {
                    Date now = new Date();
                    record.setUpdatedTime(now);
                    record.setMsg((String) result.get("msg"));
                    record.setTimes(record.getTimes() + 1);
                    tdAllocationCallRecordRepo.save(record);
                    faildIds.add(record.getAllocationId());
                }
            }
        }
        LOGGER.info("Resend allocaton done, faild allocation ids=" + faildIds);
    }

    /**
     * 从门店库存记录创建一条新的门店库存记录
     * 
     * @param from
     * @return
     */
    private TdDiySiteInventory copyTdDiySiteInventoryFromAnother(TdDiySiteInventory from) {
        TdDiySiteInventory tdDiySiteInventory = new TdDiySiteInventory();
        tdDiySiteInventory.setCategoryId(from.getCategoryId());
        tdDiySiteInventory.setCategoryIdTree(from.getCategoryIdTree());
        tdDiySiteInventory.setCategoryTitle(from.getCategoryTitle());
        tdDiySiteInventory.setGoodsCode(from.getGoodsCode());
        tdDiySiteInventory.setGoodsId(from.getGoodsId());
        tdDiySiteInventory.setGoodsTitle(from.getGoodsTitle());
        tdDiySiteInventory.setRegionId(from.getRegionId());
        tdDiySiteInventory.setRegionName(from.getRegionName());
        return tdDiySiteInventory;
    }
}
