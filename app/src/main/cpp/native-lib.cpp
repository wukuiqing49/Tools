#include <jni.h>
#include <string>
#include <sys/ptrace.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/prctl.h>

// 加密后的字符串1
static const unsigned char segment1[] = { 34, 84, 17, 100, 33, 87, 18, 103, 100, 18, 87, 34  };

// 加密用的异或 key
const unsigned char xorKey[] = {0x55, 0x23, 0x66, 0x13};

// 反调试检测：通过 ptrace
bool detectPtrace() {
    int ret = ptrace(PTRACE_TRACEME, 0, nullptr, nullptr);
    if (ret == -1) {
        return true;  // 已被调试
    }
    ptrace(PTRACE_DETACH, 0, nullptr, nullptr);
    return false;
}

// 反调试检测：通过 /proc/self/status 的 TracerPid
bool checkTracerPid() {
    FILE* f = fopen("/proc/self/status", "r");
    if (!f) return false;
    char line[256];
    while (fgets(line, sizeof(line), f)) {
        if (strncmp(line, "TracerPid:", 10) == 0) {
            int tracerPid = atoi(line + 10);
            fclose(f);
            return tracerPid != 0;
        }
    }
    fclose(f);
    return false;
}

// 反调试检测：通过 prctl 获取 dumpable 状态
bool checkPtracePrctl() {
    int status = prctl(PR_GET_DUMPABLE);
    return status == 0;
}

// 综合反调试判断
bool isBeingDebugged() {
    return detectPtrace() || checkTracerPid() || checkPtracePrctl();
}

// 解密函数
std::string decryptSegment(const unsigned char* segment, int len) {
    std::string result;
    for (int i = 0; i < len; ++i) {
        char decrypted = segment[i] ^ xorKey[i % sizeof(xorKey)];
        result.push_back(decrypted);
    }
    return result;
}

// 清理敏感内存（简单示例）
void secureClear(std::string& str) {
    volatile char* p = const_cast<volatile char*>(str.data());
    for (size_t i = 0; i < str.size(); ++i) {
        p[i] = 0;
    }
}
// 解密
extern "C"
JNIEXPORT jstring JNICALL
Java_com_wkq_tools_decrypt_DecryptUtil_getDecryptedData(JNIEnv* env, jobject /* this */) {
//    if (isBeingDebugged()) {
//        // 被调试，返回空或崩溃
//        return env->NewStringUTF("");
//    }
    std::string decrypted = decryptSegment(segment1, sizeof(segment1));
    jstring result = env->NewStringUTF(decrypted.c_str());
    // 解密字符串用完后，清理内存
    secureClear(decrypted);
    return result;
}
