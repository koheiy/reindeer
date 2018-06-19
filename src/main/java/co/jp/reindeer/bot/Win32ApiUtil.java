package co.jp.reindeer.bot;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;
import com.sun.jna.platform.win32.WinDef;

public class Win32ApiUtil {
    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);
        boolean SetForegroundWindow(WinDef.HWND hwnd);
        WinDef.HWND FindWindow(String lpClassName, String lpWindowName);
    }
}
