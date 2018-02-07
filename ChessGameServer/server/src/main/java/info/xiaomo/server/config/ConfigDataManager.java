package info.xiaomo.server.config;

import info.xiaomo.core.config.excel.ExcelConfigDataManager;
import info.xiaomo.server.GameServerBootstrap;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 * いま 最高の表現 として 明日最新の始発．．～
 * Today the best performance  as tomorrow newest starter!
 * Created by IntelliJ IDEA.
 * <p>
 * @author : xiaomo
 * github: https://github.com/xiaomoinfo
 * email : xiaomo@xiaomo.info
 * QQ    : 83387856
 * Date  : 2017/8/24 12:51
 * desc  :
 * Copyright(©) 2017 by xiaomo.
 */

public class ConfigDataManager extends ExcelConfigDataManager {
    private static final ConfigDataManager INSTANCE = new ConfigDataManager();

    private ConfigDataManager() {
        super(GameServerBootstrap.class);
    }

    public static ConfigDataManager getInstance() {
        return INSTANCE;
    }
}
