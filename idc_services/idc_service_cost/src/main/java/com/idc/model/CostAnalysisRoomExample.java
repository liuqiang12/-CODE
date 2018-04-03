package com.idc.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CostAnalysisRoomExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CostAnalysisRoomExample() {
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

        public Criteria andCostAnalysisRoomIdIsNull() {
            addCriterion("COST_ANALYSIS_ROOM_ID is null");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRoomIdIsNotNull() {
            addCriterion("COST_ANALYSIS_ROOM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRoomIdEqualTo(String value) {
            addCriterion("COST_ANALYSIS_ROOM_ID =", value, "costAnalysisRoomId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRoomIdNotEqualTo(String value) {
            addCriterion("COST_ANALYSIS_ROOM_ID <>", value, "costAnalysisRoomId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRoomIdGreaterThan(String value) {
            addCriterion("COST_ANALYSIS_ROOM_ID >", value, "costAnalysisRoomId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRoomIdGreaterThanOrEqualTo(String value) {
            addCriterion("COST_ANALYSIS_ROOM_ID >=", value, "costAnalysisRoomId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRoomIdLessThan(String value) {
            addCriterion("COST_ANALYSIS_ROOM_ID <", value, "costAnalysisRoomId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRoomIdLessThanOrEqualTo(String value) {
            addCriterion("COST_ANALYSIS_ROOM_ID <=", value, "costAnalysisRoomId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRoomIdLike(String value) {
            addCriterion("COST_ANALYSIS_ROOM_ID like", value, "costAnalysisRoomId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRoomIdNotLike(String value) {
            addCriterion("COST_ANALYSIS_ROOM_ID not like", value, "costAnalysisRoomId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRoomIdIn(List<String> values) {
            addCriterion("COST_ANALYSIS_ROOM_ID in", values, "costAnalysisRoomId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRoomIdNotIn(List<String> values) {
            addCriterion("COST_ANALYSIS_ROOM_ID not in", values, "costAnalysisRoomId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRoomIdBetween(String value1, String value2) {
            addCriterion("COST_ANALYSIS_ROOM_ID between", value1, value2, "costAnalysisRoomId");
            return (Criteria) this;
        }

        public Criteria andCostAnalysisRoomIdNotBetween(String value1, String value2) {
            addCriterion("COST_ANALYSIS_ROOM_ID not between", value1, value2, "costAnalysisRoomId");
            return (Criteria) this;
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

        public Criteria andCostIncomeIsNull() {
            addCriterion("COST_INCOME is null");
            return (Criteria) this;
        }

        public Criteria andCostIncomeIsNotNull() {
            addCriterion("COST_INCOME is not null");
            return (Criteria) this;
        }

        public Criteria andCostIncomeEqualTo(BigDecimal value) {
            addCriterion("COST_INCOME =", value, "costIncome");
            return (Criteria) this;
        }

        public Criteria andCostIncomeNotEqualTo(BigDecimal value) {
            addCriterion("COST_INCOME <>", value, "costIncome");
            return (Criteria) this;
        }

        public Criteria andCostIncomeGreaterThan(BigDecimal value) {
            addCriterion("COST_INCOME >", value, "costIncome");
            return (Criteria) this;
        }

        public Criteria andCostIncomeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_INCOME >=", value, "costIncome");
            return (Criteria) this;
        }

        public Criteria andCostIncomeLessThan(BigDecimal value) {
            addCriterion("COST_INCOME <", value, "costIncome");
            return (Criteria) this;
        }

        public Criteria andCostIncomeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_INCOME <=", value, "costIncome");
            return (Criteria) this;
        }

        public Criteria andCostIncomeIn(List<BigDecimal> values) {
            addCriterion("COST_INCOME in", values, "costIncome");
            return (Criteria) this;
        }

        public Criteria andCostIncomeNotIn(List<BigDecimal> values) {
            addCriterion("COST_INCOME not in", values, "costIncome");
            return (Criteria) this;
        }

        public Criteria andCostIncomeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_INCOME between", value1, value2, "costIncome");
            return (Criteria) this;
        }

        public Criteria andCostIncomeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_INCOME not between", value1, value2, "costIncome");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthIncomeIsNull() {
            addCriterion("COST_BANDWIDTH_INCOME is null");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthIncomeIsNotNull() {
            addCriterion("COST_BANDWIDTH_INCOME is not null");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthIncomeEqualTo(BigDecimal value) {
            addCriterion("COST_BANDWIDTH_INCOME =", value, "costBandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthIncomeNotEqualTo(BigDecimal value) {
            addCriterion("COST_BANDWIDTH_INCOME <>", value, "costBandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthIncomeGreaterThan(BigDecimal value) {
            addCriterion("COST_BANDWIDTH_INCOME >", value, "costBandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthIncomeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_BANDWIDTH_INCOME >=", value, "costBandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthIncomeLessThan(BigDecimal value) {
            addCriterion("COST_BANDWIDTH_INCOME <", value, "costBandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthIncomeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_BANDWIDTH_INCOME <=", value, "costBandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthIncomeIn(List<BigDecimal> values) {
            addCriterion("COST_BANDWIDTH_INCOME in", values, "costBandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthIncomeNotIn(List<BigDecimal> values) {
            addCriterion("COST_BANDWIDTH_INCOME not in", values, "costBandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthIncomeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_BANDWIDTH_INCOME between", value1, value2, "costBandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andCostBandwidthIncomeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_BANDWIDTH_INCOME not between", value1, value2, "costBandwidthIncome");
            return (Criteria) this;
        }

        public Criteria andCostOtherIncomeIsNull() {
            addCriterion("COST_OTHER_INCOME is null");
            return (Criteria) this;
        }

        public Criteria andCostOtherIncomeIsNotNull() {
            addCriterion("COST_OTHER_INCOME is not null");
            return (Criteria) this;
        }

        public Criteria andCostOtherIncomeEqualTo(BigDecimal value) {
            addCriterion("COST_OTHER_INCOME =", value, "costOtherIncome");
            return (Criteria) this;
        }

        public Criteria andCostOtherIncomeNotEqualTo(BigDecimal value) {
            addCriterion("COST_OTHER_INCOME <>", value, "costOtherIncome");
            return (Criteria) this;
        }

        public Criteria andCostOtherIncomeGreaterThan(BigDecimal value) {
            addCriterion("COST_OTHER_INCOME >", value, "costOtherIncome");
            return (Criteria) this;
        }

        public Criteria andCostOtherIncomeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_OTHER_INCOME >=", value, "costOtherIncome");
            return (Criteria) this;
        }

        public Criteria andCostOtherIncomeLessThan(BigDecimal value) {
            addCriterion("COST_OTHER_INCOME <", value, "costOtherIncome");
            return (Criteria) this;
        }

        public Criteria andCostOtherIncomeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_OTHER_INCOME <=", value, "costOtherIncome");
            return (Criteria) this;
        }

        public Criteria andCostOtherIncomeIn(List<BigDecimal> values) {
            addCriterion("COST_OTHER_INCOME in", values, "costOtherIncome");
            return (Criteria) this;
        }

        public Criteria andCostOtherIncomeNotIn(List<BigDecimal> values) {
            addCriterion("COST_OTHER_INCOME not in", values, "costOtherIncome");
            return (Criteria) this;
        }

        public Criteria andCostOtherIncomeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_OTHER_INCOME between", value1, value2, "costOtherIncome");
            return (Criteria) this;
        }

        public Criteria andCostOtherIncomeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_OTHER_INCOME not between", value1, value2, "costOtherIncome");
            return (Criteria) this;
        }

        public Criteria andCostOperatingProfitIsNull() {
            addCriterion("COST_OPERATING_PROFIT is null");
            return (Criteria) this;
        }

        public Criteria andCostOperatingProfitIsNotNull() {
            addCriterion("COST_OPERATING_PROFIT is not null");
            return (Criteria) this;
        }

        public Criteria andCostOperatingProfitEqualTo(BigDecimal value) {
            addCriterion("COST_OPERATING_PROFIT =", value, "costOperatingProfit");
            return (Criteria) this;
        }

        public Criteria andCostOperatingProfitNotEqualTo(BigDecimal value) {
            addCriterion("COST_OPERATING_PROFIT <>", value, "costOperatingProfit");
            return (Criteria) this;
        }

        public Criteria andCostOperatingProfitGreaterThan(BigDecimal value) {
            addCriterion("COST_OPERATING_PROFIT >", value, "costOperatingProfit");
            return (Criteria) this;
        }

        public Criteria andCostOperatingProfitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_OPERATING_PROFIT >=", value, "costOperatingProfit");
            return (Criteria) this;
        }

        public Criteria andCostOperatingProfitLessThan(BigDecimal value) {
            addCriterion("COST_OPERATING_PROFIT <", value, "costOperatingProfit");
            return (Criteria) this;
        }

        public Criteria andCostOperatingProfitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_OPERATING_PROFIT <=", value, "costOperatingProfit");
            return (Criteria) this;
        }

        public Criteria andCostOperatingProfitIn(List<BigDecimal> values) {
            addCriterion("COST_OPERATING_PROFIT in", values, "costOperatingProfit");
            return (Criteria) this;
        }

        public Criteria andCostOperatingProfitNotIn(List<BigDecimal> values) {
            addCriterion("COST_OPERATING_PROFIT not in", values, "costOperatingProfit");
            return (Criteria) this;
        }

        public Criteria andCostOperatingProfitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_OPERATING_PROFIT between", value1, value2, "costOperatingProfit");
            return (Criteria) this;
        }

        public Criteria andCostOperatingProfitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_OPERATING_PROFIT not between", value1, value2, "costOperatingProfit");
            return (Criteria) this;
        }

        public Criteria andCostIncomeProfitIsNull() {
            addCriterion("COST_INCOME_PROFIT is null");
            return (Criteria) this;
        }

        public Criteria andCostIncomeProfitIsNotNull() {
            addCriterion("COST_INCOME_PROFIT is not null");
            return (Criteria) this;
        }

        public Criteria andCostIncomeProfitEqualTo(BigDecimal value) {
            addCriterion("COST_INCOME_PROFIT =", value, "costIncomeProfit");
            return (Criteria) this;
        }

        public Criteria andCostIncomeProfitNotEqualTo(BigDecimal value) {
            addCriterion("COST_INCOME_PROFIT <>", value, "costIncomeProfit");
            return (Criteria) this;
        }

        public Criteria andCostIncomeProfitGreaterThan(BigDecimal value) {
            addCriterion("COST_INCOME_PROFIT >", value, "costIncomeProfit");
            return (Criteria) this;
        }

        public Criteria andCostIncomeProfitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_INCOME_PROFIT >=", value, "costIncomeProfit");
            return (Criteria) this;
        }

        public Criteria andCostIncomeProfitLessThan(BigDecimal value) {
            addCriterion("COST_INCOME_PROFIT <", value, "costIncomeProfit");
            return (Criteria) this;
        }

        public Criteria andCostIncomeProfitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_INCOME_PROFIT <=", value, "costIncomeProfit");
            return (Criteria) this;
        }

        public Criteria andCostIncomeProfitIn(List<BigDecimal> values) {
            addCriterion("COST_INCOME_PROFIT in", values, "costIncomeProfit");
            return (Criteria) this;
        }

        public Criteria andCostIncomeProfitNotIn(List<BigDecimal> values) {
            addCriterion("COST_INCOME_PROFIT not in", values, "costIncomeProfit");
            return (Criteria) this;
        }

        public Criteria andCostIncomeProfitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_INCOME_PROFIT between", value1, value2, "costIncomeProfit");
            return (Criteria) this;
        }

        public Criteria andCostIncomeProfitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_INCOME_PROFIT not between", value1, value2, "costIncomeProfit");
            return (Criteria) this;
        }

        public Criteria andCostRingGrowthIsNull() {
            addCriterion("COST_RING_GROWTH is null");
            return (Criteria) this;
        }

        public Criteria andCostRingGrowthIsNotNull() {
            addCriterion("COST_RING_GROWTH is not null");
            return (Criteria) this;
        }

        public Criteria andCostRingGrowthEqualTo(BigDecimal value) {
            addCriterion("COST_RING_GROWTH =", value, "costRingGrowth");
            return (Criteria) this;
        }

        public Criteria andCostRingGrowthNotEqualTo(BigDecimal value) {
            addCriterion("COST_RING_GROWTH <>", value, "costRingGrowth");
            return (Criteria) this;
        }

        public Criteria andCostRingGrowthGreaterThan(BigDecimal value) {
            addCriterion("COST_RING_GROWTH >", value, "costRingGrowth");
            return (Criteria) this;
        }

        public Criteria andCostRingGrowthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_RING_GROWTH >=", value, "costRingGrowth");
            return (Criteria) this;
        }

        public Criteria andCostRingGrowthLessThan(BigDecimal value) {
            addCriterion("COST_RING_GROWTH <", value, "costRingGrowth");
            return (Criteria) this;
        }

        public Criteria andCostRingGrowthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_RING_GROWTH <=", value, "costRingGrowth");
            return (Criteria) this;
        }

        public Criteria andCostRingGrowthIn(List<BigDecimal> values) {
            addCriterion("COST_RING_GROWTH in", values, "costRingGrowth");
            return (Criteria) this;
        }

        public Criteria andCostRingGrowthNotIn(List<BigDecimal> values) {
            addCriterion("COST_RING_GROWTH not in", values, "costRingGrowth");
            return (Criteria) this;
        }

        public Criteria andCostRingGrowthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_RING_GROWTH between", value1, value2, "costRingGrowth");
            return (Criteria) this;
        }

        public Criteria andCostRingGrowthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_RING_GROWTH not between", value1, value2, "costRingGrowth");
            return (Criteria) this;
        }

        public Criteria andCostEnergyUseRateIsNull() {
            addCriterion("COST_ENERGY_USE_RATE is null");
            return (Criteria) this;
        }

        public Criteria andCostEnergyUseRateIsNotNull() {
            addCriterion("COST_ENERGY_USE_RATE is not null");
            return (Criteria) this;
        }

        public Criteria andCostEnergyUseRateEqualTo(BigDecimal value) {
            addCriterion("COST_ENERGY_USE_RATE =", value, "costEnergyUseRate");
            return (Criteria) this;
        }

        public Criteria andCostEnergyUseRateNotEqualTo(BigDecimal value) {
            addCriterion("COST_ENERGY_USE_RATE <>", value, "costEnergyUseRate");
            return (Criteria) this;
        }

        public Criteria andCostEnergyUseRateGreaterThan(BigDecimal value) {
            addCriterion("COST_ENERGY_USE_RATE >", value, "costEnergyUseRate");
            return (Criteria) this;
        }

        public Criteria andCostEnergyUseRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_ENERGY_USE_RATE >=", value, "costEnergyUseRate");
            return (Criteria) this;
        }

        public Criteria andCostEnergyUseRateLessThan(BigDecimal value) {
            addCriterion("COST_ENERGY_USE_RATE <", value, "costEnergyUseRate");
            return (Criteria) this;
        }

        public Criteria andCostEnergyUseRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_ENERGY_USE_RATE <=", value, "costEnergyUseRate");
            return (Criteria) this;
        }

        public Criteria andCostEnergyUseRateIn(List<BigDecimal> values) {
            addCriterion("COST_ENERGY_USE_RATE in", values, "costEnergyUseRate");
            return (Criteria) this;
        }

        public Criteria andCostEnergyUseRateNotIn(List<BigDecimal> values) {
            addCriterion("COST_ENERGY_USE_RATE not in", values, "costEnergyUseRate");
            return (Criteria) this;
        }

        public Criteria andCostEnergyUseRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_ENERGY_USE_RATE between", value1, value2, "costEnergyUseRate");
            return (Criteria) this;
        }

        public Criteria andCostEnergyUseRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_ENERGY_USE_RATE not between", value1, value2, "costEnergyUseRate");
            return (Criteria) this;
        }

        public Criteria andCostFrameUseRateIsNull() {
            addCriterion("COST_FRAME_USE_RATE is null");
            return (Criteria) this;
        }

        public Criteria andCostFrameUseRateIsNotNull() {
            addCriterion("COST_FRAME_USE_RATE is not null");
            return (Criteria) this;
        }

        public Criteria andCostFrameUseRateEqualTo(BigDecimal value) {
            addCriterion("COST_FRAME_USE_RATE =", value, "costFrameUseRate");
            return (Criteria) this;
        }

        public Criteria andCostFrameUseRateNotEqualTo(BigDecimal value) {
            addCriterion("COST_FRAME_USE_RATE <>", value, "costFrameUseRate");
            return (Criteria) this;
        }

        public Criteria andCostFrameUseRateGreaterThan(BigDecimal value) {
            addCriterion("COST_FRAME_USE_RATE >", value, "costFrameUseRate");
            return (Criteria) this;
        }

        public Criteria andCostFrameUseRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_FRAME_USE_RATE >=", value, "costFrameUseRate");
            return (Criteria) this;
        }

        public Criteria andCostFrameUseRateLessThan(BigDecimal value) {
            addCriterion("COST_FRAME_USE_RATE <", value, "costFrameUseRate");
            return (Criteria) this;
        }

        public Criteria andCostFrameUseRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_FRAME_USE_RATE <=", value, "costFrameUseRate");
            return (Criteria) this;
        }

        public Criteria andCostFrameUseRateIn(List<BigDecimal> values) {
            addCriterion("COST_FRAME_USE_RATE in", values, "costFrameUseRate");
            return (Criteria) this;
        }

        public Criteria andCostFrameUseRateNotIn(List<BigDecimal> values) {
            addCriterion("COST_FRAME_USE_RATE not in", values, "costFrameUseRate");
            return (Criteria) this;
        }

        public Criteria andCostFrameUseRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_FRAME_USE_RATE between", value1, value2, "costFrameUseRate");
            return (Criteria) this;
        }

        public Criteria andCostFrameUseRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_FRAME_USE_RATE not between", value1, value2, "costFrameUseRate");
            return (Criteria) this;
        }

        public Criteria andCostElectricMatchingRateIsNull() {
            addCriterion("COST_ELECTRIC_MATCHING_RATE is null");
            return (Criteria) this;
        }

        public Criteria andCostElectricMatchingRateIsNotNull() {
            addCriterion("COST_ELECTRIC_MATCHING_RATE is not null");
            return (Criteria) this;
        }

        public Criteria andCostElectricMatchingRateEqualTo(BigDecimal value) {
            addCriterion("COST_ELECTRIC_MATCHING_RATE =", value, "costElectricMatchingRate");
            return (Criteria) this;
        }

        public Criteria andCostElectricMatchingRateNotEqualTo(BigDecimal value) {
            addCriterion("COST_ELECTRIC_MATCHING_RATE <>", value, "costElectricMatchingRate");
            return (Criteria) this;
        }

        public Criteria andCostElectricMatchingRateGreaterThan(BigDecimal value) {
            addCriterion("COST_ELECTRIC_MATCHING_RATE >", value, "costElectricMatchingRate");
            return (Criteria) this;
        }

        public Criteria andCostElectricMatchingRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_ELECTRIC_MATCHING_RATE >=", value, "costElectricMatchingRate");
            return (Criteria) this;
        }

        public Criteria andCostElectricMatchingRateLessThan(BigDecimal value) {
            addCriterion("COST_ELECTRIC_MATCHING_RATE <", value, "costElectricMatchingRate");
            return (Criteria) this;
        }

        public Criteria andCostElectricMatchingRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_ELECTRIC_MATCHING_RATE <=", value, "costElectricMatchingRate");
            return (Criteria) this;
        }

        public Criteria andCostElectricMatchingRateIn(List<BigDecimal> values) {
            addCriterion("COST_ELECTRIC_MATCHING_RATE in", values, "costElectricMatchingRate");
            return (Criteria) this;
        }

        public Criteria andCostElectricMatchingRateNotIn(List<BigDecimal> values) {
            addCriterion("COST_ELECTRIC_MATCHING_RATE not in", values, "costElectricMatchingRate");
            return (Criteria) this;
        }

        public Criteria andCostElectricMatchingRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_ELECTRIC_MATCHING_RATE between", value1, value2, "costElectricMatchingRate");
            return (Criteria) this;
        }

        public Criteria andCostElectricMatchingRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_ELECTRIC_MATCHING_RATE not between", value1, value2, "costElectricMatchingRate");
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