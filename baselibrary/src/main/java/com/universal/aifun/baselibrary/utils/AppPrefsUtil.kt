package com.universal.aifun.baselibrary.utils

/**
 * Author  guoyw
 * Date    2021/5/12
 * Desc
 */
class AppPrefsUtil {

    companion object {
        //主色
        private const val PRIMARY_COLOR = "ns_primary_color"
        // 保存保存主色
        fun savePrimaryColor(color: String) {
            SpUtil.putString(PRIMARY_COLOR, color)
        }
        // 获取主色
        fun getPrimaryColor(): String {
            return SpUtil.getString(PRIMARY_COLOR)
        }


        //本地头像地址 TODO 可以不需要了
        private const val LOCAL_HEADER_URL = "local_header_url"
        // 保存本地头像地址
        fun saveLocalHeaderUrl(color: String) {
            SpUtil.putString(PRIMARY_COLOR, color)
        }
        // 获取本地头像地址
        fun getLocalHeaderUrl(): String {
            return SpUtil.getString(PRIMARY_COLOR)
        }


        //远程头像地址
        private const val REMOTE_HEADER_URL = "remote_header_url"
        // 保存远程头像地址
        @JvmStatic
        fun saveRemoteHeaderUrl(url: String) {
            SpUtil.putString(REMOTE_HEADER_URL, url)
        }
        // 获取本地头像地址
        @JvmStatic
        fun getRemoteHeaderUrl(): String {
            return SpUtil.getString(REMOTE_HEADER_URL)
        }


        // 用户名
        private const val KEY_USER_NAME = "key_user_name"
        @JvmStatic
        fun saveUserName(name: String) {
            SpUtil.putString(KEY_USER_NAME, name)
        }
        @JvmStatic
        fun getUserName(): String {
            return SpUtil.getString(KEY_USER_NAME)
        }


        // 用户身份
        private const val KEY_USER_IDENTITY = "key_user_identity"
        @JvmStatic
        fun saveUserIdentity(identity: String) {
            SpUtil.putString(KEY_USER_IDENTITY, identity)
        }
        @JvmStatic
        fun getUserIdentity(): String {
            return SpUtil.getString(KEY_USER_IDENTITY)
        }


        // 用户id
        private const val KEY_USER_ID = "key_user_id"
        @JvmStatic
        fun saveUserId(userid: String) {
            SpUtil.putString(KEY_USER_ID, userid)
        }
        @JvmStatic
        fun getUserId(): String {
            return SpUtil.getString(KEY_USER_ID)
        }
    }

}