package info.xiaomo.server.system.user;

import info.xiaomo.server.db.DataCenter;
import info.xiaomo.server.db.DataType;
import info.xiaomo.server.entify.User;
import info.xiaomo.server.event.EventType;
import info.xiaomo.server.event.EventUtil;
import info.xiaomo.server.protocol.UserProto.LoginResponse;
import info.xiaomo.server.server.Session;
import info.xiaomo.server.server.SessionManager;
import info.xiaomo.server.util.IDUtil;
import info.xiaomo.server.util.MessageUtil;
import info.xiaomo.server.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;

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
 * Date  : 2017/7/13 15:02
 * desc  :
 * Copyright(©) 2017 by xiaomo.
 */
@Slf4j
public class UserManager {
    private static UserManager ourInstance = new UserManager();

    public static UserManager getInstance() {
        return ourInstance;
    }

    private UserManager() {
    }


    /**
     * 登录游戏
     *
     * @param session session
     */
    public void login(Session session, String loginName) {
        if (loginName.isEmpty()) {
            return;
        }
        User user = DataCenter.getUser(loginName);
        if (user == null) {
            // 新建用户
            user = createUser(loginName);
            if (user == null) {
                log.error("用户创建失败:{},{},{}", loginName);
                session.close();
                return;
            }
        }
        // 注册账户
        session.setUser(user);
        // 注册session
        SessionManager.getInstance().register(session);

        LoginResponse.Builder builder = LoginResponse.newBuilder();
        LoginResponse loginResponse = builder.setUserId(user.getId()).build();
        MessageUtil.sendMsg(loginResponse, user.getId());

        EventUtil.fireEvent(EventType.LOGIN, user);
    }


    /**
     * 创建角色
     *
     * @param loginName loginName
     * @return User
     */
    private User createUser(String loginName) {
        User user = new User();
        long id = IDUtil.getId();
        user.setId(id);
        user.setLoginName(loginName);
        user.setServerId(1);
        user.setGmLevel(1);
        user.setPlatformId(1);
        user.setRegisterTime(TimeUtil.getNowOfSeconds());
        DataCenter.insertData(user, true);
        DataCenter.registerUser(user);
        return user;
    }


    /**
     * 退出
     */
    public void logout(Session session) {
        DataCenter.updateData(session.getUser().getId(), DataType.USER, true);
        EventUtil.fireEvent(EventType.LOGOUT, session.getUser());
    }
}
