package com.entdiy.common.test.support;

import com.entdiy.common.util.DateUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.Size;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 模拟实体对象实例构造帮助类
 */
public class MockEntityUtils {

    private final static Logger logger = LoggerFactory.getLogger(MockEntityUtils.class);

    private static RandomDataGenerator randomDataGenerator = new RandomDataGenerator();

    private static Random random = new Random();

    public static <X> List<X> buildMockObject(Class<X> clazz, int minSize, int maxSize) {
        List<X> list = Lists.newArrayList();
        int size = randomInt(minSize, maxSize);
        for (int i = 0; i < size; i++) {
            list.add(buildMockObject(clazz));
        }
        return list;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <X> X buildMockObject(Class<X> clazz) {
        X x = null;
        try {
            x = clazz.getDeclaredConstructor().newInstance();
            for (Method method : clazz.getDeclaredMethods()) {
                String mn = method.getName();
                if (mn.startsWith("set")) {
                    Class[] parameters = method.getParameterTypes();
                    if (parameters.length == 1) {
                        Method getMethod = MethodUtils.getAccessibleMethod(clazz, "get" + mn.substring(3), null);
                        if (getMethod != null) {
                            if ("getId".equals(getMethod.getName())) {
                                continue;
                            }
                            //有默认值，则直接返回
                            if (MethodUtils.invokeMethod(x, getMethod.getName(), null, null) != null) {
                                continue;
                            }
                            Object value = null;
                            Class parameter = parameters[0];
                            if (parameter.isAssignableFrom(String.class)) {
                                Column column = getMethod.getAnnotation(Column.class);
                                int columnLength = 10;
                                if (column != null && column.length() < columnLength) {
                                    columnLength = column.length();
                                }
                                Size size = getMethod.getAnnotation(Size.class);
                                if (size != null && size.max() < columnLength) {
                                    columnLength = size.max();
                                }
                                value = RandomStringUtils.randomAlphabetic(columnLength);
                            } else if (parameter.isAssignableFrom(Date.class)) {
                                throw new RuntimeException("Please use LocalData to instead Date.");
                            } else if (parameter.isAssignableFrom(LocalDate.class)) {
                                value = DateUtils.currentDateTime().toLocalDate();
                            } else if (parameter.isAssignableFrom(LocalDateTime.class)) {
                                value = DateUtils.currentDateTime();
                            } else if (parameter.isAssignableFrom(BigDecimal.class)) {
                                value = new BigDecimal(10 + new Double(new Random().nextDouble() * 1000).intValue());
                            } else if (parameter.isAssignableFrom(Integer.class)) {
                                value = 1 + new Double(new Random().nextDouble() * 100).intValue();
                            } else if (parameter.isAssignableFrom(Long.class)) {
                                value = 1 + new Double(new Random().nextDouble() * 100).longValue();
                            } else if (parameter.isAssignableFrom(Boolean.class)) {
                                value = new Random().nextBoolean();
                            } else if (parameter.isEnum()) {
                                Method m = parameter.getDeclaredMethod("values", null);
                                Object[] result = (Object[]) m.invoke(parameter.getEnumConstants()[0], null);
                                value = result[new Random().nextInt(result.length)];
                            }
                            if (value != null) {
                                MethodUtils.invokeMethod(x, mn, value);
                                logger.trace("{}={}", method.getName(), value);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return x;
    }

    /**
     * 随机取一个对象返回
     */
    public static <X> X randomCandidates(X... candidates) {
        List<X> list = Lists.newArrayList(candidates);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(randomDataGenerator.nextInt(0, list.size() - 1));
    }

    /**
     * 随机取一个对象返回
     */
    public static <X> X randomCandidates(Iterable<X> candidates) {
        List<X> list = Lists.newArrayList(candidates);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        return list.get(randomDataGenerator.nextInt(0, list.size() - 1));
    }


    /**
     * 返回区间段随机字符串
     *
     * @param min 最小长度
     * @param max 最大长度
     * @return
     */
    public static String randomString(int min, int max) {
        return RandomStringUtils.randomAlphabetic(randomInt(min, max));
    }

    /**
     * 返回区间段随机整数
     *
     * @param lower 最小值
     * @param upper 最大值
     * @return
     */
    public static int randomInt(int lower, int upper) {
        return randomDataGenerator.nextInt(lower, upper);
    }

    /**
     * 返回区间段随机整数
     *
     * @param lower 最小值
     * @param upper 最大值
     * @return
     */
    public static long randomLong(int lower, int upper) {
        return randomDataGenerator.nextLong(lower, upper);
    }

    /**
     * 返回0-1区间段随机小数
     *
     * @return
     */
    public static double randomDouble() {
        return random.nextDouble();
    }

    /**
     * 返回区间段随机布尔值
     *
     * @return
     */
    public static boolean randomBoolean() {
        return randomDataGenerator.nextInt(0, 100) > 50 ? true : false;
    }

    /**
     * 返回区间段随机日期
     *
     * @param daysBeforeNow 距离当前日期之前天数
     * @param daysAfterNow  距离当前日期之后天数
     * @return
     */
    public static LocalDateTime randomDateTime(int daysBeforeNow, int daysAfterNow) {
        LocalDateTime dt = LocalDateTime.now();
        dt = dt.plusSeconds(randomInt(-30, 30));
        dt = dt.plusMinutes(randomInt(-30, 30));
        dt = dt.plusHours(randomInt(-12, 12));
        dt = dt.minusDays(daysBeforeNow);
        dt = dt.plusDays(randomInt(0, daysBeforeNow + daysAfterNow));
        return dt;
    }

    /**
     * 数据持久化
     *
     * @param entity           待持久化对象实例
     * @param existCheckFields 可变参数，提供用于检查数据是否已存在的字段名称列表
     * @return
     */
    public static boolean persistSilently(EntityManager entityManager, Object entity, String... existCheckFields) {
        try {
            if (existCheckFields != null && existCheckFields.length > 0) {
                CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                CriteriaQuery<?> criteriaQuery = criteriaBuilder.createQuery(entity.getClass());
                Root<?> root = criteriaQuery.from(entity.getClass());
                List<Predicate> predicatesList = new ArrayList<Predicate>();
                Map<String, Object> predicates = Maps.newHashMap();
                for (String field : existCheckFields) {
                    Object value = FieldUtils.readField(entity, field, true);
                    predicates.put(field, value);
                    if (value == null) {
                        predicatesList.add(criteriaBuilder.isNull(root.get(field)));
                    } else {
                        predicatesList.add(criteriaBuilder.equal(root.get(field), value));
                    }
                }
                criteriaQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
                List<?> list = entityManager.createQuery(criteriaQuery).getResultList();
                if (list != null && list.size() > 0) {
                    logger.debug("Skipped exist data: {} -> {}", entity.getClass(), predicates);
                    return false;
                }
            }
            entityManager.persist(entity);
            entityManager.flush();
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Data
    public static class TestVO {
        private String str;
        private LocalDate dt;
    }

    public static void main(String[] args) {
        TestVO testVO = MockEntityUtils.buildMockObject(TestVO.class);
        System.out.println("Mock Entity: " + testVO);
    }
}
