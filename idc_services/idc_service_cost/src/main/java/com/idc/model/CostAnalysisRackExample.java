package com.idc.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CostAnalysisRackExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CostAnalysisRackExample() {
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

        public Criteria andCostAnalysisRackIdIsNull() {
            addCriterion("COST_ANALYSIS_RACK_ID is null");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRackIdIsNotNull() {
            addCriterion("COST_ANALYSIS_RACK_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRackIdEqualTo(String value) {
            addCriterion("COST_ANALYSIS_RACK_ID =", value, "costAnalysisRackId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRackIdNotEqualTo(String value) {
            addCriterion("COST_ANALYSIS_RACK_ID <>", value, "costAnalysisRackId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRackIdGreaterThan(String value) {
            addCriterion("COST_ANALYSIS_RACK_ID >", value, "costAnalysisRackId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRackIdGreaterThanOrEqualTo(String value) {
            addCriterion("COST_ANALYSIS_RACK_ID >=", value, "costAnalysisRackId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRackIdLessThan(String value) {
            addCriterion("COST_ANALYSIS_RACK_ID <", value, "costAnalysisRackId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRackIdLessThanOrEqualTo(String value) {
            addCriterion("COST_ANALYSIS_RACK_ID <=", value, "costAnalysisRackId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRackIdLike(String value) {
            addCriterion("COST_ANALYSIS_RACK_ID like", value, "costAnalysisRackId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRackIdNotLike(String value) {
            addCriterion("COST_ANALYSIS_RACK_ID not like", value, "costAnalysisRackId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRackIdIn(List<String> values) {
            addCriterion("COST_ANALYSIS_RACK_ID in", values, "costAnalysisRackId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRackIdNotIn(List<String> values) {
            addCriterion("COST_ANALYSIS_RACK_ID not in", values, "costAnalysisRackId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRackIdBetween(String value1, String value2) {
            addCriterion("COST_ANALYSIS_RACK_ID between", value1, value2, "costAnalysisRackId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRackIdNotBetween(String value1, String value2) {
            addCriterion("COST_ANALYSIS_RACK_ID not between", value1, value2, "costAnalysisRackId");
            return (Criteria) this;
        }

        public Criteria andCostRackIdIsNull() {
            addCriterion("COST_RACK_ID is null");
            return (Criteria) this;
        }

        public Criteria andCostRackIdIsNotNull() {
            addCriterion("COST_RACK_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCostRackIdEqualTo(String value) {
            addCriterion("COST_RACK_ID =", value, "costRackId");
            return (Criteria) this;
        }

        public Criteria andCostRackIdNotEqualTo(String value) {
            addCriterion("COST_RACK_ID <>", value, "costRackId");
            return (Criteria) this;
        }

        public Criteria andCostRackIdGreaterThan(String value) {
            addCriterion("COST_RACK_ID >", value, "costRackId");
            return (Criteria) this;
        }

        public Criteria andCostRackIdGreaterThanOrEqualTo(String value) {
            addCriterion("COST_RACK_ID >=", value, "costRackId");
            return (Criteria) this;
        }

        public Criteria andCostRackIdLessThan(String value) {
            addCriterion("COST_RACK_ID <", value, "costRackId");
            return (Criteria) this;
        }

        public Criteria andCostRackIdLessThanOrEqualTo(String value) {
            addCriterion("COST_RACK_ID <=", value, "costRackId");
            return (Criteria) this;
        }

        public Criteria andCostRackIdLike(String value) {
            addCriterion("COST_RACK_ID like", value, "costRackId");
            return (Criteria) this;
        }

        public Criteria andCostRackIdNotLike(String value) {
            addCriterion("COST_RACK_ID not like", value, "costRackId");
            return (Criteria) this;
        }

        public Criteria andCostRackIdIn(List<String> values) {
            addCriterion("COST_RACK_ID in", values, "costRackId");
            return (Criteria) this;
        }

        public Criteria andCostRackIdNotIn(List<String> values) {
            addCriterion("COST_RACK_ID not in", values, "costRackId");
            return (Criteria) this;
        }

        public Criteria andCostRackIdBetween(String value1, String value2) {
            addCriterion("COST_RACK_ID between", value1, value2, "costRackId");
            return (Criteria) this;
        }

        public Criteria andCostRackIdNotBetween(String value1, String value2) {
            addCriterion("COST_RACK_ID not between", value1, value2, "costRackId");
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

        public Criteria andCostRackPowerFeeIsNull() {
            addCriterion("COST_RACK_POWER_FEE is null");
            return (Criteria) this;
        }

        public Criteria andCostRackPowerFeeIsNotNull() {
            addCriterion("COST_RACK_POWER_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andCostRackPowerFeeEqualTo(BigDecimal value) {
            addCriterion("COST_RACK_POWER_FEE =", value, "costRackPowerFee");
            return (Criteria) this;
        }

        public Criteria andCostRackPowerFeeNotEqualTo(BigDecimal value) {
            addCriterion("COST_RACK_POWER_FEE <>", value, "costRackPowerFee");
            return (Criteria) this;
        }

        public Criteria andCostRackPowerFeeGreaterThan(BigDecimal value) {
            addCriterion("COST_RACK_POWER_FEE >", value, "costRackPowerFee");
            return (Criteria) this;
        }

        public Criteria andCostRackPowerFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_RACK_POWER_FEE >=", value, "costRackPowerFee");
            return (Criteria) this;
        }

        public Criteria andCostRackPowerFeeLessThan(BigDecimal value) {
            addCriterion("COST_RACK_POWER_FEE <", value, "costRackPowerFee");
            return (Criteria) this;
        }

        public Criteria andCostRackPowerFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_RACK_POWER_FEE <=", value, "costRackPowerFee");
            return (Criteria) this;
        }

        public Criteria andCostRackPowerFeeIn(List<BigDecimal> values) {
            addCriterion("COST_RACK_POWER_FEE in", values, "costRackPowerFee");
            return (Criteria) this;
        }

        public Criteria andCostRackPowerFeeNotIn(List<BigDecimal> values) {
            addCriterion("COST_RACK_POWER_FEE not in", values, "costRackPowerFee");
            return (Criteria) this;
        }

        public Criteria andCostRackPowerFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_RACK_POWER_FEE between", value1, value2, "costRackPowerFee");
            return (Criteria) this;
        }

        public Criteria andCostRackPowerFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_RACK_POWER_FEE not between", value1, value2, "costRackPowerFee");
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

        public Criteria andCostWaterRateIsNull() {
            addCriterion("COST_WATER_RATE is null");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateIsNotNull() {
            addCriterion("COST_WATER_RATE is not null");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateEqualTo(BigDecimal value) {
            addCriterion("COST_WATER_RATE =", value, "costWaterRate");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateNotEqualTo(BigDecimal value) {
            addCriterion("COST_WATER_RATE <>", value, "costWaterRate");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateGreaterThan(BigDecimal value) {
            addCriterion("COST_WATER_RATE >", value, "costWaterRate");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_WATER_RATE >=", value, "costWaterRate");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateLessThan(BigDecimal value) {
            addCriterion("COST_WATER_RATE <", value, "costWaterRate");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_WATER_RATE <=", value, "costWaterRate");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateIn(List<BigDecimal> values) {
            addCriterion("COST_WATER_RATE in", values, "costWaterRate");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateNotIn(List<BigDecimal> values) {
            addCriterion("COST_WATER_RATE not in", values, "costWaterRate");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_WATER_RATE between", value1, value2, "costWaterRate");
            return (Criteria) this;
        }

        public Criteria andCostWaterRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_WATER_RATE not between", value1, value2, "costWaterRate");
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

        public Criteria andCostOtherIsNull() {
            addCriterion("COST_OTHER is null");
            return (Criteria) this;
        }

        public Criteria andCostOtherIsNotNull() {
            addCriterion("COST_OTHER is not null");
            return (Criteria) this;
        }

        public Criteria andCostOtherEqualTo(BigDecimal value) {
            addCriterion("COST_OTHER =", value, "costOther");
            return (Criteria) this;
        }

        public Criteria andCostOtherNotEqualTo(BigDecimal value) {
            addCriterion("COST_OTHER <>", value, "costOther");
            return (Criteria) this;
        }

        public Criteria andCostOtherGreaterThan(BigDecimal value) {
            addCriterion("COST_OTHER >", value, "costOther");
            return (Criteria) this;
        }

        public Criteria andCostOtherGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_OTHER >=", value, "costOther");
            return (Criteria) this;
        }

        public Criteria andCostOtherLessThan(BigDecimal value) {
            addCriterion("COST_OTHER <", value, "costOther");
            return (Criteria) this;
        }

        public Criteria andCostOtherLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_OTHER <=", value, "costOther");
            return (Criteria) this;
        }

        public Criteria andCostOtherIn(List<BigDecimal> values) {
            addCriterion("COST_OTHER in", values, "costOther");
            return (Criteria) this;
        }

        public Criteria andCostOtherNotIn(List<BigDecimal> values) {
            addCriterion("COST_OTHER not in", values, "costOther");
            return (Criteria) this;
        }

        public Criteria andCostOtherBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_OTHER between", value1, value2, "costOther");
            return (Criteria) this;
        }

        public Criteria andCostOtherNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_OTHER not between", value1, value2, "costOther");
            return (Criteria) this;
        }

        public Criteria andCostColligateIsNull() {
            addCriterion("COST_COLLIGATE is null");
            return (Criteria) this;
        }

        public Criteria andCostColligateIsNotNull() {
            addCriterion("COST_COLLIGATE is not null");
            return (Criteria) this;
        }

        public Criteria andCostColligateEqualTo(BigDecimal value) {
            addCriterion("COST_COLLIGATE =", value, "costColligate");
            return (Criteria) this;
        }

        public Criteria andCostColligateNotEqualTo(BigDecimal value) {
            addCriterion("COST_COLLIGATE <>", value, "costColligate");
            return (Criteria) this;
        }

        public Criteria andCostColligateGreaterThan(BigDecimal value) {
            addCriterion("COST_COLLIGATE >", value, "costColligate");
            return (Criteria) this;
        }

        public Criteria andCostColligateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_COLLIGATE >=", value, "costColligate");
            return (Criteria) this;
        }

        public Criteria andCostColligateLessThan(BigDecimal value) {
            addCriterion("COST_COLLIGATE <", value, "costColligate");
            return (Criteria) this;
        }

        public Criteria andCostColligateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_COLLIGATE <=", value, "costColligate");
            return (Criteria) this;
        }

        public Criteria andCostColligateIn(List<BigDecimal> values) {
            addCriterion("COST_COLLIGATE in", values, "costColligate");
            return (Criteria) this;
        }

        public Criteria andCostColligateNotIn(List<BigDecimal> values) {
            addCriterion("COST_COLLIGATE not in", values, "costColligate");
            return (Criteria) this;
        }

        public Criteria andCostColligateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_COLLIGATE between", value1, value2, "costColligate");
            return (Criteria) this;
        }

        public Criteria andCostColligateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_COLLIGATE not between", value1, value2, "costColligate");
            return (Criteria) this;
        }

        public Criteria andCostYearIsNull() {
            addCriterion("COST_YEAR is null");
            return (Criteria) this;
        }

        public Criteria andCostYearIsNotNull() {
            addCriterion("COST_YEAR is not null");
            return (Criteria) this;
        }

        public Criteria andCostYearEqualTo(String value) {
            addCriterion("COST_YEAR =", value, "costYear");
            return (Criteria) this;
        }

        public Criteria andCostYearNotEqualTo(String value) {
            addCriterion("COST_YEAR <>", value, "costYear");
            return (Criteria) this;
        }

        public Criteria andCostYearGreaterThan(String value) {
            addCriterion("COST_YEAR >", value, "costYear");
            return (Criteria) this;
        }

        public Criteria andCostYearGreaterThanOrEqualTo(String value) {
            addCriterion("COST_YEAR >=", value, "costYear");
            return (Criteria) this;
        }

        public Criteria andCostYearLessThan(String value) {
            addCriterion("COST_YEAR <", value, "costYear");
            return (Criteria) this;
        }

        public Criteria andCostYearLessThanOrEqualTo(String value) {
            addCriterion("COST_YEAR <=", value, "costYear");
            return (Criteria) this;
        }

        public Criteria andCostYearLike(String value) {
            addCriterion("COST_YEAR like", value, "costYear");
            return (Criteria) this;
        }

        public Criteria andCostYearNotLike(String value) {
            addCriterion("COST_YEAR not like", value, "costYear");
            return (Criteria) this;
        }

        public Criteria andCostYearIn(List<String> values) {
            addCriterion("COST_YEAR in", values, "costYear");
            return (Criteria) this;
        }

        public Criteria andCostYearNotIn(List<String> values) {
            addCriterion("COST_YEAR not in", values, "costYear");
            return (Criteria) this;
        }

        public Criteria andCostYearBetween(String value1, String value2) {
            addCriterion("COST_YEAR between", value1, value2, "costYear");
            return (Criteria) this;
        }

        public Criteria andCostYearNotBetween(String value1, String value2) {
            addCriterion("COST_YEAR not between", value1, value2, "costYear");
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

        public Criteria andBandwidthIncomeIsNull() {
            addCriterion("BANDWIDTH_INCOME is null");
            return (Criteria) this;
        }

        public Criteria andBandwidthIncomeIsNotNull() {
            addCriterion("BANDWIDTH_INCOME is not null");
            return (Criteria) this;
        }

        public Criteria andBandwidthIncomeEqualTo(BigDecimal value) {
            addCriterion("BANDWIDTH_INCOME =", value, "bandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andBandwidthIncomeNotEqualTo(BigDecimal value) {
            addCriterion("BANDWIDTH_INCOME <>", value, "bandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andBandwidthIncomeGreaterThan(BigDecimal value) {
            addCriterion("BANDWIDTH_INCOME >", value, "bandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andBandwidthIncomeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BANDWIDTH_INCOME >=", value, "bandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andBandwidthIncomeLessThan(BigDecimal value) {
            addCriterion("BANDWIDTH_INCOME <", value, "bandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andBandwidthIncomeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BANDWIDTH_INCOME <=", value, "bandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andBandwidthIncomeIn(List<BigDecimal> values) {
            addCriterion("BANDWIDTH_INCOME in", values, "bandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andBandwidthIncomeNotIn(List<BigDecimal> values) {
            addCriterion("BANDWIDTH_INCOME not in", values, "bandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andBandwidthIncomeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BANDWIDTH_INCOME between", value1, value2, "bandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andBandwidthIncomeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BANDWIDTH_INCOME not between", value1, value2, "bandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andSoldIncomeFeeIsNull() {
            addCriterion("SOLD_INCOME_FEE is null");
            return (Criteria) this;
        }

        public Criteria andSoldIncomeFeeIsNotNull() {
            addCriterion("SOLD_INCOME_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andSoldIncomeFeeEqualTo(BigDecimal value) {
            addCriterion("SOLD_INCOME_FEE =", value, "soldIncomeFee");
            return (Criteria) this;
        }

        public Criteria andSoldIncomeFeeNotEqualTo(BigDecimal value) {
            addCriterion("SOLD_INCOME_FEE <>", value, "soldIncomeFee");
            return (Criteria) this;
        }

        public Criteria andSoldIncomeFeeGreaterThan(BigDecimal value) {
            addCriterion("SOLD_INCOME_FEE >", value, "soldIncomeFee");
            return (Criteria) this;
        }

        public Criteria andSoldIncomeFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SOLD_INCOME_FEE >=", value, "soldIncomeFee");
            return (Criteria) this;
        }

        public Criteria andSoldIncomeFeeLessThan(BigDecimal value) {
            addCriterion("SOLD_INCOME_FEE <", value, "soldIncomeFee");
            return (Criteria) this;
        }

        public Criteria andSoldIncomeFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SOLD_INCOME_FEE <=", value, "soldIncomeFee");
            return (Criteria) this;
        }

        public Criteria andSoldIncomeFeeIn(List<BigDecimal> values) {
            addCriterion("SOLD_INCOME_FEE in", values, "soldIncomeFee");
            return (Criteria) this;
        }

        public Criteria andSoldIncomeFeeNotIn(List<BigDecimal> values) {
            addCriterion("SOLD_INCOME_FEE not in", values, "soldIncomeFee");
            return (Criteria) this;
        }

        public Criteria andSoldIncomeFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SOLD_INCOME_FEE between", value1, value2, "soldIncomeFee");
            return (Criteria) this;
        }

        public Criteria andSoldIncomeFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SOLD_INCOME_FEE not between", value1, value2, "soldIncomeFee");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthIsNull() {
            addCriterion("COST_BANDWIDTH is null");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthIsNotNull() {
            addCriterion("COST_BANDWIDTH is not null");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthEqualTo(BigDecimal value) {
            addCriterion("COST_BANDWIDTH =", value, "costBandwidth");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthNotEqualTo(BigDecimal value) {
            addCriterion("COST_BANDWIDTH <>", value, "costBandwidth");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthGreaterThan(BigDecimal value) {
            addCriterion("COST_BANDWIDTH >", value, "costBandwidth");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_BANDWIDTH >=", value, "costBandwidth");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthLessThan(BigDecimal value) {
            addCriterion("COST_BANDWIDTH <", value, "costBandwidth");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_BANDWIDTH <=", value, "costBandwidth");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthIn(List<BigDecimal> values) {
            addCriterion("COST_BANDWIDTH in", values, "costBandwidth");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthNotIn(List<BigDecimal> values) {
            addCriterion("COST_BANDWIDTH not in", values, "costBandwidth");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_BANDWIDTH between", value1, value2, "costBandwidth");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_BANDWIDTH not between", value1, value2, "costBandwidth");
            return (Criteria) this;
        }

        public Criteria andCostRockIncomeIsNull() {
            addCriterion("COST_ROCK_INCOME is null");
            return (Criteria) this;
        }

        public Criteria andCostRockIncomeIsNotNull() {
            addCriterion("COST_ROCK_INCOME is not null");
            return (Criteria) this;
        }

        public Criteria andCostRockIncomeEqualTo(BigDecimal value) {
            addCriterion("COST_ROCK_INCOME =", value, "costRockIncome");
            return (Criteria) this;
        }

        public Criteria andCostRockIncomeNotEqualTo(BigDecimal value) {
            addCriterion("COST_ROCK_INCOME <>", value, "costRockIncome");
            return (Criteria) this;
        }

        public Criteria andCostRockIncomeGreaterThan(BigDecimal value) {
            addCriterion("COST_ROCK_INCOME >", value, "costRockIncome");
            return (Criteria) this;
        }

        public Criteria andCostRockIncomeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_ROCK_INCOME >=", value, "costRockIncome");
            return (Criteria) this;
        }

        public Criteria andCostRockIncomeLessThan(BigDecimal value) {
            addCriterion("COST_ROCK_INCOME <", value, "costRockIncome");
            return (Criteria) this;
        }

        public Criteria andCostRockIncomeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_ROCK_INCOME <=", value, "costRockIncome");
            return (Criteria) this;
        }

        public Criteria andCostRockIncomeIn(List<BigDecimal> values) {
            addCriterion("COST_ROCK_INCOME in", values, "costRockIncome");
            return (Criteria) this;
        }

        public Criteria andCostRockIncomeNotIn(List<BigDecimal> values) {
            addCriterion("COST_ROCK_INCOME not in", values, "costRockIncome");
            return (Criteria) this;
        }

        public Criteria andCostRockIncomeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_ROCK_INCOME between", value1, value2, "costRockIncome");
            return (Criteria) this;
        }

        public Criteria andCostRockIncomeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_ROCK_INCOME not between", value1, value2, "costRockIncome");
            return (Criteria) this;
        }

        public Criteria andSoldRockFeeIsNull() {
            addCriterion("SOLD_ROCK_FEE is null");
            return (Criteria) this;
        }

        public Criteria andSoldRockFeeIsNotNull() {
            addCriterion("SOLD_ROCK_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andSoldRockFeeEqualTo(BigDecimal value) {
            addCriterion("SOLD_ROCK_FEE =", value, "soldRockFee");
            return (Criteria) this;
        }

        public Criteria andSoldRockFeeNotEqualTo(BigDecimal value) {
            addCriterion("SOLD_ROCK_FEE <>", value, "soldRockFee");
            return (Criteria) this;
        }

        public Criteria andSoldRockFeeGreaterThan(BigDecimal value) {
            addCriterion("SOLD_ROCK_FEE >", value, "soldRockFee");
            return (Criteria) this;
        }

        public Criteria andSoldRockFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SOLD_ROCK_FEE >=", value, "soldRockFee");
            return (Criteria) this;
        }

        public Criteria andSoldRockFeeLessThan(BigDecimal value) {
            addCriterion("SOLD_ROCK_FEE <", value, "soldRockFee");
            return (Criteria) this;
        }

        public Criteria andSoldRockFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SOLD_ROCK_FEE <=", value, "soldRockFee");
            return (Criteria) this;
        }

        public Criteria andSoldRockFeeIn(List<BigDecimal> values) {
            addCriterion("SOLD_ROCK_FEE in", values, "soldRockFee");
            return (Criteria) this;
        }

        public Criteria andSoldRockFeeNotIn(List<BigDecimal> values) {
            addCriterion("SOLD_ROCK_FEE not in", values, "soldRockFee");
            return (Criteria) this;
        }

        public Criteria andSoldRockFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SOLD_ROCK_FEE between", value1, value2, "soldRockFee");
            return (Criteria) this;
        }

        public Criteria andSoldRockFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SOLD_ROCK_FEE not between", value1, value2, "soldRockFee");
            return (Criteria) this;
        }

        public Criteria andAvgDepreciationYearIsNull() {
            addCriterion("AVG_DEPRECIATION_YEAR is null");
            return (Criteria) this;
        }

        public Criteria andAvgDepreciationYearIsNotNull() {
            addCriterion("AVG_DEPRECIATION_YEAR is not null");
            return (Criteria) this;
        }

        public Criteria andAvgDepreciationYearEqualTo(BigDecimal value) {
            addCriterion("AVG_DEPRECIATION_YEAR =", value, "avgDepreciationYear");
            return (Criteria) this;
        }

        public Criteria andAvgDepreciationYearNotEqualTo(BigDecimal value) {
            addCriterion("AVG_DEPRECIATION_YEAR <>", value, "avgDepreciationYear");
            return (Criteria) this;
        }

        public Criteria andAvgDepreciationYearGreaterThan(BigDecimal value) {
            addCriterion("AVG_DEPRECIATION_YEAR >", value, "avgDepreciationYear");
            return (Criteria) this;
        }

        public Criteria andAvgDepreciationYearGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AVG_DEPRECIATION_YEAR >=", value, "avgDepreciationYear");
            return (Criteria) this;
        }

        public Criteria andAvgDepreciationYearLessThan(BigDecimal value) {
            addCriterion("AVG_DEPRECIATION_YEAR <", value, "avgDepreciationYear");
            return (Criteria) this;
        }

        public Criteria andAvgDepreciationYearLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AVG_DEPRECIATION_YEAR <=", value, "avgDepreciationYear");
            return (Criteria) this;
        }

        public Criteria andAvgDepreciationYearIn(List<BigDecimal> values) {
            addCriterion("AVG_DEPRECIATION_YEAR in", values, "avgDepreciationYear");
            return (Criteria) this;
        }

        public Criteria andAvgDepreciationYearNotIn(List<BigDecimal> values) {
            addCriterion("AVG_DEPRECIATION_YEAR not in", values, "avgDepreciationYear");
            return (Criteria) this;
        }

        public Criteria andAvgDepreciationYearBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AVG_DEPRECIATION_YEAR between", value1, value2, "avgDepreciationYear");
            return (Criteria) this;
        }

        public Criteria andAvgDepreciationYearNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AVG_DEPRECIATION_YEAR not between", value1, value2, "avgDepreciationYear");
            return (Criteria) this;
        }

        public Criteria andCostProfitRateIsNull() {
            addCriterion("COST_PROFIT_RATE is null");
            return (Criteria) this;
        }

        public Criteria andCostProfitRateIsNotNull() {
            addCriterion("COST_PROFIT_RATE is not null");
            return (Criteria) this;
        }

        public Criteria andCostProfitRateEqualTo(BigDecimal value) {
            addCriterion("COST_PROFIT_RATE =", value, "costProfitRate");
            return (Criteria) this;
        }

        public Criteria andCostProfitRateNotEqualTo(BigDecimal value) {
            addCriterion("COST_PROFIT_RATE <>", value, "costProfitRate");
            return (Criteria) this;
        }

        public Criteria andCostProfitRateGreaterThan(BigDecimal value) {
            addCriterion("COST_PROFIT_RATE >", value, "costProfitRate");
            return (Criteria) this;
        }

        public Criteria andCostProfitRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_PROFIT_RATE >=", value, "costProfitRate");
            return (Criteria) this;
        }

        public Criteria andCostProfitRateLessThan(BigDecimal value) {
            addCriterion("COST_PROFIT_RATE <", value, "costProfitRate");
            return (Criteria) this;
        }

        public Criteria andCostProfitRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_PROFIT_RATE <=", value, "costProfitRate");
            return (Criteria) this;
        }

        public Criteria andCostProfitRateIn(List<BigDecimal> values) {
            addCriterion("COST_PROFIT_RATE in", values, "costProfitRate");
            return (Criteria) this;
        }

        public Criteria andCostProfitRateNotIn(List<BigDecimal> values) {
            addCriterion("COST_PROFIT_RATE not in", values, "costProfitRate");
            return (Criteria) this;
        }

        public Criteria andCostProfitRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_PROFIT_RATE between", value1, value2, "costProfitRate");
            return (Criteria) this;
        }

        public Criteria andCostProfitRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_PROFIT_RATE not between", value1, value2, "costProfitRate");
            return (Criteria) this;
        }

        public Criteria andCostCreateUserIsNull() {
            addCriterion("COST_CREATE_USER is null");
            return (Criteria) this;
        }

        public Criteria andCostCreateUserIsNotNull() {
            addCriterion("COST_CREATE_USER is not null");
            return (Criteria) this;
        }

        public Criteria andCostCreateUserEqualTo(String value) {
            addCriterion("COST_CREATE_USER =", value, "costCreateUser");
            return (Criteria) this;
        }

        public Criteria andCostCreateUserNotEqualTo(String value) {
            addCriterion("COST_CREATE_USER <>", value, "costCreateUser");
            return (Criteria) this;
        }

        public Criteria andCostCreateUserGreaterThan(String value) {
            addCriterion("COST_CREATE_USER >", value, "costCreateUser");
            return (Criteria) this;
        }

        public Criteria andCostCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("COST_CREATE_USER >=", value, "costCreateUser");
            return (Criteria) this;
        }

        public Criteria andCostCreateUserLessThan(String value) {
            addCriterion("COST_CREATE_USER <", value, "costCreateUser");
            return (Criteria) this;
        }

        public Criteria andCostCreateUserLessThanOrEqualTo(String value) {
            addCriterion("COST_CREATE_USER <=", value, "costCreateUser");
            return (Criteria) this;
        }

        public Criteria andCostCreateUserLike(String value) {
            addCriterion("COST_CREATE_USER like", value, "costCreateUser");
            return (Criteria) this;
        }

        public Criteria andCostCreateUserNotLike(String value) {
            addCriterion("COST_CREATE_USER not like", value, "costCreateUser");
            return (Criteria) this;
        }

        public Criteria andCostCreateUserIn(List<String> values) {
            addCriterion("COST_CREATE_USER in", values, "costCreateUser");
            return (Criteria) this;
        }

        public Criteria andCostCreateUserNotIn(List<String> values) {
            addCriterion("COST_CREATE_USER not in", values, "costCreateUser");
            return (Criteria) this;
        }

        public Criteria andCostCreateUserBetween(String value1, String value2) {
            addCriterion("COST_CREATE_USER between", value1, value2, "costCreateUser");
            return (Criteria) this;
        }

        public Criteria andCostCreateUserNotBetween(String value1, String value2) {
            addCriterion("COST_CREATE_USER not between", value1, value2, "costCreateUser");
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