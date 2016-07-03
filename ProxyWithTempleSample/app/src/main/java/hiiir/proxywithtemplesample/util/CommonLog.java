
package hiiir.proxywithtemplesample.util;

import android.os.Debug;

/**
 * 扩展功能的Log，可以实现点击定位，{@link Debug#DEG}控制log输出 <br />
 * <br />
 * 1.只有一个参数的函数，它的tag使用默认{@link #TAG}. 而两个参数的可以自定义tag。<br />
 * 2.在eclipse的LogCat中输出时，双击[PLACE]开头的那一行就可以定位到发出log的那一行代码.<br />
 * 3.[MESSAGE]开头的那一行就是log消息。 <br />
 * <br />
 * <b style="color:red;">正式版发布时，一定要把{@link Debug#DEG}设置为false</b> </br>
 * 
 * @author zhangshaolin
 */
public class CommonLog {
    private static boolean DEG = true;

    private static final String TAG = "CMBattery";
    private static final String HEAD_MESSAGE = "[MESSAGE] ";
    private static final String HEAD_PLACE = "[PLACE] ";
    private static final String BLANK_LINE = "\n ";

    private static Integer sLogCount = 0;

    private static final int STACK_LAYER = 3;

    public static int i(String msg) {
        return android.util.Log.i(TAG, getFormatedMsg(null, msg));
    }

    public static int e(String msg) {
        return android.util.Log.e(TAG, getFormatedMsg(null, msg));
    }

    public static int d(String msg) {
        synchronized (sLogCount) {
            sLogCount++;
        }
        return android.util.Log.d(TAG, "[" + sLogCount + "]" + getFormatedMsg(null, msg));
    }

    public static int v(String msg) {
        return android.util.Log.v(TAG, getFormatedMsg(null, msg));
    }

    public static int w(String msg) {
        return android.util.Log.w(TAG, getFormatedMsg(null, msg));
    }

    public static int i(String subTag, String msg) {
        return android.util.Log.i(TAG, getFormatedMsg(subTag, msg));
    }

    public static int e(String subTag, String msg) {
        return android.util.Log.e(TAG, getFormatedMsg(subTag, msg));
    }

    public static int d(String subTag, String msg) {
        synchronized (sLogCount) {
            sLogCount++;
        }
        return android.util.Log.d(TAG, "[" + sLogCount + "]" + getFormatedMsg(subTag, msg));
    }

    public static int v(String subTag, String msg) {
        return android.util.Log.v(TAG, getFormatedMsg(subTag, msg));
    }

    public static int w(String subTag, String msg) {
        return android.util.Log.w(TAG, getFormatedMsg(subTag, msg));
    }
    
    //直接支持DEBUG flag判断
    public static int d(boolean DEBUG, String subTag, String msg) {
    	if (DEBUG) {
    		return CommonLog.d(subTag, msg);
    	}
    	
    	return 0;
    }

    public static int e(boolean DEBUG, String subTag, String msg) {
        if (DEBUG) {
            e(subTag, msg);
        }

        return 0;
    }

    public static int i(boolean DEBUG, String subTag, String msg) {
        if (DEBUG) {
            i(subTag, msg);
        }

        return 0;
    }
    
    //记录exception
    public static int d(boolean DEBUG, String subTag, Exception e) {
    	if (DEBUG && null != e && null != e.getMessage()) {
    		return CommonLog.d(subTag, e.getMessage());
    	}
    	
    	return 0;
    }

    private static String getFormatedMsg(String subTag, String msg) {
        if (subTag == null) {
            return HEAD_PLACE + getPlace() + HEAD_MESSAGE
                    + msg + BLANK_LINE;
        } else {
            return HEAD_PLACE + getPlace() + "[" + subTag + "]"
                    + msg;
        }
    }

    private static String getPlace() {
        java.lang.StackTraceElement[] stacks = new java.lang.Throwable().getStackTrace();
        if (stacks.length <= STACK_LAYER) {
            return "";
        }
        return new java.lang.StringBuilder().append("at ")
                .append(stacks[STACK_LAYER].getClassName()).append(".")
                .append(stacks[STACK_LAYER].getMethodName()).append("(")
                .append(stacks[STACK_LAYER].getFileName()).append(":")
                .append(stacks[STACK_LAYER].getLineNumber()).append(")\n").toString();
    }

    public static boolean isDEG() {
        return DEG;
    }

    public static boolean setDEG(boolean dEG) {
        DEG = dEG;
        return isDEG();
    }
}
