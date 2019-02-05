package com.flame.util;

import java.util.Collection;
import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.flame.base.entity.DeletedEntity;
import com.flame.base.entity.Entity;
import com.flame.base.entity.OperationEntity;
import com.flame.base.enums.Deleted;
import com.flame.base.model.Params;
import com.flame.client.UserHolder;


/**
 * MyBatis会话增删改查拦截器。
 * @author liwei
 */
public class SqlSessionInterceptor implements MethodInterceptor {

	/**
	 * 默认只拦截名称以insert、update、delete、select开始的方法。
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String methodName = invocation.getMethod().getName();
		
		if (methodName.startsWith("insert")) {
			return this.invokeInsert(invocation);
		} else if (methodName.startsWith("update")) {
			return this.invokeUpdate(invocation);
		} else if (methodName.startsWith("delete")) {
			return this.invokeDelete(invocation);
		} else if (methodName.startsWith("select")) {
			return this.invokeSelect(invocation);
		} else {
			return invocation.proceed();
		}
	}

	/**
	 * 插入方法拦截。<br>
	 * 取第二个方法参数，针对该参数时行数据修改
	 * @param invocation
	 * @return
	 * @throws Throwable
	 */
	private Object invokeInsert(MethodInvocation invocation) throws Throwable {
		Object[] args = invocation.getArguments();
		if (args.length > 1) {
			Object object = args[1];
			if (object instanceof Entity) {
				Entity<?> entity = (Entity<?>) object;
				this.insertEntitySet(entity);
			} else if (object instanceof Collection<?>) {
				Collection<?> coll = (Collection<?>) object;
				for (Object item : coll) {
					Entity<?> entity = (Entity<?>) item;
					this.insertEntitySet(entity);
				}
			}
		}
		return invocation.proceed();
	}

	/**
	 * 待插入实体修改。
	 * @param entity
	 */
	private void insertEntitySet(Entity<?> entity) {
		if (UserHolder.getUser() != null && entity instanceof OperationEntity) {
			OperationEntity operationEntity = (OperationEntity) entity;
			String userName = UserHolder.getUserName();
			Date now = UserHolder.getCurrentDate();
			operationEntity.setCreateBy(userName);
			operationEntity.setCreateTime(now);
			operationEntity.setModifyBy(userName);
			operationEntity.setModifyTime(now);
		}
		if (entity instanceof DeletedEntity) {
			((DeletedEntity) entity).setDeleted(Deleted.NO.value);
		}
	}

	/**
	 * 更新方法拦截。<br>
	 * 取第二个方法参数，针对该参数时行数据修改
	 * @param invocation
	 * @return
	 * @throws Throwable
	 */
	private Object invokeUpdate(MethodInvocation invocation) throws Throwable {
		Object[] args = invocation.getArguments();
		if (args.length > 1) {
			Object object = args[1];
			if (object instanceof Entity) {
				Entity<?> entity = (Entity<?>) object;
				this.updateEntitySet(entity);
			} else if (object instanceof Collection<?>) {
				Collection<?> coll = (Collection<?>) object;
				for (Object item : coll) {
					Entity<?> entity = (Entity<?>) item;
					this.updateEntitySet(entity);
				}
			}
		}
		return invocation.proceed();
	}

	/**
	 * 待更新实体修改。
	 * @param entity
	 */
	private void updateEntitySet(Entity<?> entity) {
		if (UserHolder.getUser() != null && entity instanceof OperationEntity) {
			OperationEntity operationEntity = (OperationEntity) entity;
			operationEntity.setModifyBy(UserHolder.getUserName());
			operationEntity.setModifyTime(UserHolder.getCurrentDate());
		}
	}

	/**
	 * 删除方法拦截。<br>
	 * 取第二个方法参数，针对该参数时行数据修改
	 * @param invocation
	 * @return
	 * @throws Throwable
	 */
	private Object invokeDelete(MethodInvocation invocation) throws Throwable {
		Object[] args = invocation.getArguments();
		if (args.length > 1) {
			Object object = args[1];
			if (object instanceof Params) {
				Params params = (Params) object;
				params.addDeleted(Deleted.YES);
				if (UserHolder.getUser() != null) {
					params.addModifyBy(UserHolder.getUserName(), UserHolder.getCurrentDate());
				}
			}
		}
		return invocation.proceed();
	}

	/**
	 * 查询方法拦截。<br>
	 * 取第二个方法参数，针对该参数时行数据修改
	 * @param invocation
	 * @return
	 * @throws Throwable
	 */
	private Object invokeSelect(MethodInvocation invocation) throws Throwable {
		Object[] args = invocation.getArguments();
		if (args.length > 1) {
			Object object = args[1];
			if (object instanceof Params) {
				Params params = (Params) object;
				params.addDeleted(Deleted.NO);
			} else if (object instanceof Entity) {
				Entity<?> entity = (Entity<?>) object;
				if (entity instanceof DeletedEntity) {
					((DeletedEntity) entity).setDeleted(Deleted.NO.value);
				}
			}
		}
		return invocation.proceed();
	}
}
