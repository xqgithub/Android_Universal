package com.universal.aifun.baselibrary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.universal.aifun.baselibrary.common.ConfigConstants;
import com.universal.aifun.baselibrary.utils.LogUtils;
import com.universal.aifun.database.DaoMaster;
import com.universal.aifun.database.UserDao;

import org.greenrobot.greendao.database.Database;

/**
 * Date:2021/11/2
 * Time:16:35
 * author:dimple
 * 自定义DaoMaster，为数据库升级
 */
public class MyDaoMaster extends DaoMaster.OpenHelper {

    public MyDaoMaster(Context context, String name) {
        super(context, name);
    }

    public MyDaoMaster(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, UserDao.class);
        LogUtils.i(ConfigConstants.TAG_ALL, "onUpgrade =-= " + oldVersion, " newVersion =-= " + newVersion);
    }
}
