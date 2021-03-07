//
// Created by Matt on 27/02/2021.
//

#include <jni.h>
JNIEXPORT jstring JNICALL
Java_com_example_test_1bank_MainActivity_getApiKey(JNIEnv *env, jobject instance) {
 return (*env)->  NewStringUTF(env, "NjAwN2YxYTQzMDlmOGIwMDE3ZWU1MDIy");
}
JNIEXPORT jstring JNICALL
Java_com_example_test_1bank_MainActivity_getBaseApi(JNIEnv *env, jobject instance) {
 return (*env)->NewStringUTF(env, "TmF0aXZlNWVjcmV0UEBzc3cwcmQy");
}
