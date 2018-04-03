package com.idc.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CostRackBaseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CostRackBaseExample() {
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

        public Criteria andRackCodeIsNull() {
            addCriterion("RACK_CODE is null");
            return (Criteria) this;
        }

        public Criteria andRackCodeIsNotNull() {
            addCriterion("RACK_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andRackCodeEqualTo(String value) {
            addCriterion("RACK_CODE =", value, "rackCode");
            return (Criteria) this;
        }

        public Criteria andRackCodeNotEqualTo(String value) {
            addCriterion("RACK_CODE <>", value, "rackCode");
            return (Criteria) this;
        }

        public Criteria andRackCodeGreaterThan(String value) {
            addCriterion("RACK_CODE >", value, "rackCode");
            return (Criteria) this;
        }

        public Criteria andRackCodeGreaterThanOrEqualTo(String value) {
            addCriterion("RACK_CODE >=", value, "rackCode");
            return (Criteria) this;
        }

        public Criteria andRackCodeLessThan(String value) {
            addCriterion("RACK_CODE <", value, "rackCode");
            return (Criteria) this;
        }

        public Criteria andRackCodeLessThanOrEqualTo(String value) {
            addCriterion("RACK_CODE <=", value, "rackCode");
            return (Criteria) this;
        }

        public Criteria andRackCodeLike(String value) {
            addCriterion("RACK_CODE like", value, "rackCode");
            return (Criteria) this;
        }

        public Criteria andRackCodeNotLike(String value) {
            addCriterion("RACK_CODE not like", value, "rackCode");
            return (Criteria) this;
        }

        public Criteria andRackCodeIn(List<String> values) {
            addCriterion("RACK_CODE in", values, "rackCode");
            return (Criteria) this;
        }

        public Criteria andRackCodeNotIn(List<String> values) {
            addCriterion("RACK_CODE not in", values, "rackCode");
            return (Criteria) this;
        }

        public Criteria andRackCodeBetween(String value1, String value2) {
            addCriterion("RACK_CODE between", value1, value2, "rackCode");
            return (Criteria) this;
        }

        public Criteria andRackCodeNotBetween(String value1, String value2) {
            addCriterion("RACK_CODE not between", value1, value2, "rackCode");
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

        public Criteria andCostMaintenanceFeeIsNull() {
            addCriterion("COST_MAINTENANCE_FEE is null");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceFeeIsNotNull() {
            addCriterion("COST_MAINTENANCE_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceFeeEqualTo(BigDecimal value) {
            addCriterion("COST_MAINTENANCE_FEE =", value, "costMaintenanceFee");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceFeeNotEqualTo(BigDecimal value) {
            addCriterion("COST_MAINTENANCE_FEE <>", value, "costMaintenanceFee");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceFeeGreaterThan(BigDecimal value) {
            addCriterion("COST_MAINTENANCE_FEE >", value, "costMaintenanceFee");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_MAINTENANCE_FEE >=", value, "costMaintenanceFee");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceFeeLessThan(BigDecimal value) {
            addCriterion("COST_MAINTENANCE_FEE <", value, "costMaintenanceFee");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_MAINTENANCE_FEE <=", value, "costMaintenanceFee");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceFeeIn(List<BigDecimal> values) {
            addCriterion("COST_MAINTENANCE_FEE in", values, "costMaintenanceFee");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceFeeNotIn(List<BigDecimal> values) {
            addCriterion("COST_MAINTENANCE_FEE not in", values, "costMaintenanceFee");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_MAINTENANCE_FEE between", value1, value2, "costMaintenanceFee");
            return (Criteria) this;
        }

        public Criteria andCostMaintenanceFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_MAINTENANCE_FEE not between", value1, value2, "costMaintenanceFee");
            return (Criteria) this;
        }

        public Criteria andCostInvestIsNull() {
            addCriterion("COST_INVEST is null");
            return (Criteria) this;
        }

        public Criteria andCostInvestIsNotNull() {
            addCriterion("COST_INVEST is not null");
            return (Criteria) this;
        }

        public Criteria andCostInvestEqualTo(BigDecimal value) {
            addCriterion("COST_INVEST =", value, "costInvest");
            return (Criteria) this;
        }

        public Criteria andCostInvestNotEqualTo(BigDecimal value) {
            addCriterion("COST_INVEST <>", value, "costInvest");
            return (Criteria) this;
        }

        public Criteria andCostInvestGreaterThan(BigDecimal value) {
            addCriterion("COST_INVEST >", value, "costInvest");
            return (Criteria) this;
        }

        public Criteria andCostInvestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_INVEST >=", value, "costInvest");
            return (Criteria) this;
        }

        public Criteria andCostInvestLessThan(BigDecimal value) {
            addCriterion("COST_INVEST <", value, "costInvest");
            return (Criteria) this;
        }

        public Criteria andCostInvestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_INVEST <=", value, "costInvest");
            return (Criteria) this;
        }

        public Criteria andCostInvestIn(List<BigDecimal> values) {
            addCriterion("COST_INVEST in", values, "costInvest");
            return (Criteria) this;
        }

        public Criteria andCostInvestNotIn(List<BigDecimal> values) {
            addCriterion("COST_INVEST not in", values, "costInvest");
            return (Criteria) this;
        }

        public Criteria andCostInvestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_INVEST between", value1, value2, "costInvest");
            return (Criteria) this;
        }

        public Criteria andCostInvestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_INVEST not between", value1, value2, "costInvest");
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