package com.idc.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AlarmInfoCurrExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AlarmInfoCurrExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAlarmlevelIsNull() {
            addCriterion("ALARMLEVEL is null");
            return (Criteria) this;
        }

        public Criteria andAlarmlevelIsNotNull() {
            addCriterion("ALARMLEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmlevelEqualTo(Long value) {
            addCriterion("ALARMLEVEL =", value, "alarmlevel");
            return (Criteria) this;
        }

        public Criteria andAlarmlevelNotEqualTo(Long value) {
            addCriterion("ALARMLEVEL <>", value, "alarmlevel");
            return (Criteria) this;
        }

        public Criteria andAlarmlevelGreaterThan(Long value) {
            addCriterion("ALARMLEVEL >", value, "alarmlevel");
            return (Criteria) this;
        }

        public Criteria andAlarmlevelGreaterThanOrEqualTo(Long value) {
            addCriterion("ALARMLEVEL >=", value, "alarmlevel");
            return (Criteria) this;
        }

        public Criteria andAlarmlevelLessThan(Long value) {
            addCriterion("ALARMLEVEL <", value, "alarmlevel");
            return (Criteria) this;
        }

        public Criteria andAlarmlevelLessThanOrEqualTo(Long value) {
            addCriterion("ALARMLEVEL <=", value, "alarmlevel");
            return (Criteria) this;
        }

        public Criteria andAlarmlevelIn(List<Long> values) {
            addCriterion("ALARMLEVEL in", values, "alarmlevel");
            return (Criteria) this;
        }

        public Criteria andAlarmlevelNotIn(List<Long> values) {
            addCriterion("ALARMLEVEL not in", values, "alarmlevel");
            return (Criteria) this;
        }

        public Criteria andAlarmlevelBetween(Long value1, Long value2) {
            addCriterion("ALARMLEVEL between", value1, value2, "alarmlevel");
            return (Criteria) this;
        }

        public Criteria andAlarmlevelNotBetween(Long value1, Long value2) {
            addCriterion("ALARMLEVEL not between", value1, value2, "alarmlevel");
            return (Criteria) this;
        }

        public Criteria andAlarmtypeIsNull() {
            addCriterion("ALARMTYPE is null");
            return (Criteria) this;
        }

        public Criteria andAlarmtypeIsNotNull() {
            addCriterion("ALARMTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmtypeEqualTo(Long value) {
            addCriterion("ALARMTYPE =", value, "alarmtype");
            return (Criteria) this;
        }

        public Criteria andAlarmtypeNotEqualTo(Long value) {
            addCriterion("ALARMTYPE <>", value, "alarmtype");
            return (Criteria) this;
        }

        public Criteria andAlarmtypeGreaterThan(Long value) {
            addCriterion("ALARMTYPE >", value, "alarmtype");
            return (Criteria) this;
        }

        public Criteria andAlarmtypeGreaterThanOrEqualTo(Long value) {
            addCriterion("ALARMTYPE >=", value, "alarmtype");
            return (Criteria) this;
        }

        public Criteria andAlarmtypeLessThan(Long value) {
            addCriterion("ALARMTYPE <", value, "alarmtype");
            return (Criteria) this;
        }

        public Criteria andAlarmtypeLessThanOrEqualTo(Long value) {
            addCriterion("ALARMTYPE <=", value, "alarmtype");
            return (Criteria) this;
        }

        public Criteria andAlarmtypeIn(List<Long> values) {
            addCriterion("ALARMTYPE in", values, "alarmtype");
            return (Criteria) this;
        }

        public Criteria andAlarmtypeNotIn(List<Long> values) {
            addCriterion("ALARMTYPE not in", values, "alarmtype");
            return (Criteria) this;
        }

        public Criteria andAlarmtypeBetween(Long value1, Long value2) {
            addCriterion("ALARMTYPE between", value1, value2, "alarmtype");
            return (Criteria) this;
        }

        public Criteria andAlarmtypeNotBetween(Long value1, Long value2) {
            addCriterion("ALARMTYPE not between", value1, value2, "alarmtype");
            return (Criteria) this;
        }

        public Criteria andAlarmobjIsNull() {
            addCriterion("ALARMOBJ is null");
            return (Criteria) this;
        }

        public Criteria andAlarmobjIsNotNull() {
            addCriterion("ALARMOBJ is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmobjEqualTo(Object value) {
            addCriterion("ALARMOBJ =", value, "alarmobj");
            return (Criteria) this;
        }

        public Criteria andAlarmobjNotEqualTo(Object value) {
            addCriterion("ALARMOBJ <>", value, "alarmobj");
            return (Criteria) this;
        }

        public Criteria andAlarmobjGreaterThan(Object value) {
            addCriterion("ALARMOBJ >", value, "alarmobj");
            return (Criteria) this;
        }

        public Criteria andAlarmobjGreaterThanOrEqualTo(Object value) {
            addCriterion("ALARMOBJ >=", value, "alarmobj");
            return (Criteria) this;
        }

        public Criteria andAlarmobjLessThan(Object value) {
            addCriterion("ALARMOBJ <", value, "alarmobj");
            return (Criteria) this;
        }

        public Criteria andAlarmobjLessThanOrEqualTo(Object value) {
            addCriterion("ALARMOBJ <=", value, "alarmobj");
            return (Criteria) this;
        }

        public Criteria andAlarmobjIn(List<Object> values) {
            addCriterion("ALARMOBJ in", values, "alarmobj");
            return (Criteria) this;
        }

        public Criteria andAlarmobjNotIn(List<Object> values) {
            addCriterion("ALARMOBJ not in", values, "alarmobj");
            return (Criteria) this;
        }

        public Criteria andAlarmobjBetween(Object value1, Object value2) {
            addCriterion("ALARMOBJ between", value1, value2, "alarmobj");
            return (Criteria) this;
        }

        public Criteria andAlarmobjNotBetween(Object value1, Object value2) {
            addCriterion("ALARMOBJ not between", value1, value2, "alarmobj");
            return (Criteria) this;
        }

        public Criteria andAlarminfoIsNull() {
            addCriterion("ALARMINFO is null");
            return (Criteria) this;
        }

        public Criteria andAlarminfoIsNotNull() {
            addCriterion("ALARMINFO is not null");
            return (Criteria) this;
        }

        public Criteria andAlarminfoEqualTo(Object value) {
            addCriterion("ALARMINFO =", value, "alarminfo");
            return (Criteria) this;
        }

        public Criteria andAlarminfoNotEqualTo(Object value) {
            addCriterion("ALARMINFO <>", value, "alarminfo");
            return (Criteria) this;
        }

        public Criteria andAlarminfoGreaterThan(Object value) {
            addCriterion("ALARMINFO >", value, "alarminfo");
            return (Criteria) this;
        }

        public Criteria andAlarminfoGreaterThanOrEqualTo(Object value) {
            addCriterion("ALARMINFO >=", value, "alarminfo");
            return (Criteria) this;
        }

        public Criteria andAlarminfoLessThan(Object value) {
            addCriterion("ALARMINFO <", value, "alarminfo");
            return (Criteria) this;
        }

        public Criteria andAlarminfoLessThanOrEqualTo(Object value) {
            addCriterion("ALARMINFO <=", value, "alarminfo");
            return (Criteria) this;
        }

        public Criteria andAlarminfoIn(List<Object> values) {
            addCriterion("ALARMINFO in", values, "alarminfo");
            return (Criteria) this;
        }

        public Criteria andAlarminfoNotIn(List<Object> values) {
            addCriterion("ALARMINFO not in", values, "alarminfo");
            return (Criteria) this;
        }

        public Criteria andAlarminfoBetween(Object value1, Object value2) {
            addCriterion("ALARMINFO between", value1, value2, "alarminfo");
            return (Criteria) this;
        }

        public Criteria andAlarminfoNotBetween(Object value1, Object value2) {
            addCriterion("ALARMINFO not between", value1, value2, "alarminfo");
            return (Criteria) this;
        }

        public Criteria andAlarmtimeIsNull() {
            addCriterion("ALARMTIME is null");
            return (Criteria) this;
        }

        public Criteria andAlarmtimeIsNotNull() {
            addCriterion("ALARMTIME is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmtimeEqualTo(Date value) {
            addCriterionForJDBCDate("ALARMTIME =", value, "alarmtime");
            return (Criteria) this;
        }

        public Criteria andAlarmtimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("ALARMTIME <>", value, "alarmtime");
            return (Criteria) this;
        }

        public Criteria andAlarmtimeGreaterThan(Date value) {
            addCriterionForJDBCDate("ALARMTIME >", value, "alarmtime");
            return (Criteria) this;
        }

        public Criteria andAlarmtimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ALARMTIME >=", value, "alarmtime");
            return (Criteria) this;
        }

        public Criteria andAlarmtimeLessThan(Date value) {
            addCriterionForJDBCDate("ALARMTIME <", value, "alarmtime");
            return (Criteria) this;
        }

        public Criteria andAlarmtimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ALARMTIME <=", value, "alarmtime");
            return (Criteria) this;
        }

        public Criteria andAlarmtimeIn(List<Date> values) {
            addCriterionForJDBCDate("ALARMTIME in", values, "alarmtime");
            return (Criteria) this;
        }

        public Criteria andAlarmtimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("ALARMTIME not in", values, "alarmtime");
            return (Criteria) this;
        }

        public Criteria andAlarmtimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ALARMTIME between", value1, value2, "alarmtime");
            return (Criteria) this;
        }

        public Criteria andAlarmtimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ALARMTIME not between", value1, value2, "alarmtime");
            return (Criteria) this;
        }

        public Criteria andAlarmsendflagIsNull() {
            addCriterion("ALARMSENDFLAG is null");
            return (Criteria) this;
        }

        public Criteria andAlarmsendflagIsNotNull() {
            addCriterion("ALARMSENDFLAG is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmsendflagEqualTo(Long value) {
            addCriterion("ALARMSENDFLAG =", value, "alarmsendflag");
            return (Criteria) this;
        }

        public Criteria andAlarmsendflagNotEqualTo(Long value) {
            addCriterion("ALARMSENDFLAG <>", value, "alarmsendflag");
            return (Criteria) this;
        }

        public Criteria andAlarmsendflagGreaterThan(Long value) {
            addCriterion("ALARMSENDFLAG >", value, "alarmsendflag");
            return (Criteria) this;
        }

        public Criteria andAlarmsendflagGreaterThanOrEqualTo(Long value) {
            addCriterion("ALARMSENDFLAG >=", value, "alarmsendflag");
            return (Criteria) this;
        }

        public Criteria andAlarmsendflagLessThan(Long value) {
            addCriterion("ALARMSENDFLAG <", value, "alarmsendflag");
            return (Criteria) this;
        }

        public Criteria andAlarmsendflagLessThanOrEqualTo(Long value) {
            addCriterion("ALARMSENDFLAG <=", value, "alarmsendflag");
            return (Criteria) this;
        }

        public Criteria andAlarmsendflagIn(List<Long> values) {
            addCriterion("ALARMSENDFLAG in", values, "alarmsendflag");
            return (Criteria) this;
        }

        public Criteria andAlarmsendflagNotIn(List<Long> values) {
            addCriterion("ALARMSENDFLAG not in", values, "alarmsendflag");
            return (Criteria) this;
        }

        public Criteria andAlarmsendflagBetween(Long value1, Long value2) {
            addCriterion("ALARMSENDFLAG between", value1, value2, "alarmsendflag");
            return (Criteria) this;
        }

        public Criteria andAlarmsendflagNotBetween(Long value1, Long value2) {
            addCriterion("ALARMSENDFLAG not between", value1, value2, "alarmsendflag");
            return (Criteria) this;
        }

        public Criteria andAlarmsendtimeIsNull() {
            addCriterion("ALARMSENDTIME is null");
            return (Criteria) this;
        }

        public Criteria andAlarmsendtimeIsNotNull() {
            addCriterion("ALARMSENDTIME is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmsendtimeEqualTo(Date value) {
            addCriterionForJDBCDate("ALARMSENDTIME =", value, "alarmsendtime");
            return (Criteria) this;
        }

        public Criteria andAlarmsendtimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("ALARMSENDTIME <>", value, "alarmsendtime");
            return (Criteria) this;
        }

        public Criteria andAlarmsendtimeGreaterThan(Date value) {
            addCriterionForJDBCDate("ALARMSENDTIME >", value, "alarmsendtime");
            return (Criteria) this;
        }

        public Criteria andAlarmsendtimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ALARMSENDTIME >=", value, "alarmsendtime");
            return (Criteria) this;
        }

        public Criteria andAlarmsendtimeLessThan(Date value) {
            addCriterionForJDBCDate("ALARMSENDTIME <", value, "alarmsendtime");
            return (Criteria) this;
        }

        public Criteria andAlarmsendtimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ALARMSENDTIME <=", value, "alarmsendtime");
            return (Criteria) this;
        }

        public Criteria andAlarmsendtimeIn(List<Date> values) {
            addCriterionForJDBCDate("ALARMSENDTIME in", values, "alarmsendtime");
            return (Criteria) this;
        }

        public Criteria andAlarmsendtimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("ALARMSENDTIME not in", values, "alarmsendtime");
            return (Criteria) this;
        }

        public Criteria andAlarmsendtimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ALARMSENDTIME between", value1, value2, "alarmsendtime");
            return (Criteria) this;
        }

        public Criteria andAlarmsendtimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ALARMSENDTIME not between", value1, value2, "alarmsendtime");
            return (Criteria) this;
        }

        public Criteria andPortidIsNull() {
            addCriterion("PORTID is null");
            return (Criteria) this;
        }

        public Criteria andPortidIsNotNull() {
            addCriterion("PORTID is not null");
            return (Criteria) this;
        }

        public Criteria andPortidEqualTo(BigDecimal value) {
            addCriterion("PORTID =", value, "portid");
            return (Criteria) this;
        }

        public Criteria andPortidNotEqualTo(BigDecimal value) {
            addCriterion("PORTID <>", value, "portid");
            return (Criteria) this;
        }

        public Criteria andPortidGreaterThan(BigDecimal value) {
            addCriterion("PORTID >", value, "portid");
            return (Criteria) this;
        }

        public Criteria andPortidGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PORTID >=", value, "portid");
            return (Criteria) this;
        }

        public Criteria andPortidLessThan(BigDecimal value) {
            addCriterion("PORTID <", value, "portid");
            return (Criteria) this;
        }

        public Criteria andPortidLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PORTID <=", value, "portid");
            return (Criteria) this;
        }

        public Criteria andPortidIn(List<BigDecimal> values) {
            addCriterion("PORTID in", values, "portid");
            return (Criteria) this;
        }

        public Criteria andPortidNotIn(List<BigDecimal> values) {
            addCriterion("PORTID not in", values, "portid");
            return (Criteria) this;
        }

        public Criteria andPortidBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PORTID between", value1, value2, "portid");
            return (Criteria) this;
        }

        public Criteria andPortidNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PORTID not between", value1, value2, "portid");
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