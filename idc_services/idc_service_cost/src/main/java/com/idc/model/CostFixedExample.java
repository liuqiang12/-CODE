package com.idc.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CostFixedExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CostFixedExample() {
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

        public Criteria andCostFixedIsNull() {
            addCriterion("COST_FIXED is null");
            return (Criteria) this;
        }

        public Criteria andCostFixedIsNotNull() {
            addCriterion("COST_FIXED is not null");
            return (Criteria) this;
        }

        public Criteria andCostFixedEqualTo(BigDecimal value) {
            addCriterion("COST_FIXED =", value, "costFixed");
            return (Criteria) this;
        }

        public Criteria andCostFixedNotEqualTo(BigDecimal value) {
            addCriterion("COST_FIXED <>", value, "costFixed");
            return (Criteria) this;
        }

        public Criteria andCostFixedGreaterThan(BigDecimal value) {
            addCriterion("COST_FIXED >", value, "costFixed");
            return (Criteria) this;
        }

        public Criteria andCostFixedGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_FIXED >=", value, "costFixed");
            return (Criteria) this;
        }

        public Criteria andCostFixedLessThan(BigDecimal value) {
            addCriterion("COST_FIXED <", value, "costFixed");
            return (Criteria) this;
        }

        public Criteria andCostFixedLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_FIXED <=", value, "costFixed");
            return (Criteria) this;
        }

        public Criteria andCostFixedIn(List<BigDecimal> values) {
            addCriterion("COST_FIXED in", values, "costFixed");
            return (Criteria) this;
        }

        public Criteria andCostFixedNotIn(List<BigDecimal> values) {
            addCriterion("COST_FIXED not in", values, "costFixed");
            return (Criteria) this;
        }

        public Criteria andCostFixedBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_FIXED between", value1, value2, "costFixed");
            return (Criteria) this;
        }

        public Criteria andCostFixedNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_FIXED not between", value1, value2, "costFixed");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevIsNull() {
            addCriterion("COST_BASE_DEV is null");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevIsNotNull() {
            addCriterion("COST_BASE_DEV is not null");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevEqualTo(BigDecimal value) {
            addCriterion("COST_BASE_DEV =", value, "costBaseDev");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevNotEqualTo(BigDecimal value) {
            addCriterion("COST_BASE_DEV <>", value, "costBaseDev");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevGreaterThan(BigDecimal value) {
            addCriterion("COST_BASE_DEV >", value, "costBaseDev");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_BASE_DEV >=", value, "costBaseDev");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevLessThan(BigDecimal value) {
            addCriterion("COST_BASE_DEV <", value, "costBaseDev");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_BASE_DEV <=", value, "costBaseDev");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevIn(List<BigDecimal> values) {
            addCriterion("COST_BASE_DEV in", values, "costBaseDev");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevNotIn(List<BigDecimal> values) {
            addCriterion("COST_BASE_DEV not in", values, "costBaseDev");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_BASE_DEV between", value1, value2, "costBaseDev");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_BASE_DEV not between", value1, value2, "costBaseDev");
            return (Criteria) this;
        }

        public Criteria andCostItDevIsNull() {
            addCriterion("COST_IT_DEV is null");
            return (Criteria) this;
        }

        public Criteria andCostItDevIsNotNull() {
            addCriterion("COST_IT_DEV is not null");
            return (Criteria) this;
        }

        public Criteria andCostItDevEqualTo(BigDecimal value) {
            addCriterion("COST_IT_DEV =", value, "costItDev");
            return (Criteria) this;
        }

        public Criteria andCostItDevNotEqualTo(BigDecimal value) {
            addCriterion("COST_IT_DEV <>", value, "costItDev");
            return (Criteria) this;
        }

        public Criteria andCostItDevGreaterThan(BigDecimal value) {
            addCriterion("COST_IT_DEV >", value, "costItDev");
            return (Criteria) this;
        }

        public Criteria andCostItDevGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_IT_DEV >=", value, "costItDev");
            return (Criteria) this;
        }

        public Criteria andCostItDevLessThan(BigDecimal value) {
            addCriterion("COST_IT_DEV <", value, "costItDev");
            return (Criteria) this;
        }

        public Criteria andCostItDevLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_IT_DEV <=", value, "costItDev");
            return (Criteria) this;
        }

        public Criteria andCostItDevIn(List<BigDecimal> values) {
            addCriterion("COST_IT_DEV in", values, "costItDev");
            return (Criteria) this;
        }

        public Criteria andCostItDevNotIn(List<BigDecimal> values) {
            addCriterion("COST_IT_DEV not in", values, "costItDev");
            return (Criteria) this;
        }

        public Criteria andCostItDevBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_IT_DEV between", value1, value2, "costItDev");
            return (Criteria) this;
        }

        public Criteria andCostItDevNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_IT_DEV not between", value1, value2, "costItDev");
            return (Criteria) this;
        }

        public Criteria andUnitTimeIsNull() {
            addCriterion("UNIT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUnitTimeIsNotNull() {
            addCriterion("UNIT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUnitTimeEqualTo(String value) {
            addCriterion("UNIT_TIME =", value, "unitTime");
            return (Criteria) this;
        }

        public Criteria andUnitTimeNotEqualTo(String value) {
            addCriterion("UNIT_TIME <>", value, "unitTime");
            return (Criteria) this;
        }

        public Criteria andUnitTimeGreaterThan(String value) {
            addCriterion("UNIT_TIME >", value, "unitTime");
            return (Criteria) this;
        }

        public Criteria andUnitTimeGreaterThanOrEqualTo(String value) {
            addCriterion("UNIT_TIME >=", value, "unitTime");
            return (Criteria) this;
        }

        public Criteria andUnitTimeLessThan(String value) {
            addCriterion("UNIT_TIME <", value, "unitTime");
            return (Criteria) this;
        }

        public Criteria andUnitTimeLessThanOrEqualTo(String value) {
            addCriterion("UNIT_TIME <=", value, "unitTime");
            return (Criteria) this;
        }

        public Criteria andUnitTimeLike(String value) {
            addCriterion("UNIT_TIME like", value, "unitTime");
            return (Criteria) this;
        }

        public Criteria andUnitTimeNotLike(String value) {
            addCriterion("UNIT_TIME not like", value, "unitTime");
            return (Criteria) this;
        }

        public Criteria andUnitTimeIn(List<String> values) {
            addCriterion("UNIT_TIME in", values, "unitTime");
            return (Criteria) this;
        }

        public Criteria andUnitTimeNotIn(List<String> values) {
            addCriterion("UNIT_TIME not in", values, "unitTime");
            return (Criteria) this;
        }

        public Criteria andUnitTimeBetween(String value1, String value2) {
            addCriterion("UNIT_TIME between", value1, value2, "unitTime");
            return (Criteria) this;
        }

        public Criteria andUnitTimeNotBetween(String value1, String value2) {
            addCriterion("UNIT_TIME not between", value1, value2, "unitTime");
            return (Criteria) this;
        }

        public Criteria andObjectCodeIsNull() {
            addCriterion("OBJECT_CODE is null");
            return (Criteria) this;
        }

        public Criteria andObjectCodeIsNotNull() {
            addCriterion("OBJECT_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andObjectCodeEqualTo(String value) {
            addCriterion("OBJECT_CODE =", value, "objectCode");
            return (Criteria) this;
        }

        public Criteria andObjectCodeNotEqualTo(String value) {
            addCriterion("OBJECT_CODE <>", value, "objectCode");
            return (Criteria) this;
        }

        public Criteria andObjectCodeGreaterThan(String value) {
            addCriterion("OBJECT_CODE >", value, "objectCode");
            return (Criteria) this;
        }

        public Criteria andObjectCodeGreaterThanOrEqualTo(String value) {
            addCriterion("OBJECT_CODE >=", value, "objectCode");
            return (Criteria) this;
        }

        public Criteria andObjectCodeLessThan(String value) {
            addCriterion("OBJECT_CODE <", value, "objectCode");
            return (Criteria) this;
        }

        public Criteria andObjectCodeLessThanOrEqualTo(String value) {
            addCriterion("OBJECT_CODE <=", value, "objectCode");
            return (Criteria) this;
        }

        public Criteria andObjectCodeLike(String value) {
            addCriterion("OBJECT_CODE like", value, "objectCode");
            return (Criteria) this;
        }

        public Criteria andObjectCodeNotLike(String value) {
            addCriterion("OBJECT_CODE not like", value, "objectCode");
            return (Criteria) this;
        }

        public Criteria andObjectCodeIn(List<String> values) {
            addCriterion("OBJECT_CODE in", values, "objectCode");
            return (Criteria) this;
        }

        public Criteria andObjectCodeNotIn(List<String> values) {
            addCriterion("OBJECT_CODE not in", values, "objectCode");
            return (Criteria) this;
        }

        public Criteria andObjectCodeBetween(String value1, String value2) {
            addCriterion("OBJECT_CODE between", value1, value2, "objectCode");
            return (Criteria) this;
        }

        public Criteria andObjectCodeNotBetween(String value1, String value2) {
            addCriterion("OBJECT_CODE not between", value1, value2, "objectCode");
            return (Criteria) this;
        }

        public Criteria andCostFixedTypeIsNull() {
            addCriterion("COST_FIXED_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andCostFixedTypeIsNotNull() {
            addCriterion("COST_FIXED_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andCostFixedTypeEqualTo(String value) {
            addCriterion("COST_FIXED_TYPE =", value, "costFixedType");
            return (Criteria) this;
        }

        public Criteria andCostFixedTypeNotEqualTo(String value) {
            addCriterion("COST_FIXED_TYPE <>", value, "costFixedType");
            return (Criteria) this;
        }

        public Criteria andCostFixedTypeGreaterThan(String value) {
            addCriterion("COST_FIXED_TYPE >", value, "costFixedType");
            return (Criteria) this;
        }

        public Criteria andCostFixedTypeGreaterThanOrEqualTo(String value) {
            addCriterion("COST_FIXED_TYPE >=", value, "costFixedType");
            return (Criteria) this;
        }

        public Criteria andCostFixedTypeLessThan(String value) {
            addCriterion("COST_FIXED_TYPE <", value, "costFixedType");
            return (Criteria) this;
        }

        public Criteria andCostFixedTypeLessThanOrEqualTo(String value) {
            addCriterion("COST_FIXED_TYPE <=", value, "costFixedType");
            return (Criteria) this;
        }

        public Criteria andCostFixedTypeLike(String value) {
            addCriterion("COST_FIXED_TYPE like", value, "costFixedType");
            return (Criteria) this;
        }

        public Criteria andCostFixedTypeNotLike(String value) {
            addCriterion("COST_FIXED_TYPE not like", value, "costFixedType");
            return (Criteria) this;
        }

        public Criteria andCostFixedTypeIn(List<String> values) {
            addCriterion("COST_FIXED_TYPE in", values, "costFixedType");
            return (Criteria) this;
        }

        public Criteria andCostFixedTypeNotIn(List<String> values) {
            addCriterion("COST_FIXED_TYPE not in", values, "costFixedType");
            return (Criteria) this;
        }

        public Criteria andCostFixedTypeBetween(String value1, String value2) {
            addCriterion("COST_FIXED_TYPE between", value1, value2, "costFixedType");
            return (Criteria) this;
        }

        public Criteria andCostFixedTypeNotBetween(String value1, String value2) {
            addCriterion("COST_FIXED_TYPE not between", value1, value2, "costFixedType");
            return (Criteria) this;
        }

        public Criteria andCostFixedAvgIsNull() {
            addCriterion("COST_FIXED_AVG is null");
            return (Criteria) this;
        }

        public Criteria andCostFixedAvgIsNotNull() {
            addCriterion("COST_FIXED_AVG is not null");
            return (Criteria) this;
        }

        public Criteria andCostFixedAvgEqualTo(BigDecimal value) {
            addCriterion("COST_FIXED_AVG =", value, "costFixedAvg");
            return (Criteria) this;
        }

        public Criteria andCostFixedAvgNotEqualTo(BigDecimal value) {
            addCriterion("COST_FIXED_AVG <>", value, "costFixedAvg");
            return (Criteria) this;
        }

        public Criteria andCostFixedAvgGreaterThan(BigDecimal value) {
            addCriterion("COST_FIXED_AVG >", value, "costFixedAvg");
            return (Criteria) this;
        }

        public Criteria andCostFixedAvgGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_FIXED_AVG >=", value, "costFixedAvg");
            return (Criteria) this;
        }

        public Criteria andCostFixedAvgLessThan(BigDecimal value) {
            addCriterion("COST_FIXED_AVG <", value, "costFixedAvg");
            return (Criteria) this;
        }

        public Criteria andCostFixedAvgLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_FIXED_AVG <=", value, "costFixedAvg");
            return (Criteria) this;
        }

        public Criteria andCostFixedAvgIn(List<BigDecimal> values) {
            addCriterion("COST_FIXED_AVG in", values, "costFixedAvg");
            return (Criteria) this;
        }

        public Criteria andCostFixedAvgNotIn(List<BigDecimal> values) {
            addCriterion("COST_FIXED_AVG not in", values, "costFixedAvg");
            return (Criteria) this;
        }

        public Criteria andCostFixedAvgBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_FIXED_AVG between", value1, value2, "costFixedAvg");
            return (Criteria) this;
        }

        public Criteria andCostFixedAvgNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_FIXED_AVG not between", value1, value2, "costFixedAvg");
            return (Criteria) this;
        }

        public Criteria andCostShareBuildingYearIsNull() {
            addCriterion("COST_SHARE_BUILDING_YEAR is null");
            return (Criteria) this;
        }

        public Criteria andCostShareBuildingYearIsNotNull() {
            addCriterion("COST_SHARE_BUILDING_YEAR is not null");
            return (Criteria) this;
        }

        public Criteria andCostShareBuildingYearEqualTo(BigDecimal value) {
            addCriterion("COST_SHARE_BUILDING_YEAR =", value, "costShareBuildingYear");
            return (Criteria) this;
        }

        public Criteria andCostShareBuildingYearNotEqualTo(BigDecimal value) {
            addCriterion("COST_SHARE_BUILDING_YEAR <>", value, "costShareBuildingYear");
            return (Criteria) this;
        }

        public Criteria andCostShareBuildingYearGreaterThan(BigDecimal value) {
            addCriterion("COST_SHARE_BUILDING_YEAR >", value, "costShareBuildingYear");
            return (Criteria) this;
        }

        public Criteria andCostShareBuildingYearGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_SHARE_BUILDING_YEAR >=", value, "costShareBuildingYear");
            return (Criteria) this;
        }

        public Criteria andCostShareBuildingYearLessThan(BigDecimal value) {
            addCriterion("COST_SHARE_BUILDING_YEAR <", value, "costShareBuildingYear");
            return (Criteria) this;
        }

        public Criteria andCostShareBuildingYearLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_SHARE_BUILDING_YEAR <=", value, "costShareBuildingYear");
            return (Criteria) this;
        }

        public Criteria andCostShareBuildingYearIn(List<BigDecimal> values) {
            addCriterion("COST_SHARE_BUILDING_YEAR in", values, "costShareBuildingYear");
            return (Criteria) this;
        }

        public Criteria andCostShareBuildingYearNotIn(List<BigDecimal> values) {
            addCriterion("COST_SHARE_BUILDING_YEAR not in", values, "costShareBuildingYear");
            return (Criteria) this;
        }

        public Criteria andCostShareBuildingYearBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_SHARE_BUILDING_YEAR between", value1, value2, "costShareBuildingYear");
            return (Criteria) this;
        }

        public Criteria andCostShareBuildingYearNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_SHARE_BUILDING_YEAR not between", value1, value2, "costShareBuildingYear");
            return (Criteria) this;
        }

        public Criteria andCostShareBaseDevYearIsNull() {
            addCriterion("COST_SHARE_BASE_DEV_YEAR is null");
            return (Criteria) this;
        }

        public Criteria andCostShareBaseDevYearIsNotNull() {
            addCriterion("COST_SHARE_BASE_DEV_YEAR is not null");
            return (Criteria) this;
        }

        public Criteria andCostShareBaseDevYearEqualTo(BigDecimal value) {
            addCriterion("COST_SHARE_BASE_DEV_YEAR =", value, "costShareBaseDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareBaseDevYearNotEqualTo(BigDecimal value) {
            addCriterion("COST_SHARE_BASE_DEV_YEAR <>", value, "costShareBaseDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareBaseDevYearGreaterThan(BigDecimal value) {
            addCriterion("COST_SHARE_BASE_DEV_YEAR >", value, "costShareBaseDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareBaseDevYearGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_SHARE_BASE_DEV_YEAR >=", value, "costShareBaseDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareBaseDevYearLessThan(BigDecimal value) {
            addCriterion("COST_SHARE_BASE_DEV_YEAR <", value, "costShareBaseDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareBaseDevYearLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_SHARE_BASE_DEV_YEAR <=", value, "costShareBaseDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareBaseDevYearIn(List<BigDecimal> values) {
            addCriterion("COST_SHARE_BASE_DEV_YEAR in", values, "costShareBaseDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareBaseDevYearNotIn(List<BigDecimal> values) {
            addCriterion("COST_SHARE_BASE_DEV_YEAR not in", values, "costShareBaseDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareBaseDevYearBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_SHARE_BASE_DEV_YEAR between", value1, value2, "costShareBaseDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareBaseDevYearNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_SHARE_BASE_DEV_YEAR not between", value1, value2, "costShareBaseDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareItDevYearIsNull() {
            addCriterion("COST_SHARE_IT_DEV_YEAR is null");
            return (Criteria) this;
        }

        public Criteria andCostShareItDevYearIsNotNull() {
            addCriterion("COST_SHARE_IT_DEV_YEAR is not null");
            return (Criteria) this;
        }

        public Criteria andCostShareItDevYearEqualTo(BigDecimal value) {
            addCriterion("COST_SHARE_IT_DEV_YEAR =", value, "costShareItDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareItDevYearNotEqualTo(BigDecimal value) {
            addCriterion("COST_SHARE_IT_DEV_YEAR <>", value, "costShareItDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareItDevYearGreaterThan(BigDecimal value) {
            addCriterion("COST_SHARE_IT_DEV_YEAR >", value, "costShareItDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareItDevYearGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_SHARE_IT_DEV_YEAR >=", value, "costShareItDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareItDevYearLessThan(BigDecimal value) {
            addCriterion("COST_SHARE_IT_DEV_YEAR <", value, "costShareItDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareItDevYearLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_SHARE_IT_DEV_YEAR <=", value, "costShareItDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareItDevYearIn(List<BigDecimal> values) {
            addCriterion("COST_SHARE_IT_DEV_YEAR in", values, "costShareItDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareItDevYearNotIn(List<BigDecimal> values) {
            addCriterion("COST_SHARE_IT_DEV_YEAR not in", values, "costShareItDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareItDevYearBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_SHARE_IT_DEV_YEAR between", value1, value2, "costShareItDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareItDevYearNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_SHARE_IT_DEV_YEAR not between", value1, value2, "costShareItDevYear");
            return (Criteria) this;
        }

        public Criteria andCostShareRackCountIsNull() {
            addCriterion("COST_SHARE_RACK_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andCostShareRackCountIsNotNull() {
            addCriterion("COST_SHARE_RACK_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andCostShareRackCountEqualTo(BigDecimal value) {
            addCriterion("COST_SHARE_RACK_COUNT =", value, "costShareRackCount");
            return (Criteria) this;
        }

        public Criteria andCostShareRackCountNotEqualTo(BigDecimal value) {
            addCriterion("COST_SHARE_RACK_COUNT <>", value, "costShareRackCount");
            return (Criteria) this;
        }

        public Criteria andCostShareRackCountGreaterThan(BigDecimal value) {
            addCriterion("COST_SHARE_RACK_COUNT >", value, "costShareRackCount");
            return (Criteria) this;
        }

        public Criteria andCostShareRackCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_SHARE_RACK_COUNT >=", value, "costShareRackCount");
            return (Criteria) this;
        }

        public Criteria andCostShareRackCountLessThan(BigDecimal value) {
            addCriterion("COST_SHARE_RACK_COUNT <", value, "costShareRackCount");
            return (Criteria) this;
        }

        public Criteria andCostShareRackCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_SHARE_RACK_COUNT <=", value, "costShareRackCount");
            return (Criteria) this;
        }

        public Criteria andCostShareRackCountIn(List<BigDecimal> values) {
            addCriterion("COST_SHARE_RACK_COUNT in", values, "costShareRackCount");
            return (Criteria) this;
        }

        public Criteria andCostShareRackCountNotIn(List<BigDecimal> values) {
            addCriterion("COST_SHARE_RACK_COUNT not in", values, "costShareRackCount");
            return (Criteria) this;
        }

        public Criteria andCostShareRackCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_SHARE_RACK_COUNT between", value1, value2, "costShareRackCount");
            return (Criteria) this;
        }

        public Criteria andCostShareRackCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_SHARE_RACK_COUNT not between", value1, value2, "costShareRackCount");
            return (Criteria) this;
        }

        public Criteria andCostBuildingMonthIsNull() {
            addCriterion("COST_BUILDING_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andCostBuildingMonthIsNotNull() {
            addCriterion("COST_BUILDING_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andCostBuildingMonthEqualTo(BigDecimal value) {
            addCriterion("COST_BUILDING_MONTH =", value, "costBuildingMonth");
            return (Criteria) this;
        }

        public Criteria andCostBuildingMonthNotEqualTo(BigDecimal value) {
            addCriterion("COST_BUILDING_MONTH <>", value, "costBuildingMonth");
            return (Criteria) this;
        }

        public Criteria andCostBuildingMonthGreaterThan(BigDecimal value) {
            addCriterion("COST_BUILDING_MONTH >", value, "costBuildingMonth");
            return (Criteria) this;
        }

        public Criteria andCostBuildingMonthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_BUILDING_MONTH >=", value, "costBuildingMonth");
            return (Criteria) this;
        }

        public Criteria andCostBuildingMonthLessThan(BigDecimal value) {
            addCriterion("COST_BUILDING_MONTH <", value, "costBuildingMonth");
            return (Criteria) this;
        }

        public Criteria andCostBuildingMonthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_BUILDING_MONTH <=", value, "costBuildingMonth");
            return (Criteria) this;
        }

        public Criteria andCostBuildingMonthIn(List<BigDecimal> values) {
            addCriterion("COST_BUILDING_MONTH in", values, "costBuildingMonth");
            return (Criteria) this;
        }

        public Criteria andCostBuildingMonthNotIn(List<BigDecimal> values) {
            addCriterion("COST_BUILDING_MONTH not in", values, "costBuildingMonth");
            return (Criteria) this;
        }

        public Criteria andCostBuildingMonthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_BUILDING_MONTH between", value1, value2, "costBuildingMonth");
            return (Criteria) this;
        }

        public Criteria andCostBuildingMonthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_BUILDING_MONTH not between", value1, value2, "costBuildingMonth");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevMonthIsNull() {
            addCriterion("COST_BASE_DEV_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevMonthIsNotNull() {
            addCriterion("COST_BASE_DEV_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevMonthEqualTo(BigDecimal value) {
            addCriterion("COST_BASE_DEV_MONTH =", value, "costBaseDevMonth");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevMonthNotEqualTo(BigDecimal value) {
            addCriterion("COST_BASE_DEV_MONTH <>", value, "costBaseDevMonth");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevMonthGreaterThan(BigDecimal value) {
            addCriterion("COST_BASE_DEV_MONTH >", value, "costBaseDevMonth");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevMonthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_BASE_DEV_MONTH >=", value, "costBaseDevMonth");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevMonthLessThan(BigDecimal value) {
            addCriterion("COST_BASE_DEV_MONTH <", value, "costBaseDevMonth");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevMonthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_BASE_DEV_MONTH <=", value, "costBaseDevMonth");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevMonthIn(List<BigDecimal> values) {
            addCriterion("COST_BASE_DEV_MONTH in", values, "costBaseDevMonth");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevMonthNotIn(List<BigDecimal> values) {
            addCriterion("COST_BASE_DEV_MONTH not in", values, "costBaseDevMonth");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevMonthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_BASE_DEV_MONTH between", value1, value2, "costBaseDevMonth");
            return (Criteria) this;
        }

        public Criteria andCostBaseDevMonthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_BASE_DEV_MONTH not between", value1, value2, "costBaseDevMonth");
            return (Criteria) this;
        }

        public Criteria andCostItMonthIsNull() {
            addCriterion("COST_IT_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andCostItMonthIsNotNull() {
            addCriterion("COST_IT_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andCostItMonthEqualTo(BigDecimal value) {
            addCriterion("COST_IT_MONTH =", value, "costItMonth");
            return (Criteria) this;
        }

        public Criteria andCostItMonthNotEqualTo(BigDecimal value) {
            addCriterion("COST_IT_MONTH <>", value, "costItMonth");
            return (Criteria) this;
        }

        public Criteria andCostItMonthGreaterThan(BigDecimal value) {
            addCriterion("COST_IT_MONTH >", value, "costItMonth");
            return (Criteria) this;
        }

        public Criteria andCostItMonthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_IT_MONTH >=", value, "costItMonth");
            return (Criteria) this;
        }

        public Criteria andCostItMonthLessThan(BigDecimal value) {
            addCriterion("COST_IT_MONTH <", value, "costItMonth");
            return (Criteria) this;
        }

        public Criteria andCostItMonthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_IT_MONTH <=", value, "costItMonth");
            return (Criteria) this;
        }

        public Criteria andCostItMonthIn(List<BigDecimal> values) {
            addCriterion("COST_IT_MONTH in", values, "costItMonth");
            return (Criteria) this;
        }

        public Criteria andCostItMonthNotIn(List<BigDecimal> values) {
            addCriterion("COST_IT_MONTH not in", values, "costItMonth");
            return (Criteria) this;
        }

        public Criteria andCostItMonthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_IT_MONTH between", value1, value2, "costItMonth");
            return (Criteria) this;
        }

        public Criteria andCostItMonthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_IT_MONTH not between", value1, value2, "costItMonth");
            return (Criteria) this;
        }

        public Criteria andCostRackMonthIsNull() {
            addCriterion("COST_RACK_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andCostRackMonthIsNotNull() {
            addCriterion("COST_RACK_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andCostRackMonthEqualTo(BigDecimal value) {
            addCriterion("COST_RACK_MONTH =", value, "costRackMonth");
            return (Criteria) this;
        }

        public Criteria andCostRackMonthNotEqualTo(BigDecimal value) {
            addCriterion("COST_RACK_MONTH <>", value, "costRackMonth");
            return (Criteria) this;
        }

        public Criteria andCostRackMonthGreaterThan(BigDecimal value) {
            addCriterion("COST_RACK_MONTH >", value, "costRackMonth");
            return (Criteria) this;
        }

        public Criteria andCostRackMonthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_RACK_MONTH >=", value, "costRackMonth");
            return (Criteria) this;
        }

        public Criteria andCostRackMonthLessThan(BigDecimal value) {
            addCriterion("COST_RACK_MONTH <", value, "costRackMonth");
            return (Criteria) this;
        }

        public Criteria andCostRackMonthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_RACK_MONTH <=", value, "costRackMonth");
            return (Criteria) this;
        }

        public Criteria andCostRackMonthIn(List<BigDecimal> values) {
            addCriterion("COST_RACK_MONTH in", values, "costRackMonth");
            return (Criteria) this;
        }

        public Criteria andCostRackMonthNotIn(List<BigDecimal> values) {
            addCriterion("COST_RACK_MONTH not in", values, "costRackMonth");
            return (Criteria) this;
        }

        public Criteria andCostRackMonthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_RACK_MONTH between", value1, value2, "costRackMonth");
            return (Criteria) this;
        }

        public Criteria andCostRackMonthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_RACK_MONTH not between", value1, value2, "costRackMonth");
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