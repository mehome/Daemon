### 账户同步 拉活

	手机系统设置里会有“帐户”一项功能，任何第三方APP都可以通过此功能将数据在一定时间内同步到服务器中去。系统在将APP帐户同步时，会将未启动的APP进程拉活。
	https://github.com/googlesamples/android-BasicSyncAdapter

    在AuthenticationService的onBind需要返回AbstractAccountAuthenticator的getIBinder
