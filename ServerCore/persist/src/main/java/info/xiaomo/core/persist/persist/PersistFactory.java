package info.xiaomo.core.persist.persist;

/**
 * 数据持久化构造器
 *
 * @author 张力
 */
public interface PersistFactory {

    /**
     * 表名
     *
     * @return String
     */
    String tableName();

    /**
     * 数据类型
     *
     * @return String
     */
    int dataType();

    /**
     * 创建语句
     *
     * @return String
     */
    String createCreateSql();

    /**
     * 插入语句
     *
     * @return String
     */
    String createInsertSql();

    /**
     * 更新语句
     *
     * @return String
     */
    String createUpdateSql();

    /**
     * 删除语句
     *
     * @return String
     */
    String createDeleteSql();

    /**
     * 插入语句的参数
     *
     * @param obj obj
     * @return obj
     */
    Object[] createInsertParameters(Persistable obj);

    /**
     * 更新语句的参数
     *
     * @param obj obj
     * @return obj
     */
    Object[] createUpdateParameters(Persistable obj);

    /**
     * 删除语句的参数
     *
     * @param obj obj
     * @return obj
     */
    Object[] createDeleteParameters(Persistable obj);

    /**
     * 时间
     *
     * @return long
     */
    long taskPeriod();

}
