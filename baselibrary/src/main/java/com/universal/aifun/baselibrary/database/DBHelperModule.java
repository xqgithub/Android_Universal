package com.universal.aifun.baselibrary.database;

import android.database.sqlite.SQLiteDatabase;

import com.universal.aifun.baselibrary.TKBaseApplication;
import com.universal.aifun.baselibrary.common.ConfigConstants;
import com.universal.aifun.baselibrary.utils.LogUtils;
import com.universal.aifun.database.DaoMaster;
import com.universal.aifun.database.DaoSession;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Date:2021/11/2
 * Time:16:39
 * author:dimple
 * 数据库辅助类
 */
public class DBHelperModule {

    private volatile static DBHelperModule dbHelperModule;

    private DaoSession mDaoSession;

    private MyDaoMaster mDaoMaster;

    public static DBHelperModule getInstance() {
        if (dbHelperModule == null) {
            synchronized (DBHelperModule.class) {
                if (dbHelperModule == null) {
                    dbHelperModule = new DBHelperModule();
                }
            }
        }
        return dbHelperModule;
    }


    /**
     * 初始化数据库相关
     */
    public void initDbHelp() {
        mDaoMaster = new MyDaoMaster(TKBaseApplication.myApplication.getApplicationContext(),
                ConfigConstants.DB_SQL_NAME, null);
        SQLiteDatabase db = mDaoMaster.getWritableDatabase();
        DaoMaster mDaomaster = new DaoMaster(db);
        mDaoSession = mDaomaster.newSession();
    }

    /**
     * 输出日志
     */
    public void setDebug() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    /**
     * 关闭Helper
     */
    public void closeHelper() {
        if (mDaoMaster != null) {
            mDaoMaster.close();
            mDaoMaster = null;
        }
    }

    /**
     * 关闭session
     */
    public void closeSession() {
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    public <T> T getDao(Class<? extends Object> entityClass) {
        return (T) mDaoSession.getDao(entityClass);
    }

    /**
     * 插入数据
     */
    public boolean insert(Object object) {
        try {
            Class cls;
            if (object instanceof List) {
                List listObject = (List) object;
                if (listObject.isEmpty()) {
                    LogUtils.e("listObject is null!");
                    return false;
                }
                cls = listObject.get(0).getClass();
                ((AbstractDao<Object, String>) getDao(cls)).insertInTx(listObject);
            } else {
                cls = object.getClass();
                ((AbstractDao<Object, String>) getDao(cls)).insert(object);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 插入或者修改
     */
    public boolean insertOrReplace(Object object) {
        try {
            Class cls;
            if (object instanceof List) {
                List listObject = (List) object;
                if (listObject.isEmpty()) {
                    LogUtils.e("listObject is null!");
                    return false;
                }
                cls = listObject.get(0).getClass();
                ((AbstractDao<Object, String>) getDao(cls)).insertOrReplaceInTx(listObject);
            } else {
                cls = object.getClass();
                ((AbstractDao<Object, String>) getDao(cls)).insertOrReplace(object);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 删除单条数据
     */
    public boolean delete(Object object) {
        try {
            Class cls;
            if (object instanceof List) {
                List listObject = (List) object;
                if (listObject.isEmpty()) {
                    LogUtils.e("listObject is null!");
                    return false;
                }
                cls = listObject.get(0).getClass();
                ((AbstractDao<Object, String>) getDao(cls)).deleteInTx(listObject);
            } else {
                cls = object.getClass();
                ((AbstractDao<Object, String>) getDao(cls)).delete(object);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 删除单条数据，根据主键的值
     */
    public boolean deleteByKey(Object object, long key) {
        try {
            Class cls;
            if (object instanceof List) {
                List listObject = (List) object;
                if (listObject.isEmpty()) {
                    LogUtils.e("listObject is null!");
                    return false;
                }
                cls = listObject.get(0).getClass();
                ((AbstractDao<Object, Long>) getDao(cls)).deleteByKeyInTx(listObject);
            } else {
                cls = object.getClass();
                ((AbstractDao<Object, Long>) getDao(cls)).deleteByKey(key);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 删除全局数据，清空表
     */
    public boolean deleteAll(Class<? extends Object> classType) {
        try {
            ((AbstractDao<Object, String>) getDao(classType)).deleteAll();
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 修改单条数据
     */
    public boolean update(Object object) {
        try {
            Class cls;
            if (object instanceof List) {
                List listObject = (List) object;
                if (listObject.isEmpty()) {
                    LogUtils.e("listObject is null!");
                    return false;
                }
                cls = listObject.get(0).getClass();
                ((AbstractDao<Object, String>) getDao(cls)).updateInTx(listObject);
            } else {
                cls = object.getClass();
                ((AbstractDao<Object, String>) getDao(cls)).update(object);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
            return false;
        }
        return true;
    }


    /**
     * 查询单条数据，根据主键的值
     */
    public <T> T selectByPrimaryKey(Class<T> classType, long key, DaoSession mDaoSession) {
        return ((AbstractDao<T, Object>) getDao(classType)).load(key);
    }

    /**
     * 查询整表数据
     */
    public <T> List<T> loadAll(Class<T> classType, DaoSession mDaoSession) {
        return ((AbstractDao<T, Object>) getDao(classType)).loadAll();
    }

    /**
     * 自定义查询
     */
    public <T> List<T> getQueryRaw(Class<T> classType, DaoSession mDaoSession, String where, String... selectionArg) {
        return ((AbstractDao<T, Object>) getDao(classType)).queryRaw(where, selectionArg);
    }

    /**
     * QueryBuilder 查询
     */
    public <T> QueryBuilder<T> getQueryBuilder(Class<T> classType, DaoSession mDaoSession) {
        return ((AbstractDao<T, Object>) getDao(classType)).queryBuilder();
    }


}
