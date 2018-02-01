### 系统Service机制拉活

将 Service 设置为 START_STICKY，利用系统机制在 Service 挂掉后自动拉活：

    START_STICKY：
    	“粘性”。如果service进程被kill掉，保留service的状态为开始状态，但不保留递送的intent对象。随后系统会尝试重新创建service，
    	由于服务状态为开始状态，所以创建服务后一定会调用onStartCommand(Intent,int,int)方法。如果在此期间没有任何启动命令被传递到service，
    	那么参数Intent将为null。

    START_NOT_STICKY：
    	“非粘性的”。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统不会自动重启该服务。

    START_REDELIVER_INTENT：
    	重传Intent。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统会自动重启该服务，并将Intent的值传入。

    START_STICKY_COMPATIBILITY：
    	START_STICKY的兼容版本，但不保证服务被kill后一定能重启。

    只要 targetSdkVersion 不小于5，就默认是 START_STICKY。但是某些ROM 系统不会拉活。并且经过测试，Service 第一次被异常杀死后很快被重启，
    第二次会比第一次慢，第三次又会比前一次慢，一旦在短时间内 Service 被杀死4-5次，则系统不再拉起。

