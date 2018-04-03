package com.idc.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CostDynamicExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CostDynamicExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andCostRoomIdIsNull() {
            addCriterion("COST_ROOM_ID is null");
            return (Criteria) this;
        }

        public Criteria andCostRoomIdIsNotNull() {
            addCriterion("COST_ROOM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCostRoomIdEqualTo(String value) {
            addCriterion("COST_ROOM_ID =", value, "costRoomId");
            return (Criteria) this;
        }

        public Criteria andCostRoomIdNotEqualTo(String value) {
            addCriterion("COST_ROOM_ID <>", value, "costRoomId");
            return (Criteria) this;
        }

        public Criteria andCostRoomIdGreaterThan(String value) {
            addCriterion("COST_ROOM_ID >", value, "costRoomId");
            return (Criteria) this;
        }

        public Criteria andCostRoomIdGreaterThanOrEqualTo(String value) {
            addCriterion("COST_ROOM_ID >=", value, "costRoomId");
            return (Criteria) this;
        }

        public Criteria andCostRoomIdLessThan(String value) {
            addCriterion("COST_ROOM_ID <", value, "costRoomId");
            return (Criteria) this;
        }

        public Criteria andCostRoomIdLessThanOrEqualTo(String value) {
            addCriterion("COST_ROOM_ID <=", value, "costRoomId");
            return (Criteria) this;
        }

        public Criteria andCostRoomIdLike(String value) {
            addCriterion("COST_ROOM_ID like", value, "costRoomId");
            return (Criteria) this;
        }

        public Criteria andCostRoomIdNotLike(String value) {
            addCriterion("COST_ROOM_ID not like", value, "costRoomId");
            return (Criteria) this;
        }

        public Criteria andCostRoomIdIn(List<String> values) {
            addCriterion("COST_ROOM_ID in", values, "costRoomId");
            return (Criteria) this;
        }

        public Criteria andCostRoomIdNotIn(List<String> values) {
            addCriterion("COST_ROOM_ID not in", values, "costRoomId");
            return (Criteria) this;
        }

        public Criteria andCostRoomIdBetween(String value1, String value2) {
            addCriterion("COST_ROOM_ID between", value1, value2, "costRoomId");
            return (Criteria) this;
        }

        public Criteria andCostRoomIdNotBetween(String value1, String value2) {
            addCriterion("COST_ROOM_ID not between", value1, value2, "costRoomId");
            return (Criteria) this;
        }

        public Criteria andCostFixedIdIsNull() {
            addCriterion("COST_FIXED_ID is null");
            return (Criteria) this;
        }

        public Criteria andCostFixedIdIsNotNull() {
            addCriterion("COST_FIXED_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCostFixedIdEqualTo(String value) {
            addCriterion("COST_FIXED_ID =", value, "costFixedId");
            return (Criteria) this;
        }

        public Criteria andCostFixedIdNotEqualTo(String value) {
            addCriterion("COST_FIXED_ID <>", value, "costFixedId");
            return (Criteria) this;
        }

        public Criteria andCostFixedIdGreaterThan(String value) {
            addCriterion("COST_FIXED_ID >", value, "costFixedId");
            return (Criteria) this;
        }

        public Criteria andCostFixedIdGreaterThanOrEqualTo(String value) {
            addCriterion("COST_FIXED_ID >=", value, "costFixedId");
            return (Criteria) this;
        }

        public Criteria andCostFixedIdLessThan(String value) {
            addCriterion("COST_FIXED_ID <", value, "costFixedId");
            return (Criteria) this;
        }

        public Criteria andCostFixedIdLessThanOrEqualTo(String value) {
            addCriterion("COST_FIXED_ID <=", value, "costFixedId");
            return (Criteria) this;
        }

        public Criteria andCostFixedIdLike(String value) {
            addCriterion("COST_FIXED_ID like", value, "costFixedId");
            return (Criteria) this;
        }

        public Criteria andCostFixedIdNotLike(String value) {
            addCriterion("COST_FIXED_ID not like", value, "costFixedId");
            return (Criteria) this;
        }

        public Criteria andCostFixedIdIn(List<String> values) {
            addCriterion("COST_FIXED_ID in", values, "costFixedId");
            return (Criteria) this;
        }

        public Criteria andCostFixedIdNotIn(List<String> values) {
            addCriterion("COST_FIXED_ID not in", values, "costFixedId");
            return (Criteria) this;
        }

        public Criteria andCostFixedIdBetween(String value1, String value2) {
            addCriterion("COST_FIXED_ID between", value1, value2, "costFixedId");
            return (Criteria) this;
        }

        public Criteria andCostFixedIdNotBetween(String value1, String value2) {
            addCriterion("COST_FIXED_ID not between", value1, value2, "costFixedId");
            return (Criteria) this;
        }

        public Criteria andCostDepreciationIsNull() {
            addCriterion("COST_DEPRECIATION is null");
            return (Criteria) this;
        }

        public Criteria andCostDepreciationIsNotNull() {
            addCriterion("COST_DEPRECIATION is not null");
            return (Criteria) this;
        }

        public Criteria andCostDepreciationEqualTo(BigDecimal value) {
            addCriterion("COST_DEPRECIATION =", value, "costDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostDepreciationNotEqualTo(BigDecimal value) {
            addCriterion("COST_DEPRECIATION <>", value, "costDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostDepreciationGreaterThan(BigDecimal value) {
            addCriterion("COST_DEPRECIATION >", value, "costDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostDepreciationGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_DEPRECIATION >=", value, "costDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostDepreciationLessThan(BigDecimal value) {
            addCriterion("COST_DEPRECIATION <", value, "costDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostDepreciationLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_DEPRECIATION <=", value, "costDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostDepreciationIn(List<BigDecimal> values) {
            addCriterion("COST_DEPRECIATION in", values, "costDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostDepreciationNotIn(List<BigDecimal> values) {
            addCriterion("COST_DEPRECIATION not in", values, "costDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostDepreciationBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_DEPRECIATION between", value1, value2, "costDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostDepreciationNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_DEPRECIATION not between", value1, value2, "costDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceIsNull() {
            addCriterion("COST_MAINTENANCE is null");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceIsNotNull() {
            addCriterion("COST_MAINTENANCE is not null");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceEqualTo(BigDecimal value) {
            addCriterion("COST_MAINTENANCE =", value, "costMaintenance");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceNotEqualTo(BigDecimal value) {
            addCriterion("COST_MAINTENANCE <>", value, "costMaintenance");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceGreaterThan(BigDecimal value) {
            addCriterion("COST_MAINTENANCE >", value, "costMaintenance");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_MAINTENANCE >=", value, "costMaintenance");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceLessThan(BigDecimal value) {
            addCriterion("COST_MAINTENANCE <", value, "costMaintenance");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_MAINTENANCE <=", value, "costMaintenance");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceIn(List<BigDecimal> values) {
            addCriterion("COST_MAINTENANCE in", values, "costMaintenance");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceNotIn(List<BigDecimal> values) {
            addCriterion("COST_MAINTENANCE not in", values, "costMaintenance");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_MAINTENANCE between", value1, value2, "costMaintenance");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_MAINTENANCE not between", value1, value2, "costMaintenance");
            return (Criteria) this;
        }

        public Criteria andCostRepairIsNull() {
            addCriterion("COST_REPAIR is null");
            return (Criteria) this;
        }

        public Criteria andCostRepairIsNotNull() {
            addCriterion("COST_REPAIR is not null");
            return (Criteria) this;
        }

        public Criteria andCostRepairEqualTo(BigDecimal value) {
            addCriterion("COST_REPAIR =", value, "costRepair");
            return (Criteria) this;
        }

        public Criteria andCostRepairNotEqualTo(BigDecimal value) {
            addCriterion("COST_REPAIR <>", value, "costRepair");
            return (Criteria) this;
        }

        public Criteria andCostRepairGreaterThan(BigDecimal value) {
            addCriterion("COST_REPAIR >", value, "costRepair");
            return (Criteria) this;
        }

        public Criteria andCostRepairGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_REPAIR >=", value, "costRepair");
            return (Criteria) this;
        }

        public Criteria andCostRepairLessThan(BigDecimal value) {
            addCriterion("COST_REPAIR <", value, "costRepair");
            return (Criteria) this;
        }

        public Criteria andCostRepairLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_REPAIR <=", value, "costRepair");
            return (Criteria) this;
        }

        public Criteria andCostRepairIn(List<BigDecimal> values) {
            addCriterion("COST_REPAIR in", values, "costRepair");
            return (Criteria) this;
        }

        public Criteria andCostRepairNotIn(List<BigDecimal> values) {
            addCriterion("COST_REPAIR not in", values, "costRepair");
            return (Criteria) this;
        }

        public Criteria andCostRepairBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_REPAIR between", value1, value2, "costRepair");
            return (Criteria) this;
        }

        public Criteria andCostRepairNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_REPAIR not between", value1, value2, "costRepair");
            return (Criteria) this;
        }

        public Criteria andCostRenovationIsNull() {
            addCriterion("COST_RENOVATION is null");
            return (Criteria) this;
        }

        public Criteria andCostRenovationIsNotNull() {
            addCriterion("COST_RENOVATION is not null");
            return (Criteria) this;
        }

        public Criteria andCostRenovationEqualTo(BigDecimal value) {
            addCriterion("COST_RENOVATION =", value, "costRenovation");
            return (Criteria) this;
        }

        public Criteria andCostRenovationNotEqualTo(BigDecimal value) {
            addCriterion("COST_RENOVATION <>", value, "costRenovation");
            return (Criteria) this;
        }

        public Criteria andCostRenovationGreaterThan(BigDecimal value) {
            addCriterion("COST_RENOVATION >", value, "costRenovation");
            return (Criteria) this;
        }

        public Criteria andCostRenovationGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_RENOVATION >=", value, "costRenovation");
            return (Criteria) this;
        }

        public Criteria andCostRenovationLessThan(BigDecimal value) {
            addCriterion("COST_RENOVATION <", value, "costRenovation");
            return (Criteria) this;
        }

        public Criteria andCostRenovationLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_RENOVATION <=", value, "costRenovation");
            return (Criteria) this;
        }

        public Criteria andCostRenovationIn(List<BigDecimal> values) {
            addCriterion("COST_RENOVATION in", values, "costRenovation");
            return (Criteria) this;
        }

        public Criteria andCostRenovationNotIn(List<BigDecimal> values) {
            addCriterion("COST_RENOVATION not in", values, "costRenovation");
            return (Criteria) this;
        }

        public Criteria andCostRenovationBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_RENOVATION between", value1, value2, "costRenovation");
            return (Criteria) this;
        }

        public Criteria andCostRenovationNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_RENOVATION not between", value1, value2, "costRenovation");
            return (Criteria) this;
        }

        public Criteria andCostDevDepreciationIsNull() {
            addCriterion("COST_DEV_DEPRECIATION is null");
            return (Criteria) this;
        }

        public Criteria andCostDevDepreciationIsNotNull() {
            addCriterion("COST_DEV_DEPRECIATION is not null");
            return (Criteria) this;
        }

        public Criteria andCostDevDepreciationEqualTo(BigDecimal value) {
            addCriterion("COST_DEV_DEPRECIATION =", value, "costDevDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostDevDepreciationNotEqualTo(BigDecimal value) {
            addCriterion("COST_DEV_DEPRECIATION <>", value, "costDevDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostDevDepreciationGreaterThan(BigDecimal value) {
            addCriterion("COST_DEV_DEPRECIATION >", value, "costDevDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostDevDepreciationGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_DEV_DEPRECIATION >=", value, "costDevDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostDevDepreciationLessThan(BigDecimal value) {
            addCriterion("COST_DEV_DEPRECIATION <", value, "costDevDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostDevDepreciationLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_DEV_DEPRECIATION <=", value, "costDevDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostDevDepreciationIn(List<BigDecimal> values) {
            addCriterion("COST_DEV_DEPRECIATION in", values, "costDevDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostDevDepreciationNotIn(List<BigDecimal> values) {
            addCriterion("COST_DEV_DEPRECIATION not in", values, "costDevDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostDevDepreciationBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_DEV_DEPRECIATION between", value1, value2, "costDevDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostDevDepreciationNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_DEV_DEPRECIATION not between", value1, value2, "costDevDepreciation");
            return (Criteria) this;
        }

        public Criteria andCostDevRepairIsNull() {
            addCriterion("COST_DEV_REPAIR is null");
            return (Criteria) this;
        }

        public Criteria andCostDevRepairIsNotNull() {
            addCriterion("COST_DEV_REPAIR is not null");
            return (Criteria) this;
        }

        public Criteria andCostDevRepairEqualTo(BigDecimal value) {
            addCriterion("COST_DEV_REPAIR =", value, "costDevRepair");
            return (Criteria) this;
        }

        public Criteria andCostDevRepairNotEqualTo(BigDecimal value) {
            addCriterion("COST_DEV_REPAIR <>", value, "costDevRepair");
            return (Criteria) this;
        }

        public Criteria andCostDevRepairGreaterThan(BigDecimal value) {
            addCriterion("COST_DEV_REPAIR >", value, "costDevRepair");
            return (Criteria) this;
        }

        public Criteria andCostDevRepairGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_DEV_REPAIR >=", value, "costDevRepair");
            return (Criteria) this;
        }

        public Criteria andCostDevRepairLessThan(BigDecimal value) {
            addCriterion("COST_DEV_REPAIR <", value, "costDevRepair");
            return (Criteria) this;
        }

        public Criteria andCostDevRepairLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_DEV_REPAIR <=", value, "costDevRepair");
            return (Criteria) this;
        }

        public Criteria andCostDevRepairIn(List<BigDecimal> values) {
            addCriterion("COST_DEV_REPAIR in", values, "costDevRepair");
            return (Criteria) this;
        }

        public Criteria andCostDevRepairNotIn(List<BigDecimal> values) {
            addCriterion("COST_DEV_REPAIR not in", values, "costDevRepair");
            return (Criteria) this;
        }

        public Criteria andCostDevRepairBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_DEV_REPAIR between", value1, value2, "costDevRepair");
            return (Criteria) this;
        }

        public Criteria andCostDevRepairNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_DEV_REPAIR not between", value1, value2, "costDevRepair");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateIsNull() {
            addCriterion("\"COST_WATER_ RATE\" is null");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateIsNotNull() {
            addCriterion("\"COST_WATER_ RATE\" is not null");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateEqualTo(BigDecimal value) {
            addCriterion("\"COST_WATER_ RATE\" =", value, "costWaterRate");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateNotEqualTo(BigDecimal value) {
            addCriterion("\"COST_WATER_ RATE\" <>", value, "costWaterRate");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateGreaterThan(BigDecimal value) {
            addCriterion("\"COST_WATER_ RATE\" >", value, "costWaterRate");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("\"COST_WATER_ RATE\" >=", value, "costWaterRate");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateLessThan(BigDecimal value) {
            addCriterion("\"COST_WATER_ RATE\" <", value, "costWaterRate");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("\"COST_WATER_ RATE\" <=", value, "costWaterRate");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateIn(List<BigDecimal> values) {
            addCriterion("\"COST_WATER_ RATE\" in", values, "costWaterRate");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateNotIn(List<BigDecimal> values) {
            addCriterion("\"COST_WATER_ RATE\" not in", values, "costWaterRate");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("\"COST_WATER_ RATE\" between", value1, value2, "costWaterRate");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("\"COST_WATER_ RATE\" not between", value1, value2, "costWaterRate");
            return (Criteria) this;
        }

        public Criteria andCostEnergyIsNull() {
            addCriterion("COST_ENERGY is null");
            return (Criteria) this;
        }

        public Criteria andCostEnergyIsNotNull() {
            addCriterion("COST_ENERGY is not null");
            return (Criteria) this;
        }

        public Criteria andCostEnergyEqualTo(BigDecimal value) {
            addCriterion("COST_ENERGY =", value, "costEnergy");
            return (Criteria) this;
        }

        public Criteria andCostEnergyNotEqualTo(BigDecimal value) {
            addCriterion("COST_ENERGY <>", value, "costEnergy");
            return (Criteria) this;
        }

        public Criteria andCostEnergyGreaterThan(BigDecimal value) {
            addCriterion("COST_ENERGY >", value, "costEnergy");
            return (Criteria) this;
        }

        public Criteria andCostEnergyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_ENERGY >=", value, "costEnergy");
            return (Criteria) this;
        }

        public Criteria andCostEnergyLessThan(BigDecimal value) {
            addCriterion("COST_ENERGY <", value, "costEnergy");
            return (Criteria) this;
        }

        public Criteria andCostEnergyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_ENERGY <=", value, "costEnergy");
            return (Criteria) this;
        }

        public Criteria andCostEnergyIn(List<BigDecimal> values) {
            addCriterion("COST_ENERGY in", values, "costEnergy");
            return (Criteria) this;
        }

        public Criteria andCostEnergyNotIn(List<BigDecimal> values) {
            addCriterion("COST_ENERGY not in", values, "costEnergy");
            return (Criteria) this;
        }

        public Criteria andCostEnergyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_ENERGY between", value1, value2, "costEnergy");
            return (Criteria) this;
        }

        public Criteria andCostEnergyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_ENERGY not between", value1, value2, "costEnergy");
            return (Criteria) this;
        }

        public Criteria andCostWorkIsNull() {
            addCriterion("COST_WORK is null");
            return (Criteria) this;
        }

        public Criteria andCostWorkIsNotNull() {
            addCriterion("COST_WORK is not null");
            return (Criteria) this;
        }

        public Criteria andCostWorkEqualTo(BigDecimal value) {
            addCriterion("COST_WORK =", value, "costWork");
            return (Criteria) this;
        }

        public Criteria andCostWorkNotEqualTo(BigDecimal value) {
            addCriterion("COST_WORK <>", value, "costWork");
            return (Criteria) this;
        }

        public Criteria andCostWorkGreaterThan(BigDecimal value) {
            addCriterion("COST_WORK >", value, "costWork");
            return (Criteria) this;
        }

        public Criteria andCostWorkGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_WORK >=", value, "costWork");
            return (Criteria) this;
        }

        public Criteria andCostWorkLessThan(BigDecimal value) {
            addCriterion("COST_WORK <", value, "costWork");
            return (Criteria) this;
        }

        public Criteria andCostWorkLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_WORK <=", value, "costWork");
            return (Criteria) this;
        }

        public Criteria andCostWorkIn(List<BigDecimal> values) {
            addCriterion("COST_WORK in", values, "costWork");
            return (Criteria) this;
        }

        public Criteria andCostWorkNotIn(List<BigDecimal> values) {
            addCriterion("COST_WORK not in", values, "costWork");
            return (Criteria) this;
        }

        public Criteria andCostWorkBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_WORK between", value1, value2, "costWork");
            return (Criteria) this;
        }

        public Criteria andCostWorkNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_WORK not between", value1, value2, "costWork");
            return (Criteria) this;
        }

        public Criteria andCostNetworkIsNull() {
            addCriterion("COST_NETWORK is null");
            return (Criteria) this;
        }

        public Criteria andCostNetworkIsNotNull() {
            addCriterion("COST_NETWORK is not null");
            return (Criteria) this;
        }

        public Criteria andCostNetworkEqualTo(BigDecimal value) {
            addCriterion("COST_NETWORK =", value, "costNetwork");
            return (Criteria) this;
        }

        public Criteria andCostNetworkNotEqualTo(BigDecimal value) {
            addCriterion("COST_NETWORK <>", value, "costNetwork");
            return (Criteria) this;
        }

        public Criteria andCostNetworkGreaterThan(BigDecimal value) {
            addCriterion("COST_NETWORK >", value, "costNetwork");
            return (Criteria) this;
        }

        public Criteria andCostNetworkGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_NETWORK >=", value, "costNetwork");
            return (Criteria) this;
        }

        public Criteria andCostNetworkLessThan(BigDecimal value) {
            addCriterion("COST_NETWORK <", value, "costNetwork");
            return (Criteria) this;
        }

        public Criteria andCostNetworkLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_NETWORK <=", value, "costNetwork");
            return (Criteria) this;
        }

        public Criteria andCostNetworkIn(List<BigDecimal> values) {
            addCriterion("COST_NETWORK in", values, "costNetwork");
            return (Criteria) this;
        }

        public Criteria andCostNetworkNotIn(List<BigDecimal> values) {
            addCriterion("COST_NETWORK not in", values, "costNetwork");
            return (Criteria) this;
        }

        public Criteria andCostNetworkBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_NETWORK between", value1, value2, "costNetwork");
            return (Criteria) this;
        }

        public Criteria andCostNetworkNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_NETWORK not between", value1, value2, "costNetwork");
            return (Criteria) this;
        }

        public Criteria andCostTimeUnitIsNull() {
            addCriterion("COST_TIME_UNIT is null");
            return (Criteria) this;
        }

        public Criteria andCostTimeUnitIsNotNull() {
            addCriterion("COST_TIME_UNIT is not null");
            return (Criteria) this;
        }

        public Criteria andCostTimeUnitEqualTo(String value) {
            addCriterion("COST_TIME_UNIT =", value, "costTimeUnit");
            return (Criteria) this;
        }

        public Criteria andCostTimeUnitNotEqualTo(String value) {
            addCriterion("COST_TIME_UNIT <>", value, "costTimeUnit");
            return (Criteria) this;
        }

        public Criteria andCostTimeUnitGreaterThan(String value) {
            addCriterion("COST_TIME_UNIT >", value, "costTimeUnit");
            return (Criteria) this;
        }

        public Criteria andCostTimeUnitGreaterThanOrEqualTo(String value) {
            addCriterion("COST_TIME_UNIT >=", value, "costTimeUnit");
            return (Criteria) this;
        }

        public Criteria andCostTimeUnitLessThan(String value) {
            addCriterion("COST_TIME_UNIT <", value, "costTimeUnit");
            return (Criteria) this;
        }

        public Criteria andCostTimeUnitLessThanOrEqualTo(String value) {
            addCriterion("COST_TIME_UNIT <=", value, "costTimeUnit");
            return (Criteria) this;
        }

        public Criteria andCostTimeUnitLike(String value) {
            addCriterion("COST_TIME_UNIT like", value, "costTimeUnit");
            return (Criteria) this;
        }

        public Criteria andCostTimeUnitNotLike(String value) {
            addCriterion("COST_TIME_UNIT not like", value, "costTimeUnit");
            return (Criteria) this;
        }

        public Criteria andCostTimeUnitIn(List<String> values) {
            addCriterion("COST_TIME_UNIT in", values, "costTimeUnit");
            return (Criteria) this;
        }

        public Criteria andCostTimeUnitNotIn(List<String> values) {
            addCriterion("COST_TIME_UNIT not in", values, "costTimeUnit");
            return (Criteria) this;
        }

        public Criteria andCostTimeUnitBetween(String value1, String value2) {
            addCriterion("COST_TIME_UNIT between", value1, value2, "costTimeUnit");
            return (Criteria) this;
        }

        public Criteria andCostTimeUnitNotBetween(String value1, String value2) {
            addCriterion("COST_TIME_UNIT not between", value1, value2, "costTimeUnit");
            return (Criteria) this;
        }

        public Criteria andRoomCodeIsNull() {
            addCriterion("ROOM_CODE is null");
            return (Criteria) this;
        }

        public Criteria andRoomCodeIsNotNull() {
            addCriterion("ROOM_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andRoomCodeEqualTo(String value) {
            addCriterion("ROOM_CODE =", value, "roomCode");
            return (Criteria) this;
        }

        public Criteria andRoomCodeNotEqualTo(String value) {
            addCriterion("ROOM_CODE <>", value, "roomCode");
            return (Criteria) this;
        }

        public Criteria andRoomCodeGreaterThan(String value) {
            addCriterion("ROOM_CODE >", value, "roomCode");
            return (Criteria) this;
        }

        public Criteria andRoomCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ROOM_CODE >=", value, "roomCode");
            return (Criteria) this;
        }

        public Criteria andRoomCodeLessThan(String value) {
            addCriterion("ROOM_CODE <", value, "roomCode");
            return (Criteria) this;
        }

        public Criteria andRoomCodeLessThanOrEqualTo(String value) {
            addCriterion("ROOM_CODE <=", value, "roomCode");
            return (Criteria) this;
        }

        public Criteria andRoomCodeLike(String value) {
            addCriterion("ROOM_CODE like", value, "roomCode");
            return (Criteria) this;
        }

        public Criteria andRoomCodeNotLike(String value) {
            addCriterion("ROOM_CODE not like", value, "roomCode");
            return (Criteria) this;
        }

        public Criteria andRoomCodeIn(List<String> values) {
            addCriterion("ROOM_CODE in", values, "roomCode");
            return (Criteria) this;
        }

        public Criteria andRoomCodeNotIn(List<String> values) {
            addCriterion("ROOM_CODE not in", values, "roomCode");
            return (Criteria) this;
        }

        public Criteria andRoomCodeBetween(String value1, String value2) {
            addCriterion("ROOM_CODE between", value1, value2, "roomCode");
            return (Criteria) this;
        }

        public Criteria andRoomCodeNotBetween(String value1, String value2) {
            addCriterion("ROOM_CODE not between", value1, value2, "roomCode");
            return (Criteria) this;
        }

        public Criteria andRoomNameIsNull() {
            addCriterion("ROOM_NAME is null");
            return (Criteria) this;
        }

        public Criteria andRoomNameIsNotNull() {
            addCriterion("ROOM_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andRoomNameEqualTo(String value) {
            addCriterion("ROOM_NAME =", value, "roomName");
            return (Criteria) this;
        }

        public Criteria andRoomNameNotEqualTo(String value) {
            addCriterion("ROOM_NAME <>", value, "roomName");
            return (Criteria) this;
        }

        public Criteria andRoomNameGreaterThan(String value) {
            addCriterion("ROOM_NAME >", value, "roomName");
            return (Criteria) this;
        }

        public Criteria andRoomNameGreaterThanOrEqualTo(String value) {
            addCriterion("ROOM_NAME >=", value, "roomName");
            return (Criteria) this;
        }

        public Criteria andRoomNameLessThan(String value) {
            addCriterion("ROOM_NAME <", value, "roomName");
            return (Criteria) this;
        }

        public Criteria andRoomNameLessThanOrEqualTo(String value) {
            addCriterion("ROOM_NAME <=", value, "roomName");
            return (Criteria) this;
        }

        public Criteria andRoomNameLike(String value) {
            addCriterion("ROOM_NAME like", value, "roomName");
            return (Criteria) this;
        }

        public Criteria andRoomNameNotLike(String value) {
            addCriterion("ROOM_NAME not like", value, "roomName");
            return (Criteria) this;
        }

        public Criteria andRoomNameIn(List<String> values) {
            addCriterion("ROOM_NAME in", values, "roomName");
            return (Criteria) this;
        }

        public Criteria andRoomNameNotIn(List<String> values) {
            addCriterion("ROOM_NAME not in", values, "roomName");
            return (Criteria) this;
        }

        public Criteria andRoomNameBetween(String value1, String value2) {
            addCriterion("ROOM_NAME between", value1, value2, "roomName");
            return (Criteria) this;
        }

        public Criteria andRoomNameNotBetween(String value1, String value2) {
            addCriterion("ROOM_NAME not between", value1, value2, "roomName");
            return (Criteria) this;
        }

        public Criteria andCostYearOrMonthIsNull() {
            addCriterion("COST_YEAR_OR_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andCostYearOrMonthIsNotNull() {
            addCriterion("COST_YEAR_OR_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andCostYearOrMonthEqualTo(BigDecimal value) {
            addCriterion("COST_YEAR_OR_MONTH =", value, "costYearOrMonth");
            return (Criteria) this;
        }

        public Criteria andCostYearOrMonthNotEqualTo(BigDecimal value) {
            addCriterion("COST_YEAR_OR_MONTH <>", value, "costYearOrMonth");
            return (Criteria) this;
        }

        public Criteria andCostYearOrMonthGreaterThan(BigDecimal value) {
            addCriterion("COST_YEAR_OR_MONTH >", value, "costYearOrMonth");
            return (Criteria) this;
        }

        public Criteria andCostYearOrMonthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_YEAR_OR_MONTH >=", value, "costYearOrMonth");
            return (Criteria) this;
        }

        public Criteria andCostYearOrMonthLessThan(BigDecimal value) {
            addCriterion("COST_YEAR_OR_MONTH <", value, "costYearOrMonth");
            return (Criteria) this;
        }

        public Criteria andCostYearOrMonthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_YEAR_OR_MONTH <=", value, "costYearOrMonth");
            return (Criteria) this;
        }

        public Criteria andCostYearOrMonthIn(List<BigDecimal> values) {
            addCriterion("COST_YEAR_OR_MONTH in", values, "costYearOrMonth");
            return (Criteria) this;
        }

        public Criteria andCostYearOrMonthNotIn(List<BigDecimal> values) {
            addCriterion("COST_YEAR_OR_MONTH not in", values, "costYearOrMonth");
            return (Criteria) this;
        }

        public Criteria andCostYearOrMonthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_YEAR_OR_MONTH between", value1, value2, "costYearOrMonth");
            return (Criteria) this;
        }

        public Criteria andCostYearOrMonthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_YEAR_OR_MONTH not between", value1, value2, "costYearOrMonth");
            return (Criteria) this;
        }

        public Criteria andBuidingCodeIsNull() {
            addCriterion("BUIDING_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBuidingCodeIsNotNull() {
            addCriterion("BUIDING_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBuidingCodeEqualTo(String value) {
            addCriterion("BUIDING_CODE =", value, "buidingCode");
            return (Criteria) this;
        }

        public Criteria andBuidingCodeNotEqualTo(String value) {
            addCriterion("BUIDING_CODE <>", value, "buidingCode");
            return (Criteria) this;
        }

        public Criteria andBuidingCodeGreaterThan(String value) {
            addCriterion("BUIDING_CODE >", value, "buidingCode");
            return (Criteria) this;
        }

        public Criteria andBuidingCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BUIDING_CODE >=", value, "buidingCode");
            return (Criteria) this;
        }

        public Criteria andBuidingCodeLessThan(String value) {
            addCriterion("BUIDING_CODE <", value, "buidingCode");
            return (Criteria) this;
        }

        public Criteria andBuidingCodeLessThanOrEqualTo(String value) {
            addCriterion("BUIDING_CODE <=", value, "buidingCode");
            return (Criteria) this;
        }

        public Criteria andBuidingCodeLike(String value) {
            addCriterion("BUIDING_CODE like", value, "buidingCode");
            return (Criteria) this;
        }

        public Criteria andBuidingCodeNotLike(String value) {
            addCriterion("BUIDING_CODE not like", value, "buidingCode");
            return (Criteria) this;
        }

        public Criteria andBuidingCodeIn(List<String> values) {
            addCriterion("BUIDING_CODE in", values, "buidingCode");
            return (Criteria) this;
        }

        public Criteria andBuidingCodeNotIn(List<String> values) {
            addCriterion("BUIDING_CODE not in", values, "buidingCode");
            return (Criteria) this;
        }

        public Criteria andBuidingCodeBetween(String value1, String value2) {
            addCriterion("BUIDING_CODE between", value1, value2, "buidingCode");
            return (Criteria) this;
        }

        public Criteria andBuidingCodeNotBetween(String value1, String value2) {
            addCriterion("BUIDING_CODE not between", value1, value2, "buidingCode");
            return (Criteria) this;
        }

        public Criteria andCostStartTimeIsNull() {
            addCriterion("COST_START_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCostStartTimeIsNotNull() {
            addCriterion("COST_START_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCostStartTimeEqualTo(Date value) {
            addCriterionForJDBCDate("COST_START_TIME =", value, "costStartTime");
            return (Criteria) this;
        }

        public Criteria andCostStartTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("COST_START_TIME <>", value, "costStartTime");
            return (Criteria) this;
        }

        public Criteria andCostStartTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("COST_START_TIME >", value, "costStartTime");
            return (Criteria) this;
        }

        public Criteria andCostStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("COST_START_TIME >=", value, "costStartTime");
            return (Criteria) this;
        }

        public Criteria andCostStartTimeLessThan(Date value) {
            addCriterionForJDBCDate("COST_START_TIME <", value, "costStartTime");
            return (Criteria) this;
        }

        public Criteria andCostStartTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("COST_START_TIME <=", value, "costStartTime");
            return (Criteria) this;
        }

        public Criteria andCostStartTimeIn(List<Date> values) {
            addCriterionForJDBCDate("COST_START_TIME in", values, "costStartTime");
            return (Criteria) this;
        }

        public Criteria andCostStartTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("COST_START_TIME not in", values, "costStartTime");
            return (Criteria) this;
        }

        public Criteria andCostStartTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("COST_START_TIME between", value1, value2, "costStartTime");
            return (Criteria) this;
        }

        public Criteria andCostStartTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("COST_START_TIME not between", value1, value2, "costStartTime");
            return (Criteria) this;
        }

        public Criteria andCostEndTimeIsNull() {
            addCriterion("COST_END_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCostEndTimeIsNotNull() {
            addCriterion("COST_END_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCostEndTimeEqualTo(Date value) {
            addCriterionForJDBCDate("COST_END_TIME =", value, "costEndTime");
            return (Criteria) this;
        }

        public Criteria andCostEndTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("COST_END_TIME <>", value, "costEndTime");
            return (Criteria) this;
        }

        public Criteria andCostEndTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("COST_END_TIME >", value, "costEndTime");
            return (Criteria) this;
        }

        public Criteria andCostEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("COST_END_TIME >=", value, "costEndTime");
            return (Criteria) this;
        }

        public Criteria andCostEndTimeLessThan(Date value) {
            addCriterionForJDBCDate("COST_END_TIME <", value, "costEndTime");
            return (Criteria) this;
        }

        public Criteria andCostEndTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("COST_END_TIME <=", value, "costEndTime");
            return (Criteria) this;
        }

        public Criteria andCostEndTimeIn(List<Date> values) {
            addCriterionForJDBCDate("COST_END_TIME in", values, "costEndTime");
            return (Criteria) this;
        }

        public Criteria andCostEndTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("COST_END_TIME not in", values, "costEndTime");
            return (Criteria) this;
        }

        public Criteria andCostEndTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("COST_END_TIME between", value1, value2, "costEndTime");
            return (Criteria) this;
        }

        public Criteria andCostEndTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("COST_END_TIME not between", value1, value2, "costEndTime");
            return (Criteria) this;
        }

        public Criteria andCostCreateTimeIsNull() {
            addCriterion("COST_CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCostCreateTimeIsNotNull() {
            addCriterion("COST_CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCostCreateTimeEqualTo(Date value) {
            addCriterionForJDBCDate("COST_CREATE_TIME =", value, "costCreateTime");
            return (Criteria) this;
        }

        public Criteria andCostCreateTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("COST_CREATE_TIME <>", value, "costCreateTime");
            return (Criteria) this;
        }

        public Criteria andCostCreateTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("COST_CREATE_TIME >", value, "costCreateTime");
            return (Criteria) this;
        }

        public Criteria andCostCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("COST_CREATE_TIME >=", value, "costCreateTime");
            return (Criteria) this;
        }

        public Criteria andCostCreateTimeLessThan(Date value) {
            addCriterionForJDBCDate("COST_CREATE_TIME <", value, "costCreateTime");
            return (Criteria) this;
        }

        public Criteria andCostCreateTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("COST_CREATE_TIME <=", value, "costCreateTime");
            return (Criteria) this;
        }

        public Criteria andCostCreateTimeIn(List<Date> values) {
            addCriterionForJDBCDate("COST_CREATE_TIME in", values, "costCreateTime");
            return (Criteria) this;
        }

        public Criteria andCostCreateTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("COST_CREATE_TIME not in", values, "costCreateTime");
            return (Criteria) this;
        }

        public Criteria andCostCreateTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("COST_CREATE_TIME between", value1, value2, "costCreateTime");
            return (Criteria) this;
        }

        public Criteria andCostCreateTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("COST_CREATE_TIME not between", value1, value2, "costCreateTime");
            return (Criteria) this;
        }

        public Criteria andCostMarkettingIsNull() {
            addCriterion("COST_MARKETTING is null");
            return (Criteria) this;
        }

        public Criteria andCostMarkettingIsNotNull() {
            addCriterion("COST_MARKETTING is not null");
            return (Criteria) this;
        }

        public Criteria andCostMarkettingEqualTo(BigDecimal value) {
            addCriterion("COST_MARKETTING =", value, "costMarketting");
            return (Criteria) this;
        }

        public Criteria andCostMarkettingNotEqualTo(BigDecimal value) {
            addCriterion("COST_MARKETTING <>", value, "costMarketting");
            return (Criteria) this;
        }

        public Criteria andCostMarkettingGreaterThan(BigDecimal value) {
            addCriterion("COST_MARKETTING >", value, "costMarketting");
            return (Criteria) this;
        }

        public Criteria andCostMarkettingGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_MARKETTING >=", value, "costMarketting");
            return (Criteria) this;
        }

        public Criteria andCostMarkettingLessThan(BigDecimal value) {
            addCriterion("COST_MARKETTING <", value, "costMarketting");
            return (Criteria) this;
        }

        public Criteria andCostMarkettingLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_MARKETTING <=", value, "costMarketting");
            return (Criteria) this;
        }

        public Criteria andCostMarkettingIn(List<BigDecimal> values) {
            addCriterion("COST_MARKETTING in", values, "costMarketting");
            return (Criteria) this;
        }

        public Criteria andCostMarkettingNotIn(List<BigDecimal> values) {
            addCriterion("COST_MARKETTING not in", values, "costMarketting");
            return (Criteria) this;
        }

        public Criteria andCostMarkettingBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_MARKETTING between", value1, value2, "costMarketting");
            return (Criteria) this;
        }

        public Criteria andCostMarkettingNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_MARKETTING not between", value1, value2, "costMarketting");
            return (Criteria) this;
        }

        public Criteria andCostCustomIncomeIsNull() {
            addCriterion("COST_CUSTOM_INCOME is null");
            return (Criteria) this;
        }

        public Criteria andCostCustomIncomeIsNotNull() {
            addCriterion("COST_CUSTOM_INCOME is not null");
            return (Criteria) this;
        }

        public Criteria andCostCustomIncomeEqualTo(BigDecimal value) {
            addCriterion("COST_CUSTOM_INCOME =", value, "costCustomIncome");
            return (Criteria) this;
        }

        public Criteria andCostCustomIncomeNotEqualTo(BigDecimal value) {
            addCriterion("COST_CUSTOM_INCOME <>", value, "costCustomIncome");
            return (Criteria) this;
        }

        public Criteria andCostCustomIncomeGreaterThan(BigDecimal value) {
            addCriterion("COST_CUSTOM_INCOME >", value, "costCustomIncome");
            return (Criteria) this;
        }

        public Criteria andCostCustomIncomeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_CUSTOM_INCOME >=", value, "costCustomIncome");
            return (Criteria) this;
        }

        public Criteria andCostCustomIncomeLessThan(BigDecimal value) {
            addCriterion("COST_CUSTOM_INCOME <", value, "costCustomIncome");
            return (Criteria) this;
        }

        public Criteria andCostCustomIncomeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_CUSTOM_INCOME <=", value, "costCustomIncome");
            return (Criteria) this;
        }

        public Criteria andCostCustomIncomeIn(List<BigDecimal> values) {
            addCriterion("COST_CUSTOM_INCOME in", values, "costCustomIncome");
            return (Criteria) this;
        }

        public Criteria andCostCustomIncomeNotIn(List<BigDecimal> values) {
            addCriterion("COST_CUSTOM_INCOME not in", values, "costCustomIncome");
            return (Criteria) this;
        }

        public Criteria andCostCustomIncomeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_CUSTOM_INCOME between", value1, value2, "costCustomIncome");
            return (Criteria) this;
        }

        public Criteria andCostCustomIncomeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_CUSTOM_INCOME not between", value1, value2, "costCustomIncome");
            return (Criteria) this;
        }

        public Criteria andCustomIdIsNull() {
            addCriterion("CUSTOM_ID is null");
            return (Criteria) this;
        }

        public Criteria andCustomIdIsNotNull() {
            addCriterion("CUSTOM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCustomIdEqualTo(String value) {
            addCriterion("CUSTOM_ID =", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdNotEqualTo(String value) {
            addCriterion("CUSTOM_ID <>", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdGreaterThan(String value) {
            addCriterion("CUSTOM_ID >", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdGreaterThanOrEqualTo(String value) {
            addCriterion("CUSTOM_ID >=", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdLessThan(String value) {
            addCriterion("CUSTOM_ID <", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdLessThanOrEqualTo(String value) {
            addCriterion("CUSTOM_ID <=", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdLike(String value) {
            addCriterion("CUSTOM_ID like", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdNotLike(String value) {
            addCriterion("CUSTOM_ID not like", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdIn(List<String> values) {
            addCriterion("CUSTOM_ID in", values, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdNotIn(List<String> values) {
            addCriterion("CUSTOM_ID not in", values, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdBetween(String value1, String value2) {
            addCriterion("CUSTOM_ID between", value1, value2, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdNotBetween(String value1, String value2) {
            addCriterion("CUSTOM_ID not between", value1, value2, "customId");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}