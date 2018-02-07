package info.xiaomo.server.command;

import info.xiaomo.core.base.common.AttributeUtil;
import info.xiaomo.core.base.concurrent.command.AbstractCommand;
import info.xiaomo.server.server.Session;
import info.xiaomo.server.server.SessionKey;
import info.xiaomo.server.system.user.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author xiaomo
 */
public class LogoutCommand extends AbstractCommand {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogoutCommand.class);

	private Session session;


	public LogoutCommand(Session session) {
		this.session = session;
	}


	@Override
	public void doAction() {

		Boolean logoutHandled =  AttributeUtil.get(session.getChannel(), SessionKey.LOGOUT_HANDLED);
		if(Boolean.TRUE.equals(logoutHandled)) {
			LOGGER.error("网络连接断开的时候玩家已经处理过下线事件[顶号]->{}", session.getUser().toString());
			return;
		}
		AttributeUtil.set(session.getChannel(), SessionKey.LOGOUT_HANDLED, true);
		//登出
		UserManager.getInstance().logout(session);
		LOGGER.error("网络连接断开，处理玩家下线逻辑->{}", session.getUser().toString());
	}


}
