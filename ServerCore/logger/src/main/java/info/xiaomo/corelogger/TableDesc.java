package info.xiaomo.corelogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class TableDesc {

    private SimpleDateFormat yyyyMmDd = new SimpleDateFormat("_yyyy_MM_dd");
    private SimpleDateFormat yyyyMm = new SimpleDateFormat("_yyyy_MM");
    private SimpleDateFormat yyyy = new SimpleDateFormat("_yyyy");
    public long time = System.currentTimeMillis();

    private String createSql;

    private String insertSql;

    /**
     * 表名
     */
    private String name;

    /**
     * 主键
     */
    private String primaryKey;

    /**
     * 日志周期
     */
    private TableCycle cycle;

    /**
     * 列
     */
    private List<ColumnDesc> columns = new ArrayList<>();

    /**
     * 非自增列的个数
     */
    private int noAutoIncrementColumnCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public TableCycle getCycle() {
        return cycle;
    }

    public void setCycle(TableCycle cycle) {
        this.cycle = cycle;
    }

    public List<ColumnDesc> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnDesc> columns) {
        this.columns = columns;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }


    public String getCreateSql() {
        return createSql;
    }

    public void setCreateSql(String createSql) {
        this.createSql = createSql;
    }

    private String buildCreateSql() {
        StringBuilder ddl = new StringBuilder();
        ddl.append("CREATE TABLE IF NOT EXISTS `%s` (\n");
        for (ColumnDesc col : columns) {
            ddl.append(col.toDDL()).append(",\n");
        }
        ddl.append("PRIMARY KEY (`").append(this.primaryKey).append("`)) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8");
        return ddl.toString();
    }

    private String buildInsertSQL() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into `%s` ");

        StringBuilder fields = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (ColumnDesc col : columns) {
            //自增列不插入
            if (col.isAutoIncrement()) {
                continue;
            }
            fields.append("`").append(col.getName()).append("`,");
            values.append("?,");
        }
        if (fields.length() > 0) {
            fields.deleteCharAt(fields.length() - 1).append(")");
        }
        if (values.length() > 0) {
            values.deleteCharAt(values.length() - 1).append(")");
        }

        sql.append(fields).append(" values ").append(values);
        return sql.toString();
    }

    public Object[] buildInsertParam(Object log) {
        Object[] ret = new Object[noAutoIncrementColumnCount];
        int index = 0;
        try {
            for (ColumnDesc col : columns) {
                //自增列不插入
                if (col.isAutoIncrement()) {
                    continue;
                }
                ret[index] = col.getReadMethod().invoke(log);
                index++;
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String buildName(long time) {
        switch (cycle) {
            case DAY:
                return this.name + yyyyMmDd.format(new Date(time));
            case MONTH:
                return this.name + yyyyMm.format(new Date(time));
            case YEAR:
                return this.name + yyyy.format(new Date(time));
            case SINGLE:
                return this.name;
            default:
                return this.name;
        }
    }


    public String getInsertSql() {
        return insertSql;
    }

    public void setInsertSql(String insertSql) {
        this.insertSql = insertSql;
    }

    public void init() {
        int count = 0;
        for (ColumnDesc col : columns) {
            if (col.isAutoIncrement()) {
                continue;
            }
            count++;
        }
        this.noAutoIncrementColumnCount = count;
        this.createSql = buildCreateSql();
        this.insertSql = buildInsertSQL();
    }

    public void addCol(ColumnDesc newCol) {
        for (int i = 0; i < this.columns.size(); i++) {
            ColumnDesc col = this.columns.get(i);
            if (col.getName().equals(newCol.getName())) {
                this.columns.remove(i);
                this.columns.add(i, newCol);
                return;
            }
        }
        this.columns.add(newCol);
    }
}
